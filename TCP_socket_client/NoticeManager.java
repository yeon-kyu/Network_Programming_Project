import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class NoticeManager {

	OutputStream os;
	InputStream is;
	BufferedWriter w;
	BufferedReader r;
	
	NoticeManager(Socket socket) throws IOException{
		os = socket.getOutputStream();
		is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		r = new BufferedReader(new InputStreamReader(is));
	}
	
	public void write(String str) throws IOException {
		w.write(str+"\n"); 
		w.flush();
	}
	public String read() throws IOException{
		String str;
		str = r.readLine();
		return str;
	}


	
}
