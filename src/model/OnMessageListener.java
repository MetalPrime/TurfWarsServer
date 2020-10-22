package model;

public interface OnMessageListener {

	public void OnMessage(Session s,String msg);

	
	public void newPosition(Session s,int x, int y);
	
	public void  newBullet(Session s,Bullet b);
}
