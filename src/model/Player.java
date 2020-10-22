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
	private String weapon;
	
	public Player(PApplet app, PImage img,String name,  int life, String weapon) {
		super();
		this.app = app;
		this.img = img;
		this.name = name;
		this.bullets = new ArrayList<Bullet>();
		this.life = life;
		this.weapon = weapon;
	}
	
	public  void paint(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		if(name!=null) {
			app.text(name, posX,posY-10);
		}
	
		app.image(img, posX, posY);
		for(int i = 0; i<this.bullets.size(); i++) {
			app.ellipse(this.bullets.get(i).getPosX()+50, this.bullets.get(i).getPosY()+80, 50, 50);
			switch(weapon) {
			case "Pistol":
				app.image(app.loadImage("./../media/img/ArmaPistola.png"), posX+50, posY+80);
				this.bullets.get(i).setMov(10);
				this.bullets.get(i).setDamage(5);
				break;
			case "Rifle":
				app.image(app.loadImage("./../media/img/ArmaRifle.png"), posX+50, posY+80);
				this.bullets.get(i).setMov(20);
				this.bullets.get(i).setDamage(15);
				break;
			case "Shotgun":
				app.image(app.loadImage("./../media/img/ArmaEscopeta.png"), posX+50, posY+80);
				this.bullets.get(i).setMov(15);
				this.bullets.get(i).setDamage(10);
				break;
			}
			
			this.bullets.get(i).move();
		}
	}
	
	
	
	public PApplet getApp() {
		return app;
	}

	public void setApp(PApplet app) {
		this.app = app;
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

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public boolean compareTo(Player o) {
		// TODO Auto-generated method stub
		if(this.getLife()<o.getLife()) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	
}
