<!DOCTYPE html>
<html>
<head>
    <title>Мои сообщения</title>
</head>
<body>
<h2>Мои сообщения</h2>
<a href="/chat">Назад в чат</a>
<ul>
    <#list messages as msg>
        <li>
            [${msg.sentAt}] ${msg.content}
            <form action="/chat/${msg.id}/delete" method="post" style="display:inline;">
                <button type="submit">Удалить</button>
            </form>
        </li>
    <#else>
        <p>У вас еще нет сообщений.</p>
    </#list>
</ul>
</body>
</html>