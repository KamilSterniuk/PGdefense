package ui;

import java.awt.Color;
import java.awt.Graphics;

/**Klasa odpowiada za zachowanie interfejsu ActionBar*/
public class Bar {

	protected int x, y, width, height;

	/**Inicjacja klasy*/
	public Bar(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**Okreslenie zasad interakcji z przyciskami*/
	protected void drawButtonFeedback(Graphics g, MyButton b) {
		if (b.isMouseOver())
			g.setColor(Color.white);
		else
			g.setColor(Color.BLACK);

		g.drawRect(b.x, b.y, b.width, b.height);

		if (b.isMousePressed()) {
			g.drawRect(b.x + 1, b.y + 1, b.width - 2, b.height - 2);
			g.drawRect(b.x + 2, b.y + 2, b.width - 4, b.height - 4);
		}
	}
}
