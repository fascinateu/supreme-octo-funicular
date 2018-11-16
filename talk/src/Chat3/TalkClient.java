package Chat3;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.*;

public class TalkClient  extends Frame implements Runnable, ActionListener{
//实现 ActionListener 接口，为动作监听接口，是Java swing 监听窗体动作的一个接口
//多线程实现方式---实现Runnable接口
	//private String name;
	//static int clientNumber = 0;
	private TextField inputChat= new TextField();//输入区
    //文本输入框 TextField
    private TextArea showChat = new TextArea();//显示区

    private MenuItem OPEN=new MenuItem("open file");//菜单构造
    String prefix;     //读取到的文件后缀名，读取以后和recv拼接，形成传输文件
    
    Socket socket=null; //保存与本线程相关的Socket对象
    private BufferedReader is = null;
    private PrintWriter os = null;//两个流构造
    TalkClient(Socket socket) throws IOException{
    this.socket=socket;
    this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.os=new PrintWriter(socket.getOutputStream());
    //name="www";
//以下是图形用户界面的构造 //AWT
    setLocation(1000, 100);
    setSize(1000, 1000);
    Menu file=new Menu("SEND FILE");
    file.add(OPEN);//打开这个按钮
    OPEN.setEnabled(true);
    OPEN.addActionListener(this);//事件监听
    MenuBar bar=new MenuBar();
    bar.add(file);
    setMenuBar(bar);
    setSize(100,100);         
    setVisible(true);
    
    add(inputChat, BorderLayout.SOUTH);//布局
    add(showChat, BorderLayout.NORTH);//布局

    pack();//依据放置的组件设定窗口的大小 使之正好能容纳放置的所有组件

    inputChat.addActionListener(this);//窗口中输入，动作监听
// 注册监听器以监听事件源产生的事件
    addWindowListener(new WindowAdapter() {//添加一个窗口，监听响应关闭按钮
    	public void windowClosing(WindowEvent e) ////这是窗口关闭事件
    	{
    		System.exit(0);//退出
    		}
    	});
    setVisible(true);   //可以运行开始画图
    }
 
public void actionPerformed(ActionEvent e) {//定义处理事件的方法
	String s =inputChat.getText().trim();// 获取inputchat的text属性，也就是按钮文字,trim()去除前后空格                   
	inputChat.setText("");// 将取到的文本设置到一个输入框中

	os.println(s);
    os.flush();
    if(e.getSource()==OPEN)//事件源是OPEN
    {
    	FileDialog fd=new FileDialog(this,"open file",FileDialog.LOAD);//添加窗口
    	fd.setVisible(true);//FileDialog窗口构造
    	if(fd.getFile()!=null){
    		File file=new File(fd.getDirectory()+fd.getFile());
    		//创建了一个File对象，路径是directory，名字是filename。
    		//然后就可以调用这个对象的相关方法完成文件创建，删除，读取，写入等操作
    		String fileName=file.getName();
    		prefix=fileName.substring(fileName.lastIndexOf(".")+1);//这两行读取后缀名
    		if(file.exists()) 
    			try{
    				readFile(file.toString());
    				System.out.println(file.toString()+"\n\r");
                    } catch(IOException e1) {
                    	e1.printStackTrace();
                    	}
    		else 
    			showChat.setText("Filename:"+file+"  invalid");
    		}
    	fd.dispose();//资源释放
    	}
}   

public void readFile(String file) throws IOException{          
	//读文件，并且刷入流中
	String readline;
	BufferedReader in = new BufferedReader(new FileReader(file));//读入多种格式文件

    while((readline=in.readLine())!=null)
    {
    	os.println(readline);
    	os.flush();//刷新输出流，使Server马上收到该字符串
    	//在系统标准输出上打印读入的字符串
    	System.out.println(readline); 
    	}
}
public static void main(String[]args) throws UnknownHostException, IOException{
	//向本机的4700端口发出客户请求
	BufferedReader fis=null;
	Socket socket=new Socket("127.0.0.1",4700);
    //由系统标准输入设备构造BufferedReader对象
	BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
	//由Socket对象得到输出流，并构造PrintWriter对象
	PrintWriter os=new PrintWriter(socket.getOutputStream());
    //由Socket对象得到输入流，并构造相应的BufferedReader对象
	BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
	Thread tRecv = new Thread(new TalkClient(socket));
	tRecv.start();


//以下是没有图形处理界面的一个线程，这里保留下来作为注释，也作为项目的一个进程记录

	/*while(true)
	{
	String readline;
	readline=sin.readLine();//从系统标准输入读入一字符串
	while(!readline.equals("bye")){//若从标准输入读入的字符串为 "bye"则停止循环，将从系统标准输入读入的字符串输出到Server
	os.println(readline);
	os.flush();//刷新输出流，使Server马上收到该字符串
	在系统标准输出上打印读入的字符串
	System.out.println("Client:"+readline);
	if(!readline.equals("file")){
	//从Server读入一字符串，并打印到标准输出上
	readline=sin.readLine();//从系统标准输入读入一字符串
	}
	else
	fis=newBufferedReader(new FileReader("f:/test.txt"));//这里只有.txt文件
	readline=fis.readLine();
	System.out.println("1");
	while((readline=fis.readLine())!=null)
	{
		System.out.println("2");
		os.println(readline);
		os.println('\r');
		os.flush();//刷新输出流，使Server马上收到该字符串
		//在系统标准输出上打印读入的字符串
		System.out.println(readline);
		//从Server读入一字符串，并打印到标准输出上
	}//System.out.println("3");
	readline=sin.readLine();//从系统标准输入读入一字符串                              
	}
	os.close(); //关闭Socket输出流

	is.close(); //关闭Socket输入流

	fis.close();

	socket.close(); //关闭Socket
	}

*/         

}
public void run() {//文件操作启动线程，start方法启动run方法
	//多线程实现方式---实现Runnable接口，在使用该方式实现时，使需要实现多线程的类实现Runnable，实现该接口需要覆盖run方法,
	//然后将需要以多线程方式执行的代码书写在run方法内部或在run方法内部进行调用。
	while(true)
	{
		try {
			String ss=is.readLine();
			showChat.append(ss +"\n\r");
			} catch (IOException e) {
				e.printStackTrace();
				}
		
		}
	
	}
 /*public void disconnect() {
    try {
        if (os != null)
            os.close();
        if(is !=null)
            is.close();//关闭Socket输入流
        if (socket!= null)
            socket.close();
    } catch (IOException e) {
        e.printStackTrace();//在命令行打印异常信息在程序中出错的位置及原因
    }
    
}*/
}

 