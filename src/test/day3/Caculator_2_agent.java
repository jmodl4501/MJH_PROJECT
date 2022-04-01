package test.day3;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Caculator_2_agent {
	//생성자
	Socket socket 	= null;
	PrintStream out	= null;//System.out.println(); 같은 게 모두 printStream에서 나온다고 함. 출력관련 클래스!
	Scanner in		= null;
	
	public Caculator_2_agent(String ip, int port) throws Exception {
		socket	= new Socket(ip, port);							//소켓은 ip, port넣나봐요
		out		= new PrintStream(socket.getOutputStream());	//socket에서 찍어주는게 있나봐요.
		in		= new Scanner(socket.getInputStream());			//socket에서 뭘 입력받으려 하나봐요.
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