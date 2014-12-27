package main;

import java.io.IOException;
import chatController.ChatController;
import chatGUI.ChatGUI;
import chatModel.ChatModel;
import chatNI.ChatNI;

/**
 * Class ChatSystem
 * Main class of chat system using MVC design pattern
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class ChatSystem {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		ChatModel model = new ChatModel();
		ChatNI ni = new ChatNI();
		ChatGUI gui =new ChatGUI(model);
		
		ChatController controller = new ChatController(model,ni,gui);
		gui.setController(controller);
		ni.setController(controller);
	}
}