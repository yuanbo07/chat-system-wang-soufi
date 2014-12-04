package remoteApplication;

import java.io.Serializable;

public class Message implements Serializable {
	
	private String nickname ;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Message(String nickname){
		this.nickname = nickname ;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}