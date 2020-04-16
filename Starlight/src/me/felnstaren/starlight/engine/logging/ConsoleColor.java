package me.felnstaren.starlight.engine.logging;

public enum ConsoleColor {

	RED("\033[0;31m"),
	YELLOW("\033[0;33m"),
	GREEN("\033[0;32m"),
	LIGHT_GRAY("\\e[0;37m"),
	DARK_GRAY("\\e[1;30m"),
	WHITE("\033[0;37m");
	
	private String ansi;
	
	private ConsoleColor(String ansi) {
		this.ansi = ansi;
	}
	
	@Override
	public String toString() {
		return ansi;
	}
	
}
