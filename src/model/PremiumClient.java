package model;

public class PremiumClient extends Client {

	private int points;

	public PremiumClient(String client) {
		super(client);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public void addPoints(Amount amount) {
		// Save in the variable total the value of the variable amount.
		double total = amount.getValue();
		int points = 0;
		// If the total is upper a 0
		if (total > 0) {
			// Calculate the total of the points
			points = (int) (total / 10);
			// Add the points to the attribute
			setPoints(points);
		}
	}

	@Override
	public String toString() {
		return "Total de puntos Premium: " + points;
	}

}
