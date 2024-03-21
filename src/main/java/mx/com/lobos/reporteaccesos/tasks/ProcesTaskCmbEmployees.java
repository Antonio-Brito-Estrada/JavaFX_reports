/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.lobos.reporteaccesos.tasks;

import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import mx.com.lobos.reporteaccesos.accesos.service.accesosservice.accesosService;
import mx.com.lobos.reporteaccesos.models.EmpleadosFx;
import mx.com.lobos.reporteaccesos.singletonUtils.ACCF001ViewCmbEmpleado;
import mx.com.lobos.reporteaccesos.singletonUtils.SingletonParams;
import mx.com.lobos.reporteaccesos.views.ACCF000View;
import mx.com.lobos.reporteaccesos.views.ACCF001View;
import org.apache.poi.ss.formula.functions.Even;

/**
 *
 * @author antoniob
 */
public class ProcesTaskCmbEmployees extends Task<String> {

    @Override
    protected String call() throws Exception {

        java.lang.String mensaje = "";
        
        Thread.sleep(1505);
        updateProgress(0, 500);

        try {

            Thread.sleep(1505);
            updateProgress(150, 500);
            
            ACCF001ViewCmbEmpleado datosEmpleado = ACCF001ViewCmbEmpleado.getInstance();
            datosEmpleado.getCmbEmpleados2().itemsProperty().set(EmpleadosFx.getList());
            
            updateProgress(350, 500);
            Thread.sleep(1505);
            updateProgress(500, 500);

            mensaje = "Proceso realizado correctamente";

        } catch (Exception e) {
            System.out.println(e.getMessage());
            mensaje = "Error al consultar empleados";
        }
        return mensaje;
    }

}
