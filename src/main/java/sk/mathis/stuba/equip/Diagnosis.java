/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sk.mathis.stuba.equip;

/**
 *
 * @author Mathis
 */
public class Diagnosis {

    private Integer deviceId;
    private String specification;
    private Integer diagnosticianId;
    
    public Diagnosis(Integer deviceId, String specification, Integer diagnosticianId){
        this.deviceId = deviceId;
        this.specification = specification;
        this.diagnosticianId = diagnosticianId;
    }
    
    public void addDiagnosis(String specification){
        this.specification += " \n" + specification;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public Integer getDiagnosticianId() {
        return diagnosticianId;
    }

    public String getSpecification() {
        return specification;
    }
    
}
