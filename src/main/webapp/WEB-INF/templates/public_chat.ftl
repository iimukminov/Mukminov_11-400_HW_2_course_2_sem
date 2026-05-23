<!DOCTYPE html>
<html>
<head>
    <title>Public Chat History</title>
</head>
<body>
<h2>История сообщений чата (Публичный доступ)</h2>
<a href="/chat">Войти в чат</a>

<div style="border: 1px solid #ccc; padding: 10px;">
    <#list messages as msg>
        <p><b>${msg.author.name}</b> [${msg.sentAt}]: ${msg.content}</p>
    <#else>
        <p>Сообщений пока нет.</p>
    </#list>
</div>
</body>
</html>