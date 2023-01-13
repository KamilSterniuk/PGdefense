package scenes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import objects.Tower;
import ui.ActionBar;
import static helpz.Constants.Tiles.GRASS_TILE;
import static main.GameStates.LEVEL_COMPLETED;
import static main.GameStates.WIN;
import static main.GameStates.SetGameState;

/**Klasa zawiera tresc sceny wyswietlanej w trakcie gry*/
public class Playing extends GameScene implements SceneMethods {

	private int[][] lvl;

	private ActionBar actionBar;
	private int mouseX, mouseY;
	private EnemyManager enemyManager;
	private TowerManager towerManager;
	private ProjectileManager projManager;
	private WaveManager waveManager;
	private PathPoint start, end;
	private Tower selectedTower;
	private int goldTick;
	private int level = 1;
	private boolean gamePaused;
	public int LEVEL = 3;

	/**Inicjacja sceny*/
	public Playing(Game game) {
		super(game);
		
		loadDefaultLevel();
		
		actionBar = new ActionBar(this);
		enemyManager = new EnemyManager(this, start, end);
		towerManager = new TowerManager(this);
		projManager = new ProjectileManager(this);
		waveManager = new WaveManager(this);
	}

	/**Zaladowanie odpowiedniego poziomu*/
	void loadDefaultLevel() {
		if (level == 1)	{
			lvl = LoadSave.GetLevelData("level1");
			ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("level1");
			start = points.get(0);
			end = points.get(1);
			level ++;
		}
		else if (level == 2)	{
			lvl = LoadSave.GetLevelData("level2");
			ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("level2");
			start = points.get(0);
			end = points.get(1);
			level ++;
		}
		else if (level == 3)	{
			lvl = LoadSave.GetLevelData("level3");
			ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints("level3");
			start = points.get(0);
			end = points.get(1);
			level ++;
		}
		else if (level == 4)	{
			resetEverything();
			SetGameState(WIN);
			level = 1;
		}
		else	{
			System.out.println(" Błąd wczytania poziomu! ");
		}
	}

	/**Ustawienie poziomu*/
	public void setLevel(int[][] lvl) {
		this.lvl = lvl;
	}

	public void update() {
		if (!gamePaused) {
			updateTick();
			waveManager.update();

			goldTick++;
			if (goldTick % (60 * 3) == 0)
				actionBar.addGold(1);

			if (isAllEnemiesDead()) {
				if (isThereMoreWaves()) {
					waveManager.startWaveTimer();
					if (isWaveTimerOver()) {
						waveManager.increaseWaveIndex();
						enemyManager.getEnemies().clear();
						waveManager.resetEnemyIndex();
					}
				}
				else	{
					resetEverything();
					SetGameState(LEVEL_COMPLETED);
					loadDefaultLevel();
				}		
			}
			if (isTimeForNewEnemy()) {
				spawnEnemy();
			}
			enemyManager.update();
			towerManager.update();
			projManager.update();
		}
	}

	/**Sprawdzeinie, czy minal czas do kolejnej fali*/
	private boolean isWaveTimerOver() {
		return waveManager.isWaveTimerOver();
	}

	/**Sprawdzenie, czy jest wiecej fal*/
	private boolean isThereMoreWaves() {
		return waveManager.isThereMoreWaves();
	}

	/**Sprawdzenie, czy wszyscy przeciwnicy zostali pokonani*/
	private boolean isAllEnemiesDead() {

		if (waveManager.isThereMoreEnemiesInWave())
			return false;
		for (Enemy e : enemyManager.getEnemies())
			if (e.isAlive())
				return false;
		return true;
	}

	/**Dodanie nowego przeciwnika*/
	private void spawnEnemy() {
		enemyManager.spawnEnemy(waveManager.getNextEnemy());
	}

	/**Sprawdzenie, czy minal czas na dodanie kolejnego przeciwnika*/
	private boolean isTimeForNewEnemy() {
		if (waveManager.isTimeForNewEnemy()) {
			if (waveManager.isThereMoreEnemiesInWave())
				return true;
		}
		return false;
	}

	/**Ustawienie elementu strzelajacego*/
	public void setSelectedTower(Tower selectedTower) {
		this.selectedTower = selectedTower;
	}

	@Override
	/**Wyswietlenie tresci na ekran*/
	public void render(Graphics g) {
		drawLevel(g);
		actionBar.draw(g);
		enemyManager.draw(g);
		towerManager.draw(g);
		projManager.draw(g);

		drawSelectedTower(g);
		drawHighlight(g);
	}

