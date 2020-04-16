package me.felnstaren.starlight.game.object;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;

public interface GameObject {

	public void render(GameContainer gc, GL2 gl);
	public void update(GameContainer gc, float delta_time);
	
}
