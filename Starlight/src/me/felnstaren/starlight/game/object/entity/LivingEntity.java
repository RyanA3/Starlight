package me.felnstaren.starlight.game.object.entity;

import me.felnstaren.starlight.game.object.entity.type.EntityType;
import me.felnstaren.starlight.game.world.World;

public abstract class LivingEntity extends Entity {

	protected boolean is_alive = true;
	
	public LivingEntity(EntityType type, float x, float y, float width, float height, World world) {
		super(type, x, y, width, height, world);
	}
	
	public boolean isAlive() {
		return this.is_alive;
	}

}
