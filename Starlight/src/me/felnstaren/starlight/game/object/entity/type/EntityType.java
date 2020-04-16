package me.felnstaren.starlight.game.object.entity.type;

public enum EntityType {

	PLAYER_ENTITY("Player"),
	CRATE("Crate");
	
	
	
	private String name;
	
	private EntityType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
