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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import sk.mathis.stuba.data.Mds_sendDeviceDataCollector;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.equip.Invoice;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnosis;
import sk.mathis.stuba.hibernatemapper.MdsDiagnostician;
import sk.mathis.stuba.hibernatemapper.MdsInvoice;
import sk.mathis.stuba.hibernatemapper.MdsRepair;
import sk.mathis.stuba.hibernatemapper.MdsSentDevices;
import sk.mathis.stuba.hibernatemapper.MdsServiceOrder;


public class Mds_sendDevicePanel extends javax.swing.JPanel {

    private Mds_mainGui gui;
    private Mds_sendDeviceDataCollector collector;
    Long id_repair = null;
    Double id_repair_costs = null;
    private Integer deviceSent = 0;
    private MdsRepair repair;

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

        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        List<MdsDiagnostician> mdsDiagnosticians = session.createCriteria(MdsDiagnostician.class).list();
        session.getTransaction().commit();
        session.close();
        for (MdsDiagnostician temp : mdsDiagnosticians) {
            diagnosticianMap.put(temp.getIdDiagnostician(), temp.getName());
        }

        String[] diagnosticianString = new String[diagnosticianMap.size()];
        for (int i = 1; i <= diagnosticianMap.size(); i++) {
            diagnosticianString[i - 1] = diagnosticianMap.get(i);
        }
        diagnosticianIdComboBox.setModel(new javax.swing.DefaultComboBoxModel(diagnosticianString));
    }
    private void cancelOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOperationActionPerformed
        gui.getjTabbedPane1().remove(gui.getjTabbedPane1().getSelectedIndex());
        gui.getjTabbedPane1().setSelectedIndex(0);
        gui.remove(gui.sendDevicePanel);
        gui.sendDevicePanel = null;
    }//GEN-LAST:event_cancelOperationActionPerformed

    private void devicesToSendTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_devicesToSendTableMouseClicked
        collector.fillSelectedDeviceTable(devicesToSendTable.getSelectedRow());

    }//GEN-LAST:event_devicesToSendTableMouseClicked

    private void chooseDeviceToSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDeviceToSendActionPerformed
        if (SelectedDeviceTable.getModel().getRowCount() > 0) {
            Integer idRepairedDevice = (Integer) SelectedDeviceTable.getModel().getValueAt(0, 0);
            Session session = DataHelpers.sessionFactory.openSession();
            session.beginTransaction();
            MdsDevice device = (MdsDevice) session.get(MdsDevice.class, idRepairedDevice);
            for(MdsDiagnosis diagnosis : (Set<MdsDiagnosis>)device.getMdsDiagnosises()){
                if(diagnosis.getMdsDevice().equals(device)){
                    for(MdsRepair temp : (Set<MdsRepair>)diagnosis.getMdsRepairs()){
                        if(temp.getMdsDiagnosis().equals(diagnosis)){
                            repair = temp;
                        }
                    }
                }
            }
            MdsSentDevices sentDevice = new MdsSentDevices();
            sentDevice.setMdsDiagnostician((MdsDiagnostician) session.get(MdsDiagnostician.class, (Integer) diagnosticianIdComboBox.getSelectedIndex() + 1));
            System.out.println(repair.getReport());
            sentDevice.setMdsRepair(repair);
            sentDevice.setSentDate(new Timestamp(new Date().getTime()));
            session.save(sentDevice);
            MdsServiceOrder order = (MdsServiceOrder) session.createCriteria(MdsServiceOrder.class).add(Restrictions.eq("mdsDevice", device)).list().get(0);
            order.setDeviceSent(true);
            session.update(order);
            MdsInvoice invoice = new MdsInvoice();
            invoice.setPrice(repair.getRepairCosts());
            invoice.setMdsDiagnostician((MdsDiagnostician) session.get(MdsDiagnostician.class, (Integer) diagnosticianIdComboBox.getSelectedIndex() + 1));
            invoice.setMdsRepair(repair);
            invoice.setDate(new Timestamp(new Date().getTime()));
            session.save(invoice);
            session.getTransaction().commit();
            session.close();
            deviceSent = 1;
            gui.refreshListingPanel();
            gui.updateOrderCount();
            chooseDeviceToSend.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(this, "You have to choose device to send !", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_chooseDeviceToSendActionPerformed

    private void downloadPdfInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadPdfInvoiceActionPerformed
        if (deviceSent.equals(1)) {
            Invoice inv = new Invoice();
            try {
                inv.createPdf(repair);
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

    public JTextArea getReportTextArea() {
        return reportTextArea;
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
