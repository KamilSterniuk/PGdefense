package managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import objects.Projectile;
import objects.Tower;
import scenes.Playing;
import static helpz.Constants.Towers.*;
import static helpz.Constants.Projectiles.*;

/**Klasa odpowiada za obsluge pociskow uzytych w grze*/
public class ProjectileManager {

	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	private ArrayList<Explosion> explosions = new ArrayList<>();
	private BufferedImage[] proj_imgs, explo_imgs;
	private int proj_id = 0;

	/**Inicjacja obslugi pociskow*/
	public ProjectileManager(Playing playing) {
		this.playing = playing;
		importImgs();

	}

	/**Zaladowanie grafiki*/
	private void importImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		proj_imgs = new BufferedImage[3];

		for (int i = 0; i < 3; i++)
			proj_imgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
		importExplosion(atlas);
	}

	/**Zaladowanie grafik efektu eksplozji*/
	private void importExplosion(BufferedImage atlas) {
		explo_imgs = new BufferedImage[7];

		for (int i = 0; i < 7; i++)
			explo_imgs[i] = atlas.getSubimage(i * 32, 32 * 2, 32, 32);

	}

	/**Zaladowanie wlasciwosci lotu pocisku*/
	public void newProjectile(Tower t, Enemy e) {
		int type = getProjType(t);

		int xDist = (int) (t.getX() - e.getX());
		int yDist = (int) (t.getY() - e.getY());
		int totDist = Math.abs(xDist) + Math.abs(yDist);

		float xPer = (float) Math.abs(xDist) / totDist;

		float xSpeed = xPer * helpz.Constants.Projectiles.GetSpeed(type);
		float ySpeed = helpz.Constants.Projectiles.GetSpeed(type) - xSpeed;

		if (t.getX() > e.getX())
			xSpeed *= -1;
		if (t.getY() > e.getY())
			ySpeed *= -1;

		float rotate = 0;

		if (type == STRZALA) {
			float arcValue = (float) Math.atan(yDist / (float) xDist);
			rotate = (float) Math.toDegrees(arcValue);

			if (xDist < 0)
				rotate += 180;
		}

		projectiles.add(new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, t.getDmg(), rotate, proj_id++, type));

	}

	/**Aktualizacja stanu pociskow*/
	public void update() {
		for (Projectile p : projectiles)
			if (p.isActive()) {
				p.move();
				if (isProjHittingEnemy(p)) {
					p.setActive(false);
					if (p.getProjectileType() == BOMBA) {
						explosions.add(new Explosion(p.getPos()));
						explodeOnEnemies(p);
					}
				} else if (isProjOutsideBounds(p)) {
					p.setActive(false);
				}
			}

		for (Explosion e : explosions)
			if (e.getIndex() < 7)
				e.update();
	}

	//*Obsluga eksplozji bomby*/
	private void explodeOnEnemies(Projectile p) {
		for (Enemy e : playing.getEnemyManger().getEnemies()) {
			if (e.isAlive()) {
				float radius = 40.0f;

				float xDist = Math.abs(p.getPos().x - e.getX());
				float yDist = Math.abs(p.getPos().y - e.getY());

				float realDist = (float) Math.hypot(xDist, yDist);

				if (realDist <= radius)
					e.hurt(p.getDmg());
			}

		}

	}

	/**Sprawdzenie, czy pocisk uderzyl w przeciwnika*/
	private boolean isProjHittingEnemy(Projectile p) {
		for (Enemy e : playing.getEnemyManger().getEnemies()) {
			if (e.isAlive())
				if (e.getBounds().contains(p.getPos())) {
					e.hurt(p.getDmg());
					if (p.getProjectileType() == CZARY)
						e.slow();

					return true;
				}
		}
		return false;
	}
	/**Sprawdzenie, czy pocisk znajduje sie wewnatr pola gry*/
	private boolean isProjOutsideBounds(Projectile p) {
		if (p.getPos().x >= 0)
			if (p.getPos().x <= 768)
				if (p.getPos().y >= 0)
					if (p.getPos().y <= 768)
						return false;
		return true;
	}

	/**Wyswietlanie zawartosci na ekran*/
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		for (Projectile p : projectiles)
			if (p.isActive()) {
				if (p.getProjectileType() == STRZALA) {
					g2d.translate(p.getPos().x, p.getPos().y);
					g2d.rotate(Math.toRadians(p.getRotation()));
					g2d.drawImage(proj_imgs[p.getProjectileType()], -16, -16, null);
					g2d.rotate(-Math.toRadians(p.getRotation()));
					g2d.translate(-p.getPos().x, -p.getPos().y);
				} else {
					g2d.drawImage(proj_imgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
				}
			}

		drawExplosions(g2d);

	}

	/**Wyswietlanie efektu eksplozji*/
	private void drawExplosions(Graphics2D g2d) {
		for (Explosion e : explosions)
			if (e.getIndex() < 7)
				g2d.drawImage(explo_imgs[e.getIndex()], (int) e.getPos().x - 16, (int) e.getPos().y - 16, null);
	}

	/**Pozyskanie typu pocisku*/
	private int getProjType(Tower t) {
		switch (t.getTowerType()) {
		case DOKTORANT:
			return STRZALA;
		case ARMATA:
			return BOMBA;
		case PROFESOR:
			return CZARY;
		}
		return 0;
	}

	/**Obsluguje efekt eksplozji*/
	public class Explosion {

		private Point2D.Float pos;
		private int exploTick, exploIndex;

		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}

		public void update() {
			exploTick++;
			if (exploTick >= 6) {
				exploTick = 0;
				exploIndex++;
			}
		}

		public int getIndex() {
			return exploIndex;
		}

		public Point2D.Float getPos() {
			return pos;
		}
	}

	/**Usuniecie pociskow i eksplozji*/
	public void reset() {
		projectiles.clear();
		explosions.clear();

		proj_id = 0;
	}

}
