package me.felnstaren.starlight.engine.graphics.font;

import java.awt.image.BufferedImage;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import me.felnstaren.starlight.engine.graphics.Image;

public class Font {

	public static final Font STANDARD = new Font("fonts/standard.png");
	public static final Font COMIC_SANS = new Font("fonts/comic_sans.png");
	
	private Image font_image;
	private int[] offsets;
	private int[] widths;
	
	private Texture[] loaded;
	
	public Font(String path) {
		font_image = new Image(path);
		
		offsets = new int[256];
		widths = new int[256];
		loaded = new Texture[256];
		
		int unicode = 0;
		
		for(int i = 0; i < font_image.getWidth(); i++) {
			if(font_image.getPixel(i, 0) == 0xff0000ff) { //Beginning of a character: 0 red 0 green 255 blue
				offsets[unicode] = i + 1;
			}
			
			if(font_image.getPixel(i, 0) == 0xffff0000) { //Ending of a character 255 red 0 green 0 blue
				widths[unicode] = i - offsets[unicode];
				unicode++;
			}
		}
	}
	
	
	
	public Image getFontImage() {
		return this.font_image;
	}
	
	public Texture getCharacterTexture(GLProfile profile, char c) {
		if(loaded[c] != null) return loaded[c];
		int x = offsets[c];
		int y = 0;
		int width = widths[c];
		int height = font_image.getHeight();
		BufferedImage character = font_image.getBuffered().getSubimage(x, y, width, height);
		Texture texture = AWTTextureIO.newTexture(profile, character, true);
		loaded[c] = texture;
		return texture;
	}
	
	public int getWidth(int loc) {
		return this.widths[loc];
	}
	
	public int[] getWidths() {
		return this.widths;
	}
	
	public int getOffset(int loc) {
		return this.offsets[loc];
	}
	
	public int[] getOffsets() {
		return this.offsets;
	}
	
}
