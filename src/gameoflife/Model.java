package gameoflife;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.math.BigInteger;
import java.util.Random;

public class Model {

	private BufferedImage state;
	BigInteger seed;
	private int width;
	private int height;

	public Model(int width, int height) {
		this.width = width;
		this.height = height;

		state = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		seed = new BigInteger(width * height, new Random());

		System.out.println("seed: " + seed);

		init();
	}

	private void init() {
		for (int x = 0; x < state.getWidth(); x++) {
			for (int y = 0; y < state.getHeight(); y++) {
				if (seed.testBit(x * y + y)) {
					state.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}
	}

	public BufferedImage getImage() {
		return state;
	}

	public void step() {

		BufferedImage newState = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

		for (int x = 0; x < state.getWidth(); x++) {
			for (int y = 0; y < state.getHeight(); y++) {
				if (positionSurvives(x, y)) {
					newState.setRGB(x, y, Color.WHITE.getRGB());
				}
			}
		}

		state = newState;
	}

	private boolean positionSurvives(int x, int y) {

		Neighbourhood hood = getNeighborhood(x, y);

		int nbrOfNeighbours = hood.nbrOfNeighbours();

		if (hood.isAlive()) {
			if (nbrOfNeighbours < 2) {
				return false;
			}
			if (nbrOfNeighbours <= 3) {
				return true;
			}
			return false;
		}

		return (nbrOfNeighbours == 3);

	}

	private Neighbourhood getNeighborhood(int x, int y) {
		Neighbourhood hood = new Neighbourhood();

		hood.state[0] = getValue(x - 1, y - 1);
		hood.state[1] = getValue(x, y - 1);
		hood.state[2] = getValue(x + 1, y - 1);

		hood.state[3] = getValue(x - 1, y);
		hood.state[4] = getValue(x, y);
		hood.state[5] = getValue(x + 1, y);

		hood.state[6] = getValue(x - 1, y + 1);
		hood.state[7] = getValue(x, y + 1);
		hood.state[8] = getValue(x + 1, y + 1);

		return hood;
	}

	boolean getValue(int x, int y) {

		if (x == -1) {
			x = state.getWidth() - 1;
		}
		if (y == -1) {
			y = state.getHeight() - 1;
		}

		if (x == state.getWidth()) {
			x = 0;
		}

		if (y == state.getHeight()) {
			y = 0;
		}

		return Color.WHITE.getRGB() == state.getRGB(x, y);
	}

	class Neighbourhood {
		private static final int SELF = 4;

		boolean[] state = new boolean[9];

		boolean isAlive() {
			return state[4];
		}

		int nbrOfNeighbours() {
			int count = 0;
			for (int i = 0; i < state.length; i++) {
				// 4 is center and thus the current cell itself
				if (state[i] && i != SELF) {
					count++;
				}
			}
			return count;
		}

	}

}
