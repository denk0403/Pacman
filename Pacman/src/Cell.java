import java.awt.Color;
import java.awt.Graphics;

public class Cell extends GridSquare {

	public Color color = Color.BLACK;

	public Cell(int x, int y, Collectable col, boolean isBarrier) {
		super(x, y, col, isBarrier);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paintComponent(Graphics g) {
		setBackground(color);
		// g.fillRect(0, 0, getWidth(), getHeight());
		if (color == Color.BLACK) {
			g.setColor(Color.WHITE);
			if (getCol() == Collectable.POINT) {
				g.fillOval(getWidth() / 2 - 2, getWidth() / 2 - 2, 4, 4);
			} else if (getCol() == Collectable.POWER) {
				g.fillOval(getWidth() / 2 - 4, getWidth() / 2 - 4, 8, 8);
			}
		}
	}

}
