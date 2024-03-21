package mx.com.lobos.reporteaccesos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import de.saxsys.mvvmfx.guice.MvvmfxGuiceApplication;

/**
 * JavaFX App
 */
public class App extends MvvmfxGuiceApplication {

    private static Scene scene;

    @Override
    public void startMvvmfx(Stage stage) throws IOException {
        scene = new Scene(loadFXML("ACCF000View"), 1020, 600);
        stage.setTitle("FRESAM-Reportes de asistencia");
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void stopMvvmfx() throws Exception {
        System.exit(1);
    }

}