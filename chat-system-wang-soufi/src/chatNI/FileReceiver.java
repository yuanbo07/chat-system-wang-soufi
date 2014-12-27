package chatNI;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

/**
 * Class FileReceiver
 * Handle file receiving and perform file saving to local disk
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class FileReceiver extends Observable implements Runnable {

	private ServerSocket serverSocket ;
	private Socket clientSocket ;
	private FileOutputStream fileOutputStream ;
	private InputStream inputStream = null ;
	private BufferedInputStream bufferedInputStream = null ;
	private String filename ;
	private int receiveBufferSize = 0 ;
	private int nbBytesRead = 0 ;
	
	public FileReceiver(String filename){
		this.filename = filename ;
	}
	
	public void run(){
		try {
			serverSocket = new ServerSocket(Network.tcpPort);
			clientSocket = serverSocket.accept();
		    receiveBufferSize = clientSocket.getReceiveBufferSize();
		    inputStream = clientSocket.getInputStream();
		    bufferedInputStream = new BufferedInputStream(inputStream);
		    fileOutputStream = new FileOutputStream(filename);
		    byte[] buffer = new byte[receiveBufferSize];
		    
		    // if the number of octets read is not null
		    while ((nbBytesRead = bufferedInputStream.read(buffer)) > 0){
		    	fileOutputStream.write(buffer, 0, nbBytesRead);
		    	}
		    // inform observer of received file
			setChanged();
			notifyObservers(filename);
			clearChanged();
		    fileOutputStream.close();
		    bufferedInputStream.close();
		    inputStream.close();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	}
		}
}