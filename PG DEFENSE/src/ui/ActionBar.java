package ui;

import static main.GameStates.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import objects.Tower;
import scenes.Playing;

/**Klasa odpowiada za wyswietlanie i dzialanie elementow interfejsu wewnatrz sceny playing*/
public class ActionBar extends Bar {

	private Playing playing;
	private MyButton bMenu, bPause;

	private MyButton[] towerButtons;
	private Tower selectedTower;
	private Tower displayedTower;
	
	private boolean showTowerCost;
	private int towerCostType;

	public int lives = 3;
	public int gold = 200;
	

	/**Inicjacja interfejsu*/
	public ActionBar(Playing playing) {
		super(768, 0, 256, 768);
		this.playing = playing;
		new DecimalFormat("0.0");

		initButtons();
	}
	
	/**Zresetowanie danych interfejsu*/
	public void resetEverything() {
		lives = 3;
		towerCostType = 0;
		showTowerCost = false;
		gold = 200;
		selectedTower = null;
		displayedTower = null;
	}

	/**Inicjacja przyciskow*/
	private void initButtons() {

		bMenu = new MyButton("Menu", 906, 10, 100, 30);
		bPause = new MyButton("Pauza", 786, 10, 100, 30);

		towerButtons = new MyButton[3];
		int w = 50;
		int h = 50;
		int xStart = 871;
		int yStart = 200;
		int yOffset = 100;

		for (int i = 0; i < towerButtons.length; i++)
			towerButtons[i] = new MyButton("", xStart, yStart + yOffset * i, w, h, i);
	}

	/**Odjecie jednego zycia*/
	public void removeOneLife() {
		lives--;
		if (lives <= 0)
			SetGameState(GAME_OVER);
	}

	/**Wyswietlenie przyciskow*/
	private void drawButtons(Graphics g) {
		bMenu.draw(g);
		bPause.draw(g);

		for (MyButton b : towerButtons) {
			g.setColor(Color.gray);
			g.fillRect(b.x, b.y, b.width, b.height);
			g.drawImage(playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
			drawButtonFeedback(g, b);
		}
	}

	/**Wyswietlenie tresci na ekran*/
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);

		drawButtons(g);

		drawDisplayedTower(g);

		drawWaveInfo(g);

		drawGoldAmount(g);

		if (showTowerCost)
			drawTowerCost(g);

		if (playing.isGamePaused()) {
			g.setColor(Color.RED);
			g.setFont(new Font("LucidaSans", Font.BOLD, 20));
			g.drawString("Gra zapauzowana!", 800, 100);
		}

