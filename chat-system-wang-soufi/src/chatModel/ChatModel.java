package chatModel;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Observable;
import javax.swing.DefaultListModel;

/**
 * Class ChatModel
 * The "Model" in the design pattern "MVC"
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class ChatModel extends Observable {
	
	private ArrayList<User> userlist = new ArrayList<User>();
	@SuppressWarnings("rawtypes")
	private DefaultListModel userlistModel = new DefaultListModel();
	private String localUsername ;
	
	public ChatModel(){
	}
	
	/**
	 * Add a user to userlist and notify observer
	 */
	@SuppressWarnings("unchecked")
	public void addUser(String remoteUserNickname, InetAddress remoteUserIP){
		User newUser = new User(remoteUserNickname, remoteUserIP);
		this.userlist.add(newUser);
		userlistModel.addElement(newUser.getNickname()+"["+remoteUserIP.getHostAddress()+"]");
		setChanged();
		notifyObservers(newUser);
		clearChanged();
	}
	
	/**
	 * Add local user to the user list
	 */
	@SuppressWarnings("unchecked")
	public void addLocalUser(String localUserNickname){
		userlistModel.addElement(localUserNickname+"[Localhost]");
		setChanged();
		notifyObservers(localUserNickname);
		clearChanged();
	}
	
	/**
	 * Remove user from user list and notify observer
	 */
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
	
	/**
	 * Determine if a user is in the user list
	 */
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

	@SuppressWarnings("rawtypes")
	public DefaultListModel getUserlistModel() {
		return userlistModel;
	}

	@SuppressWarnings("rawtypes")
	public void setUserlistModel(DefaultListModel userlistModel) {
		this.userlistModel = userlistModel;
	}

	public String getLocalUsername() {
		return localUsername;
	}

	public void setLocalUsername(String localUsername) {
		this.localUsername = localUsername;
	}
}