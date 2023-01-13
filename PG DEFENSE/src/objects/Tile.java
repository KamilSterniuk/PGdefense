package objects;

import java.awt.image.BufferedImage;

/**Klasa odpowiada za przekazywanie danych tekstury*/
public class Tile {

	private BufferedImage[] sprite;
	private int id, tileType;

	/**Inicjacja danych tekstury*/
	public Tile(BufferedImage sprite, int id, int tileType) {
		this.sprite = new BufferedImage[1];
		this.sprite[0] = sprite;
		this.id = id;
		this.tileType = tileType;
	}

	/**Inicjacja danych tekstur*/
	public Tile(BufferedImage[] sprite, int id, int tileType) {
		this.sprite = sprite;
		this.id = id;
		this.tileType = tileType;
	}

	/**Zwraca typ tekstury*/
	public int getTileType() {
		return tileType;
	}

	/**Zwraca teksture animowana*/
	public BufferedImage getSprite(int animationIndex) {
		return sprite[animationIndex];
	}

	/**Zwraca teksture*/
	public BufferedImage getSprite() {
		return sprite[0];
	}

	/**Sprawdza, czy dla tekstury istnieje animacja*/
	public boolean isAnimation() {
		return sprite.length > 1;
	}

	/**Zwraca ID tekstury*/
	public int getId() {
		return id;
	}

}
