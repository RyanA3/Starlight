package me.felnstaren.starlight.engine;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLProfile;

import me.felnstaren.starlight.engine.logging.Level;
import me.felnstaren.starlight.engine.logging.Logger;

public class GameContainer implements Runnable {

	private Thread thread;
	private AbstractGame game;
	private Window window;
	private Input input;


	private boolean running = false;
	private final double update_cap = 1.0/60.0;
	
	public GameContainer(AbstractGame game) {
		this.game = game;
	}
	
	public void start() {
		GLProfile.initSingleton();
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		window = new Window(profile);
		window.init(new WindowEventListener(this) {
			public void init(GLAutoDrawable drawable) {
				super.init(drawable);
				game.init(GameContainer.this, drawable.getGL().getGL2());
			}
			
			public void display(GLAutoDrawable drawable) {
				super.display(drawable);
				game.render(GameContainer.this, drawable.getGL().getGL2());
			}
		});
		
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
	}
	
	public void stop() {
		running = false;
	}
	
	public void run() {
		running = true;
		
		boolean render = false;
		double first_time = 0;
		double last_time = System.nanoTime() / 1000000000.0;
		double passed_time = 0;
		double unprocessed_time = 0;
		
		double frame_time = 0;
		int frames = 0;
		int fps = 0;
		
		//game.init(this); //Game is init from gl window

		while(running) {
			render = false; //set to true to disable frame cap
			
			first_time = System.nanoTime() / 1000000000.0;
			passed_time = first_time - last_time;
			last_time = first_time;
			
			unprocessed_time += passed_time;
			frame_time += passed_time;
			
			while(unprocessed_time >= update_cap) {
				Logger.log(Level.STREAM, "Updating");
				
				unprocessed_time -= update_cap;
				render = true;
				
				//Update game
				game.update(this, (float) (update_cap));
				input.update();
				
				if(frame_time >= 1.0) {
					frame_time = 0;
					fps = frames;
					frames = 0;
				}
			}
			
			if(render) {
				Logger.log(Level.STREAM, "Rendering at FPS: " + fps);

				window.display(); //Game is rendered from gl window
				
				frames++;
			} else {
				try { Thread.sleep(1); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		}
	}
	
	
	
	public Window getWindow() {
		return window;
	}
	
	public AbstractGame getGame() {
		return game;
	}
	
	public Input getInput() {
		return input;
	}

}
