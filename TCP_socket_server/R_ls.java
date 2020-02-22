import java.io.File;
import java.util.ArrayList;

public class R_ls {
	
	private String MyPath;
	private File path;
	private File[] fileList;
	private int fileListLength;
	public R_ls(){
		MyPath = System.getProperty("user.dir");
		path = new File(MyPath);
		fileList = path.listFiles();
		fileListLength = fileList.length;
	}
	
	public void getPath(){
		System.out.println("위치 : "+MyPath);
	}
	public int getLength() {
		return fileListLength;
	}
	
	public ArrayList<String> getFiles() {
		
		//System.out.println("파일 수 : "+fileList);
		
		if(fileList.length < 1) {
			System.out.println("아무 파일이 없습니다.");
			return null;
		}
		
		ArrayList<String> fileName = new ArrayList<String>();
		for(int i=0;i<fileList.length;i++) {
			fileName.add(fileList[i].getName());
		}
		return fileName;
	}
	public void showFiles() {
		
		
		
		if(fileList.length < 1) {
			System.out.println("아무 파일이 없습니다.");
			return;
		}
		
		for(int i=0;i<fileList.length;i++) {
			System.out.println(fileList[i].getName());
		}
	}
}
