package gameoflife;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestGameType {

	@Test
	void test() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			GameType t = new GameType();
			System.out.println(t);
			Thread.sleep(2);
		}
	}

}
