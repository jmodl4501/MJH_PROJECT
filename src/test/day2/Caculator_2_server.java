package test.day2;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Caculator_2_server {
	private int port;
	
	public Caculator_2_server(int port) {
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
				
				processRequest(socket);			
				System.out.println("Ŭ���̾�Ʈ�� �����մϴ�.");
			} catch (Throwable e) {
				System.out.println("���� ����!");
			}
		}
		
	}	
	
	private void processRequest(Socket socket) throws Exception {
		Scanner	in		= new Scanner(socket.getInputStream());
		PrintStream out = new PrintStream(socket.getOutputStream());
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
						if(b==0) throw new Exception("0���ݾƿ� �Ѥ�");
						r = a / b;
					break;
					default:
						throw new Exception("�ش� ������ �������� �ʽ��ϴ�!");
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
	}

	public static void main(String[] args) throws Exception {
		Caculator_2_server app = new Caculator_2_server(7001);
		app.service();
	}
}

