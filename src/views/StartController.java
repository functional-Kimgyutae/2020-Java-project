package views;

import java.net.InetAddress;
import java.net.UnknownHostException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class StartController extends MasterController{
	
	@FXML
	private AnchorPane Stl;
	
	public void start() {
		Main.app.slideOut(Stl);
		Main.app.loadPane("login");
	}
}

