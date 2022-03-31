package test;

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
		System.out.println("CalculatorServer startup:");
		
		Socket socket = null;
		
		while(true) {
			try {
				System.out.println("waiting client...");
				
				socket = serverSocket.accept();
				System.out.println("connected to client.");
				
				processRequest(socket);
				System.out.println("closed client.");
				
			} catch (Throwable e) {
				System.out.println("connection error!");
			}
		}
	}
	
	private void processRequest(Socket socket) throws Exception {
		Scanner in = new Scanner(socket.getInputStream());
		PrintStream out = new PrintStream(socket.getOutputStream());
			
		String operator = null;
		double a, b, r;
		
		while(true) {
			try {
				operator = in.nextLine();
				
				if (operator.equals("goodbye")) {
					out.println("goodbye");
					break;
					
				} else {
					a = Double.parseDouble(in.nextLine());
					b = Double.parseDouble(in.nextLine());
					r = 0;
				
					switch (operator) {
					case "+": r = a + b; break;
					case "-": r = a - b; break;
					case "*": r = a * b; break;
					case "/": 
						if (b == 0) throw new Exception("0 �쑝濡� �굹�닃 �닔 �뾾�뒿�땲�떎!");
						r = a / b; 
						break;
					default:
						throw new Exception("�빐�떦 �뿰�궛�쓣 吏��썝�븯吏� �븡�뒿�땲�떎!");
					}
					out.println("success");
					out.println(r);
				}
				
			} catch (Exception err) {
				out.println("failure");
				out.println(err.getMessage());
			}
		}
		
		try {out.close();} catch (Exception e) {}
		try {in.close();} catch (Exception e) {}
		try {socket.close();} catch (Exception e) {}
	}
	
	public static void main(String[] args) throws Exception {
		Caculator_2_server app = new Caculator_2_server(8888);
		app.service();
	}
}

