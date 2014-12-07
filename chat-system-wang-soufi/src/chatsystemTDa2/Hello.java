package chatsystemTDa2;

public class Hello extends Message
{
	private static final long serialVersionUID = 2L;

	private String nickname;

	public String getNickname(){
		return this.nickname;
	}

	public void setNickname(String nickname){
		this.nickname = nickname;
	}

	public Hello(String nickname) {
		this.nickname = nickname;
	}

	public String toString() {
		return "Hello [nickname=" + nickname + "]";
	}
}