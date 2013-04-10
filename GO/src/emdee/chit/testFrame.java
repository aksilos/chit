package emdee.chit;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class testFrame {

	/** 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		new GUI();				
	}

}
 