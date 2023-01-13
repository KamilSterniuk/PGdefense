package helpz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import objects.PathPoint;

/**Klasa odpowiada za wczytanie poziomu oraz zapis punktów początkowych i końcowych*/
public class LoadSave {

	/**Wczytuje plik graficzny zawierajacy tekstury*/
	public static BufferedImage getSpriteAtlas() {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getClassLoader().getResourceAsStream("tekstury.png");

		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**Sprawdza czy istnieje plik zawierajacy poziom*/
	public static void CreateLevel(String name, int[] idArr) {
		File newLevel = new File("res/" + name + ".txt");
		if (newLevel.exists()) {
			System.out.println("File: " + name + " already exists!");
			return;
			}
	}

	/**Odczytuje poziom z pliku*/
	private static ArrayList<Integer> ReadFromFile(File file) {
		ArrayList<Integer> list = new ArrayList<>();

		try {
			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				list.add(Integer.parseInt(sc.nextLine()));
			}

			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return list;
	}

	/**Odczytuje punkt poczatkowy i koncowy z pliku*/
	public static ArrayList<PathPoint> GetLevelPathPoints(String name) {
		File lvlFile = new File("res/" + name + ".txt");

		if (lvlFile.exists()) {
			ArrayList<Integer> list = ReadFromFile(lvlFile);
			ArrayList<PathPoint> points = new ArrayList<>();
			points.add(new PathPoint(list.get(576), list.get(577)));
			points.add(new PathPoint(list.get(578), list.get(579)));

			return points;

		} else {
			System.out.println("File: " + name + " does not exists! ");
			return null;
		}
	}

	/**Tworzy macierz zawierajaca wartosci liczbowe z gotowym poziomem*/
	public static int[][] GetLevelData(String name) {
		File lvlFile = new File("res/" + name + ".txt");

		if (lvlFile.exists()) {
			ArrayList<Integer> list = ReadFromFile(lvlFile);
			return Utilz.ArrayListTo2Dint(list, 24, 24);

		} else {
			System.out.println("File: " + name + " does not exists! ");
			return null;
		}

	}
}
