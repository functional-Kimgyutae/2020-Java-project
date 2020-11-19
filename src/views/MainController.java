package views;

import application.Main;
import game.MainGame;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class MainController extends MasterController {
	private MainGame game;
	@FXML
	private Canvas canvas;
	@FXML 
	private Label user1;
	@FXML 
	private Label user2;	
	@FXML 
	private Label trun;
	
	@FXML 
	private Label winning;
	
	static boolean start = false;
	public String name = "";
	public int win = 0;
	public int lose = 0;
	public void startRoom() {
		init();
		trun.setText("컴퓨터와의 대전");
	}
	
	public void startBtn() {
		start = true;
		if(start) {
			System.out.println("start");
			game =	new MainGame(canvas, this);
			trun.setText(name+"'s turn");	
			start = true;
		}
	}
	public void init() {
		name = Main.app.player.name();
		win = Main.app.player.win();
		lose = Main.app.player.lose();
		user1.setText(name);
		setScore();
	}
	public void first() {
		trun.setText(Main.app.player.name() + "'s turn");		
	}
	
	public void second() {
		trun.setText("com's turn");
	}
	
	public void end(int turn) {
		if(turn == 1) {
			trun.setText( name+"의 승리입니다.");
			Main.app.player.wining();
		}else {
			trun.setText("com의 승리입니다.");
			Main.app.player.loseing();
		}
		Main.app.player.update();
		init();
	}
	
	public void clickHandler(MouseEvent e) {
		if(game == null) return;
		game.mouseClick(e);
	}
	
	
	public void MouseMove(MouseEvent e) {
		if(game == null) return;
		game.mouseMove(e);
	}
	
	public void out() {
		if(start) {
			if(!game.out()) {
				Main.app.player.loseing();
				Main.app.player.update();
			}
		}
		Main.app.slideOut(getRoot());
		Main.app.loadPane("login");
	}
	public void setScore() {
		winning.setText("전적 :"+this.win+ "승"+this.lose+"패");
	}
	
}
