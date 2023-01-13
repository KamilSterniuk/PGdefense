package enemies;

import static helpz.Constants.Enemies.SZYBKI;

import managers.EnemyManager;

/**Klasa zawiera funkcje przekazujaca parametry przeciwnika SZYBKI do klasy Enemy*/
public class Szybki extends Enemy {

	public Szybki(float x, float y, int ID, EnemyManager em) {
		super(x, y, ID, SZYBKI,em);
	}

}
