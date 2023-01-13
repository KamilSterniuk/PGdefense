package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;
import static helpz.Constants.Tiles.*;

/**Klasa obsluguje grafike podloza*/
public class TileManager {

	public Tile GRASS, FINISH, ROAD_LR, ROAD_TB, ROAD_B_TO_R, ROAD_L_TO_B, ROAD_L_TO_T, ROAD_T_TO_R;

	private BufferedImage atlas;
	public ArrayList<Tile> tiles = new ArrayList<>();

	public ArrayList<Tile> roadsS = new ArrayList<>();
	public ArrayList<Tile> roadsC = new ArrayList<>();

	/**Inicjacja obslugi tekstur*/
	public TileManager() {
		loadAtalas();
		createTiles();
	}

	/**Utworzenie pojedynczych tekstur*/
	private void createTiles() {

		int id = 0;

		tiles.add(GRASS = new Tile(getSprite(9, 0), id++, GRASS_TILE));
		tiles.add(FINISH = new Tile(getAniSprites(0, 0), id++, FINISH_TILE));

		roadsS.add(ROAD_LR = new Tile(getSprite(8, 0), id++, ROAD_TILE));
		roadsS.add(ROAD_TB = new Tile(ImgFix.getRotImg(getSprite(8, 0), 90), id++, ROAD_TILE));

		roadsC.add(ROAD_B_TO_R = new Tile(getSprite(7, 0), id++, ROAD_TILE));
		roadsC.add(ROAD_L_TO_B = new Tile(ImgFix.getRotImg(getSprite(7, 0), 90), id++, ROAD_TILE));
		roadsC.add(ROAD_L_TO_T = new Tile(ImgFix.getRotImg(getSprite(7, 0), 180), id++, ROAD_TILE));
		roadsC.add(ROAD_T_TO_R = new Tile(ImgFix.getRotImg(getSprite(7, 0), 270), id++, ROAD_TILE));

		tiles.addAll(roadsS);
		tiles.addAll(roadsC);
	}

	/**Zaladowanie pliku z teksturami*/
	private void loadAtalas() {
		atlas = LoadSave.getSpriteAtlas();
	}

	/**Zwraca wartosc ID tekstury*/
	public Tile getTile(int id) {
		return tiles.get(id);
	}

	/**Pozyskanie tekstury*/
	public BufferedImage getSprite(int id) {
		return tiles.get(id).getSprite();
	}

	/**Pozyskanie tekstury do animacji*/
	public BufferedImage getAniSprite(int id, int animationIndex) {
		return tiles.get(id).getSprite(animationIndex);
	}

	/**Pozyskanie konkretnej tekstury animacyjnej*/
	private BufferedImage[] getAniSprites(int xCord, int yCord) {
		BufferedImage[] arr = new BufferedImage[4];
		for (int i = 0; i < 4; i++) {
			arr[i] = getSprite(xCord + i, yCord);
		}
		return arr;
	}

	/**Pozyskanie konkretnej tekstury*/
	private BufferedImage getSprite(int xCord, int yCord) {
		return atlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
	}

	/**Sprawdzanie, czy tekstura ma animacje*/
	public boolean isSpriteAnimation(int spriteID) {
		return tiles.get(spriteID).isAnimation();
	}

	/**Pozyskanie prostych drog*/
	public ArrayList<Tile> getRoadsS() {
		return roadsS;
	}

	/**Pozyskanie zakreconych drog*/
	public ArrayList<Tile> getRoadsC() {
		return roadsC;
	}
}
