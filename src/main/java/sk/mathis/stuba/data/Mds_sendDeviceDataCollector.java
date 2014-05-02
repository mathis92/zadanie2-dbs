package sk.mathis.stuba.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import sk.mathis.stuba.equip.DataHelpers;
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
        try {

            ResultSet rs = DataHelpers.selectFrom("SELECT id_device,imei,vendor,model,report FROM (SELECT mds_service_order.device_sent, mds_device.imei,mds_device_vendor.vendor,mds_device_model.model,mds_repair.report,mds_diagnosis.id_device,mds_device.repaired\n"
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
                    + "WHERE table1.device_sent = 0");
            while (rs.next()) {
                for (int i = 0; i < 5; i++) {

                    data[i] = rs.getString(i + 1);
                }
                devicesToSendTable.addRow(data);
                sendPanel.getDevicesToSendTable().setModel(devicesToSendTable);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Mds_findSpecificDeviceDataCollector.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillSelectedDeviceTable(Integer selectedRow) {
        Object[] data = new Object[5];
        DefaultTableModel selectedDevice;

        selectedDevice = (DefaultTableModel) sendPanel.getSelectedDeviceTable().getModel();
        try {
            ResultSet rs = DataHelpers.selectFrom("SELECT id_device,imei,vendor,model,report FROM (SELECT mds_service_order.device_sent, mds_device.imei,mds_device_vendor.vendor,mds_device_model.model,mds_repair.report,mds_diagnosis.id_device,mds_device.repaired\n"
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
                    + "WHERE table1.device_sent = 0;");
            int j = 0;
            while (rs.next()) {
                if (selectedRow.equals(j)) {
                    selectedDevice.setRowCount(0);
                    for (int i = 0; i < 5; i++) {

                        data[i] = rs.getString(i + 1);
                    }
                    selectedDevice.addRow(data);
                    sendPanel.getSelectedDeviceTable().setModel(selectedDevice);
                }
                j++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Mds_findSpecificDeviceDataCollector.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
