package Chat0;

/**
 * 连多个客户端
 */
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.text.StyledEditorKit.BoldAction;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;
	
	public static void main(String[] args) {
		

		new ChatServer().start();
		
	}

	public void start() {
		try {
			 ss = new ServerSocket(8888);
			 started = true;
		}catch (BindException e) {
				System.out.println("端口使用中");
				System.out.println("请关闭相关程序并重新运行服务器");
				System.exit(0);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		try{
			while (started) {
				Socket s = ss.accept();
				Client c = new Client(s);
System.out.println("a client connnented");//调试语句
				new Thread(c).start();
			
				//dis.close();
			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	class Client implements Runnable{
		private Socket s;
		private DataInputStream dis = null;
		private boolean  bConnected = false;
		public Client(Socket s) {
			this.s=s;
			try {
				dis = new DataInputStream( s.getInputStream());
				bConnected = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void run() {
			
			try{
				while (bConnected) {
					String	str = dis.readUTF();
					System.out.println(str);
				} 
			}catch (EOFException e) {
					System.out.println("client close");			
			}catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
						if(dis != null)dis.close();
						if (s != null) s.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				
				}
			}
		
		}
}

