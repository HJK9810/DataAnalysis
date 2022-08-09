package rtdownAnalys;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class FileGUI extends JFrame {

	JPanel jp = new JPanel(); // 패널 초기화
	JButton jb = new JButton("버튼"); // 버튼 초기화
	JLabel jlabel = new JLabel();
	public FileGUI() {
		super("BigData 분석기(부동산편)"); // JFrame의 생성자에 값을 입력하면 윈도창에 해당 값이 입력됩니다.
		jp.add(jlabel);
		jlabel.setText("버튼을 누르시면 작업이 실행됩니다");
		jp.add(jb); // jp라는 패널에 jb라는 버튼 추가
		add(jp); // JFrame에 jp라는 패널 추가

		setSize(250, 150); // 윈도우의 크기 가로x세로
		setVisible(true); // 창을 보여줄떄 true, 숨길때 false
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x 버튼을 눌렀을때 종료
		jb.addActionListener(new ActionListener() { // 익명클래스로 리스너 작성

			@Override
			public void actionPerformed(ActionEvent e) {
				RealEstate process = new RealEstate();
				process.process();

				jlabel.setText("실행완료");
				JOptionPaneEx();
				
			}
		});
	}

	public void JOptionPaneEx() {
		JOptionPane.showMessageDialog(this, "완료되었습니다", "공지", JOptionPane.OK_OPTION);
	}

	public static void main(String[] args) {
		new FileGUI();
	}
}