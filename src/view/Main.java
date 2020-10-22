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
	
	private Gson gson;
	private Player playerJ1;
	private PowerUp prueba;
	private boolean endGame;
	
	
	public void settings() {
		size(1400,800);
	}
	
	public void setup() {
		tcp = TCPServer.getInstance();
		tcp.setObserver(this);
		gson = new Gson();
		endGame = false;
		prueba = new PowerUp(this, (int)random(0,2),(int) random(0,200), (int)random(0,200));
		
	}
	
	public void draw() {
		background(180);
		//veo quien esta conetado
		if(!endGame) {
			for(int i=0; i< tcp.getSessions().size(); i++) {
				//esto es para poder recorrer las sesiones de forma más suave
				Session session = tcp.getSessions().get(i);
				
				//creo una condición para iniciar a pintar los personajes, si existen dos
				//if(tcp.getSessions().size()==2) {
					//System.out.println( tcp.getSessions().size());
					
					if(tcp.getSessions().size()==2) {
						fill(0);
						textSize(25);
						if(i%2==0) {
							session.getPlayer().setApp(this);
							session.getPlayer().setImg(loadImage("./../media/img/PersonajeJ1.png"));
							
						}else {
							session.getPlayer().setApp(this);
							session.getPlayer().setImg(loadImage("./../media/img/PersonajeJ2.png"));
						}
						session.getPlayer().paint(session.getPlayer().getPosX(), session.getPlayer().getPosY());	
					}
				//}
			}
			
			if(tcp.getSessions().size()==2) {
				
				//prueba.paint();
				impactBullet(tcp.getSessions().get(0),tcp.getSessions().get(1));
				endGame();
			}
		} else {
			if(tcp.getSessions().get(0).getPlayer().compareTo(tcp.getSessions().get(1).getPlayer())) {
				text("Ganador: "+tcp.getSessions().get(1).getPlayer().getName(),width/2,height/2);
			} else {
				text("Ganador: "+tcp.getSessions().get(0).getPlayer().getName(),width/2,height/2);
			}
			
		}
		
		
	}
	
	public void impactBullet(Session a,Session b) {
		
		//if(tcp.getSessions().size()==2) {
			///si bala le pega a jugador 1
			if(a.getPlayer().getBullets().size()>0) {
				
				//System.out.println("hay balas");
				/*System.out.println(a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getPosX()+"y"+
						a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getPosY() + " " + 
						b.getPlayer().getPosX() + b.getPlayer().getPosY());*/
				
				if(a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getPosX()>b.getPlayer().getPosX()
						&& a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getPosX()<b.getPlayer().getPosX()+50 
						&& a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getPosY()>b.getPlayer().getPosY()-50
						&& a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getPosY()<b.getPlayer().getPosY()+100) {
					b.getPlayer().setLife(b.getPlayer().getLife()-a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getDamage());
					a.getPlayer().getBullets().remove(a.getPlayer().getBullets().size()-1);
					System.out.println(b.getPlayer().getLife());
				} else {
					if(a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getPosX()>1220) {
						a.getPlayer().getBullets().remove(a.getPlayer().getBullets().size()-1);
					}
				}
						
			}
			
			if(b.getPlayer().getBullets().size()>0) {
				if(b.getPlayer().getBullets().get(b.getPlayer().getBullets().size()-1).getPosX()>a.getPlayer().getPosX()
						&& b.getPlayer().getBullets().get(b.getPlayer().getBullets().size()-1).getPosX()<a.getPlayer().getPosX()+50 
						&& b.getPlayer().getBullets().get(b.getPlayer().getBullets().size()-1).getPosY()>a.getPlayer().getPosY()-50
						&& b.getPlayer().getBullets().get(b.getPlayer().getBullets().size()-1).getPosY()<a.getPlayer().getPosY()+100) {
					a.getPlayer().setLife(a.getPlayer().getLife()-a.getPlayer().getBullets().get(a.getPlayer().getBullets().size()-1).getDamage());
					b.getPlayer().getBullets().remove(b.getPlayer().getBullets().size()-1);
					System.out.println(b.getPlayer().getLife());
				} else {
					if(b.getPlayer().getBullets().get(b.getPlayer().getBullets().size()-1).getPosX()>1220) {
						b.getPlayer().getBullets().remove(b.getPlayer().getBullets().size()-1);
					}
				}
			}
			
			
			
		//}
		
	}
	
	public void takePowerup() {
		if(tcp.getSessions().size()==2) {
			if(playerJ1.getPosX()>prueba.getPosX() && playerJ1.getPosX()<prueba.getPosX()+50
					&& playerJ1.getPosY()>prueba.getPosY() && playerJ1.getPosY()<prueba.getPosY()+50) {
				System.out.println("probando 1,2,3");
				playerJ1.setWeapon(prueba.getName());
				playerJ1.getBullets().get(playerJ1.getBullets().size()-1).setMov(10);
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
		// TODO Auto-generated method stub
		s.getPlayer().setPosX(x);
		s.getPlayer().setPosY(y);
		System.out.println(s.getPlayer().getPosX() +" " + s.getPlayer().getPosY());
	}

	@Override
	public void newBullet(Session s,Bullet b) {
		// TODO Auto-generated method stub
		b.setPosX(s.getPlayer().getPosX());
		b.setPosY(s.getPlayer().getPosY());
		s.getPlayer().getBullets().add(b);
	}
	
	
	
	

}
