package chatNI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import remoteApplication.Hello;
import remoteApplication.Message;

public class UDPSender {
	
	private DatagramSocket socket ;
	private String ip = "192.168.10.1" ; // loopback
	InetAddress dest ;
	private DatagramPacket packet ;
	
	// constructeur
	public UDPSender(DatagramSocket socket){
		this.socket = socket ;
	}
	
	// envoyer un DatagramPacket
	public void send() throws UnknownHostException, IOException{

		Message hello = new Hello("Yuanbo");
		dest = InetAddress.getByName(ip);
		
		// Serialize to a byte array
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(hello);
		oo.close();
		byte[] Buf = bStream.toByteArray();
		packet = new DatagramPacket(Buf,Buf.length,dest,5003);
		socket.send(packet);
	}
}
