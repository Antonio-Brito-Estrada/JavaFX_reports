package mx.com.lobos.reporteaccesos.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;
import mx.com.lobos.reporteaccesos.App;
import mx.com.lobos.reporteaccesos.models.EmpleadosFx;
import mx.com.lobos.reporteaccesos.models.EmpleadosViewModel;
import mx.com.lobos.reporteaccesos.singletonUtils.ACCF001ViewCmbEmpleado;
import mx.com.lobos.reporteaccesos.singletonUtils.SingletonParams;
import mx.com.lobos.reporteaccesos.tasks.ProcesTaskCmbEmployeesService;
import mx.com.lobos.reporteaccesos.tasks.ProcesTaskService;

public class ACCF001View implements FxmlView<EmpleadosViewModel>,Initializable {

    @FXML DatePicker dpFechaInicio;
    @FXML DatePicker dpFechaFin;
    @FXML ComboBox cmbEmpleados2;
    @FXML TextField txtNombre;
    @FXML Pane ACCF001content;
    @FXML VBox ACCF001ProgressPane;
    @FXML VBox ACCF000ProgressPane;
    boolean van = false;

    private static ACCF001View _instance;
    ProgressIndicator p = new ProgressIndicator();

    public void comboAction(){
        EmpleadosFx empleadosFx;

        if(cmbEmpleados2.getValue() != null){
            empleadosFx = (EmpleadosFx) cmbEmpleados2.getItems().get(cmbEmpleados2.getSelectionModel().getSelectedIndex());
            txtNombre.setText(empleadosFx.getNombre() + " " + empleadosFx.getApellidos());
            cmbEmpleados2.setValue(empleadosFx.getCveEmpleado());
        }
    }

    public Window getMainWindow() {
        return ACCF001content.getScene().getWindow();
    }

    public static ACCF001View instance() {
        return _instance;
    }

    public void llenaDatePickers(){
        LocalDate localDate = LocalDate.now();
        dpFechaInicio.setValue(localDate.minusDays(15));
        dpFechaFin.setValue(localDate);
    }
    
    public void homeButton(ActionEvent e) throws IOException {
        App.setRoot("ACCF000View");
    }

    public void limpiarButton (ActionEvent e) throws IOException {
        LocalDate localDate = LocalDate.now();
        dpFechaInicio.setValue(localDate.minusDays(15));
        dpFechaFin.setValue(localDate);
        txtNombre.setText("");
        cmbEmpleados2.setValue(null);
    }

    public void exportarButton(ActionEvent e) throws IOException {
        HashMap<String, String> parametrosHsm = new HashMap<>();
        EmpleadosFx empleadosFx = new EmpleadosFx();
        Integer cveEmpleado = cmbEmpleados2.getSelectionModel().getSelectedIndex();

        if(cveEmpleado >= 0){
             empleadosFx = (EmpleadosFx) cmbEmpleados2.getItems().get(cveEmpleado);
        }
        
        parametrosHsm.put("cveEmpleado", empleadosFx.getCveEmpleado().isEmpty() ? "%": empleadosFx.getCveEmpleado());
        parametrosHsm.put("fechaInicio", dpFechaInicio.getValue().toString());
        parametrosHsm.put("fechaFin", dpFechaFin.getValue().toString());

        try {

            //FileChooser fc = new FileChooser();
            DirectoryChooser dirChooser = new DirectoryChooser();
            File fileSelect = dirChooser.showDialog(ACCF001View.instance().getMainWindow());
            String SEPARATOR = System.getProperty("file.separator");
            String ruta = fileSelect.getAbsolutePath().concat(SEPARATOR);
            parametrosHsm.put("rutaArchivo", ruta);

            SingletonParams params = SingletonParams.getInstance();
            params.setParametrosHsm(parametrosHsm);
            
            ProcesTaskService tarea = new ProcesTaskService();
            
            /*Region veil = new Region();
            veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4)");
            veil.setPrefSize(240, 240);*/
//            ProgressIndicator p = new ProgressIndicator();
            p.setPrefSize(100, 100);
            p.progressProperty().bind(tarea.progressProperty());
            //veil.visibleProperty().bind(tarea.runningProperty());
            p.visibleProperty().bind(tarea.runningProperty());
            //veil.setCenterShape(true);
            p.setPadding(new Insets(10, 10, 10, 10));
            p.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);"
            + " -fx-background-radius: 10");
            if(!van){
               ACCF001ProgressPane.getChildren().add(p);
            }
            van = true;
            tarea.start();
            
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        _instance = this;

        llenaDatePickers();
                
        ProcesTaskCmbEmployeesService tareaCmbEmpleado = new ProcesTaskCmbEmployeesService();
        ProgressIndicator procesoInicio = new ProgressIndicator();
        procesoInicio.setPrefSize(100, 100);
        procesoInicio.progressProperty().bind(tareaCmbEmpleado.progressProperty());
        procesoInicio.visibleProperty().bind(tareaCmbEmpleado.runningProperty());
        procesoInicio.setPadding(new Insets(10, 10, 10, 10));
        procesoInicio.setStyle("-fx-background-color: rgba(0, 0, 0, 0.4);"
        + " -fx-background-radius: 10");

        ACCF001ProgressPane.getChildren().add(procesoInicio);
        
        ACCF001ViewCmbEmpleado datosEmpleado = ACCF001ViewCmbEmpleado.getInstance();
        datosEmpleado.setCmbEmpleados2(cmbEmpleados2);
        tareaCmbEmpleado.start();
    }
}