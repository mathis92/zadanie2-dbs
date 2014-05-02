/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk.mathis.stuba.data;

import sk.mathis.stuba.equip.DataHelpers;
import java.util.ArrayList;

/**
 *
 * @author Mathis
 */
public class Mds_registerDeviceDataCollector {

    public ArrayList<String> dataList;

    public Mds_registerDeviceDataCollector(ArrayList<String> list) {
        this.dataList = list;

    }

    public Long executeInsertClaimantData() {
        System.out.println(dataList);
        Long idClaimant;
        idClaimant = DataHelpers.insertFromArray(dataList, "mds_service_claimant", DataHelpers.mds_service_claimant);
        return idClaimant;
    }

    public Long executeInsertDeviceData() {
        System.out.println(dataList);
        Long idDevice;
        idDevice = DataHelpers.insertFromArray(dataList, "mds_device", DataHelpers.mds_device);
        return idDevice;
    }
    
    public void executeInsertOrderData(){
        System.out.println(dataList);
        DataHelpers.insertFromArray(dataList, "mds_service_order", DataHelpers.mds_service_order);
    }
}
