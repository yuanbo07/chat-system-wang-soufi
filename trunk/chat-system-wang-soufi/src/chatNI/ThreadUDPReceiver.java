package chatNI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;

import chatsystemTDa2.*;

public class ThreadUDPReceiver extends Observable implements Runnable {

	private ObjectInputStream in ;
	private ChatNI chatNI ;
	private Message receivedPacket;
	
	public ThreadUDPReceiver(ObjectInputStream in, ChatNI chatNI){
		this.in = in ;
		this.chatNI = chatNI ;
	}

	public void run() {
		try {
			receivedPacket = (Message)in.readObject();
			setChanged();
			notifyObservers(receivedPacket);
			clearChanged();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ChatNI getChatNI() {
		return chatNI;
	}
}