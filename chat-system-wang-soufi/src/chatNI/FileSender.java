package chatNI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class FileSender extends Thread {

	private File file ;
	private InetAddress dest ;
	private Socket socket ;
	private InputStream inputStream ;
	private OutputStream outputStream ;
	
	public FileSender(File file, InetAddress dest){
		this.file = file ;
		this.dest = dest ;
	}
	
	public void Run() throws IOException{
		socket = new Socket(dest,5003);
		inputStream = new FileInputStream(file);
		outputStream = socket.getOutputStream();
		long fileLength = file.length();
		byte[] bytes = new byte[(int) fileLength];
		int count ;
		BufferedInputStream in = new BufferedInputStream(inputStream);
		BufferedOutputStream out = new BufferedOutputStream(outputStream);
	    while ((count = in.read(bytes)) > 0) {
	        out.write(bytes, 0, count);
	    }
	    
	    in.close();
	    out.flush();
	    out.close();
	    inputStream.close();
	    socket.close();
	}
}
