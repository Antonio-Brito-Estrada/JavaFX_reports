package mx.com.lobos.reporteaccesos.tasks;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

public class ProcesTaskService extends Service {

    @Override
    protected Task createTask() {
        return new ProcesTask();
    }

    @Override
    protected void succeeded() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Mensaje");
        a.setHeaderText(null);
        a.setContentText("Proceso realizado correctamente");
        a.showAndWait();
    }

    @Override
    protected void failed() {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Mensaje");
        a.setHeaderText(null);
        a.setContentText("Error al generar el excel");
        a.showAndWait();
    } 


}
