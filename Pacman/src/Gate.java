import java.awt.Color;
import java.awt.Graphics;

public class Gate extends Barrier {

	public Gate(int x, int y, Collectable col, boolean isBarrier) {
		super(x, y, col, isBarrier);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0, getHeight()/2-2, getWidth(), 5);
	}
	
}
