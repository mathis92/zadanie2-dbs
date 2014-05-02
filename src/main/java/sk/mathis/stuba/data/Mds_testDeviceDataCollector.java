/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.equip.Diagnosis;
import sk.mathis.stuba.equip.TestTypes;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnosis;
import sk.mathis.stuba.hibernatemapper.MdsDiagnostician;
import sk.mathis.stuba.hibernatemapper.MdsRepair;
import sk.mathis.stuba.hibernatemapper.MdsServiceOrder;
import sk.mathis.stuba.hibernatemapper.MdsTest;
import sk.mathis.stuba.hibernatemapper.MdsTesting;
import sk.mathis.stuba.mobiledeviceservice.Mds_testDevicePanel;

public class Mds_testDeviceDataCollector {

    private final Mds_testDevicePanel testPanel;

    public Mds_testDeviceDataCollector(Mds_testDevicePanel testPanel) {
        this.testPanel = testPanel;
        fillDevicesToTestTable();
    }

    public void fillDiagnosisTable(MdsDevice idevice) {
        ArrayList<Diagnosis> diagList = new ArrayList<Diagnosis>();
        Integer added = 0;

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(MdsTesting.class);
        cr.add(Restrictions.eq("mdsDevice", idevice));
        List<MdsTesting> testing = cr.list();
        MdsDevice device = testing.get(0).getMdsDevice();
        cr = session.createCriteria(MdsTest.class);
        cr.add(Restrictions.eq("mdsTesting", testing.get(0)));
        List<MdsTest> test = cr.list();
        MdsDiagnostician diagnostician = testing.get(0).getMdsDiagnostician();
        for (int i = 0; i < test.size(); i++) {
            for (Diagnosis temp : diagList) {
                if (temp.getDeviceId().equals(device.getIdDevice())) {
                    temp.addDiagnosis(test.get(i).getType() + "->" + test.get(i).getFoundFault());
                    added = 1;
                }
            }
            if (!added.equals(1)) {
                Diagnosis diag = new Diagnosis(device.getIdDevice(), test.get(i).getType() + "->" + test.get(i).getFoundFault(), diagnostician.getIdDiagnostician());
                diagList.add(diag);
            }
        }
        MdsDiagnosis diagnosis = new MdsDiagnosis();
        diagnosis.setMdsDevice(device);
        diagnosis.setMdsDiagnostician(diagnostician);
        diagnosis.setSpecification(diagList.get(0).getSpecification());
        diagnosis.setSetTime(new Timestamp(new Date().getTime()));
        session.save(diagnosis);
        session.getTransaction().commit();
        session.close();

    }

    public void fillTests(ArrayList<TestTypes> testTypes, ArrayList<JFormattedTextField> faultfields, MdsTesting testing) {
        for (int i = 0; i < faultfields.size(); i++) {
            Session session = DataHelpers.sessionFactory.openSession();
            session.beginTransaction();
            MdsTest test = new MdsTest();
            test.setFoundFault(faultfields.get(i).getText());
            test.setMdsTesting(testing);
            test.setType(testTypes.get(i).toString());
            session.save(test);
            session.getTransaction().commit();
            session.close();
        }
    }

    public void fillSelectedDeviceTable(Integer selectedRow) {
        Object[] data = new Object[5];
        DefaultTableModel selectedDevice;

        selectedDevice = (DefaultTableModel) testPanel.getSelectedDeviceTable().getModel();

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(MdsDevice.class).add(Restrictions.eq("tested", false));
        List<MdsDevice> deviceList = cr.list();
        int i = 0;
        for (MdsDevice temp : deviceList) {
            if (selectedRow.equals(i)) {
                selectedDevice.setRowCount(0);
                data[0] = temp.getIdDevice();
                data[1] = temp.getImei();
                data[3] = temp.getMdsDeviceModel().getMdsDeviceVendor().getVendor();
                data[2] = temp.getMdsDeviceModel().getModel();
                data[4] = ((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", temp)).list().get(0)).getFaultDescription();
                testPanel.getFaultTextArea().setText(((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", temp)).list().get(0)).getFaultDescription());
                selectedDevice.addRow(data);
                testPanel.getSelectedDeviceTable().setModel(selectedDevice);
            }
            i++;
        }
        session.getTransaction().commit();
        session.close();
    }

    public final void fillDevicesToTestTable() {
        Object[] data = new Object[5];
        DefaultTableModel devicesToTestTable;

        devicesToTestTable = (DefaultTableModel) testPanel.getDevicesToTestTable().getModel();

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(MdsDevice.class).add(Restrictions.eq("tested", false));
        List<MdsDevice> deviceList = cr.list();
        for (MdsDevice temp : deviceList) {
            data[0] = temp.getIdDevice();
            data[1] = temp.getImei();
            data[3] = temp.getMdsDeviceModel().getMdsDeviceVendor().getVendor();
            data[2] = temp.getMdsDeviceModel().getModel();

            data[4] = ((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", temp)).list().get(0)).getFaultDescription();
            devicesToTestTable.addRow(data);
            testPanel.getDevicesToTestTable().setModel(devicesToTestTable);

        }
        session.getTransaction().commit();
        session.close();

    }
}
