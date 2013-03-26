package p2p_go_v42;

import javax.swing.ImageIcon;


public class Ctr {
	
	Player player1, player2;
	
	Stone activeButton;
	Stone selectedButton;
	Stone[][] zoneGameButtons; 
	
	ImageIcon black = new ImageIcon("src/default_black_back.png");
//	ImageIcon black =new ImageIcon(new ImageIcon("src/default_black_back.png").getImage().getScaledInstance(76, 60, java.awt.Image.SCALE_SMOOTH));
	ImageIcon white = new ImageIcon("src/default_white_back.png");
//	ImageIcon white =new ImageIcon(new ImageIcon("src/default_white_back.png").getImage().getScaledInstance(76, 60, java.awt.Image.SCALE_SMOOTH));
	ImageIcon default_bg = new ImageIcon("src/default_BG_back.png");
//	ImageIcon default_bg =new ImageIcon(new ImageIcon("src/default_BG_back.png").getImage().getScaledInstance(76, 60, java.awt.Image.SCALE_SMOOTH));
	ImageIcon select_black =new ImageIcon(new ImageIcon("src/default_black_select.png").getImage().getScaledInstance(76, 60, java.awt.Image.SCALE_SMOOTH));
	ImageIcon select_white =new ImageIcon(new ImageIcon("src/default_white_select.png").getImage().getScaledInstance(76, 60, java.awt.Image.SCALE_SMOOTH));
	
	ImageIcon black_yiss = new ImageIcon("src/default_black_yiss.png");
	ImageIcon white_yiss = new ImageIcon("src/default_white_yiss.png");
	ImageIcon black_select_yiss = new ImageIcon("src/default_black_select_yiss.png");
	ImageIcon white_select_yiss = new ImageIcon("src/default_white_select_yiss.png");
	
	private boolean selected = false;
	
	Ctr() {
		player1 = new Player();
		player2 = new Player();
		player1.isCurrentPlayer = true; // because it will be turned at the beginning of the game
	}
	
	
	public void move(Stone activeButton, Stone[][] zoneGameButtons) {
	
		this.activeButton = activeButton;
		this.zoneGameButtons = zoneGameButtons;
		
		GUI.disableButtons();
		
		if (selected && selectedButton == activeButton){
			unselect();
			System.out.println("1");
		}
		else if (selected && (!activeButton.taken || dalsag() || dajbed() )){
			if (selectedButton.isyiss){
				selectAndMoveYiss(activeButton, selectedButton ,zoneGameButtons);
			}
			else
				selectAndMove(activeButton, selectedButton ,zoneGameButtons);
			System.out.println("2");
		}
		else if (selected && activeButton.taken  ){
			System.out.println("3");
			unselect();	
		}
		
		else if (!activeButton.taken){
			ech(activeButton,zoneGameButtons);
			System.out.println("4");
		}
		else{
			selectedButton = drawSelect();
			System.out.println("5");
			
			
		}
	}	
	
	
	public boolean dalsag(){
		if ((selectedButton.x == 2 && selectedButton.y == 2))
			return true;
		else
			return false;
	}
	
	public boolean dajbed(){
		if (selectedButton.ifoq || !selectedButton.ijbad)
			return true;
		else
			return false;
	}
	
	
	
