package helpz;

import java.util.ArrayList;

/**Klasa zawiera element zamieniajacy liste na macierz oraz mieracy odleglosci*/
public class Utilz {

	/**Z zadanej listy wartosci tworzy macierz o podanym w argumencie rozmiarach*/
	public static int[][] ArrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {
		int[][] newArr = new int[ySize][xSize];

		for (int j = 0; j < newArr.length; j++)
			for (int i = 0; i < newArr[j].length; i++) {
				int index = j * ySize + i;
				newArr[j][i] = list.get(index);
			}
		return newArr;
	}
	
	/**Zwraca dystans pomiedzy obiektami*/
	public static int GetHypoDistance(float x1, float y1, float x2, float y2) {
		float xDiff = Math.abs(x1 - x2);
		float yDiff = Math.abs(y1 - y2);

		return (int) Math.hypot(xDiff, yDiff);
	}
}
