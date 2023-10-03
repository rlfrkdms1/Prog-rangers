const eventSource = new EventSource('http://13.124.131.171:8080/api/v1/notifications/subscribe');

eventSource.addEventListener('message', function(event) {
    // 이벤트 처리 로직 구현
    const eventData = JSON.parse(event.data);
    // 알림을 화면에 표시하거나 다른 작업 수행
  });


  

  window.addEventListener('scroll', function() {
    // 스크롤 이벤트 처리 로직 구현
    if (shouldSubscribeToNotifications()) {
      subscribeToNotifications();
    }
  });

  function shouldSubscribeToNotifications() {
    const windowHeight = window.innerHeight;
    const scrollY = window.scrollY;
    const documentHeight = document.documentElement.scrollHeight;
  
    // 페이지 하단에 도달하면 true를 반환하여 알림을 구독
    return windowHeight + scrollY >= documentHeight;
  }

  function subscribeToNotifications() {
    const eventSource = new EventSource('/sse-endpoint');
  
    eventSource.addEventListener('message', function(event) {
      // 알림을 화면에 표시하거나 다른 작업 수행
    });
  
    eventSource.addEventListener('error', function(event) {
      // 연결 오류 처리 및 다시 연결 로직 구현
    });
  }
