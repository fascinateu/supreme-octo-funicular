package Chat3;

import java.io.*;

import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;

import java.net.*;

public class ServerThread extends Thread {

    //��������������ӵĿͻ��˵�socket
	Socket socket=null; //�����뱾�߳���ص�Socket����
	HashMaps hashmap = null;
	int clientNumber;
	private BufferedReader is = null;
	private PrintWriter os = null;
	private BufferedReader sin=null;
	
	public ServerThread(Socket s,int num,HashMaps hashmap) throws IOException{//���캯��
		super();
		this.socket=s;
		clientNumber=num+1;//��ʼ��clientNumber����
		this.hashmap=hashmap;//��ʼ��socket����
		this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //��Socket����õ��������������PrintWriter����

        this.os=new PrintWriter(socket.getOutputStream());//�׽��ֵ��ն������д��������

        //��ϵͳ��׼�����豸����BufferedReader����
    	this.sin=new BufferedReader(new InputStreamReader(System.in));
       }
public void run() { //�߳�����
	try{
		//int n;
		//n=MultiTalkServer.getnumber();
		Stream S = new Stream(socket);
    	S.os.println("���������������");
		S.os.flush();
    	S.name = S.is.readLine();
    	S.os.println(S.name);
    	S.os.flush();
    	hashmap.put(S.name, S);//����ͻ�������
    	S.os.println("�������������");
    	S.os.flush();
		String line = S.is.readLine();
		S.os.println(line);
		S.os.flush();
		while (!hashmap.containsKey(line)){
			S.os.println("������󲻴��ڣ����������룺");
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
			//����ʾ��������ͻ��˶�����ַ���
			System.out.println("Client "+socket.getInetAddress()+"��"+str);//���ip��ַ,������Ը�Ϊ�ͻ�������
			/*for(int i = 0 ; i < MultiTalkServer.st.size(); i++){                   
				
				ServerThread serverThread1= MultiTalkServer.st.get(i);
				//serverThread1.send("Client"+MultiTalkServer.clientNumber+":"+str);
               
				} */	
        //System.out.println("Online Client is " +n);
		//}
		}catch(Exception e){
			
			System.out.println("Error:"+e);//������ӡ������Ϣ
			}
	
	//��������������رտͻ���ʱ����������˼�һ��������Ϊ�����ͻ��˽�������ʱд����try�����
	//�������������Finally�����Ǵ���ķ�ʽ��
	/*finally {
				MultiTalkServer.clientNumber--;
		System.out.println("Online Client is " +MultiTalkServer.clientNumber);}*/
}

}

 

 
