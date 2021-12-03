package br.unisul.library;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class SampleController {
	
	@FXML TabPane pane;
	
	@FXML
	public void abreCadastroCliente() {
		try {
			boolean aberta = false;
			for (Tab tb : pane.getTabs()) {
				if(tb.getText().equals("Cadastro de Clientes"))
					aberta = true;
			}
			if(!aberta) {
				Tab tab = new Tab("Cadastro de Clientes");
				tab.setClosable(true);
				pane.getTabs().add(tab);
				tab.setContent((Node) FXMLLoader.load(getClass().getResource("Cliente.fxml")));
				pane.getSelectionModel().select(tab);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
