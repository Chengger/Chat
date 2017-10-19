package Chat0;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.text.StyledEditorKit.BoldAction;

public class ChatServer {
	
	
	public static void main(String[] args) {
		boolean started = false;
		ServerSocket ss = null;
		Socket s = null;
		DataInputStream dis = null;
		try {
			 ss = new ServerSocket(8888);
		}catch (BindException e) {
				System.out.println("端口使用中");
				System.out.println("请关闭相关程序并重新运行服务器");
				System.exit(0);
		}catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		try{
			started = true;
			while (started) {
				boolean bconnected = false;
				 s = ss.accept();
System.out.println("a client connnented");//调试语句
				bconnected = true;
				 dis = new DataInputStream(s.getInputStream());
				while(bconnected){
					String str = dis.readUTF();
					System.out.println(str);
				}
				//dis.close();
			}
		}catch (EOFException e) {
			// TODO: handle exception
			System.out.println("client close");
		} 
		catch (IOException e) {
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
