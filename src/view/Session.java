package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Session extends Thread{
	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;
	private OnMessageListener observer;
	
	public Session(Socket socket) {
		this.socket = socket;
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
	        	this.observer.OnMessage(line);
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
	

}
