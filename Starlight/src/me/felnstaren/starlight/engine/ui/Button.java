package me.felnstaren.starlight.engine.ui;

import java.awt.event.MouseEvent;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.geometry.Vertex;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;
import me.felnstaren.starlight.game.Options;

public abstract class Button implements GameObject {

	protected float width, height;
	protected float x, y;
	protected boolean visible = true;
	protected boolean active = true;
	private boolean pressed = false;
	
	protected int fillr = 220;
	protected int fillg = 220;
	protected int fillb = 220;
	protected int edger = 180;
	protected int edgeg = 180;
	protected int edgeb = 180;
	protected float edge_buffer;
	
	public Button(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.edge_buffer = 0.02f;
	}
	
	public void render(GameContainer gc, GL2 gl) {
		Graphics.setColor(fillr, fillg, fillb);
		Graphics.fillRect(gl, x, y, width, height);
		
		Graphics.setColor(edger, edgeg, edgeb);
		Graphics.fillRect(gl, x, (y + height/2 - edge_buffer/2), width, edge_buffer);
		Graphics.fillRect(gl, (x + width/2 - edge_buffer/2), y, edge_buffer, height);
		Graphics.fillRect(gl, (x - width/2 + edge_buffer/2), y, edge_buffer, height);
		Graphics.fillRect(gl, x, (y - height/2 + edge_buffer/2), width, edge_buffer);
		
		if(Options.debug) {
			Graphics.setColor(255, 0, 0);
			Graphics.rect(gl, x, y, width, height);
			Graphics.fillEquilPolygon(gl, x, y, 0.05f, 3);
			Graphics.setColor(255, 255, 255);
		}
	}

	public void update(GameContainer gc, float delta_time) {
		Vertex mcor = new Vertex(gc.getInput().getMouseX(), gc.getInput().getMouseY());
		gc.getWindow().toWindowCoords(mcor);
		
		if(pressed) {
			if(gc.getInput().isButtonUp(MouseEvent.BUTTON1)) pressed = false;
			else {
				
			}
			return;
		}
		
		if(!gc.getInput().isButtonDown(MouseEvent.BUTTON1)) return;
		Logger.log(Level.INFO, "Click " + mcor.x + "," + mcor.y);
		
		if(mcor.x > x - (width / 2) && 
				mcor.y > y - (height / 2) &&
				mcor.x < x + (width / 2) &&
				mcor.y < y + (height / 2)) {
			Logger.log(Level.INFO, "Is Inside");
			onPress(gc, delta_time);
			pressed = true;
		}
	}
	
	public abstract void onPress(GameContainer gc, float delta_time);

}
