package model;

import processing.core.PApplet;
import processing.core.PImage;

public class PowerUp {

	private PApplet app;
	private PImage img;
	private int type;
	private int posX,posY;
	private String name;
	
	public PowerUp(PApplet app,int type, int posX, int posY) {
		super();
		this.app = app;
		this.type = type;
		this.posX = posX;
		this.posY = posY;
		
		switch(type) {
		case 0:
			this.name = "Rifle";
			break;
		case 1:
			this.name = "Shotgun";
			
		}
	}
	
	public void paint() {
		if(this.name.equals("Rilfe")) {
			this.img = app.loadImage("./../media/img/balaPowerupRifle.png");
		}
		if(this.name.equals("Shotgun")) {
			this.img = app.loadImage("./../media/img/balaPowerupEscopeta.png");
		}
		
		
		app.image(img, this.posX, this.posY);
	}

	public PImage getImg() {
		return img;
	}

	public void setImg(PImage img) {
		this.img = img;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
	
	
}
