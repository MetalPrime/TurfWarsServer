package view;

import java.util.ArrayList;

import com.google.gson.Gson;

import model.Bullet;
import model.Coordinate;
import model.Life;
import model.OnMessageListener;
import model.Player;
import model.PowerUp;
import model.Session;
import model.TCPServer;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Main extends PApplet implements OnMessageListener{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(Main.class.getName());
	}
	
	private TCPServer tcp;
	
	private Gson gson;
	private boolean endGame;
	private ArrayList<PowerUp> powers;
	
	private PFont font;
	
	
	
	public void settings() {
		size(1300,700);
	}
	
	public void setup() {
		tcp = TCPServer.getInstance();
		tcp.setObserver(this);
		gson = new Gson();
		endGame = false;
		font = createFont("../media/font/megaman_2.ttf",25);
		powers = new ArrayList<PowerUp>();
	}
	
	public void draw() {
		PImage back =  loadImage("./../media/img/MenuVacio.png");
		background(back);
		fill(255);
		textSize(25);
		textFont(font);
		textLeading(50);
		if(tcp.getSessions().size()==0 || tcp.getSessions().size()==1) {
			image(loadImage("./../media/img/Titulo.png"),300,20);
			text("derrota a tu adversario del color opuesto dispara"+ "\n" +
			"y esquiva ataques de tu adversario haz uso de las balas "  +"\n"+ 
			"que aparecen para cambiar de tipo de arma, " +"\n" +
			"cada una tiene diferentes velocidadades y"+"\n"+ 
			" cadencias de tiro.",50,200);
			textSize(40);
			text("Esperando Jugadores...",300,500);
		}
		
		//veo quien esta conetado
		if(!endGame) {
			for(int i=0; i< tcp.getSessions().size(); i++) {
				//esto es para poder recorrer las sesiones de forma m�s suave
				Session session = tcp.getSessions().get(i);
				
				//creo una condici�n para iniciar a pintar los personajes, si existen dos
				//if(tcp.getSessions().size()==2) {
					//System.out.println( tcp.getSessions().size());
				fill(255);
				textSize(25);
				textFont(font);
				
				
				
					if(tcp.getSessions().size()==2) {
						
						if(i%2==0) {
							session.getPlayer().setApp(this);
							session.getPlayer().setImg(loadImage("./../media/img/PersonajeJ1.png"));
							session.getPlayer().setDir(1);
							Coordinate coord = new Coordinate(session.getPlayer().getPosX(),session.getPlayer().getPosY());
							String line = gson.toJson(coord);
							session.sendMessages(line);
						}else {
							session.getPlayer().setApp(this);
							session.getPlayer().setImg(loadImage("./../media/img/PersonajeJ2.png"));
							session.getPlayer().setDir(-1);
							Coordinate coord = new Coordinate(session.getPlayer().getPosX(),session.getPlayer().getPosY());
							String line = gson.toJson(coord);
							session.sendMessages(line);
						}
						
						session.getPlayer().paint(session.getPlayer().getPosX(), session.getPlayer().getPosY(),session.getPlayer().getDir());	
					} 
				//}
			}
			
			
			
			if(tcp.getSessions().size()==2) {
				if(powers.size()>0) {
					for (int i = 0; i < powers.size(); i++) {
						powers.get(i).paint();
					}
				}
				
				
				impactBullet(tcp.getSessions().get(0),tcp.getSessions().get(1));
				
				addPowerup();
				
				takePowerup();
				
				endGame();
			}
		} else {
			textSize(40);
			if(tcp.getSessions().get(0).getPlayer().compareTo(tcp.getSessions().get(1).getPlayer())) {
				text("Ganador: "+tcp.getSessions().get(1).getPlayer().getName(),width/2-100,height/2);
			} else {
				text("Ganador: "+tcp.getSessions().get(0).getPlayer().getName(),width/2-100,height/2);
			}
			
		}
		
		
	}
	
	public void impactBullet(Session a,Session b) {
		
		for(int i=0; i<a.getPlayer().getBullets().size(); i++) {
			if(a.getPlayer().getBullets().get(i).getPosX()>b.getPlayer().getPosX()
					&& a.getPlayer().getBullets().get(i).getPosX()<b.getPlayer().getPosX()+70 
					&& a.getPlayer().getBullets().get(i).getPosY()>b.getPlayer().getPosY()-50
					&& a.getPlayer().getBullets().get(i).getPosY()<b.getPlayer().getPosY()+120) {
				b.getPlayer().setLife(b.getPlayer().getLife()-a.getPlayer().getBullets().get(i).getDamage());
				a.getPlayer().getBullets().remove(i);
				System.out.println(b.getPlayer().getLife());
				
				if(b.getPlayer().getLife()==50) {
					Life life = new Life("Media");
					String line = gson.toJson(life);
					b.sendMessages(line);
				} if(b.getPlayer().getLife()==25){
					Life life = new Life("Baja");
					String line = gson.toJson(life);
					b.sendMessages(line);	
				}
			} else {
				if(a.getPlayer().getBullets().get(i).getPosX()>1220) {
					a.getPlayer().getBullets().remove(i);
				}
			}
		}

		for(int i=0; i<b.getPlayer().getBullets().size(); i++) {

				if(b.getPlayer().getBullets().get(i).getPosX()>a.getPlayer().getPosX()
						&& b.getPlayer().getBullets().get(i).getPosX()<a.getPlayer().getPosX()+70 
						&& b.getPlayer().getBullets().get(i).getPosY()>a.getPlayer().getPosY()-50
						&& b.getPlayer().getBullets().get(i).getPosY()<a.getPlayer().getPosY()+120) {
					
					
					a.getPlayer().setLife(a.getPlayer().getLife()-b.getPlayer().getBullets().get(i).getDamage());
					b.getPlayer().getBullets().remove(i);
					System.out.println(b.getPlayer().getLife());
					
					if(a.getPlayer().getLife()==50) {
						Life life = new Life("Media");
						String line = gson.toJson(life);
						a.sendMessages(line);
					} if(a.getPlayer().getLife()==25){
						Life life = new Life("Baja");
						String line = gson.toJson(life);
						a.sendMessages(line);	
					}
					
				} else {
					if(b.getPlayer().getBullets().get(i).getPosX()>1220) {
						b.getPlayer().getBullets().remove(i);
					}
				}
			}
			
		
	}
	
	public void addPowerup() {
			if(frameCount%900==0) {
				powers.add(new PowerUp(this, (int)random(0,2),(int) random(0,1200), (int)random(140,400)));
				
			}
		
	}
	public void takePowerup() {
		for(int i=0; i< tcp.getSessions().size(); i++) {
			for (int j = 0; j < powers.size(); j++) {
				if(tcp.getSessions().get(i).getPlayer().getPosX()>powers.get(j).getPosX()-25 &&
						tcp.getSessions().get(i).getPlayer().getPosX()<powers.get(j).getPosX()+25 &&
							tcp.getSessions().get(i).getPlayer().getPosY()>powers.get(j).getPosY()-25 && 
								tcp.getSessions().get(i).getPlayer().getPosY()<powers.get(j).getPosY()+25) {
					System.out.println("probando 1,2,3");
					tcp.getSessions().get(i).getPlayer().setWeapon(powers.get(j).getName());
					if(powers.size()>0) {
						powers.remove(powers.get(j));
					}
				}
			}
			
		}
			
		
	}
	

	public void endGame() {
		for(int i=0; i< tcp.getSessions().size(); i++) {
			if(tcp.getSessions().size()==2) {
				if(tcp.getSessions().get(i).getPlayer().getLife()<=0){
					endGame = true;
				}
			}
		}
	}
	
	
	@Override
	public void OnMessage(Session s,String msg) {
		
		// TODO Auto-generated method stub
		s.getPlayer().setName(msg);
	}

	@Override
	public void newPosition(Session s, int x, int y) {
		if(x>50 || x<1250 || y>140 || y<400) {
			// TODO Auto-generated method stub
			s.getPlayer().setPosX(x);
			s.getPlayer().setPosY(y);
			System.out.println(s.getPlayer().getPosX() +" " + s.getPlayer().getPosY());
		}

	}

	@Override
	public void newBullet(Session s,Bullet b) {
		// TODO Auto-generated method stub
		b.setPosX(s.getPlayer().getPosX());
		b.setPosY(s.getPlayer().getPosY());
		s.getPlayer().getBullets().add(b);
	
	}
	
	
	
	

}
