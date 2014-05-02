/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.data;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.equip.FindingTypes;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnosis;
import sk.mathis.stuba.hibernatemapper.MdsRepair;
import sk.mathis.stuba.hibernatemapper.MdsServiceClaimant;
import sk.mathis.stuba.hibernatemapper.MdsServiceOrder;

/**
 *
 * @author Mathis
 */
public class Mds_findSpecificDeviceDataCollector {

    private final String findingMask;
    private final JTable claimantData;
    private final FindingTypes findingType;
    private final JTable deviceData;

    public Mds_findSpecificDeviceDataCollector(FindingTypes findingType, String findingMask, JTable claimantData, JTable deviceData) {
        this.findingMask = findingMask;
        this.claimantData = claimantData;
        this.findingType = findingType;
        this.deviceData = deviceData;
        fillTable();
    }

    public final void fillTable() {
        ResultSet rs;
        switch (findingType) {

            case EMAIL: {
                Session session = DataHelpers.sessionFactory.openSession();
                session.beginTransaction();
                List<MdsServiceClaimant> claimantList = session.createCriteria(MdsServiceClaimant.class).add(Restrictions.eq("email", findingMask)).list();
                fillClaimantDataTable(claimantList);
                Object[] data = new Object[9];
                DefaultTableModel deviceDataTablemodel;
                deviceDataTablemodel = (DefaultTableModel) deviceData.getModel();
                for (MdsServiceOrder temp : (Set<MdsServiceOrder>) claimantList.get(0).getMdsServiceOrders()) {
                    data[1] = temp.getMdsDevice().getImei();
                    data[0] = temp.getMdsDevice().getIdDevice();
                    data[2] = temp.getRegistrationDate();
                    data[3] = temp.getMdsDevice().getMdsDeviceModel().getModel();
                    data[4] = temp.getMdsDevice().getMdsDeviceModel().getMdsDeviceVendor().getVendor();
                    data[5] = temp.getFaultDescription();
                    data[6] = temp.getMdsDevice().isRepaired();
                    data[7] = ((MdsRepair) session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", ((MdsDiagnosis) session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", temp.getMdsDevice())).list().get(0)))).list().get(0)).getRepairCosts();
                    data[8] = ((MdsRepair) session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", ((MdsDiagnosis) session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", temp.getMdsDevice())).list().get(0)))).list().get(0)).getReport();
                    deviceDataTablemodel.addRow(data);
                    deviceData.setModel(deviceDataTablemodel);
                }

                session.getTransaction().commit();
                session.close();
                break;
            }
            case IMEI: {
                Session session = DataHelpers.sessionFactory.openSession();
                session.beginTransaction();
                MdsDevice device = (MdsDevice) session.createCriteria(MdsDevice.class).add(Restrictions.eq("imei", findingMask)).list().get(0);
                MdsServiceClaimant claimant = ((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", device)).list().get(0)).getMdsServiceClaimant();
                ArrayList<MdsServiceClaimant> list = new ArrayList<>();
                list.add(claimant);
                fillClaimantDataTable(list);
                Object[] data = new Object[9];
                DefaultTableModel deviceDataTablemodel;
                deviceDataTablemodel = (DefaultTableModel) deviceData.getModel();

                data[1] = device.getImei();
                data[0] = device.getIdDevice();
                data[2] = ((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", device)).list().get(0)).getRegistrationDate();
                data[3] = device.getMdsDeviceModel().getModel();
                data[4] = device.getMdsDeviceModel().getMdsDeviceVendor().getVendor();
                data[5] = ((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", device)).list().get(0)).getFaultDescription();
                data[6] = device.isRepaired();
                data[7] = ((MdsRepair) session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", ((MdsDiagnosis) session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", device)).list().get(0)))).list().get(0)).getRepairCosts();
                data[8] = ((MdsRepair) session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", ((MdsDiagnosis) session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", device)).list().get(0)))).list().get(0)).getReport();
                deviceDataTablemodel.addRow(data);
                deviceData.setModel(deviceDataTablemodel);

                session.getTransaction().commit();
                session.close();
                break;

            }

            case PHONENUMBER: {
                Session session = DataHelpers.sessionFactory.openSession();
                session.beginTransaction();
                List<MdsServiceClaimant> claimantList = session.createCriteria(MdsServiceClaimant.class).add(Restrictions.eq("phoneNumber", findingMask)).list();
                fillClaimantDataTable(claimantList);
                Object[] data = new Object[9];
                DefaultTableModel deviceDataTablemodel;
                deviceDataTablemodel = (DefaultTableModel) deviceData.getModel();
                for (MdsServiceOrder temp : (Set<MdsServiceOrder>) claimantList.get(0).getMdsServiceOrders()) {
                    data[1] = temp.getMdsDevice().getImei();
                    data[0] = temp.getMdsDevice().getIdDevice();
                    data[2] = temp.getRegistrationDate();
                    data[3] = temp.getMdsDevice().getMdsDeviceModel().getModel();
                    data[4] = temp.getMdsDevice().getMdsDeviceModel().getMdsDeviceVendor().getVendor();
                    data[5] = temp.getFaultDescription();
                    data[6] = temp.getMdsDevice().isRepaired();
                    data[7] = ((MdsRepair) session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", ((MdsDiagnosis) session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", temp.getMdsDevice())).list().get(0)))).list().get(0)).getRepairCosts();
                    data[8] = ((MdsRepair) session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", ((MdsDiagnosis) session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", temp.getMdsDevice())).list().get(0)))).list().get(0)).getReport();
                    deviceDataTablemodel.addRow(data);
                    deviceData.setModel(deviceDataTablemodel);
                }

                session.getTransaction().commit();
                session.close();
                break;

            }
        }
    }

    public void fillClaimantDataTable(List<MdsServiceClaimant> claimantList) {
        Object[] data = new Object[8];
        DefaultTableModel claimantDataTablemodel;

        claimantDataTablemodel = (DefaultTableModel) claimantData.getModel();
        for (MdsServiceClaimant claimant : claimantList) {
            data[0] = claimant.getIdServiceClaimant();
            data[1] = claimant.getName();
            data[2] = claimant.getAdress();
            data[3] = claimant.getCity();
            data[4] = claimant.getCountry();
            data[5] = claimant.getEmail();
            data[6] = claimant.getPhoneNumber();
            data[7] = claimant.getLegalType();
            claimantDataTablemodel.addRow(data);
            claimantData.setModel(claimantDataTablemodel);
        }

    }

}
