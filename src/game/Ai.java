package game;

import java.util.Random;

public class Ai {
	private int[][] board = new int[16][16];
	private int[][] attck = new int[16][16];
	
	public String Aicheck(int[][] board) {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				this.board[i][j] = board[i][j];
				this.attck[i][j] = board[i][j];
			}
		}
		
		String aa = attck();
		return aa;
	}
	
	public String attck() {
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				if(this.board[j][i] == 1) {
					find(1,j,i);
				}else if(this.board[j][i] == 2) {
					attcking(2,j,i);					
				}else if(this.board[j][i] == 0) {
					emptyattck(j, i);
				}
			}
		}
		String aa = getxy();
		return aa;
	}

	public String getxy() {
		int cnt = 0;
		int high = 0;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				if(high > attck[j][i]) {
					high = attck[j][i];
					cnt = 1;
				}else if(high == attck[j][i]) {
					cnt++;
				}
			}
		}
		Random ran = new Random();
		int a = ran.nextInt(cnt);
		System.out.println("최고로 높은점수"+high);
		
		a++;
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				if(high == attck[j][i]) {
					a--;
					if(a == 0) {
						System.out.println("공격 좌표"+ j+ " "+ i);
						return j+":"+i; 
					}
				}
			}
		}
		return "";
	}
 
	public void find(int turn,int y ,int x) {
//		1개짜리 구하기
		for (int i = x-1; i < x+2; i++) {
			for (int j = y-1; j < y+2; j++) {
				if(i >= 0 && i < this.board.length &&j >= 0 && j < this.board.length ) {
					if(this.attck[j][i] != 1 && this.attck[j][i] != 2) {
						if(turn == 1) {
							this.attck[j][i] -= 1; 
						}else if(turn == 2){
							this.attck[j][i] += 1;
						}	
					}
				}
			}
		}
		if(turn == 2) {
			return;
		}
//		 ㅡ
		int block = 0;		
		int empty = 0;
		int blockLine = 0;
		int emptyLine = 0;
		int blockCnt = 0;
		int emptyCnt = 0;
		for (int i = -2; i < 3; i++) {
			if(x +i >= 0 && x+i < this.board.length) {
				if(board[y][x+i] == 1 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y][x+i] == 0){
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length) {
						if(board[y][x+i] != 1 &&  board[y][x+i] != 2 &&attck[y][x+i] != 1 && attck[y][x+i] !=2) {
							attck[y][x+i] -= 1000;
							System.out.println("- : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}else if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 2) {
				// 중요도 -50
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length ) {
						if(board[y][x+i] != 1 &&  board[y][x+i] != 2 &&attck[y][x+i] != 1 && attck[y][x+i] !=2 ) {
							System.out.println("- : 3 ["+y+"] ["+x+"]");
							attck[y][x+i] -= 50;
						}
					}
				}
			}
		}
		
//		|
		block = 0;		
		empty = 0;
		blockLine = 0;
		emptyLine = 0;
		blockCnt = 0;
		emptyCnt = 0;
		
		for (int i = -2; i < 3; i++) {
			if(y +i >= 0 && y+i < this.board.length) {
				if(board[y+i][x] == 1 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y+i][x] == 0) {
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(y +i >= 0 && y+i < this.board.length) {
						if(board[y+i][x] != 1 && board[y+i][x] != 2 &&attck[y+i][x] != 1 && attck[y+i][x] !=2) {
							attck[y+i][x] -= 1000;
							System.out.println("| : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}else if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 2) {
				// 중요도 -50
				for (int i = -2; i < 3; i++) {
					if(y +i >= 0 && y+i < this.board.length) {
						if(board[y+i][x] != 1 && board[y+i][x] != 2&&attck[y+i][x] != 1 && attck[y+i][x] !=2 ) {
							attck[y+i][x] -= 50;
							System.out.println("| : 3 ["+y+"] ["+x+"]");
						}
					}
				}
			}
		}//현 해결
//		\
		block = 0;		
		empty = 0;
		blockLine = 0;
		emptyLine = 0;
		blockCnt = 0;
		emptyCnt = 0;
		
		for (int i = -2; i < 3; i++) {
			if(x +i >= 0 && x+i < this.board.length && y +i >= 0 && y+i < this.board.length) {
				if(board[y+i][x+i] == 1 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y+i][x+i] == 0){
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length && y +i >= 0 && y+i < this.board.length) {
						if(board[y+i][x+i] != 1 && board[y+i][x+i] != 2&&attck[y+i][x+i] != 1 && attck[y+i][x+i] !=2) {
							attck[y+i][x+i] -= 1000;
							System.out.println("\\ : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}else if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 2) {
				// 중요도 -50
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length && y +i >= 0 && y+i < this.board.length) {
						if(board[y+i][x+i] != 1 && board[y+i][x+i] != 2&&attck[y+i][x+i] != 1 && attck[y+i][x+i] !=2) {
							attck[y+i][x+i] -= 50;
							System.out.println("\\ : 3 ["+y+"] ["+x+"]");
						}
					}
				}
			}
		} // 요기도
//		/
		block = 0;		
		empty = 0;
		blockLine = 0;
		emptyLine = 0;
		blockCnt = 0;
		emptyCnt = 0;
		
		for (int i = -2; i < 3; i++) {
			if(x +i >= 0 && x+i < this.board.length && y -i >= 0 && y-i < this.board.length) {
				if(board[y-i][x+i] == 1 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y-i][x+i] == 0){
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length && y -i >= 0 && y-i < this.board.length) {
						if(board[y-i][x+i] != 1 && board[y-i][x+i] != 2&&attck[y-i][x+i] != 1 && attck[y-i][x+i] !=2) {
							attck[y-i][x+i] -= 1000;
							System.out.println("/ : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}else if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 2) {
				// 중요도 -50
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length && y -i >= 0 && y-i < this.board.length) {
						if(board[y-i][x+i] != 1 && board[y-i][x+i] != 2&&attck[y-i][x+i] != 1 && attck[y-i][x+i] !=2) {
							attck[y-i][x+i] -= 50;
							System.out.println("/ : 3 ["+y+"] ["+x+"]");
						}
					}
				}
			}
		}
	}
	
	public void attcking(int turn,int y ,int x) {
//		 ㅡ
		int block = 0;		
		int empty = 0;
		int blockLine = 0;
		int emptyLine = 0;
		int blockCnt = 0;
		int emptyCnt = 0;
		for (int i = -2; i < 3; i++) {
			if(x +i >= 0 && x+i < this.board.length) {
				if(board[y][x+i] == 2 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y][x+i] == 0){
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length) {
						if(board[y][x+i] != 1 &&  board[y][x+i] != 2 &&attck[y][x+i] != 1 && attck[y][x+i] !=2) {
							attck[y][x+i] -= 1000;
							System.out.println("- : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}
		}
		
//		|
		block = 0;		
		empty = 0;
		blockLine = 0;
		emptyLine = 0;
		blockCnt = 0;
		emptyCnt = 0;
		
		for (int i = -2; i < 3; i++) {
			if(y +i >= 0 && y+i < this.board.length) {
				if(board[y+i][x] == 2 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y+i][x] == 0) {
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(y +i >= 0 && y+i < this.board.length) {
						if(board[y+i][x] != 1 && board[y+i][x] != 2 &&attck[y+i][x] != 1 && attck[y+i][x] !=2) {
							attck[y+i][x] -= 1000;
							System.out.println("| : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}
		}//현 해결
//		\
		block = 0;		
		empty = 0;
		blockLine = 0;
		emptyLine = 0;
		blockCnt = 0;
		emptyCnt = 0;
		
		for (int i = -2; i < 3; i++) {
			if(x +i >= 0 && x+i < this.board.length && y +i >= 0 && y+i < this.board.length) {
				if(board[y+i][x+i] == 2 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y+i][x+i] == 0){
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length && y +i >= 0 && y+i < this.board.length) {
						if(board[y+i][x+i] != 1 && board[y+i][x+i] != 2&&attck[y+i][x+i] != 1 && attck[y+i][x+i] !=2) {
							attck[y+i][x+i] -= 1000;
							System.out.println("\\ : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}
		} // 요기도
//		/
		block = 0;		
		empty = 0;
		blockLine = 0;
		emptyLine = 0;
		blockCnt = 0;
		emptyCnt = 0;
		
		for (int i = -2; i < 3; i++) {
			if(x +i >= 0 && x+i < this.board.length && y -i >= 0 && y-i < this.board.length) {
				if(board[y-i][x+i] == 2 ) {
					block++;
					blockCnt++;
					empty = 0;
					if(blockLine < block) {
						blockLine = block;
					}
				}else if(board[y-i][x+i] == 0){
					block = 0;
					empty++;
					emptyCnt++;
					if(emptyLine < empty) {
						emptyLine = empty;
					}
				}
			}
		}
		if(blockCnt >= 3 && emptyLine < 2 ) {
			if(danger(blockLine, emptyLine, blockCnt, emptyCnt) == 1) {
				// 중요도 -1000
				for (int i = -2; i < 3; i++) {
					if(x +i >= 0 && x+i < this.board.length && y -i >= 0 && y-i < this.board.length) {
						if(board[y-i][x+i] != 1 && board[y-i][x+i] != 2&&attck[y-i][x+i] != 1 && attck[y-i][x+i] !=2) {
							attck[y-i][x+i] -= 1000;
							System.out.println("/ : 4 ["+y+"] ["+x+"]");
						}
					}
				}
			}
		}
	}
	public void emptyattck(int y,int x) {
		int isFive = 0;
		for (int i = -2; i < 3; i++) {
			if (x + i >= 0 && x + i < 16) {
				if (board[y][x + i] == 2) {
					isFive++;
					if (isFive == 4) {
						attck[y][x] -=100000;
					}
				}
			}
		}
//		|
		isFive = 0;
		for (int i = -2; i < 3; i++) {
			if (y + i >= 0 && y + i < 16) {
				if (board[y + i][x] == 2) {
					isFive++;
					if (isFive == 4) {
						attck[y][x] -=100000;
					}
				}
			}
		}
//		\
		isFive = 0;
		for (int i = -2; i < 3; i++) {
			if (x + i >= 0 && x + i < 16 && y + i >= 0 && y + i < 16) {
				if (board[y + i][x + i] == 2) {
					isFive++;
					if (isFive == 4) {
						attck[y][x] -=100000;
					}
				}
			}
		}
//		/
		isFive = 0;
		for (int i = -2; i < 3; i++) {
			if (x + i >= 0 && x + i < 16 && y - i >= 0 && y - i < 16) {
				if (board[y - i][x + i] == 2) {
					isFive++;
					if (isFive == 4) {
						attck[y][x] -=100000;
					}
				}
			}
		}
	}
	
	public int danger(int blockLine,int emptyLine,int blockCnt,int emptyCnt){
		if(blockCnt == 4&&emptyCnt == 1) { // 5개가 될 수 있는 상황  
			return 1;
		}else if(emptyLine == 1&& emptyCnt == 2 && blockLine >= 2) { // 4개를 만들수 있는 상황
			return 2;
		}else {
			return 0;
		}
	}
}
