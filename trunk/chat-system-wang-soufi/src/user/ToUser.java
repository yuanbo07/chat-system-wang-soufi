package user;

import java.net.InetAddress;

/**
 * Interface ToUser
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public abstract interface ToUser {
	
	public void connected();
	
	public void disconnected();
	
	public void displayMessage(InetAddress remoteUserIP, String receivedMessage);
	
	public void displaySentMessage(String sentMessage);
	
	public void displaySentPrivateMessage(String sentMessage, String username);
	
	public void askForFileTransfertPermission(InetAddress remoteUserIP, String fileRequestName);
	
	public void showFileRequestAcceptedDialog(String fileResponseName, InetAddress remoteUserIP);
	
	public void showFileRequestRefusedDialog(String fileResponseName, InetAddress remoteUserIP);
	
	public void showReceivedNewFile(String filename);
	
}
