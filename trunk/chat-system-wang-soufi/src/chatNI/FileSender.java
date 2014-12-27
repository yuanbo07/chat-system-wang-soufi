package chatNI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Class FileSender
 * Handle file sending
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class FileSender extends Thread {

	private File file ;
	private Socket socketClient ;
	private InetAddress remoteUserIP ;
	private int fileSize ;
	private BufferedInputStream bufferedInputStream ;
	private BufferedOutputStream bufferedOutputStream ;
	private OutputStream outputStream ;
	private int nbBytesRead ;
	
	public FileSender(File file, InetAddress remoteUserIP){
		this.file = file ;
		this.remoteUserIP = remoteUserIP ;
	}
	
	public void run(){
		try {
			socketClient = new Socket(remoteUserIP, Network.tcpPort);
			outputStream = socketClient.getOutputStream() ;
			fileSize = (int) file.length();
			byte[] bytes = new byte[fileSize];
			FileInputStream fileInputStream = new FileInputStream(file);
			bufferedInputStream = new BufferedInputStream(fileInputStream);
			bufferedOutputStream = new BufferedOutputStream(outputStream);
			
			while ((nbBytesRead = bufferedInputStream.read(bytes)) > 0) {
				bufferedOutputStream.write(bytes, 0, nbBytesRead);
				}
			
			bufferedOutputStream.flush();
			bufferedOutputStream.close();
			fileInputStream.close();
			bufferedInputStream.close();
			socketClient.close();
			} catch (IOException e) {
				e.printStackTrace();
				}
		}
	}