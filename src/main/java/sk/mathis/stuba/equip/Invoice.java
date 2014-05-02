/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.equip;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnosis;
import sk.mathis.stuba.hibernatemapper.MdsDiagnostician;
import sk.mathis.stuba.hibernatemapper.MdsRepair;
import sk.mathis.stuba.hibernatemapper.MdsSentDevices;
import sk.mathis.stuba.hibernatemapper.MdsServiceClaimant;
import sk.mathis.stuba.hibernatemapper.MdsServiceOrder;
import sk.mathis.stuba.hibernatemapper.MdsTesting;

public class Invoice {

    public static String RESULT;

    public void createPdf(MdsRepair repair) throws DocumentException, FileNotFoundException, IOException, InterruptedException {
        Document document = new Document();
        setRESULT(repair.getIdRepair());
        PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        document.open();
        document.add(new Paragraph("MobileDeviceService"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Personal informations :"));

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        session.refresh(repair);

        MdsDiagnosis diagnosis = repair.getMdsDiagnosis();
        MdsDiagnostician repairDiagnostician = repair.getMdsDiagnostician();
        MdsDevice device = diagnosis.getMdsDevice();

        MdsServiceOrder order = ((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", device)).list().get(0));
        MdsDiagnostician testDiagnostician = ((MdsTesting) session.createCriteria(MdsTesting.class).add(Restrictions.eq("mdsDevice", device)).list().get(0)).getMdsDiagnostician();
        MdsDiagnostician sentDiagnostician = ((MdsSentDevices) session.createCriteria(MdsSentDevices.class).add(Restrictions.eq("mdsRepair", repair)).list().get(0)).getMdsDiagnostician();
        Date sentDate = ((MdsSentDevices) session.createCriteria(MdsSentDevices.class).add(Restrictions.eq("mdsRepair", repair)).list().get(0)).getSentDate();
        MdsServiceClaimant claimant = order.getMdsServiceClaimant();

        System.out.println(claimant.getAdress());
        document.add(new Paragraph(claimant.getName()));
        document.add(new Paragraph(claimant.getAdress()));
        document.add(new Paragraph(claimant.getEmail()));
        document.add(new Paragraph(claimant.getPhoneNumber()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Registration Date :"));
        document.add(new Paragraph(order.getRegistrationDate().toString()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Fault :"));
        document.add(new Paragraph(order.getFaultDescription()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("IMEI :"));
        document.add(new Paragraph(device.getImei()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Device type :"));
        document.add(new Paragraph(device.getType()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Repair report :"));
        document.add(new Paragraph(repair.getReport()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Repair costs : "));
        document.add(new Paragraph(Double.toString(repair.getRepairCosts()) + "€"));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Send back date : "));
        document.add(new Paragraph(sentDate.toString()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Device tested by :"));
        document.add(new Paragraph(testDiagnostician.getName()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Device repaired by :"));
        document.add(new Paragraph(repairDiagnostician.getName()));

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Device sent by :"));
        document.add(new Paragraph(sentDiagnostician.getName()));
        session.getTransaction().commit();
        session.close();
        document.close();

        File file = new File(RESULT);
        if (file.toString().endsWith(".pdf")) {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + file);
        } else {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        }
    }

    public void setRESULT(Integer id_repair) {
        RESULT = "src/main/java/sk/mathis/stuba/invoices/invoice" + id_repair + ".pdf";
    }
}
