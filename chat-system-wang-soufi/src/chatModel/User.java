package chatModel;

import java.net.InetAddress;

/**
 * Class User
 * The class defining a user in the chat system by his nickname and IP address
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class User {
	
	private String nickname ;
	private InetAddress ip ;
	
	public User(String nickname,InetAddress ip){
		this.nickname = nickname;
		this.ip = ip ;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public InetAddress getIp() {
		return ip;
	}

	public void setIp(InetAddress ip) {
		this.ip = ip;
	}
}
