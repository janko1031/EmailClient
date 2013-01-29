package email;


import java.awt.EventQueue;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JDialog;
import javax.swing.JOptionPane;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JSeparator;

import dodatno.*;

import java.awt.print.PrinterJob;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JScrollPane;

public class NewGui extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	JTextField from;
	JTextField to;
	JTextField subject;

	String sub;
	String mailTo;
	String mailFrom;
	String Text;
	final JTextArea area;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewGui frame = new NewGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewGui() {
		setResizable(false);

		final JTextArea textArea = new JTextArea();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 747, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, Color.LIGHT_GRAY,
				null));
		panel.setBounds(0, 11, 721, 136);
		contentPane.add(panel);
		panel.setLayout(null);

		from = new JTextField();
		from.setBounds(57, 10, 340, 20);
		panel.add(from);
		from.setColumns(10);

		to = new JTextField();
		to.setBounds(57, 42, 340, 20);
		panel.add(to);
		to.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(406, 10, 279, 104);
		panel.add(panel_2);

		JMenuBar menuBar = new JMenuBar();
		panel_2.add(menuBar);

		JMenu mnNewMenu = new JMenu("Message");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMessage = new JMenuItem("New");
		mntmNewMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane pane = new JOptionPane(
						"Save your document before starting new one?");
				Object[] options = new String[] { "Yes", "No" };
				pane.setOptions(options);
				JDialog dialog = pane.createDialog(new JFrame(),
						"Save your document");
				dialog.show();
				Object obj = pane.getValue();
				if (obj.toString().equals("Yes")) {

					SaveAs save = new SaveAs();
					save.saveAs(textArea, from, to, subject, area);
					textArea.setText(null);

				} else {
					from.setText(null);
					to.setText(null);
					subject.setText(null);
					textArea.setText(null);
					textArea.setText(null);

				}

			}
		});
		mnNewMenu.add(mntmNewMessage);

		JMenuItem mntmSaveMessage = new JMenuItem("Save ");
		mntmSaveMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SaveAs s = new SaveAs();
				s.saveAs(textArea, from, to, subject, area);
			}

		});
		mnNewMenu.add(mntmSaveMessage);

		JSeparator separator = new JSeparator();
		mnNewMenu.add(separator);

		JMenuItem mntmSendMessage = new JMenuItem("Send ");
		mntmSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] adresa = to.getText().split(" ");
				for (int i = 0; i < adresa.length; i++) {
					String adresaPrimaoca = adresa[i];
					emailClient.posaljiMail(from.getText(), adresaPrimaoca,
							subject.getText(), textArea.getText());
				

				}
				GregorianCalendar datum = new GregorianCalendar();
				String dat = datum.getTime().toString();
				String	teskt=textArea.getText();
				textArea.setText(teskt+"\n"+dat+"."	);
			}
		});

		JMenuItem mntmOpenMessage = new JMenuItem("Open Message");
		mntmOpenMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpenMessage msg = new OpenMessage();
				String d = msg.openMsg(textArea);

				String[] linije = d.split(";");
				from.setText(linije[0]);
				to.setText(linije[1]);
				subject.setText(linije[2]);
				textArea.setText(linije[3]);

			}

		});
		mnNewMenu.add(mntmOpenMessage);
		mnNewMenu.add(mntmSendMessage);

		JMenu mnM = new JMenu("File");
		menuBar.add(mnM);
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Open ucitaj = new Open();
				ucitaj.openTxt(textArea);
			}
		});
		mnM.add(mntmOpen);

		JMenuItem mntmPrint = new JMenuItem("Print");
		mntmPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PrinterJob pjob = PrinterJob.getPrinterJob();
				PageFormat pf = pjob.defaultPage();
				pjob.setPrintable(new PrintableClass(), pf);
				try {
					if (pjob.printDialog()) {
						pjob.print();
					}
				} catch (PrinterException ex) {
				}

			}
		});
		mnM.add(mntmPrint);

		JSeparator separator_1 = new JSeparator();
		mnM.add(separator_1);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnM.add(mntmExit);

		subject = new JTextField();
		subject.setText("");
		subject.setBounds(57, 73, 342, 20);
		panel.add(subject);
		subject.setColumns(10);
		area = new JTextArea();
		area.setVisible(false);
		area.setBounds(315, 104, 4, 22);
		panel.add(area);

		JLabel lblFrom = new JLabel("From:");
		lblFrom.setBounds(10, 14, 46, 14);
		panel.add(lblFrom);

		JLabel lblTo = new JLabel("To:");
		lblTo.setBounds(10, 45, 46, 14);
		panel.add(lblTo);

		JLabel lblSubject = new JLabel("Subject");
		lblSubject.setBounds(10, 76, 46, 14);
		panel.add(lblSubject);
		DragDrop dropText = new DragDrop();
		dropText.drop(textArea);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				

				String[] adresa = to.getText().split(" ");
				for (int i = 0; i < adresa.length; i++) {
					String adresaPrimaoca = adresa[i];
					emailClient.posaljiMail(from.getText(), adresaPrimaoca,
							subject.getText(), textArea.getText());

				}
				GregorianCalendar datum = new GregorianCalendar();
				String dat = datum.getTime().toString();
				String	teskt=textArea.getText();
				textArea.setText(teskt+"\n"+dat+"."	);
			}
		});
		btnSend.setBounds(54, 104, 122, 21);
		panel.add(btnSend);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBounds(10, 164, 721, 245);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(-2, -1, 721, 245);
		panel_1.add(scrollPane_1);

		// textArea = new JTextArea();
		scrollPane_1.setViewportView(textArea);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(textArea, popupMenu);

		JMenuItem mntmSelectAll = new JMenuItem("Select All");
		mntmSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.selectAll();
			}
		});
		popupMenu.add(mntmSelectAll);

		JMenuItem mntmClearAll = new JMenuItem("Clear All");
		mntmClearAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText(null);
			}
		});
		popupMenu.add(mntmClearAll);

		JMenuItem mntmNewMenuItem = new JMenuItem("WordWrap");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setLineWrap(true);
			}
		});
		popupMenu.add(mntmNewMenuItem);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
