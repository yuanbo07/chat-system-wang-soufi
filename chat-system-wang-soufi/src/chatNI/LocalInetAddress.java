package chatNI;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

public class LocalInetAddress {

	private static ArrayList<String> localUserInterfaces ;
	
	public LocalInetAddress() throws SocketException, UnknownHostException{
		constructLocalUserInterfaces();
	}
	
	public static void constructLocalUserInterfaces() throws SocketException, UnknownHostException{
		localUserInterfaces = new ArrayList<String>();
		localUserInterfaces.add(InetAddress.getLocalHost().getHostAddress());
		Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
		for (; n.hasMoreElements();)
		{
			NetworkInterface e = n.nextElement();
			Enumeration<InetAddress> a = e.getInetAddresses();
			for (; a.hasMoreElements();){
				InetAddress addr = a.nextElement();
				localUserInterfaces.add(addr.getHostAddress());
				}
			}
		}
	
	public static boolean isOfLocalInterface(String ip) throws SocketException, UnknownHostException{
		constructLocalUserInterfaces();
		int i ;
		boolean result = false ;
		for(i=0;i<localUserInterfaces.size();i++){
			if(ip.equals(localUserInterfaces.get(i))){
				result = true ;
			}
		}
		return result ;
	}
}