		g.setColor(Color.black);
		g.setFont(new Font("LucidaSans", Font.BOLD, 20));
		g.drawString("Życia: " + lives, 930, 550);

	}

	/**Wyswietlenie informacji o koszcie elementu strzelajacego*/
	private void drawTowerCost(Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(768, 668, 256, 100);
		g.setColor(Color.black);
		g.drawRect(768, 668, 256, 100);

		g.drawString("" + getTowerCostName(), 778, 700);
		g.drawString("Koszt: " + getTowerCostCost() + "zł", 910, 700);
		g.setFont(new Font("LucidaSans", Font.BOLD, 9));
		g.drawString("" + getTowerDescription(), 770, 750);

		if (isTowerCostMoreThanCurrentGold()) {
			g.setColor(Color.RED);
			g.setFont(new Font("LucidaSans", Font.BOLD, 15));
			g.drawString("Nie masz wystarczająco dużo monet", 770, 520);

		}

	}

	/**Sprawdzenie, czy koszt elementu jest wiekszy od liczby monet*/
	private boolean isTowerCostMoreThanCurrentGold() {
		return getTowerCostCost() > gold;
	}

	/**Zwraca nazwe wybranego elementu*/
	private String getTowerCostName() {
		return helpz.Constants.Towers.GetName(towerCostType);
	}
	
	/**Zwraca opis wybranego elementu*/
	private String getTowerDescription() {
		return helpz.Constants.Towers.GetDescription(towerCostType);
	}

	/**Zwraca koszt wybranego elementu*/
	private int getTowerCostCost() {
		return helpz.Constants.Towers.GetTowerCost(towerCostType);
	}

	/**Wyswietlenie ilosci monet*/
	private void drawGoldAmount(Graphics g) {
		g.drawString("Monety: " + gold + "zł", 780, 550);
	}

	/**Wyswietlenie informacji o fali przeciwnikow*/
	private void drawWaveInfo(Graphics g) {
		g.setColor(Color.black);
		g.setFont(new Font("LucidaSans", Font.BOLD, 20));
		drawEnemiesLeftInfo(g);
		drawWavesLeftInfo(g);
	}

	/**Wyswietlenie informacji o obecnej fali i ilosci wszytskich fal*/
	private void drawWavesLeftInfo(Graphics g) {
		int current = playing.getWaveManager().getWaveIndex();
		int size = playing.getWaveManager().getWaves().size();
		g.drawString("Fala " + (current + 1) + " / " + size, 850, 575);
	}

	/**Wyswietlenie informacji o pozostalych przeciwnikach*/
	private void drawEnemiesLeftInfo(Graphics g) {
		int remaining = playing.getEnemyManger().getAmountOfAliveEnemies();
		g.drawString("Pozostali studenci: " + remaining, 800, 600);
	}

	/**Wyswietlenie danych wybranego elementu*/
	private void drawDisplayedTower(Graphics g) {
		if (displayedTower != null) {
			drawDisplayedTowerBorder(g);
			drawDisplayedTowerRange(g);
			}
	}

	/**Wyswietlenie zasiegu elementu*/
	private void drawDisplayedTowerRange(Graphics g) {
		g.setColor(Color.white);
		g.drawOval(displayedTower.getX() + 16 - (int) (displayedTower.getRange() * 2) / 2, displayedTower.getY() + 16 - (int) (displayedTower.getRange() * 2) / 2, (int) displayedTower.getRange() * 2,
				(int) displayedTower.getRange() * 2);
	}

	/**Podswietlenie lokalizacji elementu*/
	private void drawDisplayedTowerBorder(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawRect(displayedTower.getX(), displayedTower.getY(), 32, 32);
	}

	/**Wyswietlenie elementu*/
	public void displayTower(Tower t) {
		displayedTower = t;
	}

	/**Obsluga przycisku pauzy*/
	private void togglePause() {
		playing.setGamePaused(!playing.isGamePaused());

		if (playing.isGamePaused())
			bPause.setText("Wznów");
		else
			bPause.setText("Pauza");
	}

	/**Odpowiada za obsluge klikniecia myszki w scenie*/
	public void mouseClicked(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			SetGameState(MENU);
		else if (bPause.getBounds().contains(x, y))
			togglePause();
		else {
			for (MyButton b : towerButtons) {
				if (b.getBounds().contains(x, y)) {
					if (!isGoldEnoughForTower(b.getId()))
						return;

					selectedTower = new Tower(0, 0, -1, b.getId());
					playing.setSelectedTower(selectedTower);
					return;
				}
			}
		}
	}

	/**Sprawdzenie, czy uzytkownika stac na element*/
	private boolean isGoldEnoughForTower(int towerType) {
		return gold >= helpz.Constants.Towers.GetTowerCost(towerType);
	}

	/**Odpowiada za obsluge ruchu myszki w scenie*/
	public void mouseMoved(int x, int y) {
		bMenu.setMouseOver(false);
		bPause.setMouseOver(false);
		showTowerCost = false;

		for (MyButton b : towerButtons)
			b.setMouseOver(false);

		if (bMenu.getBounds().contains(x, y))
			bMenu.setMouseOver(true);
		else if (bPause.getBounds().contains(x, y))
			bPause.setMouseOver(true);
		else {
			
			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMouseOver(true);
					showTowerCost = true;
					towerCostType = b.getId();
					return;
				}
		}
	}

	/**Odpowiada za obsluge wcisniecia przycisku myszki w scenie*/
	public void mousePressed(int x, int y) {
		if (bMenu.getBounds().contains(x, y))
			bMenu.setMousePressed(true);
		else if (bPause.getBounds().contains(x, y))
			bPause.setMousePressed(true);
		else {
			for (MyButton b : towerButtons)
				if (b.getBounds().contains(x, y)) {
					b.setMousePressed(true);
					return;
				}
		}
	}

	/**Odpowiada za obsluge zwolnienia przycisku myszki w scenie*/
	public void mouseReleased(int x, int y) {
		bMenu.resetBooleans();
		bPause.resetBooleans();
		for (MyButton b : towerButtons)
			b.resetBooleans();
	}

	/**Odjecie monet przy kupnie elementu*/
	public void payForTower(int towerType) {
		this.gold -= helpz.Constants.Towers.GetTowerCost(towerType);
	}

	/**Dodanie monet*/
	public void addGold(int getReward) {
		this.gold += getReward;
	}

	/**Zwraca liczbe zyc*/
	public int getLives() {
		return lives;
	}
}