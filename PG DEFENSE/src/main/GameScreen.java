package main;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import inputs.MyMouseListener;

/**Klasa odpowiada za graficzene funkcjonowanie programu*/
public class GameScreen extends JPanel {

	private Game game;
	private Dimension size;

	private MyMouseListener myMouseListener;

	/**Rozpoczyna prace ekranu gry*/
	public GameScreen(Game game) {
		this.game = game;

		setPanelSize();

	}

	/**Iniciuje obsluge myszki*/
	public void initInputs() {
		myMouseListener = new MyMouseListener(game);

		addMouseListener(myMouseListener);
		addMouseMotionListener(myMouseListener);

		requestFocus();
	}

	/**Tworzy okienko o ustalonej wielkosci*/
	private void setPanelSize() {
		size = new Dimension(1024, 768);

		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);

	}

	/**Tworzy komponent obslugi grafiki*/
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		game.getRender().render(g);

	}

}