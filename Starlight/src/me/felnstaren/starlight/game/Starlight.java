package me.felnstaren.starlight.game;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.AbstractGame;
import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;
import me.felnstaren.starlight.game.object.entity.interactable.CrateEntity;
import me.felnstaren.starlight.game.object.entity.player.PlayerEntity;
import me.felnstaren.starlight.game.world.World;

public class Starlight extends AbstractGame {
	
	private World world;
	private CrateEntity crate;
	private PlayerEntity player;
	
	public void init(GameContainer gc, GL2 gl) {
		world = new World("world");
		player = new PlayerEntity(world, 0.0f, 0.0f);
		crate = new CrateEntity(world, -1.0f, -1.0f);
		crate.setVelocity(0.25f, 0.25f);
		world.spawnEntity(player);
		world.spawnEntity(crate);
	}

	public void update(GameContainer gc, float delta_time) {
		world.updateEntities(gc, delta_time);
	}

	public void render(GameContainer gc, GL2 gl) {
		world.renderEntities(gc, gl);
		Graphics.setColor(255, 255, 255);
		Graphics.drawText(gl, "Hello, World!", -4, 1, 0.1f);
	}
	
	
	
	public World getWorld() {
		return world;
	}
	
	
	
	public static void main(String[] args) {
		Logger.log_level = Level.STREAM;
		Logger.colors = false;
		GameContainer gc = new GameContainer(new Starlight());
		gc.start();
	}

}
