package me.felnstaren.starlight.engine.logging;

public enum Level {

	STREAM(ConsoleColor.LIGHT_GRAY, "[STREAM]", 10),
	DEBUG(ConsoleColor.LIGHT_GRAY, "[DEBUG]", 20),
	INFO(ConsoleColor.WHITE, "[INFO]", 30),
	WARNING(ConsoleColor.YELLOW, "[WARN]", 40),
	FATAL(ConsoleColor.RED, "[FATAL]", 50);
	
	private ConsoleColor color;
	private String prefix;
	private int priority;
	
	private Level(ConsoleColor color, String prefix, int priority) {
		this.color = color;
		this.prefix = prefix;
		this.priority = priority;
	}
	
	public ConsoleColor getColor() {
		return color;
	}
	
	public String getPrefix() {
		return prefix;
	}
	
	public int getPriority() {
		return priority;
	}
	
}
