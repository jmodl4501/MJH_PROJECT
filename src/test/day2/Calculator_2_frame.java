package test.day2;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * calculatorSingle -> calculator_2_frame / agent 2개의 파일로 분기.
 * 왜 쪼개느냐? 프레임 역할 / 계산역할을 분기하기 위해서
 * 어떤 걸 학습할 수 있느냐? 서버와 클라이언트의 역할로 나눌 수 있다.
 * 이 화면에서는 calculatorSingle에서 계산하는 로직을 덜어내는 대신, agent 호출소스를 추가했다.
 * 우측 하단에 Display Selected Console 누르면 server 콘솔따로, frame 콘솔따로 확인 가능하다.
 * */

public class Calculator_2_frame extends JFrame implements ActionListener {

	Caculator_2_agent calcAgent;
	//텍스트필드 (에디트박스 같은거). JTextArea는 TextArea란다.
	JTextField operand1 = new JTextField(4);
	JTextField operand2 = new JTextField(5);
	
/**
 * 에이전트로 보낼거야	
 * */
//	//딱봐도 콤보박스 ㅇㅈ? operatorData는 여기 안에 무슨 값을 채워넣을거냐.
//	String[] operatorData = {"+", "-", "*", "/"};	
	JTextField operator = new JTextField(2); // 문자 받을수만 있게
//	JComboBox<String> operator = new JComboBox<String>(operatorData);
	
	
	//버튼만들기
	JButton equal = new JButton("=");
	//result
	JTextField result	= new JTextField(6);
	//clear버튼
	JButton btnClear = new JButton("CLEAR");
	

	public Calculator_2_frame() {
		this.setTitle("Lesson01-Java Calculator");
		
		try {
			calcAgent = new Caculator_2_agent("localhost",7001);
		} catch (Exception err) {
			JOptionPane.showMessageDialog(
					null, err.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		
		Container contentPane = this.getContentPane();
		//X_AXIS: X축 - horizontal , Y_AXIS: Y축 - vertical
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(this.createInputForm());
		contentPane.add(this.createToolBar());
		contentPane.add(Box.createVerticalGlue());
		
		/**
		 * 프레임에서 끄는 게 아니라 에이전트를 닫도록 설정.	
		 * */		
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				calcAgent.close();
				System.exit(0);
			}
		});		
		
		this.pack();
		this.setLocationRelativeTo(null);
	}	
	
	private Component createToolBar() {
		Box box = Box.createHorizontalBox();
		box.add(btnClear);
		btnClear.addActionListener(this);
		return box;
	}

	private Component createInputForm() {
		//박스를 수평으로 만든다.
		Box box = Box.createHorizontalBox();
		//박스의 최대 사이즈 (width, height)
		box.setMaximumSize(new Dimension(300, 30));
		box.setAlignmentY(Box.CENTER_ALIGNMENT);
		box.add(operand1);		
		box.add(operator);		
		box.add(operand2);
		box.add(equal);
		box.add(result);
		equal.addActionListener(this);
		return box;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//소스이름이 equal이면 계산해라!
		if(e.getSource() == equal) {
			compute();
		//소스이름이 btnClear이면 지워라!
		}else if(e.getSource() == btnClear) {
			clearForm();
		}
		
	}
	
	private void compute() {
		double a = Double.parseDouble(operand1.getText());
		double b = Double.parseDouble(operand2.getText());
		double r = 0;
		
		try {
			/**
			 *  에이전트에서 산출할거야.
			 * */  
//			 switch (operator.getSelectedItem().toString()) {
//				case "+": r = a + b;
//				break;
//				case "-": r = a - b;
//				break;
//				case "*": r = a * b;
//				break;
//				case "/": 
//					if(b==0) throw new Exception("0이잖아요 ㅡㅡ");
//					r = a / b;
//				break;
//			}
			r = calcAgent.compute(operator.getText(), a, b);
			result.setText(Double.toString(r));			


			result.setText(Double.toString(r));			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void clearForm() {
		operand1.setText("");
		operand2.setText("");		
		result.setText("");
	}
	
	
	public static void main(String[] args) {
		Calculator_2_frame app = new Calculator_2_frame();
		app.setVisible(true);		
	}
	

}
