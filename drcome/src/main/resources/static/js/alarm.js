var alarmSocket = null;

function connectWebSocket(uid) {
	try {
		// WebSocket 연결
		alarmSocket = new WebSocket('wss://www.drcome.store/echo');
		
		// 연결이 열리면 호출되는 이벤트 핸들러
		alarmSocket.onopen = function (event) {
			console.log('WebSocket 연결이 열렸습니다.');
			//alarmSocket.send(uid);
		};

		// 메시지를 받았을 때 호출되는 이벤트 핸들러
		alarmSocket.onmessage = function (event) {
			console.log('onmessage' + event.data);
			// let $socketAlert = $('div#socketAlert');
			// $socketAlert.css('display', 'block');
			// $socketAlert.html(event.data);
			// setTimeout(function () {
			// 	$socketAlert.css('display', 'none');
			// }, 5000);
			let $socketAlert = $('div#socketAlert');

			let $newMessageDiv = $('<div>').text(event.data);

			$socketAlert.append('<div style="margin-top: 10px;"></div>');

			$socketAlert.append($newMessageDiv);

			$socketAlert.css('display', 'block');
		};

		// 연결이 닫혔을 때 호출되는 이벤트 핸들러
		alarmSocket.onclose = function (event) {
			console.log('WebSocket 연결이 닫혔습니다.');
			setTimeout(function () {
				console.log('다시 연결을 시도합니다.');
				connectWebSocket();
			}, 5000);
		};

		// 에러가 발생했을 때 호출되는 이벤트 핸들러
		alarmSocket.onerror = function (error) {
			console.error('WebSocket 연결 에러:', error);
		};
	} catch (error) {
		console.error('WebSocket 연결 에러:', error);
	}
}
