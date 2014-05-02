/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mathis.stuba.device;

import sk.mathis.stuba.hibernatemapper.MdsDeviceModel;
import sk.mathis.stuba.hibernatemapper.MdsDeviceVendor;

/**
 *
 * @author Mathis
 */
public class DeviceModel {
private final String modelName;
private final MdsDeviceVendor modelVendor;
private final Integer modelId;

    public DeviceModel(MdsDeviceModel model) {
        this.modelName = model.getModel();
        this.modelVendor = model.getMdsDeviceVendor();
        this.modelId = model.getIdDeviceModel();
    }



    public String getModelName() {
        return modelName;
    }

    public Integer getModelId() {
        return modelId;
    }

    public MdsDeviceVendor getModelVendor() {
        return modelVendor;
    }


     
    
}
