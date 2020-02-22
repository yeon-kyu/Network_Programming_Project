import java.io.File;
import java.util.ArrayList;

public class C_ls {
	
	private String MyPath;
	
	C_ls(){
		MyPath = System.getProperty("user.dir");
	}
	
	public void getPath(){
		System.out.println("위치 : "+MyPath);
	}
	
	public int fileLength() {
		File path = new File(MyPath);
		File[] fileList = path.listFiles();
		return fileList.length;
	}
	
	public ArrayList<String> getFiles() {
		
		File path = new File(MyPath);
		File[] fileList = path.listFiles();
		ArrayList<String> fileNames = new ArrayList<String>();
		
		//System.out.println("파일 수 : "+fileList);
		
		if(fileList.length < 1) {
			fileNames.add("System : no file");
		}
		else {
			for(int i=0;i<fileList.length;i++) {
				fileNames.add(fileList[i].getName());
			}
		}
		return fileNames;
	}
	public void showFiles() {
		File path = new File(MyPath);
		File[] fileList = path.listFiles();
		
		
		if(fileList.length < 1) {
			System.out.println("아무 파일이 없습니다.");
			return;
		}
		
		for(int i=0;i<fileList.length;i++) {
			System.out.println(fileList[i].getName());
		}
	}
}
