package events;

import java.util.ArrayList;

/**Klasa inicjuje powstanie i przekazywanie fali przeciwnikow*/
public class Wave {
	private ArrayList<Integer> enemyList;

	public Wave(ArrayList<Integer> enemyList) {
		this.enemyList = enemyList;
	}

	public ArrayList<Integer> getEnemyList() {
		return enemyList;
	}

}