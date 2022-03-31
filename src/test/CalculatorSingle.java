package test;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CalculatorSingle extends JFrame implements ActionListener {

	//�ؽ�Ʈ�ʵ� (����Ʈ�ڽ� ������). JTextArea�� TextArea����.
	JTextField operand1 = new JTextField(4);
	JTextField operand2 = new JTextField(5);	
	//������ �޺��ڽ� ����? operatorData�� ���� �ȿ� ���� ���� ä�������ų�.
	String[] operatorData = {"+", "-", "*", "/"};	
	JComboBox<String> operator = new JComboBox<String>(operatorData);
	
	//��ư�����
	JButton equal = new JButton("=");
	//result
	JTextField result	= new JTextField(6);
	//clear��ư
	JButton btnClear = new JButton("CLEAR");
	

	public CalculatorSingle() {
		this.setTitle("Lesson01-Java Calculator");
		
		Container contentPane = this.getContentPane();
		//X_AXIS: X�� - horizontal , Y_AXIS: Y�� - vertical
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		contentPane.add(Box.createVerticalGlue());
		contentPane.add(this.createInputForm());
		contentPane.add(this.createToolBar());
		contentPane.add(Box.createVerticalGlue());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		//�ڽ��� �������� �����.
		Box box = Box.createHorizontalBox();
		//�ڽ��� �ִ� ������ (width, height)
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
		//�ҽ��̸��� equal�̸� ����ض�!
		if(e.getSource() == equal) {
			compute();
		//�ҽ��̸��� btnClear�̸� ������!
		}else if(e.getSource() == btnClear) {
			clearForm();
		}
		
	}
	
	private void compute() {
		double a = Double.parseDouble(operand1.getText());
		double b = Double.parseDouble(operand2.getText());
		double r = 0;
		
		try {
			switch (operator.getSelectedItem().toString()) {
				case "+": r = a + b;
				break;
				case "-": r = a - b;
				break;
				case "*": r = a * b;
				break;
				case "/": 
					if(b==0) throw new Exception("0���ݾƿ� �Ѥ�");
					r = a / b;
				break;
			}
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
		CalculatorSingle app = new CalculatorSingle();
		app.setVisible(true);		
	}
	

}
