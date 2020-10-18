package model;

import processing.core.PApplet;
import processing.core.PImage;

public class Player {
	
	private PApplet app;
	private PImage img;
	private String name;
	private int posX;
	private int posY;
	private int life;
	
	public Player(PApplet app, PImage img,String name, int posX, int posY, int life) {
		super();
		this.app = app;
		this.img = img;
		this.name = name;
		this.posX = posX;
		this.posY = posY;
		this.life = life;
	}
	
	public  void paint() {
		app.image(img, posX, posY);
	}
	
	public void move() {
		
	}
	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}
	
	
	
}
