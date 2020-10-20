package model;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Player {
	
	private PApplet app;
	private PImage img;
	private String name;
	private int posX;
	private int posY;
	private int life;
	private ArrayList<Bullet> bullets;
	
	public Player(PApplet app, PImage img,String name,  int life) {
		super();
		this.app = app;
		this.img = img;
		this.name = name;
		this.bullets = new ArrayList<Bullet>();
		this.life = life;
	}
	
	public  void paint(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		app.image(img, posX, posY);
		
		for(int i = 0; i<this.bullets.size(); i++) {
			app.ellipse(this.bullets.get(i).getPosX(), this.bullets.get(i).getPosY(), 50, 50);
			this.bullets.get(i).move();
		}
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

	public PImage getImg() {
		return img;
	}

	public void setImg(PImage img) {
		this.img = img;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
	
	
	
}
