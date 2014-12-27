package chatGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;

/**
 * Class PrivateChatFrame
 * The frame in private chat mode
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class PrivateChatFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textAreaSendPrivateMessage ;
	private JButton btnSendPrivateMessage ;
	private JButton btnSendFile ;
	private JLabel infoLabel ;
	private String username ;
	private String ip ;
	
	/**
	 * Constructor
	 */
	public PrivateChatFrame(String username, String ip) {
		this.username = username;
		this.ip = ip ;
		initComponent();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	/**
	 * Initiate all components
	 */
	public void initComponent(){
		setTitle("PrivateChat");
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 57, 414, 135);
		contentPane.add(scrollPane);
		
		textAreaSendPrivateMessage = new JTextArea();
		scrollPane.setViewportView(textAreaSendPrivateMessage);
		
		btnSendPrivateMessage = new JButton("Send private message");
		btnSendPrivateMessage.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSendPrivateMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSendPrivateMessage.setBounds(41, 203, 159, 47);
		contentPane.add(btnSendPrivateMessage);
		
		btnSendFile = new JButton("Send File");
		btnSendFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSendFile.setBounds(210, 203, 170, 47);
		contentPane.add(btnSendFile);
		
		infoLabel = new JLabel();
		infoLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		infoLabel.setBounds(10, 11, 414, 35);
		contentPane.add(infoLabel);
	}

	public JTextArea getTextAreaSendPrivateMessage() {
		return textAreaSendPrivateMessage;
	}

	public void setTextAreaSendPrivateMessage(JTextArea textAreaSendPrivateMessage) {
		this.textAreaSendPrivateMessage = textAreaSendPrivateMessage;
	}

	public JLabel getInfoLabel() {
		return infoLabel;
	}

	public void setInfoLabel(JLabel infoLabel) {
		this.infoLabel = infoLabel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public JButton getBtnSendPrivateMessage() {
		return btnSendPrivateMessage;
	}

	public JButton getBtnSendFile() {
		return btnSendFile;
	}
}