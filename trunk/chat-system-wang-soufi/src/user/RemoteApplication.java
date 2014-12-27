package user;

import java.net.InetAddress;

/**
 * Interface RemoteApplication
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public interface RemoteApplication {
	
	public void sendHello(String localNickname);
	
	public void sendHelloAck(InetAddress remoteIP);
	
	public void sendGoodbye(String remoteNickname);
	
	public void sendMessage(String sendMessage, InetAddress ip);
	
	public void sendMessage(String sendMessage);
	
	public void sendFileRequest(String fileName);
	
	public void sendFileRequestUnicast(InetAddress remoteUserIP, String fileName);
	
	public void sendFileResponse(boolean response, InetAddress remoteUserIP, String fileRequestName);
	
	public void sendFile(String fileResponseName, InetAddress remoteUserIP);
	
	public void receiveHello(String remoteUserNickname, InetAddress remoteUserIP);
	
	public void receiveHelloAck(String remoteUserNickname, InetAddress remoteUserIP);
	
	public void receiveMessage(InetAddress remoteUserIP, int receivedID, String receivedMessage);
	
	public void receiveGoodbye(String remoteUserNickname, InetAddress remoteUserIP);
	
	public void receiveSendAck(InetAddress remoteUserIP, int receivedID);
	
	public void receiveFileRequest(InetAddress remoteUserIP, String fileRequestName);
	
	public void receiveFileResponse(boolean response, InetAddress remoteUserIP,String fileResponseName);

}
