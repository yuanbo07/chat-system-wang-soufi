package chatNI;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Class Network
 * Class contains static methods & variables concerning local network interfaces and the choice of ports
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class Network {

	private static ArrayList<String> localUserInterfaces ;
	public static int udpSenderPort = 16050 ;
	public static int udpReceiverPort = 5003 ;
	public static int tcpPort = 5003 ;
	public static int bufferSize = 10000 ;
	public static String broadcastAddress = "255.255.255.255" ;
	
	public Network() throws SocketException, UnknownHostException{
		constructLocalUserInterfaces();
	}
	
	/**
	 * Static method used to determine all local interfaces
	 */
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
	
	/**
	 * Detect if the given IP is one of local interfaces
	 * @param ip
	 * @throws SocketException
	 * @throws UnknownHostException
	 */
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