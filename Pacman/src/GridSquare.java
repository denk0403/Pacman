import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public abstract class GridSquare extends JPanel { //let every grid square be responsible for drawing itself
	private final int x, y;
	private Collectable col;
	private boolean isBarrier;
	private static boolean pickClip;
	
	static AudioClip clip1 = Applet.newAudioClip(GridSquare.class.getResource("/EatingSound1.wav"));
	static AudioClip clip2 = Applet.newAudioClip(GridSquare.class.getResource("/EatingSound2.wav"));
	
	GridSquare(int x, int y, Collectable col, boolean isBarrier) {
		super();
		this.x = x;
		this.y = y;
		this.col = col;
		this.isBarrier = isBarrier;
		setPreferredSize(new Dimension(20,20));
	}
	
	public boolean isBarrier() {
		return isBarrier;
	}
	
	public void collect() {
		if (col != Collectable.NONE) {
			if (pickClip) {
				pickClip = false;
				clip1.play();
			} else {
				pickClip = true; 
				clip2.play();
			}
			
		}
		col = Collectable.NONE;
		
	}
	
	public Collectable getCol() {
		return col;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}
	
}
