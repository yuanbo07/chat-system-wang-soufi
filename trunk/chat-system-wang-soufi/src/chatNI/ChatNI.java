package chatNI;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import chatController.ChatController;
import chatsystemTDa2.* ;

public class ChatNI extends Thread {
	
	private DatagramSocket UDPReceiverSocket ;
	private ChatController controller ;
	private UDPReceiver udpReceiver ;
	private String localNickname ;
	private boolean socketInitialized ;
	private int sendId ;
	private ArrayList<File> pendingFilesToSend = new ArrayList<File>();
	
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
	
	public void sendMessage(String sendMessage, InetAddress ip){
		Send send = new Send(sendMessage, sendId);
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			sender.sendEnUnicast(send, ip);
			sendId++;
			senderSocket.close();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	public void sendMessage(String sendMessage){

		Send send = new Send(sendMessage, sendId);
		DatagramSocket senderSocket;
		int i;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			
			for(i=0;i<controller.getChatModel().getUserlist().size();i++){
				InetAddress destIP = controller.getChatModel().getUserlist().get(i).getIp();
				sender.sendEnUnicast(send, destIP);
				sendId++;
			}
			senderSocket.close();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	

	public void sendFileRequest(String fileName) {
		FileRequest fileRequest = new FileRequest(fileName);
		DatagramSocket senderSocket;
		int i;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			
			for(i=0;i<controller.getChatModel().getUserlist().size();i++){
				InetAddress destIP = controller.getChatModel().getUserlist().get(i).getIp();
				sender.sendEnUnicast(fileRequest, destIP);
			}
			senderSocket.close();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}

	public void sendFileResponse(boolean response, InetAddress remoteUserIP, String fileRequestName) {
		FileResponse fileResponse = new FileResponse(response, fileRequestName);
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(5002);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			sender.sendEnUnicast(fileResponse, remoteUserIP);
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
	
	public void processReceive(InetAddress remoteUserIP, int receivedID, String receivedMessage){
		controller.processReceive(remoteUserIP, receivedID, receivedMessage);
	}
	
	public void processGoodbye(String remoteUserNickname, InetAddress remoteUserIP) throws SocketException, UnknownHostException{
		controller.processGoodbye(remoteUserNickname, remoteUserIP);
	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}

	public boolean isSocketInitialized() {
		return socketInitialized;
	}

	public void processFileRequest(InetAddress remoteUserIP, String fileRequestName) {
		controller.processFileRequest(remoteUserIP, fileRequestName);
	}

	public void processFileResponse(boolean response, InetAddress remoteUserIP,String fileResponseName) {
		controller.processFileResponse(response, remoteUserIP, fileResponseName);
	}

	public void sendFile(String fileResponseName, InetAddress remoteUserIP) {
		int i;
		for(i=0;i<pendingFilesToSend.size();i++){
			if(pendingFilesToSend.get(i).getName().equals(fileResponseName)){
				System.out.println("The file name is :"+pendingFilesToSend.get(i).getName());
				File fileToSend = pendingFilesToSend.get(i) ;
				FileSender newFileSender = new FileSender(fileToSend,remoteUserIP);
				newFileSender.start();
			}
		}
	}

	public ArrayList<File> getPendingFilesToSend() {
		return pendingFilesToSend;
	}

	public void initFileReceiveSocket() {
		FileReceiver newFileReceiver = new FileReceiver(null);
		newFileReceiver.start();
	}
}