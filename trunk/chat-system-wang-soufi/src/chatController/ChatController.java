package chatController;

import java.io.File;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import chatGUI.ChatGUI;
import chatModel.ChatModel;
import chatNI.ChatNI;
import chatNI.Network;

/**
 * Class ChatController
 * The "controller" in the design pattern "MVC"
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */

public class ChatController {

	ChatNI chatNI ;
	ChatGUI chatGUI ;
	ChatModel chatModel ;
	
	/**
	 * Constructor
	 */
	public ChatController(ChatModel chatModel, ChatNI chatNI, ChatGUI chatGUI){
		this.chatNI = chatNI ;
		this.chatGUI = chatGUI ;
		this.chatModel = chatModel ;
	}
	
	/**
	 * Process connection
	 */
	public void processConnect(String localNickname){
		if(!chatNI.isSocketInitialized()){
			chatNI.initSocket();
		}
		chatNI.sendHello(localNickname);
		chatModel.addLocalUser(chatModel.getLocalUsername());
		chatGUI.connected();
	}
	
	/**
	 * Process disconnection
	 */
	public void processDisconnect(String localNickname){
		chatNI.closeSocket();
		chatNI.sendGoodbye(localNickname);
		chatModel.getUserlistModel().removeAllElements();
		chatModel.getUserlist().clear();
		chatGUI.disconnected();
	}

	/**
	 * Process when received a Hello message
	 */
	public void processHello(String remoteUserNickname, InetAddress remoteUserIP) {
		chatModel.addUser(remoteUserNickname,remoteUserIP);
		chatNI.sendHelloAck(remoteUserIP);
	}
	
	/**
	 * Process sending messages
	 */
	public void processSendMessage(String sendMessage){
		chatGUI.displaySentMessage(sendMessage);
		chatNI.sendMessage(sendMessage);
	}
	
	/**
	 * Process receiving messages
	 */
	public void processReceive(InetAddress remoteUserIP, int receivedID, String receivedMessage){
		chatGUI.displayMessage(remoteUserIP,receivedMessage);
	}
	
	/**
	 * Process when received a Goodbye message
	 */
	public void processGoodbye(String remoteUserNickname, InetAddress remoteUserIP) throws SocketException, UnknownHostException {
		if(!Network.isOfLocalInterface(remoteUserIP.getHostAddress())){
			chatModel.removeUser(remoteUserNickname,remoteUserIP);
		}
	}
	
	/**
	 * Process when received a HelloAck message
	 */
	public void processHelloAck(String remoteUserNickname, InetAddress remoteUserIP) throws UnknownHostException, SocketException {
		if(!Network.isOfLocalInterface(remoteUserIP.getHostAddress())&& !chatModel.isInOnlineList(remoteUserIP)){
			chatModel.addUser(remoteUserNickname,remoteUserIP);
		}
	}
	
	/**
	 * Process when received a SendAck message
	 */
	public void processSendAck(InetAddress remoteUserIP, int receivedID) {
		// on a fait le choix de ne pas l'implémenter
	}

	/**
	 * Process sending messages
	 */
	public void processSendMessage(String message, InetAddress ip) {
		chatNI.sendMessage(message, ip);
	}

	/**
	 * Process sending files
	 */
	public void processSendFile(File fileChosen) {
		chatNI.getPendingFilesToSend().add(fileChosen);
		chatNI.sendFileRequest(fileChosen.getName());
	}

	/**
	 * Process when received a FileRequest message
	 */
	public void processFileRequest(InetAddress remoteUserIP, String fileRequestName){
		chatGUI.askForFileTransfertPermission(remoteUserIP, fileRequestName);
	}

	/**
	 * Process when received a FileResponse message
	 */
	public void processFileResponse(boolean response, InetAddress remoteUserIP, String fileResponseName) {
		if(response == true){
			chatGUI.showFileRequestAcceptedDialog(fileResponseName,remoteUserIP);
			chatNI.sendFile(fileResponseName,remoteUserIP);
		}
		else {
			chatGUI.showFileRequestRefusedDialog(fileResponseName,remoteUserIP);
		}
	}

	/**
	 * Process file transfert in unicast mode
	 */
	public void processSendFileUnicast(InetAddress remoteUserIP, File fileChosen) {
		chatNI.getPendingFilesToSend().add(fileChosen);
		chatNI.sendFileRequestUnicast(remoteUserIP, fileChosen.getName());
	}
	
	/**
	 * Process a request of file transfert according to user's response
	 */
	public void acceptFileRequest(boolean response, InetAddress remoteUserIP, String fileRequestName) {
		if(response == true){
			chatNI.sendFileResponse(true, remoteUserIP, fileRequestName);
			chatNI.initFileReceiveSocket(fileRequestName);
		}
		else{
			chatNI.sendFileResponse(false, remoteUserIP, fileRequestName);
		}
	}
	
	public void processReceivedNewFile(String filename) {
		chatGUI.showReceivedNewFile(filename);
	}
	
	/**
	 * Getter of chatModel
	 */
	public ChatModel getChatModel() {
		return chatModel;
	}


}