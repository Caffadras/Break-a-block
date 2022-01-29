package breakABlock;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean isPlaying;
	private int score;
	
	private int appWidth; 
	private int appHeight;
	
	private int totalBricks;
	
	private Timer timer; 
	private int delay; 
	
	private int paddleLength;
	private int playerX;
	private int ballPosX;
	private int ballPosY;
	private int ballDirX;
	private int ballDirY;
	
	Gameplay(int width, int height){
		appWidth = width;
		appHeight = height;
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
		paddleLength = 100;
		playerX = appWidth / 2 - paddleLength;
		ballPosX = 120;
		ballPosY = 350;
		ballDirX = -2;
		ballDirY = -3;
	}
	
	@Override
	public void paint(Graphics g) {
		//background 
		g.setColor(Color.black);
		g.fillRect(0, 0, appWidth, appHeight); 
		
		drawBorders(g);
		drawBallAndPaddle(g);
	}
	
	public void drawBorders(Graphics g) {
		int borderWidth = 3; 
		g.setColor(Color.yellow);
		g.fillRect(0, 0, borderWidth, appHeight);	//left
		g.fillRect(0, 0, appWidth, borderWidth);	//top
		g.fillRect(appWidth - 16, 0, borderWidth, appHeight);	//right
	}
	
	public void drawBallAndPaddle(Graphics g) {
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, appHeight - 50, paddleLength, 8);
				
		//ball
		g.setColor(Color.yellow);	
		g.fillOval(ballPosX, ballPosY, 20, 20);
	}
	
	@Override 
	public void actionPerformed(ActionEvent e) { 
		//timer.start();
		ballTick();
		repaint();
	}
	
	private void ballTick() {
		if (!isPlaying) return;
		
		ballPosX += ballDirX; 
		ballPosY += ballDirY;
		
		//Check border collision
		if (ballPosX <= 0) {
			ballDirX = -ballDirX;
		}
		if(ballPosY <= 0) {
			ballDirY = -ballDirY;
		}
		if (ballPosX > appWidth-40) {
			ballDirX = -ballDirX;
		}
	
		//Check paddle collision	
		Rectangle ballHitBox = new Rectangle(ballPosX, ballPosY, 20, 20);
		if (ballHitBox.intersects(
				new Rectangle(playerX, appHeight - 50, paddleLength, 8))) {
			ballDirY = -ballDirY;
			ballPosY = appHeight - 50-20;
			if (ballHitBox.intersects(new Rectangle(playerX-20, appHeight - 50, 8, 8))) {
				ballDirX = -ballDirX;
			}
			if (ballHitBox.intersects(new Rectangle(playerX+paddleLength, appHeight - 50, 8, 8))) {
				ballDirX = -ballDirX;
			}
			
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		isPlaying = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX + paddleLength < appWidth-20) {
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

