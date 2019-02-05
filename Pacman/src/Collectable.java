
public enum Collectable {
	
	FRUIT (5), POINT (1), POWER (2), NONE (0);
	
	private final int worth;
	
	Collectable(int worth) {
		this.worth = worth;
	}

	public int getWorth() {
		return worth;
	}
}
