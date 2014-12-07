package chatController;

import java.io.File;
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
		chatModel.addLocalUser(chatModel.getLocalUsername());
		chatGUI.connected();
	}
	
	public void processDisconnect(String localNickname){
		chatNI.closeSocket();
		chatNI.sendGoodbye(localNickname);
		chatModel.getUserlistModel().removeAllElements();
		chatModel.getUserlist().clear();
		chatGUI.disconnected();
	}

	public void processHello(String remoteUserNickname, InetAddress remoteUserIP) {
		chatModel.addUser(remoteUserNickname,remoteUserIP);
		chatNI.sendHelloAck(remoteUserIP);
	}
	
	public void processSendMessage(String sendMessage){
		chatGUI.displaySentMessage(sendMessage);
		chatNI.sendMessage(sendMessage);
	}
	
	public void processReceive(InetAddress remoteUserIP, int receivedID, String receivedMessage){
		chatGUI.displayMessage(remoteUserIP,receivedMessage);
	}
	
	public void processGoodbye(String remoteUserNickname, InetAddress remoteUserIP) throws SocketException, UnknownHostException {
		if(!LocalInetAddress.isOfLocalInterface(remoteUserIP.getHostAddress())){
			chatModel.removeUser(remoteUserNickname,remoteUserIP);
		}
	}
	
	public void processHelloAck(String remoteUserNickname, InetAddress remoteUserIP) throws UnknownHostException, SocketException {
		if(!LocalInetAddress.isOfLocalInterface(remoteUserIP.getHostAddress())&& !chatModel.isInOnlineList(remoteUserIP)){
			chatModel.addUser(remoteUserNickname,remoteUserIP);
		}
	}

	public ChatModel getChatModel() {
		return chatModel;
	}

	public void processSendMessage(String message, InetAddress ip) {
		chatNI.sendMessage(message, ip);
	}

	public void processSendFile(File fileChosen) {
		chatNI.getPendingFilesToSend().add(fileChosen);
		chatNI.sendFileRequest(fileChosen.getName());
	}

	public void processFileRequest(InetAddress remoteUserIP, String fileRequestName){
		chatGUI.askForFileTransfertPermission(remoteUserIP, fileRequestName);
	}

	public void acceptFileRequest(boolean response, InetAddress remoteUserIP, String fileRequestName) {
		if(response == true){
			chatNI.sendFileResponse(true, remoteUserIP, fileRequestName);
			chatNI.initFileReceiveSocket();
		}
		else{
			chatNI.sendFileResponse(false, remoteUserIP, fileRequestName);
		}
	}

	public void processFileResponse(boolean response, InetAddress remoteUserIP, String fileResponseName) {
		if(response == true){
			chatGUI.showFileRequestAcceptedDialog(fileResponseName,remoteUserIP);
			chatNI.sendFile(fileResponseName,remoteUserIP);
		}
		else {
			chatGUI.showFileRequestRefusedDialog(fileResponseName,remoteUserIP);
		}
	}
}