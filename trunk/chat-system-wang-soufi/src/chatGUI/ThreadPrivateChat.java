package chatGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Class ThreadPrivateChat
 * The thread of a private chat used to avoid any interrupt of main thread of ChatGUI as well as other private chats
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class ThreadPrivateChat extends Thread implements ActionListener{
	
	private PrivateChatFrame privateChatFrame ;
	private ChatGUI chatGUI ;
	private String username ;
	private String ip ;
	
	public ThreadPrivateChat(String username, String ip, ChatGUI chatGUI, UserListMouseAdapter windowListener){
		this.chatGUI = chatGUI ;
		this.username = username; 
		this.ip = ip ;
		privateChatFrame = new PrivateChatFrame(username, ip);
		privateChatFrame.addWindowListener(windowListener);
		privateChatFrame.setVisible(true);
		privateChatFrame.getBtnSendFile().addActionListener(this);
		privateChatFrame.getBtnSendPrivateMessage().addActionListener(this);
		privateChatFrame.getInfoLabel().setText("Only "+ username+"["+ip+"] can receive your message or file request. \n");
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()== privateChatFrame.getBtnSendPrivateMessage()){
			String message = privateChatFrame.getTextAreaSendPrivateMessage().getText();
			try {
				chatGUI.sendMessageUnicast(message,InetAddress.getByName(ip));
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			chatGUI.displaySentPrivateMessage(message, username);
			privateChatFrame.getTextAreaSendPrivateMessage().setText(null);
		}
		if(e.getSource()== privateChatFrame.getBtnSendFile()){
			ThreadFileChooser fileChooser = new ThreadFileChooser(chatGUI,false,ip);
			fileChooser.start();
		}
	}

	public ChatGUI getChatGUI() {
		return chatGUI;
	}

	public PrivateChatFrame getPrivateChatFrame() {
		return privateChatFrame;
	}

	public String getUsername() {
		return username;
	}

	public String getIp() {
		return ip;
	}
}