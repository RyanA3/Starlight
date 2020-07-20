package me.felnstaren.starlight.engine.graphics.font;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import me.felnstaren.starlight.engine.graphics.Image;
import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;
import me.felnstaren.starlight.game.Options;

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
		Logger.log(Level.INFO, "Loading new texture for character: " + c);
		BufferedImage character = font_image.getBuffered().getSubimage(offsets[c], 0, widths[c], font_image.getHeight());
		Texture texture = AWTTextureIO.newTexture(profile, character, true);
		loaded[c] = texture;
		return texture;
	}
	
	//Oh God I don't want to refactor this
	public BufferedImage genStitchedTexture(String text) {
		//Calculate the width and height of the end resulting stitched buffered image
		int stitched_width = 0;
		int stitched_height = 0;
		
		int max_width = 0;
		int line_width = 0;
		int height_scale = 1;
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i);
			
			//Handle return character
			if(text.length() > i + 1 && unicode == 92 && text.codePointAt(i + 1) == 110) {
				if(line_width > max_width) max_width = line_width;
				height_scale++; line_width = 0;
				i++; continue;
			}
			
			line_width += widths[unicode];
		}
		
		if(max_width == 0) stitched_width = line_width;
		else stitched_width = max_width;
		stitched_height = (int) (height_scale * font_image.getHeight() * 0.75f);
		
		
		//Create stitched image
		BufferedImage stitched = new BufferedImage(stitched_width, stitched_height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = stitched.createGraphics();

		
		//Stitching Loop
		int offset = 0;
		int lineset = 0;
		for(int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i);
			
			//Handle return character
			if(text.length() > i + 1 && unicode == 92 && text.codePointAt(i + 1) == 110) {
				offset = 0; lineset += font_image.getHeight() * 0.75f;
				i++; continue;
			}
			
			//Stitch
			BufferedImage character = font_image.getBuffered().getSubimage(offsets[unicode], 0, widths[unicode], font_image.getHeight());
			g2.drawImage(character, null, offset, lineset);
			offset += character.getWidth();
			character.flush();
		}
		
		if(Options.debug) {
			g2.setColor(Color.RED);
			g2.drawRect(0, 0, stitched_width - 1, stitched_height - 1);
		}
		
		//Cleanup
		g2.dispose();
		return stitched;
	}
	
	public Texture genStitchedTexture(GLProfile profile, String text) {
		return AWTTextureIO.newTexture(profile, genStitchedTexture(text), true);
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
