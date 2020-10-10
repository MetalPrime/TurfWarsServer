package view;

import processing.core.PApplet;

public class Main extends PApplet implements OnMessageListener{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(Main.class.getName());
	}
	
	private TCPServer tcp;
	private String msg;
	
	public void settings() {
		size(500,500);
	}
	
	public void setup() {
		tcp = TCPServer.getInstance();
		tcp.setObserver(this);
	}
	
	public void draw() {
		if(msg !=null) {
			fill(0);
			text(msg, 250, 250);
		}
	}
	
	public void mouseClicked() {
		
	}

	@Override
	public void OnMessage(String msg) {
		
		// TODO Auto-generated method stub
		this.msg = msg;
	}
	
	

}
