package me.felnstaren.starlight.engine.logging;

public class Logger {
	
	public static Level log_level = Level.INFO;
	public static boolean colors = true;

	public static void log(Level level, String message) {
		if(level.getPriority() < log_level.getPriority()) return;
		message = level.getPrefix() + " " + message;
		if(colors) message = level.getColor() + message;
		System.out.println(message);
	}
	
}
