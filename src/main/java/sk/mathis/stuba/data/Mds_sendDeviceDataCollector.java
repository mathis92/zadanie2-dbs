package sk.mathis.stuba.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnosis;
import sk.mathis.stuba.hibernatemapper.MdsRepair;
import sk.mathis.stuba.hibernatemapper.MdsServiceOrder;
import sk.mathis.stuba.hibernatemapper.MdsTest;
import sk.mathis.stuba.hibernatemapper.MdsTesting;
import sk.mathis.stuba.mobiledeviceservice.Mds_sendDevicePanel;

public class Mds_sendDeviceDataCollector {

    private final Mds_sendDevicePanel sendPanel;

    public Mds_sendDeviceDataCollector(Mds_sendDevicePanel sendPanel) {
        this.sendPanel = sendPanel;
        fillDevicesToTestTable();
    }

    public final void fillDevicesToTestTable() {
        Object[] data = new Object[5];
        DefaultTableModel devicesToSendTable;

        devicesToSendTable = (DefaultTableModel) sendPanel.getDevicesToSendTable().getModel();

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        List<MdsDevice> devList = session.createCriteria(MdsDevice.class).add(Restrictions.eq("repaired", true)).add(Restrictions.eq("tested", true)).list();
        Criteria cr = session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("deviceSent", false));
        List<MdsServiceOrder> deviceList = cr.list();
        List<MdsServiceOrder> list = new ArrayList<>();
        for(MdsServiceOrder temp : deviceList){
            if(devList.contains(temp.getMdsDevice())){
                list.add(temp);
            }
        }
        for (MdsServiceOrder temp : list) {
            data[0] = temp.getMdsDevice().getIdDevice();
            data[2] = temp.getMdsDevice().getMdsDeviceModel().getMdsDeviceVendor().getVendor();
            data[1] = temp.getMdsDevice().getImei();
            data[3] = temp.getMdsDevice().getMdsDeviceModel().getModel();
            for (MdsDiagnosis temp1 : (Set<MdsDiagnosis>) temp.getMdsDevice().getMdsDiagnosises()) {
                if (temp1.getMdsDevice().equals(temp.getMdsDevice())) {
                    for (MdsRepair temp2 : (Set<MdsRepair>) temp1.getMdsRepairs()) {
                        if (temp2.getMdsDiagnosis().equals(temp1)) {
                            data[4] = temp2.getReport();
                        }
                    }
                }
            }
            devicesToSendTable.addRow(data);
            sendPanel.getDevicesToSendTable().setModel(devicesToSendTable);

        }
        session.getTransaction().commit();
        session.close();
    }

    public void fillSelectedDeviceTable(Integer selectedRow) {
        Object[] data = new Object[5];
        DefaultTableModel selectedDevice;

        selectedDevice = (DefaultTableModel) sendPanel.getSelectedDeviceTable().getModel();
        selectedDevice.setRowCount(0);
        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        List<MdsDevice> devList = session.createCriteria(MdsDevice.class).add(Restrictions.eq("repaired", true)).add(Restrictions.eq("tested", true)).list();
        Criteria cr = session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("deviceSent", false));
        List<MdsServiceOrder> deviceList = cr.list();
        List<MdsServiceOrder> list = new ArrayList<>();
        for(MdsServiceOrder temp : deviceList){
            if(devList.contains(temp.getMdsDevice())){
                list.add(temp);
            }
        }
        
        int i = 0;
        for (MdsServiceOrder temp : list) {
            if (selectedRow.equals(i)) {
                data[0] = temp.getMdsDevice().getIdDevice();
                data[2] = temp.getMdsDevice().getMdsDeviceModel().getMdsDeviceVendor().getVendor();
                data[1] = temp.getMdsDevice().getImei();
                data[3] = temp.getMdsDevice().getMdsDeviceModel().getModel();
                for (MdsDiagnosis temp1 : (Set<MdsDiagnosis>) temp.getMdsDevice().getMdsDiagnosises()) {
                    if (temp1.getMdsDevice().equals(temp.getMdsDevice())) {
                        System.out.println("sems");
                        for (MdsRepair temp2 : (Set<MdsRepair>) temp1.getMdsRepairs()) {
                            if (temp2.getMdsDiagnosis().equals(temp1)) {
                                System.out.println("sems+");
                                data[4] = temp2.getReport();
                                sendPanel.getReportTextArea().setText(temp2.getReport());
                            }
                        }
                    }
                }
                selectedDevice.addRow(data);
                sendPanel.getSelectedDeviceTable().setModel(selectedDevice);
                break;
            }
            i++;
        }
        session.getTransaction().commit();
        session.close();
    }

}
