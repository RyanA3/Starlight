package me.felnstaren.starlight.engine.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import me.felnstaren.starlight.engine.geometry.Polygon;
import me.felnstaren.starlight.engine.geometry.Vertex;
import me.felnstaren.starlight.engine.graphics.font.Font;
import me.felnstaren.starlight.game.Options;

public class Graphics {

	private static float r = 1f;
	private static float g = 1f;
	private static float b = 1f;
	private static float a = 1f;
	
	private static float rotation = 0;
	
	private static Font font = Font.STANDARD;
	
	
	
	public static void setColor(int red, int green, int blue) {
		r = red/255f;
		g = green/255f;
		b = blue/255f;
	}
	
	public static void setColor(int red, int green, int blue, int alpha) {
		r = red/255f;
		g = green/255f;
		b = blue/255f;
		a = alpha/255f;
	}
	
	
	public static void setRotation(float r) {
		rotation = r;
	}
	
	

	
	public static void line(GL2 gl, float x1, float y1, float x2, float y2) {
		begin(gl, GL2.GL_LINE_STRIP, 0, 0);
		
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		
		end(gl, 0, 0);
	}
	
	public static void rect(GL2 gl, float x, float y, float width, float height) {
		begin(gl, GL2.GL_LINE_STRIP, x, y);
		
		gl.glVertex2f(-width/2, height/2);
		gl.glVertex2f(width/2, height/2);
		gl.glVertex2f(width/2, -height/2);
		gl.glVertex2f(-width/2, -height/2);
		gl.glVertex2f(-width/2, height/2);
		
		end(gl, x, y);
	}
	
	public static void fillRect(GL2 gl, float x, float y, float width, float height) {		
		begin(gl, GL2.GL_QUADS, x, y);
		
		gl.glVertex2f(-width/2, height/2);
		gl.glVertex2f(width/2, height/2);
		gl.glVertex2f(width/2, -height/2);
		gl.glVertex2f(-width/2, -height/2);
		
		end(gl, x, y);
	}
	
	public static void fillTriangle(GL2 gl, float x, float y, float x1, float y1, float x2, float y2, float x3, float y3) {
		begin(gl, GL2.GL_TRIANGLES, x, y);
		
		gl.glVertex2f(x1, y1);
		gl.glVertex2f(x2, y2);
		gl.glVertex2f(x3, y3);

		end(gl, x, y);
	}
	
	public static void polygon(GL2 gl, Polygon polygon, float x, float y) {
		begin(gl, GL2.GL_LINE_STRIP, x, y);
		
		for(int i = 0; i < polygon.verticies(); i++) {
			Vertex vertex = polygon.getVertex(i);
			gl.glVertex2f(vertex.x, vertex.y);
		}
		
		gl.glVertex2f(polygon.getVertex(0).x, polygon.getVertex(0).y);
		
		end(gl, x, y);
	}
	
	public static void fillPolygon(GL2 gl, Polygon polygon, float x, float y) {
		begin(gl, GL2.GL_POLYGON, x, y);
		
		for(int i = 0; i < polygon.verticies(); i++) {
			Vertex vertex = polygon.getVertex(i);
			gl.glVertex2f(vertex.x, vertex.y);
		}
		
		end(gl, x, y);
	}
	
	public static void fillEquilPolygon(GL2 gl, float x, float y, float radius, int verticies) {
		begin(gl, GL2.GL_POLYGON, x, y);
		
		for(float rad = 0; rad < 2 * Math.PI; rad += (Math.PI * 2) / verticies) {
			float offX = (float) (Math.cos(rad) * radius);
			float offY = (float) (Math.sin(rad) * radius);
			gl.glVertex2f(offX, offY);
		}
		
		end(gl, x, y);
	}
	
	public static void fillCircle(GL2 gl, float x, float y, float radius) {
		fillEquilPolygon(gl, x, y, radius, 64);
	}
	
	public static void drawTexture(GL2 gl, Texture texture, float x, float y, float width, float height) {
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(-rotation, 0, 0, 1);
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, texture.getTextureObject());
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
		
		gl.glColor4f(r, g, b, a);
		gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(0, 0);
			gl.glVertex2f(-width/2, height/2);
			gl.glTexCoord2f(1, 0);
			gl.glVertex2f(width/2, height/2);
			gl.glTexCoord2f(1, 1);
			gl.glVertex2f(width/2, -height/2);
			gl.glTexCoord2f(0, 1);
			gl.glVertex2f(-width/2, -height/2);
		gl.glEnd();
		gl.glFlush();
		
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		gl.glDisable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glRotatef(rotation, 0, 0, 1);
		gl.glTranslatef(-x, -y, 0);
	}
	
	public static void drawTexture(GL2 gl, Image resource, float x, float y, float width, float height) {
		Texture texture = resource.getTexture(gl.getGLProfile());
		drawTexture(gl, texture, x, y, width, height);
	}
	
	
	
	public static void drawText(GL2 gl, String text, float offX, float offY, float size) {
		float offset = 0;
		float lineset = 0;
		
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i);
			
			if(text.length() > i + 1 && unicode == 92 && text.codePointAt(i + 1) == 110) {
				i += 1;
				lineset -= (font.getFontImage().getHeight() * size);
				offset = 0;
				continue;
			}
			
			Texture character = font.getCharacterTexture(gl.getGLProfile(), (char) unicode);
			offset += (character.getWidth() * size) / 2;
			
			drawTexture(gl, character, offX + offset, offY + lineset, character.getWidth() * size, character.getHeight() * size);
			if(Options.debug) {
				float pr = r;
				float pg = g;
				float pb = b;
				setColor(255, 255, 255);
				rect(gl, offset + offX, lineset + offY, character.getWidth() * size, character.getHeight() * size);
				r = pr;
				g = pg;
				b = pb;
			}
			offset += (character.getWidth() * size) / 2;
		}
	}
	
	
	
	private static void begin(GL2 gl, int type, float x, float y) {
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(-rotation, 0, 0, 1);
		gl.glColor4f(r, g, b, a);
		gl.glBegin(type);
	}
	
	private static void end(GL2 gl, float x, float y) {
		gl.glEnd();
		gl.glFlush();
		gl.glRotatef(rotation, 0, 0, 1);
		gl.glTranslatef(-x, -y, 0);
	}
	
}
