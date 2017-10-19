package Chat0;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.text.StyledEditorKit.BoldAction;

public class ChatServer {
	
	
	public static void main(String[] args) {
		boolean started = false;
		try {
			ServerSocket ss = new ServerSocket(8888);
			started = true;
			while (started) {
				boolean bconnected = false;
				Socket s = ss.accept();
System.out.println("a client connnented");//µ÷ÊÔÓï¾ä
				bconnected = true;
				DataInputStream dis = new DataInputStream(s.getInputStream());
				while(bconnected){
					String str = dis.readUTF();
					System.out.println(str);
				}
				dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
