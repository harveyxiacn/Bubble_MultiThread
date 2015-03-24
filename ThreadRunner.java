//*****************************************************************************
//  ThreadRunner.java       Author: Ye Xia (ID: 121866)
//  C SC152 - Assignment #6 - Problem #1
//  Represents a main class to show the frame 
//*****************************************************************************
public class ThreadRunner{
	// main method
	public static void main (String[] args){
		// create a frame
 		BubbleFrame bf = new BubbleFrame();
 		bf.pack();
 		bf.setLocationRelativeTo(null);
 		// start the thread
 		bf.startBubbles();	
 	}//main method
}//class