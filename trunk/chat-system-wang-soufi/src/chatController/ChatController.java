package chatController;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import chatGUI.ChatGUI;
import chatModel.ChatModel;
import chatNI.ChatNI;
import chatNI.LocalInetAddress;

public class ChatController {
	
	ChatNI chatNI ;
	ChatGUI chatGUI ;
	ChatModel chatModel ;
	
	public ChatController(ChatModel chatModel, ChatNI chatNI, ChatGUI chatGUI){
		this.chatNI = chatNI ;
		this.chatGUI = chatGUI ;
		this.chatModel = chatModel ;
	}
	
	public void processConnect(String localNickname){
		if(!chatNI.isSocketInitialized()){
			chatNI.initSocket();
		}
		chatNI.sendHello(localNickname);
	}
	
	public void processDisconnect(String localNickname){
		chatNI.closeSocket();
		chatNI.sendGoodbye(localNickname);
		chatModel.getUserlistModel().removeAllElements();
		chatModel.getUserlist().clear();
	}

	public void processHello(String remoteUserNickname, InetAddress remoteUserIP) {
		chatModel.addUser(remoteUserNickname,remoteUserIP);
		chatNI.sendHelloAck(remoteUserIP);
	}
	
	public void processGoodbye(String remoteUserNickname, InetAddress remoteUserIP) {
		chatModel.removeUser(remoteUserNickname,remoteUserIP);
	}
	
	public void processHelloAck(String remoteUserNickname, InetAddress remoteUserIP) throws UnknownHostException, SocketException {
		if(!LocalInetAddress.isOfLocalInterface(remoteUserIP.getHostAddress())&& !chatModel.isInOnlineList(remoteUserIP)){
			System.out.println("je vais ajouter user "+remoteUserNickname);
			chatModel.addUser(remoteUserNickname,remoteUserIP);
		}
	}

	public ChatModel getChatModel() {
		return chatModel;
	}
}
