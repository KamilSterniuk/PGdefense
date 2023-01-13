package scenes;

import java.awt.Graphics;

/**Interfejs zawiera metody, ktore musza byc zawarte w scenach*/
public interface SceneMethods {

	public void render(Graphics g);

	public void mouseClicked(int x, int y);

	public void mouseMoved(int x, int y);

	public void mousePressed(int x, int y);

	public void mouseReleased(int x, int y);
}
