package model;

public class Coordinate {
	
	private String type = "Coordinate";
    private int posX;
    private int posY;

    public Coordinate(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Coordinate() {

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
