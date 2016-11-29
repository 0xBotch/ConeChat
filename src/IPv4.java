import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class IPv4 {

	private static String ipAddress;
	private String name;
	
	public IPv4() throws UnknownHostException {
		// TODO Auto-generated constructor stub
		ipAddress = Inet4Address.getLocalHost().getHostAddress();
	}
	
	public static String getIP(){
		return ipAddress;
	}

	public static void main(String[] args) {
		try {
			IPv4 iPv4 = new IPv4();
			JOptionPane.showMessageDialog (null, "IPv4: " + IPv4.getIP());
			System.out.println(InetAddress.getLocalHost().getHostAddress().toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
