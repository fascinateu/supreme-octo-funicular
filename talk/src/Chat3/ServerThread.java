package Chat3;

import java.io.*;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.*;

public class ServerThread extends Thread {

    //下面这个表储存链接的客户端的socket
	Socket socket=null; //保存与本线程相关的Socket对象
	HashMaps hashmap = null;
	int clientNumber;
	private BufferedReader is = null;
	private PrintWriter os = null;
	private BufferedReader sin=null;
	
	public ServerThread(Socket s,int num,HashMaps hashmap) throws IOException{//构造函数
		super();
		this.socket=s;
		clientNumber=num+1;//初始化clientNumber变量
		this.hashmap=hashmap;//初始化socket变量
		this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //由Socket对象得到输出流，并构造PrintWriter对象

        this.os=new PrintWriter(socket.getOutputStream());//套接字的终端输出流写到网络中

        //由系统标准输入设备构造BufferedReader对象
    	this.sin=new BufferedReader(new InputStreamReader(System.in));
       }
public void run() { //线程主体
	try{
		//int n;
		//n=MultiTalkServer.getnumber();
		Stream S = new Stream(socket);
    	S.os.println("请输入你的姓名：");
		S.os.flush();
    	S.name = S.is.readLine();
    	S.os.println(S.name);
    	S.os.flush();
    	hashmap.put(S.name, S);//存入客户端名称
    	S.os.println("请输入聊天对象：");
    	S.os.flush();
		String line = S.is.readLine();
		S.os.println(line);
		S.os.flush();
		while (!hashmap.containsKey(line)){
			S.os.println("聊天对象不存在，请重新输入：");
			S.os.flush();
    		line = S.is.readLine();
    		S.os.println(line);
    		S.os.flush();
    		continue;
			}
			//S.os.println(line);
			Stream s = hashmap.get(line);
			
			DistributeThread assign = new DistributeThread(S, s);
			assign.start();
		
			/*String str=S.is.readLine();
			//在显示屏上输出客户端都入的字符串
			System.out.println("Client "+socket.getInetAddress()+"："+str);//输出ip地址,这里可以改为客户端名称
			/*for(int i = 0 ; i < MultiTalkServer.st.size(); i++){                   
				
				ServerThread serverThread1= MultiTalkServer.st.get(i);
				//serverThread1.send("Client"+MultiTalkServer.clientNumber+":"+str);
               
				} */	
        //System.out.println("Online Client is " +n);
		//}
		}catch(Exception e){
			
			System.out.println("Error:"+e);//出错，打印出错信息
			}
	
	//最初在线人数当关闭客户端时在这里进行了减一，但是因为两个客户端建立连接时写在了try语句块里，
	//所以在这里加入Finally出口是错误的方式。
	/*finally {
				MultiTalkServer.clientNumber--;
		System.out.println("Online Client is " +MultiTalkServer.clientNumber);}*/
}

}

 

 
