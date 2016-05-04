package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> boxStart;

    @FXML
    private ComboBox<?> boxDest;

    @FXML
    private Button btnDistance;

    @FXML
    private TextArea txtResult;

    @FXML
    void doDistance(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxStart != null : "fx:id=\"boxStart\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert boxDest != null : "fx:id=\"boxDest\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnDistance != null : "fx:id=\"btnDistance\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }
}
