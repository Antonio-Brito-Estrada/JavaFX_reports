/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.com.lobos.reporteaccesos.tasks;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

/**
 *
 * @author antoniob
 */
public class ProcesTaskCmbEmployeesService extends Service {
    
    @Override
    protected Task createTask() {
        return new ProcesTaskCmbEmployees();
    }

    @Override
    protected void failed() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Mensaje");
        a.setHeaderText(null);
        a.setContentText("Error al cargar el combo de empleados");
        a.showAndWait();
    } 
    
}
