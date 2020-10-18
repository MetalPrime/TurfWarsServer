package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TCPServer extends Thread{
	
	private TCPServer() {
	}
	
	protected static TCPServer instanceUnique;
	
	public static TCPServer getInstance() {
		if(instanceUnique==null) {
			instanceUnique = new TCPServer();
			instanceUnique.start();
		}
		return instanceUnique;
		
	}
	
	private ServerSocket server;
	private OnMessageListener observer;
	private ArrayList<Session> sessions;
	
	public void setObserver(OnMessageListener observer) {
		// TODO Auto-generated method stub
		this.observer = observer;
	}
	
	@Override
	public void run() {
		try {
			System.out.println("Iniciando servidor");
			sessions = new ArrayList<Session>();
			server = new ServerSocket(5000);
			
			while(true) {
				System.out.println("Esperando...");
				Socket socket = server.accept();
				Session session = new Session(socket);
				session.setObserver(observer);
				session.start();
				System.out.println("Conexión asegurada");
				sessions.add(session);
			}
	
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ArrayList<Session> getSessions() {
		return sessions;
	}

	public void setSessions(ArrayList<Session> sessions) {
		this.sessions = sessions;
	}
	


	
	
	
}
