package dodatno;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JTextField;

import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveAs {

	public void saveAs(JTextArea text, JTextField sender, JTextField to,
			JTextField subject, JTextArea pom ) {

		
		String recipientAd;
		String tekst ;
		
		String red = ";\n";
		String[] recipients = to.getText().split(" ");
		for (int i=0; i<recipients.length;i++){
			recipientAd = recipients [i];
		
		tekst = sender.getText() + red + recipientAd + red + subject.getText()
				+ red + text.getText();
	
		pom.setText(tekst);
		JFileChooser fileC = new JFileChooser();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			 "Text files", ".txt");
		fileC.setFileFilter(filter);
		int ret = fileC.showSaveDialog(fileC);
		if (ret == javax.swing.JFileChooser.APPROVE_OPTION) {
			java.io.File sFile = fileC.getSelectedFile();

			FileWriter writer = null;
			try {
				writer = new FileWriter(sFile);
				pom.write(writer);

				
			} catch (IOException ex) {

			} finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException x) {
					}
				}
			}
		}
		}
		}
	

	

}
