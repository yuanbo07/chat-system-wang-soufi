package chatGUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.JList;

/**
 * Class UserListMouseAdapter
 * Mouse adapter which enables a user to choose other users from a user list
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class UserListMouseAdapter extends MouseAdapter implements WindowListener {
	
	private ArrayList<String> processingPrivateChatList = new ArrayList<String>() ;
	private boolean chatAlreadyStarted =false ;
	private String chosenUser ;
	private String username ;
	private String ip ;
	private ChatGUI chatGUI ;
	
    public UserListMouseAdapter(ChatGUI chatGUI) {
		this.chatGUI = chatGUI ;
	}

	@SuppressWarnings("rawtypes")
	public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && chatGUI.isLocalUserIsConnected()) {
        	chosenUser = ((JList)e.getSource()).getSelectedValue().toString();
    		String[] firstParser = chosenUser.split("\\[");	
    		username = firstParser[0];
    		String[] secondParser = firstParser[1].split("\\]");
    		ip = secondParser[0];
    		
        	if(username.equals(chatGUI.getLocalUsername())){
        		chatGUI.getMainFrame().getTextAreaReceiveMessage().append("System notification : "
        				+ "You cannot start a private chat with yourself. \n");
        	}
        	else {
        		
        	if(!processingPrivateChatList.isEmpty()){
        		int i ;
        		for(i=0;i<processingPrivateChatList.size();i++){
        			if(processingPrivateChatList.get(i).equals(ip)){
        				chatAlreadyStarted = true ;
        			}
        		}
        	}
        	// if the chat is already begun, we won't need to start a new window
        	if(!chatAlreadyStarted){
        		processingPrivateChatList.add(ip);
    	    	ThreadPrivateChat newPrivateChat = new ThreadPrivateChat(username,ip, chatGUI, this);
    	    	newPrivateChat.start();
    	    	}
        	}
        }
    }

	public void windowClosing(WindowEvent e) {
		int i ;
		for(i=0;i<processingPrivateChatList.size();i++){
			if(processingPrivateChatList.get(i).equals(ip)){
					processingPrivateChatList.remove(i);
			}
		}
	}
	
	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}
}