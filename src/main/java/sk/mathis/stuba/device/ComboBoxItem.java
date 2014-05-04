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

    private String name;
    private Integer id;

    public ComboBoxItem(String name, Integer id) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;

    }
}
