package chatGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import chatController.ChatController;
import chatModel.ChatModel;
import chatModel.User;
import user.FromUser;
import user.ToUser;

public class ChatGUI extends Thread implements FromUser, ToUser, ActionListener, Observer {
	
	private MainFrame mainFrame ;
	private ChatController controller ;
	private String localUsername ;
	private ChatModel model ;
	private User lastUser ;
	
	public ChatGUI(ChatModel model){
		mainFrame = new MainFrame();
		mainFrame.getBtnConnect().addActionListener(this);
		mainFrame.getBtnDisconnect().addActionListener(this);
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
		this.model = model ;
		model.addObserver(this);
		this.start();
	}

	public void connected(){
		
	}

	public void disconnected(){
		
	}
	
	public void setController(ChatController controller) {
		this.controller = controller;
	}
	
	public void addUserToUserlist(String remoteNickname){
		int nbUser = model.getUserlist().size();
		lastUser = model.getUserlist().get(nbUser-1);
		String lastUserName = lastUser.getNickname();
		mainFrame.getTextAreaReceiveMessage().append(lastUserName+" is connected.\n");
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==mainFrame.getBtnConnect()){
			if(!mainFrame.getTextFieldNickname().getText().equals("")){
				localUsername = mainFrame.getTextFieldNickname().getText() ;
				mainFrame.getTextFieldNickname().setEditable(false);
				mainFrame.getTextAreaReceiveMessage().append("Connect...\n");
				controller.processConnect(localUsername);
			}
			else{
				mainFrame.getTextAreaReceiveMessage().append("Error : please enter a valid username.\n");
			}
		}
		
		if(e.getSource()==mainFrame.getBtnDisconnect()){
			controller.processDisconnect(localUsername);
			mainFrame.getTextAreaReceiveMessage().append("You are disconnected. \n");
			mainFrame.getTextFieldNickname().setEditable(true);
		}
		
		
		if (e.getSource()==mainFrame.getBtnSendMessage()){
			
		}
	}
	
	public void updateUserlist(){
		mainFrame.getOnlineUserlist().setModel(model.getUserlistModel());
		
	}
	
	public void update(Observable arg0, Object o) {
		if(o instanceof User){
			User user = (User)o;
			addUserToUserlist(user.getNickname());
		}
		if(o instanceof String){
			mainFrame.getTextAreaReceiveMessage().append(o+" is disconnected.\n");
		}
		updateUserlist();
	}
}