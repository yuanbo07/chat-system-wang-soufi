package chatNI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import remoteApplication.Hello;
import remoteApplication.Message;

public class UDPReceiver extends Thread {

	private DatagramSocket socket ;
	private DatagramPacket packet ;
	private ChatNI chatNI ;
	
	// constructeur
	public UDPReceiver(DatagramSocket socket, ChatNI chatNI){
		this.socket = socket ;
		this.chatNI = chatNI ;
	}
	
	public void run(){
		byte[] buffer = new byte[10000];
		packet = new DatagramPacket(buffer, buffer.length);
		try{
			while(true){
				socket.receive(packet);
				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer));
				ThreadUDPReceiver newUDPReceiver = new ThreadUDPReceiver(in,chatNI);
				newUDPReceiver.start();
			}
		}
		catch(IOException e1){
		}
	}
}