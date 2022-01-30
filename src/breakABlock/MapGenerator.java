package breakABlock;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.BasicStroke;


public class MapGenerator {
	private Block[][] blocks;
	
	MapGenerator(int rowNum, int colNum, int blockWidth, int blockHeight){
		blocks = new Block[rowNum][colNum];
		for(int i=0; i<rowNum; ++i) {
			for(int j =0; j<colNum; ++j) {
				blocks[i][j] = new Block();
				blocks[i][j].setRectangle(i * blockWidth + 80, j * blockHeight + 50,  blockWidth, blockHeight);
			}
		}	
		System.out.println(blockWidth + " " + blockHeight);
		//InitMap(totalBlocks, blockWidth, blockHeight);
	}
	
	public void initMap() {
		for(int i=0; i<blocks.length; ++i) {
			for(int j =0; j<blocks[i].length; ++j) {
				blocks[i][j].active = true;
			}
		}
	}
	
	public void drawMap(Graphics2D g) {
		for(int i=0; i<blocks.length; ++i) {
			for(int j =0; j<blocks[i].length; ++j) {
				if (blocks[i][j].active){
					blocks[i][j].draw(g);
				}
			}
		}
	}
	
	public Rectangle checkCollision(Rectangle ballHitbox) {
		for (int i = 0; i<blocks.length; ++i) {
			for(int j =0; j<blocks[i].length; ++j) {
				if (blocks[i][j].active) {
					if (ballHitbox.intersects(blocks[i][j].rect)) {
						blocks[i][j].active = false;
						return blocks[i][j].rect;
					}
				}
			}
		}
		return null;
	}
}


class Block{
	public boolean active = false;
	public Rectangle rect;
	
	public Block(){
		active = true;
	}
	public void setRectangle(int x, int y, int width, int height) {
		rect = new Rectangle(x, y, width, height);
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		
		g.setStroke(new BasicStroke(3));
		g.setColor(Color.black);
		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	
}