package chatNI;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class FileReceiver extends Thread {

	private File file ;
	private ServerSocket serverSocket ;
	private Socket clientSocket ;
	private InputStream is ;
	private FileOutputStream fos ;
	private BufferedOutputStream bos ;
	
	public FileReceiver(File file){
		this.file = file ;
	}
	
	public void run(){
	    try {
			serverSocket = new ServerSocket(5004);
		    clientSocket = serverSocket.accept();
		    
		    is = clientSocket.getInputStream();	
			int bufferSize = clientSocket.getReceiveBufferSize();
			System.out.println("Buffer size: " + bufferSize);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
	        
			byte[] bytes = new byte[bufferSize];
		    int count;
		    while ((count = is.read(bytes)) > 0){
		        bos.write(bytes, 0, count);
		    }
		    
		    bos.flush();
		    bos.close();
		    is.close();
		    clientSocket.close();
		    serverSocket.close();
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}