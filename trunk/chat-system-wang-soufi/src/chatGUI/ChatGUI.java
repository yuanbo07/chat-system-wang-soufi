package chatGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.InetAddress;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import chatController.ChatController;
import chatModel.ChatModel;
import chatModel.User;
import chatNI.FileReceiver;
import user.FromUser;
import user.ToUser;

/**
 * Class ChatGUI
 * The "view" in the design pattern "MVC"
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */

public class ChatGUI extends Thread implements FromUser, ToUser, ActionListener, WindowListener, Observer {
	
	private MainFrame mainFrame ;
	private ChatController controller ;
	private String localUsername ;
	private ChatModel model ;
	private User lastUser ;
	private String receivedMessageUsername ;
	private boolean localUserIsConnected = false ;
	private boolean localUsernameAvailable = false ;
	
	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public ChatGUI(ChatModel model){
		mainFrame = new MainFrame();
		mainFrame.addWindowListener(this);
		mainFrame.getBtnConnect().addActionListener(this);
		mainFrame.getBtnDisconnect().addActionListener(this);
		mainFrame.getBtnSendMessage().addActionListener(this);
		mainFrame.getBtnSendFile().addActionListener(this);
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
		mainFrame.getOnlineUserlist().addMouseListener(new UserListMouseAdapter(this));
		mainFrame.getBtnDisconnect().setEnabled(false);
		this.model = model ;
		model.addObserver(this);
		this.start();
	}

	/**
	 * To inform the user who has been successfully connected
	 */
	public void connected(){
		mainFrame.getTextAreaReceiveMessage().append("System notification : You have successfully connected. \n");
		localUserIsConnected = true ;
	}

	/**
	 * To inform the user who has been successfully disconnected
	 */
	public void disconnected(){
		mainFrame.getTextAreaReceiveMessage().append("System notification : You have successfully disconnected. \n");
		localUserIsConnected = false ;
	}
	
	/**
	 * To add a user to the userlist
	 */
	@SuppressWarnings("unchecked")
	public void addUserToUserlist(String remoteNickname){
		int nbUser = model.getUserlist().size();
		lastUser = model.getUserlist().get(nbUser-1);
		String lastUserName = lastUser.getNickname();
		mainFrame.getTextAreaReceiveMessage().append("System notification : "+lastUserName+" is connected. \n");
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
	}
	
