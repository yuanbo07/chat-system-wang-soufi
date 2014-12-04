package chatModel;

import java.util.ArrayList;
import java.util.Observable;

public class ChatModel extends Observable {
	
	private ArrayList<User> userlist = new ArrayList<User>();
	
	public ChatModel(){
	}
	
	public void addUser(String remoteNickname){
		System.out.println("je suis passé dans add user et le remote Nickname is"+remoteNickname);
		User newUser = new User(remoteNickname);
		this.userlist.add(newUser);
		setChanged();
		notifyObservers();
	}
	
	public ArrayList<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(ArrayList<User> userlist) {
		this.userlist = userlist;
	}
	

}