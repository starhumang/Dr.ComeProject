var socket = null;
var reconnectInterval = 5000; // 다시 연결하는 간격 (밀리초)

function connectWebSocket() {
	try {
		// WebSocket 연결
		socket = new WebSocket('ws://localhost:80/echo');

		// 연결이 열리면 호출되는 이벤트 핸들러
		socket.onopen = function (event) {
			console.log('WebSocket 연결이 열렸습니다.');
			// /topic/messages 토픽을 구독합니다.
			// Change this line
			// socket.send('연결 시작');
		};

		// 메시지를 받았을 때 호출되는 이벤트 핸들러
		socket.onmessage = function (event) {
			console.log('onmessage' + event.data);
			let $socketAlert = $('div#socketAlert');
			$socketAlert.css('display', 'block');
			$socketAlert.html(event.data);

			var v_alarmIcon = document.querySelector('#alarmIcon');
			v_alarmIcon.style.display = 'inline';

			// setTimeout(function () {
			// 	$socketAlert.css('display', 'none');
			// }, 2000);
		};

		// 연결이 닫혔을 때 호출되는 이벤트 핸들러
		socket.onclose = function (event) {
			console.log('WebSocket 연결이 닫혔습니다.');
			setTimeout(function () {
				console.log('다시 연결을 시도합니다.');
				connectWebSocket();
			}, reconnectInterval);
		};

		// 에러가 발생했을 때 호출되는 이벤트 핸들러
		socket.onerror = function (error) {
			console.error('WebSocket 연결 에러:', error);
			setTimeout(function () {
				console.log('다시 연결을 시도합니다.');
				connectWebSocket();
			}, reconnectInterval);
		};
	} catch (error) {
		console.error('WebSocket 연결 에러:', error);
	}
}
