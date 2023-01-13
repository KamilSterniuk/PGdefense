package managers;

import java.awt.Color;
import java.awt.Graphics;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import enemies.Wolny;
import enemies.Szybki;
import helpz.LoadSave;
import objects.PathPoint;
import scenes.Playing;
import static helpz.Constants.Direction.*;
import static helpz.Constants.Tiles.*;
import static helpz.Constants.Enemies.*;

/**Klasa opowiada za oblsuje przeciwnika*/
public class EnemyManager {

	private Playing playing;
	private BufferedImage[] enemyImgs;
	private ArrayList<Enemy> enemies = new ArrayList<>();
	private PathPoint start, end;
	private int HPbarWidth = 20;
	private BufferedImage slowEffect;

	/**Inicjacja przeciwnika i podstawowych wlasciwosci gry*/
	public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
		this.playing = playing;
		enemyImgs = new BufferedImage[2];
		this.start = start;
		this.end = end;

		loadEffectImg();
		loadEnemyImgs();
	}

	/**Rozpoczenie spowolnienia*/
	private void loadEffectImg() {
		slowEffect = LoadSave.getSpriteAtlas().getSubimage(32 * 9, 32 * 2, 32, 32);
	}

	/**Zaladowanie tekstury przeciwnika*/
	private void loadEnemyImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		for (int i = 0; i < 2; i++)
			enemyImgs[i] = atlas.getSubimage(i * 32, 32, 32, 32);

	}

	/**Aktualizacja postepu w grze*/
	public void update() {
		for (Enemy e : enemies)
			if (e.isAlive())
				updateEnemyMove(e);
	}

	/**Poruszanie sie przeciwnika*/
	public void updateEnemyMove(Enemy e) {
		if (e.getLastDir() == -1)
			setNewDirectionAndMove(e);

		int newX = (int) (e.getX() + getSpeedAndWidth(e.getLastDir(), e.getEnemyType()));
		int newY = (int) (e.getY() + getSpeedAndHeight(e.getLastDir(), e.getEnemyType()));

		if (getTileType(newX, newY) == ROAD_TILE) {
			e.move(GetSpeed(e.getEnemyType()), e.getLastDir());
		} else if (isAtEnd(e)) {
			e.kill();
			playing.removeOneLife();
		} else {
			setNewDirectionAndMove(e);
		}
	}

	/**Zmiana kierunku ruchu*/
	private void setNewDirectionAndMove(Enemy e) {
		int dir = e.getLastDir();

		int xCord = (int) (e.getX() / 32);
		int yCord = (int) (e.getY() / 32);

		fixEnemyOffsetTile(e, dir, xCord, yCord);

		if (isAtEnd(e))
			return;

		if (dir == LEFT || dir == RIGHT) {
			int newY = (int) (e.getY() + getSpeedAndHeight(UP, e.getEnemyType()));
			if (getTileType((int) e.getX(), newY) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()), UP);
			else
				e.move(GetSpeed(e.getEnemyType()), DOWN);
		} else {
			int newX = (int) (e.getX() + getSpeedAndWidth(RIGHT, e.getEnemyType()));
			if (getTileType(newX, (int) e.getY()) == ROAD_TILE)
				e.move(GetSpeed(e.getEnemyType()), RIGHT);
			else
				e.move(GetSpeed(e.getEnemyType()), LEFT);

		}

	}

	/**Zmiana informacji o pozycji*/
	private void fixEnemyOffsetTile(Enemy e, int dir, int xCord, int yCord) {
		switch (dir) {
		case RIGHT:
			if (xCord < 19)
				xCord++;
			break;
		case DOWN:
			if (yCord < 19)
				yCord++;
			break;
		}

		e.setPos(xCord * 32, yCord * 32);

	}

	/**Sprawdzenie, czy przeciwnik doszedl do koncowego punktu*/
	private boolean isAtEnd(Enemy e) {
		if (e.getX() == end.getxCord() * 32)
			if (e.getY() == end.getyCord() * 32)
				return true;
		return false;
	}

	/**Sprawdzenie typu podloza*/
	private int getTileType(int x, int y) {
		return playing.getTileType(x, y);
	}

	/**Zwraca wartosc predkosci dla ruchu pionowego*/
	private float getSpeedAndHeight(int dir, int enemyType) {
		if (dir == UP)
			return -GetSpeed(enemyType);
		else if (dir == DOWN)
			return GetSpeed(enemyType) + 32;

		return 0;
	}

	/**Zwraca wartosc predkosci dla ruchu poziomego*/
	private float getSpeedAndWidth(int dir, int enemyType) {
		if (dir == LEFT)
			return -GetSpeed(enemyType);
		else if (dir == RIGHT)
			return GetSpeed(enemyType) + 32;

		return 0;
	}

	/**Dodanie przeciwnika do pojawienia sie*/
	public void spawnEnemy(int nextEnemy) {
		addEnemy(nextEnemy);
	}

	/**Pojawienie sie przeciwnika w odpowiednim miejscu*/
	public void addEnemy(int enemyType) {

		int x = start.getxCord() * 32;
		int y = start.getyCord() * 32;

		switch (enemyType) {
		case SZYBKI:
			enemies.add(new Szybki(x, y, 0, this));
			break;
		case WOLNY:
			enemies.add(new Wolny(x, y, 0, this));
			break;
		}

	}

	/**Powoduje wyswietlanie sie na ekran obiektow*/
	public void draw(Graphics g) {
		for (Enemy e : enemies) {
			if (e.isAlive()) {
				drawEnemy(e, g);
				drawHealthBar(e, g);
				drawEffects(e, g);
			}
		}
	}

	/**Dodanie graficznej reprezentacji efektu spowolnienia*/
	private void drawEffects(Enemy e, Graphics g) {
		if (e.isSlowed())
			g.drawImage(slowEffect, (int) e.getX(), (int) e.getY(), null);

	}

	/**Wyswietlenie poziomu zdrowia przeciwnika*/
	private void drawHealthBar(Enemy e, Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int) e.getX() + 16 - (getNewBarWidth(e) / 2), (int) e.getY() - 10, getNewBarWidth(e), 3);

	}

	/**Uzyskanie poziomu zdrowia przeciwnika*/
	private int getNewBarWidth(Enemy e) {
		return (int) (HPbarWidth * e.getHealthBarFloat());
	}

	/**Wyswietlenie przeciwnika*/
	private void drawEnemy(Enemy e, Graphics g) {
		g.drawImage(enemyImgs[e.getEnemyType()], (int) e.getX(), (int) e.getY(), null);
	}

	/**Wczytanie przeciwnikow*/
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	/**Uzyskanie liczby zyjacych przeciwnikow*/
	public int getAmountOfAliveEnemies() {
		int size = 0;
		for (Enemy e : enemies)
			if (e.isAlive())
				size++;

		return size;
	}

	/**Nagrodzenie gracza za zabicie przeciwnika*/
	public void rewardPlayer(int enemyType) {
		playing.rewardPlayer(enemyType);
	}

	/**Usuniecie przeciwnikow*/
	public void reset() {
		enemies.clear();
	}

}
