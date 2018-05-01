package SpaceImpact_package;


import java.awt.*;       // Using AWT's Graphics and Color
import java.awt.event.*; // Using AWT event classes and listener interfaces
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;    // Using Swing's components and containers



public class GraphicUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	

	//Space for GUI
	public Space GuiSpace;
	
	//Creating Control for GUI
	protected ShipControl GuiControl = new ShipControl();
	
	private NetClient GuiClient;
	private int Multiplayer = 0;
	
	
	public class DrawCanvas extends JPanel
	{
		private static final long serialVersionUID = 1L;


		private void paintGame(Graphics g)
		{
			
			//Draw myShip
			int myShipXpos = GuiSpace.myShipXpos;
			int myShipYpos = GuiSpace.myShipYpos;
			
			g.setColor(Color.RED);
	        int myxpoints[] = {myShipXpos, myShipXpos-10, myShipXpos-30, myShipXpos-100, myShipXpos-80, myShipXpos-100, myShipXpos-30, myShipXpos-10};
	        int myypoints[] = {myShipYpos, myShipYpos+10, myShipYpos+20,  myShipYpos+20, myShipYpos   ,  myShipYpos-20, myShipYpos-20, myShipYpos-10};
	        int mynpoints = 8;
	        g.fillPolygon(myxpoints, myypoints, mynpoints);
	        
	        g.setColor(Color.YELLOW);
	        g.fillArc(myShipXpos-30, myShipYpos-10, 10, 20, 0, 360);
	        
	        if(GuiSpace.myShipLaser == 1)
	        {
	        	g.setColor(Color.GREEN);
	        	g.drawLine(myShipXpos, myShipYpos, myShipXpos+500, myShipYpos);
	        }
	         
			
	        //Draw otherShip
			int ShipXpos = GuiSpace.otherShipXpos;
			int ShipYpos = GuiSpace.otherShipYpos;
				
			g.setColor(Color.BLUE);
		    int xpoints[] = {ShipXpos, ShipXpos-10, ShipXpos-30, ShipXpos-100, ShipXpos-80, ShipXpos-100, ShipXpos-30, ShipXpos-10};
		    int ypoints[] = {ShipYpos, ShipYpos+10, ShipYpos+20,  ShipYpos+20, ShipYpos   ,  ShipYpos-20, ShipYpos-20, ShipYpos-10};
		    int npoints = 8;
		    g.fillPolygon(xpoints, ypoints, npoints);
		         
		    g.setColor(Color.WHITE);
		    g.fillArc(ShipXpos-30, ShipYpos-10, 10, 20, 0, 360);
		    
	        if(GuiSpace.otherShipLaser == 1)
	        {
	        	g.setColor(Color.GREEN);
	        	g.drawLine(ShipXpos, ShipYpos, ShipXpos+500, ShipYpos);
	        }
	        
		    
	        //Draw alienShip
			for (int i=0; i < GuiSpace.Aliens.size(); i++)
			{
			    if( GuiSpace.Aliens.get(i).getAlienShipXpos() != -1 && GuiSpace.Aliens.get(i).getAlienShipYpos() != -1)
			    {
					int alienShipXpos = GuiSpace.Aliens.get(i).getAlienShipXpos();
					int alienShipYpos = GuiSpace.Aliens.get(i).getAlienShipYpos();
						
					g.setColor(Color.GREEN);
				    int alienxpoints[] = {alienShipXpos, alienShipXpos+10, alienShipXpos+30, alienShipXpos+100, alienShipXpos+80, alienShipXpos+100, alienShipXpos+30, alienShipXpos+10};
				    int alienypoints[] = {alienShipYpos, alienShipYpos+10, alienShipYpos+20,  alienShipYpos+20, alienShipYpos   ,  alienShipYpos-20, alienShipYpos-20, alienShipYpos-10};
				    int aliennpoints = 8;
				    g.fillPolygon(alienxpoints, alienypoints, aliennpoints);
				         
				    g.setColor(Color.WHITE);
				    g.fillArc(alienShipXpos+30, alienShipYpos-10, 10, 20, 0, 360);
				    
//			        if(GuiSpace.alienShipLaser == 1)
//			        {
//			        	g.setColor(Color.GREEN);
//			        	g.drawLine(alienShipXpos, alienShipYpos, alienShipXpos-500, alienShipYpos);
//			        }
			    }
			}
		    
		    //Draw meteor
			for (int i=0; i < GuiSpace.Meteors.size(); i++)
			{
				if( GuiSpace.Meteors.get(i).getMeteorXpos() != -1 && GuiSpace.Meteors.get(i).getMeteorYpos() != -1)
				{
					int meteorXpos = GuiSpace.Meteors.get(i).getMeteorXpos();
					int meteorYpos = GuiSpace.Meteors.get(i).getMeteorYpos();
	
					g.setColor(Color.WHITE);
					g.fillArc(meteorXpos, meteorYpos, GuiSpace.Meteors.get(i).getMeteorWidth(), GuiSpace.Meteors.get(i).getMeteorHeight(), 0, 360);
				}
			}

		}
		
	    //MainComponentPainter
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);     // paint parent's background
	        setBackground(Color.BLACK);  // set background to black
	        g.setColor(Color.WHITE);	 // set drawing color to white
	        
	        paintGame(g);
	        
		}
		
	    private class ScheduleTask extends TimerTask {

	        @Override
	        public void run() {
	            
	            repaint();
	            /*---  Magic command to faster graphic, but just under LINUX! ---*/
	            // Toolkit.getDefaultToolkit().sync();
	            
	            if(Multiplayer == 1)
	            {
	            	GuiClient.send(GuiControl);
	            }
	        }
	    }
	    
	    Timer MTimer      = new Timer();
   		long MainTimerDelay = 40; //0.04s = 1/25s
   		
   		
   		DrawCanvas()
   		{
   			MTimer.scheduleAtFixedRate( new ScheduleTask(), MainTimerDelay, MainTimerDelay);
   		}
   		
		
	}
	
	public static DrawCanvas canvas;
	
  	
	public class MyKeyListener implements KeyListener {
  		
		public void keyTyped(KeyEvent e) {
			
		}

		public void keyPressed(KeyEvent e) {
			//System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
			
			//Control Game
			if(KeyEvent.getKeyText(e.getKeyCode()) == "Down")
				GuiControl.ShipYdir =  3;
			if(KeyEvent.getKeyText(e.getKeyCode()) == "Up")
				GuiControl.ShipYdir = -3;
			if(KeyEvent.getKeyText(e.getKeyCode()) == "Left")
				GuiControl.ShipXdir = -3;
			if(KeyEvent.getKeyText(e.getKeyCode()) == "Right")
				GuiControl.ShipXdir =  3;
			
			if(KeyEvent.getKeyText(e.getKeyCode()) == "Space")
				GuiControl.ShipLaser = 1;

		}

		public void keyReleased(KeyEvent e) {
			//System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
			if(KeyEvent.getKeyText(e.getKeyCode()) == "Space")
				GuiControl.ShipLaser = -1;
		}
	}
	

	//Gui Thread starter
	public void GraphicUIStart(Space GameSpace)
	{		
		
		GuiSpace = GameSpace;  
		
		  //Build the Canvas
		  canvas = new DrawCanvas();
	      canvas.setPreferredSize(new Dimension(640, 480));
	      //--- !!! ---
	      canvas.setDoubleBuffered(true);
	 
	      //Pack the Canvas
	      Container cp = getContentPane();
	      cp.add(canvas);
	      
	      setDefaultCloseOperation(EXIT_ON_CLOSE);
	      pack();
	      setVisible(true);
	      
	      //Add key Listener
	      KeyListener listener = new MyKeyListener();
		  addKeyListener(listener);
		  setFocusable(true);
	}
	
	public void setGuiClient(NetClient ClientToReceive)
	{
		GuiClient = ClientToReceive;
		Multiplayer = 1;
	}

}