	public void selectAndMoveYiss(Stone activeButton,Stone selectedButton, Stone[][] field){
	
		if (selectedButton.ifoq || !selectedButton.ijbad){
			System.out.println((selectedButton.ifoq) ?"illa ifoq":"asswey");
			if (!field[2][2].taken){
				if ((activeButton.x == 2 && activeButton.y == 3 && activeButton.player != null && activeButton.player != selectedButton.player && selectedButton.y==4)
					||(activeButton.x == 3 && activeButton.y == 2 && activeButton.player != null && activeButton.player != selectedButton.player && selectedButton.x==4)
					||(activeButton.x == 1 && activeButton.y == 2 && activeButton.player != null && activeButton.player != selectedButton.player && selectedButton.x==0)
					||(activeButton.x == 2 && activeButton.y == 1 && activeButton.player != null && activeButton.player != selectedButton.player && selectedButton.y==0)){
					
					System.out.println("ijbad l timess");
					selectedButton.ijbad = true;
					
					//field[2][2].isyiss = true;
					this.activeButton = field[2][2];
					
					selectAndMoveYiss(this.activeButton, selectedButton, field);
					
				}
			}
		}
		
		else if ( (activeButton.x == selectedButton.x+1 && activeButton.y == selectedButton.y )
			|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+1 ) 
			|| (activeButton.x == selectedButton.x-1 && activeButton.y == selectedButton.y )
			|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-1 ) ){
			
			selectedButton.taken = false;
			selectedButton.isyiss = false;
			selectedButton.player = null;
			
			if(player1.isCurrentPlayer){
				activeButton.player = player1;
			}else{
				activeButton.player = player2;
			}
			activeButton.taken = true;
			activeButton.isyiss = true;

			drawNormalMove(selectedButton, false);
			changePlayer();
			selected = false;
		}
		else if ( (activeButton.x == selectedButton.x+2 && activeButton.y == selectedButton.y && field[selectedButton.x+1][selectedButton.y].player != null && field[selectedButton.x+1][selectedButton.y].player != selectedButton.player && !field[selectedButton.x+1][selectedButton.y].ifoq)
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+2 && field[selectedButton.x][selectedButton.y+1].player != null && field[selectedButton.x][selectedButton.y+1].player != selectedButton.player && !field[selectedButton.x][selectedButton.y+1].ifoq) 
				|| (activeButton.x == selectedButton.x-2 && activeButton.y == selectedButton.y && field[selectedButton.x-1][selectedButton.y].player != null && field[selectedButton.x-1][selectedButton.y].player != selectedButton.player && !field[selectedButton.x-1][selectedButton.y].ifoq)
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-2 && field[selectedButton.x][selectedButton.y-1].player != null && field[selectedButton.x][selectedButton.y-1].player != selectedButton.player && !field[selectedButton.x][selectedButton.y-1].ifoq) ){
				selectedButton.taken = false;
				selectedButton.isyiss = false;
				selectedButton.player = null;
				
				if(player1.isCurrentPlayer){
					activeButton.player = player1;
				}else{
					activeButton.player = player2;
				}
				activeButton.taken = true;
				activeButton.isyiss = true;
				if (activeButton.x == selectedButton.x){
					if (activeButton.y > selectedButton.y){
						field[activeButton.x][activeButton.y-1].taken = false;
						field[activeButton.x][activeButton.y-1].player = null;
						field[activeButton.x][activeButton.y-1].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x][activeButton.y-1]);
					}
					else{
						field[activeButton.x][activeButton.y+1].taken = false;
						field[activeButton.x][activeButton.y+1].player = null;
						field[activeButton.x][activeButton.y+1].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x][activeButton.y+1]);
					}
				}
				else if (activeButton.y == selectedButton.y){
					if (activeButton.x > selectedButton.x){
						System.out.println("this");
						field[activeButton.x-1][activeButton.y].taken = false;
						field[activeButton.x-1][activeButton.y].player = null;
						field[activeButton.x-1][activeButton.y].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x-1][activeButton.y]);
					}
					else{
						field[activeButton.x+1][activeButton.y].taken = false;
						field[activeButton.x+1][activeButton.y].player = null;
						field[activeButton.x+1][activeButton.y].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x+1][activeButton.y]);
					}
				}
				changePlayer();
				selected = false;
			}
		else if ( (activeButton.x == selectedButton.x+3 && activeButton.y == selectedButton.y && field[activeButton.x-1][activeButton.y].player != null && field[activeButton.x-1][activeButton.y].player != selectedButton.player && !field[activeButton.x-2][activeButton.y].taken && !field[activeButton.x-1][activeButton.y].ifoq)
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+3 && field[activeButton.x][activeButton.y-1].player != null && field[activeButton.x][activeButton.y-1].player != selectedButton.player && !field[activeButton.x][activeButton.y-2].taken && !field[activeButton.x][activeButton.y-1].ifoq) 
				|| (activeButton.x == selectedButton.x-3 && activeButton.y == selectedButton.y && field[activeButton.x+1][activeButton.y].player != null && field[activeButton.x+1][activeButton.y].player != selectedButton.player && !field[activeButton.x+2][activeButton.y].taken && !field[activeButton.x+1][activeButton.y].ifoq)
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-3 && field[activeButton.x][activeButton.y+1].player != null && field[activeButton.x][activeButton.y+1].player != selectedButton.player && !field[activeButton.x][activeButton.y+2].taken && !field[activeButton.x][activeButton.y+1].ifoq) ){
				selectedButton.taken = false;
				selectedButton.isyiss = false;
				selectedButton.player = null;
				
				if(player1.isCurrentPlayer){
					activeButton.player = player1;
				}else{
					activeButton.player = player2;
				}
				activeButton.taken = true;
				activeButton.isyiss = true;
				if (activeButton.x == selectedButton.x){
					if (activeButton.y > selectedButton.y){
						field[activeButton.x][activeButton.y-1].taken = false;
						field[activeButton.x][activeButton.y-1].player = null;
						field[activeButton.x][activeButton.y-1].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x][activeButton.y-1]);
					}
					else{
						field[activeButton.x][activeButton.y+1].taken = false;
						field[activeButton.x][activeButton.y+1].player = null;
						field[activeButton.x][activeButton.y+1].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x][activeButton.y+1]);
					}
				}
				else if (activeButton.y == selectedButton.y){
					if (activeButton.x > selectedButton.x){
						field[activeButton.x-1][activeButton.y].taken = false;
						field[activeButton.x-1][activeButton.y].player = null;
						field[activeButton.x-1][activeButton.y].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x-1][activeButton.y]);
					}
					else{
						field[activeButton.x+1][activeButton.y].taken = false;
						field[activeButton.x+1][activeButton.y].player = null;
						field[activeButton.x+1][activeButton.y].isyiss = false;
						drawTakeMove(selectedButton,field[activeButton.x+1][activeButton.y]);
					}
				}
				changePlayer();
				selected = false;
			}
	}
	
	/**
	 * actually move the selected piece
	 * @param activeButton
	 * @param field
	 */
