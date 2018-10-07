package gameoflife;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GameOfLife {

	public static void main(String[] args) {
		new GameOfLife(320, 240, 300);
	}

	int ticks;
	
	public GameOfLife(int x, int y, int ticks) {
		
		this.ticks = ticks;
		
		Model model = new Model(x, y);
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}

				JFrame frame = new JFrame("GameOfLife");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new TestPane(model));
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				
				Timer timer = new Timer(ticks, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						model.step();
						frame.repaint();
					}
				});
				timer.start();
			}
		});
	}

	public class TestPane extends JPanel{

		Model model;
		
		public TestPane(Model model) {
			this.model = model;
		}


		@Override
		public Dimension getPreferredSize() {
			return new Dimension(640, 480);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			BufferedImage currentFrame = model.getImage();
			if (currentFrame != null) {
				Graphics2D g2d = (Graphics2D) g.create();
				double horizontalScale = currentFrame.getWidth() / new Double(getWidth());
				double verticalScale = currentFrame.getHeight() / new Double(getHeight());

				double scale = 1.0;
				if (horizontalScale >= verticalScale) {
					scale = horizontalScale;
				} else {
					scale = verticalScale;
				}

				g2d.drawImage(scale(currentFrame, 1 / scale), 0, 0, this);
				g2d.dispose();
			}
		}

		public BufferedImage scale(BufferedImage img, double scale) {
			BufferedImage after = new BufferedImage(new Double(Math.floor(img.getWidth() * scale)).intValue(),
					new Double(Math.floor(img.getHeight() * scale)).intValue(), img.getType());
			AffineTransform at = new AffineTransform();
			at.scale(scale, scale);
			AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			scaleOp.filter(img, after);
			return after;
		}

		
	}

}
