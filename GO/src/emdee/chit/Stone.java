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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/*
 * represents one Stone
 * 
 */
 
public class Stone extends JButton implements ActionListener{

	private static final long serialVersionUID = 1L;
		int x;
		int y;
		int liberties; 
		
		boolean isyiss = false;
		boolean taken;
		boolean ifoq = false;
		boolean ijbad = true; 
		
		boolean isCorner = false;  
		boolean isEdge = false;
	
		Player player;  
		Stone[] neighbors; 
		
		Stone(){
			isyiss = false;
		}
		
		Stone (int x, int y, ImageIcon img){
			super(img);
			this.x = x;
			this.y = y;
			this.taken = false;
			this.neighbors = new Stone[4];
		}
		
		public int getLiberties(){
			return this.liberties;
		}
		
		public void setLiberties(int liberties){
			this.liberties = liberties;
		}
		
		/**
		 * return a stone' neighbors and update its liberties 
		 * @param stoneField 
		 * @return
		 */
		
		public Stone[] getNeighbors(Stone[][] stoneField){
			this.liberties = 4;
			if (this.x == 0 || this.x == 8){
				this.isEdge = true;
				this.liberties--;
				if (this.x == 0 ){
					if((stoneField[this.x + 1][this.y].taken)){
						this.liberties--;
						neighbors[3] =  stoneField[this.x + 1][this.y]; 
					}
				}
				else {
					if((stoneField[this.x - 1][this.y].taken)){
						this.liberties--;
						neighbors[1] =  stoneField[this.x - 1][this.y]; 

					}
				}
				
				
				if(this.y == 0){
					this.isCorner = true;
					this.liberties --;
					this.isEdge = false;
					if((stoneField[this.x][this.y+1].taken)){
						this.liberties--;
						neighbors[2] =  stoneField[this.x][this.y+1]; 

					}					
				}
				else if(this.y == 8){	
					this.isCorner = true;
					this.liberties --;
					this.isEdge = false;
					if((stoneField[this.x][this.y-1].taken)){
						this.liberties--;
						neighbors[0] =  stoneField[this.x][this.y -1]; 

					}
				}
				else {				
					if((stoneField[this.x][this.y-1].taken)){
						this.liberties--;
						neighbors[0] =  stoneField[this.x][this.y -1]; 

					}
					if((stoneField[this.x][this.y+1].taken)){
						this.liberties--;
						neighbors[2] =  stoneField[this.x][this.y+1]; 

					}
				}
			}
			
			else if( this.y == 0){
				this.isEdge = true;
				this.liberties--;
				if((stoneField[this.x +1][this.y].taken)){
					this.liberties--;
					neighbors[3] =  stoneField[this.x + 1][this.y]; 

				}
				if((stoneField[this.x-1][this.y].taken)){
					this.liberties--;
					neighbors[1] =  stoneField[this.x - 1][this.y]; 

				}
				if((stoneField[this.x][this.y+1].taken)){
					this.liberties--;
					neighbors[2] =  stoneField[this.x][this.y+1]; 

				}
			}
			else if( this.y == 8){
				this.isEdge = true;
				this.liberties--;
				if((stoneField[this.x +1][this.y].taken)){
					this.liberties--;
					neighbors[3] =  stoneField[this.x + 1][this.y]; 

				}
				if((stoneField[this.x-1][this.y].taken)){
					this.liberties--;
					neighbors[1] =  stoneField[this.x - 1][this.y]; 

				}
				if((stoneField[this.x][this.y-1].taken)){
					this.liberties--;
					neighbors[0] =  stoneField[this.x][this.y-1]; 

				}
			}
			else {
				if((stoneField[this.x +1][this.y].taken)){
					this.liberties--;
					neighbors[3] =  stoneField[this.x + 1][this.y]; 

				}
				if((stoneField[this.x-1][this.y].taken)){
					this.liberties--;
					neighbors[1] =  stoneField[this.x - 1][this.y]; 

				}
				if((stoneField[this.x][this.y+1].taken)){
					this.liberties--;
					neighbors[2] =  stoneField[this.x][this.y+1]; 

				}
				if((stoneField[this.x][this.y-1].taken)){
					this.liberties--;
					neighbors[0] =  stoneField[this.x][this.y-1]; 

				}
			}
			if (this.y > 0 && neighbors[0] == null){
				neighbors[0] = stoneField[this.x][this.y-1]; 
			}
			if (this.x > 0 && neighbors[1] == null){
				neighbors[1] = stoneField[this.x-1][this.y]; 
			}
			if (this.y < 8 && neighbors[2] == null){
				neighbors[2] = stoneField[this.x][this.y+1]; 
			}
			if (this.x < 8 && neighbors[3] == null){
				neighbors[3] = stoneField[this.x+1][this.y]; 
			}
			
			return neighbors;
		}

	
		public String toString(){
			String src = "";
			src ="x: "+ this.x +" y:"+ this.y + " Liberties :" + this.liberties +"\n"; 
			return src;
		}

		public void actionPerformed(ActionEvent arg0) {
			
		}

}