public void selectAndMove(Stone activeButton,Stone selectedButton, Stone[][] field){
		
		if(player1.isCurrentPlayer){
			//alsag
			if (selectedButton.x == 2 && selectedButton.y == 2){
				if ((activeButton.x == 2 && activeButton.y == 4 && !field[2][3].taken && activeButton.player == player2)
					||(activeButton.x == 4 && activeButton.y == 2 && !field[3][2].taken && activeButton.player == player2)	){
					System.out.println("ilsag");
					selectedButton.taken = false;
					selectedButton.player = null;
					activeButton.player = player1;
					activeButton.taken = true;
					
					drawNormalMove(selectedButton, false);
					changePlayer();
					selected = false;
				}
			}
			
		else if ( ((activeButton.x == selectedButton.x+1 && activeButton.y == selectedButton.y )
			|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+1 )) 
			&& !(activeButton.y>=3 &&  activeButton.x>=3) ){
			selectedButton.taken = false;
			selectedButton.player = null;
			activeButton.player = player1;
			activeButton.taken = true;
			
			drawNormalMove(selectedButton, false);
			changePlayer();
			selected = false;
		}
		else if (activeButton.x == selectedButton.x+3 && activeButton.y == selectedButton.y
				  && field[activeButton.x-1][activeButton.y].player == player2
				  && !field[activeButton.x-2][activeButton.y].taken
				  && !field[activeButton.x-1][activeButton.y].ifoq){
			selectedButton.taken = false;
			selectedButton.player = null;
			activeButton.player = player1;
			activeButton.taken = true;
			field[activeButton.x-1][activeButton.y].taken = false;
			field[activeButton.x-1][activeButton.y].isyiss = false;
			field[activeButton.x-1][activeButton.y].player = null;
			drawTakeMove(selectedButton,field[activeButton.x-1][activeButton.y]);
			changePlayer();
			selected = false;
		}
		else if(activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+3
					&& field[activeButton.x][activeButton.y-1].player == player2
					&& !field[activeButton.x][activeButton.y-2].taken
					&& !field[activeButton.x][activeButton.y-1].ifoq){
			selectedButton.taken = false;
			selectedButton.player = null;
			activeButton.player = player1;
			activeButton.taken = true;
			field[activeButton.x][activeButton.y-1].taken = false;
			field[activeButton.x][activeButton.y-1].isyiss = false;
			field[activeButton.x][activeButton.y-1].player = null;
			drawTakeMove(selectedButton,field[activeButton.x][activeButton.y-1]);
			changePlayer();
			selected = false;
		}
			
	}
	else if (player2.isCurrentPlayer){
		if (selectedButton.x == 2 && selectedButton.y == 2){
			if ((activeButton.x == 0 && activeButton.y == 2 && !field[1][2].taken && activeButton.player == player1)
				||(activeButton.x == 2 && activeButton.y == 0 && !field[2][1].taken && activeButton.player == player1)	){
				System.out.println("ilsag");
				selectedButton.taken = false;
				selectedButton.player = null;
				activeButton.player = player2;
				activeButton.taken = true;
				
				drawNormalMove(selectedButton, false);
				changePlayer();
				selected = false;
			}
		}
		
		else if ( ((activeButton.x == selectedButton.x-1 && activeButton.y == selectedButton.y) 
			|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-1)) 
			&& !(activeButton.y<=1 && activeButton.x<=1) ){
				selectedButton.taken = false;
				selectedButton.player = null;
				activeButton.player = player2;
				activeButton.taken = true;
				drawNormalMove(selectedButton,false);
				changePlayer();
				selected = false;
			}
		else if (activeButton.x == selectedButton.x-3 && activeButton.y == selectedButton.y
				  && field[activeButton.x+1][activeButton.y].player == player1
				  && !field[activeButton.x+2][activeButton.y].taken
				  && !field[activeButton.x+1][activeButton.y].ifoq){
		
			selectedButton.taken = false;
			selectedButton.player = null;
			activeButton.player = player2;
			activeButton.taken = true;
			field[activeButton.x+1][activeButton.y].taken = false;
			field[activeButton.x+1][activeButton.y].isyiss = false;
			field[activeButton.x+1][activeButton.y].player = null;
			drawTakeMove(selectedButton,field[activeButton.x+1][activeButton.y]);
			changePlayer();
			selected = false;
		}
		else if (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-3
					&& field[activeButton.x][activeButton.y+1].player == player1
					&& !field[activeButton.x][activeButton.y+2].taken
					&& !field[activeButton.x][activeButton.y+1].ifoq){
		
			selectedButton.taken = false;
			selectedButton.player = null;
			activeButton.player = player2;
			activeButton.taken = true;
			field[activeButton.x][activeButton.y+1].taken = false;
			field[activeButton.x][activeButton.y+1].isyiss = false;
			field[activeButton.x][activeButton.y+1].player = null;
			drawTakeMove(selectedButton,field[activeButton.x][activeButton.y+1]);
			changePlayer();
			selected = false;
		}
			
	}		
}

	
	/**
	 * ech and refresh the display
	 * @param activeButton
	 * @param field
	 */
	public void ech(Stone activeButton, Stone[][] field){
		if(player1.isCurrentPlayer){
			if (activeButton.x <= 1 && activeButton.y <= 1){
				drawEch();
				activeButton.player = player1 ;
				activeButton.taken = true;
				changePlayer();
			}
			else System.err.println("wrong place for 'ech'");
		}
		else{
			if (activeButton.x >= 3 && activeButton.y >= 3){
				drawEch();
				activeButton.player = player2 ;
				activeButton.taken = true;
				changePlayer();
			}
			else System.err.println("wrong place for 'ech'");
		}
		
	}
	
	/**
	 * when a piece moves without taking an enemy piece
	 */
	public void drawNormalMove(Stone selectedButton, boolean isYiss) {
		ImageIcon piece_black = black;
		ImageIcon piece_white = white;
		if (activeButton.isyiss){
			piece_black = black_yiss;
			piece_white = white_yiss;
		}

		if (player1.isCurrentPlayer) {
			if ((activeButton.x == 4 && activeButton.y == 2) || (activeButton.x == 2 && activeButton.y == 4)){
				piece_black = black_yiss;
				if (!activeButton.isyiss) activeButton.ijbad = false;
				activeButton.isyiss = true;
				
			}
			activeButton.setIcon(piece_black);
			selectedButton.setIcon(default_bg);
		}
		else if (player2.isCurrentPlayer) {
			if ((activeButton.x == 2 && activeButton.y == 0) || (activeButton.x == 0 && activeButton.y == 2)){
				piece_white = white_yiss;
				if (!activeButton.isyiss) activeButton.ijbad = false;
				activeButton.isyiss = true;
			}
			activeButton.setIcon(piece_white);
			selectedButton.setIcon(default_bg);
		}
	}
	
	/**
	 * piece moves and takes an enemy piece
	 */
	public void drawTakeMove(Stone selectedButton, Stone takenPiece) {
		ImageIcon piece_black = black;
		ImageIcon piece_white = white;
		if (activeButton.isyiss){
			piece_black = black_yiss;
			piece_white = white_yiss;
		}
		if (player1.isCurrentPlayer) {
			if ((activeButton.x == 4 && activeButton.y == 2) || (activeButton.x == 2 && activeButton.y == 4)){
//				GUI.setButtonenable("all");
				piece_black = black_yiss;
				if (!activeButton.isyiss) activeButton.ijbad = false;
				activeButton.isyiss = true;
			}
			activeButton.setIcon(piece_black);
			takenPiece.setIcon(default_bg);
			selectedButton.setIcon(default_bg);
		}
		else if (player2.isCurrentPlayer) {
			if ((activeButton.x == 0 && activeButton.y == 2) || (activeButton.x == 2 && activeButton.y == 0)){
//				GUI.setButtonenable("all");
				piece_white = white_yiss;
				if (!activeButton.isyiss) activeButton.ijbad = false;
				activeButton.isyiss = true;
			}
			System.out.println("this2");
			activeButton.setIcon(piece_white);
			takenPiece.setIcon(default_bg);
			selectedButton.setIcon(default_bg);
		}
	}
	
	/**
	 * select display to show selected piece
	 */
	public Stone drawSelect(){
		ImageIcon piece_black = select_black;
		ImageIcon piece_white = select_white;
		if (activeButton.isyiss){
			piece_black = black_select_yiss;
			piece_white = white_select_yiss;
		}
		
		if (player1.isCurrentPlayer && activeButton.player == player1) {
			if ((activeButton.x == 4 && activeButton.y == 2) || (activeButton.x == 2 && activeButton.y == 4)){
				if (activeButton.ifoq) GUI.enableButton("ijbad");
				else GUI.enableButton("all");
			}
			activeButton.setIcon(piece_black);
			selectedButton = activeButton;
			selected = true;
		}
		else if (player2.isCurrentPlayer && activeButton.player == player2) {
			if ((activeButton.x == 0 && activeButton.y == 2) || (activeButton.x == 2 && activeButton.y == 0)){
				if (activeButton.ifoq) GUI.enableButton("ijbad");
				else GUI.enableButton("all");
			}
			activeButton.setIcon(piece_white);
			selectedButton = activeButton;
			selected = true;
		}
		return selectedButton;
	}
	
	/**
	 * unselect the selected button
	 */
	public void unselect(){
		ImageIcon piece_black = black;
		ImageIcon piece_white = white;
		if (selectedButton.isyiss){
			piece_black = black_yiss;
			piece_white = white_yiss;
		}
		
		if (player1.isCurrentPlayer) {
			selectedButton.setIcon(piece_black);
			selected = false;
			selectedButton = null;
		}
		else if (player2.isCurrentPlayer) {
			selectedButton.setIcon(piece_white);
			selected = false;
			selectedButton = null;
		}
	}
	
	/**
	 * draws the button and refresh the display 
	 */
	public void drawEch() {
		if (player1.isCurrentPlayer) {
			activeButton.setIcon(black);
		}
		else if (player2.isCurrentPlayer) {
			activeButton.setIcon(white);
		}
	}
	
	
	/**
	 * change player after a successful move
	 */
	public void changePlayer() {
		
		if (player1.isCurrentPlayer) {
			activeButton.player = player1;
			player1.isCurrentPlayer = false;
			player2.isCurrentPlayer = true;
			GUI.updateActivePlayer(new ImageIcon ("src/white_logo.png"));
		} 
		else if (player2.isCurrentPlayer) {
			activeButton.player = player2;
			player2.isCurrentPlayer = false;
			player1.isCurrentPlayer = true;
			GUI.updateActivePlayer(new ImageIcon ("src/black_logo.png"));
		}
	}

	
	public void setifoq(){
		selectedButton.ifoq = true;
		selectedButton.ijbad = false;
		unselect();
		changePlayer();
		selected = false;
		GUI.disableButtons();
		
	}
	
	public void setijbad(){
		selectedButton.ifoq = false;
		selectedButton.ijbad = true;
		unselect();
		changePlayer();
		selected = false;
		GUI.disableButtons();
	}
}
