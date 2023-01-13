package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import ui.MyButton;
import static main.GameStates.*;

/**Klasa zawiera tresc sceny wyswietlanej po ukonczeniu poziomu*/
public class LevelCompleted extends GameScene implements SceneMethods  {

	private MyButton bContinue, bMenu;
	
	/**Inicjacja sceny*/
	public LevelCompleted(Game game) {
		super(game);
		initButton();
	}
	
	/**Inicjacja przyciskow*/
	private void initButton() {

		int w = 180;
		int h = w / 3;
		int x = 422;
		int y = 400;
		int yOffset = 100;

		bMenu = new MyButton("Menu", x, y, w, h);
		bContinue = new MyButton("Kontynuuj", x, y + yOffset, w, h);

	}

	@Override
	/**Wyswietlenie tresci na ekran*/
	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, 1024, 768);
		g.setFont(new Font("LucidaSans", Font.BOLD, 50));
		g.setColor(Color.YELLOW);
		g.drawString("Gratulacje!", 380, 130);
		g.drawString("Ukończyłeś poziom", 280, 200);

		g.setFont(new Font("LucidaSans", Font.BOLD, 20));
		bMenu.draw(g);
		bContinue.draw(g);
	}

	/**Zresetowanie wszystkich danych gry*/
	private void resetAll() {
		game.getPlaying().resetEverything();
	}

	@Override
	/**Odpowiada za obsluge klikniecia myszki w scenie*/
	public void mouseClicked(int x, int y) {
		// TODO Auto-generated method stub
		if (bMenu.getBounds().contains(x, y)) {
			SetGameState(MENU);
			resetAll();
		} else if (bContinue.getBounds().contains(x, y))
			SetGameState(PLAYING);
			resetAll();
	}

	@Override
	/**Odpowiada za obsluge ruchu myszki w scenie*/
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		bMenu.setMouseOver(false);
		bContinue.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if (bContinue.getBounds().contains(x, y))
			bContinue.setMouseOver(true);
	}

	@Override
	/**Odpowiada za obsluge wcisniecia przycisku myszki w scenie*/
	public void mousePressed(int x, int y) {
		// TODO Auto-generated method stub
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bContinue.getBounds().contains(x, y))
			bContinue.setMousePressed(true);
	}

	@Override
	/**Odpowiada za obsluge zwolnienia przycisku myszki w scenie*/
	public void mouseReleased(int x, int y) {
		// TODO Auto-generated method stub
		bMenu.resetBooleans();
		bContinue.resetBooleans();
	}

}
