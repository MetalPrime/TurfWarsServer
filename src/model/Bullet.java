package model;


public class Bullet {
	
	private String type = "Bullet";
	private int posX;
	private int posY;
	private int mov;
	
	

	public Bullet(int posX, int posY, int mov) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.mov = mov;
	}
	
	public void move() {
		this.posX+=this.mov;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
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

	public int getMov() {
		return mov;
	}

	public void setMov(int mov) {
		this.mov = mov;
	}

	

}
