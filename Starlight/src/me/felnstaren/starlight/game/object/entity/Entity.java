package me.felnstaren.starlight.game.object.entity;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.engine.geometry.Polygon;
import me.felnstaren.starlight.engine.geometry.Translation;
import me.felnstaren.starlight.engine.geometry.Vertex;
import me.felnstaren.starlight.engine.graphics.Color;
import me.felnstaren.starlight.engine.graphics.Graphics;
import me.felnstaren.starlight.engine.graphics.ImageResource;
import me.felnstaren.starlight.game.Options;
import me.felnstaren.starlight.game.object.GameObject;
import me.felnstaren.starlight.game.object.entity.type.EntityType;
import me.felnstaren.starlight.game.world.World;

public abstract class Entity implements GameObject {

	protected float vx = 0f;
	protected float vy = 0f;
	protected float x, y;
	protected float width, height;
	protected World world;
	protected Polygon bb;
	protected EntityType type;
	protected ImageResource texture;
	protected static final Color DEBUG_COLOR = new Color(0, 255, 0);
	
	public Entity(EntityType type, float x, float y, float width, float height, World world) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.world = world;
		this.bb = new Polygon(
				new Vertex(-width / 2, height / 2),
				new Vertex(-width / 2, -height / 2),
				new Vertex(width / 2, -height / 2),
				new Vertex(width / 2, height / 2)
				);
	}
	
	
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public float getVX() {
		return this.vx;
	}
	
	public float getVY() {
		return this.vy;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public EntityType getType() {
		return this.type;
	}
	
	public Polygon getBoundingBox() {
		return this.bb;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void translate(float x, float y) {
		this.x += x;
		this.y += y;
	}
	
	public void setVX(float vx) {
		this.vx = vx;
	}
	
	public void setVY(float vy) {
		this.vy = vy;
	}
	
	public void setVelocity(float vx, float vy) {
		this.vx = vx;
		this.vy = vy;
	}
	
	public boolean isCollision(Entity entity) {
		Polygon otherbb = entity.getBoundingBox();
		
		otherbb.translate(new Translation(entity.getX(), entity.getY(), 0));
		bb.translate(new Translation(x, y, 0));
		
		boolean collided = bb.isCollision(otherbb);
		
		bb.translate(new Translation(-x, -y, 0));
		otherbb.translate(new Translation(-entity.getX(), -entity.getY(), 0));
		
		return collided;
	}
	
	
	
	public void render(GameContainer gc, GL2 gl) {
		if(Options.debug) {
			Graphics.setColor(DEBUG_COLOR.r, DEBUG_COLOR.g, DEBUG_COLOR.b);
			Graphics.polygon(gl, bb, x, y);
			Graphics.fillEquilPolygon(gl, x, y, 0.01f, 32);
			Graphics.setColor(255, 255, 255);
		}
	}
	
	public void update(GameContainer gc, float delta_time) {
		x += vx * delta_time;
		y += vy * delta_time;
		
		if(Options.debug) {
			if(world.isColliding(this)) {
				DEBUG_COLOR.r = 255; DEBUG_COLOR.g = 0;	
			} else {
				DEBUG_COLOR.r = 0; DEBUG_COLOR.g = 255;	
			}
		}
	}
	
}
