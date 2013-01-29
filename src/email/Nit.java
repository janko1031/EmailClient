package email;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.Socket;
import java.util.GregorianCalendar;

public class Nit extends Thread {
	private static String CRLF = "\r\n";
	BufferedReader ulazniTokOdKlijenta = null;
	DataOutputStream izlazniTokKaKlijentu = null;
	Socket socketZaKom = null;

	public Nit(Socket soket) {
		socketZaKom = soket;

	}

	public void run() {

		try {
			ulazniTokOdKlijenta = new BufferedReader(new InputStreamReader(
					socketZaKom.getInputStream()));
			izlazniTokKaKlijentu = new DataOutputStream(
					socketZaKom.getOutputStream());
			GregorianCalendar datum = new GregorianCalendar();

			String dat = datum.getTime().toString();

			String unos = null;

			izlazniTokKaKlijentu.writeBytes("220 localhost" + CRLF);
			unos = ulazniTokOdKlijenta.readLine();
			String pom = unos;
			System.out.println(unos);
			boolean helo = pom.startsWith("HELO");
			if (helo) {

				izlazniTokKaKlijentu
						.writeBytes("250 Hello, pleased to meet you!" 
								+ CRLF);
			}

			unos = ulazniTokOdKlijenta.readLine();
			pom = unos;
			System.out.println(unos);
			String sender = unos.substring(unos.indexOf(":") + 2);
			boolean from = pom.startsWith("MAIL FROM");

			if (from)
				izlazniTokKaKlijentu.writeBytes("250 " + sender
						+ " ... Sender ok" + CRLF);

			unos = ulazniTokOdKlijenta.readLine();
			pom = unos;
			System.out.println(unos);
			boolean to = pom.startsWith("RCPT TO");
			String mailTo = unos.substring(unos.indexOf(":") + 2);
			if (to)
				izlazniTokKaKlijentu.writeBytes("250 " + mailTo
						+ " ... Recipient ok" + CRLF);

			unos = ulazniTokOdKlijenta.readLine();
			pom = unos;
			System.out.println(unos);
			boolean data = pom.startsWith("DATA");

			if (data) {

				izlazniTokKaKlijentu
						.writeBytes("354 Enter mail, end with \".\" on a line by itself"
								+ CRLF);

			}

			unos = ulazniTokOdKlijenta.readLine();
			System.out.println(unos);
			String tekst = "";
			boolean kraj = false;
			while (!kraj) {

				unos = ulazniTokOdKlijenta.readLine();
				System.out.println(unos);
				

				if (unos.endsWith(".")) {

					kraj = true;

				} else {

					tekst = tekst + unos + "\n";

				}
			}
			unos = ulazniTokOdKlijenta.readLine();
			System.out.println(unos);

			pom = unos;
			boolean dot = pom.equals(".");
			if (dot) {
				izlazniTokKaKlijentu
						.writeBytes("250 Message accepted for delivery" + CRLF);

			}
			unos = ulazniTokOdKlijenta.readLine();
			pom = unos;
			System.out.println(unos);
			boolean quit = pom.startsWith("QUIT");

			if (quit) {
				izlazniTokKaKlijentu
						.writeBytes("221 localhost is closing connection"
								+ CRLF);
		

			}

			socketZaKom.setReuseAddress(true);
			socketZaKom.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}
}