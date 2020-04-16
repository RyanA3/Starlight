package me.felnstaren.starlight.game.world;

import java.util.ArrayList;

import com.jogamp.opengl.GL2;

import me.felnstaren.starlight.engine.GameContainer;
import me.felnstaren.starlight.game.object.entity.Entity;
import me.felnstaren.starlight.game.object.entity.LivingEntity;
import me.felnstaren.starlight.game.object.entity.type.EntityType;

public class World {

	private String name;
	private ArrayList<LivingEntity> entities;
	private ArrayList<LivingEntity> to_spawn;
	private ArrayList<LivingEntity> to_despawn;
	private boolean looping = false;
	
	public World(String name) {
		this.entities = new ArrayList<LivingEntity>();
		this.to_spawn = new ArrayList<LivingEntity>();
		this.to_despawn = new ArrayList<LivingEntity>();
		this.name = name;
	}
	
	
	
	public void renderEntities(GameContainer gc, GL2 gl) {
		looping = true;
		for(Entity e : entities) e.render(gc, gl);
		looping = false;
	}
	
	public void updateEntities(GameContainer gc, float delta_time) {
		looping = true;
		for(LivingEntity e : entities) {
			e.update(gc, delta_time);
			if(!e.isAlive()) to_despawn.add(e);
		}
		looping = false;
		
		entities.removeAll(to_despawn);
		if(!to_spawn.isEmpty()) {
			entities.addAll(to_spawn);
			to_spawn.clear();
		}
	}
	
	public void spawnEntity(LivingEntity entity) {
		//if(entities.size() > 270) {
		//	Logger.log(Level.FATAL, "WORLD HAS TOO MANY ENTITIES!");
		//	return;
		//}
		if(looping) to_spawn.add(entity);
		else entities.add(entity);
	}
	
	public void purgeEntities() {
		if(looping) to_despawn.addAll(entities);
		else entities.clear();
	}
	
	
	
	public ArrayList<LivingEntity> getEntities() {
		return entities;
	}
	
	public ArrayList<LivingEntity> getColliding(Entity check) {
		ArrayList<LivingEntity> colliding = new ArrayList<LivingEntity>();
		for(LivingEntity e : entities) 
			if(!e.equals(check) && e.isCollision(check)) colliding.add(e);
		return colliding;
	}
	
	public LivingEntity getColliding(Entity check, EntityType cancollide) {
		for(LivingEntity e : entities) 
			if(e.getType() == cancollide && !e.equals(check) && e.isCollision(check)) return e;
		return null;
	}
	
	public boolean isColliding(Entity check) {
		for(Entity e : entities) 
			if(!e.equals(check) && e.isCollision(check)) return true;
		return false;
	}
	
	public boolean isColliding(Entity check, EntityType cancollide) {
		for(Entity e : entities) 
			if(e.getType() == cancollide && !e.equals(check) && e.isCollision(check)) return true;
		return false;
	}
	
}
