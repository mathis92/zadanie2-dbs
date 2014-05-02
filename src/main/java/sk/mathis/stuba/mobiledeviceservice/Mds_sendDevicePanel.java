/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.mobiledeviceservice;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import sk.mathis.stuba.data.Mds_sendDeviceDataCollector;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.equip.Invoice;

public class Mds_sendDevicePanel extends javax.swing.JPanel {

    private Mds_mainGui gui;
    private Mds_sendDeviceDataCollector collector;
    Long id_repair = null;
    Double id_repair_costs = null;
    private Integer deviceSent = 0;

    public Mds_sendDevicePanel(Mds_mainGui gui) {
        initComponents();
        setDiagnosticianComboBox();
        this.gui = gui;
        collector = new Mds_sendDeviceDataCollector(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cancelOperation = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        devicesToSendTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        diagnosticianIdComboBox = new javax.swing.JComboBox();
        chooseDeviceToSend = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        SelectedDeviceTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        reportTextArea = new javax.swing.JTextArea();
        downloadPdfInvoice = new javax.swing.JButton();

        setDoubleBuffered(false);
        setMinimumSize(new java.awt.Dimension(1100, 650));

        jLabel1.setText("Send device Panel");

        cancelOperation.setText("Cancel operation");
        cancelOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOperationActionPerformed(evt);
            }
        });

        devicesToSendTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "IMEI", "Vendor", "Model", "Report"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        devicesToSendTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                devicesToSendTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(devicesToSendTable);

        jLabel2.setText("Repaired devices ready to be send");

        jLabel4.setText("Diagnostician : ");

        diagnosticianIdComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        chooseDeviceToSend.setText("Choose this device to send");
        chooseDeviceToSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDeviceToSendActionPerformed(evt);
            }
        });

        SelectedDeviceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "IMEI", "Vendor", "Model", "Report"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(SelectedDeviceTable);

        reportTextArea.setColumns(20);
        reportTextArea.setRows(5);
        jScrollPane3.setViewportView(reportTextArea);

        downloadPdfInvoice.setText("Download pdf invoice ");
        downloadPdfInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadPdfInvoiceActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(302, 302, 302)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chooseDeviceToSend)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cancelOperation)
                            .addComponent(diagnosticianIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(downloadPdfInvoice))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(cancelOperation))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jLabel2)
                                        .addGap(8, 8, 8))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jLabel1)
                                        .addGap(18, 18, 18)))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(diagnosticianIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chooseDeviceToSend)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(downloadPdfInvoice)
                .addContainerGap(246, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
 public final void setDiagnosticianComboBox() {
        Map<Integer, String> diagnosticianMap = new HashMap<Integer, String>();
        ResultSet rs;
        try {
            rs = DataHelpers.selectFrom("SELECT * FROM `mds_diagnostician`");

            while (rs.next()) {
                diagnosticianMap.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mds_testDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] vendorString = new String[diagnosticianMap.size()];
        for (int i = 1; i <= diagnosticianMap.size(); i++) {
            vendorString[i - 1] = diagnosticianMap.get(i);
        }
        diagnosticianIdComboBox.setModel(new javax.swing.DefaultComboBoxModel(vendorString));
    }
    private void cancelOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOperationActionPerformed
        gui.getjTabbedPane1().remove(gui.getjTabbedPane1().getSelectedIndex());
        gui.getjTabbedPane1().setSelectedIndex(0);
        gui.remove(gui.sendDevicePanel);
        gui.sendDevicePanel = null;
    }//GEN-LAST:event_cancelOperationActionPerformed

    private void devicesToSendTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_devicesToSendTableMouseClicked
        collector.fillSelectedDeviceTable(devicesToSendTable.getSelectedRow());
        ResultSet rs;
        try {
            rs = DataHelpers.selectFrom("SELECT report FROM (SELECT id_device,imei,vendor,model,report FROM (SELECT mds_service_order.device_sent, mds_device.imei,mds_device_vendor.vendor,mds_device_model.model,mds_repair.report,mds_diagnosis.id_device,mds_device.repaired\n"
                    + "	FROM mds_repair\n"
                    + "	JOIN mds_diagnosis\n"
                    + "	 ON mds_repair.id_diagnosis = mds_diagnosis.id_diagnosis\n"
                    + "	JOIN mds_device\n"
                    + "		ON mds_diagnosis.id_device = mds_device.id_device\n"
                    + "	JOIN mds_service_order\n"
                    + "		ON mds_service_order.id_device = mds_device.id_device\n"
                    + "	JOIN mds_device_model\n"
                    + "		ON mds_device.id_device_model = mds_device_model.id_device_model\n"
                    + "	JOIN mds_device_vendor\n"
                    + "	ON mds_device_model.id_device_vendor = mds_device_vendor.id_device_vendor) AS `table1`\n"
                    + "WHERE table1.device_sent = 0) AS `table2` \n"
                    + "WHERE id_device = '" + devicesToSendTable.getValueAt(devicesToSendTable.getSelectedRow(), 0) + "'");
            deviceSent = 1;
            while (rs.next()) {
                reportTextArea.setText(rs.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Mds_repairDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_devicesToSendTableMouseClicked

    private void chooseDeviceToSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDeviceToSendActionPerformed
        if (SelectedDeviceTable.getModel().getRowCount() > 0) {
            ResultSet rs;
            ArrayList<String> sendInfo = new ArrayList<String>();
            ArrayList<String> invoiceData = new ArrayList<String>();
            String idRepairedDevice = (String) SelectedDeviceTable.getModel().getValueAt(0, 0);
            try {
                rs = DataHelpers.selectFrom("SELECT id_repair,repair_costs FROM (SELECT mds_repair.id_repair, mds_repair.repair_costs, mds_diagnosis.id_device\n"
                        + "	FROM mds_repair\n"
                        + "	JOIN mds_diagnosis\n"
                        + "		ON mds_diagnosis.id_diagnosis = mds_repair.id_diagnosis) AS `table1`\n"
                        + "WHERE table1.id_device ='" + idRepairedDevice + "'");

                while (rs.next()) {
                    id_repair = rs.getLong(1);
                    id_repair_costs = rs.getDouble(2);
                }
            } catch (SQLException ex) {
                Logger.getLogger(Mds_repairDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            sendInfo.add(id_repair.toString());
            Integer diagId = diagnosticianIdComboBox.getSelectedIndex() + 1;
            sendInfo.add(diagId.toString());
            DataHelpers.insertFromArray(sendInfo, "mds_sent_devices", DataHelpers.mds_sent_devices);
            System.out.println(idRepairedDevice);
            DataHelpers.updateRow("UPDATE mds_service_order SET mds_service_order.device_sent = 1 WHERE mds_service_order.id_device ='" + idRepairedDevice + "'");
            invoiceData.add(id_repair_costs.toString());
            invoiceData.add(Integer.toString(diagnosticianIdComboBox.getSelectedIndex() + 1));
            invoiceData.add(id_repair.toString());
            gui.refreshListingPanel();
            try {
                gui.updateOrderCount();
            } catch (SQLException ex) {
                Logger.getLogger(Mds_sendDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            DataHelpers.insertFromArray(invoiceData, "mds_invoice", DataHelpers.mds_invoice);
            chooseDeviceToSend.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "You have to choose device to send !", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_chooseDeviceToSendActionPerformed

    private void downloadPdfInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadPdfInvoiceActionPerformed
        if (deviceSent.equals(1)) {
            Invoice inv = new Invoice();
            try {
                inv.createPdf((Integer) id_repair.intValue());
            } catch (DocumentException ex) {
                Logger.getLogger(Mds_sendDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Mds_sendDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Mds_sendDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Mds_sendDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
          
            gui.getjTabbedPane1().remove(gui.getjTabbedPane1().getSelectedIndex());
            gui.getjTabbedPane1().setSelectedIndex(0);
            gui.remove(gui.sendDevicePanel);
            gui.sendDevicePanel = null;
        } else {
            JOptionPane.showMessageDialog(this, "You have to choose device to send !", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_downloadPdfInvoiceActionPerformed

    public JTable getDevicesToSendTable() {
        return devicesToSendTable;
    }

    public JTable getSelectedDeviceTable() {
        return SelectedDeviceTable;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable SelectedDeviceTable;
    private javax.swing.JButton cancelOperation;
    private javax.swing.JButton chooseDeviceToSend;
    private javax.swing.JTable devicesToSendTable;
    private javax.swing.JComboBox diagnosticianIdComboBox;
    private javax.swing.JButton downloadPdfInvoice;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea reportTextArea;
    // End of variables declaration//GEN-END:variables
}
