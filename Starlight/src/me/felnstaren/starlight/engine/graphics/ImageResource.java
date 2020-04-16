package me.felnstaren.starlight.engine.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;

public class ImageResource {
	
	private BufferedImage image;
	private Texture texture;
	
	public ImageResource(String path) {
		try {
			image = ImageIO.read(ImageResource.class.getResourceAsStream("resources/" + path));
			image.flush();
		} catch(Exception e) {
			e.printStackTrace();
			Logger.log(Level.WARNING, "Error loading image " + path);
		}
	}
	
	public Texture getTexture(GLProfile profile) {
		if(texture == null) texture = AWTTextureIO.newTexture(profile, image, true);
		return texture;
	}
	
}
