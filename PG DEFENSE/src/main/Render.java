package main;

import java.awt.Graphics;

/**Klasa wyswietla zawartosc na ekran*/
public class Render {

	private Game game;

	/**Rozpoczenie wyswietlania*/
	public Render(Game game) {
		this.game = game;
	}

	/**Wyswietlenie odpowiedniej zawartosci w zaleznosci od sceny*/
	public void render(Graphics g) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().render(g);
			break;
		case PLAYING:
			game.getPlaying().render(g);
			break;
		case SETTINGS:
			game.getSettings().render(g);
			break;
		case GAME_OVER:
			game.getGameOver().render(g);
			break;
		case LEVEL_COMPLETED:
			game.getLevelCompleted().render(g);
			break;
		case WIN:
			game.getWin().render(g);
		default:
			break;
		}
	}
}