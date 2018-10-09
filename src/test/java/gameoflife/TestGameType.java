package gameoflife;

import org.junit.Test;

public class TestGameType {

	@Test
	public void test() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			GameType t = new GameType();
			System.out.println(t);
			Thread.sleep(2);
		}
	}

}
