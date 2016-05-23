package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.MetroDeParisModel;
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
    
    @FXML
    private Button btnCycle;

    @FXML
    void doCycle(ActionEvent event) {
    	
    }
    
    public void setModel(MetroDeParisModel model){
    	
    	this.model = model;
		
		try {
			model.creaGrafo();	
			List<Fermata> stazioni = model.getStazioni();
			boxStart.getItems().addAll(stazioni);
			boxDest.getItems().addAll(stazioni);
		} 
		catch (RuntimeException e) {
			txtResult.setText(e.getMessage());
		}
	}

    @FXML
    public void doDistance(ActionEvent event) {
    	Fermata stazioneDiPartenza = boxStart.getValue();
		Fermata stazioneDiArrivo = boxDest.getValue();

		if (stazioneDiPartenza != null && stazioneDiArrivo != null) {
			if (!stazioneDiPartenza.equals(stazioneDiArrivo)) {
				try {
					// Calcolo il percorso tra le due stazioni
					model.calcolaPercorso(stazioneDiPartenza, stazioneDiArrivo);
					// Ottengo il tempo di percorrenza
					int tempoTotaleInSecondi = (int) model.getPercorsoTempoTotale();
					int ore = tempoTotaleInSecondi / 3600;
					int minuti = (tempoTotaleInSecondi % 3600) / 60;
					int secondi = tempoTotaleInSecondi % 60;
					String timeString = String.format("%02d:%02d:%02d", ore, minuti, secondi);
					StringBuilder risultato = new StringBuilder();
					// Ottengo il percorso
					risultato.append(model.getPercorsoEdgeList());
					risultato.append("\n\nTempo di percorrenza stimato: " + timeString + "\n");
					// Aggiorno la TextArea
					txtResult.setText(risultato.toString());
				} catch (RuntimeException e) {
					txtResult.setText(e.getMessage());
				}
			} else {
				txtResult.setText("Inserire una stazione di arrivo diversa da quella di partenza.");
			}			
		} else {			
			txtResult.setText("Inserire una stazione di arrivo ed una di partenza.");
		}
    }

    @FXML
    void initialize() {
        assert boxStart != null : "fx:id=\"boxStart\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert boxDest != null : "fx:id=\"boxDest\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnDistance != null : "fx:id=\"btnDistance\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnCycle != null : "fx:id=\"btnCycle\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }
}
