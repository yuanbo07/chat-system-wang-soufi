package user;

import java.io.File;
import java.net.InetAddress;

public abstract interface FromUser {
	
	
	public void Connect();
	
	public void Disconnect();
	
	public void SendMessage(String message);
	
	public void SendMessageUnicast(String message, InetAddress ip);
	
	public void SendFile(File fileChosen);
	
}
