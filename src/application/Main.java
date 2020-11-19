package application;
	
import java.util.HashMap;
import java.util.Map;

import com.mysql.cj.xdevapi.Client;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import object.Player;
import views.LoginController;
import views.MainController;
import views.MasterController;
import views.RegisterController;
import views.StartController;



public class Main extends Application {
	public static Main app;
	
	private StackPane skp;
	
	private Map<String, MasterController> controllerMap = new HashMap<>();
	
	public Player player = null;

	@Override
	public void start(Stage primaryStage) {
		app = this;
		try {
			FXMLLoader mainloader = new FXMLLoader(); // fx 연결
			mainloader.setLocation(getClass().getResource("/views/AllInOne.fxml"));
			skp = mainloader.load();
			

			FXMLLoader Startloader = new FXMLLoader(); // fx 연결
			Startloader.setLocation(getClass().getResource("/views/StartLayout.fxml"));
			AnchorPane stl = Startloader.load();
			
			
			StartController sc = Startloader.getController();
			sc.setRoot(stl);
			controllerMap.put("start", sc);
			
			FXMLLoader Loginloader = new FXMLLoader(); // fx 연결
			Loginloader.setLocation(getClass().getResource("/views/LoginLayout.fxml"));
			AnchorPane lgl = Loginloader.load();
			
			
			LoginController lg = Loginloader.getController();
			lg.setRoot(lgl);
			controllerMap.put("login", lg);
			
			FXMLLoader Registerloader = new FXMLLoader(); // fx 연결
			Registerloader.setLocation(getClass().getResource("/views/RegisterLayout.fxml"));
			AnchorPane rgl = Registerloader.load();
			
			
			RegisterController rg = Registerloader.getController();
			rg.setRoot(rgl);
			controllerMap.put("Register", rg);
			
			
			FXMLLoader GameLoader = new FXMLLoader(); // fx 연결
			GameLoader.setLocation(getClass().getResource("/views/MainLayout.fxml"));
			AnchorPane gml = GameLoader.load();
			
			MainController gm = GameLoader.getController();
			gm.setRoot(gml);
			controllerMap.put("Main", gm);
			
			
			
			Scene scene = new Scene(skp);
			skp.getChildren().add(stl);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void loadPane(String name) {
		MasterController c = controllerMap.get(name);
		Pane pane = c.getRoot();
		skp.getChildren().add(pane);
		pane.setTranslateX(-800);
		pane.setOpacity(0);
		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(),0);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(),1);
		KeyFrame keyframe = new KeyFrame(Duration.millis(500),toRight ,fadeOut);
		timeline.getKeyFrames().add(keyframe);
		timeline.play();
	}
	
	public void slideOut(Pane pane) {
		Timeline timeline = new Timeline();
		KeyValue toRight = new KeyValue(pane.translateXProperty(),800);
		KeyValue fadeOut = new KeyValue(pane.opacityProperty(),0);
		KeyFrame keyframe = new KeyFrame(Duration.millis(500), (e) -> {
			skp.getChildren().remove(pane);
		},toRight ,fadeOut);
		timeline.getKeyFrames().add(keyframe);
		timeline.play();
	}
	
	public MasterController getController( String name ) {
		return this.controllerMap.get(name);
	}
	
}
