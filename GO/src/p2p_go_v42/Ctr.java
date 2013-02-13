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
		}
		else if (selected && !activeButton.taken){
			selectAndMove(activeButton, selectedButton ,zoneGameButtons);	
		}
		else if (selected && activeButton.taken){
			unselect();	
		}
		
		else if (!activeButton.taken){
			ech(activeButton,zoneGameButtons);
		}
		else{
			selectedButton = drawSelect();
			
			
		}
	}	
	
	/**
	 * actually move the selected piece
	 * @param activeButton
	 * @param field
	 */
	public void selectAndMove(Stone activeButton,Stone selectedButton, Stone[][] field){
		
		if(player1.isCurrentPlayer){
			if ( ((activeButton.x == selectedButton.x+1 && activeButton.y == selectedButton.y )
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+1 )) 
				&& !(activeButton.y>=3 &&  activeButton.x>=3) ){
				selectedButton.taken = false;
//				selectedButton.player = null;
				activeButton.player = player1;
				activeButton.taken = true;
				drawNormalMove(selectedButton);
				changePlayer();
				selected = false;
			}
			else if ((activeButton.x == selectedButton.x+3 && activeButton.y == selectedButton.y
					  && field[activeButton.x-1][activeButton.y].player == player2
					  && !field[activeButton.x-2][activeButton.y].taken)
					|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y+3
						&& field[activeButton.x][activeButton.y-1].player == player2
						&& !field[activeButton.x][activeButton.y-2].taken)){
				selectedButton.taken = false;
//				selectedButton.player = null;
				activeButton.player = player1;
				activeButton.taken = true;
//				if (field[activeButton.x][activeButton.y-1].player == player2){
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
				
			}
		}
		else if (player2.isCurrentPlayer){
			if ( ((activeButton.x == selectedButton.x-1 && activeButton.y == selectedButton.y) 
				|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-1)) 
				&& !(activeButton.y<=1 && activeButton.x<=1) ){
					selectedButton.taken = false;
//					selectedButton.player = null;
					activeButton.player = player2;
					activeButton.taken = true;
					drawNormalMove(selectedButton);
					changePlayer();
					selected = false;
				}
			else if ((activeButton.x == selectedButton.x-3 && activeButton.y == selectedButton.y
					  && field[activeButton.x+1][activeButton.y].player == player1
					  && !field[activeButton.x+2][activeButton.y].taken)
					|| (activeButton.x == selectedButton.x && activeButton.y == selectedButton.y-3
						&& field[activeButton.x][activeButton.y+1].player == player1
						&& !field[activeButton.x][activeButton.y+2].taken)){
			
				selectedButton.taken = false;
//				selectedButton.player = null;
				activeButton.player = player2;
				activeButton.taken = true;
//				if (field[activeButton.x][activeButton.y+1].player == player1){
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
	public void drawNormalMove(Stone selectedButton) {
		if (player1.isCurrentPlayer) {
			activeButton.setIcon(black);
			selectedButton.setIcon(default_bg);
			
			
		}
		else if (player2.isCurrentPlayer) {
			activeButton.setIcon(white);
			selectedButton.setIcon(default_bg);
		}
	}
	
	/**
	 * piece moves and takes an enemy piece
	 */
	public void drawTakeMove(Stone selectedButton, Stone takenPiece) {
		if (player1.isCurrentPlayer) {
			activeButton.setIcon(black);
			takenPiece.setIcon(default_bg);
			selectedButton.setIcon(default_bg);
		}
		else if (player2.isCurrentPlayer) {
			activeButton.setIcon(white);
			takenPiece.setIcon(default_bg);
			selectedButton.setIcon(default_bg);
		}
	}
	
	/**
	 * select display to show selected piece
	 */
	public Stone drawSelect(){
		if (player1.isCurrentPlayer && activeButton.player == player1) {
			activeButton.setIcon(select_black);
			selectedButton = activeButton;
			selected = true;
		}
		else if (player2.isCurrentPlayer && activeButton.player == player2) {
			activeButton.setIcon(select_white);
			selectedButton = activeButton;
			selected = true;
		}
		return selectedButton;
	}
	
	/**
	 * unselect the selected button
	 */
	public void unselect(){
		if (player1.isCurrentPlayer) {
			selectedButton.setIcon(black);
			selected = false;
			selectedButton = null;
		}
		else if (player2.isCurrentPlayer) {
			selectedButton.setIcon(white);
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
