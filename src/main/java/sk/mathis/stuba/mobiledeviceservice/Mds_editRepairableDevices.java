/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.mobiledeviceservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import sk.mathis.stuba.device.ComboBoxItem;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.hibernatemapper.MdsDeviceModel;
import sk.mathis.stuba.hibernatemapper.MdsDeviceVendor;
import sk.mathis.stuba.hibernatemapper.MdsDiagnostician;

/**
 *
 * @author Mathis
 */
public class Mds_editRepairableDevices extends javax.swing.JPanel {

    private Mds_mainGui gui;

    public Mds_editRepairableDevices(Mds_mainGui gui) {
        initComponents();
        this.gui = gui;
        setComboBox();
    }

    public void setComboBox() {
        Session session = DataHelpers.sessionFactory.openSession();
        session.beginTransaction();
        List<MdsDeviceVendor> vendorList = session.createCriteria(MdsDeviceVendor.class).list();
        session.getTransaction().commit();
        session.close();
        vendorComboBox.removeAllItems();
        for(MdsDeviceVendor temp : vendorList){
            vendorComboBox.addItem(temp.getVendor());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        addVendorField = new javax.swing.JFormattedTextField();
        jLabel2 = new javax.swing.JLabel();
        vendorComboBox = new javax.swing.JComboBox();
        addModelField = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        addDiagnosticianField = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        cancelOperation = new javax.swing.JButton();
        modelButton = new javax.swing.JButton();
        vendorButton = new javax.swing.JButton();
        diagButton = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(1100, 650));

        jLabel1.setText("Add Vendor");

        jLabel2.setText("Add Model ");

        vendorComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Add Diagnostician ");

        jLabel4.setText("Add Models , Vendors , Diagnosticians");

        cancelOperation.setText("Cancel operation");
        cancelOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOperationActionPerformed(evt);
            }
        });

        modelButton.setText("Add Model");
        modelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelButtonActionPerformed(evt);
            }
        });

        vendorButton.setText("Add Vendor");
        vendorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vendorButtonActionPerformed(evt);
            }
        });

        diagButton.setText("Add Diagnostican");
        diagButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diagButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(vendorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel3)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(addVendorField, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(addModelField)
                            .addComponent(addDiagnosticianField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(vendorButton)
                            .addComponent(modelButton)
                            .addComponent(diagButton))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(397, 397, 397)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 398, Short.MAX_VALUE)
                        .addComponent(cancelOperation)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cancelOperation))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(addVendorField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vendorButton))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(vendorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addModelField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modelButton))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(addDiagnosticianField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(diagButton, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(493, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cancelOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOperationActionPerformed
        gui.getjTabbedPane1().remove(gui.getjTabbedPane1().getSelectedIndex());
        gui.getjTabbedPane1().setSelectedIndex(0);
        gui.remove(gui.editRepairableDevices);
        gui.editRepairableDevices = null;
    }//GEN-LAST:event_cancelOperationActionPerformed

    private void vendorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vendorButtonActionPerformed
        if (!addVendorField.getText().equalsIgnoreCase("")) {
            MdsDeviceVendor vendor = new MdsDeviceVendor();
           
            vendor.setVendor(addVendorField.getText());
            Session session = DataHelpers.sessionFactory.openSession();
            session.beginTransaction();
            session.save(vendor);
            session.getTransaction().commit();
            session.close();
            addVendorField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Field must be filled", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_vendorButtonActionPerformed

    private void modelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelButtonActionPerformed
        if (!addModelField.getText().equalsIgnoreCase("")) {
            Session session = DataHelpers.sessionFactory.openSession();
            session.beginTransaction();
            MdsDeviceModel model = new MdsDeviceModel();
            model.setModel(addModelField.getText());
            MdsDeviceVendor vendor = (MdsDeviceVendor)session.get(MdsDeviceVendor.class, vendorComboBox.getSelectedIndex() + 1);
            model.setMdsDeviceVendor(vendor);
            System.out.println(model.getModel() + " " + model.getMdsDeviceVendor().getVendor());
            session.save(model);
            session.getTransaction().commit();
            session.close();
            addModelField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Field must be filled", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_modelButtonActionPerformed

    private void diagButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_diagButtonActionPerformed
        if(!addDiagnosticianField.getText().equalsIgnoreCase("")){
            MdsDiagnostician diagnostician = new MdsDiagnostician();
           
            diagnostician.setName(addDiagnosticianField.getText());
            Session session = DataHelpers.sessionFactory.openSession();
            session.beginTransaction();
            session.save(diagnostician);
            session.getTransaction().commit();
            session.close();
            
            addDiagnosticianField.setText("");
        }else { 
             JOptionPane.showMessageDialog(this, "Field must be filled", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_diagButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField addDiagnosticianField;
    private javax.swing.JFormattedTextField addModelField;
    private javax.swing.JFormattedTextField addVendorField;
    private javax.swing.JButton cancelOperation;
    private javax.swing.JButton diagButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton modelButton;
    private javax.swing.JButton vendorButton;
    private javax.swing.JComboBox vendorComboBox;
    // End of variables declaration//GEN-END:variables
}
