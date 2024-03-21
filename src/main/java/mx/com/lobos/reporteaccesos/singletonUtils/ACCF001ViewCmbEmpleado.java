/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.lobos.reporteaccesos.singletonUtils;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import lombok.Getter;
import lombok.Setter;
import mx.com.lobos.reporteaccesos.models.EmpleadosFx;

/**
 *
 * @author antoniob
 */
@Getter
@Setter
public class ACCF001ViewCmbEmpleado {
    
    private ObservableList<EmpleadosFx> cmbEmpleados;
    private ComboBox cmbEmpleados2;
    
    private static class ACCF001ViewCmbEmpleadoHolder {
        public static ACCF001ViewCmbEmpleado instance = new ACCF001ViewCmbEmpleado();
    }
     
    private ACCF001ViewCmbEmpleado() {}
    
    public static ACCF001ViewCmbEmpleado getInstance() {
        return ACCF001ViewCmbEmpleadoHolder.instance;
    }
    
}
