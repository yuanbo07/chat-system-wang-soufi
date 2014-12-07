package chatGUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JLabel;

public class PairChatFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PairChatFrame frame = new PairChatFrame();
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
	public PairChatFrame() {
		setTitle("PairChat Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 413, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea receiveMessageTextArea = new JTextArea();
		receiveMessageTextArea.setEditable(false);
		receiveMessageTextArea.setBounds(10, 36, 377, 222);
		contentPane.add(receiveMessageTextArea);
		receiveMessageTextArea.setLineWrap(true);
		receiveMessageTextArea.setWrapStyleWord(true);
			
		JTextArea sendMessageTextArea = new JTextArea();
		sendMessageTextArea.setBounds(10, 269, 377, 75);
		contentPane.add(sendMessageTextArea);
		
		JButton btnSendMessage = new JButton("Send Message");
		btnSendMessage.setBounds(31, 355, 144, 23);
		contentPane.add(btnSendMessage);
		
		JButton btnSendFile = new JButton("Send File");
		btnSendFile.setBounds(212, 355, 162, 23);
		contentPane.add(btnSendFile);
		
		JLabel lblCurrentChatting = new JLabel("You are chatting with :");
		lblCurrentChatting.setBounds(12, 14, 163, 14);
		contentPane.add(lblCurrentChatting);
		
		JLabel lblCurrentChattingName = new JLabel("");
		lblCurrentChattingName.setBounds(166, 14, 63, 14);
		contentPane.add(lblCurrentChattingName);
		

		
	}
}
