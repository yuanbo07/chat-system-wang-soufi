package chatNI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class UDPSender
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class UDPSender extends Thread {
	
	private DatagramSocket socket ;
	private DatagramPacket packet ;
	
	public UDPSender(DatagramSocket socket){
		this.socket = socket ;
	}
	
	/**
	 * Send message in unicast mode
	 * @param messageToSend
	 * @param destIP
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void sendEnUnicast(Object messageToSend, InetAddress destIP) throws UnknownHostException, IOException{
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput objectMessageToSend = new ObjectOutputStream(bStream); 
		objectMessageToSend.writeObject(messageToSend);
		objectMessageToSend.close();
		byte[] Buf = bStream.toByteArray();
		packet = new DatagramPacket(Buf,Buf.length,destIP,Network.udpReceiverPort);
		socket.send(packet);
	}
	
	/**
	 * Send message in broadcast mode
	 * @param messageToSend
	 * @throws IOException
	 */
	public void sendEnBroadcast(Object messageToSend) throws IOException{
		InetAddress destIP = InetAddress.getByName(Network.broadcastAddress);
		ByteArrayOutputStream bStream = new ByteArrayOutputStream();
		ObjectOutput objectMessageToSend = new ObjectOutputStream(bStream); 
		objectMessageToSend.writeObject(messageToSend);
		objectMessageToSend.close();
		byte[] Buf = bStream.toByteArray();
		packet = new DatagramPacket(Buf, Buf.length,destIP,Network.udpReceiverPort);
		socket.send(packet);
	}
}