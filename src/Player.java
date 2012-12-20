
public class Player {
	private String alias;
	private int score;
	private static int nbAnon = 0;
	
	public Player(String alias) {
		this.alias = alias;
		this.score = 0;
	}
	
	public Player() {
		nbAnon++;
		this.alias = "Player " + nbAnon;
		this.score = 0;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
