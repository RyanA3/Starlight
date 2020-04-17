package me.felnstaren.starlight.engine.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;

public class Image {
	
	private BufferedImage image;
	private Texture texture;
	private int width, height;
	
	public Image(String path) {
		try {
			image = ImageIO.read(Image.class.getResourceAsStream("resources/" + path));
			
			width = image.getWidth();
			height = image.getHeight();
			
			image.flush();
		} catch(Exception e) {
			e.printStackTrace();
			Logger.log(Level.WARNING, "Error loading image " + path);
		}
	}
	
	public Image(BufferedImage image) {
		this.image = image;
		width = image.getWidth();
		height = image.getHeight();
		image.flush();
	}
	
	
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int getPixel(int x, int y) {
		return image.getRGB(x, y);
	}
	
	public Texture getTexture(GLProfile profile) {
		if(texture == null) texture = AWTTextureIO.newTexture(profile, image, true);
		return texture;
	}
	
	public BufferedImage getBuffered() {
		return image;
	}
	
}
