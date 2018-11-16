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
//ʵ�� ActionListener �ӿڣ�Ϊ���������ӿڣ���Java swing �������嶯����һ���ӿ�
//���߳�ʵ�ַ�ʽ---ʵ��Runnable�ӿ�
	//private String name;
	//static int clientNumber = 0;
	private TextField inputChat= new TextField();//������
    //�ı������ TextField
    private TextArea showChat = new TextArea();//��ʾ��

    private MenuItem OPEN=new MenuItem("open file");//�˵�����
    String prefix;     //��ȡ�����ļ���׺������ȡ�Ժ��recvƴ�ӣ��γɴ����ļ�
    
    Socket socket=null; //�����뱾�߳���ص�Socket����
    private BufferedReader is = null;
    private PrintWriter os = null;//����������
    TalkClient(Socket socket) throws IOException{
    this.socket=socket;
    this.is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
    this.os=new PrintWriter(socket.getOutputStream());
    //name="www";
//������ͼ���û�����Ĺ��� //AWT
    setLocation(1000, 100);
    setSize(1000, 1000);
    Menu file=new Menu("SEND FILE");
    file.add(OPEN);//�������ť
    OPEN.setEnabled(true);
    OPEN.addActionListener(this);//�¼�����
    MenuBar bar=new MenuBar();
    bar.add(file);
    setMenuBar(bar);
    setSize(100,100);         
    setVisible(true);
    
    add(inputChat, BorderLayout.SOUTH);//����
    add(showChat, BorderLayout.NORTH);//����

    pack();//���ݷ��õ�����趨���ڵĴ�С ʹ֮���������ɷ��õ��������

    inputChat.addActionListener(this);//���������룬��������
// ע��������Լ����¼�Դ�������¼�
    addWindowListener(new WindowAdapter() {//���һ�����ڣ�������Ӧ�رհ�ť
    	public void windowClosing(WindowEvent e) ////���Ǵ��ڹر��¼�
    	{
    		System.exit(0);//�˳�
    		}
    	});
    setVisible(true);   //�������п�ʼ��ͼ
    }
 
public void actionPerformed(ActionEvent e) {//���崦���¼��ķ���
	String s =inputChat.getText().trim();// ��ȡinputchat��text���ԣ�Ҳ���ǰ�ť����,trim()ȥ��ǰ��ո�                   
	inputChat.setText("");// ��ȡ�����ı����õ�һ���������

	os.println(s);
    os.flush();
    if(e.getSource()==OPEN)//�¼�Դ��OPEN
    {
    	FileDialog fd=new FileDialog(this,"open file",FileDialog.LOAD);//��Ӵ���
    	fd.setVisible(true);//FileDialog���ڹ���
    	if(fd.getFile()!=null){
    		File file=new File(fd.getDirectory()+fd.getFile());
    		//������һ��File����·����directory��������filename��
    		//Ȼ��Ϳ��Ե�������������ط�������ļ�������ɾ������ȡ��д��Ȳ���
    		String fileName=file.getName();
    		prefix=fileName.substring(fileName.lastIndexOf(".")+1);//�����ж�ȡ��׺��
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
    	fd.dispose();//��Դ�ͷ�
    	}
}   

public void readFile(String file) throws IOException{          
	//���ļ�������ˢ������
	String readline;
	BufferedReader in = new BufferedReader(new FileReader(file));//������ָ�ʽ�ļ�

    while((readline=in.readLine())!=null)
    {
    	os.println(readline);
    	os.flush();//ˢ���������ʹServer�����յ����ַ���
    	//��ϵͳ��׼����ϴ�ӡ������ַ���
    	System.out.println(readline); 
    	}
}
public static void main(String[]args) throws UnknownHostException, IOException{
	//�򱾻���4700�˿ڷ����ͻ�����
	BufferedReader fis=null;
	Socket socket=new Socket("127.0.0.1",4700);
    //��ϵͳ��׼�����豸����BufferedReader����
	BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
	//��Socket����õ��������������PrintWriter����
	PrintWriter os=new PrintWriter(socket.getOutputStream());
    //��Socket����õ�����������������Ӧ��BufferedReader����
	BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
	
	Thread tRecv = new Thread(new TalkClient(socket));
	tRecv.start();


//������û��ͼ�δ�������һ���̣߳����ﱣ��������Ϊע�ͣ�Ҳ��Ϊ��Ŀ��һ�����̼�¼

	/*while(true)
	{
	String readline;
	readline=sin.readLine();//��ϵͳ��׼�������һ�ַ���
	while(!readline.equals("bye")){//���ӱ�׼���������ַ���Ϊ "bye"��ֹͣѭ��������ϵͳ��׼���������ַ��������Server
	os.println(readline);
	os.flush();//ˢ���������ʹServer�����յ����ַ���
	��ϵͳ��׼����ϴ�ӡ������ַ���
	System.out.println("Client:"+readline);
	if(!readline.equals("file")){
	//��Server����һ�ַ���������ӡ����׼�����
	readline=sin.readLine();//��ϵͳ��׼�������һ�ַ���
	}
	else
	fis=newBufferedReader(new FileReader("f:/test.txt"));//����ֻ��.txt�ļ�
	readline=fis.readLine();
	System.out.println("1");
	while((readline=fis.readLine())!=null)
	{
		System.out.println("2");
		os.println(readline);
		os.println('\r');
		os.flush();//ˢ���������ʹServer�����յ����ַ���
		//��ϵͳ��׼����ϴ�ӡ������ַ���
		System.out.println(readline);
		//��Server����һ�ַ���������ӡ����׼�����
	}//System.out.println("3");
	readline=sin.readLine();//��ϵͳ��׼�������һ�ַ���                              
	}
	os.close(); //�ر�Socket�����

	is.close(); //�ر�Socket������

	fis.close();

	socket.close(); //�ر�Socket
	}

*/         

}
public void run() {//�ļ����������̣߳�start��������run����
	//���߳�ʵ�ַ�ʽ---ʵ��Runnable�ӿڣ���ʹ�ø÷�ʽʵ��ʱ��ʹ��Ҫʵ�ֶ��̵߳���ʵ��Runnable��ʵ�ָýӿ���Ҫ����run����,
	//Ȼ����Ҫ�Զ��̷߳�ʽִ�еĴ�����д��run�����ڲ�����run�����ڲ����е��á�
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
            is.close();//�ر�Socket������
        if (socket!= null)
            socket.close();
    } catch (IOException e) {
        e.printStackTrace();//�������д�ӡ�쳣��Ϣ�ڳ����г����λ�ü�ԭ��
    }
    
}*/
}

 