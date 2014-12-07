package chatModel;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.DefaultListModel;

public class ChatModel extends Observable {
	
	private ArrayList<User> userlist = new ArrayList<User>();
	private DefaultListModel<String> userlistModel = new DefaultListModel<String>();
	private String localUsername ;
	
	public ChatModel(){
	}
	
	public void addUser(String remoteUserNickname, InetAddress remoteUserIP){
		User newUser = new User(remoteUserNickname, remoteUserIP);
		this.userlist.add(newUser);
		userlistModel.addElement(newUser.getNickname()+"["+remoteUserIP.getHostAddress()+"]");
		setChanged();
		notifyObservers(newUser);
		clearChanged();
	}
	
	public void addLocalUser(String localUserNickname){
		userlistModel.addElement(localUserNickname+"[Localhost]");
		setChanged();
		notifyObservers(localUserNickname);
		clearChanged();
	}
	
	public void removeUser(String remoteUserNickname, InetAddress remoteUserIP){
		int i ;
		for(i=0;i<userlist.size();i++){
			if(userlist.get(i).getIp().equals(remoteUserIP))
			{
				this.userlist.remove(i);
			}
		}
		for(i=0;i<userlistModel.size();i++){
			if(userlistModel.getElementAt(i).equals(remoteUserNickname+"["+remoteUserIP.getHostAddress()+"]")){
				userlistModel.remove(i);
			}
		}
		setChanged();
		notifyObservers(remoteUserNickname);
		clearChanged();
	}
	
	public boolean isInOnlineList(InetAddress remoteUserIP){
		int i ;
		boolean result = false ;
		for(i=0;i<userlist.size();i++){
			if(userlist.get(i).getIp().equals(remoteUserIP))
			{
				result = true ;
			}
		}
		return result ;
	}
	
	public ArrayList<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(ArrayList<User> userlist) {
		this.userlist = userlist;
		setChanged();
		notifyObservers();
		clearChanged();
	}

	public DefaultListModel<String> getUserlistModel() {
		return userlistModel;
	}

	public void setUserlistModel(DefaultListModel<String> userlistModel) {
		this.userlistModel = userlistModel;
	}

	public String getLocalUsername() {
		return localUsername;
	}

	public void setLocalUsername(String localUsername) {
		this.localUsername = localUsername;
	}
}