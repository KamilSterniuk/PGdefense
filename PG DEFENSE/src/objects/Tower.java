package objects;

/**Klasa odpowiada za przekazywanie danych elementow strzelajacych*/
public class Tower {

	private int x, y, id, towerType, cdTick, dmg;
	private float range, cooldown;
	
	/**Inicjacja danych*/
	public Tower(int x, int y, int id, int towerType) {
		this.x = x;
		this.y = y;
		this.id = id;
		this.towerType = towerType;
		setDefaultDmg();
		setDefaultRange();
		setDefaultCooldown();
	}

	/**Aktualizacja elementu*/
	public void update() {
		cdTick++;
	}

	/**Sprawdzenie, czy minal czas potrzebny do wystrzalu kolejnego pocisku*/
	public boolean isCooldownOver() {

		return cdTick >= cooldown;
	}

	/**Zresetowanie czasu potrzebnego do wystrzalu kolejnego pocisku*/
	public void resetCooldown() {
		cdTick = 0;
	}

	/**Ustawienie czasu potrzebnego do wystrzalu kolejnego pocisku*/
	private void setDefaultCooldown() {
		cooldown = helpz.Constants.Towers.GetDefaultCooldown(towerType);

	}
	
	/**Ustawienie zasiegu*/
	private void setDefaultRange() {
		range = helpz.Constants.Towers.GetDefaultRange(towerType);

	}

	/**Ustawienie obrazen*/
	private void setDefaultDmg() {
		dmg = helpz.Constants.Towers.GetStartDmg(towerType);

	}

	/**Uzyskanie pozycji poziomej*/
	public int getX() {
		return x;
	}

	/**Ustawienie pozycji poziomej*/
	public void setX(int x) {
		this.x = x;
	}

	/**Uzyskanie pozycji pionowej*/
	public int getY() {
		return y;
	}

	/**Ustawienie pozycji pionowej*/
	public void setY(int y) {
		this.y = y;
	}

	/**Zwraca ID*/
	public int getId() {
		return id;
	}

	/**Ustawia ID*/
	public void setId(int id) {
		this.id = id;
	}

	/**Zwraca typ*/
	public int getTowerType() {
		return towerType;
	}

	/**Ustawia typ*/
	public void setTowerType(int towerType) {
		this.towerType = towerType;
	}

	/**Zwraca obrazenia*/
	public int getDmg() {
		return dmg;
	}

	/**Zwraca zasieg*/
	public float getRange() {
		return range;
	}

	/**Zwraca czas potrzebny do wystrzalu kolejnego pocisku*/
	public float getCooldown() {
		return cooldown;
	}
}
