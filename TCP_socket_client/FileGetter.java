import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FileGetter {

	OutputStream os;
	InputStream is;
	BufferedWriter w;
	BufferedReader r;
	
	FileOutputStream fos;
	
	DataInputStream dis;
	//ByteCasting Caster;
	
	int fileSize;
	
	FileGetter(Socket socket) throws IOException{
		os = socket.getOutputStream();
		is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		r = new BufferedReader(new InputStreamReader(is));
		
		dis = new DataInputStream(is);
		//Caster = new ByteCasting(); //ByteOrdering
		
	}
	
	public void write(String str) throws IOException{
		w.write(str+"\n");
		w.flush();
	}
	public int fileExist(String FILE_NAME) throws IOException {
		w.write(FILE_NAME+"\n"); //FILE_NAME 전송
		w.flush();
		
		//byte[] temp = new byte[4];
		//is.read(temp); //File Size 받음
		//fileSize = Caster.byteArrayToInt(temp);
		fileSize = dis.readInt();
		
		if(fileSize<0) {
			System.out.println("No such file in server");
			return -1;
		}
		return fileSize;
	}
	
	public int get(String FILE_NAME) throws IOException {

		
		fos = new FileOutputStream(FILE_NAME); //나중에 FILE_NAME으로 바꿔야함
		byte[] buffer = new byte[1024];
		
		int readBytes;
		int readFileSize=0;
		//int fileSize = Caster.byteArrayToInt(temp);
		//if(fileSize<0) {
		//	System.out.println("No such file in server");
		//	return -1;
		//}
		while(readFileSize<fileSize){	//File content 받음
			readBytes = is.read(buffer);
			fos.write(buffer,0,readBytes);
			readFileSize+=readBytes;
		}
		
		fos.close();
		return fileSize;
		
	}
	
	
}



//	w.write("g\n");		// MsgType보냄
//	w.flush();
//	System.out.print("FILE_NAME to get from Server -> ");
//	FILE_NAME = sc.nextLine();
//	
//	w.write(FILE_NAME+"\n");	//File Name보냄
//	w.flush();
//	
//	
//	fos = new FileOutputStream("rcved_file(from client.txt)");
//	buffer = new byte[1024];
//	byte[] temp = new byte[4];
//	is.read(temp);				//File Size 받음
//	int readBytes;
//	int readFileSize=0;
//	int fileSize = C.byteArrayToInt(temp);
//	if(fileSize<0) {
//		System.out.println("No such file in server");
//		break;
//	}
//	
//	while(readFileSize<fileSize){	//File content 받음
//		readBytes = is.read(buffer);
//		fos.write(buffer,0,readBytes);
//		readFileSize+=readBytes;
//	}
//	
//	System.out.println("file received completly");
//	fos.close();