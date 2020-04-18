package me.felnstaren.starlight.game.object.entity.player;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.graphics.Image;
import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;
import me.felnstaren.starlight.game.object.CollisionFlag;
import me.felnstaren.starlight.game.object.entity.LivingEntity;
import me.felnstaren.starlight.game.object.entity.type.EntityType;
import me.felnstaren.starlight.game.world.World;

public class PlayerEntity extends LivingEntity {
	
	private boolean grounded = false;

	public PlayerEntity(World world, float x, float y) {
		super(EntityType.PLAYER_ENTITY, x, y, 0.2f, 0.4f, world, CollisionFlag.PASSTHROUGH);
		this.texture = new Image("entity/player/player.png");
		//bb.setRotation(0.45f);
	}

	public void render(GameContainer gc, GL2 gl) {
		Graphics.setColor(255, 255, 255);
		Graphics.setRotation(0);
		Graphics.drawTexture(gl, texture, x, y, width, height);
		super.render(gc, gl);
	}

	public void update(GameContainer gc, float delta_time) {
		float speed = 0.05f;
		
		//https://stackoverflow.com/questions/14888619/java-collision-detection-walls
		
		if(vx > 0.0001 || vx < -0.0001) vx *= 0.96;
		else vx = 0;
		//if(vy > 0.0001 || vy < -0.0001) vy *= 0.96;
		if(vy == 0) {
			vy = 0;
			grounded = true;
		}
		
		vy -= 0.05f;
		
		if(gc.getInput().isKeyPressed(KeyEvent.VK_W) && grounded) {
			vy = 2f;
			grounded = false;
		}
		if(gc.getInput().isKeyPressed(KeyEvent.VK_S)) vy -= speed;
		if(gc.getInput().isKeyPressed(KeyEvent.VK_A)) vx -= speed;
		if(gc.getInput().isKeyPressed(KeyEvent.VK_D)) vx += speed;
		super.update(gc, delta_time);
		
		if(x > gc.getWindow().getWidthScale() / 2 + width) x = -gc.getWindow().getWidthScale() / 2 - width + 0.1f;
		if(x < -gc.getWindow().getWidthScale() / 2 - width) x = gc.getWindow().getWidthScale() / 2 + width - 0.1f;
		if(y > gc.getWindow().getHeightScale() / 2 + height) y = -gc.getWindow().getHeightScale() / 2 - height + 0.1f;
		if(y < -gc.getWindow().getHeightScale() / 2 - height) y = gc.getWindow().getHeightScale() / 2 + height - 0.1f;
	}

}
