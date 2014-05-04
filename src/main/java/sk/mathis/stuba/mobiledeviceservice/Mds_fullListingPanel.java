package sk.mathis.stuba.mobiledeviceservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.hql.internal.ast.tree.RestrictableStatement;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnosis;
import sk.mathis.stuba.hibernatemapper.MdsDiagnostician;
import sk.mathis.stuba.hibernatemapper.MdsRepair;
import sk.mathis.stuba.hibernatemapper.MdsSentDevices;
import sk.mathis.stuba.hibernatemapper.MdsServiceClaimant;
import sk.mathis.stuba.hibernatemapper.MdsServiceOrder;
import sk.mathis.stuba.hibernatemapper.MdsTesting;

public class Mds_fullListingPanel extends javax.swing.JPanel {

    private Mds_mainGui gui;

    public Mds_fullListingPanel(Mds_mainGui gui) {
        this.gui = gui;
        initComponents();
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void fillData(Integer deviceId) {
        ResultSet rs;

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        MdsDevice device = (MdsDevice) session.get(MdsDevice.class, deviceId);

        MdsDiagnosis diagnosis = (MdsDiagnosis) session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", device)).list().get(0);
        MdsRepair repair = (MdsRepair) session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", diagnosis)).list().get(0);
        MdsDiagnostician repairDiagnostician = repair.getMdsDiagnostician();
        MdsServiceOrder order = ((MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", device)).list().get(0));
        MdsDiagnostician testDiagnostician = ((MdsTesting) session.createCriteria(MdsTesting.class).add(Restrictions.eq("mdsDevice", device)).list().get(0)).getMdsDiagnostician();
        MdsDiagnostician sentDiagnostician = ((MdsSentDevices) session.createCriteria(MdsSentDevices.class).add(Restrictions.eq("mdsRepair", repair)).list().get(0)).getMdsDiagnostician();
        Date sentDate = ((MdsSentDevices) session.createCriteria(MdsSentDevices.class).add(Restrictions.eq("mdsRepair", repair)).list().get(0)).getSentDate();
        MdsServiceClaimant claimant = order.getMdsServiceClaimant();

        Object[] data = new Object[17];
        DefaultTableModel listingDataTablemodel;
        listingDataTablemodel = (DefaultTableModel) jTable1.getModel();
        data[0] = claimant.getName();
        data[1] = claimant.getAdress();
        data[2] = claimant.getEmail();
        data[3] = claimant.getPhoneNumber();
        data[4] = device.getMdsDeviceModel().getModel();
        data[5] = device.getMdsDeviceModel().getMdsDeviceVendor().getVendor();
        data[6] = device.getType();
        data[7] = order.getRegistrationDate();
        data[8] = device.getImei();
        data[9] = device.isRepaired();
        data[10] = device.isTested();
        data[11] = order.isDeviceSent();
        data[12] = sentDate;
        data[13] = repair.getRepairCosts();
        data[14] = diagnosis.getSpecification();
        data[15] = repair.getReport();
        data[16] = order.getFaultDescription();
        diagnosisTextArea.setText(diagnosis.getSpecification());
        reportTextArea.setText(repair.getReport());
        faultTextArea.setText(order.getFaultDescription());

        listingDataTablemodel.addRow(data);
        jTable1.setModel(listingDataTablemodel);
        session.getTransaction().commit();
        session.close();
    }

    public ArrayList<Object[]> filterListing(Integer choosing) {
        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();

        ArrayList<Object[]> objectList = new ArrayList<>();

        List<MdsDevice> deviceList = null;

        if (repairedDevicesBox.isSelected()) {
            if (deviceList == null || deviceList.isEmpty()) {
                deviceList = session.createCriteria(MdsDevice.class).add(Restrictions.eq("repaired", true)).list();
            } else {
                deviceList.addAll(session.createCriteria(MdsDevice.class).add(Restrictions.eq("repaired", true)).list());
            }
        }
        if (sentDevicesBox.isSelected()) {
            List<MdsServiceOrder> orderList = session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("deviceSent", true)).list();
            if (deviceList != null) {
                for (MdsServiceOrder temp : orderList) {
                    deviceList.add(temp.getMdsDevice());
                }
            } else {
                if (deviceList == null || deviceList.isEmpty()) {
                    ArrayList<MdsDevice> deviceL = new ArrayList<>();
                    for (MdsServiceOrder temp : orderList) {
                        deviceL.add(temp.getMdsDevice());
                    }
                    if (deviceList == null) {
                        deviceList = (deviceL);
                    } else {
                        deviceList.addAll(deviceL);
                    }
                }
            }
        }
        if (testedDevicesBox.isSelected()) {
            if (deviceList == null || deviceList.isEmpty()) {
                deviceList = session.createCriteria(MdsDevice.class).add(Restrictions.eq("tested", true)).list();
            } else {
                deviceList.addAll(session.createCriteria(MdsDevice.class).add(Restrictions.eq("tested", true)).list());
            }
        }
        if (justRegisteredDevicesBox.isSelected()) {
            if (deviceList == null || deviceList.isEmpty()) {
                deviceList = session.createCriteria(MdsDevice.class).add(Restrictions.eq("tested", false)).add(Restrictions.eq("repaired", false)).list();

            } else {
                deviceList.addAll(session.createCriteria(MdsDevice.class).add(Restrictions.eq("tested", false)).add(Restrictions.eq("repaired", false)).list());
            }
        }
        if (deviceList == null) {
            deviceList = session.createCriteria(MdsDevice.class).list();
        }

        if (choosing.equals(1)) {
            ArrayList<MdsDevice> deviceLL = new ArrayList<>();

            for (MdsDevice device : deviceList) {
                if (device.getImei().equals(jTable1.getValueAt(jTable1.getSelectedRow(), 7))) {
                    deviceLL.add(device);
                    break;
                }
            }
            deviceList = null;
            deviceList = deviceLL;
        }

        for (MdsDevice device : deviceList) {
            MdsServiceOrder order = (MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", device)).list().get(0);
            MdsServiceClaimant claimant = order.getMdsServiceClaimant();
            List<MdsDiagnosis> diagnosisL = session.createCriteria(MdsDiagnosis.class).add(Restrictions.eq("mdsDevice", device)).list();
            MdsDiagnosis diagnosis;
            if (diagnosisL.isEmpty()) {
                diagnosis = null;
            } else {
                diagnosis = diagnosisL.get(0);
            }
            List<MdsRepair> repairL = session.createCriteria(MdsRepair.class).add(Restrictions.eq("mdsDiagnosis", diagnosis)).list();
            MdsRepair repair;
            if (repairL.isEmpty()) {
                repair = null;
            } else {
                repair = repairL.get(0);
            }
            List<MdsSentDevices> sentDeviceL = session.createCriteria(MdsSentDevices.class).add(Restrictions.eq("mdsRepair", repair)).list();
            MdsSentDevices sentDevice;
            if (sentDeviceL.isEmpty()) {
                sentDevice = null;
            } else {
                sentDevice = sentDeviceL.get(0);
            }
            if (choosing.equals(0)) {
                Object[] data = new Object[17];
                data[0] = claimant.getName();
                data[1] = claimant.getAdress();
                data[2] = claimant.getEmail();
                data[3] = claimant.getPhoneNumber();
                data[4] = device.getMdsDeviceModel().getModel();
                data[5] = device.getMdsDeviceModel().getMdsDeviceVendor().getVendor();
                data[6] = device.getType();
                data[7] = device.getImei();
                data[8] = order.getRegistrationDate();
                data[9] = device.isRepaired();
                data[10] = device.isTested();
                data[11] = order.isDeviceSent();
                data[12] = ((sentDevice == null) ? "--" : sentDevice.getSentDate());
                data[13] = ((repair == null) ? "--" : repair.getRepairCosts());
                data[14] = ((diagnosis == null) ? "--" : diagnosis.getSpecification());
                data[15] = ((repair == null) ? "--" : repair.getReport());
                data[16] = order.getFaultDescription();

                objectList.add(data);
            } else {
                Object[] write = new Object[3];
                write[0] = ((diagnosis == null) ? "--" : diagnosis.getSpecification());
                write[1] = ((repair == null) ? "--" : repair.getReport());
                write[2] = order.getFaultDescription();

                objectList.add(write);
            }
        }
        session.getTransaction().commit();
        session.close();
        return objectList;

    }

    public void fillListingTable() {

        ArrayList<Object[]> objectList = filterListing(0);
        DefaultTableModel listingDataTablemodel;

        listingDataTablemodel = (DefaultTableModel) jTable1.getModel();
        listingDataTablemodel.setRowCount(0);
        
        

        for (Object[] data : objectList) {
            listingDataTablemodel.addRow(data);

        }
        jTable1.setModel(listingDataTablemodel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        diagnosisTextArea = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        faultTextArea = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        reportTextArea = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sentDevicesBox = new javax.swing.JCheckBox();
        repairedDevicesBox = new javax.swing.JCheckBox();
        testedDevicesBox = new javax.swing.JCheckBox();
        justRegisteredDevicesBox = new javax.swing.JCheckBox();
        allDevicesBox = new javax.swing.JCheckBox();

        setMinimumSize(new java.awt.Dimension(1100, 600));
        setPreferredSize(new java.awt.Dimension(1100, 600));

        jScrollPane2.setToolTipText("");
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setMinimumSize(new java.awt.Dimension(1100, 309));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1100, 309));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Claimant Name", "Claimant Address", "Claimant email", "Claimant Phone Number", "Device Model", "Device Vendor", "Device Type", "Device IMEI", "Registration Date", "Repaired", "Tested", "Sent", "Sent date", "Repair Costs"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.setDragEnabled(true);
        jTable1.setMaximumSize(new java.awt.Dimension(1700, 280));
        jTable1.setMinimumSize(new java.awt.Dimension(1700, 280));
        jTable1.setPreferredSize(new java.awt.Dimension(1700, 1000));
        jTable1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTable1FocusGained(evt);
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(120);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(1).setMinWidth(120);
            jTable1.getColumnModel().getColumn(1).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(2).setMinWidth(200);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(3).setMinWidth(120);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(120);
            jTable1.getColumnModel().getColumn(4).setMinWidth(100);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(5).setMinWidth(100);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(6).setMinWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
            jTable1.getColumnModel().getColumn(7).setMinWidth(200);
            jTable1.getColumnModel().getColumn(7).setMaxWidth(200);
            jTable1.getColumnModel().getColumn(8).setMinWidth(180);
            jTable1.getColumnModel().getColumn(8).setMaxWidth(180);
            jTable1.getColumnModel().getColumn(9).setMinWidth(60);
            jTable1.getColumnModel().getColumn(9).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(10).setMinWidth(60);
            jTable1.getColumnModel().getColumn(10).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(11).setMinWidth(60);
            jTable1.getColumnModel().getColumn(11).setMaxWidth(60);
            jTable1.getColumnModel().getColumn(12).setMinWidth(180);
            jTable1.getColumnModel().getColumn(12).setMaxWidth(180);
            jTable1.getColumnModel().getColumn(13).setMinWidth(100);
            jTable1.getColumnModel().getColumn(13).setMaxWidth(100);
        }

        diagnosisTextArea.setColumns(20);
        diagnosisTextArea.setRows(5);
        jScrollPane3.setViewportView(diagnosisTextArea);

        faultTextArea.setColumns(20);
        faultTextArea.setRows(5);
        jScrollPane4.setViewportView(faultTextArea);

        reportTextArea.setColumns(20);
        reportTextArea.setRows(5);
        jScrollPane5.setViewportView(reportTextArea);

        jLabel1.setText("Fault");

        jLabel2.setText("Diagnosis");

        jLabel3.setText("Repair report");

        sentDevicesBox.setText("Show sent devices");
        sentDevicesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sentDevicesBoxActionPerformed(evt);
            }
        });

        repairedDevicesBox.setText("Show repaired devices");
        repairedDevicesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repairedDevicesBoxActionPerformed(evt);
            }
        });

        testedDevicesBox.setText("Show tested devices");
        testedDevicesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                testedDevicesBoxActionPerformed(evt);
            }
        });

        justRegisteredDevicesBox.setText("Show just registered devices");
        justRegisteredDevicesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                justRegisteredDevicesBoxActionPerformed(evt);
            }
        });

        allDevicesBox.setText("Show All devices");
        allDevicesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allDevicesBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1080, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(sentDevicesBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(repairedDevicesBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(testedDevicesBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(justRegisteredDevicesBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(allDevicesBox)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sentDevicesBox)
                    .addComponent(repairedDevicesBox)
                    .addComponent(testedDevicesBox)
                    .addComponent(justRegisteredDevicesBox)
                    .addComponent(allDevicesBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        ArrayList<Object[]> objectList = filterListing(1);
        for (Object[] data : objectList) {
            faultTextArea.setText(data[2].toString());
            diagnosisTextArea.setText(data[0].toString());
            reportTextArea.setText(data[1].toString());
        }

    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTable1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1FocusGained

    private void sentDevicesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sentDevicesBoxActionPerformed
        fillListingTable();
    }//GEN-LAST:event_sentDevicesBoxActionPerformed

    private void repairedDevicesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repairedDevicesBoxActionPerformed
        fillListingTable();
    }//GEN-LAST:event_repairedDevicesBoxActionPerformed

    private void testedDevicesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_testedDevicesBoxActionPerformed
        fillListingTable();
    }//GEN-LAST:event_testedDevicesBoxActionPerformed

    private void justRegisteredDevicesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_justRegisteredDevicesBoxActionPerformed
        fillListingTable();
    }//GEN-LAST:event_justRegisteredDevicesBoxActionPerformed

    private void allDevicesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allDevicesBoxActionPerformed
        fillListingTable();
    }//GEN-LAST:event_allDevicesBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allDevicesBox;
    private javax.swing.JTextArea diagnosisTextArea;
    private javax.swing.JTextArea faultTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable jTable1;
    private javax.swing.JCheckBox justRegisteredDevicesBox;
    private javax.swing.JCheckBox repairedDevicesBox;
    private javax.swing.JTextArea reportTextArea;
    private javax.swing.JCheckBox sentDevicesBox;
    private javax.swing.JCheckBox testedDevicesBox;
    // End of variables declaration//GEN-END:variables
}
