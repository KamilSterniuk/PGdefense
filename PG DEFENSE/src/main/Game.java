package main;

import javax.swing.JFrame;

import helpz.LoadSave;
import managers.TileManager;
import scenes.GameOver;
import scenes.LevelCompleted;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;
import scenes.Win;

/**Klasa glowna*/
public class Game extends JFrame implements Runnable {

	private GameScreen gameScreen;
	private Thread gameThread;

	private final double FPS_SET = 120.0;
	private final double UPS_SET = 60.0;

	private Render render;
	private Menu menu;
	private Playing playing;
	private Settings settings;
	private GameOver gameOver;
	private LevelCompleted levelCompleted;
	private Win win;

	private TileManager tileManager;

	/**Uruchamia potrzebne procesy i reguluje zasade ich dzialania*/
	public Game() {

		initClasses();
		createLevel();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		add(gameScreen);
		
		pack();
		setVisible(true);

	}

	/**Tworzy poziom*/
	private void createLevel() {
		int[] arr = new int[576];
		for (int i = 0; i < arr.length; i++)
			arr[i] = 0;

		LoadSave.CreateLevel("level1", arr);

	}

	/**Iniciuje klasy*/
	private void initClasses() {
		tileManager = new TileManager();
		render = new Render(this);
		gameScreen = new GameScreen(this);
		menu = new Menu(this);
		playing = new Playing(this);
		settings = new Settings(this);
		gameOver = new GameOver(this);
		levelCompleted = new LevelCompleted(this);
		win = new Win(this);
	}

	/**Rozpoczyna prace procesow*/
	private void start() {
		gameThread = new Thread(this) {
		};
		gameThread.start();
	}

	/**Odpowiada za aktualizacje gry*/
	private void updateGame() {
		switch (GameStates.gameState) {
		case MENU:
			break;
		case PLAYING:
			playing.update();
			break;
		case SETTINGS:
			break;
		default:
			break;
		}
	}

	/**Rozpoczyna prace programu*/
	public static void main(String[] args) {
		Game game = new Game();
		game.gameScreen.initInputs();
		game.start();

	}

	@Override
	/**Odpowiada za ustalenie liczby klatek i aktualizacji na sekunde*/
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long lastFrame = System.nanoTime();
		long lastUpdate = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();

		int frames = 0;
		int updates = 0;

		long now;

		while (true) {
			now = System.nanoTime();

			if (now - lastFrame >= timePerFrame) {
				repaint();
				lastFrame = now;
				frames++;
			}

			if (now - lastUpdate >= timePerUpdate) {
				updateGame();
				lastUpdate = now;
				updates++;
			}

			if (System.currentTimeMillis() - lastTimeCheck >= 1000) {
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}

		}

	}

	
	public Render getRender() {
		return render;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}

	public Settings getSettings() {
		return settings;
	}

	
	public GameOver getGameOver() {
		return gameOver;
	}
	public LevelCompleted getLevelCompleted() {
		return levelCompleted;
	}
	public Win getWin() {
		return win;
	}

	public TileManager getTileManager() {
		return tileManager;
	}

}