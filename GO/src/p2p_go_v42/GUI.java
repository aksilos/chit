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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public class GUI {
	
	// it is better than anonyme class, because so it will be used
	// for each stone
	private class StoneListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Stone activeButton = (Stone) e.getSource();
			//control.move(activeButton, (Stone[][]) zoneGameButtons);
			ctr.move(activeButton, (Stone[][]) zoneGameButtons);
			
		}
	}
	
	
//	Control control = new Control();
	Ctr ctr = new Ctr();
	JFrame frame = new JFrame("GO Game");
	JPanel playerPanel = new JPanel();
	JPanel controlPanel = new JPanel();
	JPanel gameZonePanel = new JPanel();
	JPanel logoPanel = new JPanel();
	JPanel topPanel = new JPanel();
	JPanel buttomPanel = new JPanel();
	
	
//	JLabel scoreLabel = new JLabel(" Score");
//	JLabel territoryLabel = new JLabel(" Territory");
//	JLabel prisonersLabel = new JLabel(" Prisoners");
//	static JLabel territoryBScore = new JLabel("0", JLabel.CENTER);
//	static JLabel prisonersBScore = new JLabel("0", JLabel.CENTER);
//	static JLabel territoryWScore = new JLabel("0", JLabel.CENTER);
//	static JLabel prisonersWScore = new JLabel("0", JLabel.CENTER);
	
	
	JLabel activePlayer = new JLabel("Who is on : ");
	static JLabel activePlayerIcon = new JLabel(new ImageIcon("src/black_logo.png"));
	JLabel logoLabel = new JLabel(new ImageIcon("src/logo.png"));
	JLabel player1Label = new JLabel(new ImageIcon("src/black_logo.png"));
	static JLabel player1Score = new JLabel("0", JLabel.CENTER);
	JLabel player2Label = new JLabel(new ImageIcon("src/white_logo.png"));
	static JLabel player2Score = new JLabel("0", JLabel.CENTER);
	static JButton foqButton = new JButton("ifoq");
	static JButton jbadButton = new JButton("ijbad");
	
	JButton passButton = new JButton(new ImageIcon("src/pass.png"));
	JButton undoButton = new JButton(new ImageIcon("src/undo.png"));
	JButton resetButton = new JButton(new ImageIcon("src/reset.png"));
	
	ImageIcon defautlt_bg = new ImageIcon("src/default_BG_back.png");
	
	JButton[][] zoneGameButtons = new Stone[5][5];
	
	GUI(){
		Border r;
		r= BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		
		playerPanel.setLayout(new GridLayout(4, 1));
		playerPanel.setPreferredSize(new Dimension(200, 200));
		playerPanel.setBorder(r);
//		playerPanel.add(new JLabel("dd"));
		playerPanel.add(foqButton);
		playerPanel.add(jbadButton);
//		playerPanel.add(player1Label);
//		playerPanel.add(player2Label);
//		playerPanel.add(territoryLabel);
//		playerPanel.add(territoryBScore);
//		playerPanel.add(territoryWScore);
//		playerPanel.add(prisonersLabel);
//		playerPanel.add(prisonersWScore);
//		playerPanel.add(prisonersBScore);
//		playerPanel.add(scoreLabel);
//		playerPanel.add(player1Score);
//		playerPanel.add(player2Score);

		gameZonePanel.setLayout(new GridLayout(5,5));
		bildZoneGame();		
		gameZonePanel.setBorder(r);
		controlPanel.add(resetButton);
		controlPanel.add(passButton);
		controlPanel.add(undoButton);
		controlPanel.add(playerPanel);
		
		logoPanel.add(logoLabel);
		
		foqButton.setEnabled(false);
		foqButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ctr.setifoq();
			}
		});
		jbadButton.setEnabled(false);
		jbadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ctr.setijbad();
			}
		});

		buttomPanel.setBorder(r);
		buttomPanel.add(activePlayer);
		buttomPanel.add(activePlayerIcon);
		
		
		
//		passButton.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				// TODO Auto-generated method stub
//				control.pass();
//			}
//		});
//	
//		undoButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				control.undo();
//			}
//		});
		
//		resetButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				frame.dispose();
//				territoryBScore.setText("0");
//				territoryWScore.setText("0");
//				prisonersBScore.setText("0");
//				prisonersWScore.setText("0");
//				player1Score.setText("0");
//				player2Score.setText("0");
//				new GUI();
//			}
//		});
		
		// add component to topPanel
		topPanel.setBorder(r);
		topPanel.setLayout(new BorderLayout());
		topPanel.add(logoPanel, BorderLayout.NORTH);
		topPanel.add(controlPanel, BorderLayout.WEST);

		// add panels to frame's content-pane
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		frame.getContentPane().add(playerPanel, BorderLayout.EAST);
		frame.getContentPane().add(gameZonePanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttomPanel, BorderLayout.SOUTH);
		frame.setSize(600,490);
//		frame.setResizable(false);
//		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	
	/**
	 * creates the 81 stone and puts them in a panel
	 */
	public void bildZoneGame(){
//		ImageIcon def_img =new ImageIcon(defautlt_bg.getImage().getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH));
				for ( int j = 0; j < 5; j++) {
				for (int i = 0; i<5; i++) {
					 zoneGameButtons[i][j] = new Stone(i,j,defautlt_bg );
//					 zoneGameButtons[i][j].setBorderPainted(false);
					 zoneGameButtons[i][j].setOpaque(false); 
					 zoneGameButtons[i][j].setContentAreaFilled(false);
					 zoneGameButtons[i][j].setName(""+i+""+j);
					 zoneGameButtons[i][j].addActionListener(new StoneListener());
					 gameZonePanel.add(zoneGameButtons[i][j]);
				}
			} 					
	}
	
	/**
	 * Update the scores of each player
	 * @param blackT
	 * @param prisonersB
	 * @param whiteT
	 * @param prisonersW
	 * @param bScore
	 * @param wScore
	 */
//	public static void updateScore(int blackT, int prisonersB, int whiteT, int prisonersW, int bScore, int wScore) {
//		territoryBScore.setText(Integer.toString(whiteT));
//		territoryWScore.setText(Integer.toString(blackT));
//		prisonersBScore.setText(Integer.toString(prisonersB));
//		prisonersWScore.setText(Integer.toString(prisonersW));
//		player1Score.setText(Integer.toString(bScore));
//		player2Score.setText(Integer.toString(wScore));
//	}
	
	/**
	 * enable ifoq and ijbad Buttons
	 */
	public static void enableButton(String button){
		switch (button) {
		case "all":
			foqButton.setEnabled(true);
			jbadButton.setEnabled(true);
			break;
		case "ijbad":
			jbadButton.setEnabled(true);
			break;
		case "ifoq":
			foqButton.setEnabled(true);
			break;
		default:
			break;
		}
	}
	/**
	 * disble ijbad and ifoq buttons
	 */
	public static void disableButtons(){
		foqButton.setEnabled(false);
		jbadButton.setEnabled(false);
	}
	
	/**
	 * set the icon of active player 
	 * @param playerIcon
	 */
	public static void updateActivePlayer(ImageIcon playerIcon){
		activePlayerIcon.setIcon(playerIcon) ;
	}
	
	/**
	 * display the appropriate message if something goes "wrong"
	 * @param message : the message to display 
	 * @param titel : the window's title
	 */
	public static void infoMessage(String message, String title){
		JOptionPane.showMessageDialog(null, message, title,JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void reset(){
		new GUI();

	}
		
}
