// let socket;

// window.addEventListener("load", function () {
//   socket = io.connect("http://localhost:3000/", {
//     transports: ["websocket"],
//   });
// });

let socket = io('http://localhost:3000/', {
	cors: { origin: '*' },
});

console.log(socket.connected);

const myPeer = new Peer({
	host: 'localhost',
	port: '3001', //PeerJS 서버가 실행되고 있는 포트
});

const peers = {};
let ROOM_ID = '123';

navigator.mediaDevices
	.getUserMedia({
		video: true,
		audio: true,
	})
	.then((stream) => {
		//미디어 스트림을 전달받아 해당 스트림을 비디오에 연결, 내 화면을 화면에추가
		console.log('내화면추가');
		localVideo.srcObject = stream;

		// 내 피어(myPeer) 다른 사용자로부터 call 받을 때의 동작을 정의
		myPeer.on('call', async (call) => {
			// 전화를 받았을 때 내 비디오 및 오디오 스트림을 상대방에게 전달
			console.log('전화받음');
			call.answer(stream);

			// answer가 발생하면 'stream' 이라는 이벤트를 통해 다른 유저의 stream을 받아옴
			// 상대방의 비디오 스트림을 수신할 때 실행되는 콜백함수 정의
			call.on('stream', (userVideoStream) => {
				// 상대방의 비디오 스트림을 새로운 비디오 요소에 추가하여 나의 화면에 표시
				console.log('stream이벤트 발생  다른유저의 stream받아옴 ');
				remoteVideo.srcObject = userVideoStream;
			});
		});

		// 다른 사용자가 방에 연결되었을 때 / user-connected 이벤트가 발생했을 때, 실행되는 콜백함수,
		// 새로운 사용자의 id를 전달 받는다 (userId)
		socket.on('user-connected', async (userId) => {
			// 해당 사용자와의 연결을 설정하고 스트림을 전달하는 작업
			// connectToNewUser 함수 실행
			console.log('user-connected소켓');
			await connectToNewUser(userId, stream);
		});
	}); ////.then

//다른 사용자가 연결을 종료하면 호출되는 이벤트 핸들러
//다른 peer의 stream을 close 시킴
socket.on('user-disconnected', (userId) => {
	console.log('다른사용자가 연결 종료');
	if (peers[userId]) peers[userId].close();
});

//peer 서버와 정상적으로 통신이 된 경우 'open' 이벤트가 발생
//이어서 Peer 객체의 ID를 생성하고, 해당 ID로 Peer가 서버에 등록
//이 과정에서 Peer 객체가 서버와 연결될 때 open 이벤트가 자동으로 발생
// id = 자신의 peerID
myPeer.on('open', (id) => {
	console.log('피어실행');
	//socket을 통해 서버로 join-room 이벤트를 emit , 이때 ROOM_ID와 자신의 id를 함께 전달,
	//서버가 해당 Peer를 특정 방에 연결하고, 다른 사용자들과의 연결을 관리할 수 있도록 하는 역할
	//서버는 이 이벤트를 수신하여 클라이언트가 특정 방에 참여하고자 함을 이해
	socket.emit('join-room', ROOM_ID, id);
	console.log('방번호:', ROOM_ID + '아이디:' + id);
	console.log('socket.emit join-room ');
});

//mypeer.가 다른 사용자에게 통화를 걸어 자신의 비디오 스트림을 전달하는 과정.
//새로운 사용자가 방에 연결되었을때 실행되는 함수
//새로운 사용자와 peer연결 설정,
//사용자id와 사용자의 스트림을 매개변수로 받음
async function connectToNewUser(userId, stream) {
	const call = await myPeer.call(userId, stream);
	console.log('내가전화검');

	call.on('stream', (userVideoStream) => {
		remoteVideo.srcObject = userVideoStream;
		console.log('상대방스트림 화면에 추가');
	});

	call.on('close', () => {
		video.remove();
	});

	peers[userId] = call;
}
