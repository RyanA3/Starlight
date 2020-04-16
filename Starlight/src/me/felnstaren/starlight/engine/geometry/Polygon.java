package me.felnstaren.starlight.engine.geometry;

import java.util.ArrayList;

public class Polygon {

	protected Vertex[] verticies;
	protected float rotation = 0;
	protected Vertex rotation_vertex = new Vertex(0, 0);
	
	public Polygon() {
		this.verticies = new Vertex[0];
	}
	
	public Polygon(Vertex... verticies) {
		this.verticies = verticies;
	}
	
	
	
	public int verticies() {
		return verticies.length;
	}
	
	public Vertex[] getVerticies() {
		return verticies;
	}
	
	public Vertex getVertex(int pos) {
		return verticies[pos];
	}
	
	public void setVerticies(Vertex... verticies) {
		this.verticies = verticies;
	}
	
	public void setVerticies(ArrayList<Vertex> verticies) {
		this.verticies = new Vertex[verticies.size()];
		for(int i = 0; i < verticies.size(); i++) this.verticies[i] = verticies.get(i);
	} 
	
	public void setVertex(Vertex vertex, int pos) {
		verticies[pos] = vertex;
	}
	
	
	
	public void translate(Translation translation) {
		for(Vertex v : verticies) {
			v.x += translation.x;
			v.y += translation.y;
		}
	}
	
	public Polygon getRotated() {
		Polygon rotated = clone();
		if(rotation == 0) return rotated;
		rotated.rotate(rotation, rotation_vertex);
		rotated.setRotationVertex(rotation_vertex);
		return rotated;
	}
	
	public Polygon getFlipped() {
		Polygon rotated = clone();
		rotated.rotate((float) Math.PI, rotation_vertex);
		rotated.setRotationVertex(rotation_vertex);
		return rotated;
	}
	
	public void rotate(float rotation, Vertex center) {
		for(Vertex v : verticies) {
			/** http://en.wikipedia.org/wiki/Rotation_matrix **/
	        double cosThetha = Math.cos(rotation); 
	        double sinThetha = Math.sin(rotation); 
	        double dx = (v.x - center.x); 
	        double dy = (v.y - center.y); 
	 
	        v.x = center.x + (float) (dx * cosThetha - dy * sinThetha);
	        v.y = center.y + (float) (dx * sinThetha + dy * cosThetha);
		}
	}
	
	public void setRotation(float rotation) {
		if(rotation > 6.25)
			rotation = ((rotation % 6.25f));
		this.rotation = rotation;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotationVertex(Vertex vertex) {
		this.rotation_vertex = vertex;
	}
	
	public void scale(float scale) {
		for(Vertex v : verticies) {
			v.x *= scale;
			v.y *= scale;
		}
	}
	
	public Polygon clone() {
		Vertex[] newvs = new Vertex[verticies.length];
		for(int i = 0; i < verticies.length; i++) 
			newvs[i] = new Vertex(verticies[i].x, verticies[i].y);
		return new Polygon(newvs);
	}
	
	
	
	public float getMaxX() {
		float value = verticies[0].x;
		for(int i = 1; i < verticies.length; i++)
			if(verticies[i].x > value) value = verticies[i].x;
		return value;
	}
	
	public float getMinX() {
		float value = verticies[0].x;
		for(int i = 1; i < verticies.length; i++)
			if(verticies[i].x < value) value = verticies[i].x;
		return value;
	}
	
	public float getMaxY() {
		float value = verticies[0].y;
		for(int i = 1; i < verticies.length; i++)
			if(verticies[i].y > value) value = verticies[i].y;
		return value;
	}
	
	public float getMinY() {
		float value = verticies[0].y;
		for(int i = 1; i < verticies.length; i++)
			if(verticies[i].y < value) value = verticies[i].y;
		return value;
	}
	
	
	
	public boolean isCollision(Polygon other) {
		if
			((getMinX() < other.getMinX() && getMaxX() > other.getMaxX() && getMinY() > other.getMinY() && getMaxY() < other.getMaxY())
			||
			(getMinY() < other.getMinY() && getMaxY() > other.getMaxY() && getMinX() > other.getMinX() && getMaxX() < other.getMaxX())) {
			return true;
		}
		
		
		for(int i = 0; i < other.getVerticies().length; i++) 
			if(isInside(other.getVertex(i))) return true;
		for(int i = 0; i < verticies.length; i++) 
			if(other.isInside(verticies[i])) return true;
	
		return false;
	}
	
	public boolean isInside(Vertex test) {
		boolean result = false;
		int i, j = 0;
		for (i = 0, j = verticies.length - 1; i < verticies.length; j = i++) {
			if ((verticies[i].y > test.y) != (verticies[j].y > test.y) && 
				(test.x < (verticies[j].x - verticies[i].x) * (test.y - verticies[i].y) / (verticies[j].y - verticies[i].y) + verticies[i].x)) { 
				result = !result;
			}
		}
		return result;
	}
	
}
