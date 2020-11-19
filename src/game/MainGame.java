package game;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import views.MainController;

public class MainGame {
	private GraphicsContext gc;
	private int gap = 2; // 吏�猶곗갼湲� 釉붾윮怨� 釉붾윮�궗�씠�쓽 嫄곕━ 5�뵿��
	private int size = 30; // 吏�猶곗갼湲� 釉붾윮�쓽 �겕湲� 媛�濡쒖꽭濡� 25�뵿��
	public Canvas canvas;
	private static int[][] board;
	public int turn = 1;
	private static MainController mc;
	private static int x_len = 16;
	private static int y_len = 16;
	private static boolean isStart = false;
	private Ai ai;

	public MainGame(Canvas canvas, MainController mc) {
		if (isStart) {
			return;
		}
		isStart = true;
		this.canvas = canvas;
		this.mc = mc;
		this.gc = this.canvas.getGraphicsContext2D();
		; // canvas 媛��졇�삤湲�
		board = new int[x_len][y_len]; // �뙋 諛곗뿴 �젣�옉
		for (int i = 0; i < y_len; i++) { // 諛곗뿴 湲곕낯媛�
			for (int j = 0; j < x_len; j++) {
				board[i][j] = 0;
			}
		}
		render();
		ai = new Ai();
	}
	
	public boolean out() {
		for (int i = 0; i < y_len; i++) { // 諛곗뿴 湲곕낯媛�
			for (int j = 0; j < x_len; j++) {
				board[i][j] = 0;
			}
		}
		render();
		if(!isStart) {
			return true;
		}else {
			return false;			
		}
	}
	
	public void mouseMove(MouseEvent e) {
		if (!isStart) {
			return;
		}
		double x = e.getX();
		double y = e.getY();
		double xx = x % 32;
		double yy = y % 32;

		int i;
		int j;
		if (xx > 16) {
			i = (int) (x / 32);
		} else {
			i = (int) (x / 32) - 1;
		}
		if (yy > 16) {
			j = (int) (y / 32);
		} else {
			j = (int) (y / 32) - 1;
		}

		render();
		int xxx = 32 + j * (30 + 2);
		int yyy = 32 + i * (30 + 2);
		gc.strokeRect(yyy - 7, xxx - 7, 16, 16);
	}

	public void mouseClick(MouseEvent e) {
		if (!isStart) {
			return;
		}
		if (turn == 2) {
			return;
		}
		double x = e.getX();
		double y = e.getY();
		double xx = x % 32;
		double yy = y % 32;

		int i;
		int j;
		if (xx > 16) {
			i = (int) (x / 32);
		} else {
			i = (int) (x / 32) - 1;
		}
		if (yy > 16) {
			j = (int) (y / 32);
		} else {
			j = (int) (y / 32) - 1;
		}
		if (i >= x_len || j >= y_len||i <0 || j <0) {
			return;
		}

		
		if (board[j][i] == 0) {
			if (turn == 1) {
				this.board[j][i] = turn;
				turn = 2;
				mc.second();
				check(turn);
				render();
				if (!isStart) {
					return;
				}
				String aa = ai.Aicheck(board);
				String[] tokens = aa.split(":");
				String aaa = tokens[0];
				int jj = Integer.parseInt(aaa);
				String bbb = tokens[1];
				int ii = Integer.parseInt(bbb);				
				this.board[jj][ii] = turn;
				turn = 1;	
				mc.first();
				check(turn);
			}

			render();
		}

	}

	public static void check(int turn) {
		if (turn == 1) {
			turn = 2;
		} else {
			turn = 1;
		}
		for (int i = 0; i < x_len; i++) {
			for (int j = 0; j < y_len; j++) {
				if (board[j][i] == turn) {
					find(turn, j, i);
				}
			}
		}
	}

	public static void find(int turn, int y, int x) {
//		 ㅡ
		int isFive = 0;
		for (int i = -2; i < 3; i++) {
			if (x + i >= 0 && x + i < x_len) {
				if (board[y][x + i] == turn) {
					isFive++;
					if (isFive == 5) {
						System.out.println(x + " " + y + "좌표로 부터 생김-");
						System.out.println("5목 완성");
						end(turn);
						break;
					}
				} else {
					isFive = 0;
				}
			}
		}
//		|
		isFive = 0;
		for (int i = -2; i < 3; i++) {
			if (y + i >= 0 && y + i < y_len) {

				if (board[y + i][x] == turn) {
					isFive++;
					if (isFive == 5) {
						System.out.println(x + " " + y + "좌표로 부터 생김|");
						System.out.println("5목 완성");
						end(turn);
						break;
					}
				} else {
					isFive = 0;
				}
			}
		}
//		\
		isFive = 0;
		for (int i = -2; i < 3; i++) {

			if (x + i >= 0 && x + i < x_len && y + i >= 0 && y + i < y_len) {
				if (board[y + i][x + i] == turn) {
					isFive++;
					if (isFive == 5) {
						System.out.println(x + " " + y + "좌표로 부터 생김 왼 -> 아래");
						System.out.println("5목 완성");
						end(turn);
						break;
					}
				} else {
					isFive = 0;
				}
			}
		}
//		/
		isFive = 0;
		for (int i = -2; i < 3; i++) {
			if (x + i >= 0 && x + i < x_len && y - i >= 0 && y - i < y_len) {
				if (board[y - i][x + i] == turn) {
					isFive++;
					if (isFive == 5) {
						System.out.println(x + " " + y + "좌표로 부터 생김/");
						System.out.println("5목 완성");
						end(turn);
						break;
					}
				} else {
					isFive = 0;
				}
			}
		}
	}

	public static void end(int turn) {
		mc.end(turn);

		isStart = false;
	}

	public void render() {
		gc.clearRect(0, 0, 546, 546);
		gc.setLineWidth(2);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		gc.setFill(Color.rgb(220, 179, 92));
		gc.fillRect(0, 0, 546, 546);
		gc.setFill(Color.rgb(0, 0, 0));
		gc.strokeRect(0, 0, 546, 546);
		for (int i = 0; i < 18; i++) {
			int y = (size + gap) * i;
			int x = 0;
			gc.strokeLine(0, y + 1, 546, y + 1);
		}
		for (int i = 0; i < 18; i++) {
			int y = 0;
			int x = (size + gap) * i;
			gc.strokeLine(x + 1, 0, x + 1, 546);
		}

		for (int i = 0; i < y_len; i++) { // it is
			for (int j = 0; j < x_len; j++) {
				int x = 32 + j * (30 + 2);
				int y = 32 + i * (30 + 2);
				if (board[i][j] == 1) {
					gc.fillOval(x - 7, y - 7, 16, 16);
				} else if (board[i][j] == 2) {
					gc.setFill(Color.rgb(255, 255, 255));
					gc.fillOval(x - 7, y - 7, 16, 16);
					gc.setFill(Color.rgb(0, 0, 0));
				}
			}
		}

	}

}
