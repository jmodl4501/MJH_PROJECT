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
	public void run() { //�����带 ���۽�Ű�� ������ �����帧���� �и��Ǿ� ������ run() �ڵ尡 �����. ���? �θ�/�ڽ� �����尡 �������� �۾�����.
		System.out.println("workerId::["+workerId+"] ��û�� �������Դϴ�...");
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
		System.out.println("Ŭ���̾�Ʈ ���Կ�~");
	}
/**
 * <���� ������ �Ѿ�� �˾Ƶ־� �� �̾߱�>
 * ���� ���α׷��� �ÿ��� DB����, Ʈ������, ����, �ڹ� �� �� �Ű澵 �� ����ؿ�. ����ũ�� ���ø����̼� ���� ������ �̷��� ���絥 ���̿���.
 * ���� ���ø����̼� �ڿ��� �����ϴ� ���α׷��ֵ� �ʿ�����. ���� �̷� �κ��� �ڵ�ȭ�� �����Ѵٸ�? �ξ� �������� �������?
 * �װ� ���� �忡�� ������ �̴ϴ�.
 * */
}