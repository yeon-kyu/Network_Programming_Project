//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.IOException;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.InputStream;
//import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TCPServer {
	
	static final int PORT = 2000;
	static ConcurrentHashMap<String,Socket> Nets;

	public static void main(String[] args) {
		
		Nets = new ConcurrentHashMap<>();
		
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			
			System.out.println("서버 : 클라이언트 접속을 대기합니다.");
			ClientHandler handler;
			while(true) { //run forever..
				Socket socket = serverSocket.accept();
				Thread t = new ClientHandler(socket,Nets);
				System.out.println("<Server>");
				System.out.println("서버 "+socket.getInetAddress()+
						" 클라이언트와 "+socket.getLocalPort()+"포트로 연결되었습니다.");
				t.start();
				//fork() or thread()
				//자식 process/thread는 serverSocket.close()
				//handler = new ClientHandler(socket);//이거 잘 실행되면
				//handler = new ClientHandler(serverSocket.accept()); 해볼까
				//close(servSock)이건 왜하지? 과제#6에는 이렇게 하라고 나와있던데
				//자식 process/thread는 exit(0);
				System.out.println("@?");
				
				
			}
		}catch(IOException e) {
			System.out.println("serverSocket fail().."+e.getMessage());
		}
		finally{
			
			System.out.println("server끝");
		}
	}

}
