package managers;

import java.util.ArrayList;
import java.util.Arrays;

import events.Wave;
import scenes.Playing;

/**Klasa odpowiada za obsluge fali przeciwnikow*/
public class WaveManager {

	private ArrayList<Wave> waves = new ArrayList<>();
	private int enemySpawnTickLimit = 120 * 1;
	private int enemySpawnTick = enemySpawnTickLimit;
	private int enemyIndex, waveIndex;
	private int waveTickLimit = 60 * 5;
	private int waveTick = 0;
	private boolean waveStartTimer, waveTickTimerOver;

	/**Inicjacja obslugi*/
	public WaveManager(Playing playing) {
		createWaves();
	}

	/**Aktualizacja stanu fali*/
	public void update() {
		if (enemySpawnTick < enemySpawnTickLimit)
			enemySpawnTick++;

		if (waveStartTimer) {
			waveTick++;
			if (waveTick >= waveTickLimit) {
				waveTickTimerOver = true;
			}
		}
	}

	/**Zwiekszenie numeru fali*/
	public void increaseWaveIndex() {
		waveIndex++;
		waveTickTimerOver = false;
		waveStartTimer = false;
	}
	
	/**Sprawdzenie, czy poziom zostal ukonczony*/
	public void levelIsOver() {
		waveTickTimerOver = false;
		waveStartTimer = false;
	}
	
	/**Sprawdzenie, czy czas na kolejna fale*/
	public boolean isWaveTimerOver() {
		
		return waveTickTimerOver;
	}

	/**Rozpoczecie pomiaru czasu*/
	public void startWaveTimer() {
		waveStartTimer = true;
	}

	/**Pozyskanie kolejnego przeciwnika*/
	public int getNextEnemy() {
		enemySpawnTick = 0;
		return waves.get(waveIndex).getEnemyList().get(enemyIndex++);
	}

	/**Stworzenie fali wedlug podanej listy*/
	private void createWaves() {
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0))));
		waves.add(new Wave(new ArrayList<Integer>(Arrays.asList(1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1))));
	}

	/**Pozyskanie listy fali*/
	public ArrayList<Wave> getWaves() {
		return waves;
	}

	/**Sprawdzenie, czy powinien zostac dodany kolejny przeciwnik*/
	public boolean isTimeForNewEnemy() {
		return enemySpawnTick >= enemySpawnTickLimit;
	}

	/**Sprawdzenie, czy fala zostala ukonczona*/
	public boolean isThereMoreEnemiesInWave() {
		return enemyIndex < waves.get(waveIndex).getEnemyList().size();
	}

	/**Sprawdzenie, czy jest kolejna fala*/
	public boolean isThereMoreWaves() {
		return waveIndex + 1 < waves.size();
	}

	/**Zresetowanie indeksu przeciwnika*/
	public void resetEnemyIndex() {
		enemyIndex = 0;
	}

	/**Pozyskanie indeksu fali*/
	public int getWaveIndex() {
		return waveIndex;
	}

	/**Pozyskanie pozostalego czasu*/
	public float getTimeLeft() {
		float ticksLeft = waveTickLimit - waveTick;
		return ticksLeft / 60.0f;
	}

	/**Sprawdzenie, czy zegar fali jest wlaczony*/
	public boolean isWaveTimerStarted() {
		return waveStartTimer;
	}

	/**Zresetowanie stanu fali do stanu poczatkowego*/
	public void reset() {
		waves.clear();
		createWaves();
		enemyIndex = 0;
		waveIndex = 0;
		waveStartTimer = false;
		waveTickTimerOver = false;
		waveTick = 0;
		enemySpawnTick = enemySpawnTickLimit;
	}

}
