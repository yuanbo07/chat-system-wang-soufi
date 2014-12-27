package chatNI;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import user.RemoteApplication;
import chatController.ChatController;
import chatsystemTDa2.* ;

/**
 * Class ChatNI
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class ChatNI extends Thread implements Observer, RemoteApplication {
	
	private DatagramSocket UDPReceiverSocket ;
	private ChatController controller ;
	private UDPReceiver udpReceiver ;
	private String localNickname ;
	private boolean socketInitialized ;
	private int sendId ;
	private ArrayList<File> pendingFilesToSend = new ArrayList<File>();
	private FileReceiver newFileReceiver ;
	
	public ChatNI() throws SocketException{
	}
	
	/**
	 * Initiate UDP receiving socket
	 */
	public void initSocket(){
		try {
			UDPReceiverSocket = new DatagramSocket(Network.udpReceiverPort);
			udpReceiver = new UDPReceiver(UDPReceiverSocket, this);
			udpReceiver.start();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		socketInitialized = true ;
	}
	
	/**
	 * Close a UDP receiving socket
	 */
	public void closeSocket(){
		UDPReceiverSocket.close();
		socketInitialized = false ;
	}
	
	/**
	 * Send a Hello message in broadcast mode
	 */
	public void sendHello(String localNickname){
		this.localNickname = localNickname ;
		Message hello = new Hello(localNickname);	
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(Network.udpSenderPort);
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
	
	/**
	 * Send a HelloAck message
	 */
	public void sendHelloAck(InetAddress remoteIP){
		Message helloAck = new HelloAck(localNickname);
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(Network.udpSenderPort);
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
	
	/**
	 * Send a Goodbye message
	 */
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
	
	/**
	 * Send a message in unicast mode (used in private chat)
	 */
	public void sendMessage(String sendMessage, InetAddress ip){
		Send send = new Send(sendMessage, sendId);
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(Network.udpSenderPort);
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
	
	/**
	 * Send a message to everyone
	 */
	public void sendMessage(String sendMessage){

		Send send = new Send(sendMessage, sendId);
		DatagramSocket senderSocket;
		int i;
		try {
			senderSocket = new DatagramSocket(Network.udpSenderPort);
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
	

	/**
	 * Send a FileRequest message to everyone who is connected
	 */
	public void sendFileRequest(String fileName) {
		FileRequest fileRequest = new FileRequest(fileName);
		DatagramSocket senderSocket;
		int i;
		try {
			senderSocket = new DatagramSocket(Network.udpSenderPort);
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

	/**
	 * Send a FileRequest message in unicast mode
	 */
	public void sendFileRequestUnicast(InetAddress remoteUserIP, String fileName) {
		FileRequest fileRequest = new FileRequest(fileName);
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(Network.udpSenderPort);
			UDPSender sender = new UDPSender(senderSocket);
			sender.start();
			sender.sendEnUnicast(fileRequest, remoteUserIP);
			senderSocket.close();
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
	}
	
	/**
	 * Send a FileResponse message
	 */
	public void sendFileResponse(boolean response, InetAddress remoteUserIP, String fileRequestName) {
		FileResponse fileResponse = new FileResponse(response, fileRequestName);
		DatagramSocket senderSocket;
		try {
			senderSocket = new DatagramSocket(Network.udpSenderPort);
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
	
	/**
	 * Send a file
	 */
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
	
	/**
	 * Initiate a file receiving socket
	 */
	public void initFileReceiveSocket(String fileRequestName) {
		newFileReceiver = new FileReceiver(fileRequestName);
		newFileReceiver.addObserver(this);
		newFileReceiver.run();
	}
	
	public void receiveHello(String remoteUserNickname, InetAddress remoteUserIP){
		controller.processHello(remoteUserNickname, remoteUserIP);
	}
	
	public void receiveHelloAck(String remoteUserNickname, InetAddress remoteUserIP){
		try {
			controller.processHelloAck(remoteUserNickname, remoteUserIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveMessage(InetAddress remoteUserIP, int receivedID, String receivedMessage){
		controller.processReceive(remoteUserIP, receivedID, receivedMessage);
	}
	
	public void receiveGoodbye(String remoteUserNickname, InetAddress remoteUserIP){
		try {
			controller.processGoodbye(remoteUserNickname, remoteUserIP);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void receiveSendAck(InetAddress remoteUserIP, int receivedID) {
		controller.processSendAck(remoteUserIP, receivedID);
	}
	
	public void receiveFileRequest(InetAddress remoteUserIP, String fileRequestName) {
		controller.processFileRequest(remoteUserIP, fileRequestName);
	}

	public void receiveFileResponse(boolean response, InetAddress remoteUserIP,String fileResponseName) {
		controller.processFileResponse(response, remoteUserIP, fileResponseName);
	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}

	public boolean isSocketInitialized() {
		return socketInitialized;
	}

	public ArrayList<File> getPendingFilesToSend() {
		return pendingFilesToSend;
	}

	public void update(Observable observable, Object object) {
		controller.processReceivedNewFile((String)object);
	}

}