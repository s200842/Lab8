package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.MetroDeParisModel;
import javafx.concurrent.Task;
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
    private ComboBox<Fermata> boxStart;

    @FXML
    private ComboBox<Fermata> boxDest;

    @FXML
    private Button btnDistance;

    @FXML
    private TextArea txtResult;
    
    private MetroDeParisModel model;
    
    public void setModel(MetroDeParisModel model){
    	this.model = model;
    	boxStart.getItems().addAll(model.getFermate());
    	boxDest.getItems().addAll(model.getFermate());
    	Task<Void> task = new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				model.generateGraph();
				return null;
			}
    	};
    	
    	Thread th = new Thread(task);
    	th.setDaemon(true);
    	th.start();
    }

    @FXML
    public void doDistance(ActionEvent event) {
    	List<Fermata> result = model.calcolaPercorso(boxStart.getValue(), boxDest.getValue());
    	txtResult.setText("Percorso: "+result+"\n\nTempo di percorrenza stimato: "+model.tempoPercorso(result));
    	
    }

    @FXML
    void initialize() {
        assert boxStart != null : "fx:id=\"boxStart\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert boxDest != null : "fx:id=\"boxDest\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnDistance != null : "fx:id=\"btnDistance\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }
}
