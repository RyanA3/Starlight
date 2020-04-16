package me.felnstaren.starlight.engine;


import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class Input implements KeyListener, MouseListener {

	private GameContainer gc;
	
	//Keeps track of all keys and if they are down
	private final int NUM_KEYS = 256;
	private boolean[] keys = new boolean[NUM_KEYS];
	private boolean[] keys_last = new boolean[NUM_KEYS];
	
	//Keeps track of mouse buttons and if they are down
	private final int NUM_BUTTONS = 5;
	private boolean[] buttons = new boolean[NUM_KEYS];
	private boolean[] buttons_last = new boolean[NUM_KEYS];
	
	private int mouseX, mouseY;
	private int scroll;
	
	public Input(GameContainer gc) {
		this.gc = gc;
		mouseX = 0;
		mouseY = 0;
		scroll = 0;
		
		gc.getWindow().getGLWindow().addKeyListener(this);
		gc.getWindow().getGLWindow().addMouseListener(this);
	}
	
	
	public void update() {
		scroll = 0;
		
		for(int i = 0; i < NUM_KEYS; i++) keys_last[i] = keys[i];
		for(int i = 0; i < NUM_BUTTONS; i++) buttons_last[i] = buttons[i];
	}
	
	
	
	
	
	
	public boolean isKeyPressed(int key_code) {
		return keys[key_code];
	}
	
	public boolean isKeyUp(int key_code) {
		return keys_last[key_code] && !keys[key_code];
	}
	
	public boolean isKeyDown(int key_code) {
		return !keys_last[key_code] && keys[key_code];
	}
	
	public boolean isButtonPressed(int button) {
		return buttons[button];
	}
	
	public boolean isButtonUp(int button) {
		return buttons_last[button] && !buttons[button];
	}
	
	public boolean isButtonDown(int button) {
		return !buttons_last[button] && buttons[button];
	}
	
	
	
	

	public int getMouseX() {
		return this.mouseX;
	}
	
	public int getMouseY() {
		return this.mouseY;
	}
	
	public int getScroll() {
		return this.scroll;
	}

	
	
	
	
	
	
	

	public void keyPressed(com.jogamp.newt.event.KeyEvent event) {
		if(event.isAutoRepeat()) return;
		keys[event.getKeyCode()] = true;
	}

	public void keyReleased(com.jogamp.newt.event.KeyEvent event) {
		if(event.isAutoRepeat()) return;
		keys[event.getKeyCode()] = false;
	}



	
	
	public void mouseClicked(MouseEvent event) {

	}

	public void mouseDragged(MouseEvent event) {
		mouseX = (int) event.getX();
		mouseY = (int) event.getY();
	}

	public void mouseEntered(MouseEvent event) {

	}

	public void mouseExited(MouseEvent event) {

	}

	public void mouseMoved(MouseEvent event) {
		mouseX = (int) event.getX();
		mouseY = (int) event.getY();
	}

	public void mousePressed(MouseEvent event) {
		buttons[event.getButton()] = true;
	}

	public void mouseReleased(MouseEvent event) {
		buttons[event.getButton()] = false;
	}

	public void mouseWheelMoved(MouseEvent event) {
		scroll = (int) event.getRotation()[0];
	}
	
}
