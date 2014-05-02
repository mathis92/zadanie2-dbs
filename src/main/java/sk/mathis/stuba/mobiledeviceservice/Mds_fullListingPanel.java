package sk.mathis.stuba.mobiledeviceservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import sk.mathis.stuba.equip.DataHelpers;

public class Mds_fullListingPanel extends javax.swing.JPanel {

    private Mds_mainGui gui;

    public Mds_fullListingPanel(Mds_mainGui gui) {
        this.gui = gui;
        initComponents();
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    public void fillData(Integer deviceId) {
        ResultSet rs;
        try {
            rs = DataHelpers.selectFrom("SELECT name, adress, email,phone_number, model, vendor, `type`,registration_date,imei,repaired,tested,device_sent, sent_date, repair_costs,specification,report,fault_description  FROM (SELECT mds_service_claimant.name,mds_service_claimant.adress,mds_service_claimant.email,mds_service_claimant.phone_number,mds_service_order.registration_date,mds_service_order.fault_description,mds_service_order.device_sent,mds_device.tested,mds_device.repaired,mds_device.`type`,mds_device.id_device ,mds_device.imei,mds_device_model.model,mds_device_vendor.vendor,mds_diagnosis.specification,mds_repair.report,mds_repair.repair_costs,mds_sent_devices.sent_date\n"
                    + "FROM mds_service_claimant\n"
                    + "LEFT JOIN mds_service_order\n"
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
                    + "		LEFT JOIN mds_sent_devices\n"
                    + "		ON mds_repair.id_repair = mds_sent_devices.id_repair) AS `table1` WHERE table1.id_device ='" + deviceId + "'");

            Object[] data = new Object[17];
            DefaultTableModel listingDataTablemodel;

            listingDataTablemodel = (DefaultTableModel) jTable1.getModel();

            while (rs.next()) {
                for (int i = 0; i < 17; i++) {

                    if (i == 14) {
                        DiagnosisTextArea.setText(rs.getString(i + 1));
                    } else if (i == 15) {
                        reportTextArea.setText(rs.getString(i + 1));
                    } else if (i == 16) {
                        faultTextArea.setText(rs.getString(i + 1));
                    } else {
                        data[i] = ((rs.getString(i + 1) == null) ? "--" : rs.getString(i + 1));
                    }
                }
                listingDataTablemodel.addRow(data);
                jTable1.setModel(listingDataTablemodel);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Mds_fullListingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet filterListing(Integer choosing) throws SQLException {
        ResultSet rs = null;
        ArrayList<String> filterList = new ArrayList<String>();

        if (repairedDevicesBox.isSelected()) {
            if (!filterList.isEmpty()) {
                filterList.add(" AND ");
            }
            filterList.add(" table1.repaired = 1 ");
        }
        if (sentDevicesBox.isSelected()) {
            if (!filterList.isEmpty()) {
                filterList.add(" AND ");
            }
            filterList.add(" table1.device_sent = 1 ");
        }
        if (testedDevicesBox.isSelected()) {
            if (!filterList.isEmpty()) {
                filterList.add(" AND ");
            }
            filterList.add(" table1.tested = 1 ");
        }
        if (justRegisteredDevicesBox.isSelected()) {
            if (!filterList.isEmpty()) {
                filterList.add(" AND ");
            }
            filterList.add(" table1.tested = 0 AND table1.device_sent = 0 AND table1.repaired = 0 ");
        }
        /*
         if (allDevicesBox.isSelected()) {
         if(!filterList.isEmpty()){
         filterList.clear();
         }
         filterList.add("");
         }
         */
        String filterLine = null;
        if (choosing.equals(0)) {
            if (!filterList.isEmpty()) {
                filterLine = "WHERE";
            } else {
                filterLine = "";
            }
        }
        if (filterList.isEmpty()) {
            filterLine = "";
        } else {
            if (choosing.equals(1)) {
                filterList.add(" AND ");
            }
        }

        for (String temp : filterList) {
            if (filterLine == null) {
                filterLine = temp;
            } else {
                filterLine += temp;
            }
        }

        try {
            if (choosing.equals(0)) {
                rs = DataHelpers.selectFrom("SELECT name, adress, email,phone_number, model, vendor, `type`,imei,registration_date,repaired,tested,device_sent, sent_date, repair_costs  FROM (SELECT mds_service_claimant.name,mds_service_claimant.adress,mds_service_claimant.email,mds_service_claimant.phone_number,mds_service_order.registration_date,mds_service_order.fault_description,mds_service_order.device_sent,mds_device.tested,mds_device.repaired,mds_device.`type`,mds_device.id_device ,mds_device.imei,mds_device_model.model,mds_device_vendor.vendor,mds_diagnosis.specification,mds_repair.report,mds_repair.repair_costs,mds_sent_devices.sent_date\n"
                        + "FROM mds_service_claimant\n"
                        + "LEFT JOIN mds_service_order\n"
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
                        + "		LEFT JOIN mds_sent_devices\n"
                        + "		ON mds_repair.id_repair = mds_sent_devices.id_repair ORDER BY id_device ASC) AS `table1`" + filterLine);

            } else {
                rs = DataHelpers.selectFrom("SELECT specification,report,fault_description  FROM (SELECT mds_service_claimant.name,mds_service_claimant.adress,mds_service_claimant.email,mds_service_claimant.phone_number,mds_service_order.registration_date,mds_service_order.fault_description,mds_service_order.device_sent,mds_device.tested,mds_device.repaired,mds_device.`type`,mds_device.id_device ,mds_device.imei,mds_device_model.model,mds_device_vendor.vendor,mds_diagnosis.specification,mds_repair.report,mds_repair.repair_costs,mds_sent_devices.sent_date\n"
                        + "FROM mds_service_claimant\n"
                        + "LEFT JOIN mds_service_order\n"
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
                        + "		LEFT JOIN mds_sent_devices\n"
                        + "		ON mds_repair.id_repair = mds_sent_devices.id_repair ORDER BY id_device ASC) AS `table1` WHERE " + filterLine + " table1.imei ='" + jTable1.getValueAt(jTable1.getSelectedRow(), 7) + "'");
            }
            System.out.println(choosing + " + " + filterLine);
        } catch (SQLException ex) {
            Logger.getLogger(Mds_fullListingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;

    }

    public void fillListingTable() {
        ResultSet rs;
        try {
            /*rs = DataHelpers.selectFrom("SELECT name, adress, email,phone_number, model, vendor, `type`,registration_date,imei,repaired,tested,device_sent, sent_date, repair_costs  FROM (SELECT mds_service_claimant.name,mds_service_claimant.adress,mds_service_claimant.email,mds_service_claimant.phone_number,mds_service_order.registration_date,mds_service_order.fault_description,mds_service_order.device_sent,mds_device.tested,mds_device.repaired,mds_device.`type`,mds_device.id_device ,mds_device.imei,mds_device_model.model,mds_device_vendor.vendor,mds_diagnosis.specification,mds_repair.report,mds_repair.repair_costs,mds_sent_devices.sent_date\n"
             + "FROM mds_service_claimant\n"
             + "LEFT JOIN mds_service_order\n"
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
             + "		LEFT JOIN mds_sent_devices\n"
             + "		ON mds_repair.id_repair = mds_sent_devices.id_repair ORDER BY id_device ASC) AS `table1`");
             */
            rs = filterListing(0);
            Object[] data = new Object[14];
            DefaultTableModel listingDataTablemodel;

            listingDataTablemodel = (DefaultTableModel) jTable1.getModel();
            listingDataTablemodel.setRowCount(0);
            while (rs.next()) {
                for (int i = 0; i < 14; i++) {
                    data[i] = ((rs.getString(i + 1) == null) ? "--" : rs.getString(i + 1));
                }
                listingDataTablemodel.addRow(data);
                jTable1.setModel(listingDataTablemodel);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Mds_fullListingPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        DiagnosisTextArea = new javax.swing.JTextArea();
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
        jScrollPane2.setMinimumSize(new java.awt.Dimension(1100, 309));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(1100, 309));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Claimant Name", "Claimant Address", "Claimant email", "Claimant Phone Number", "Device Model", "Device Vendor", "Device Type", "Device IMEI", "Registration Date", "Repaired", "Tested", "Sent", "Sent date", "Repair Costs"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setDragEnabled(true);
        jTable1.setMaximumSize(new java.awt.Dimension(1700, 280));
        jTable1.setMinimumSize(new java.awt.Dimension(1700, 280));
        jTable1.setPreferredSize(new java.awt.Dimension(1700, 280));
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

        DiagnosisTextArea.setColumns(20);
        DiagnosisTextArea.setRows(5);
        jScrollPane3.setViewportView(DiagnosisTextArea);

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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        ResultSet rs;
        try {
            /*  rs = DataHelpers.selectFrom("SELECT specification,report,fault_description  FROM (SELECT mds_service_claimant.name,mds_service_claimant.adress,mds_service_claimant.email,mds_service_claimant.phone_number,mds_service_order.registration_date,mds_service_order.fault_description,mds_service_order.device_sent,mds_device.tested,mds_device.repaired,mds_device.`type`,mds_device.id_device ,mds_device.imei,mds_device_model.model,mds_device_vendor.vendor,mds_diagnosis.specification,mds_repair.report,mds_repair.repair_costs,mds_sent_devices.sent_date\n"
             + "FROM mds_service_claimant\n"
             + "LEFT JOIN mds_service_order\n"
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
             + "		LEFT JOIN mds_sent_devices\n"
             + "		ON mds_repair.id_repair = mds_sent_devices.id_repair ORDER BY id_device ASC) AS `table1` WHERE table1.imei ='" + jTable1.getValueAt(jTable1.getSelectedRow(), 8) + "'");
             */
            rs = filterListing(1);
            while (rs.next()) {
                System.out.println(rs.getString(1));
                faultTextArea.setText(rs.getString(3));
                DiagnosisTextArea.setText(rs.getString(1));
                reportTextArea.setText(rs.getString(2));
            }

        } catch (SQLException ex) {
            Logger.getLogger(Mds_fullListingPanel.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JTextArea DiagnosisTextArea;
    private javax.swing.JCheckBox allDevicesBox;
    private javax.swing.JTextArea faultTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
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
