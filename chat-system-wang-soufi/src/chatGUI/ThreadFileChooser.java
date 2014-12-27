package chatGUI;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JFileChooser;
import chatNI.FileSender;

/**
 * Class ThreadFileChooser
 * The thread of a file chooser used to avoid any interrupt of main thread of ChatGUI
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */

public class ThreadFileChooser extends Thread {
	
	private ChatGUI chatGUI ;
	private boolean isBroadcast = false ;
	private String remoteUserIP ;
	private InetAddress addr ;
	
	public ThreadFileChooser(ChatGUI chatGUI, boolean isBroadcast){
		this.chatGUI = chatGUI ;
		this.isBroadcast = isBroadcast ;
	}
	
	public ThreadFileChooser(ChatGUI chatGUI, boolean isBroadcast, String remoteUserIP){
		this.chatGUI = chatGUI ;
		this.isBroadcast = isBroadcast ;
		this.remoteUserIP = remoteUserIP ;
	}
	
	public void run(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose a file to send");
		int returnVal = fileChooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {	
			File fileChosen = fileChooser.getSelectedFile() ;
			
			FileSender newFileSender = new FileSender(fileChosen,null);
			newFileSender.start();
			if(isBroadcast){
				chatGUI.sendFile(fileChosen);
			}
			else{
				try {
					addr = InetAddress.getByName(remoteUserIP);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				chatGUI.sendFileUnicast(addr, fileChosen);
			}
		}
		fileChooser.setVisible(true);
	}

	public boolean isBroadcast() {
		return isBroadcast;
	}

	public void setBroadcast(boolean isBroadcast) {
		this.isBroadcast = isBroadcast;
	}

	public String getRemoteUserIP() {
		return remoteUserIP;
	}
}