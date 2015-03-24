//*****************************************************************************
//  BubbleFrame.java       Author: Ye Xia (ID: 121866)
//  C SC152 - Assignment #6 - Problem #1
//  Represents a frame that consists of panels to show the bubbles 
//*****************************************************************************
 
 import javax.swing.*;
 import java.awt.*;
 import java.awt.event.*;
 import java.io.*;
 import java.util.*;
 
 public class BubbleFrame extends JFrame
 {
 	// declare variables
 	private JPanel[] bubblePanel = new JPanel[4];
 	private int[] bubbleVal = new int[4];
 	private BubbleThread[] bubbleThread = new BubbleThread[4];
 	private int[] minDiameter = new int[4];
 	private int[] maxDiameter = new int[4];
 	private Random random = new Random();
 	private JButton run, stop;
 	private JButton[] runButton = new JButton[4];
 	private JButton[] stopButton = new JButton[4];
 	private boolean[] isMax = new boolean[4];
 	private boolean[] isDraw = new boolean[4];
 	private JLabel[] delay = new JLabel[4];
 	private JTextField[] delayText = new JTextField[4];
 	private JButton[] delayButton = new JButton[4];
 	private JPanel[] delayPanel = new JPanel[4];
 	private JButton[] randomButton = new JButton[4];
 	private JLabel[] randomLabel = new JLabel[4];
 	//-----------------------------------------------------------------
    //  Constructor: Sets up the frame
    //-----------------------------------------------------------------
 	public BubbleFrame()
 	{
 		super();
 		this.setTitle("Breathing Bubble");
 		this.setVisible(true);
 		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
 		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set up bubble display elements
 		
 		// add buttons and set the step of adding
 		for (int x = 0; x < 4; x++)
 		{
 			// panels
 			bubblePanel[x] = MakeBubblePanel();
 			// threads
 			bubbleThread[x] = new BubbleThread(((x+1)*100),x,this);
 			// random the min diameter
 			minDiameter[x] = random.nextInt(20) + 10;
 			// random the max diameter
 			maxDiameter[x] = random.nextInt(100) + 50;
 			// initialize the bubbleVal
 			bubbleVal[x] = 0;
 			isMax[x] = false;
 			isDraw[x] = false;
 			// set up dalay panel
 			randomLabel[x] = new JLabel("Random the Diameters:");
 			randomButton[x] = new JButton("Random!");
 			randomButton[x].addActionListener(new RandomListener(bubbleThread[x]));
 			delay[x] = new JLabel("Set Delay");
 			delayText[x] = new JTextField(bubbleThread[x].getDelay(), 5);
 			delayButton[x] = new JButton("confirm");
 			delayButton[x].addActionListener(new DelayListener(bubbleThread[x]));
 			delayPanel[x] = new JPanel();
 			delayPanel[x].setBackground(Color.white);
 			delayPanel[x].setPreferredSize(new Dimension(600, 50));
 			delayPanel[x].add(randomLabel[x]);
 			delayPanel[x].add(randomButton[x]);
 			delayPanel[x].add(delay[x]);
 			delayPanel[x].add(delayText[x]);
 			delayPanel[x].add(delayButton[x]);
 			bubblePanel[x].add(delayPanel[x]);
 		}
 		// run buttons
 		runButton[0] = MakeRunButton("One", bubbleThread[0]);
 		runButton[1] = MakeRunButton("Two", bubbleThread[1]);
 		runButton[2] = MakeRunButton("Three", bubbleThread[2]);
 		runButton[3] = MakeRunButton("Four", bubbleThread[3]);
 		// stop buttons
 		stopButton[0] = MakeStopButton("One", bubbleThread[0]);
 		stopButton[1] = MakeStopButton("Two", bubbleThread[1]);
 		stopButton[2] = MakeStopButton("Three", bubbleThread[2]);
 		stopButton[3] = MakeStopButton("Four", bubbleThread[3]);
 		for(int x = 0; x < 4; x ++){
 			// add the control buttons
 			bubblePanel[x].add(runButton[x]);
 			bubblePanel[x].add(stopButton[x]);
 		}
 		this.setLayout(new GridLayout(2,2));
 		// add panels to the frame
 		this.getContentPane().add(bubblePanel[0]);
 		this.getContentPane().add(bubblePanel[1]);
 		this.getContentPane().add(bubblePanel[2]);
 		this.getContentPane().add(bubblePanel[3]); 		
 	}
 	// start the threads 
 	public void startBubbles()
 	{
 		for (int x = 0; x < 4; x++)
 			bubbleThread[x].start();
 	}
	// make the run button
	private JButton MakeRunButton(String title, BubbleThread bt)
	{
		run = new JButton(title + " RUN ");
		run.setEnabled(false);
		run.addActionListener(new ButtonListener(bt));
		return run;
	}
	// make the stop button
	private JButton MakeStopButton(String title, BubbleThread bt)
	{
		stop = new JButton(title + " STOP ");
		stop.addActionListener(new ButtonListener(bt));
		return stop;
	}
	// make the bubble panel
	private JPanel MakeBubblePanel()
	{
		JPanel s = new JPanel();
		s.setBackground(Color.white);
		s.setPreferredSize(new Dimension(600,350));
 		return s;
 	}
 	// change the bubble like breathing
 	public void ChangeBubble(int bubbleNum)
 	{
 		// get graphics area
 		Graphics g = bubblePanel[bubbleNum].getGraphics();
 		Color bc = bubblePanel[bubbleNum].getBackground();
 		// undraw current bubble and the string
 		g.setColor(bc);
 		isDraw[bubbleNum] = false;
 		DrawOval(g, bubbleVal[bubbleNum], bubbleNum, isMax[bubbleNum], isDraw[bubbleNum]);
 		//undraw(bubbleNum);
 		// if current diameter is equal the max diameter, the bubble shrinks
 		if(!isMax[bubbleNum] && minDiameter[bubbleNum]+bubbleVal[bubbleNum]==maxDiameter[bubbleNum]){
 			bubbleVal[bubbleNum] = 0;
 			isMax[bubbleNum] = true;
 		}
 		// if current diameter is equal the min diameter, the bubble grows
 		if(isMax[bubbleNum] && maxDiameter[bubbleNum]-bubbleVal[bubbleNum]==minDiameter[bubbleNum]){
 			bubbleVal[bubbleNum] = 0;
 			isMax[bubbleNum] = false;
 		}	
 		// increase/decrease the diameter step by step
 		bubbleVal[bubbleNum] ++;	
 		//draw(bubbleNum);
 		// draw new bubble and the string
 		// random the color and avoid the white color
 		Color color = new Color(random.nextInt(255)+1,random.nextInt(255)+1,random.nextInt(255)+1);
 		g.setColor(color);
 		isDraw[bubbleNum] = true;
 		DrawOval(g, bubbleVal[bubbleNum], bubbleNum, isMax[bubbleNum], isDraw[bubbleNum]);
 	}
 	public void undraw(int bubbleNum){
 		// get graphics area
 		Graphics g = bubblePanel[bubbleNum].getGraphics();
 		Color bc = bubblePanel[bubbleNum].getBackground();
 		// undraw current bubble and the string
 		g.setColor(bc);
 		isDraw[bubbleNum] = false;
 		DrawOval(g, bubbleVal[bubbleNum], bubbleNum, isMax[bubbleNum], isDraw[bubbleNum]);
 	}
 	// draw the bubble and the string
 	private void DrawOval(Graphics page, int phase, int number, boolean isMax, boolean isDraw)
 	{
 		if(!isMax){
 			page.fillOval((bubblePanel[number].getWidth() - (minDiameter[number]+phase))/2, (bubblePanel[number].getHeight() - (minDiameter[number]+phase))/2, minDiameter[number]+phase, minDiameter[number]+phase);
 			if(isDraw)
 				page.setColor(Color.black);
 			page.drawString("MinDiameter:"+minDiameter[number]+" MaxDiameter:"+maxDiameter[number]+" Current Diameter:"+(minDiameter[number]+bubbleVal[number]), bubblePanel[number].getWidth()/4 , bubblePanel[number].getHeight() - 50);
 		}	
 		else if(isMax){
 			page.fillOval((bubblePanel[number].getWidth() - (maxDiameter[number]-phase))/2, (bubblePanel[number].getHeight() - (maxDiameter[number]-phase))/2, maxDiameter[number]-phase, maxDiameter[number]-phase);
 			if(isDraw)
 				page.setColor(Color.black);
 			page.drawString("MinDiameter:"+minDiameter[number]+" MaxDiameter:"+maxDiameter[number]+" Current Diameter:"+(maxDiameter[number]-bubbleVal[number]), bubblePanel[number].getWidth()/4, bubblePanel[number].getHeight() - 50);
 		}		
 	}
 	//-----------------------------------------------------------------------
    // a private class for listening to the buttons
    //-----------------------------------------------------------------------
 	private class ButtonListener implements ActionListener
 	{
 		// declare variables
 		private BubbleThread bubbleThread;
 		//-----------------------------------------------------------------
    	//  Constructor: Sets up the button listener
    	//-----------------------------------------------------------------
 		public ButtonListener (BubbleThread bt)
 		{
 			bubbleThread = bt;
 		}
 		// decided to do which action perform by which button is clicked
 		public void actionPerformed (ActionEvent e)
 		{
 			for(int i = 0; i < runButton.length; i ++){
 				if(e.getSource() == runButton[i]||e.getSource() == stopButton[i]){
 					// while the run button is clicked
 					if(bubbleThread.holdToggle()){
 						stopButton[i].setEnabled(true);
 						runButton[i].setEnabled(false);
 					}else{
 						stopButton[i].setEnabled(false);
 						runButton[i].setEnabled(true);
 					}// else
 				}//if the button is clicked
 			}// for loop
 		}// actionPerformed method
 	}// buttonListener class
 	//-----------------------------------------------------------------------
    // a private class for listening to the delay button
    //-----------------------------------------------------------------------
 	private class DelayListener implements ActionListener
 	{
 		// declare variables
 		private BubbleThread bubbleThread;
 		//-----------------------------------------------------------------
    	//  Constructor: Sets up the button listener
    	//-----------------------------------------------------------------
 		public DelayListener (BubbleThread bt)
 		{
 			bubbleThread = bt;
 		}
 		// decided to do which action perform by which button is clicked
 		public void actionPerformed (ActionEvent e)
 		{
 			for(int i = 0; i < delayButton.length; i ++){
 				if(e.getSource() == delayButton[i]){
 					bubbleThread.setDelay(Integer.parseInt(delayText[i].getText()));
 				}// if button is clicked
 			}// for loop
 		}// actionPerformed method
 	}// DelayListener class
 	//-----------------------------------------------------------------------
    // a private class for listening to the delay button
    //-----------------------------------------------------------------------
 	private class RandomListener implements ActionListener
 	{
 		// declare variables
 		private BubbleThread bubbleThread;
 		//-----------------------------------------------------------------
    	//  Constructor: Sets up the button listener
    	//-----------------------------------------------------------------
 		public RandomListener (BubbleThread bt)
 		{
 			bubbleThread = bt;
 		}
 		// decided to do which action perform by which button is clicked
 		public void actionPerformed (ActionEvent e)
 		{
 			for(int i = 0; i < randomButton.length; i ++){
 				if(e.getSource() == randomButton[i]){
 					undraw(i);
 					// random the min diameter
 					minDiameter[i] = random.nextInt(20) + 10;
 					// random the max diameter
 					maxDiameter[i] = random.nextInt(100) + 50;
 					isMax[i] = false;
 					bubbleVal[i] = 0;
 					isDraw[i] = false;
 				}// if button is clicked
 			}// for loop
 		}// actionPerformed method
 	}// RandomListener class
 }// BubbleFrame class