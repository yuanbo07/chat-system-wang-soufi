package chatModel;

import java.net.InetAddress;

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
