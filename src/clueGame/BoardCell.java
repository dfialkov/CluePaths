//Authors: Daniel Fialkov and Darian Dickerson
package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Map;

public class BoardCell {
	private int row;
	private int col;
	private String initial;
	private Map<Character, String> legend;
	private Color currColor;
	private Color originalColor; 
	private Color outline;
	private int topLeftX;
	private int topLeftY;
	
	public BoardCell containsClick(int mouseX, int mouseY) {
		Rectangle rect = new Rectangle(topLeftX, topLeftY, 25, 25);
		if(rect.contains(new Point(mouseX, mouseY))) {
			return this;
		}
		else {
			return null;
		}

	}
	public BoardCell(int newRow, int newCol, String newInitial) {
		row = newRow;
		col = newCol;
		initial = newInitial;
		calcColor();
		topLeftX = col * 25;
		topLeftY = row * 25;
	}

	public boolean isWalkway() {
			if(initial.charAt(0) == 'W') {
				return true;
			}
			return false;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	public boolean isDoorway() {
		if(initial.length() == 2 && initial.charAt(1) != 'N') {
			return true;
		}
		return false;
	}

	public DoorDirection getDoorDirection() {
		DoorDirection direction = DoorDirection.NONE;
		if(initial.length() < 2) {
			return direction;
		}
		
		char dir = initial.charAt(1);
		
		switch(dir) {
			case 'R':
				direction = DoorDirection.RIGHT;
				break;
			case 'L':
				direction = DoorDirection.LEFT;
				break;
			case 'U':
				direction = DoorDirection.UP;
				break;
			case 'D':
				direction = DoorDirection.DOWN;
				break;
			default:
				break;
		}
		
		return direction;
	}

	public char getInitial() {
		return initial.charAt(0);
	}
	public Color getColor() {
		return currColor;
	}
	public void highlight() {
		currColor = Color.cyan;
	}
	//De-highlight cells
	public void resetColor() {
		currColor = originalColor;
	}
	public void calcColor() {
		//fill becomes current color. Outline is public so it doesn't change. 
		if(initial.charAt(0) == 'W') {
			originalColor = Color.yellow;
			outline = Color.black;
			
		}else {
			originalColor = Color.gray;
			outline = Color.gray;
		}
		resetColor();
	}
	
	public void draw(Graphics g) {
		
		g.setColor(currColor);
		g.fillRect(col*25 , row*25 , 25, 25);
		g.setColor(outline);
		g.drawRect(col*25 , row*25 , 25, 25);
		
		if(initial.length() == 2 && initial.charAt(1) != 'N') {
			int height = 3; 
			int width = 3;
			int x = col;
			int y = row;
		
			switch(initial.charAt(1)) {
				case 'U':
					height = 5;
					width = 25;
					break;
				case 'D':
					height = 5;
					width = 25;
					break;
				case 'L':
					height = 25;
					width = 5;
					break;
				case 'R':
					height = 25;
					width = 5;
					break;
			}
			
			
			g.setColor(Color.blue);
			g.fillOval(x*25, y*25, width, height);
		}
		
		if(initial.length() == 2 && initial.charAt(1) == 'N') {
			legend = Board.getInstance().getLegend();
			String room = legend.get(initial.charAt(0));
			
			g.setColor(Color.white);
			g.drawString(room, col*25, row*25);
		}
	}
}
