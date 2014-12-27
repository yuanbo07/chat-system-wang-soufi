package chatNI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;

import chatsystemTDa2.*;

/**
 * Class ThreadUDPReceiver
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class ThreadUDPReceiver extends Observable implements Runnable {

	private ObjectInputStream objectInputStream ;
	private ChatNI chatNI ;
	private Message receivedPacket;
	
	public ThreadUDPReceiver(ObjectInputStream objectInputStream, ChatNI chatNI){
		this.objectInputStream = objectInputStream ;
		this.chatNI = chatNI ;
	}

	/**
	 * When receiving a packet from network, notify chatNI
	 */
	public void run() {
		try {
			receivedPacket = (Message)objectInputStream.readObject();
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