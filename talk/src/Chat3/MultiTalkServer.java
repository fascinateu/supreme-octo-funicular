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
              ////创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口在端口4700监听客户请求
              listening = true;
              }catch(IOException e){
            	  System.out.println("Couldnot listen on port:4700.");
            	  //出错，打印出错信息
            	  System.exit(-1); //退出
            	  }
    	  System.out.println("***服务器已经启动，等待客户端的连接***");  
    	  while(listening){ //循环监听等待客户端的连接 
    	  //监听到客户请求，根据得到的Socket对象和客户计数创建服务线程，并启动之
    	  //调用accept()方法开始监听，等待客户端的连接
    	  Socket socket = serverSocket.accept();//程序将在此等候客户端的连接
    	  System.out.println("***客户端成功连接***");  
    	  clientNumber++;//记录客户数目
          ServerThread serverThread= new ServerThread(socket,clientNumber,hashmap);////创建一个新的线程 
          new Thread(serverThread).start();//启动线程，调用start()方法后，线程类会自动调用run()方法
          System.out.println("Online Client is " + clientNumber);
          InetAddress address = socket.getInetAddress();  //获取IP
          System.out.println("当前客户端的IP:"+address);//*
          }
    	  serverSocket.close(); //关闭ServerSocket

    	  }
    
      }
      

 