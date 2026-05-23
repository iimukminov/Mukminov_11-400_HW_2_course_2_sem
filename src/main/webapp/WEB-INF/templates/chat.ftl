<!DOCTYPE html>
<html>
<head>
    <title>Live Chat</title>
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
</head>
<body>
<h2>Общий чат</h2>
<a href="/chat/my">Мои сообщения</a>

<div id="chat-box" style="border: 1px solid #ccc; height: 300px; overflow-y: scroll; padding: 10px; margin-bottom: 10px;">
    <#list messages as msg>
        <p><b>${msg.author.name} ${msg.author.lastname}</b> [${msg.sentAt}]: ${msg.content}</p>
    </#list>
</div>

<input type="text" id="message-input" placeholder="Введите сообщение..." />
<button onclick="sendMessage()">Отправить</button>

<script>
    var stompClient = null;

    function connect() {
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            stompClient.subscribe('/topic/messages', function (messageOutput) {
                var message = JSON.parse(messageOutput.body);
                showMessage(message);
            });
        });
    }

    function sendMessage() {
        var content = document.getElementById('message-input').value;
        if (content.trim() !== "") {
            // Отправляем просто как строку. Spring поймет, так как в контроллере String content
            stompClient.send("/app/send", {}, content);
            document.getElementById('message-input').value = '';
        }
    }

    function showMessage(message) {
        var chatBox = document.getElementById('chat-box');
        var p = document.createElement('p');
        p.innerHTML = "<b>" + message.authorName + "</b> [" + message.sentAt + "]: " + message.content;
        chatBox.appendChild(p);
        chatBox.scrollTop = chatBox.scrollHeight;
    }

    window.onload = connect;
</script>
</body>
</html>