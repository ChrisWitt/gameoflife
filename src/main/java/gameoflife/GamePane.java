package gameoflife;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePane extends JPanel {

	Model model;

	public GamePane(Model model) {
		this.model = model;
	}

	public void setModel(Model model) {
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
			double horizontalScale = currentFrame.getWidth() / Double.valueOf(getWidth());
			double verticalScale = currentFrame.getHeight() / Double.valueOf(getHeight());

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
		// AffineTransformOp scaleOp = new AffineTransformOp(at,
		// AffineTransformOp.TYPE_BILINEAR);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		scaleOp.filter(img, after);
		return after;
	}

}
