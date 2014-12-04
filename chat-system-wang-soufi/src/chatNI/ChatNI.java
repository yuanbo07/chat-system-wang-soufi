package chatNI;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

import chatController.ChatController;
import remoteApplication.Hello;
import remoteApplication.Message;

public class ChatNI{
	
	private DatagramSocket UDPReceiverSocket ;
	private ChatController controller ;
	Thread sendThread ;
	UDPReceiver udpReceiver ;
	
	public ChatNI() throws SocketException{
		UDPReceiverSocket = new DatagramSocket(5003);
		udpReceiver = new UDPReceiver(UDPReceiverSocket, this);
		udpReceiver.start();
	}
	
	public void sendHello(String localNickname){
		Message hello = new Hello(localNickname);	
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			sender.sendEnBroadcast(hello);
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public void HelloACK(String remoteNickname){
		System.out.println("je suis passé dans NI");
		controller.processHelloACK(remoteNickname);
	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}
}
