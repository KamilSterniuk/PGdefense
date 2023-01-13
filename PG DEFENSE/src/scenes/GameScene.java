package scenes;

import java.awt.image.BufferedImage;

import main.Game;

/**Klasa odpowaiada za obsluge animacji*/
public class GameScene {

	protected Game game;
	protected int animationIndex;
	protected int ANIMATION_SPEED = 25;
	protected int tick;

	/**Inicjacja obslugi*/
	public GameScene(Game game) {
		this.game = game;
	}

	/**Sprawdzenie, czy tekstura posiada animacje*/
	protected boolean isAnimation(int spriteID) {
		return game.getTileManager().isSpriteAnimation(spriteID);
	}

	/**Obsluga zegara animacji*/
	protected void updateTick() {
		tick++;
		if (tick >= ANIMATION_SPEED) {
			tick = 0;
			animationIndex++;
			if (animationIndex >= 4)
				animationIndex = 0;
		}
	}

	/**Zwraca wartosc konkretnej tekstury*/
	protected BufferedImage getSprite(int spriteID) {
		return game.getTileManager().getSprite(spriteID);
	}

	/**Zwraca wartosc konkretnej tekstury z animacja*/
	protected BufferedImage getSprite(int spriteID, int animationIndex) {
		return game.getTileManager().getAniSprite(spriteID, animationIndex);
	}

}
