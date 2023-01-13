package enemies;

import java.awt.Rectangle;

import managers.EnemyManager;

import static helpz.Constants.Direction.*;

/**Klasa odpowiada za obsluge i inicjacje parametrow przeciwnika*/
public abstract class Enemy {
	protected EnemyManager enemyManager;
	protected float x, y;
	protected Rectangle bounds;
	protected int health;
	protected int maxHealth;
	protected int ID;
	protected int enemyType;
	protected int lastDir;
	protected boolean alive = true;
	protected int slowTickLimit = 120;
	protected int slowTick = slowTickLimit;
	
	/**Funkcja przechowuje parametry przeciwnika (pozycja, ID, typ, zdrowie)*/
	public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager) {
		this.x = x;
		this.y = y;
		this.ID = ID;
		this.enemyType = enemyType;
		this.enemyManager = enemyManager;
		bounds = new Rectangle((int) x, (int) y, 32, 32);
		lastDir = -1;
		setStartHealth();
	}
	/**Funkcja podaje wartość zdrowia do funkcji enemy*/
	private void setStartHealth() {
		health = helpz.Constants.Enemies.GetStartHealth(enemyType);
		maxHealth = health;
	}

	/**Funkcja zadaje obrazenia przeciwnikowi, a gdy zdrowie spadnie do zera, daje nagrodę graczowi*/
	public void hurt(int dmg) {
		this.health -= dmg;
		if (health <= 0) {
			alive = false;
			enemyManager.rewardPlayer(enemyType);
		}

	}

	/**Usuniecie przeciwnika, gdy dotrze do punktu koncowego*/
	public void kill() {
		alive = false;
		health = 0;
	}
	/**Ustawienie licznika spowolnienia na 0*/
	public void slow() {
	
		slowTick = 0;
	}
	
	/**Funkcja odpowiada za ruch przeciwnika oraz jego spowolnienie*/
	public void move(float speed, int dir) {
		lastDir = dir;

		if (slowTick < slowTickLimit) {
			slowTick++;
			speed *= 0.5f;
		}

		switch (dir) {
		case LEFT:
			this.x -= speed;
			break;
		case UP:
			this.y -= speed;
			break;
		case RIGHT:
			this.x += speed;
			break;
		case DOWN:
			this.y += speed;
			break;
		}

		updateHitbox();
	}
	
	/**Aktualizacja pozycji przeciwnika*/
	private void updateHitbox() {
		bounds.x = (int) x;
		bounds.y = (int) y;
	}
	
	/**Ustawienie pozycji przeciwnika*/
	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**Ustawienie paska zycia przeciwnika*/
	public float getHealthBarFloat() {
		return health / (float) maxHealth;
	}
	/**Zwraca pozycje pozioma*/
	public float getX() {
	
		return x;
	}

	/**Zwraca pozycje pionowa*/
	public float getY() {
		return y;
	}
	
	/**Zwraca granice obszaru zajmowanego przez przeciwnika*/
	public Rectangle getBounds() {
		return bounds;
	}

	/**Zwraca zdrowie*/
	public int getHealth() {
		return health;
	}

	/**Zwraca ID przeciwnika*/
	public int getID() {
		return ID;
	}

	/**Zwraca typ przeciwnika*/
	public int getEnemyType() {
		return enemyType;
	}

	/**Zwraca ostatni kierunek poruszania sie*/
	public int getLastDir() {
		return lastDir;
	}

	/**Sprawdza, czy przeciwnik zyje*/
	public boolean isAlive() {
		return alive;
	}

	/**Sprawdza, czy przeciwnik jest spowolniony*/
	public boolean isSlowed() {
		return slowTick < slowTickLimit;
	}

}
