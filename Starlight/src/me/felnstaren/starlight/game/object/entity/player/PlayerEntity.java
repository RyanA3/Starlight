package me.felnstaren.starlight.game.object.entity.player;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.geometry.Polygon;
import me.felnstaren.starlight.engine.geometry.Vertex;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.graphics.ImageResource;
import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;
import me.felnstaren.starlight.game.object.entity.LivingEntity;
import me.felnstaren.starlight.game.object.entity.type.EntityType;
import me.felnstaren.starlight.game.world.World;

public class PlayerEntity extends LivingEntity {

	public PlayerEntity(World world, float x, float y) {
		super(EntityType.PLAYER_ENTITY, x, y, 0.2f, 0.4f, world);
		this.texture = new ImageResource("entity/player/player.png");
		this.bb = new Polygon(new Vertex(0.1f, -0.1f), new Vertex(0.2f, -0.2f), new Vertex(0f, 0.25f), new Vertex(-0.2f, -0.2f), new Vertex(-0.1f, -0.1f));
	}

	public void render(GameContainer gc, GL2 gl) {
		Graphics.drawTexture(gl, texture, x, y, width, height);
		super.render(gc, gl);
	}

	public void update(GameContainer gc, float delta_time) {
		float speed = 0.05f;
		
		//https://stackoverflow.com/questions/14888619/java-collision-detection-walls
		Logger.log(Level.STREAM, "Player updating w/ delta time: " + delta_time);
		
		if(vx > 0.0001 || vx < -0.0001) vx *= 0.96;
		else vx = 0;
		if(vy > 0.0001 || vy < -0.0001) vy *= 0.96;
		else vy = 0;
		
		if(gc.getInput().isKeyPressed(KeyEvent.VK_W)) vy += speed;
		if(gc.getInput().isKeyPressed(KeyEvent.VK_S)) vy -= speed;
		if(gc.getInput().isKeyPressed(KeyEvent.VK_A)) vx -= speed;
		if(gc.getInput().isKeyPressed(KeyEvent.VK_D)) vx += speed;
		super.update(gc, delta_time);
	}

}
