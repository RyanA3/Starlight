package me.felnstaren.starlight.engine;

import com.jogamp.opengl.GL2;

public abstract class AbstractGame {

	public abstract void init(GameContainer gc, GL2 gl);
	public abstract void update(GameContainer gc, float delta_time);
	public abstract void render(GameContainer gc, GL2 gl);
	
}
