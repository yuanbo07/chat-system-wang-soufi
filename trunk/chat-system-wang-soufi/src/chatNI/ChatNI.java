package chatNI;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Observable;

import chatController.ChatController;
import chatsystemTDa2.Goodbye;
import chatsystemTDa2.Hello;
import chatsystemTDa2.HelloAck;
import chatsystemTDa2.Message;

public class ChatNI extends Thread {
	
	private DatagramSocket UDPReceiverSocket ;
	private ChatController controller ;
	private UDPReceiver udpReceiver ;
	private String localNickname ;
	private boolean socketInitialized ;
	
	public ChatNI() throws SocketException{
	}
	
	public void initSocket(){
		try {
			UDPReceiverSocket = new DatagramSocket(16050);
			udpReceiver = new UDPReceiver(UDPReceiverSocket, this);
			udpReceiver.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		socketInitialized = true ;
	}
	
	public void closeSocket(){
		UDPReceiverSocket.close();
		socketInitialized = false ;
	}
	
	public void sendHello(String localNickname){
		this.localNickname = localNickname ;
		Message hello = new Hello(localNickname);	
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			sender.sendEnBroadcast(hello);
			senderSocket.close();

		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public void sendHelloAck(InetAddress remoteIP){
		Message helloAck = new HelloAck(localNickname);
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			sender.sendEnUnicast(helloAck, remoteIP);
			senderSocket.close();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public void sendGoodbye(String remoteNickname){
		Message goodbye = new Goodbye(remoteNickname);
		DatagramSocket senderSocket;
		int i;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();

			for(i=0;i<controller.getChatModel().getUserlist().size();i++){
				InetAddress destIP = controller.getChatModel().getUserlist().get(i).getIp();
				sender.sendEnUnicast(goodbye, destIP);
			}
			
			senderSocket.close();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public void processHello(String remoteUserNickname, InetAddress remoteUserIP){
		controller.processHello(remoteUserNickname, remoteUserIP);
	}
	
	public void processHelloAck(String remoteUserNickname, InetAddress remoteUserIP) throws UnknownHostException, SocketException{
		controller.processHelloAck(remoteUserNickname, remoteUserIP);
	}
	
	public void processGoodbye(String remoteUserNickname, InetAddress remoteUserIP){
		controller.processGoodbye(remoteUserNickname, remoteUserIP);
	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}

	public void update(Observable arg0, Object receivedPacket) {

	}

	public boolean isSocketInitialized() {
		return socketInitialized;
	}
}