	/**Wyswietlenie podswietlenia pozycji na ktorej ustawiono element*/
	private void drawHighlight(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawRect(mouseX, mouseY, 32, 32);
	}

	/**Wyswietlenie elementu strzelajacego*/
	private void drawSelectedTower(Graphics g) {
		if (selectedTower != null)
			g.drawImage(towerManager.getTowerImgs()[selectedTower.getTowerType()], mouseX, mouseY, null);
	}

	/**Wyswietlenie tekstur poziomu*/
	private void drawLevel(Graphics g) {

		for (int y = 0; y < lvl.length; y++) {
			for (int x = 0; x < lvl[y].length; x++) {
				int id = lvl[y][x];
				if (isAnimation(id)) {
					g.drawImage(getSprite(id, animationIndex), x * 32, y * 32, null);
				} else
					g.drawImage(getSprite(id), x * 32, y * 32, null);
			}
		}
	}

	/**Zwraca typ tekstury*/
	public int getTileType(int x, int y) {
		int xCord = x / 32;
		int yCord = y / 32;

		if (xCord < 0 || xCord > 19)
			return 0;
		if (yCord < 0 || yCord > 19)
			return 0;

		int id = lvl[y / 32][x / 32];
		return game.getTileManager().getTile(id).getTileType();
	}

	@Override
	/**Odpowiada za obsluge klikniecia myszki w scenie*/
	public void mouseClicked(int x, int y) {
		if (x >= 768)
			actionBar.mouseClicked(x, y);
		else {
			if (selectedTower != null) {
				if (isTileGrass(mouseX, mouseY)) {
					if (getTowerAt(mouseX, mouseY) == null) {
						towerManager.addTower(selectedTower, mouseX, mouseY);

						removeGold(selectedTower.getTowerType());

						selectedTower = null;
					}
				}
			} else {
				Tower t = getTowerAt(mouseX, mouseY);
				actionBar.displayTower(t);
			}
		}
	}

	/**Zabiera podana w argumencie ilosc monet*/
	private void removeGold(int towerType) {
		actionBar.payForTower(towerType);
	}

	/**Ustawienie elementu na danej pozycji*/
	private Tower getTowerAt(int x, int y) {
		return towerManager.getTowerAt(x, y);
	}

	/**Sprawdzenie, czy tekstura jest trawa*/
	private boolean isTileGrass(int x, int y) {
		int id = lvl[y / 32][x / 32];
		int tileType = game.getTileManager().getTile(id).getTileType();
		return tileType == GRASS_TILE;
	}

	/**Strzal do przeciwnika*/
	public void shootEnemy(Tower t, Enemy e) {
		projManager.newProjectile(t, e);
	}

	/**Ustawienie pauzy*/
	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}

	/**Zwraca numer poziomu*/
	public int getLevelNumber()	{
		return level;
	}

	@Override
	/**Odpowiada za obsluge ruchu myszki w scenie*/
	public void mouseMoved(int x, int y) {
		if (x >= 768)
			actionBar.mouseMoved(x, y);
		else {
			mouseX = (x / 32) * 32;
			mouseY = (y / 32) * 32;
		}
	}

	@Override
	/**Odpowiada za obsluge wcisniecia przycisku myszki w scenie*/
	public void mousePressed(int x, int y) {
		if (x >= 768)
			actionBar.mousePressed(x, y);
	}

	@Override
	/**Odpowiada za obsluge zwolnienia przycisku myszki w scenie*/
	public void mouseReleased(int x, int y) {
		actionBar.mouseReleased(x, y);
	}

	/**Dodanie monet graczowi*/
	public void rewardPlayer(int enemyType) {
		actionBar.addGold(helpz.Constants.Enemies.GetReward(enemyType));
	}

	/**Inicjacja klasy TowerManager*/
	public TowerManager getTowerManager() {
		return towerManager;
	}

	/**Inicjacja klasy EnemyManager*/
	public EnemyManager getEnemyManger() {
		return enemyManager;
	}

	/**Inicjacja klasy WaveManager*/
	public WaveManager getWaveManager() {
		return waveManager;
	}

	/**Sprawdzenie, czy gra jest zapauzowana*/
	public boolean isGamePaused() {
		return gamePaused;
	}

	/**Usuniecie jednego zycia*/
	public void removeOneLife() {
		actionBar.removeOneLife();
	}

	/**Zresetowanie danych poziomu*/
	public void resetEverything() {
		actionBar.resetEverything();

		enemyManager.reset();
		towerManager.reset();
		projManager.reset();
		waveManager.reset();

		mouseX = 0;
		mouseY = 0;

		selectedTower = null;
		goldTick = 0;
		gamePaused = false;
	}
}