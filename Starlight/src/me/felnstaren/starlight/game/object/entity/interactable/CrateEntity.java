package me.felnstaren.starlight.game.object.entity.interactable;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.graphics.ImageResource;
import me.felnstaren.starlight.game.object.entity.LivingEntity;
import me.felnstaren.starlight.game.object.entity.player.PlayerEntity;
import me.felnstaren.starlight.game.object.entity.type.EntityType;
import me.felnstaren.starlight.game.world.World;

public class CrateEntity extends LivingEntity {

	public CrateEntity(World world, float x, float y) {
		super(EntityType.CRATE, x, y, 0.5f, 0.25f, world);
		this.texture = new ImageResource("entity/neutral/crate.png");
	}

	public void render(GameContainer gc, GL2 gl) {
		Graphics.drawTexture(gl, texture, x, y, width, height);
		super.render(gc, gl);
	}

	public void update(GameContainer gc, float delta_time) {
		super.update(gc, delta_time);
		
		PlayerEntity col = (PlayerEntity) world.getColliding(this, EntityType.PLAYER_ENTITY);
		if(col == null) return;
		
		col.translate(-col.getVX() * delta_time, -col.getVY() * delta_time);
		col.setVelocity(0, 0);
	}

}
