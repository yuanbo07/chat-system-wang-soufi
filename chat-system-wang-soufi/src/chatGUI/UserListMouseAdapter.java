package chatGUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JList;

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

	@SuppressWarnings("unchecked")
	public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && chatGUI.isLocalUserIsConnected()) {
        	chosenUser = ((JList<String>)e.getSource()).getSelectedValue().toString();
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
        	if(!chatAlreadyStarted){
        		processingPrivateChatList.add(ip);
    	    	ThreadPrivateChat newPrivateChat = new ThreadPrivateChat(username,ip, chatGUI, this);
    	    	newPrivateChat.start();
    	    	}
        	}
            }
        }

	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		int i ;
		for(i=0;i<processingPrivateChatList.size();i++){
			if(processingPrivateChatList.get(i).equals(ip)){
					processingPrivateChatList.remove(i);
			}
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
}