import java.awt.Color;
import java.awt.Graphics;

public class Barrier extends GridSquare {

	public Barrier(int x, int y, Collectable col, boolean isBarrier) {
		super(x, y, col, isBarrier);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		setBackground(Color.BLUE);
	}

}
