package test.day3;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 이 소스에서 모든 처리를 수행하게 되면 서버 1개 - 클라이언트 1개 , 밖에 수행하지 못한다. 원인은 '스레드'가 하나이기 때문이다.
 * 스레드가 뭔데? 스레드는 프로세스 흐름을 단위로 쪼갠거다. 그러면 여러 클라이언트를 받기 위해서는 어떻게 해야하나?
 * 2가지 방법이 있다. 1, 멀티 프로세스 2. 멀티 스레드. 여기에서 우리는 멀티 스레드 방식을 채택한다.
 * 멀티 프로세스는 자원이 많이 들기 때문이다. 멀티 프로세스는 클라이언트를 받을 때마다 프로세스 관련 모든 소스를 통째로 복사한다.
 * 그러면 이 프로세스가 1M 용량을 차지했다면 2개는 2M, 3개는 3M.. 자원낭비가 심하다.
 * 반면, 멀티 스레드는 클라이언트 요청을 처리하는 그 '소스'만 복사하는거다. 그러니 자원이 가볍다. 그래서 이 방식으로 스레드를 늘리는 작업을 할거다.
 * 파일은 Calculator_3_Worker로 만들어서 사용할거니 while문에서 어떻게 넘기는지 공부하는 게 오늘의 목표다.
 * 바뀐 부분에 ## 처리를 해놓겠다
 * */

public class Caculator_3_server {
	private int port;
	
	public Caculator_3_server(int port) {
		this.port = port;
	}
	
	@SuppressWarnings("resource")
	public void service() throws Exception {
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("Server Startup !!!!");
		Socket socket = null;
		
		while(true) {
			try {
				System.out.println("클라이언트 기다리는 중...");
				socket = serverSocket.accept();	//클라이언트 연결이 들어오면 연결 승인 후 소켓 값 리턴
				System.out.println("클라이언트와 연결되었습니다.");
				
//				##자체 메소드를 호출하지 않고 Calculator_3_Worker로 소스를 분산시킨다. 				
//				processRequest(socket);	
				new Calculator_3_Worker(socket).start();				
				System.out.println("클라이언트를 종료합니다.");
			} catch (Throwable e) {
				System.out.println("연결 오류!");
			}
		}
		
	}	
	
//	private void processRequest(Socket socket) throws Exception {
//		Scanner	in		= new Scanner(socket.getInputStream());
//		PrintStream out = new PrintStream(socket.getOutputStream());
//		String operator	= null;
//		double a, b, r;
//		
//		while(true) {
//			try {
//				operator = in.nextLine();
//				
//				if(operator.equals("see you again")) {
//					out.println("see you again");
//					break;
//				}else {
//					a = Double.parseDouble(in.nextLine());
//					b = Double.parseDouble(in.nextLine());
//					r = 0;
//					
//					switch(operator) {
//					case "+": r = a + b;
//					break;
//					case "-": r = a - b;
//					break;
//					case "*": r = a * b;
//					break;
//					case "/": 
//						if(b==0) throw new Exception("0이잖아요 ㅡㅡ");
//						r = a / b;
//					break;
//					default:
//						throw new Exception("해당 연산을 지원하지 않습니다!");
//					}
//					out.println("success");
//					out.println(r);
//					
//				}
//			} catch (Exception err) {
//				out.println("Fail");
//				out.println(err.getMessage());
//			}
//		}
//		try {out.close();} catch (Exception e) {}
//		try {in.close();} catch (Exception e) {}
//		try {socket.close();} catch (Exception e) {}			
//	}

	public static void main(String[] args) throws Exception {
		Caculator_3_server app = new Caculator_3_server(7001);
		app.service();
	}
}

