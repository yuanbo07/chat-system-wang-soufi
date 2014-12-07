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
import user.FromUser;
import user.ToUser;

public class ChatGUI extends Thread implements FromUser, ToUser, ActionListener, WindowListener, Observer {
	
	private MainFrame mainFrame ;
	private ChatController controller ;
	private String localUsername ;
	private ChatModel model ;
	private User lastUser ;
	private String receivedMessageUsername ;
	private boolean localUserIsConnected = false ;
	private boolean localUsernameAvailable = false ;
	
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

	public void connected(){
		mainFrame.getTextAreaReceiveMessage().append("System notification : You have successfully connected. \n");
		localUserIsConnected = true ;
	}

	public void disconnected(){
		mainFrame.getTextAreaReceiveMessage().append("System notification : You have successfully disconnected. \n");
		localUserIsConnected = false ;
	}
	
	public void setController(ChatController controller) {
		this.controller = controller;
	}
	
	public void addUserToUserlist(String remoteNickname){
		int nbUser = model.getUserlist().size();
		lastUser = model.getUserlist().get(nbUser-1);
		String lastUserName = lastUser.getNickname();
		mainFrame.getTextAreaReceiveMessage().append("System notification : "+lastUserName+" is connected. \n");
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==mainFrame.getBtnConnect()){
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
		
		if(e.getSource()==mainFrame.getBtnDisconnect()){
			mainFrame.getBtnDisconnect().setEnabled(false);
			mainFrame.getTextAreaReceiveMessage().append("System notification : Disconnecting... \n");
			controller.processDisconnect(localUsername);
			mainFrame.getTextFieldNickname().setEditable(true);
			mainFrame.getBtnConnect().setEnabled(true);
		}
		
		if (e.getSource()==mainFrame.getBtnSendMessage()){
			if(localUserIsConnected){
				String message = mainFrame.getTextAreaSendMessage().getText();
				SendMessage(message);
				mainFrame.getTextAreaSendMessage().setText(null);
			}
			else{
				mainFrame.getTextAreaReceiveMessage().append("Error : To send a message, please connect first. \n");
			}
		}
		
		if (e.getSource()==mainFrame.getBtnSendFile()){
			if(localUserIsConnected){
				ThreadFileChooser fileChooser = new ThreadFileChooser(this);
				fileChooser.start();
			}
			else{
				mainFrame.getTextAreaReceiveMessage().append("Error : To send a file, please connect first. \n");
			}
		}
	}
	
	public void updateUserlist(){
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
		
	}
	
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
	
	public void displaySentMessage(String sentMessage){
		mainFrame.getTextAreaReceiveMessage().append(localUsername+"[Localhost]" + " said to everyone : "+sentMessage +"\n");
	}
	
	public void displaySentPrivateMessage(String sentMessage, String username){
		mainFrame.getTextAreaReceiveMessage().append(localUsername+"[Localhost]" + " said to "+username+" : "+sentMessage +"\n");
	}
	
	public void update(Observable observable, Object object) {
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

	@Override
	public void windowActivated(WindowEvent e) {	
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(localUserIsConnected){
			controller.processDisconnect(localUsername);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent e) {	
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {	
	}

	@Override
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

	public void Connect() {
	}

	public void Disconnect() {
	}

	public void SendMessage(String message) {
		controller.processSendMessage(message);
	}
	
	public void SendMessageUnicast(String message, InetAddress ip) {
		controller.processSendMessage(message, ip);
	}
	
	public void SendFile(File fileChosen){
		controller.processSendFile(fileChosen);
		JOptionPane.showMessageDialog(this.mainFrame, "Your file request has been sent.");
	}

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
	
	public String getLocalUsername() {
		return localUsername;
	}

	public boolean isLocalUserIsConnected() {
		return localUserIsConnected;
	}

	public void setLocalUserIsConnected(boolean localUserIsConnected) {
		this.localUserIsConnected = localUserIsConnected;
	}

	public void showFileRequestAcceptedDialog(String fileResponseName, InetAddress remoteUserIP) {
		JOptionPane.showMessageDialog(this.mainFrame, "Your file request is accepted, file transfert is started.");
	}

	public void showFileRequestRefusedDialog(String fileResponseName, InetAddress remoteUserIP) {
		JOptionPane.showMessageDialog(this.mainFrame, "Your file request is refused, no file will be sent.");
	}
}