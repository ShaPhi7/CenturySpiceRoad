package action;

import java.util.Optional;

public enum Action {
	SETUP("Setup"),
	ACQUIRE("Acquire"),
	REST("Rest"),
	CLAIM("Claim"),
	PLAY("Play"),
	DISCARD("Discard"),
	EXIT("Exit");

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
	
	public static Optional<Action> fromString(String text) {
        for (Action action : Action.values()) {
            if (action.getActionText().equalsIgnoreCase(text)) {
                return Optional.of(action);
            }
        }
        return Optional.empty();
    }
}
