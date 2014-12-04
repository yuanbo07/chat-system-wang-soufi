package chatController;

import chatGUI.ChatGUI;
import chatModel.ChatModel;
import chatNI.ChatNI;

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
		chatNI.sendHello(localNickname);
		chatGUI.connected();
	}

	public void processHelloACK(String remoteNickname) {
		System.out.println("je suis passé dans controller");
		chatModel.addUser(remoteNickname);
	}

}
