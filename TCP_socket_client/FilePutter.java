import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class FilePutter {

	OutputStream os;
	//InputStream is;
	BufferedWriter w;
	//BufferedReader r;
	
	FileInputStream fis;
	
	DataOutputStream dos;
	
	//ByteCasting Caster;
	int fileSize;
	
	FilePutter(Socket socket) throws IOException{
		os = socket.getOutputStream();
		//is = socket.getInputStream();
		w = new BufferedWriter(new OutputStreamWriter(os));
		//r = new BufferedReader(new InputStreamReader(is));
		dos = new DataOutputStream(os);
		//Caster = new ByteCasting(); //ByteOrdering
	}
	
	public void write(String str) throws IOException {
		w.write(str+"\n");		//MsgType 보냄
		w.flush();
	}
	
	public int fileExist(String FILE_NAME) {
		
		File file = new File(FILE_NAME);
		if(!file.exists()) {
			return -1;
		}
		
		return 1;
	}
	public int putSize(String FILE_NAME) throws IOException {
		
		File file = new File(FILE_NAME);
		
		if(!file.exists()) {
			System.out.println("File not Exist");
			fileSize = -1;
			//os.write(Caster.intToByteArray(fileSize));
			dos.writeInt(fileSize);
			return -1;//파일 못찾으면 -1 반환
		}
		fileSize = (int) file.length();
		//os.write(Caster.intToByteArray(fileSize));	//file Size 보냄
		dos.writeInt(fileSize);
		System.out.println("");
//		System.out.println("보낸 byte[4] : "+Caster.intToByteArray(fileSize));
//		System.out.println("보낸 byte[4] : "+Caster.intToByteArray(fileSize));
//		System.out.println("보낸 int : "+Caster.byteArrayToInt(Caster.intToByteArray(fileSize)));
//		System.out.println("보낸 int : "+Caster.byteArrayToInt(Caster.intToByteArray(fileSize)));
		
		return fileSize;
	}
	
	public int put(String FILE_NAME) throws IOException { //보낸 파일의 크기 반환
		
		File file = new File(FILE_NAME);
		
		if(!file.exists()) {
			System.out.println("File not Exist");
			fileSize = -1;
			//os.write(Caster.intToByteArray(fileSize));
			dos.writeInt(fileSize);
			return -1;//파일 못찾으면 -1 반환
		}
		
		fis = new FileInputStream(file);

		long totalReadBytes = 0;
		int readBytes;
		byte[] buffer = new byte[1024];
		System.out.println("Sending...");
		while((readBytes = fis.read(buffer))>0) {
			os.write(buffer,0,readBytes);	// file buffer전송
			totalReadBytes +=readBytes;
			System.out.println("progress : "+((totalReadBytes*100)/fileSize)+"%");
		}
		
		fis.close();
		os.flush();
		return fileSize;
	}
	
//	w.write("p\n");	//한줄 더보냄	MsgType
//	w.flush();
//	//os.write("p".getBytes());
//	System.out.print("FILE_NAME to put to Server -> ");
//	FILE_NAME = sc.nextLine();
//	
//	File file = new File(FILE_NAME);
//	if(!file.exists()) {
//		System.out.println("File not Exist");
//		int fileSize = -1;
//		os.write(C.intToByteArray(fileSize));
//		break;
//	}
//	fis = new FileInputStream(file);
//	
//	int fileSize;
//	fileSize = (int) file.length();
//	
//	os.write(C.intToByteArray(fileSize));	//file Size 보냄
//	
//	long totalReadBytes = 0;
//	int readBytes;
//	buffer = new byte[1024];
//	while((readBytes = fis.read(buffer))>0) {
//		os.write(buffer,0,readBytes);	//전송
//		totalReadBytes +=readBytes;
//		System.out.println("progress : "+((totalReadBytes*100)/fileSize)+"%");
//	}
//	
//	System.out.println("file sended completly");
//	
//	fis.close();
//	os.flush();
	
}
