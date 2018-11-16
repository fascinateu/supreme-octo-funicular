package Chat3;

import java.io.*;
import java.net.*;

public class Stream {
	Socket socket = null;
	//���ڷ���Ϣ
	BufferedReader sin = null;
	PrintWriter os = null;
	BufferedReader is = null;
    //���ڴ��ļ�
	DataInputStream dis = null;
	DataOutputStream dos = null;
	
	String name = null;
	boolean flag = false;
	
	public Stream(Socket socket) throws Exception{
		this.socket = socket;
		
		sin=new BufferedReader(new InputStreamReader(System.in));	//ͨ���������빹��������
		is=new BufferedReader(new InputStreamReader(socket.getInputStream()));			//��������������Ϣ���䣬�ַ�������
		os=new PrintWriter(socket.getOutputStream());									//�������������Ϣ���䣬�ַ�������
		
	}
	public void close() throws Exception{
		dis.close();
		dos.close();
		is.close();
		os.close();
		socket.close();
	}
}
