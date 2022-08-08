package chat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ChatGUI extends JFrame {
	
	protected ChatGUI() {
		setTitle("ChatBot");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lb1 = new JLabel("Chatter");
		JTextField text = new JTextField(40);
		
	}

	public static void main(String[] args) {

	}
}
