package me.felnstaren.starlight.game.object.entity.interactable;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.graphics.ImageResource;
import me.felnstaren.starlight.game.object.CollisionFlag;
import me.felnstaren.starlight.game.object.entity.LivingEntity;
import me.felnstaren.starlight.game.object.entity.type.EntityType;
import me.felnstaren.starlight.game.world.World;

public class CrateEntity extends LivingEntity {

	public CrateEntity(World world, float x, float y) {
		super(EntityType.CRATE, x, y, 0.5f, 0.25f, world, CollisionFlag.SOLID);
		this.texture = new ImageResource("entity/neutral/crate.png");
	}

	public void render(GameContainer gc, GL2 gl) {
		Graphics.drawTexture(gl, texture, x, y, width, height);
		super.render(gc, gl);
	}

	public void update(GameContainer gc, float delta_time) {
		super.update(gc, delta_time);
	}

}
