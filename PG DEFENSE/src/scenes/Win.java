package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

/**Klasa zawiera tresc sceny wyswietlanej ukonczeniu ostatniego poziomu*/
public class Win extends GameScene implements SceneMethods {

	private MyButton bMenu;

	/**Inicjacja sceny*/
	public Win(Game game) {
		super(game);
		initButton();
	}

	/**Inicjacja przycisku*/
	private void initButton() {
		int w = 180;
		int h = w / 3;
		int x = 422;
		int y = 500;
		
		bMenu = new MyButton("Menu", x, y, w, h);
	}

	@Override
	/**Wyswietlenie tresci na ekran*/
	public void render(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, 1024, 768);
		g.setFont(new Font("LucidaSans", Font.BOLD, 50));
		g.setColor(Color.BLACK);
		g.drawString("Gratulacje!", 400, 80);
		g.drawString("Oblałeś wszystkich studentów", 170, 150);
		g.setFont(new Font("LucidaSans", Font.BOLD, 30));
		g.drawString("Przejdź do Menu. Możesz rozpocząć grę od nowa.", 180, 400);

		g.setFont(new Font("LucidaSans", Font.BOLD, 20));
		bMenu.draw(g);
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
		}
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
