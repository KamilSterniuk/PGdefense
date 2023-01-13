package objects;

import java.awt.geom.Point2D;

/**Klasa odpowiada za przekazywanie danych pocisku*/
public class Projectile {

	private Point2D.Float pos;
	private int id, projectileType, dmg;
	private float xSpeed, ySpeed, rotation;
	private boolean active = true;

	/**Inicjacja pocisuku*/
	public Projectile(float x, float y, float xSpeed, float ySpeed, int dmg, float rotation, int id, int projectileType) {
		pos = new Point2D.Float(x, y);
		this.xSpeed = xSpeed;
		this.ySpeed = ySpeed;
		this.dmg = dmg;
		this.rotation = rotation;
		this.id = id;
		this.projectileType = projectileType;
	}

	/**Ustawienie kierunku ruchu*/
	public void move() {
		pos.x += xSpeed;
		pos.y += ySpeed;
	}

	/**Zwraca pozycje*/
	public Point2D.Float getPos() {
		return pos;
	}

	/**Ustawia pozycje*/
	public void setPos(Point2D.Float pos) {
		this.pos = pos;
	}

	/**Zwraca ID*/
	public int getId() {
		return id;
	}

	/**Zwraca typ*/
	public int getProjectileType() {
		return projectileType;
	}

	/**Sprawdzenie aktywnosci pocisku*/
	public boolean isActive() {
		return active;
	}

	/**Ustawienie aktywnosci pocisku*/
	public void setActive(boolean active) {
		this.active = active;
	}

	/**Zwraca obrazenia pocisku*/
	public int getDmg() {
		return dmg;
	}

	/**Uzyskanie poziomu rotacji pocisku (strzały)*/
	public float getRotation() {
		return rotation;
	}

}
