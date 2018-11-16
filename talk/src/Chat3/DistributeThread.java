package Chat3;

public class DistributeThread extends Thread{
	Stream stream1 = null;
	Stream stream2 = null;
	boolean flag = false;
	public DistributeThread(Stream stream1, Stream stream2) {
		this.stream1 = stream1; 	//ÏûÏ¢Á÷
		this.stream2 = stream2;
		flag = true;
	}
	public void run() {
		try {
			String line = null;
			line = stream1.is.readLine();
			while(!line.equals("bye")) {
			    
				stream1.os.println(stream1.name+":"+line);
			    stream1.os.flush();
				stream2.os.println(stream1.name+":"+line);
				stream2.os.flush();
				System.out.println(stream1.name+":"+line);
				line = stream1.is.readLine();	
				
			}
			flag = false;
		}catch(Exception e){
			System.out.println("Error:"+e);
		}
		finally {
			//MultiTalkServer.getnumber() ;
			MultiTalkServer.clientNumber--;
			System.out.println("Online Client is " +MultiTalkServer.clientNumber);
			
		}
	}
}
