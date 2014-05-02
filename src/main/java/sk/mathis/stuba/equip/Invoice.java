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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Invoice {

    public static String RESULT = "F:\\Moje dokumenty\\Martin HUdec\\škola\\FIIT\\4. sem\\DBS\\MobileDeviceService\\src\\main\\java\\sk\\mathis\\stuba\\invoices\\invoice";

    public void createPdf(Integer id_repair) throws DocumentException, FileNotFoundException, IOException, InterruptedException {
        Document document = new Document();
        setRESULT(id_repair);
        PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        document.open();
        document.add(new Paragraph("MobileDeviceService"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("Personal informations :"));
        try {
            ResultSet rs;
            rs = DataHelpers.selectFrom("SELECT name,adress,email,phone_number,registration_date,fault_description,imei,model,vendor,report,repair_costs,sent_date,effected FROM (SELECT mds_service_claimant.name,mds_service_claimant.adress,mds_service_claimant.email,mds_service_claimant.phone_number,mds_service_order.registration_date,mds_service_order.fault_description,mds_service_order.device_sent,mds_device.tested,mds_device.repaired,mds_device.`type`,mds_device.id_device ,mds_device.imei,mds_device_model.model,mds_device_vendor.vendor,mds_diagnosis.specification,mds_repair.report,mds_repair.repair_costs,mds_repair.id_repair,`diags`.`effected`,`mds_sent_devices`.sent_date\n"
                    + "FROM mds_service_claimant\n"
                    + "	LEFT JOIN mds_service_order\n"
                    + "		ON mds_service_claimant.id_service_claimant = mds_service_order.id_claimant\n"
                    + "	LEFT JOIN mds_device\n"
                    + "		ON mds_service_order.id_device = mds_device.id_device\n"
                    + "	LEFT JOIN mds_device_model\n"
                    + "		ON mds_device.id_device_model = mds_device_model.id_device_model \n"
                    + "	LEFT JOIN mds_device_vendor\n"
                    + "		ON mds_device_model.id_device_vendor = mds_device_vendor.id_device_vendor\n"
                    + "	LEFT JOIN mds_diagnosis\n"
                    + "		ON mds_device.id_device = mds_diagnosis.id_device\n"
                    + "	LEFT JOIN mds_repair\n"
                    + "		ON mds_diagnosis.id_diagnosis = mds_repair.id_diagnosis\n"
                    + "	LEFT JOIN mds_sent_devices\n"
                    + "		ON mds_repair.id_repair = mds_sent_devices.id_repair  \n"
                    + "	LEFT JOIN mds_invoice \n"
                    + "		ON mds_invoice.id_repair = mds_repair.id_repair\n"
                    + "	LEFT JOIN (SELECT GROUP_CONCAT(diagT.name,' , ',diagR.name ,' , ' ,diagD.name) AS `effected`, mds_device.id_device\n"
                    + "			FROM mds_device \n"
                    + "		LEFT JOIN mds_testing AS testing\n"
                    + "			ON mds_device.id_device = testing.id_device  \n"
                    + "		LEFT JOIN mds_diagnostician AS diagT\n"
                    + "			ON testing.id_head_diagnostician = diagT.id_diagnostician\n"
                    + "		LEFT JOIN mds_diagnosis AS diagnosis\n"
                    + "			ON mds_device.id_device = diagnosis.id_device\n"
                    + "		LEFT JOIN mds_diagnostician AS diagD\n"
                    + "			ON diagnosis.id_diagnostician  = diagD.id_diagnostician\n"
                    + "		LEFT JOIN mds_repair AS `repair` \n"
                    + "			ON diagnosis.id_diagnosis = `repair`.id_diagnosis\n"
                    + "		LEFT JOIN mds_diagnostician AS diagR\n"
                    + "			ON `repair`.id_diagnostician = diagR.id_diagnostician\n"
                    + "		LEFT JOIN mds_sent_devices AS `sent`\n"
                    + "			ON `repair`.id_repair = sent.id_repair\n"
                    + "		GROUP BY id_device) AS `diags`\n"
                    + "	ON mds_device.id_device = diags.id_device\n"
                    + "	GROUP BY (imei)\n"
                    + "	ORDER BY id_device ASC) AS `table1` WHERE  table1.id_repair ='" + id_repair + "'");

            while (rs.next()) {
                for (int i = 0; i < 13; i++) {
                    if (i == 4) {
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Registration Date :"));
                    }
                    if (i == 5) {
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Fault :"));
                    }
                    if (i == 6) {
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("IMEI :"));
                    }
                    if (i == 7) {
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Device type :"));
                    }
                    if (i == 9) {
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Repair report :"));
                    }
                    if (i == 10) {
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Repair costs : "));
                    }
                    if (i == 11) {
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Send back date : "));
                    }
                    if(i == 12){
                        document.add(new Paragraph("\n"));
                        document.add(new Paragraph("Device repaired by :"));
                    }
                    document.add(new Paragraph(rs.getString(i + 1) + ((i == 10) ? "€" : "")));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Invoice.class.getName()).log(Level.SEVERE, null, ex);
        }
        document.close();
        Process p = Runtime
                .getRuntime()
                .exec("rundll32 url.dll,FileProtocolHandler c:\\" + RESULT);
        p.waitFor();
    }

    public void setRESULT(Integer id_repair) {
        RESULT = RESULT + id_repair + ".pdf";
    }
}
