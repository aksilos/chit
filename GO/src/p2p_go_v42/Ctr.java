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
		
		
		if (selectedButton == activeButton){
			unselect();
			System.out.println("1");
		}
		else if (selected && !activeButton.taken){
			selectAndMove(activeButton, selectedButton ,zoneGameButtons);
			System.out.println("2");
		}
		else if (selected && activeButton.taken){
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
	
	/**
	 * actually move the selected piece
	 * @param activeButton
	 * @param field
	 */
	public void selectAndMove(Stone activeButton,Stone selectedButton, Stone[][] field){
		System.out.println("b: "+(player1.isCurrentPlayer && selectedButton.isyiss));
		if(player1.isCurrentPlayer || (player2.isCurrentPlayer && selectedButton.isyiss) ){
			
			if ( ((activeButton.x == selectedButton.x+1 && activeButton.y == selectedButton.y )
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+1 )) 
				&& !(activeButton.y>=3 && activeButton.x>=3 && !selectedButton.isyiss) ){
				selectedButton.taken = false;
				activeButton.player = player1;
				activeButton.taken = true;
				if ((activeButton.x == 2 && activeButton.y == 4) 
					|| (activeButton.x == 4 && activeButton.y == 2)
					|| !player1.isCurrentPlayer){
					activeButton.isyiss = true;
					activeButton.player = player2;
				}
				
				drawNormalMove(selectedButton,false);
				
				changePlayer();
				selected = false;
				this.selectedButton = new Stone();
			}
			else if ((activeButton.x == selectedButton.x+3 && activeButton.y == selectedButton.y
					  && field[activeButton.x-1][activeButton.y].player == player2
					  && !field[activeButton.x-2][activeButton.y].taken)
					|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+3
						&& field[activeButton.x][activeButton.y-1].player == player2
						&& !field[activeButton.x][activeButton.y-2].taken)){
				selectedButton.taken = false;
				activeButton.player = player1;
				activeButton.taken = true;
				if ((activeButton.x == 2 && activeButton.y == 4)  || (activeButton.x == 4 && activeButton.y == 2)){
					activeButton.isyiss = true;
					activeButton.player = player2;
				}
				if (selectedButton.x == activeButton.x){
					field[activeButton.x][activeButton.y-1].taken = false;
					drawTakeMove(selectedButton,field[activeButton.x][activeButton.y-1]);
				}
				else{
					drawTakeMove(selectedButton,field[activeButton.x-1][activeButton.y]);
					field[activeButton.x-1][activeButton.y].taken = false;
				}
				changePlayer();
				selected = false;
				this.selectedButton = new Stone();
			}
		}
		if (player2.isCurrentPlayer || (player1.isCurrentPlayer && selectedButton.isyiss) ){
			if ( ((activeButton.x == selectedButton.x-1 && activeButton.y == selectedButton.y) 
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-1)) 
				&& !(activeButton.y<=1 && activeButton.x<=1 && !selectedButton.isyiss)){
				
					selectedButton.taken = false;
					activeButton.player = player2;
					activeButton.taken = true;
					if ((activeButton.x == 2 && activeButton.y == 0) 
							|| (activeButton.x == 0 && activeButton.y == 2)
							|| !player2.isCurrentPlayer ){
							activeButton.isyiss = true;
							activeButton.player = player1;
						}
					
					drawNormalMove(selectedButton,false);
						
					changePlayer();
					selected = false;
					this.selectedButton = new Stone();
				}
			else if ((activeButton.x == selectedButton.x-3 && activeButton.y == selectedButton.y
					  && field[activeButton.x+1][activeButton.y].player == player1
					  && !field[activeButton.x+2][activeButton.y].taken)
					|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-3
						&& field[activeButton.x][activeButton.y+1].player == player1
						&& !field[activeButton.x][activeButton.y+2].taken)){
			
				selectedButton.taken = false;
				activeButton.player = player2;
				activeButton.taken = true;
				if ((activeButton.x == 2 && activeButton.y == 0)  || (activeButton.x == 0 && activeButton.y == 2)){
					activeButton.isyiss = true;
					activeButton.player = player2;
				}
				if (selectedButton.x == activeButton.x){
					field[activeButton.x][activeButton.y+1].taken = false;
					drawTakeMove(selectedButton,field[activeButton.x][activeButton.y+1]);
				}
				else{
					drawTakeMove(selectedButton,field[activeButton.x+1][activeButton.y]);
					field[activeButton.x+1][activeButton.y].taken = false;
				}
				changePlayer();
				selected = false;
				this.selectedButton = new Stone();
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
			activeButton.setIcon(piece_black);
			selectedButton.setIcon(default_bg);
		}
		else if (player2.isCurrentPlayer) {
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
			activeButton.setIcon(piece_black);
			takenPiece.setIcon(default_bg);
			selectedButton.setIcon(default_bg);
		}
		else if (player2.isCurrentPlayer) {
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
			activeButton.setIcon(piece_black);
			selectedButton = activeButton;
			selected = true;
		}
		else if (player2.isCurrentPlayer && activeButton.player == player2) {
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

}
