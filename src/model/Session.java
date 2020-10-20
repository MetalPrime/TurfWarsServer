package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.Gson;

public class Session extends Thread{
	
	private String id;
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	private OnMessageListener observer;
	
	public Session(Socket socket) {
		this.socket = socket;
		this.id = UUID.randomUUID().toString();
	}
	
	public void setObserver(OnMessageListener observer) {
		// TODO Auto-generated method stub
		this.observer = observer;
	}
	
	public void run() {
		try {
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			reader = new BufferedReader(isr);
			
	        OutputStream os = socket.getOutputStream();
	        OutputStreamWriter osw = new OutputStreamWriter(os);
	        writer = new BufferedWriter(osw);
	        
	        
	        while (true){
	        	System.out.println("Esperando Mensaje");
	        	String line = reader.readLine();
	        	System.out.println("Recibido:"+" "+line);
	        	
	        	Gson gson = new Gson();
	        	Generic generic = gson.fromJson(line, Generic.class);
	        	
	        	switch(generic.getType()) {
	        	case  "Name":
	        		Name name = gson.fromJson(line, Name.class);
	        		this.observer.OnMessage(this,name.getName());
	        		break;
	        	case "Coordinate":
	        		Coordinate coord = gson.fromJson(line, Coordinate.class);
	        		this.observer.newPosition(this, coord.getPosX(), coord.getPosY());
	        		break;
	        	case "Bullet":
	        		Bullet bullet = gson.fromJson(line,Bullet.class);
	        		this.observer.newBullet(bullet);
	        		break;
	        	}
	        	
	        	//this.observer.OnMessage(line);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void sendMessages(String msg){
        new Thread(
                () ->{
                    try {
                            writer.write(msg+"\n");
                            writer.flush();
                    } catch (IOException e){
                        e.printStackTrace();

                    }
                }
        ).start();


    }

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}
	

}
