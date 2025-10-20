package functions.function3;

public class KeywordObject {
	private final String foundKeyword;
	private final int timeAppears;
	
	public KeywordObject(String foundKeyword, int timeAppears) {
		this.foundKeyword = foundKeyword;
		this.timeAppears = timeAppears;
	}

	public String getFoundKeyword() {
		return foundKeyword;
	}

	public int getTimeAppears() {
		return timeAppears;
	}
	
	
}
