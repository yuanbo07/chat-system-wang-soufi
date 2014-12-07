package chatGUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextArea;

import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
	private JButton btnSendMessage ;
	private JList<String> onlineUserlist ;

	public MainFrame() {
		this.initComponents();
		this.setVisible(true);
	}
	
	public void initComponents(){
		setTitle("Peer-to-Peer ChatSystem");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		

		onlineUserlist = new JList<String>();
		onlineUserlist.setBounds(414, 85, 147, 300);
		contentPane.add(onlineUserlist);
		
		/*
		 * 
		 * 
		 */
		
		onlineUserlist.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	PairChatFrame chatFrame = new PairChatFrame();
		        	chatFrame.setVisible(true);
		        } 
		    }
		});
		
		/*
		 * 
		 * 
		 */
		lblOnlineUsers = new JLabel("Online Users");
		lblOnlineUsers.setBounds(443, 61, 102, 14);
		contentPane.add(lblOnlineUsers);
		
		textAreaReceiveMessage = new JTextArea();
		textAreaReceiveMessage.setEditable(false);
		textAreaReceiveMessage.setLineWrap(true);
		textAreaReceiveMessage.setWrapStyleWord(true);
		textAreaReceiveMessage.setBounds(23, 81, 379, 215);
		contentPane.add(textAreaReceiveMessage);
		
		textAreaSendMessage = new JTextArea();
		textAreaSendMessage.setBounds(23, 307, 372, 78);
		textAreaSendMessage.setLineWrap(true);
		textAreaSendMessage.setWrapStyleWord(true);
		contentPane.add(textAreaSendMessage);
		
		btnSendMessage = new JButton("Send Message");
		btnSendMessage.setBounds(23, 396, 122, 23);
		contentPane.add(btnSendMessage);
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

	public JList<String> getOnlineUserlist() {
		return onlineUserlist;
	}

	public void setOnlineUserlist(JList<String> onlineUserlist) {
		this.onlineUserlist = onlineUserlist;
	}

	public JButton getBtnSendMessage() {
		return btnSendMessage;
	}

}
