package objects;

/**Klasa zwraca wartosci pozycji punktu poczatkowego i koncowego*/
public class PathPoint {
	private int xCord, yCord;

	/**Zaladowanie wartosci punktow*/
	public PathPoint(int xCord, int yCord) {
		this.xCord = xCord;
		this.yCord = yCord;
	}

	/**Pozyskanie wartosci pozycji w poziomie*/
	public int getxCord() {
		return xCord;
	}

	/**Ustawienie wartosci pozycji w poziomie*/
	public void setxCord(int xCord) {
		this.xCord = xCord;
	}

	/**Pozyskanie wartosci pozycji w pionie*/
	public int getyCord() {
		return yCord;
	}

	/**Ustawienie wartosci pozycji w pionie*/
	public void setyCord(int yCord) {
		this.yCord = yCord;
	}

}
