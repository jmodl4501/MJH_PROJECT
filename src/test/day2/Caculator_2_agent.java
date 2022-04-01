package test.day2;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Caculator_2_agent {
	//������
	Socket socket 	= null;
	PrintStream out	= null;//System.out.println(); ���� �� ��� printStream���� ���´ٰ� ��. ��°��� Ŭ����!
	Scanner in		= null;
	
	public Caculator_2_agent(String ip, int port) throws Exception {
		socket	= new Socket(ip, port);							//������ ip, port�ֳ�����
		out		= new PrintStream(socket.getOutputStream());	//socket���� ����ִ°� �ֳ�����.
		in		= new Scanner(socket.getInputStream());			//socket���� �� �Է¹����� �ϳ�����.
	}

	public double compute(String operator, double a, double b) throws Exception{
		try {
			out.println(operator);
			out.println(a);
			out.println(b);
			out.flush();
			
			String state = in.nextLine();
			if(state.equals("success")) {
				return Double.parseDouble(in.nextLine());
			}else {
				throw new Exception(in.nextLine());
			}
		} catch (Exception e) {
			throw e;
		}
		
	}

	public void close() {
		try {
			out.println("see you again");
			System.out.println(in.nextLine());
		} catch (Exception e) {}
		
		try {out.close(); } catch (Exception e) {}
		try {in.close(); } catch (Exception e) {}
		try {socket.close(); } catch (Exception e) {}		
	}
}