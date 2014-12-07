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
	private DatagramPacket packet ;
	
	public UDPSender(DatagramSocket socket){
		this.socket = socket ;
	}
	
	public void sendEnUnicast(Object messageToSend, InetAddress destIP) throws UnknownHostException, IOException{
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput objectMessageToSend = new ObjectOutputStream(bStream); 
		objectMessageToSend.writeObject(messageToSend);
		objectMessageToSend.close();
		byte[] Buf = bStream.toByteArray();
		packet = new DatagramPacket(Buf,Buf.length,destIP,16050);
		socket.send(packet);
	}
	
	public void sendEnBroadcast(Object messageToSend) throws IOException{
		InetAddress destIP = InetAddress.getByName("192.168.10.255");
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput objectMessageToSend = new ObjectOutputStream(bStream); 
		objectMessageToSend.writeObject(messageToSend);
		objectMessageToSend.close();
		byte[] Buf = bStream.toByteArray();
		packet = new DatagramPacket(Buf, Buf.length,destIP,16050);
		socket.send(packet);
	}
}