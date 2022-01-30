package breakABlock;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.KeyListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Font;
import java.util.Random;


public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean isPlaying;
	private boolean hasLost;
	private int score;
	
	private int appWidth; 
	private int appHeight;
	
	MapGenerator map;
	private int totalBlocks;
	
	private Timer timer; 
	private int timerDelay; 
	
	private int paddleLength;
	private int playerX;
	private int ballPosX;
	private int ballPosY;
	private int ballDirX;
	private int ballDirY;
	
	Gameplay(int width, int height){
		appWidth = width;
		appHeight = height;
		map = new MapGenerator(7, 2, 75, 75);
		initGame();
		addKeyListener(this);
		setFocusable(true);
		//setFocusTraversalKeysEnabled(false);
		
		timer = new Timer(timerDelay, this);
		timer.start();
	}
	
	public void initGame() {
		Random rand = new Random();
		
		isPlaying = false;
		hasLost = false;
		score = 0;
		timerDelay= 10;

		map.initMap();
		totalBlocks = 14;
		
		//ball and paddle positions and directions
		paddleLength = 100;
		playerX = appWidth / 2 - paddleLength;
		ballPosX = appWidth / 2 - 20;
		ballPosY = appHeight / 2;
		ballDirX = -2 * (rand.nextBoolean()? 1 : -1); //random direction on x-axis
		ballDirY = -3;
	}
	
	@Override
	public void paint(Graphics g) {
		//background 
		g.setColor(Color.black);
		g.fillRect(0, 0, appWidth, appHeight); 
		
		drawBorders(g);
		drawBallAndPaddle(g);
		map.drawMap((Graphics2D)g);
		
		//score 
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, appWidth -80, 40);
		
		//loss screen
		if(hasLost) {
			g.setColor(Color.red);
			g.drawString("Game over, Score: " + score, appWidth/3, appHeight/2);
			g.drawString("Press Enter to restart.", appWidth/3, appHeight/2+50);
		}
	}
	
	private void drawBorders(Graphics g) {
		int borderWidth = 3; 
		g.setColor(Color.yellow);
		g.fillRect(0, 0, borderWidth, appHeight);	//left
		g.fillRect(0, 0, appWidth, borderWidth);	//top
		g.fillRect(appWidth - 16, 0, borderWidth, appHeight);	//right
	}
	
	private void drawBallAndPaddle(Graphics g) {
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
		
		//Move the ball
		ballPosX += ballDirX; 
		ballPosY += ballDirY;
		
		//check loss condition
		if(ballPosY > appHeight - 20) {
			isPlaying = false;
			hasLost = true;
			ballDirX = 0; 
			ballDirY = 0; 	
		}
		
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
		Rectangle ballHitbox = new Rectangle(ballPosX, ballPosY, 20, 20);
		if (ballHitbox.intersects(
				new Rectangle(playerX, appHeight - 50, paddleLength, 8))) {
			ballDirY = -ballDirY;
			ballPosY = appHeight - 50-20;
			if (ballHitbox.intersects(new Rectangle(playerX-20, appHeight - 50, 8, 8))) {
				ballDirX = -ballDirX;
			}
			if (ballHitbox.intersects(new Rectangle(playerX+paddleLength, appHeight - 50, 8, 8))) {
				ballDirX = -ballDirX;
			}
			
		}
		
		//check collision with blocks
		Rectangle obstacle  = map.checkCollision(ballHitbox);
		if (obstacle != null) {
			score += 5;
			--totalBlocks;
			if (ballPosX + 19 <= obstacle.x || ballPosX + 1 >=obstacle.x + obstacle.width) {
				ballDirX = -ballDirX;
			}
			else ballDirY = -ballDirY;
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		isPlaying = true;       //any button press starts the game
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX + paddleLength < appWidth-20) { //check if paddle exceeds right border
				playerX += 20; 
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_LEFT) { //check if paddle exceeds left border
			if (playerX > 20) {
				playerX -= 20;
			}
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (hasLost) initGame();
		}
	}
	
	
	@Override
	public void keyReleased(KeyEvent e) { }
	
	@Override 
	public void keyTyped(KeyEvent e) { }
}

