package chatNI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import remoteApplication.Hello;
import remoteApplication.Message;

public class UDPReceiver {

	private DatagramSocket socket ;
	private DatagramPacket packet ;
	
	// constructeur
	public UDPReceiver(DatagramSocket socket){
		this.socket = socket ;
	}
	
	public void receive() throws IOException, ClassNotFoundException{
		byte[] buffer = new byte[10000];
		packet = new DatagramPacket(buffer, buffer.length);
		System.out.println("waiting for the packet....");
		socket.receive(packet);
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer));
		Message messageReceived = (Hello) in.readObject() ;
		System.out.println("packet received");
		System.out.println("The nickname is :" + messageReceived.getNickname());
	}
}
