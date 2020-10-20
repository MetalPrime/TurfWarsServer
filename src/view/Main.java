package view;

import com.google.gson.Gson;

import model.Bullet;
import model.Coordinate;
import model.OnMessageListener;
import model.Player;
import model.PowerUp;
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
	private Gson gson;
	private Player playerJ1;
	private Player playerJ2;
	private PowerUp prueba;
	
	private int x,y;
	
	public void settings() {
		size(1400,800);
	}
	
	public void setup() {
		tcp = TCPServer.getInstance();
		tcp.setObserver(this);
		gson = new Gson();
		
		
		if(tcp.getSessions().size()<2) {
			
			playerJ1 = new Player(this, loadImage("./../media/img/PersonajeJ1.png"), msg, 50, "Pistol" );
		}
		
		prueba = new PowerUp(this, (int)random(0,2),(int) random(0,1200), (int)random(0,700));;
		
		
			
		
	}
	
	public void draw() {
		background(180);
		if(msg !=null) {
			fill(0);
			text(msg, x, y);
			for(int i=0; i< tcp.getSessions().size(); i++) {
				session = tcp.getSessions().get(i);
				playerJ1.paint(x,y);
			}
		}
		
		
		
		
	}
	
	public void impactBullet() {
		
	}
	
	public void mouseClicked() {
		
	}

	@Override
	public void OnMessage(Session s,String msg) {
		
		// TODO Auto-generated method stub
		this.msg = msg+ " "+ s.getID();
	}

	@Override
	public void newPosition(Session s, int x, int y) {
		// TODO Auto-generated method stub
		System.out.println(s.getID()+" "+x +"  " +y);
		this.x = x;
		this.y = y;
		//System.out.println(x +" " + y);
	}

	@Override
	public void newBullet(Bullet b) {
		// TODO Auto-generated method stub
		b.setPosX(x);
		b.setPosY(y);
		playerJ1.getBullets().add(b);
	}
	
	

}
