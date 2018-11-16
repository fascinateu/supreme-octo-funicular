package Chat3;
import java.net.InetAddress;

import java.io.*;

import java.net.*;

import java.net.Socket;

import java.util.ArrayList;

import java.util.List;

public class MultiTalkServer{
      boolean listening=true;
      ServerSocket serverSocket = null;
      static HashMaps hashmap = new HashMaps();
      static int clientNumber = 0;
      
      public static void main(String[]args) throws IOException {
      new MultiTalkServer().start();
      }
      public void start() throws IOException{
    	  try {
    		  serverSocket = new ServerSocket(4700);
              ////����һ����������Socket����ServerSocket��ָ���󶨵Ķ˿ڣ��������˶˿��ڶ˿�4700�����ͻ�����
              listening = true;
              }catch(IOException e){
            	  System.out.println("Couldnot listen on port:4700.");
            	  //������ӡ������Ϣ
            	  System.exit(-1); //�˳�
            	  }
    	  System.out.println("***�������Ѿ��������ȴ��ͻ��˵�����***");  
    	  while(listening){ //ѭ�������ȴ��ͻ��˵����� 
    	  //�������ͻ����󣬸��ݵõ���Socket����Ϳͻ��������������̣߳�������֮
    	  //����accept()������ʼ�������ȴ��ͻ��˵�����
    	  Socket socket = serverSocket.accept();//�����ڴ˵Ⱥ�ͻ��˵�����
    	  System.out.println("***�ͻ��˳ɹ�����***");  
    	  clientNumber++;//��¼�ͻ���Ŀ
          ServerThread serverThread= new ServerThread(socket,clientNumber,hashmap);////����һ���µ��߳� 
          new Thread(serverThread).start();//�����̣߳�����start()�������߳�����Զ�����run()����
          System.out.println("Online Client is " + clientNumber);
          InetAddress address = socket.getInetAddress();  //��ȡIP
          System.out.println("��ǰ�ͻ��˵�IP:"+address);//*
          }
    	  serverSocket.close(); //�ر�ServerSocket

    	  }
    
      }
      

 