/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.mobiledeviceservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import org.hibernate.Session;
import sk.mathis.stuba.data.Mds_testDeviceDataCollector;
import sk.mathis.stuba.device.DeviceModel;
import sk.mathis.stuba.equip.DataHelpers;
import sk.mathis.stuba.equip.TestTypes;
import sk.mathis.stuba.hibernatemapper.MdsDevice;
import sk.mathis.stuba.hibernatemapper.MdsDiagnostician;
import sk.mathis.stuba.hibernatemapper.MdsTesting;

/**
 *
 * @author Mathis
 */
public class Mds_testDevicePanel extends javax.swing.JPanel {

    private Mds_testDeviceDataCollector collector;
    private ArrayList<JFormattedTextField> testResults = new ArrayList<JFormattedTextField>();
    private ArrayList<TestTypes> testTypesList = new ArrayList<TestTypes>();
    private MdsTesting testing = null;
    private Integer idTestedDevice = null;
    private Integer selectedDeviceToTest = 0;
    private Mds_mainGui gui;

    public Mds_testDevicePanel(Mds_mainGui gui) {
        initComponents();
        setDiagnosticianComboBox();
        this.gui = gui;
        collector = new Mds_testDeviceDataCollector(this);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        devicesToTestTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        SelectedDeviceTable = new javax.swing.JTable();
        displayTest = new javax.swing.JCheckBox();
        cameraTest = new javax.swing.JCheckBox();
        memoryTest = new javax.swing.JCheckBox();
        speakerTest = new javax.swing.JCheckBox();
        batteryTest = new javax.swing.JCheckBox();
        chargingTest = new javax.swing.JCheckBox();
        sensorTest = new javax.swing.JCheckBox();
        stabilityTest = new javax.swing.JCheckBox();
        displayTestField = new javax.swing.JFormattedTextField();
        cameraTestField = new javax.swing.JFormattedTextField();
        memoryTestField = new javax.swing.JFormattedTextField();
        speakerTestField = new javax.swing.JFormattedTextField();
        batteryTestField = new javax.swing.JFormattedTextField();
        chargingTestField = new javax.swing.JFormattedTextField();
        sensorsTestField = new javax.swing.JFormattedTextField();
        stabilityTestField = new javax.swing.JFormattedTextField();
        submitTestsButton = new javax.swing.JButton();
        chooseDeviceToTest = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cancelOperation = new javax.swing.JButton();
        diagnosticianIdComboBox = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        faultTextArea = new javax.swing.JTextArea();

        setEnabled(false);
        setMinimumSize(new java.awt.Dimension(1100, 650));

        jLabel1.setText("Test Device");

        jLabel2.setText("Devices To test");

        devicesToTestTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "IMEI", "Vendor", "Model", "Fault"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        devicesToTestTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                devicesToTestTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(devicesToTestTable);
        if (devicesToTestTable.getColumnModel().getColumnCount() > 0) {
            devicesToTestTable.getColumnModel().getColumn(0).setResizable(false);
            devicesToTestTable.getColumnModel().getColumn(1).setResizable(false);
            devicesToTestTable.getColumnModel().getColumn(2).setResizable(false);
            devicesToTestTable.getColumnModel().getColumn(3).setResizable(false);
            devicesToTestTable.getColumnModel().getColumn(4).setResizable(false);
        }

        SelectedDeviceTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "IMEI", "Vendor", "Model", "Fault"
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
        if (SelectedDeviceTable.getColumnModel().getColumnCount() > 0) {
            SelectedDeviceTable.getColumnModel().getColumn(0).setResizable(false);
            SelectedDeviceTable.getColumnModel().getColumn(1).setResizable(false);
            SelectedDeviceTable.getColumnModel().getColumn(2).setResizable(false);
            SelectedDeviceTable.getColumnModel().getColumn(3).setResizable(false);
            SelectedDeviceTable.getColumnModel().getColumn(4).setResizable(false);
        }

        displayTest.setText("Display test");
        displayTest.setEnabled(false);

        cameraTest.setText("Camera test");
        cameraTest.setEnabled(false);

        memoryTest.setText("Memory test");
        memoryTest.setEnabled(false);
        memoryTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memoryTestActionPerformed(evt);
            }
        });

        speakerTest.setText("Speaker test");
        speakerTest.setEnabled(false);

        batteryTest.setText("Battery test");
        batteryTest.setEnabled(false);

        chargingTest.setText("Charging test");
        chargingTest.setEnabled(false);

        sensorTest.setText("Sensors test");
        sensorTest.setEnabled(false);

        stabilityTest.setText("Stability test");
        stabilityTest.setEnabled(false);

        displayTestField.setEnabled(false);

        cameraTestField.setEnabled(false);
        cameraTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cameraTestFieldActionPerformed(evt);
            }
        });

        memoryTestField.setEnabled(false);
        memoryTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memoryTestFieldActionPerformed(evt);
            }
        });

        speakerTestField.setEnabled(false);
        speakerTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                speakerTestFieldActionPerformed(evt);
            }
        });

        batteryTestField.setEnabled(false);
        batteryTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batteryTestFieldActionPerformed(evt);
            }
        });

        chargingTestField.setEnabled(false);
        chargingTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chargingTestFieldActionPerformed(evt);
            }
        });

        sensorsTestField.setEnabled(false);
        sensorsTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sensorsTestFieldActionPerformed(evt);
            }
        });

        stabilityTestField.setEnabled(false);
        stabilityTestField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stabilityTestFieldActionPerformed(evt);
            }
        });

        submitTestsButton.setText("Submit tests");
        submitTestsButton.setEnabled(false);
        submitTestsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitTestsButtonActionPerformed(evt);
            }
        });

        chooseDeviceToTest.setText("Choose this device to test");
        chooseDeviceToTest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDeviceToTestActionPerformed(evt);
            }
        });

        jLabel3.setText("Diagnostician id");

        cancelOperation.setText("Cancel operation");
        cancelOperation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelOperationActionPerformed(evt);
            }
        });

        diagnosticianIdComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane3.setMinimumSize(new java.awt.Dimension(200, 15));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(200, 15));

        faultTextArea.setColumns(20);
        faultTextArea.setRows(5);
        faultTextArea.setMinimumSize(new java.awt.Dimension(200, 15));
        jScrollPane3.setViewportView(faultTextArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(425, 425, 425)
                                .addComponent(cancelOperation))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(memoryTest)
                                .addGap(99, 99, 99)
                                .addComponent(memoryTestField, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stabilityTest)
                                .addGap(99, 99, 99)
                                .addComponent(stabilityTestField, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cameraTest)
                                    .addComponent(displayTest))
                                .addGap(99, 99, 99)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(displayTestField, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                                    .addComponent(cameraTestField, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel3)
                                        .addGap(18, 18, 18)
                                        .addComponent(diagnosticianIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(75, 75, 75)
                                        .addComponent(chooseDeviceToTest)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chargingTest)
                                    .addComponent(batteryTest)
                                    .addComponent(speakerTest)
                                    .addComponent(sensorTest))
                                .addGap(93, 93, 93)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(speakerTestField, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                                    .addComponent(batteryTestField, javax.swing.GroupLayout.DEFAULT_SIZE, 896, Short.MAX_VALUE)
                                    .addComponent(chargingTestField, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(sensorsTestField, javax.swing.GroupLayout.Alignment.TRAILING)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(480, 480, 480)
                                .addComponent(submitTestsButton)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 738, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelOperation)
                    .addComponent(jLabel1))
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(diagnosticianIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(171, 171, 171)
                        .addComponent(chooseDeviceToTest))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(displayTest)
                    .addComponent(displayTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cameraTest)
                    .addComponent(cameraTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(memoryTest)
                    .addComponent(memoryTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(speakerTest)
                    .addComponent(speakerTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(batteryTest)
                    .addComponent(batteryTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chargingTest)
                    .addComponent(chargingTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sensorTest)
                    .addComponent(sensorsTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stabilityTest)
                    .addComponent(stabilityTestField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(submitTestsButton)
                .addGap(28, 28, 28))
        );

        displayTest.getAccessibleContext().setAccessibleName("");
        cameraTest.getAccessibleContext().setAccessibleName("");
        memoryTest.getAccessibleContext().setAccessibleName("");
        speakerTest.getAccessibleContext().setAccessibleName("");
        batteryTest.getAccessibleContext().setAccessibleName("");
        chargingTest.getAccessibleContext().setAccessibleName("");
        sensorTest.getAccessibleContext().setAccessibleName("");
        stabilityTest.getAccessibleContext().setAccessibleName("");
        displayTestField.getAccessibleContext().setAccessibleName("");
        cameraTestField.getAccessibleContext().setAccessibleName("");
        memoryTestField.getAccessibleContext().setAccessibleName("");
        speakerTestField.getAccessibleContext().setAccessibleName("");
        batteryTestField.getAccessibleContext().setAccessibleName("");
        chargingTestField.getAccessibleContext().setAccessibleName("");
        sensorsTestField.getAccessibleContext().setAccessibleName("");
        stabilityTestField.getAccessibleContext().setAccessibleName("");

        getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    public void setDiagnosticianComboBox() {
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


    private void devicesToTestTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_devicesToTestTableMouseClicked
        collector.fillSelectedDeviceTable(devicesToTestTable.getSelectedRow());

    }//GEN-LAST:event_devicesToTestTableMouseClicked

    private void memoryTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memoryTestActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memoryTestActionPerformed

    private void memoryTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memoryTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_memoryTestFieldActionPerformed

    private void speakerTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_speakerTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_speakerTestFieldActionPerformed

    private void batteryTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batteryTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_batteryTestFieldActionPerformed

    private void chargingTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chargingTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chargingTestFieldActionPerformed

    private void sensorsTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sensorsTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sensorsTestFieldActionPerformed

    private void stabilityTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stabilityTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stabilityTestFieldActionPerformed

    private void cameraTestFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cameraTestFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cameraTestFieldActionPerformed

    private void submitTestsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitTestsButtonActionPerformed

        if (batteryTest.isSelected()) {
            testTypesList.add(TestTypes.BATTERY);
            if (!batteryTestField.getText().equalsIgnoreCase("")) {
                testResults.add(batteryTestField);
            }
        }
        if (cameraTest.isSelected()) {
            testTypesList.add(TestTypes.CAMERA);
            if (!cameraTestField.getText().equalsIgnoreCase("")) {
                testResults.add(cameraTestField);
            }
        }
        if (chargingTest.isSelected()) {
            testTypesList.add(TestTypes.CHARGING);
            if (!chargingTestField.getText().equalsIgnoreCase("")) {
                testResults.add(chargingTestField);
            }
        }
        if (displayTest.isSelected()) {
            testTypesList.add(TestTypes.DISPLAY);
            if (!displayTestField.getText().equalsIgnoreCase("")) {
                testResults.add(displayTestField);
            }
        }
        if (memoryTest.isSelected()) {
            testTypesList.add(TestTypes.MEMORY);
            if (!memoryTestField.getText().equalsIgnoreCase("")) {
                testResults.add(memoryTestField);
            }
        }
        if (sensorTest.isSelected()) {
            testTypesList.add(TestTypes.SENSORS);
            if (!sensorsTestField.getText().equalsIgnoreCase("")) {
                testResults.add(sensorsTestField);
            }
        }
        if (stabilityTest.isSelected()) {
            testTypesList.add(TestTypes.STABILITY);
            if (!speakerTestField.getText().equalsIgnoreCase("")) {
                testResults.add(stabilityTestField);
            }
        }
        if (speakerTest.isSelected()) {
            testTypesList.add(TestTypes.SPEAKER);
            if (!speakerTestField.getText().equalsIgnoreCase("")) {
                testResults.add(speakerTestField);
            }
        }
        System.out.println(testTypesList);
        if (!testResults.isEmpty() && !testTypesList.isEmpty()) {
            collector.fillTests(testTypesList, testResults, testing);
            Session session = DataHelpers.sessionFactory.openSession();
            session.beginTransaction();

            MdsDevice device = (MdsDevice) session.get(MdsDevice.class, idTestedDevice);
            device.setTested(true);
            session.save(device);
            testing.setEndTime(new Timestamp(new Date().getTime()));
            session.update(testing);
            session.getTransaction().commit();
            session.close();

            collector.fillDiagnosisTable(device);
            gui.getjTabbedPane1().remove(gui.getjTabbedPane1().getSelectedIndex());
            gui.getjTabbedPane1().setSelectedIndex(0);
            gui.remove(gui.testDevicePanel);
            gui.refreshListingPanel();
            gui.testDevicePanel = null;
        } else {
            testResults.clear();
            testTypesList.clear();
            JOptionPane.showMessageDialog(this, "You have to do some tests on the device!", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_submitTestsButtonActionPerformed

    private void chooseDeviceToTestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDeviceToTestActionPerformed
        if (SelectedDeviceTable.getModel().getRowCount() > 0) {
            Session session = DataHelpers.sessionFactory.openSession();
            session.beginTransaction();
            Integer diagId = diagnosticianIdComboBox.getSelectedIndex() + 1;
            idTestedDevice = (Integer)SelectedDeviceTable.getModel().getValueAt(0, 0);
            MdsDevice device = (MdsDevice) session.get(MdsDevice.class, idTestedDevice);
            MdsDiagnostician diagnostician = (MdsDiagnostician) session.get(MdsDiagnostician.class, diagId);
            MdsTesting mdsTesting = new MdsTesting();
            mdsTesting.setMdsDevice(device);
            mdsTesting.setMdsDiagnostician(diagnostician);
            mdsTesting.setStartTime(new Timestamp(new Date().getTime()));
            session.save(mdsTesting);
            session.getTransaction().commit();
            session.refresh(mdsTesting);
            testing = mdsTesting;
            session.close();
            selectedDeviceToTest = 1;
            chooseDeviceToTest.setEnabled(false);
            displayTest.setEnabled(true);
            cameraTest.setEnabled(true);
            memoryTest.setEnabled(true);
            speakerTest.setEnabled(true);
            batteryTest.setEnabled(true);
            chargingTest.setEnabled(true);
            sensorTest.setEnabled(true);
            stabilityTest.setEnabled(true);
            displayTestField.setEnabled(true);
            cameraTestField.setEnabled(true);
            memoryTestField.setEnabled(true);
            speakerTestField.setEnabled(true);
            batteryTestField.setEnabled(true);
            chargingTestField.setEnabled(true);
            sensorsTestField.setEnabled(true);
            stabilityTestField.setEnabled(true);
            submitTestsButton.setEnabled(true);

        } else {
            JOptionPane.showMessageDialog(this, "You have to choose a device to be tested !", "Notification !!!!", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_chooseDeviceToTestActionPerformed

    private void cancelOperationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelOperationActionPerformed
        if (selectedDeviceToTest.equals(1)) {
            try {
                DataHelpers.deleteRow("DELETE FROM mds_testing WHERE id_testing = " + testing);
            } catch (SQLException ex) {
                Logger.getLogger(Mds_testDevicePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        gui.getjTabbedPane1().remove(gui.getjTabbedPane1().getSelectedIndex());
        gui.getjTabbedPane1().setSelectedIndex(0);
        gui.remove(gui.testDevicePanel);
        gui.testDevicePanel = null;
    }//GEN-LAST:event_cancelOperationActionPerformed

    public JTable getDevicesToTestTable() {
        return devicesToTestTable;
    }

    public JTable getSelectedDeviceTable() {
        return SelectedDeviceTable;
    }

    public JTextArea getFaultTextArea() {
        return faultTextArea;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable SelectedDeviceTable;
    private javax.swing.JCheckBox batteryTest;
    private javax.swing.JFormattedTextField batteryTestField;
    private javax.swing.JCheckBox cameraTest;
    private javax.swing.JFormattedTextField cameraTestField;
    private javax.swing.JButton cancelOperation;
    private javax.swing.JCheckBox chargingTest;
    private javax.swing.JFormattedTextField chargingTestField;
    private javax.swing.JButton chooseDeviceToTest;
    private javax.swing.JTable devicesToTestTable;
    private javax.swing.JComboBox diagnosticianIdComboBox;
    private javax.swing.JCheckBox displayTest;
    private javax.swing.JFormattedTextField displayTestField;
    private javax.swing.JTextArea faultTextArea;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JCheckBox memoryTest;
    private javax.swing.JFormattedTextField memoryTestField;
    private javax.swing.JCheckBox sensorTest;
    private javax.swing.JFormattedTextField sensorsTestField;
    private javax.swing.JCheckBox speakerTest;
    private javax.swing.JFormattedTextField speakerTestField;
    private javax.swing.JCheckBox stabilityTest;
    private javax.swing.JFormattedTextField stabilityTestField;
    private javax.swing.JButton submitTestsButton;
    // End of variables declaration//GEN-END:variables
}
