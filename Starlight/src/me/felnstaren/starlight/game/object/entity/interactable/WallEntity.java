package me.felnstaren.starlight.game.object.entity.interactable;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.graphics.Image;
import me.felnstaren.starlight.game.object.CollisionFlag;
import me.felnstaren.starlight.game.object.entity.LivingEntity;
import me.felnstaren.starlight.game.object.entity.type.EntityType;
import me.felnstaren.starlight.game.world.World;

public class WallEntity extends LivingEntity {

	public WallEntity(float x, float y, float width, float height, World world) {
		super(EntityType.WALL, x, y, width, height, world, CollisionFlag.SOLID);
		this.texture = new Image("entity/neutral/crate.png");
	}
	
	public void render(GameContainer gc, GL2 gl) {
		Graphics.drawTexture(gl, texture, x, y, width, height);
		super.render(gc, gl);
	}

}
