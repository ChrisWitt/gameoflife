package gameoflife;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigInteger;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GameOfLife extends JFrame implements KeyListener {

	public static void main(String[] args) {
		new GameOfLife(1920 / 8, 1080 / 8, 300, new GameType());
	}

	int ticks;
	GameType type;
	Model model;
	private GamePane gamePane = new GamePane(null);;

	public GameOfLife(int x, int y, int ticks, GameType type) {
		super("GameOfLife (" + type + ")");

		this.ticks = ticks;
		this.type = type;
		System.out.println(type);

		addKeyListener(this);

		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}

				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				add(gamePane);
				setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
				pack();
				setLocationRelativeTo(null);
				setVisible(true);

//				model = new Model(getWidth() / 10, getHeight() / 10, type);
				
				model = new Model(4,4,new GameTypeConvey(), BigInteger.valueOf(7l * 16));

				gamePane.setModel(model);

				Timer timer = new Timer(ticks, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						model.step();
						repaint();
						// System.out.println("frame size: " + getSize());
					}
				});
				// timer.start();
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		if (arg0.getKeyChar() == 's') {
			model.step();
			repaint();
		}

		if (arg0.getKeyChar() == 'r') {
			model = new Model(getWidth() / 10, getHeight() / 10);
			gamePane.setModel(model);
			setTitle(model.type.toString());
		}

		if (arg0.getKeyChar() == 'c') {
			model = new Model(getWidth() / 10, getHeight() / 10, new GameTypeConvey());
			gamePane.setModel(model);
			setTitle(model.type.toString());
		}

		if (arg0.getKeyChar() == 'g') {
			model.toggleColorMode();
			if (model.born.equals(Color.WHITE)) {
				model.setBorn(Color.GREEN);
				model.setDied(Color.RED);
			} else {
				model.setBorn(Color.WHITE);

			}
		}

	}

}
