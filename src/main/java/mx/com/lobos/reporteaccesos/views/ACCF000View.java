package mx.com.lobos.reporteaccesos.views;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import mx.com.lobos.reporteaccesos.App;

public class ACCF000View {

    @FXML
    public void onClickEvent(MouseEvent event) throws IOException {    
        
            App.setRoot("ACCF001View");

    }
    
}
