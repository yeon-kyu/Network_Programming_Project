//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.Flushable;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.io.OutputStream;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TCPClient extends Frame implements ActionListener {
	
	static final int PORT = 2000;
	static String id;
	C_rls rls; ///////////이번에 새로넣은것. 오류가 나는지 주의깊게 볼것
	FileGetter FG; ///////얘도  마찬가지. 근데 변수로남아있는건 fileSize 같은거 뿐이라 버튼누를때마다 초기화되서 괜찮은듯 
	
	Label news;
	Label Notice;
	Label ID;
	//Label port;
	Label clientUsers;
	Label To,Msg,Messages;
	Label system;
	
	TextField NoticeField,IDField;//portField;
	TextField Who,MsgContent;
	TextField systemField;
	
	TextArea rlsArea,lsArea;
	TextField getField,putField;

	TextArea newsArea,UsersArea;
	TextArea MsgArea;
	
	Button getButton;
	Button putButton;
	Button SReset;
	Button CReset;
	Button startButton;
	Button enrolButton;
	Button WhisperButton;
	
	JLabel label1;
	
	Panel p;
	Socket socket;
	
	
	
	public TCPClient(String ClientGUI) {
		
		super(ClientGUI);
		
		addWindowListener(new WindowHandler());
		
		p = new Panel();
		
		news = new Label("News");
		Notice = new Label("Notice : ");
		NoticeField = new TextField();
		ID = new Label("ID : ");
		IDField = new TextField();
		//port = new Label("port : ");
		//portField = new TextField();
		clientUsers = new Label("Client Users");
		UsersArea = new TextArea(20,30);
		enrolButton = new Button("< post up >");
		To = new Label("to : ");
		Who = new TextField();
		Msg = new Label("msg : ");
		MsgContent = new TextField();
		WhisperButton = new Button("Whisper");
		MsgArea = new TextArea(20,30);
		Messages = new Label("Messages");
		system = new Label("< system >");
		systemField = new TextField();
		
		news.setBounds(80,10,100,30);
		Notice.setBounds(80,370,100,30);
		NoticeField.setBounds(10,400,250,25);
		ID.setBounds(300,440,20,30);
		IDField.setBounds(330,440,120,25);
		//port.setBounds(580,440,30,30);
		//portField.setBounds(620,440,120,25);
		clientUsers.setBounds(970,10,100,30);
		UsersArea.setBounds(900,40,240,320);
		enrolButton.setBounds(10,430,70,30);
		To.setBounds(860,435,30,30);
		Who.setBounds(890,435,100,25);
		Msg.setBounds(845,470,40,30);
		MsgContent.setBounds(890,470,160,25);
		WhisperButton.setBounds(1060,465,60,30);
		MsgArea.setBounds(560,420,220,80);
		Messages.setBounds(560,390,60,30);
		system.setBounds(820,505,70,30);
		systemField.setBounds(890,505,220,25);
		
		newsArea = new TextArea("ID를 입력한 후에 \nstart를 눌러주세요");
		rlsArea = new TextArea(20,30);
		lsArea = new TextArea(20,30);
		newsArea.setBounds(10,40,240,320);
		rlsArea.setBounds(300,160,240,220);
		lsArea.setBounds(600,160,240,220);
		
		getButton = new Button("get -> ");
		putButton = new Button("put <- ");
		getButton.setBounds(400,25,40,30);
		putButton.setBounds(700,25,40,30);
		getButton.addActionListener(this);
		putButton.addActionListener(this);
		startButton = new Button("start");
		startButton.setBounds(470,435,40,30);
		startButton.addActionListener(this);
		
		SReset = new Button("Server_update");
		CReset = new Button("Client_update");
		SReset.setBounds(370,120,100,30);
		CReset.setBounds(670,120,100,30);
		SReset.addActionListener(this); //이거 넣어줘야 버튼이 작동함
		CReset.addActionListener(this);
		enrolButton.addActionListener(this);
		WhisperButton.addActionListener(this);

		getField = new TextField();
		putField = new TextField();
		getField.setBounds(300,70,250,25);
		putField.setBounds(600,70,250,25);

		newsArea.setEditable(false);
		lsArea.setEditable(false);
		rlsArea.setEditable(false);
		UsersArea.setEditable(false);
		MsgArea.setEditable(false);
		systemField.setEditable(false);
		
		label1 = new JLabel();
		//label1.setLocation(1140, 40);
		label1.setBounds(1140,40,240,320);
		label1.setIcon(null);
		
		
		p.setLayout(null);
		
		p.add(newsArea);
		p.add(news);
		p.add(Notice);
		p.add(NoticeField);
		p.add(ID);
		p.add(IDField);
		//p.add(port);
		//p.add(portField);
		p.add(clientUsers);
		p.add(UsersArea);
		
		p.add(SReset);
		p.add(rlsArea);
		p.add(getButton);
		p.add(getField);
		
		p.add(CReset);
		p.add(lsArea);
		p.add(putButton);
		p.add(putField);
		
		p.add(startButton);
		p.add(enrolButton);
		p.add(To);
		p.add(Who);
		p.add(Msg);
		p.add(MsgContent);
		p.add(WhisperButton);
		p.add(MsgArea);
		p.add(Messages);
		p.add(system);
		p.add(systemField);
		p.add(label1);
		add(p);
		
		setSize(1400,600);
		setVisible(true);
		getButton.setVisible(false);
		putButton.setVisible(false);
		SReset.setVisible(false);
		CReset.setVisible(false);
		enrolButton.setVisible(false);
		WhisperButton.setVisible(false);
		
		System.out.println("<Client>");
		Scanner sc = new Scanner(System.in);
		//ByteCasting C = new ByteCasting();
		//MyGUI B = new MyGUI("Client GUI");
		
		try {
			//소켓 생성
			socket = new Socket("localhost",PORT);	
			System.out.println("PORT("+PORT+")로 접속을 시도합니다.");
			NoticeManager NP = new NoticeManager(socket);
			
			while(true) {	
				String MsgType;
				System.out.println("MsgType 대기중..");
				MsgType = NP.read();
				System.out.println("MsgType : "+MsgType);
				if(MsgType.equals("N")) {
					String userSet="";
					userSet = NP.read();
					System.out.println(userSet);
					userSet = userSet.replace(", ","\n");
					userSet = userSet.replace("[", "");
					userSet = userSet.replace("]", "");
					
					UsersArea.setText(userSet);
					
					//시작할 때 서버의 NoticeMsg받아야함
					
					IDField.setEditable(false);
					newsArea.setText(null);
					
					getButton.setVisible(true);
					putButton.setVisible(true);
					SReset.setVisible(true);
					CReset.setVisible(true);
					enrolButton.setVisible(true);
					WhisperButton.setVisible(true);
					startButton.setVisible(false);
				}
				else if(MsgType.equals("Duplicated")) {
					System.out.println("아이디 중복입니다. 다시 입력하세요");
					newsArea.setText("ID 중복입니다. 다시입력하세요");
					continue;
					
				}
				else if(MsgType.equals("AKNAK")) {
					//systemField.setText("message has beend sent");
					String success = NP.read();
					if(success.equals("AK")) {
						systemField.setText("message has beend sent");
						MsgContent.setText("");
					}
					else {
						systemField.setText("The corresponding ID does not exist");
					}
				}
				else if(MsgType.equals("w")) { //다른 클라이언트로부터 whisper가 왔을 때
					//Messages 창에 띄워야지
					String senderID = NP.read();//senderID
					String MyMessage = NP.read();//Message
					//둘이 합쳐서 출력
					MsgArea.append(senderID+"> "+MyMessage+"\n");
				}
				else if(MsgType.equals("Notice")) { //어떤 클라이언트가 무언가를 put또는 Notice했을 때(post)
					newsArea.setText("");
					String newsSize = NP.read(); //news_size받음
					System.out.println("newsSize : "+newsSize);
					
					int news_size = Integer.parseInt(newsSize);
					
					for(int i=0;i<news_size;i++) {
						String temp = NP.read();
						newsArea.append(temp+"\n");
						//System.out.print(temp+" ");
					}
					
					
				}
				else if(MsgType.equals("r")) {
					
					ArrayList<String> fileNames;
					int success = rls.getLength();	// fileLength(파일 수) 받음
					
					System.out.println("server의 file수는 : "+success);
					if(success<0) 
						rlsArea.append("system : no file in server\n");
					else {
						fileNames = rls.getNames(); //fileNames 받음
						for(int i=0;i<success;i++) {
							rlsArea.append(fileNames.remove(0)+"\n");
						}	
					}
					System.out.println("rls complete");
					
				}
				else if(MsgType.equals("g")) {
					System.out.print("FILE_NAME to get from Server -> ");
					String FILE_NAME = getField.getText();
					
					//파일이름을 보내고 먼저 존재하는지 검사해야함
					if(FG.fileExist(FILE_NAME)<0) {		//FILE_NAME을 보내고 fileSize를 받음
						getField.setText("File not exist");
					}
					else {
						// FILE Content 받음
						int success = FG.get(FILE_NAME);  //success는 받은 파일의 크기
						if(success<0) {
							getField.setText("File not exist");
						}
						else {
							getField.setText(FILE_NAME+"다운로드 성공 ("+success+"bytes)");
						}
						int strlen = FILE_NAME.length();

						//System.out.println("dd" + FILE_NAME.substring(strlen-4));
						if(FILE_NAME.substring(strlen-4).equals(".jpg")) {
						
							String MyPath;
							MyPath = System.getProperty("user.dir");
							System.out.println(MyPath);
							Image image1=null;
							Image image2=null;
						    try {
						    //	File sourceimage = new File("c:\\Users/user\Desktop\java workspace\연습");
						    	
						    	File sourceimage = new File(MyPath+"//"+FILE_NAME);
								image1 = ImageIO.read(sourceimage);		
								image2 = image1.getScaledInstance(240,320,java.awt.Image.SCALE_SMOOTH);
								label1.setIcon(new ImageIcon(image2));
								
								//pack();
//								add(label1);
//								setVisible(true);
						    } catch (IOException e) {
								e.printStackTrace();
							}
						}
						else if(FILE_NAME.substring(strlen-4).equals(".png")) {
						
							String MyPath;
							MyPath = System.getProperty("user.dir");
							System.out.println(MyPath);
							Image image1=null;
							Image image2=null;
						    try {
						    //	File sourceimage = new File("c:\\Users/user\Desktop\java workspace\연습");
						    	
						    	File sourceimage = new File(MyPath+"//"+FILE_NAME);
								image1 = ImageIO.read(sourceimage);		
								image2 = image1.getScaledInstance(240,320,java.awt.Image.SCALE_SMOOTH);
								label1.setIcon(new ImageIcon(image2));
								
						    } catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				
				else if(MsgType.equals("e")) {
					break;
				}			
			}
			
			socket.close();
			
			System.out.println("client 종료");
			
			System.exit(0);
		}catch(Exception e) {
			System.out.println("에러 발생 "+e.getMessage());
			//e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) { //이건 버튼 눌렀을 떄!!!!!
		// TODO Auto-generated method stub
		String name;
		name = e.getActionCommand();
		
		String FILE_NAME;
		
		if(name.equals("Server_update")) {// execute rls
			rlsArea.setText(null);
			try {
				rls = new C_rls(socket);
				rls.write("/FTP");	// /FTP임을 보냄
				rls.write("r");		//MsgType 보냄
										
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(name.equals("Client_update")) { //execute ls
			lsArea.setText(null);
			
			C_ls ls = new C_ls();
			ArrayList<String> fileNames = new ArrayList<String>();
			fileNames = ls.getFiles();
			int fileLength = ls.fileLength();
			for(int i=0;i<fileLength;i++) {
				lsArea.append(fileNames.remove(0)+"\n");
			}
		}
		else if(name.equals("get -> ")) { //execute get
					
			try {
				FG = new FileGetter(socket);
				
				FG.write("/FTP");
				FG.write("g");  //MsgType 보냄
							
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(name.equals("put <- ")) {//execute put
			FILE_NAME = putField.getText();
			
			FilePutter FP;
			try {
				FP = new FilePutter(socket);
				
				if(FP.fileExist(FILE_NAME)<0) { //일단 파일이 있는지 확인해야함
					putField.setText("System : File not exist");
				}
				else {
					FP.write("/FTP"); //FTP임을 보냄
					FP.write("p"); //MsgType 보냄
					
					FP.write(id);
					FP.write(FILE_NAME); //FILE NAME 보냄
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					
					int size = FP.putSize(FILE_NAME);
					System.out.println("보낸 파일의 크기 : "+size);
					int success = FP.put(FILE_NAME);  //File 크기와 File content 보냄
					if(success<0) //success 는 보낸 파일의 크기
						putField.setText("System : File not exist");
					else {
						putField.setText(FILE_NAME+"업로드 성공 ("+success+"bytes)");
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(name.equals("start")){
						
			FilePutter FP;  //여기 6줄 없으면 안됨. 필요없어보여도 없으면 rls->put할때 에러남
			try {
				FP = new FilePutter(socket);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
				
			id = IDField.getText();
			if(id.equals("")) {
				id="NoName"+(int)(Math.random()*10000);
				IDField.setText(id);
			}
		
			try {
				FP=new FilePutter(socket);
				FP.write(id);	
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		else if(name.equals("Whisper")) {
			System.out.println("whisper..");
			String partnerID = Who.getText();
			String MyMessage = MsgContent.getText();
			try {
				FilePutter FP = new FilePutter(socket);
				FP.write("w"); //MsgType 보냄
				FP.write(id); //내 ID 보냄
				FP.write(partnerID); // 상대방 ID 보냄
				FP.write(MyMessage); //msg 보냄    ..이제 다시 receive하는건 위의 생성자 쪽에서 할거임
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		else if(name.equals("< post up >")) {
			//System.out.println("post up~");
			String NoticeMsg = NoticeField.getText();
			System.out.println("post<- "+NoticeMsg);
			try {
				FilePutter FP = new FilePutter(socket);
				FP.write("Notice"); //MsgType보냄
				FP.write(id); //내 ID보냄
				FP.write(NoticeMsg); //NoticeMsg보냄 ..이제 받는건 위의 생성자쪽에서!
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
}
