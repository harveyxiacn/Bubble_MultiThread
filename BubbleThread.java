//*****************************************************************************
//  BubbleThread.java       Author: Ye Xia (ID: 121866)
//  C SC152 - Assignment #6 - Problem #1
//  Represents a bubble thread 
//*****************************************************************************
 
 public class BubbleThread extends Thread
 {
 	// declare variables
 	private int delay = 0;
 	private int bubbleID = 0;
 	private BubbleFrame parent = null;
 	private boolean running = true;
 	//-----------------------------------------------------------------
    //  Constructor: Sets up the thread
    //-----------------------------------------------------------------
 	public BubbleThread(int delay, int ID, BubbleFrame bf)
 	{
 		super();
 		this.delay = delay;
 		bubbleID = ID;
 		parent = bf;
 	}
 	// run the thread
 	public void run()
 	{
 		while (true)
 		{
 			// sleep for a bit
 			try 
 			{
 				sleep(delay);
 			}
 			catch (InterruptedException ie)
 			{
 				System.exit(1);
 			}
 			// trigger a change in the appropriate bubble
 			if (running)
 				parent.ChangeBubble(bubbleID);
 			
 		}
 		
 	}
 	// set delay
 	public void setDelay(int delay){
 		this.delay = delay;
 	}
 	// get delay
 	public String getDelay(){
 		String result = "";
 		result += delay;
 		return result;
 	}
 	// change the status
 	public boolean holdToggle()
 	{
 		running = !running;
 		return running;
 	}// holdToggle method
 }//class
 		