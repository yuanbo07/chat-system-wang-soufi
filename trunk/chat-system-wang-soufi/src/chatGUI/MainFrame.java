package chatGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class MainFrame
 * The main frame of chat system
 * @author Yuanbo Wang & Asma Soufi
 * @version 1.0
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldNickname;
	private JTextArea textAreaReceiveMessage ;
	private JTextArea textAreaSendMessage ;
	private JLabel lblNickname ;
	private JLabel lblOnlineUsers ;
	private JButton btnConnect ;
	private JButton btnDisconnect ;
	private JButton btnSendFile;
	private JButton btnSendMessage ;
	@SuppressWarnings("rawtypes")
	private JList onlineUserlist ;

	/**
	 * Constructor
	 */
	public MainFrame() {
		this.initComponents();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Initiate all components
	 */
	@SuppressWarnings("rawtypes")
	public void initComponents(){
		setTitle("Peer-to-Peer ChatSystem");
		setBounds(100, 100, 619, 509);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNickname = new JLabel("Nickname : ");
		lblNickname.setBounds(23, 43, 74, 14);
		contentPane.add(lblNickname);
		
		textFieldNickname = new JTextField();
		textFieldNickname.setBounds(107, 40, 89, 20);
		contentPane.add(textFieldNickname);
		textFieldNickname.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(206, 39, 89, 23);
		contentPane.add(btnConnect);
		
		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(305, 39, 102, 23);
		contentPane.add(btnDisconnect);

		JScrollPane scrollPaneUserList = new JScrollPane();
		scrollPaneUserList.setBounds(414, 82, 147, 303);
		onlineUserlist = new JList();
		onlineUserlist.setToolTipText("Double click username to start private chat");
		
		scrollPaneUserList.setViewportView(onlineUserlist);
		contentPane.add(scrollPaneUserList);
		
		lblOnlineUsers = new JLabel("Online Users");
		lblOnlineUsers.setBounds(443, 61, 102, 14);
		contentPane.add(lblOnlineUsers);
		
		JScrollPane scrollPaneReceiveMessage = new JScrollPane();
		scrollPaneReceiveMessage.setBounds(23, 81, 379, 215);
		textAreaReceiveMessage = new JTextArea();
		textAreaReceiveMessage.setEditable(false);
		textAreaReceiveMessage.setLineWrap(true);
		textAreaReceiveMessage.setWrapStyleWord(true);
		textAreaReceiveMessage.setLineWrap(true);
		textAreaReceiveMessage.setWrapStyleWord(true);
		contentPane.add(scrollPaneReceiveMessage);
		scrollPaneReceiveMessage.setViewportView(textAreaReceiveMessage);
		
		JScrollPane scrollPaneSendMessage = new JScrollPane();
		scrollPaneSendMessage.setBounds(23, 307, 379, 78);
		textAreaSendMessage = new JTextArea();
		textAreaSendMessage.setWrapStyleWord(true);
		scrollPaneSendMessage.setViewportView(textAreaSendMessage);
		contentPane.add(scrollPaneSendMessage);
		
		btnSendMessage = new JButton("Send Message");
		btnSendMessage.setToolTipText("Send message to all online users");
		btnSendMessage.setBounds(23, 396, 122, 23);
		contentPane.add(btnSendMessage);
		
		btnSendFile = new JButton("Send a file to everyone");
		btnSendFile.setToolTipText("Send a file to all online users");
		btnSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSendFile.setBounds(155, 396, 240, 23);
		contentPane.add(btnSendFile);
	}

	public JButton getBtnConnect() {
		return btnConnect;
	}

	public JTextArea getTextAreaReceiveMessage() {
		return textAreaReceiveMessage;
	}

	public JTextField getTextFieldNickname() {
		return textFieldNickname;
	}

	public JButton getBtnDisconnect() {
		return btnDisconnect;
	}

	@SuppressWarnings("rawtypes")
	public JList getOnlineUserlist() {
		return onlineUserlist;
	}

	@SuppressWarnings("rawtypes")
	public void setOnlineUserlist(JList onlineUserlist) {
		this.onlineUserlist = onlineUserlist;
	}

	public JButton getBtnSendMessage() {
		return btnSendMessage;
	}

	public JTextArea getTextAreaSendMessage() {
		return textAreaSendMessage;
	}

	public JButton getBtnSendFile() {
		return btnSendFile;
	}

	public void setBtnSendFile(JButton btnSendFile) {
		this.btnSendFile = btnSendFile;
	}
}