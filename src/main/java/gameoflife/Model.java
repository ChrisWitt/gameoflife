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

	boolean isColorMode = false;
	Color born = Color.WHITE;
	Color alive = Color.WHITE;
	Color died = Color.BLACK;

	GameType type;

	public Model(int width, int height) {
		this(width, height, new GameType());
	}

	public Model(int width, int height, GameType type) {
		this(width, height, type, new BigInteger(width * height, new Random()));
	}

	Model(int width, int height, GameType type, BigInteger seed) {
		this.width = width;
		this.height = height;
		this.type = type;
		this.seed = seed;

		state = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		System.out.println("seed: " + seed);

		init();
	}

	private void init() {
		for (int y = 0; y < state.getHeight(); y++) {
			for (int x = 0; x < state.getWidth(); x++) {
				if (seed.testBit(y * state.getWidth() + x)) {
					state.setRGB(x, y, born.getRGB());
				}
			}
		}
	}

	public BufferedImage getImage() {
		return state;
	}

	public void step() {

		BufferedImage newState = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int x = 0; x < state.getWidth(); x++) {
			for (int y = 0; y < state.getHeight(); y++) {
				updatePosition(newState, x, y);
			}
		}

		state = newState;
	}

	private void updatePosition(BufferedImage newState, int x, int y) {
		Neighbourhood hood = getNeighborhood(x, y);

		int nbrOfNeighbours = hood.nbrOfNeighbours();

		if (isAlive(x, y)) {
			if (type.keepAlive(nbrOfNeighbours)) {
				newState.setRGB(x, y, alive.getRGB());
			} else {
				newState.setRGB(x, y, died.getRGB());
			}
		} else if (type.birth(nbrOfNeighbours)) {
			newState.setRGB(x, y, born.getRGB());
		}
	}

	private boolean isAlive(int x, int y) {
		return state.getRGB(x, y) == alive.getRGB() || state.getRGB(x, y) == born.getRGB();
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

		return isAlive(x, y);
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

	public void setBorn(Color born) {
		this.born = born;
	}

	public void setDied(Color died) {
		this.died = died;
	}

	public void setAlive(Color alive) {
		this.alive = alive;
	}

	public void toggleColorMode() {
		isColorMode = !isColorMode;
		if (isColorMode) {
			born = Color.GREEN;
			alive = Color.WHITE;
			died = Color.RED;
		} else {
			born = Color.WHITE;
			alive = Color.WHITE;
			died = Color.BLACK;

		}
	}

}
