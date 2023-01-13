package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

/**Klasa zawiera tresc sceny wyswietlanej jako menuj*/
public class Menu extends GameScene implements SceneMethods {

	private MyButton bPlaying, bSettings, bQuit;

	/**Inicjacja sceny*/
	public Menu(Game game) {
		super(game);
		initButtons();
	}

	/**Inicjacja przyciskow*/
	private void initButtons() {
		int w = 150;
		int h = w / 3;
		int x = 1024 / 2 - w / 2;
		int y = 400;
		int yOffset = 100;

		bPlaying = new MyButton("Graj", x, y, w, h);
		bSettings = new MyButton("Poziom trudności", x, y + yOffset, w, h);
		bQuit = new MyButton("Wyjście", x, y + yOffset * 2, w, h);

	}

	@Override
	/**Wyswietlenie tresci na ekran*/
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 1024, 768);
		drawButtons(g);
		g.setFont(new Font("LucidaSans", Font.BOLD, 80));
		g.setColor(Color.WHITE);
		g.drawString("PG DEFENSE", 250, 120);
		g.setFont(new Font("LucidaSans", Font.BOLD, 25));
		g.setColor(new Color(255, 0, 0));
		g.drawString("Wciel się w rolę Profesora i powstrzymaj studentów przed zaliczeniem semestru.", 30, 250);
		g.setColor(Color.WHITE);
		g.drawString("Autor: Kamil Sterniuk", 750, 750);
	}

	/**Wyswietlenie przyciskow*/
	private void drawButtons(Graphics g) {
		bPlaying.draw(g);
		bSettings.draw(g);
		bQuit.draw(g);

	}

	@Override
	/**Odpowiada za obsluge klikniecia myszki w scenie*/
	public void mouseClicked(int x, int y) {
		if (bPlaying.getBounds().contains(x, y))
			SetGameState(PLAYING);
		else if (bSettings.getBounds().contains(x, y))
			SetGameState(SETTINGS);
		else if (bQuit.getBounds().contains(x, y))
			System.exit(0);
	}

	@Override
	/**Odpowiada za obsluge ruchu myszki w scenie*/
	public void mouseMoved(int x, int y) {
		bPlaying.setMouseOver(false);
		bSettings.setMouseOver(false);
		bQuit.setMouseOver(false);

		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMouseOver(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMouseOver(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMouseOver(true);

	}

	@Override
	/**Odpowiada za obsluge wcisniecia przycisku myszki w scenie*/
	public void mousePressed(int x, int y) {
		if (bPlaying.getBounds().contains(x, y))
			bPlaying.setMousePressed(true);
		else if (bSettings.getBounds().contains(x, y))
			bSettings.setMousePressed(true);
		else if (bQuit.getBounds().contains(x, y))
			bQuit.setMousePressed(true);
	}

	@Override
	/**Odpowiada za obsluge zwolnienia przycisku myszki w scenie*/
	public void mouseReleased(int x, int y) {
		bPlaying.resetBooleans();
		bSettings.resetBooleans();
		bQuit.resetBooleans();
	}
}