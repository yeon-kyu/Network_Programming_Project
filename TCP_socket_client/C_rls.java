import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class C_rls {

	OutputStream os;
	InputStream is;
	BufferedWriter w;
	BufferedReader r;

	DataInputStream dis;
	//ByteCasting Caster;
	
	ArrayList<String> fileNames;
	int fileLength;
	
	C_rls(Socket socket) throws IOException{
		os = socket.getOutputStream();
		is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		r = new BufferedReader(new InputStreamReader(is));

		dis = new DataInputStream(is);
		
		//Caster = new ByteCasting(); //ByteOrdering
		
		fileNames = new ArrayList<String>();
	}
	
	
	public void write(String str) throws IOException{
		w.write(str+"\n");
		w.flush();
	}
	public int getLength() throws IOException {
		//byte[] temp = new byte[4];
		//is.read(temp);		//파일 수 받음
		//fileLength = Caster.byteArrayToInt(temp);
		fileLength = dis.readInt();
		if(fileLength<1) {
			System.out.println("no file in server");
			return -1;
		}
		else return fileLength;
	}
	
	public ArrayList<String> getNames() throws IOException {
		
		for(int i=0;i<fileLength;i++) {
			String lsSample = r.readLine();
			//B.addText(lsSample);
			System.out.println(lsSample);
			fileNames.add(lsSample);
		}
		w.flush();
		return fileNames;
	}

}

