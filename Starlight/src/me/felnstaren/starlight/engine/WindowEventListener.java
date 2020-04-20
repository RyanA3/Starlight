package me.felnstaren.starlight.engine;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;

public abstract class WindowEventListener implements GLEventListener {

	private GameContainer gc;
	
	public WindowEventListener(GameContainer gc) {
		this.gc = gc;
	}
	
	
	
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
	}
	
	public void dispose(GLAutoDrawable drawable) {
		Logger.log(Level.INFO, " -= Game Closing =- ");
		gc.stop();
		System.exit(0);
	}
	
	public void init(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClearColor(0, 0, 0, 1);
		gl.setSwapInterval(0);
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		float width_scale = gc.getWindow().getWidthScale();
		float height_scale = gc.getWindow().getHeightScale(); /*height / (width / width_scale);*/
		gl.glOrtho(-width_scale /2, width_scale / 2, -height_scale / 2, height_scale / 2, -1, 1);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}
	
}
