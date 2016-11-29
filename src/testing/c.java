package testing;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class c {

	Socket socket = null;
	
	public c() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 2000);
	}
	
}