	/**
	 * To handle actions invoked by differents sources(buttons)
	 */
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==mainFrame.getBtnConnect()){
			connect();
		}
		
		if(e.getSource()==mainFrame.getBtnDisconnect()){
			disconnect();
		}
		
		if (e.getSource()==mainFrame.getBtnSendMessage()){
			if(localUserIsConnected){
				String message = mainFrame.getTextAreaSendMessage().getText();
				sendMessage(message);
				mainFrame.getTextAreaSendMessage().setText(null);
			}
			else{
				mainFrame.getTextAreaReceiveMessage().append("Error : To send a message, please connect first. \n");
			}
		}
		
		if (e.getSource()==mainFrame.getBtnSendFile()){
			if(localUserIsConnected){
				ThreadFileChooser fileChooser = new ThreadFileChooser(this, true);
				fileChooser.start();
			}
			else{
				mainFrame.getTextAreaReceiveMessage().append("Error : To send a file, please connect first. \n");
			}
		}
	}
	
	/**
	 * To update the user list
	 */
	@SuppressWarnings("unchecked")
	public void updateUserlist(){
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
		
	}
	
	/**
	 * To display a received message
	 */
	public void displayMessage(InetAddress remoteUserIP, String receivedMessage){
		int i;
		for (i=0;i<model.getUserlist().size();i++){
			if(model.getUserlist().get(i).getIp().equals(remoteUserIP))
			{
				receivedMessageUsername = model.getUserlist().get(i).getNickname();
			}
		}
		mainFrame.getTextAreaReceiveMessage().append(receivedMessageUsername+"["+remoteUserIP.getHostAddress()+"] : "+receivedMessage +"\n");
	}
	
	/**
	 * To display a sent message
	 */
	public void displaySentMessage(String sentMessage){
		mainFrame.getTextAreaReceiveMessage().append(localUsername+"[Localhost]" + " said to everyone : "+sentMessage +"\n");
	}
	
	/**
	 * To display a sent message in private chat mode
	 */
	public void displaySentPrivateMessage(String sentMessage, String username){
		mainFrame.getTextAreaReceiveMessage().append(localUsername+"[Localhost]" + " said to "+username+" : "+sentMessage +"\n");
	}
	
	/**
	 * Method of observer design pattern, execute actions according to any state changement of different objects (observables)
	 */
	public void update(Observable observable, Object object) {
		
		if(observable instanceof FileReceiver){
			mainFrame.getTextAreaReceiveMessage().append("You have received a file "+(String)object+ "\n");
		}
		else{
			if(object instanceof User){
				User user = (User)object;
				addUserToUserlist(user.getNickname());
			}
			
			if(object instanceof String){
				if(!((String)object).equals(localUsername)){
					mainFrame.getTextAreaReceiveMessage().append("System notification : "+object+" is disconnected. \n");
				}
			}
			updateUserlist();
		}
	}

	public void windowActivated(WindowEvent e) {	
	}

	public void windowClosed(WindowEvent e) {
	}

	/**
	 * When user closes the window, we perform his disconnection
	 */
	public void windowClosing(WindowEvent e) {
		if(localUserIsConnected){
			controller.processDisconnect(localUsername);
		}
	}

	public void windowDeactivated(WindowEvent e) {	
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {	
	}

	public void windowOpened(WindowEvent e) {	
	}

	public boolean isLocalUsernameAvailable() {
		return localUsernameAvailable;
	}

	public void setLocalUsernameAvailable(boolean localUsernameAvailable) {
		this.localUsernameAvailable = localUsernameAvailable;
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void connect() {
		if(!mainFrame.getTextFieldNickname().getText().equals("")){
			localUsername = mainFrame.getTextFieldNickname().getText() ;
			model.setLocalUsername(localUsername);
			mainFrame.getTextAreaReceiveMessage().append("System notification : Connecting...\n");
			mainFrame.getTextFieldNickname().setEditable(false);
			controller.processConnect(localUsername);
			mainFrame.getBtnConnect().setEnabled(false);
			mainFrame.getBtnDisconnect().setEnabled(true);
		}
		else{
			mainFrame.getTextAreaReceiveMessage().append("Error : The username cannot be null. \n");
		}
	}

	public void disconnect() {
		mainFrame.getBtnDisconnect().setEnabled(false);
		mainFrame.getTextAreaReceiveMessage().append("System notification : Disconnecting... \n");
		controller.processDisconnect(localUsername);
		mainFrame.getTextFieldNickname().setEditable(true);
		mainFrame.getBtnConnect().setEnabled(true);
	}

	public void sendMessage(String message) {
		controller.processSendMessage(message);
	}
	
	public void sendMessageUnicast(String message, InetAddress ip) {
		controller.processSendMessage(message, ip);
	}
	
	public void sendFile(File fileChosen){
		controller.processSendFile(fileChosen);
		JOptionPane.showMessageDialog(this.mainFrame, "Your file request has been sent.");
	}

	/**
	 * Ask for file transfert permission to remote user
	 */
	public void askForFileTransfertPermission(InetAddress remoteUserIP, String fileRequestName) {
		String message = "Do you want to accept file "+fileRequestName+" from "+remoteUserIP.getHostAddress()+" ?" ;
		String title = "File request";
		int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION)
	    {
			controller.acceptFileRequest(true, remoteUserIP, fileRequestName);
	    }
		else{
			controller.acceptFileRequest(false, remoteUserIP, fileRequestName);
		}
	}
	
	/**
	 * Show a dialog window when file request is accepted
	 */
	public void showFileRequestAcceptedDialog(String fileResponseName, InetAddress remoteUserIP) {
		JOptionPane.showMessageDialog(this.mainFrame, "Your file request is accepted, file transfert is started.");
	}

	/**
	 * Show a dialog window when file request is refused
	 */
	public void showFileRequestRefusedDialog(String fileResponseName, InetAddress remoteUserIP) {
		JOptionPane.showMessageDialog(this.mainFrame, "Your file request is refused, no file will be sent.");
	}
	
	/**
	 * Show a dialog window when file request is refused
	 */
	public void showReceivedNewFile(String filename) {
		mainFrame.getTextAreaReceiveMessage().append("System notification : You have received a file "+filename+"in your current folder \n");
	}

	/**
	 * Send a file in unicast mode
	 */
	public void sendFileUnicast(InetAddress remoteUserIP, File fileChosen) {
		controller.processSendFileUnicast(remoteUserIP,fileChosen);
		JOptionPane.showMessageDialog(this.mainFrame, "Your file request has been sent.");
	}
	
	public void setController(ChatController controller) {
		this.controller = controller;
	}
	
	public String getLocalUsername() {
		return localUsername;
	}

	public boolean isLocalUserIsConnected() {
		return localUserIsConnected;
	}

	public void setLocalUserIsConnected(boolean localUserIsConnected) {
		this.localUserIsConnected = localUserIsConnected;
	}
}