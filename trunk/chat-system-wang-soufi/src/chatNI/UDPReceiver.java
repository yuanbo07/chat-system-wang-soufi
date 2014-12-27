package chatNI;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;
import chatsystemTDa2.*;

/**
 * Class UDPReceiver
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class UDPReceiver extends Thread implements Observer {

	private DatagramSocket socket ;
	private DatagramPacket receivedPacket ;
	private ChatNI chatNI ;
	private ObjectInputStream objectInputStream ;
	private byte[] buffer ;
	
	public UDPReceiver(DatagramSocket socket, ChatNI chatNI){
		this.socket = socket ;
		this.chatNI = chatNI ;
	}
	
	public void run(){
		buffer = new byte[Network.bufferSize];
		receivedPacket = new DatagramPacket(buffer, buffer.length);
		try{
			while(true){
				socket.receive(receivedPacket);
				objectInputStream = new ObjectInputStream(new ByteArrayInputStream(buffer));
				ThreadUDPReceiver threadUDPReceiver = new ThreadUDPReceiver(objectInputStream,chatNI);
				threadUDPReceiver.addObserver(this);
				threadUDPReceiver.run();
			}
		}
		catch(IOException e1){
		}
	}

	public void update(Observable o, Object receivedPacket) {
	
		if(receivedPacket instanceof Hello)
		{
			String senderAddress = getReceivedPacket().getAddress().getHostAddress();
			try {		
				if(!Network.isOfLocalInterface(senderAddress)){
					String remoteUserNickname = ((Hello)receivedPacket).getNickname();
					InetAddress remoteUserIP = getReceivedPacket().getAddress();
					chatNI.receiveHello(remoteUserNickname, remoteUserIP);
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		if(receivedPacket instanceof HelloAck)
		{
			String remoteUserNickname = ((HelloAck)receivedPacket).getNickname();
			InetAddress remoteUserIP = getReceivedPacket().getAddress();
			chatNI.receiveHelloAck(remoteUserNickname, remoteUserIP);
		}
		
		if(receivedPacket instanceof Goodbye)
		{
			InetAddress remoteUserIP = getReceivedPacket().getAddress();
			try {
				if(!Network.isOfLocalInterface(remoteUserIP.getHostAddress())){
					String remoteUserNickname = ((Goodbye)receivedPacket).getNickname();
					chatNI.receiveGoodbye(remoteUserNickname, remoteUserIP);
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
		
		if(receivedPacket instanceof Send)
		{
			InetAddress remoteUserIP = getReceivedPacket().getAddress();
			int receivedID = ((Send)receivedPacket).getID();
			String receivedMessage = ((Send)receivedPacket).getMessage();
			chatNI.receiveMessage(remoteUserIP, receivedID, receivedMessage);
		}
		
		if(receivedPacket instanceof SendAck)
		{
			InetAddress remoteUserIP = getReceivedPacket().getAddress();
			int receivedID = ((SendAck)receivedPacket).getId_message();
			chatNI.receiveSendAck(remoteUserIP, receivedID);
		}
		
		if(receivedPacket instanceof FileRequest){
			InetAddress remoteUserIP = getReceivedPacket().getAddress();
			String fileRequestName = ((FileRequest)receivedPacket).getName();
			chatNI.receiveFileRequest(remoteUserIP,fileRequestName);
		}
		
		if(receivedPacket instanceof FileResponse){
			InetAddress remoteUserIP = getReceivedPacket().getAddress();
			String fileResponseName = ((FileResponse)receivedPacket).getName();
			boolean response = ((FileResponse)receivedPacket).getResponse() ;
			chatNI.receiveFileResponse(response, remoteUserIP, fileResponseName);
		}
	}

	public DatagramPacket getReceivedPacket() {
		return receivedPacket;
	}
}