package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer extends Thread{
	
	private TCPServer() {
	}
	
	protected static TCPServer instanceUnique;
	
	protected static TCPServer getInstance() {
		if(instanceUnique==null) {
			instanceUnique = new TCPServer();
			instanceUnique.start();
		}
		return instanceUnique;
		
	}
	
	private ServerSocket server;
	private OnMessageListener observer;
	
	public void setObserver(OnMessageListener observer) {
		// TODO Auto-generated method stub
		this.observer = observer;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Iniciando servidor");
			server = new ServerSocket(5000);
			
			while(true) {
				System.out.println("Esperando...");
				Socket socket = server.accept();
				Session session = new Session(socket);
				session.setObserver(observer);
				System.out.println("Conexión asegurada");
			}
	
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	
	
	
}
