package email;

import java.io.BufferedReader;

import java.io.DataOutputStream;
import java.io.InputStreamReader;

import java.net.Socket;
import java.util.GregorianCalendar;

public class emailClient {
	

	private static String CRLF = "\r\n";
	static String unos = null;
	String mailFrom; String mailTo;
	String subject; String poruka;
	public static void posaljiMail(String mailFrom, String mailTo,
			String subject, String poruka) {
		try {

			Socket s = new Socket("localhost",2555);

			BufferedReader ulazniTokOdServera = new BufferedReader(
					new InputStreamReader(s.getInputStream()));
			DataOutputStream izlazniTokKaServeru = new DataOutputStream(
					s.getOutputStream());
	
			
			GregorianCalendar datum = new GregorianCalendar();
			String dat = datum.getTime().toString();

			unos = ulazniTokOdServera.readLine();
			System.out.println(unos);

			if (unos.startsWith("220"))

				izlazniTokKaServeru.writeBytes("HELO "  + CRLF);

			izlazniTokKaServeru.flush();

			unos = ulazniTokOdServera.readLine();
			System.out.println(unos);
			if (unos.startsWith("250"))
				izlazniTokKaServeru.writeBytes("MAIL FROM: " + mailFrom +CRLF);
			
			izlazniTokKaServeru.flush();

			unos = ulazniTokOdServera.readLine();
			System.out.println(unos);
			if (unos.startsWith("250"))
				izlazniTokKaServeru.writeBytes("RCPT TO: " + mailTo + CRLF);
			izlazniTokKaServeru.flush();

			unos = ulazniTokOdServera.readLine();
			System.out.println(unos);
			if (unos.startsWith("250"))
				izlazniTokKaServeru.writeBytes("DATA"+CRLF);
			izlazniTokKaServeru.flush();

			unos = ulazniTokOdServera.readLine();
			System.out.println(unos);
			if (unos.startsWith("354")) {
				izlazniTokKaServeru.writeBytes("Subject: " + subject + CRLF);
				
				izlazniTokKaServeru.flush();
				izlazniTokKaServeru.writeBytes("Message: "+ dat+"\n"+poruka+CRLF);
				
				izlazniTokKaServeru.flush();
				izlazniTokKaServeru.writeBytes("."+CRLF);
			}
			izlazniTokKaServeru.flush();

			unos = ulazniTokOdServera.readLine();
		
			System.out.println(unos);
			if (unos.startsWith("250"))
				izlazniTokKaServeru.writeBytes("QUIT "+ CRLF);
			izlazniTokKaServeru.flush();
			unos = ulazniTokOdServera.readLine();
			System.out.println(unos);

			s.close();
		} catch (Exception e) {

		}
	}

}


