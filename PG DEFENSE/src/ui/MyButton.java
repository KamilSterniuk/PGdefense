package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**Klasa odpowiada za obsluge przycisku*/
public class MyButton {

	public int x, y, width, height, id;
	private String text;
	private Rectangle bounds;
	private boolean mouseOver, mousePressed;

	/**Inicjacja przycisku*/
	public MyButton(String text, int x, int y, int width, int height) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = -1;

		initBounds();
	}

	/**Inicjacja przycisku z ID*/
	public MyButton(String text, int x, int y, int width, int height, int id) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;

		initBounds();
	}

	/**Inicjacja granicy przycisku*/
	private void initBounds() {
		this.bounds = new Rectangle(x, y, width, height);
	}

	/**Wyswietlenie przycisku na ekran*/
	public void draw(Graphics g) {
		drawBody(g);
		drawBorder(g);
		drawText(g);
	}

	/**Wyswietlenie granicy przycisku na ekran*/
	private void drawBorder(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		if (mousePressed) {
			g.drawRect(x + 1, y + 1, width - 2, height - 2);
			g.drawRect(x + 2, y + 2, width - 4, height - 4);
		}
	}

	//*Wyswietlenie wnetrza przycisku*/
	private void drawBody(Graphics g) {
		if (mouseOver)
			g.setColor(Color.gray);
		else
			g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

	/**Wyswietlenie tekstu przycisku*/
	private void drawText(Graphics g) {
		int w = g.getFontMetrics().stringWidth(text);
		int h = g.getFontMetrics().getHeight();
		g.drawString(text, x - w / 2 + width / 2, y + h / 2 + height / 2);
	}

	/**Zresetowanie stanu przycisku*/
	public void resetBooleans() {
		this.mouseOver = false;
		this.mousePressed = false;
	}

	/**Ustawienie tekstu*/
	public void setText(String text) {
		this.text = text;
	}

	/**Ustawienie wcisniecia przycisku myszki*/
	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	/**Ustawienie wartosci dla kursora wewnatrz przycisku*/
	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	/**Sprawdzenie czy kursor jest na przycisku*/
	public boolean isMouseOver() {
		return mouseOver;
	}

	/**Sprawdzenie wcisniecia przycisku na myszki*/
	public boolean isMousePressed() {
		return mousePressed;
	}

	/**Zwraca granice przycisku*/
	public Rectangle getBounds() {
		return bounds;
	}

	/**Zwraca ID przycisku*/
	public int getId() {
		return id;
	}

}
