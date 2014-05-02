/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.data;

import java.util.List;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnosis;
import sk.mathis.stuba.mobiledeviceservice.Mds_repairDevicePanel;

public class Mds_repairDeviceDataCollector {

    private final Mds_repairDevicePanel repairPanel;

    public Mds_repairDeviceDataCollector(Mds_repairDevicePanel repairPanel) {
        this.repairPanel = repairPanel;
        fillDevicesToTestTable();
    }

    public final void fillDevicesToTestTable() {
        Object[] data = new Object[5];
        DefaultTableModel devicesToRepairTable;

        devicesToRepairTable = (DefaultTableModel) repairPanel.getDevicesToRepairTable().getModel();

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(MdsDevice.class).add(Restrictions.eq("repaired", false));
        List<MdsDevice> deviceList = cr.list();
        for (MdsDevice temp : deviceList) {
            data[0] = temp.getIdDevice();
            data[1] = temp.getImei();
            data[3] = temp.getMdsDeviceModel().getMdsDeviceVendor().getVendor();
            data[2] = temp.getMdsDeviceModel().getModel();
            for (MdsDiagnosis temp1 : (Set<MdsDiagnosis>) temp.getMdsDiagnosises()) {
                if (temp1.getMdsDevice().equals(temp)) {
                    data[4] = temp1.getSpecification();
                }
            }
            devicesToRepairTable.addRow(data);
            repairPanel.getDevicesToRepairTable().setModel(devicesToRepairTable);
        }
        session.getTransaction().commit();
        session.close();

    }

    public void fillSelectedDeviceTable(Integer selectedRow) {
        Object[] data = new Object[5];
        DefaultTableModel selectedDevice;

        selectedDevice = (DefaultTableModel) repairPanel.getSelectedDeviceTable().getModel();

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        Criteria cr = session.createCriteria(MdsDevice.class).add(Restrictions.eq("repaired", false));
        List<MdsDevice> deviceList = cr.list();
        int i = 0;
        for (MdsDevice temp : deviceList) {
            if (selectedRow.equals(i)) {
                selectedDevice.setRowCount(0);
                data[0] = temp.getIdDevice();
                data[1] = temp.getImei();
                data[3] = temp.getMdsDeviceModel().getMdsDeviceVendor().getVendor();
                data[2] = temp.getMdsDeviceModel().getModel();
                for (MdsDiagnosis temp1 : (Set<MdsDiagnosis>) temp.getMdsDiagnosises()) {
                    if (temp1.getMdsDevice().equals(temp)) {
                        data[4] = temp1.getSpecification();
                        repairPanel.getDiagnosisTextArea().setText(temp1.getSpecification());
                    }
                }
                selectedDevice.addRow(data);
                repairPanel.getSelectedDeviceTable().setModel(selectedDevice);
            }
            i++;
        }
        session.getTransaction().commit();
        session.close();
    }
}
