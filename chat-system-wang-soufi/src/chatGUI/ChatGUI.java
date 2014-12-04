package chatGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import chatController.ChatController;
import chatModel.ChatModel;
import user.FromUser;
import user.ToUser;

public class ChatGUI extends Thread implements 
	FromUser, ToUser, ActionListener, Observer {
	
	private MainFrame mainFrame ;
	private ChatController controller ;
	private String localUsername ;
	private ChatModel model ;
	
	public ChatGUI(ChatModel model){
		mainFrame = new MainFrame();
		mainFrame.getBtnConnect().addActionListener(this);
		mainFrame.getBtnDisconnect().addActionListener(this);
		this.model = model ;
		model.addObserver(this);
	}

	public void actionPerformed(ActionEvent e){
		
		// si on clique sur connect
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
			mainFrame.getTextFieldNickname().setEditable(true);
		}
	}
	
	public void connected(){
		mainFrame.getTextAreaReceiveMessage().append("You["+localUsername+"] are connected.\n");
	}

	public void setController(ChatController controller) {
		this.controller = controller;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		mainFrame.getTextAreaReceiveMessage().append(model.getUserlist().get(0).getNickname()+"is connected. \n");
	}
}