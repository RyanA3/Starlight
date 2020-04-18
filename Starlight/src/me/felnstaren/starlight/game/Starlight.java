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
	
	private int r = 0;
	private int g = 85;
	private int b = 170;
	private int rdir = 1;
	private int gdir = -1;
	private int bdir = 1;
	
	public void init(GameContainer gc, GL2 gl) {
		world = new World("world");
		player = new PlayerEntity(world, 0.0f, 0.0f);
		crate = new CrateEntity(world, -3f, -0.5f);
		crate.setVelocity(2f, 0);
		world.spawnEntity(player);
		world.spawnEntity(crate);
	}

	public void update(GameContainer gc, float delta_time) {
		world.updateEntities(gc, delta_time);
		r += rdir;
		g += gdir;
		b += bdir;
		if(r > 254 || r < 1) rdir *= -1;
		if(g > 254 || g < 1) gdir *= -1;
		if(b > 254 || b < 1) bdir *= -1;
	}

	public void render(GameContainer gc, GL2 gl) {
		Graphics.setColor(r, g, b);
		Graphics.drawText(gl, "NerdTyler     Mecrazy13\\n\\n  Felnstaren\\n          Geko96", -4.4f, 2f, 0.095f);
		Graphics.setColor(255, 255, 255);
		world.renderEntities(gc, gl);
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
