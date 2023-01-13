package enemies;

import static helpz.Constants.Enemies.WOLNY;

import managers.EnemyManager;

/**Klasa zawiera funkcje przekazujaca parametry przeciwnika WOLNY do klasy Enemy*/
public class Wolny extends Enemy {

	public Wolny(float x, float y, int ID,EnemyManager em) {
		super(x, y, ID, WOLNY, em);
		
	}

}
