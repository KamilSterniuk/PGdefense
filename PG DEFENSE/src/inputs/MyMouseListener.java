package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.Game;
import main.GameStates;

/**Klasa odpowiada za obsluge myszy*/
public class MyMouseListener implements MouseListener, MouseMotionListener {

	private Game game;

	/**Obsluga myszy zostaje zainicjowana z glownej klasy*/
	public MyMouseListener(Game game) {
		this.game = game;
	}

	@Override
	/**Nie jest obslugiwane*/
	public void mouseDragged(MouseEvent e) {
	
	}

	@Override
	/**Odpowiada za poruszanie myszy we wszystkich scenach*/
	public void mouseMoved(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseMoved(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseMoved(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseMoved(e.getX(), e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mouseMoved(e.getX(), e.getY());
			break;
		case LEVEL_COMPLETED:
			game.getLevelCompleted().mouseMoved(e.getX(), e.getY());
			break;
		case WIN:
			game.getWin().mouseMoved(e.getX(), e.getY());
			break;
			
		default:
			break;
		}
	}

	@Override
	/**Odpowiada za klikanie myszy we wszystkich scenach*/
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (GameStates.gameState) {
			case MENU:
				game.getMenu().mouseClicked(e.getX(), e.getY());
				break;
			case PLAYING:
				game.getPlaying().mouseClicked(e.getX(), e.getY());
				break;
			case SETTINGS:
				game.getSettings().mouseClicked(e.getX(), e.getY());
				break;
			case GAME_OVER:
				game.getGameOver().mouseClicked(e.getX(), e.getY());
				break;
			case LEVEL_COMPLETED:
				game.getLevelCompleted().mouseClicked(e.getX(), e.getY());
				break;
			case WIN:
				game.getWin().mouseClicked(e.getX(), e.getY());
				break;
			default:
				break;
			}
		}
	}

	@Override
	/**Odpowiada za przyciskanie myszy we wszystkich scenach*/
	public void mousePressed(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mousePressed(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mousePressed(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mousePressed(e.getX(), e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mousePressed(e.getX(), e.getY());
			break;
		case LEVEL_COMPLETED:
			game.getLevelCompleted().mousePressed(e.getX(), e.getY());
			break;
		case WIN:
			game.getWin().mousePressed(e.getX(), e.getY());
			break;
		default:
			break;
		}
	}

	@Override
	/**Odpowiada za zwalnianie myszy we wszystkich scenach*/
	public void mouseReleased(MouseEvent e) {
		switch (GameStates.gameState) {
		case MENU:
			game.getMenu().mouseReleased(e.getX(), e.getY());
			break;
		case PLAYING:
			game.getPlaying().mouseReleased(e.getX(), e.getY());
			break;
		case SETTINGS:
			game.getSettings().mouseReleased(e.getX(), e.getY());
			break;
		case GAME_OVER:
			game.getGameOver().mouseReleased(e.getX(), e.getY());
			break;
		case LEVEL_COMPLETED:
			game.getLevelCompleted().mouseReleased(e.getX(), e.getY());
			break;
		case WIN:
			game.getWin().mouseReleased(e.getX(), e.getY());
			break;
		default:
			break;
		}
	}

	@Override
	/**Nie jest obslugiwane*/
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	/**Nie jest obslugiwane*/
	public void mouseExited(MouseEvent e) {
		
	}

}
