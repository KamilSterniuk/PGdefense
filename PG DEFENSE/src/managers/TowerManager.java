package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import objects.Tower;
import scenes.Playing;

/**Klasa odpowiada za oblsuge elementow strzejajacych*/
public class TowerManager {

	private Playing playing;
	private BufferedImage[] towerImgs;
	private ArrayList<Tower> towers = new ArrayList<>();
	private int towerAmount = 0;

	/**Inicjajcia obslugi*/
	public TowerManager(Playing playing) {
		this.playing = playing;
		loadTowerImgs();
	}

	/**Zaladowanie tekstur elementow*/
	private void loadTowerImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		towerImgs = new BufferedImage[3];
		for (int i = 0; i < 3; i++)
			towerImgs[i] = atlas.getSubimage((4 + i) * 32, 32, 32, 32);
	}

	/**Dodanie elementu*/
	public void addTower(Tower selectedTower, int xPos, int yPos) {
		towers.add(new Tower(xPos, yPos, towerAmount++, selectedTower.getTowerType()));
	}

	/**Aktualizacja stanu elementu*/
	public void update() {
		for (Tower t : towers) {
			t.update();
			attackEnemyIfClose(t);
		}
	}

	/**Strzal gry przeciwnik znajduje sie w zasiegu*/
	private void attackEnemyIfClose(Tower t) {
		for (Enemy e : playing.getEnemyManger().getEnemies()) {
			if (e.isAlive())
				if (isEnemyInRange(t, e)) {
					if (t.isCooldownOver()) {
						playing.shootEnemy(t, e);
						t.resetCooldown();
					}
				}
		}
	}

	/**Sprawdzenie czy przeciwnik znajduje sie w zasiegu*/
	private boolean isEnemyInRange(Tower t, Enemy e) {
		int range = helpz.Utilz.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
		return range < t.getRange();
	}

	/**Obsluga graficzna elementu*/
	public void draw(Graphics g) {
		for (Tower t : towers)
			g.drawImage(towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
	}

	/**Ustawienie elementu w odpowiednim miejscu*/
	public Tower getTowerAt(int x, int y) {
		for (Tower t : towers)
			if (t.getX() == x)
				if (t.getY() == y)
					return t;
		return null;
	}

	/**Uzyskanie tekstur elementow*/
	public BufferedImage[] getTowerImgs() {
		return towerImgs;
	}

	/**Usuniecie elementow*/
	public void reset() {
		towers.clear();
		towerAmount = 0;
	}

}
