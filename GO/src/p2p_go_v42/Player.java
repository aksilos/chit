package p2p_go_v42;
/**
 * p2p
 * SS11
 * A2
 * @Team  S4T3
 * @author Mahmoud Dariti, #1991840
 * @author Mohamed Sakhri, #1991287
 * @Aufnahme  von Herr Schaefers
 * 
 */
import javax.swing.ImageIcon;

public class Player {

	int score;
	int territory;
	int prisoners;
	ImageIcon playerIcon;
	boolean isCurrentPlayer;
	
	Player(){
		this.score = 0;
		this.territory = 0;
		this.prisoners = 0;
		this.isCurrentPlayer = false;		
	}
	
}
