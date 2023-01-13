package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import ui.MyButton;

import helpz.Constants;
import static main.GameStates.*;

/**Klasa zawiera tresc sceny wyswietlanej w wyborze poziomu trudnosci*/
public class Settings extends GameScene implements SceneMethods {

	private MyButton bMenu, bEasy, bMedium, bHard;
	public static int level = 3;
	String levelText = "Latwy";
	
	/**Inicjacja sceny*/
	public Settings(Game game) {
		super(game);
		initButtons();
	}

	/**Inicjacja przyciskow*/
	private void initButtons() {
		bMenu = new MyButton("Menu", 2, 2, 100, 30);
		
		int w = 180;
		int h = w / 3;
		int x = 422;
		int y = 350;
		int yOffset = 100;

		bEasy = new MyButton("Latwy", x, y, w, h);
		bMedium = new MyButton("Średni", x, y + yOffset, w, h);
		bHard = new MyButton("Trudny", x, y + yOffset * 2, w, h);
	}

	@Override
	/**Wyswietlenie tresci na ekran*/
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 1024, 768);

		drawButtons(g);
		
		g.setFont(new Font("LucidaSans", Font.BOLD, 30));
		g.setColor(Color.WHITE);
		g.drawString("Wybrany poziom trudności: " +levelText, 280, 200);
	}

	/**Wyswietlenie przyciskow*/
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bEasy.draw(g);
		bMedium.draw(g);
		bHard.draw(g);
	}
	
	/**Ustawienie tekstu poziomu trudnosci*/
	String setText()	{
		
		switch(level)	{
		
		case 1:
			levelText = "Trudny";
			break;
		case 2:
			levelText = "Średni";
			break;
		case 3:
			levelText = "Latwy";
			break;
		default:
			
		}
		return levelText;	
	}
	
	/**Wysalanie numeru poziomu trudnosci*/
	public static int sendLevel() {
		return level;
	}
	

	@Override
	/**Odpowiada za obsluge klikniecia myszki w scenie*/
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))	
			SetGameState(MENU);
		else if (bEasy.getBounds().contains(x, y))
			level=3;
		else if (bMedium.getBounds().contains(x, y))
			level=2;
		else if (bHard.getBounds().contains(x, y))
			level=1;
		
		setText();
		Constants.getLevel();
	}

	@Override
	/**Odpowiada za obsluge ruchu myszki w scenie*/
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
	}

	@Override
	/**Odpowiada za obsluge wcisniecia przycisku myszki w scenie*/
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
	}

	@Override
	/**Odpowiada za obsluge zwolnienia przycisku myszki w scenie*/
	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
	}
}
