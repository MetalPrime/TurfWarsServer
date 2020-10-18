package view;

import model.OnMessageListener;
import model.Player;
import model.Session;
import model.TCPServer;
import processing.core.PApplet;

public class Main extends PApplet implements OnMessageListener{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(Main.class.getName());
	}
	
	private TCPServer tcp;
	private String msg;
	private Session session;
	
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
		
		for(int i=0; i< tcp.getSessions().size(); i++) {
			session = tcp.getSessions().get(i);
			if(i%2==0) {
				Player player = new Player(this, loadImage("./../media/img/PersonajeJ1.png"), msg, 130,250 ,50 );
				player.paint();
			} else {
				Player player = new Player(this, loadImage("./../media/img/PersonajeJ2.png"), msg, 180,250 ,50 );
				player.paint();
			}
		}
	}
	
	public void mouseClicked() {
		
	}

	@Override
	public void OnMessage(Session s,String msg) {
		
		// TODO Auto-generated method stub
		this.msg = msg+ " "+ s.getID();
	}
	
	

}
