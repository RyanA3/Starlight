package me.felnstaren.starlight.game;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.AbstractGame;
import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;
import me.felnstaren.starlight.engine.ui.Button;
import me.felnstaren.starlight.game.object.entity.interactable.CrateEntity;
import me.felnstaren.starlight.game.object.entity.interactable.WallEntity;
import me.felnstaren.starlight.game.object.entity.player.PlayerEntity;
import me.felnstaren.starlight.game.world.World;

public class Starlight extends AbstractGame {
	
	private World world;
	private CrateEntity crate;
	private PlayerEntity player;
	private Button button;
	
	private int r = 0;
	private int g = 85;
	private int b = 170;
	private int rdir = 1;
	private int gdir = -1;
	private int bdir = 1;
	
	public void init(GameContainer gc, GL2 gl) {
		world = new World("world");
		player = new PlayerEntity(world, 0.0f, 0.0f);
		crate = new CrateEntity(world, -5f, -1.9f);
		crate.setVelocity(3f, 0);
		//world.spawnEntity(player);
		//world.spawnEntity(crate);
		//world.spawnEntity(new WallEntity(0.01f, -2.25f, 9.98f, 0.25f, world));
		
		button = new Button(1, 1, 2, 0.5f) {
			public void onPress(GameContainer gc, float delta_time) {
				Logger.log(Level.INFO, "Pressed");
			}};
	}

	public void update(GameContainer gc, float delta_time) {
		world.updateEntities(gc, delta_time);
		r += rdir;
		g += gdir;
		b += bdir;
		if(r > 254 || r < 1) rdir *= -1;
		if(g > 254 || g < 1) gdir *= -1;
		if(b > 254 || b < 1) bdir *= -1;
		
		//button.update(gc, delta_time);
	}

	public void render(GameContainer gc, GL2 gl) {
		world.renderEntities(gc, gl);
		Graphics.setColor(r, g, b);
		Graphics.drawText(gl, "FPS: " + gc.getFPS(), -4.75f, (gc.getWindow().getHeightScale() / 2f) - 0.25f, 0.025f);
		Graphics.drawText(gl, "Text Rendering", -3.0f, 0f, 0.095f);
		//Graphics.drawText(gl, "1234567890 1234567890 1234567890 1234567890 1234567890 1234567890\\n1234567890 1234567890 1234567890 1234567890 1234567890 1234567890\\n1234567890 1234567890 1234567890 1234567890 1234567890 1234567890\\n1234567890 1234567890 1234567890 1234567890 1234567890 1234567890", -4.8f, -0.5f, 0.025f);
		Graphics.setColor(255, 255, 255);
		
		//button.render(gc, gl);
		
		if(Options.debug) {
			Graphics.line(gl, -gc.getWindow().getWidthScale() / 2, 0, gc.getWindow().getWidthScale() / 2, 0);
			Graphics.drawText(gl, -gc.getWindow().getWidthScale() / 2 + "", -gc.getWindow().getWidthScale() / 2 + 0.015f, -0.15f, 0.015f);
			Graphics.drawText(gl, gc.getWindow().getWidthScale() / 2 + "", gc.getWindow().getWidthScale() / 2 - 0.1f, -0.15f, 0.015f);
			
			Graphics.line(gl, 0, -gc.getWindow().getHeightScale() / 2, 0, gc.getWindow().getHeightScale() / 2);
			Graphics.drawText(gl, -gc.getWindow().getHeightScale() / 2 + "", 0.1f, -gc.getWindow().getHeightScale() / 2 + 0.15f, 0.015f);
			Graphics.drawText(gl, gc.getWindow().getHeightScale() / 2 + "", 0.1f, gc.getWindow().getHeightScale() / 2 - 0.15f, 0.015f);
		}
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
