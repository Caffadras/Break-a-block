package breakABlock;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean isPlaying;
	private int score;
	
	private int totalBricks;
	
	private Timer timer; 
	private int delay; 
	
	private int playerX;
	private int ballPosX;
	private int ballPosY;
	private int ballXDir;
	private int ballYDir;
	
	Gameplay(){
		initGame();
		addKeyListener(this);
		setFocusable(true);
		//setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void initGame() {
		isPlaying = false;
		score = 0;
		totalBricks = 0;
		delay = 10;
		
		//ball and paddle positions and directions
		playerX = 310;
		ballPosX = 120;
		ballPosY = 350;
		ballXDir = -1;
		ballYDir = -2;
	}
	
	@Override
	public void paint(Graphics g) {
		//background 
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592); // magic numbers
		
		drawBorders(g);
		drawBallAndPaddle(g);
	}
	
	public void drawBorders(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(684, 0, 3, 592);
	}
	
	public void drawBallAndPaddle(Graphics g) {
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
				
		//ball
		g.setColor(Color.yellow);	
		g.fillOval(ballPosX, ballPosY, 20, 20);
	}
	
	@Override 
	public void actionPerformed(ActionEvent e) { 
		//timer.start();
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX < 579) {
				playerX += 20; 
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX > 20) {
				playerX -= 20;
			}
		}
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override 
	public void keyTyped(KeyEvent e) { }
}

