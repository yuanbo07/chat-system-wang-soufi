package main;

import java.io.IOException;
import chatController.ChatController;
import chatGUI.ChatGUI;
import chatModel.ChatModel;
import chatNI.ChatNI;

public class ChatSystem {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		ChatModel model = new ChatModel();
		ChatNI ni = new ChatNI();
		ChatGUI gui =new ChatGUI(model);
		gui.start();
		
		ChatController controller = new ChatController(model,ni,gui);
		gui.setController(controller);
		ni.setController(controller);
	}
}