package Chat3;

import java.io.*;
import java.net.*;

public class Stream {
	Socket socket = null;
	//用于发消息
	BufferedReader sin = null;
	PrintWriter os = null;
	BufferedReader is = null;
    //用于传文件
	DataInputStream dis = null;
	DataOutputStream dos = null;
	
	String name = null;
	boolean flag = false;
	
	public Stream(Socket socket) throws Exception{
		this.socket = socket;
		
		sin=new BufferedReader(new InputStreamReader(System.in));	//通过键盘输入构造输入流
		is=new BufferedReader(new InputStreamReader(socket.getInputStream()));			//输入流，用于消息传输，字符串传输
		os=new PrintWriter(socket.getOutputStream());									//输出流，用于消息传输，字符串传输
		
	}
	public void close() throws Exception{
		dis.close();
		dos.close();
		is.close();
		os.close();
		socket.close();
	}
}
