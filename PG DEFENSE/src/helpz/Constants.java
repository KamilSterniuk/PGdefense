package helpz;

import scenes.Settings;

/**Klasa zawiera stale wartosci dla gry*/
public class Constants {
	
	static int level = 3;
	
	/**Ustala poziom trudnosci*/
	public static void getLevel()	{
		
		switch(Settings.sendLevel())	{
			case 1:
				level = 1;
				break;
			case 2:
				level = 2;
				break;
			case 3:
				level = 3;
			break;
			default:
					
		}
	}

	/**Zawiera wartosci dla pociskow*/
	public static class Projectiles {
		
		public static final int STRZALA = 0;
		public static final int CZARY = 1;
		public static final int BOMBA = 2;

		/**Zwraca wartosc predkosci pocisku*/
		public static float GetSpeed(int type) {
			switch (type) {
			case STRZALA:
				return 4f * level;
			case BOMBA:
				return 2f * level;
			case CZARY:
				return 3f * level;
			}
			return 0f;
		}
	}

	/**Zawiera wartosci dla obiektow strzelajacych*/
	public static class Towers {
		public static final int ARMATA = 0;
		public static final int DOKTORANT = 1;
		public static final int PROFESOR = 2;

		/**Zwraca koszt*/
		public static int GetTowerCost(int towerType) {
			switch (towerType) {
			case ARMATA:
				return 90;
			case DOKTORANT:
				return 30;
			case PROFESOR:
				return 60;
			}
			return 0;
		}

		/**Zwraca nazwe*/
		public static String GetName(int towerType) {
			switch (towerType) {
			case ARMATA:
				return "Armata DS2";
			case DOKTORANT:
				return "Doktorant";
			case PROFESOR:
				return "Profesor";
			}
			return "";
		}
		
		/**Zwraca opis*/
		public static String GetDescription(int towerType)	{
			switch (towerType) {
			case ARMATA:
				return "Wykładowcy używają armaty spod DS2 do ostrzału";
			case DOKTORANT:
				return "Laboratoryjna wejściówka powoduje lekkie obrażenia";
			case PROFESOR:
				return "Egzamin oprócz obrażeń powoduje efekt spowolnienia";
			}
			return"";
		}

		/**Zwraca zadawane obrazenia*/
		public static int GetStartDmg(int towerType) {
			switch (towerType) {
			case ARMATA:
				return 30 * level;
			case DOKTORANT:
				return 5 * level;
			case PROFESOR:
				return 10 * level;
			}

			return 0;
		}

		/**Zwraca zasieg*/
		public static float GetDefaultRange(int towerType) {
			switch (towerType) {
			case ARMATA:
				return 130;
			case DOKTORANT:
				return 100;
			case PROFESOR:
				return 100;
			}

			return 0;
		}

		/**Zwraca czas odstepu pomiedzy strzalami*/
		public static float GetDefaultCooldown(int towerType) {
			switch (towerType) {
			case ARMATA:
				return 150;
			case DOKTORANT:
				return 60;
			case PROFESOR:
				return 80;
			}

			return 0;
		}
	}

	/**Zawiera liczbowe interpretacje kierunkow ruchu*/
	public static class Direction {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	/**Zawiera wartosci dla przeciwnikow*/
	public static class Enemies {

		public static final int SZYBKI = 0;
		public static final int WOLNY = 1;

		/**Zwraca nagrode za zabicie przeciwnika*/
		public static int GetReward(int enemyType) {
			switch (enemyType) {
			case SZYBKI:
				return 5;
			case WOLNY:
				return 25;
			}
			return 0;
		}

		/**Zwraca predkosc*/
		public static float GetSpeed(int enemyType) {
			switch (enemyType) {
			case SZYBKI:
				return 0.8f;
			case WOLNY:
				return 0.4f;
			}
			return 0;
		}

		/**Zwraca zdrowie*/
		public static int GetStartHealth(int enemyType) {
			switch (enemyType) {
			case SZYBKI:
				if(level == 3)
					return 100;
				else if(level == 2)
					return 120;
				else if(level == 1)
					return 150;
			case WOLNY:
				return 250;
			}
			return 0;
		}
	}

	/**Zawiera liczbowe interpretacje tekstur podloza*/
	public static class Tiles {
		public static final int FINISH_TILE = 0;
		public static final int GRASS_TILE = 1;
		public static final int ROAD_TILE = 2;
	}

}
