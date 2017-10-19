package Chat0;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends Frame{
	Socket s=null;
	DataInputStream dis = null;
	DataOutputStream dos = null;
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	private boolean bConnected = false;
	Thread tRecv = new Thread(new RecvThread());
	
	
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	public void launchFrame() {
		setLocation(400,300);
		this.setSize(500, 500);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
		
		tRecv.start();
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
System.out.println("connect");
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		/*
		try {
				bConnected = false;
				
				tRecv.join();
		}catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				
				try {
				
						dos.close();
						dis.close();
						s.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			
		} 
		*/
	}
	
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			//taContent.setText(str);
			tfTxt.setText("");
			try {
				
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}

	private class RecvThread implements Runnable{

		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					//System.out.println(str);
					taContent.setText(taContent.getText()+str+'\n');
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
