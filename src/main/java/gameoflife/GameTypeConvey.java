package gameoflife;

public class GameTypeConvey extends GameType {
	public GameTypeConvey() {
		
		super();
		
		keepAlive = new boolean[keepAlive.length];
		birth = new boolean[birth.length];
		
		keepAlive[2] = true;
		keepAlive[3] = true;
		
		birth[3] = true;
		
		
	}
}
