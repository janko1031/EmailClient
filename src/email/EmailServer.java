package email;

import java.net.*;

public class EmailServer {

	public static void main(String args[]) {
		int port = 2555;
		Socket Soket = null;
		try {
			ServerSocket sc = new ServerSocket(port);
			while (true) {
				Soket = sc.accept();
				new Nit(Soket).start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
