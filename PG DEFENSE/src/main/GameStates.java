package main;

/**Zawiera mozliwe sceny gry*/
public enum GameStates {

	PLAYING, MENU, SETTINGS, GAME_OVER, LEVEL_COMPLETED, WIN;

	public static GameStates gameState = MENU;

	public static void SetGameState(GameStates state) {
		gameState = state;
	}

}
