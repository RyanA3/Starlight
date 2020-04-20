package me.felnstaren.starlight.engine;

import com.jogamp.nativewindow.WindowClosingProtocol.WindowClosingMode;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;

import me.felnstaren.starlight.engine.geometry.Vertex;

public class Window {
	
	private GLWindow window;
	private GLProfile profile;
	private int width = 640;
	private int height = 360;
	private int width_scale = 10;
	private int height_scale = 5;
	
	public Window(GLProfile profile, int width, int height) {
		this.profile = profile;
		this.width = width;
		this.height = height;
	}
	
	public Window(GLProfile profile) {
		this.profile = profile;
		this.width = 640;
		this.height = 360;
	}
	
	public void init(GLEventListener listener) {
		window = GLWindow.create(new GLCapabilities(profile));
		window.setSize(width, height);
		window.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
		window.setResizable(true);
		window.requestFocus();
		window.addGLEventListener(listener);
		window.setVisible(true);
	}
	
	
	
	public void display() {
		window.display();
	}
	

	
	public int getWidth() {
		return window.getWidth();
	}
	
	public int getHeight() {
		return window.getHeight();
	}
	
	public int getWidthScale() {
		return width_scale;
	}
	
	public float getHeightScale() {
		//return window.getHeight() / (window.getWidth() / width_scale);
		return height_scale;
	}
	
	public GLWindow getGLWindow() {
		return window;
	}
	
	public void toWindowCoords(Vertex pxcoords) {
		pxcoords.x -= window.getWidth() / 2;
		pxcoords.y -= window.getHeight() / 2;
		pxcoords.x /= (window.getWidth());
		pxcoords.y /= (window.getHeight());
		pxcoords.x *= width_scale;
		pxcoords.y *= -getHeightScale();
	}

}
