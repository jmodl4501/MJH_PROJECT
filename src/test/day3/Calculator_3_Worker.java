package test.day3;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Calculator_3_Worker extends Thread{
	static int count;
	
	Socket socket;
	Scanner in;
	PrintStream out;
	int workerId;

	public Calculator_3_Worker(Socket socket) throws Exception{
		workerId = count++;
		this.socket = socket;
		in 	= new Scanner(socket.getInputStream());
		out	= new PrintStream(socket.getOutputStream());
	}
	
	@Override
	public void run() { //스레드를 시작시키면 별도의 실행흐름으로 분리되어 별개로 run() 코드가 실행됨. 어떻게? 부모/자식 스레드가 병행으로 작업수행.
		System.out.println("workerId::["+workerId+"] 요청이 진행중입니다...");
		String operator	= null;
		double a, b, r;
		
		while(true) {
			try {
				operator = in.nextLine();
				
				if(operator.equals("see you again")) {
					out.println("see you again");
					break;
				}else {
					a = Double.parseDouble(in.nextLine());
					b = Double.parseDouble(in.nextLine());
					r = 0;
					
					switch(operator) {
					case "+": r = a + b;
					break;
					case "-": r = a - b;
					break;
					case "*": r = a * b;
					break;
					case "/": 
						if(b==0) throw new Exception("0이잖아요 ㅡㅡ");
						r = a / b;
					break;
					default:
						throw new Exception("해당 연산을 지원하지 않습니다!");
					}
					out.println("success");
					out.println(r);
					
				}
			} catch (Exception err) {
				out.println("Fail");
				out.println(err.getMessage());
			}
		}
		try {out.close();} catch (Exception e) {}
		try {in.close();} catch (Exception e) {}
		try {socket.close();} catch (Exception e) {}
		System.out.println("클라이언트 끌게요~");
	}
/**
 * <다음 장으로 넘어가며 알아둬야 할 이야기>
 * 실제 프로그래밍 시에는 DB관리, 트랜젝션, 보안, 자바 빈 등 신경쓸 게 방대해요. 데스크톱 애플리케이션 띄우는 데에도 이렇게 힘든데 말이에요.
 * 따라서 애플리케이션 자원을 관리하는 프로그래밍도 필요하죠. 만약 이런 부분을 자동화로 관리한다면? 훨씬 편해지지 않을까요?
 * 그걸 다음 장에서 진행할 겁니다.
 * */
}