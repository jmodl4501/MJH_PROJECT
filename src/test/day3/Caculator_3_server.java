package test.day3;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * �� �ҽ����� ��� ó���� �����ϰ� �Ǹ� ���� 1�� - Ŭ���̾�Ʈ 1�� , �ۿ� �������� ���Ѵ�. ������ '������'�� �ϳ��̱� �����̴�.
 * �����尡 ����? ������� ���μ��� �帧�� ������ �ɰ��Ŵ�. �׷��� ���� Ŭ���̾�Ʈ�� �ޱ� ���ؼ��� ��� �ؾ��ϳ�?
 * 2���� ����� �ִ�. 1, ��Ƽ ���μ��� 2. ��Ƽ ������. ���⿡�� �츮�� ��Ƽ ������ ����� ä���Ѵ�.
 * ��Ƽ ���μ����� �ڿ��� ���� ��� �����̴�. ��Ƽ ���μ����� Ŭ���̾�Ʈ�� ���� ������ ���μ��� ���� ��� �ҽ��� ��°�� �����Ѵ�.
 * �׷��� �� ���μ����� 1M �뷮�� �����ߴٸ� 2���� 2M, 3���� 3M.. �ڿ����� ���ϴ�.
 * �ݸ�, ��Ƽ ������� Ŭ���̾�Ʈ ��û�� ó���ϴ� �� '�ҽ�'�� �����ϴ°Ŵ�. �׷��� �ڿ��� ������. �׷��� �� ������� �����带 �ø��� �۾��� �ҰŴ�.
 * ������ Calculator_3_Worker�� ���� ����ҰŴ� while������ ��� �ѱ���� �����ϴ� �� ������ ��ǥ��.
 * �ٲ� �κп� ## ó���� �س��ڴ�
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
				System.out.println("Ŭ���̾�Ʈ ��ٸ��� ��...");
				socket = serverSocket.accept();	//Ŭ���̾�Ʈ ������ ������ ���� ���� �� ���� �� ����
				System.out.println("Ŭ���̾�Ʈ�� ����Ǿ����ϴ�.");
				
//				##��ü �޼ҵ带 ȣ������ �ʰ� Calculator_3_Worker�� �ҽ��� �л��Ų��. 				
//				processRequest(socket);	
				new Calculator_3_Worker(socket).start();				
				System.out.println("Ŭ���̾�Ʈ�� �����մϴ�.");
			} catch (Throwable e) {
				System.out.println("���� ����!");
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
//						if(b==0) throw new Exception("0���ݾƿ� �Ѥ�");
//						r = a / b;
//					break;
//					default:
//						throw new Exception("�ش� ������ �������� �ʽ��ϴ�!");
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

