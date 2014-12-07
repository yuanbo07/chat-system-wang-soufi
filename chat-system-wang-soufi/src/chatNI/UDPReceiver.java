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

import chatsystemTDa2.Goodbye;
import chatsystemTDa2.Hello;
import chatsystemTDa2.HelloAck;

public class UDPReceiver extends Thread implements Observer {

	private DatagramSocket socket ;
	private DatagramPacket receivedPacket ;
	private ChatNI chatNI ;
	
	public UDPReceiver(DatagramSocket socket, ChatNI chatNI){
		this.socket = socket ;
		this.chatNI = chatNI ;
	}
	
	public void run(){
		byte[] buffer = new byte[10000];
		receivedPacket = new DatagramPacket(buffer, buffer.length);
		try{
			while(true){
				socket.receive(receivedPacket);
				ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(buffer));
				ThreadUDPReceiver threadUDPReceiver = new ThreadUDPReceiver(in,chatNI);
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
				if(!LocalInetAddress.isOfLocalInterface(senderAddress)){
					String remoteUserNickname = ((Hello)receivedPacket).getNickname();
					InetAddress remoteUserIP = getReceivedPacket().getAddress();
					chatNI.processHello(remoteUserNickname, remoteUserIP);
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
			try {
				chatNI.processHelloAck(remoteUserNickname, remoteUserIP);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		
		if(receivedPacket instanceof Goodbye)
		{
			String remoteUserNickname = ((Goodbye)receivedPacket).getNickname();
			InetAddress remoteUserIP = getReceivedPacket().getAddress();
			chatNI.processGoodbye(remoteUserNickname, remoteUserIP);
		}
		
	}

	public DatagramPacket getReceivedPacket() {
		return receivedPacket;
	}
}