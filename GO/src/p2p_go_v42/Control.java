//package p2p_go_v42;
///**
// * p2p
// * SS11
// * A2
// * @Team  S4T3
// * @author Mahmoud Dariti, #1991840
// * @author Mohamed Sakhri, #1991287
// * @Aufnahme  von Herr Schaefers
// * 
// */
//import java.util.ArrayList;
//
//import javax.swing.ImageIcon;
//
//public class Control {
//	
//	Player player1;
//	Player player2;
//	
//	Stone activeButton; 
//	Stone lastWhiteCaptured;
//	Stone lastBlackCaptured;
//	Stone lastKo;
//	Stone[][] zoneGameButtons; 
//	
//	Set set;
//	Set territoty;
//	Set lastPrisoners; // used in Undo
//
//
//	ArrayList<Set> enemiesSet; // ArrayList of enemies' Sets
//	ArrayList<Set> ownSet; // ArrayList of own Sets
//	ArrayList<Set> terrSet; // ArrayList of territory Sets
//	ArrayList<Set> whiteTerritory;
//	ArrayList<Set> blackTerritory;
//	ArrayList<Set> lastBlackTerritory;  // to use for Undo
//	ArrayList<Set> lastWhiteTerritory;  // to use for Undo
//	
//	boolean pass ;
//	boolean undo ;
//	boolean out = false;  // to get out from bildTerritory
//	boolean willCapture = false;
//	boolean ko = false;
//	boolean started = false;
//	
//	int blackT;
//	int WhiteT;
//	int whitePrisoners = 0;
//	int blackPrisoners = 0;
//	int blackScore = 0;
//	int whiteScore = 0;
//
//	ImageIcon black = new ImageIcon("src/default_black_back.png");
//	ImageIcon white = new ImageIcon("src/default_white_back.png");
//	ImageIcon default_bg = new ImageIcon("src/default_BG_back.png");
//
//	Control() {
//		player1 = new Player();
//		player2 = new Player();
//		player2.isCurrentPlayer = true; // because it will be turned at the beginning of the game
//		blackTerritory = new ArrayList<Set>();
//		whiteTerritory = new ArrayList<Set>();
//		lastBlackTerritory = new ArrayList<Set>();
//		lastWhiteTerritory = new ArrayList<Set>();
//	}
//
//
//	/**
//	 * 
//	 * @param activeButton
//	 * @param zoneGameButtons
//	 */
//	@SuppressWarnings("unchecked")
//	public void move(Stone activeButton, Stone[][] zoneGameButtons) {
//		
//		this.activeButton = activeButton;
//		this.zoneGameButtons = zoneGameButtons;
//		started = true;
//		enemiesSet = new ArrayList<Set>(); // enemiesSet contains sets of Enemies
//		ownSet = new ArrayList<Set>(); // ownSet contains own sets
//		
//		if (!activeButton.taken) {
//			terrSet = new ArrayList<Set>(); // contains territory sets	
//			set = new Set();
//			activeButton.taken = true;
//			turnPlayer(); // turns the activity of players alternatively
//			
//			willCapture = willCapturePrisonners(activeButton);
//			
//			ownSet = getOwnSets(activeButton);
//			enemiesSet = getEnemmies(activeButton);
//			willCapture = willCapturePrisonners(activeButton);
//
//			// a move is legal if it's not a KO
//			// AND if the move is not a suicide or will capture prisoners, even if it seems as
//			// suicide, it's legal.
//			// to capture prisoners has a higher priority
//			if (!isKO(activeButton) && (willCapturePrisonners(activeButton) || !(isSuicide(activeButton)))){
//				undo = false;
//				pass = false;
//				lastPrisoners = new Set();
//				lastBlackTerritory = (ArrayList<Set>) blackTerritory.clone();
//				lastWhiteTerritory = (ArrayList<Set>) whiteTerritory.clone();
//				
//				updateTerritory();
//				updateDisplay(activeButton); // as earlier said, it updates the board
// 				drawMove(); // set suitable icon for each son of bitch player ! 				
//				terrSet = getTerritorySet(activeButton);  //search for territories
//				updateTerritory();
//				
//				blackT = getTerritorySize(blackTerritory) ;
//				WhiteT = getTerritorySize(whiteTerritory);
//				whiteScore = blackPrisoners + blackT;
//				blackScore = whitePrisoners + WhiteT;
//				
//				GUI.updateScore(blackT, blackPrisoners, WhiteT, whitePrisoners,blackScore, whiteScore);
//				if(player1.isCurrentPlayer)
//					GUI.updateActivePlayer(new ImageIcon ("src/white_logo.png"));
//				else
//					GUI.updateActivePlayer(new ImageIcon ("src/black_logo.png"));
//
//				System.out.println("territory Black: "+WhiteT);
//				System.out.println("territory White: "+blackT);
//				System.out.println("Prisoners White : "+whitePrisoners);
//				System.out.println("Prisoners Balck: "+blackPrisoners);
//			}			
//			else // if the move was illegal(suicide), we just cancel it
//			{
//				cancelPlay();
//			}
//		} 
//	}
//
//	
//	/**
//	 * Undo last move
//	 *  Restore score, territory
//	 *  display the restored state
//	 *  special case : undo at the game's begin = > display error
//	 */
//	public void undo() {
//		
//		if(activeButton != null){
//		if (undo){
//			GUI.infoMessage("No  more Undo ! Please play a Move or Pass", "Undo, error");
//			return ;
//		}
//		if(ko){
//			GUI.infoMessage("You cannot undo ! Play a legal move first ", "Undo, error");
//			turnPlayer();
//		}
//		
//		// if last move has changed the score, restore it
//		if(willCapture){
//			for(Stone s:lastPrisoners.stoneSet){
//				zoneGameButtons[s.x][s.y].taken = true;
//				if(player1.isCurrentPlayer){
//					zoneGameButtons[s.x][s.y].setIcon(white);
//					zoneGameButtons[s.x][s.y].player = player1;
//					whitePrisoners--;
//				}
//				else{
//					zoneGameButtons[s.x][s.y].setIcon(black);
//					zoneGameButtons[s.x][s.y].player = player2;
//					blackPrisoners--;
//				}
//				
//				System.out.println("Prisoner : " + s);
//				
//			}
//		}
//			// in all cases the last move will be removed
//			activeButton.setIcon(default_bg);
//			activeButton.player = null;
//			activeButton.taken = false;
//			cancelPlay();
//			undo = true;
//			
//			// restore each player territory
//			whiteTerritory = lastWhiteTerritory;
//			blackTerritory = lastBlackTerritory;
//			blackT = getTerritorySize(blackTerritory) ;
//			WhiteT = getTerritorySize(whiteTerritory);
//			System.out.println("territory Black in Undo: "+WhiteT);
//			System.out.println("territory White in Undo: "+blackT);
//			whiteScore = blackPrisoners + blackT;
//			blackScore = whitePrisoners + WhiteT;
//			// display the restored score
//			GUI.updateScore(blackT, blackPrisoners, WhiteT, whitePrisoners,blackScore, whiteScore);
//			//display the active player
//			if(player1.isCurrentPlayer)
//				GUI.updateActivePlayer(new ImageIcon ("src/white_logo.png"));
//			else
//				GUI.updateActivePlayer(new ImageIcon ("src/black_logo.png"));
//		}
//		// if the undo button is clicked at the game's begin 
//		else {
//			GUI.infoMessage("You cannot undo anything. It has been no moves yet !", "Undo error");
//		}
//	}
//	
//	/**
//	 * change the active player
//	 * 2 pass => game over and show who is the winner
//	 */
//	public void pass() {
//		if (pass){
//			String titel = "Game over";
//			String message = "GAME OVER.";
//			//updateAfterPass();
//
////			System.out.println("2 mal pass hintereinander !!");
//			if(blackScore > whiteScore){
//				message +="  Black player wins ! Congratulations! :)";
//			}
//			else if(blackScore < whiteScore){
//				message +="  White player wins ! Well done dude ! ;)";
//
//			}
//			else {
//				message +=" " + "Egality ! You might have give it a try again ;)";
//			}
//			// display the window and go back 
//			GUI.infoMessage(message, titel);
//			GUI.reset();
//			System.out.print("Game over after pass \n");
//			return ;
//		} else {
//		updateAfterPass();
//		pass = true ;
//		}
//	}
//	
//	public void updateAfterPass(){
//
//		if(!started && !pass){
//			player1.isCurrentPlayer = true;
//		}
//				
//		if (player1.isCurrentPlayer) {
//			player1.isCurrentPlayer = false;
//			player2.isCurrentPlayer = true;
//			blackPrisoners++;
//			GUI.updateActivePlayer(new ImageIcon ("src/black_logo.png"));
//
//		} 
//		else if (player2.isCurrentPlayer) {
//			player2.isCurrentPlayer = false;
//			player1.isCurrentPlayer = true;
//			whitePrisoners++;
//			GUI.updateActivePlayer(new ImageIcon ("src/white_logo.png"));
//
//		}
//		whiteScore = blackPrisoners + blackT;
//		blackScore = whitePrisoners + WhiteT;
//		
//		GUI.updateScore(blackT, blackPrisoners, WhiteT, whitePrisoners,blackScore, whiteScore);
//		if(player1.isCurrentPlayer)
//			GUI.updateActivePlayer(new ImageIcon ("src/white_logo.png"));
//		else
//			GUI.updateActivePlayer(new ImageIcon ("src/black_logo.png"));
//
//	}
//
//	// changes current player alternatively, the icon hasn't been displayed yet
//	// but will be in drawMove if the move is legal
//	public void turnPlayer() {
//		
//		if (player1.isCurrentPlayer) {
//			activeButton.player = player1;
//			player1.isCurrentPlayer = false;
//			player2.isCurrentPlayer = true;
//		} 
//		else if (player2.isCurrentPlayer) {
//			activeButton.player = player2;
//			player2.isCurrentPlayer = false;
//			player1.isCurrentPlayer = true;
//		}
//	}
//
//	// just cancel the move if it's illegal
//	public void cancelPlay() {
//		activeButton.taken = false;
//		activeButton.player = null;
//		if (player1.isCurrentPlayer) {
//			player1.isCurrentPlayer = false;
//			player2.isCurrentPlayer = true;
//		}
//		else if (player2.isCurrentPlayer) {
//			player2.isCurrentPlayer = false;
//			player1.isCurrentPlayer = true;
//		}
//	}
//
//	// set suitable icon for the new move's player
//	public void drawMove() {
//		if (player1.isCurrentPlayer) {
//			activeButton.setIcon(black);
//		}
//		else if (player2.isCurrentPlayer) {
//			activeButton.setIcon(white);
//		}
//	}
//
//	// how to handle if the move is already in a territory
//	public void updateTerritory(){
//		if ( player2.isCurrentPlayer){
//			// black stone inside its own territory, a stone from territory will be lost
//			for( int i =0; i<blackTerritory.size(); i++){
//				for(Stone sB: blackTerritory.get(i).stoneSet) {
//					if (sB == activeButton){
//						blackTerritory.get(i).stoneSet.remove(sB);
//						terrSet = getTerritorySet(activeButton);  //search for territories
//						return;
//					}
//				}
//			}
//			
//			// black stone inside enemy'e territory, the territorySet will be lost !
//			for( int i =0; i<whiteTerritory.size(); i++){
//				for(Stone sB: whiteTerritory.get(i).stoneSet) {
//					if (sB == activeButton){
//						whiteTerritory.remove(whiteTerritory.get(i));
//						terrSet = getTerritorySet(activeButton);  //search for territories
//						return;
//					}
//				}
//			}
//		}
//		if (player1.isCurrentPlayer){
//			// white stone inside its own territory, a stone from territory will be lost
//			for( int i =0; i<whiteTerritory.size(); i++){
//				for(Stone sW: whiteTerritory.get(i).stoneSet) {
//					if (sW == activeButton){
//						whiteTerritory.get(i).stoneSet.remove(sW);
//						terrSet = getTerritorySet(activeButton);  //search for territories
//						return;
//					}
//				}
//			}
//			
//			// white stone inside enemy'e territory, the territorySet will be lost !
//			for( int i =0; i<blackTerritory.size(); i++){
//				for(Stone sW: blackTerritory.get(i).stoneSet) {
//					if (sW == activeButton){
//						blackTerritory.remove(blackTerritory.get(i));
//						terrSet = getTerritorySet(activeButton);  //search for territories
//						return;
//					}
//				}
//			}
//		}
//	}
//	
//	
//	/**
//	 *  bilSet gets a stone and built its set.
//	 * @param actualStone
//	 */
//	public void bildSet(Stone actualStone) {
//		// first we determine the stone's neighbors,
//		// it doesn't matter if they are enemies's or not
//		//  they will be filtered in for-loop		
//		Stone[] neighbors = actualStone.getNeighbors(zoneGameButtons);
//		set.stoneSet.add(actualStone);
//		for (int i = 0; i < neighbors.length; i++) {
//			// we just add the stones that are the same as "stone"
//			if (neighbors[i] != null
//					&& neighbors[i].player == actualStone.player
//					&& !(set.stoneSet.contains(neighbors[i]))) {
//				bildSet(neighbors[i]);
//			}
//		}
//	}
//	
//
//	/**
//	 *  gets a move and give the list (Arraylist) if its enemies' sets back
//	 * @param stone
//	 * @return
//	 */
//	public ArrayList<Set> getEnemmies(Stone stone) {
//		Stone[] neighbors = stone.getNeighbors(zoneGameButtons);
//		for (int i = 0; i < neighbors.length; i++) {
//			if (neighbors[i] != null 
//					&& neighbors[i].player != null
//					&& neighbors[i].player != stone.player
//					&& !(set.stoneSet.contains(neighbors[i]))) {
//				bildSet(neighbors[i]);
//				enemiesSet.add(set);
//				set = new Set(); 
//			}
//		}
//		return enemiesSet;
//	}
//
//	
//	/**
//	 *  gets a move and give the list (Arraylist) of its own sets back
//	 * @param stone
//	 * @return
//	 */
//	public ArrayList<Set> getOwnSets(Stone stone) {
//		Stone[] neighbours = stone.getNeighbors(zoneGameButtons);
//		boolean isAllEnemies = true;	 // for particular case : if all the
//										// neighbors are enemies (or empty ??)
//										// then the stone itself is a set
//		for (int i = 0; i < neighbours.length; i++) {
//			if (neighbours[i] != null
//					&& neighbours[i].player == stone.player
//					&& !(set.stoneSet.contains(neighbours[i]))) {
//				isAllEnemies = false;
//				bildSet(neighbours[i]);
//				ownSet.add(set);
//				set = new Set();
//			}
//		}
//		
//		// the particular case : the stone is isolated or all its neighbors are enemies
//		// the bitch forms a set alone!
//		if (isAllEnemies) {
//			set.stoneSet.add(stone);
//			ownSet.add(set);
//			set = new Set();
//		}
//		return ownSet;
//	}
//	
//	
//	/**
//	 * 
//	 * search for the territories
//	 * @param stone
//	 * @return
//	 */
//	private ArrayList<Set> getTerritorySet(Stone stone) {
//		
//		Stone[] neighbours = stone.getNeighbors(zoneGameButtons);
//		for (int i = 0; i < neighbours.length; i++) {
//			if (neighbours[i] != null
//					&& !neighbours[i].taken
//					&& !(set.stoneSet.contains(neighbours[i]))) {	
//				out = false;
//				bildTerritorySet(neighbours[i],stone);
//
//				if ( set.stoneSet.size() > 0 ) {
//					if (stone.player == player1) {
//						blackTerritory.add(set);
//					}
//					else{
//						whiteTerritory.add(set);
//					}		
//				}
//				set = new Set();
//			}
//		}
//		return terrSet;
//	}
//	
//	/**
//	 * how many stones are there in "territory"
//	 * @param territory
//	 * @return
//	 */
//	public int getTerritorySize(ArrayList<Set> territory){
//		int terr = 0;
//		for (int i = 0; i< territory.size(); i++){
//				terr += territory.get(i).stoneSet.size();			
//			}
//		return terr;
//	}
//	
//	
//	/**
//	 * @param actualStone
//	 * @param caller
//	 */
//	public void bildTerritorySet(Stone actualStone, Stone caller) {
//		// first we determine the stone's neighbors,
//		// it MATTERS if they are enemies's or not
//		// if there is one enemy it's not a territory		
//		Stone[] neighbors = actualStone.getNeighbors(zoneGameButtons);
//		if (out) {
//			return;
//		}
//		set.stoneSet.add(actualStone);
//		// System.out.println(actualStone + " added to Set");
//		for (int i = 0; i < neighbors.length; i++) {
//			//if there is one enemy in the neighbors get out from this method
//			// we just add the stones that are the same as "stone" 
//			// & if we met another than caller we just break the set
//			if (neighbors[i] != null) {
//					if((neighbors[i].taken && neighbors[i].player != caller.player)
//							|| (isContains(neighbors[i], whiteTerritory))
//							|| (isContains(neighbors[i], blackTerritory))) {
//						set = new Set();
//						out = true;
//					}
//					else if( neighbors[i].taken == false
//							&& !(set.stoneSet.contains(neighbors[i]))){
//						bildTerritorySet(neighbors[i], caller);
//					}
//			}
//		}
//	}
//	
//	/**
//	 * returns true if "ArraySet" contains stone 
//	 * @param stone
//	 * @param set
//	 * @return
//	 */
//	public boolean isContains(Stone stone, ArrayList<Set> ArraySet){
//		for( int i =0; i<ArraySet.size(); i++){
//			for(Stone sB: ArraySet.get(i).stoneSet) {
//				if (sB == stone){
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	
//
//	/**
//	 *  a move is considered as suicide if it's leading to capture its own stones
//	 * @param move
//	 * @return
//	 */
//	public boolean isSuicide(Stone move) {
//		
//		/**
//		 * DEBUGGING
//	
//		System.out.println("\n\n### OWN SET  ####### ");
//		for (Set oS : ownSet) {
//			oS.liberties = oS.countLiberties();
//			System.out.println("w"+oS);
//		}
//		System.out.println("### END OWN SET  ####### ");
//
//	
//		 * END DEBUGGING
//		 */
//
//		for (Set oS : ownSet) {
//			oS.countLiberties();
//			if (oS != null && oS.getLiberties() == 0) {
//				GUI.infoMessage("This move is a suicide. Please play another Move or Pass ", " Suicide");
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	/**
//	 * tests if the move is a KO
//	 * lastCapture is updated in UpdateDisplay() if the last captured stone is just 1
//	 * @param move
//	 * @return
//	 */
//
//	public boolean isKO(Stone move){
//		if((ownSet.size() == 1) 
//				&& (ownSet.get(0).stoneSet.size() == 1)) {
//			if( ((player1.isCurrentPlayer && move == lastBlackCaptured && willCapturePrisonners(move) ) )
//					|| (player2.isCurrentPlayer && move == lastWhiteCaptured && willCapturePrisonners(move))){
//				GUI.infoMessage("This move is a KO. Please play another Move or Pass! ", "KO");
//				ko = true;
//			return true;		
//			}
//		}
//		ko = false;
//		return false;
//	}
//	
//	
//	/**
//	 *  is the move will capture prisoners from the enemie's stones
//	 * @param move
//	 * @return
//	 */
//	public boolean willCapturePrisonners(Stone move) {
//		for (Set eS : enemiesSet) {
//			eS.liberties = eS.countLiberties();
//			if (eS.liberties == 0) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//
//	/**
//	 *  update the board
//	 *  update lastCaptured stone to be used in isKO() next move
//	 * @param move
//	 */
//	public void updateDisplay(Stone move) {		
//		int numberOfPrisoners = 0;
//		Stone tmpCaptured = null;
//		for (Set eS : enemiesSet) {
//			eS.liberties = eS.countLiberties();
//			System.out.println(eS);
//			if (eS.liberties == 0) {
//				if(player1.isCurrentPlayer){
//					whitePrisoners += eS.stoneSet.size();
//				}
//				if(player2.isCurrentPlayer) {
//					blackPrisoners += eS.stoneSet.size();
//				}
//				
//				// remove prisoners from the board
//				for (Stone s : eS.stoneSet) {		
//					numberOfPrisoners++;
//					tmpCaptured = s;
//					lastPrisoners.stoneSet.add(s);
//					zoneGameButtons[s.x][s.y].setIcon(default_bg);
//					zoneGameButtons[s.x][s.y].taken = false;
//					zoneGameButtons[s.x][s.y].player = null;
//	
//				}
//			}
//		}
//		// For KO-case : update last captured stone if it is just one
//		if(numberOfPrisoners == 1){
//			if(player1.isCurrentPlayer){
//				lastWhiteCaptured = tmpCaptured;
//			}
//			else {
//				lastBlackCaptured = tmpCaptured;
//			}
//		}
//		else {
//			lastBlackCaptured = null;
//			lastWhiteCaptured = null;
//		}
//	}
//	
//}
