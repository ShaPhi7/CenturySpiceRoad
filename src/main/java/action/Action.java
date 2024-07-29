package action;

public enum Action {
	SETUP("Setup"),
	ACQUIRE("Acquire"),
	REST("Rest"),
	CLAIM("Claim"),
	PLAY("Play"),
	DISCARD("Discard");

	private final String actionText;

	private Action(String actionText) {
		this.actionText = actionText;
	}

	public String getActionText() {
		return actionText;
	}

	@Override
	public String toString() {
		return actionText;
	}
}
