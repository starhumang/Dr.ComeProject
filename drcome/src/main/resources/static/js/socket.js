let socket = io("https://www.drcome.store:3000/", {
  cors: { origin: "*" },
});

console.log(socket.connected);

const myPeer = new Peer({
  host: "www.drcome.store",
  port: "445", //PeerJS 서버가 실행되고 있는 포트
  secure: true, // HTTPS를 사용하는 경우 true로 설정
});

const peers = {};
let ROOM_ID = "123";
let myScreen;

navigator.mediaDevices
  .getUserMedia({
    video: {
      width: { ideal: 300 },
      height: { ideal: 450 },
    },
    audio: true,
  })
  .then((stream) => {
    //화면onoff
    myScreen = stream;
    //내화면만들고
    localVideo.srcObject = stream;
    // call 받을 때의 동작
    myPeer.on("call", async (call) => {
      console.log("전화받음");
      // 내 스트림을 상대방에게 전달
      call.answer(stream);
      // 다른 유저의 stream을 받아옴  // 상대방 스트림 내 화면에 표시
      call.on("stream", (userVideoStream) => {
        console.log("두번째 사용자 remote 화면 만들기");
        remoteVideo.srcObject = userVideoStream;
      });
    });

    // 새로운사람 입장
    socket.on("user-connected", async (userId) => {
      //새로운사람 id , 현재 내 스트림
      await connectToNewUser(userId, stream);
    });
  }); ////.then

//다른 사용자가 연결을 종료하면
socket.on("user-disconnected", (userId) => {
  if (peers[userId]) peers[userId].close();
});

//peer 서버와 정상적으로 통신이 된 경우 'open' 이벤트가 발생
myPeer.on("open", (id) => {
  //socket을 통해 서버로 join-room 이벤트를 emit , 이때 ROOM_ID와 자신의 id를 함께 전달,
  console.log("소켓서버 조인..");
  socket.emit("join-room", ROOM_ID, id);
});

async function connectToNewUser(userId, stream) {
  console.log("새로운 사람들어와서 전화검");
  //전화검 새로운사람에게  현재 내 스트림 보냄
  const call = await myPeer.call(userId, stream);

  // 다른 유저의 stream을 받아와서 내 화면에 표시
  call.on("stream", (userVideoStream) => {
    console.log("첫번째 사용자 remote 화면 만들기");
    remoteVideo.srcObject = userVideoStream;
  });

  call.on("close", () => {
    video.remove();
  });

  peers[userId] = call;
}
