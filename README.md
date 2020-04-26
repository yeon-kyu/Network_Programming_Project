# Network_Programming_Project
 TCP socket
 
  </br>
 </br>
 개요 : facebook과 같이 접속한 친구들과 게시글 올리기, 1대1 채팅하기, 파일 업로드/다운로드, 실시간 접속자 확인 등을 하는 프로그램입니다.
 </br>
 올린 게시글은 게시글이 만들어진 이후에 들어온 client에게도 보이도록 설계하였습니다.
  </br>
 </br>
 
<전체적인 시스템 구조>
<div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/structure.JPG">
 </div>
ArrayList에는 최근의 30개의 News의 제목을 저장하기 위한 자료구조이고, </br>
HashMap은 접속한 클라이언트의 ID와 Socket을 저장하기 위한 자료구조입니다. </br>
이를 통해 귓속말을 보내거나 전체에게 공지사항을 전송시킬 수 있습니다.
 </br>
 </br>
 
 <시작할 때 첫 화면>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime1.JPG">
 </div>
 </br>
 </br>
 
 <새로운 버튼의 등장>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime2.JPG">
 </div>
 start버튼을 누르면 그 버튼은 사라지고 위와 같은 화면처럼 새로운 버튼들이 생깁니다.
  </br>
   </br>
   
   
 <모두에게 공지하기 기능>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime3.JPG">
 </div>
  </br>
   </br>
   
 
 <파일 업로드하기>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime4.JPG">
 </div>
  </br>
   </br>
   
 
 <파일 다운로드>
  <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime5.JPG">
 </div>
 </br>
 
 
  </br>
<이미지 파일을 다운로드 받았을때 GUI변화>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime6.JPG">
 </div>
 </br>
  </br>
  
<자신의 파일 목록 확인>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime7.JPG">
 </div>
 클라이언트의 exe파일이 있는 디렉토리 기준으로 어떤 파일이 있는지 확인할 수 있습니다.
 </br>
  </br>
  
<서버의 파일 목록>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime8.JPG">
 </div>
 서버의 exe파일이 있는 디렉토리를 기준으로 어떤 파일이 있는지 확인할 수 있습니다.
  </br>
   </br>

<접속한 모든 사용자들 닉네임 실시간 확인>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime9.JPG">
 </div>
  </br>
   </br>

<귓속말 기능>
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/runtime10.JPG">
 </div>
  </br>
   </br>

<여러 개의 클라이언트 실행시켰을 때 >
 <div>
<img width = "600" src = "https://github.com/yeon-kyu/Network_Programming_Project/blob/master/images/many_clinets.JPG">
 </div>
 20개의 클라이언트를 실행시켰을 때의 화면입니다.  </br>
 이상 없이 잘 작동하는 것을 확인할 수 있습니다.
  </br>
   </br>
[그 외 예외처리]</br>
#1. 시작할 때 start버튼 외의 다른 버튼을 보이지 않게 함으로써 
    start 버튼으로만 시작하도록 강제함 (비정상적 로그인 오류 방지)
    </br></br>
#2. 아이디 안치고 start버튼 클릭 시 “NoName + XXXX”로 아이디 자동생성
</br></br>
#3. 로그인 할 때 아이디 중복일 경우 재입력을 요구
</br></br>
#4. 파일 업로드/ 다운로드 할 때 존재하지 않는 파일일 경우 </br> 
System : file not exist 를 보여주며 재입력을 요구
</br></br>
#5. 귓속말 보낼 때 존재하지 않는 ID일 경우 </br>
The corresponding ID does not exist 를 보여주며 재입력을 요구
</br>
</br>




