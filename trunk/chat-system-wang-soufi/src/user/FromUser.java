package user;

import java.io.File;
import java.net.InetAddress;

/**
 * Interface FromUser
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public abstract interface FromUser {
	
	public void connect();
	
	public void disconnect();
	
	public void sendMessage(String message);
	
	public void sendMessageUnicast(String message, InetAddress ip);
	
	public void sendFile(File fileChosen);
	
	public void sendFileUnicast(InetAddress remoteUserIP, File fileChosen);
	
}