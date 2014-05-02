/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mathis.stuba.device;

import sk.mathis.stuba.hibernatemapper.MdsDeviceVendor;

/**
 *
 * @author Mathis
 */
public class ComboBoxItem {

    private MdsDeviceVendor obj;
    public ComboBoxItem(MdsDeviceVendor obj) {
    this.obj = obj;
    }
    
    
    @Override
    public String toString(){
        
        return obj.getVendor();
        
    }
}
