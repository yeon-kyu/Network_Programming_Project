//import java.io.OutputStream;
//import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ClientHandler extends Thread{
	
	InputStream is;
	OutputStream os;
	BufferedReader r;
	BufferedWriter w;
	FileOutputStream fos;
	FileInputStream fis;
	DataInputStream dis;
	DataOutputStream dos;
	
	Socket socket;
	
	//ByteCasting C = new ByteCasting();
	byte[] buffer;
	
	static int counter=0;
	//모두에게 전송할 file 제목이나 notice
	static List<String> News = Collections.synchronizedList(new ArrayList<>());
	
	ConcurrentHashMap<String,Socket> Nets;
	
	ClientHandler(Socket socket,ConcurrentHashMap<String,Socket> Netss) throws IOException{
		
		this.Nets = Netss;
		
		this.socket = socket;
		is = socket.getInputStream();
		os = socket.getOutputStream();
		r = new BufferedReader(new InputStreamReader(is));
		w = new BufferedWriter(new OutputStreamWriter(os));
		dis = new DataInputStream(is);
		dos = new DataOutputStream(os);
		
	}
	public void run(){
	
		String id ="";
		try {
			counter++;
			
			while(true) {
				id = r.readLine();
				int flag=0;
				for(String key : Nets.keySet()) {
					if(key.equals(id)) {
						flag=1;
						w.write("Duplicated\n"); //ID 중복임을 보냄
						w.flush();
						continue;
					}
				}
				if(flag==0)
					break;
				
			}
//			w.write("login\n"); //성공적으로 로그인 됨을 알림 ... 필요없겠다. 이거는 아래의 N이 알아서 할듯
//			w.flush();
			System.out.println(id+"님이 접속하였습니다.");
			Nets.putIfAbsent(id,socket);
			
			System.out.println("현재 접속한 users : "+Nets.size()+","+Nets.keySet());
			System.out.println("client 수 (+not로그인) : "+counter);
			
			for(String key : Nets.keySet()) { //모든 클라이언트들에게 /N과 userID 전송하기
				BufferedWriter ww = new BufferedWriter(new OutputStreamWriter(Nets.get(key).getOutputStream()));
				
				ww.write("N\n"); //각각의 클라이언트에게 MsgType 보냄
				ww.flush();
				
				ww.write(Nets.keySet()+"\n"); // id보냄
				ww.flush();
			}
			
			//지금까지의 News 보여주기 
			int real_size2 = News.size(); //이후에 반복문에서 real_size가 나오기때문에 여기선 2를 붙음
			int news_size2 = News.size();
			if(news_size2>30) { //최근 30개만 보낼것임
				news_size2 = 30;
			}
			String newsSize = Integer.toString(news_size2);
			
			w.write("Notice\n"); //MsgType 보냄
			w.flush();
			
			w.write(newsSize+"\n"); // news_size 보냄
			w.flush();
			
			for(int i=0;i<news_size2;i++) {
				w.write(News.get(real_size2-1-i)+"\n"); 
				w.flush();
			}
			
			
			
		} catch (IOException e1) {
			//e1.printStackTrace();
			counter--;
			System.out.println("A Client has quit before login");
		}
		
		while(true) {
			
			try {
				String str;	
				str = r.readLine();  //한 줄 읽음(Msg)					
				
				//System.out.println("str : "+str);
				
				if(str.equals("/quit"))
					break;
				else if(str.equals("/FTP")) {
					
					str = r.readLine();		//MsgType 받음
					System.out.println("str : "+str);
					if(str.equals("p")) { //put
						System.out.println("MsgType : 'p'");	
						String senderID = r.readLine();
						String FILE_NAME = r.readLine();	//파일 이름 읽음
						System.out.print("senderID : "+senderID+", ");
						System.out.println("FILE_NAME : "+FILE_NAME);
						fos = new FileOutputStream(FILE_NAME); //나중에 FILE_NAME으로 바꿔야함
						buffer = new byte[1024];
						//byte[] temp = new byte[4];
						//System.out.println("check1");
						//is.read(temp);		//file Size받음 (byte[])
						//int fileSize = C.byteArrayToInt(temp);
						int readBytes;
						int readFileSize=0;
						int fileSize = dis.readInt();	//파일 사이즈 받음
						//System.out.println("check2");
						System.out.println("받은 파일의 크기 : "+fileSize +"bytes");
						if(fileSize<0) {
							System.out.println("File not Exist");
							break;
						}
						System.out.println("Receiving...");
						while(readFileSize<fileSize){
							readBytes = is.read(buffer);
							fos.write(buffer,0,readBytes);
							readFileSize+=readBytes;
						}
						System.out.println("file received completly");
						
						News.add(senderID+" > "+FILE_NAME);			
						for(String key : Nets.keySet()) { //모든 클라이언트들에게 /N과 userID 전송하기
							BufferedWriter ww = new BufferedWriter(new OutputStreamWriter(Nets.get(key).getOutputStream()));
							
							int real_size = News.size();
							int news_size = News.size();
							if(news_size>30) { //최근 30개만 보낼것임
								news_size = 30;
							}
							String newsSize = Integer.toString(news_size);
							
							ww.write("Notice\n"); //MsgType다시 보냄
							ww.flush();
							
							ww.write(newsSize+"\n"); // news_size 보냄
							ww.flush();
							
							for(int i=0;i<news_size;i++) {
								ww.write(News.get(real_size-1-i)+"\n"); 
								ww.flush();
							}
							
						}
						
						fos.close();
					}
					else if(str.equals("g")) { //get
						System.out.println("MsgType : 'g'");	
						
						w.write("g\n"); //MsgType 다시 보냄
						w.flush();
						
						String FILE_NAME = r.readLine(); //File Name 받음
						File file = new File(FILE_NAME);
						if(!file.exists()) {		//file이 없으면 -1의 fileSize보냄
							System.out.println("File not Exist");  
							int fileSize = -1;
							//os.write(C.intToByteArray(fileSize));
							dos.writeInt(fileSize);
							continue;
						}
						fis = new FileInputStream(file);
						int fileSize;
						fileSize = (int) file.length();
						
						//os.write(C.intToByteArray(fileSize));	//file Size 보냄
						dos.writeInt(fileSize);
						System.out.println("fileSize : "+fileSize+"bytes");
						long totalReadBytes = 0;
						int readBytes;
						buffer = new byte[1024];
						
						while((readBytes = fis.read(buffer))>0) {
							os.write(buffer,0,readBytes);		// file content 보냄
							totalReadBytes +=readBytes;
							System.out.println("progress : "+((totalReadBytes*100)/fileSize)+"%");
						}
						System.out.println("file sended completly");
						fis.close();
					}
		
					else if(str.equals("r")) { //rls
						System.out.println("MsgType : 'r'");
						R_ls ls = new R_ls();
							
						w.write("r\n"); //MsgType 다시 보냄
						w.flush();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						int fileLength = ls.getLength();
						dos.writeInt(fileLength);
						if(ls.getLength()<1)
							continue;
							
						ArrayList<String> files = ls.getFiles();
						for(int i=0;i<fileLength;i++) {//file 이름들 보냄
							w.write(files.remove(0)+"\n"); //한줄 보냄
							w.flush();
						}
						System.out.println("rls complete");
					}
					
					else if(str.equals("e")) { //exit
						System.out.println("MsgType : 'e'");
						System.out.println("-chatting mode-");
						break;
					}
					else {
						System.out.println("unexpected FTP command");
						//client의 처음화면 유도하도록 send()해야할듯 ㅇㅇ
					}
							
				}
				else if(str.equals("w")) { //whisper
					String senderID;
					String partnerID;
					String MyMessage;
					senderID = r.readLine();
					partnerID = r.readLine();
					MyMessage = r.readLine();
					int n = Nets.size();
					System.out.println("ID : "+ partnerID);
					System.out.println("MyMessage : "+ MyMessage);
					w.write("AKNAK\n"); //MsgType 다시 보냄
					w.flush();
					if(Nets.containsKey(partnerID)) {
						w.write("AK\n"); //MsgType 다시 보냄
						w.flush();
						
						//Nets.get(partnerID); //상대방 소켓임
						BufferedWriter ww = new BufferedWriter(new OutputStreamWriter(Nets.get(partnerID).getOutputStream()));
						ww.write("w\n"); 
						ww.flush();
						
						ww.write(senderID+"\n"); 
						ww.flush();
						
						ww.write(MyMessage+"\n"); 
						ww.flush();
					}
					else {
						w.write("NAK\n"); //
						w.flush();
					}
				}
				else if(str.equals("Notice")) {
					String senderID = r.readLine();
					String NoticeMsg = r.readLine();
					System.out.println(senderID+"> "+NoticeMsg);
					News.add(senderID+"> "+NoticeMsg);
					
					for(String key : Nets.keySet()) { //모든 클라이언트들에게 /N과 userID 전송하기
						BufferedWriter ww = new BufferedWriter(new OutputStreamWriter(Nets.get(key).getOutputStream()));
						
						int real_size = News.size();
						int news_size = News.size();
						if(news_size>30) { //최근 30개만 보낼것임
							news_size = 30;
						}
						String newsSize = Integer.toString(news_size);
						
						ww.write("Notice\n"); //MsgType다시 보냄
						ww.flush();
						
						ww.write(newsSize+"\n"); // news_size 보냄
						ww.flush();
						
						for(int i=0;i<news_size;i++) {
							ww.write(News.get(real_size-1-i)+"\n"); 
							ww.flush();
						}
						
					}
					
				}
				else {
					System.out.println("msg<- "+str);
				}	
				
			} catch (IOException e) {
			//e.printStackTrace();
			Nets.remove(id);
			counter--;
			System.out.println(id+"님이 나갑니다");
			
				
			try {
				for(String key : Nets.keySet()) { //모든 클라이언트들에게 /N과 userID 전송하기
					BufferedWriter ww = new BufferedWriter(new OutputStreamWriter(Nets.get(key).getOutputStream()));
					ww.write("N\n"); 
					ww.flush();
				
					ww.write(Nets.keySet()+"\n");//한줄 보냄
					ww.flush();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			} 
				
			
			
			//System.out.println("접속자 : "+Nets.keySet());
			//System.out.println("break");
			break;
		}	
	}
		try {
			r.close();
			w.close();
			is.close();
			os.close();
			socket.close();
			System.out.println("A Client has quit");
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
}
