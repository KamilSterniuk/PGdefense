package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

/**Klasa zawiera tresc sceny wyswietlanej po przegranej*/
public class GameOver extends GameScene implements SceneMethods {

	private MyButton bReplay, bMenu;

	/**Inicjacja sceny*/
	public GameOver(Game game) {
		super(game);
		initButtons();
	}

	/**Inicjacja przyciskow*/
	private void initButtons() {

		int w = 180;
		int h = w / 3;
		int x = 422;
		int y = 400;
		int yOffset = 100;

		bMenu = new MyButton("Menu", x, y, w, h);
		bReplay = new MyButton("Spróbuj ponownie", x, y + yOffset, w, h);

	}

	@Override
	/**Wyswietlenie tresci na ekran*/
	public void render(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 1024, 768);
		g.setFont(new Font("LucidaSans", Font.BOLD, 50));
		g.setColor(Color.red);
		g.drawString("Przegrana!", 400, 80);
		g.drawString("Studenci zdali sesję", 300, 150);

		g.setFont(new Font("LucidaSans", Font.BOLD, 20));
		bMenu.draw(g);
		bReplay.draw(g);
	}

	/**Powtorzenie poziomu od nowa*/
	private void replayGame() {
		resetAll();

		SetGameState(PLAYING);
	}

	/**Zresetowanie wszystkich danych gry*/
	private void resetAll() {
		game.getPlaying().resetEverything();
	}

	@Override
	/**Odpowiada za obsluge klikniecia myszki w scenie*/
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			resetAll();
		} else if (bReplay.getBounds().contains(x, y))
			replayGame();
	}

	@Override
	/**Odpowiada za obsluge ruchu myszki w scenie*/
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bReplay.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if (bReplay.getBounds().contains(x, y))
			bReplay.setMouseOver(true);
	}

	@Override
	/**Odpowiada za obsluge wcisniecia przycisku myszki w scenie*/
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bReplay.getBounds().contains(x, y))
			bReplay.setMousePressed(true);

	}

	@Override
	/**Odpowiada za obsluge zwolnienia przycisku myszki w scenie*/
	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bReplay.resetBooleans();
	}
}
