package main;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import chatController.ChatController;
import chatGUI.ChatGUI;
import chatModel.ChatModel;
import chatNI.ChatNI;
import chatNI.UDPReceiver;
import chatNI.UDPSender;

public class ChatSystem {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		
		ChatModel chatModel = new ChatModel();
		ChatNI chatNI = new ChatNI();
		ChatGUI chatGUI =new ChatGUI();
		ChatController chatController = new ChatController(chatModel,chatNI,chatGUI);
		
		DatagramSocket receiverSocket = new DatagramSocket(5003);
		//DatagramSocket senderSocket = new DatagramSocket(5002); 
		
		UDPReceiver receiver = new UDPReceiver(receiverSocket);
		receiver.receive();
		//UDPSender sender = new UDPSender(senderSocket);
		//sender.send();
	}
}