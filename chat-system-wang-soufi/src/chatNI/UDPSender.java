package chatNI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPSender extends Thread {
	
	private DatagramSocket socket ;
	private String ip = "192.168.10.129" ; // loopback
	InetAddress dest ;
	private DatagramPacket packet ;
	
	// constructeur
	public UDPSender(DatagramSocket socket){
		this.socket = socket ;
	}
	
	public void sendEnUnicast(Object o) throws UnknownHostException, IOException{
		dest = InetAddress.getByName(ip);
		// Serialize to a byte array
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(o);
		oo.close();
		byte[] Buf = bStream.toByteArray();
		packet = new DatagramPacket(Buf,Buf.length,dest,5003);
		socket.send(packet);
	}
	
	public void sendEnBroadcast(Object o) throws IOException{
		
		
		
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput oo = new ObjectOutputStream(bStream); 
		oo.writeObject(o);
		oo.close();
		byte[] Buf = bStream.toByteArray();
		packet = new DatagramPacket(Buf, Buf.length,InetAddress.getByName("192.168.10.255"),5003);
		socket.send(packet);
	}
}