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
import java.util.ArrayList;

public class Set {
	
	ArrayList<Stone> stoneSet;
	int liberties;  // set's liberties are the sum of stone's liberties 
	
	Set(){
		stoneSet = new ArrayList<Stone>();
		this.liberties = 0;
	}
	
	
	public int getLiberties(){
		return liberties;
	}
	
	public void setLiberties(int liberties){
		this.liberties = liberties;
	}
	
	public boolean isSurrounded(){
		return ( this.liberties == 0);
	}
	
	public int countLiberties(){
		this.liberties = 0;
		for(Stone s: stoneSet){
			this.liberties += s.liberties;
		}
		return this.liberties;
	}
	
	// for DEBUGGING
	public String toString(){
		String src = "";
		for(Stone s: stoneSet){
			src += s + "";
		}
		src += " SetLiberties: " + this.liberties +"\n###################";
		return src;
	}
}
