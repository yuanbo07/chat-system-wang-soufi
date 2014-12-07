package chatGUI;

import java.io.File;
import javax.swing.JFileChooser;

public class ThreadFileChooser extends Thread {
	
	private ChatGUI chatGUI ;
	
	public ThreadFileChooser(ChatGUI chatGUI){
		this.chatGUI = chatGUI ;
	}
	
	public void run(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Choose a file to send");
		int returnVal = fileChooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {	
			File fileChosen = fileChooser.getSelectedFile() ;
			chatGUI.SendFile(fileChosen);
		}
		fileChooser.setVisible(true);
	}
}