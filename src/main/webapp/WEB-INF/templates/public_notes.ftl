<#include "base.ftl">

<#macro page_head>
    <title>Публичные заметки</title>
</#macro>

<#macro page_body>
    <main class="container mt-4">
        <h2>Публичные заметки</h2>
        <div class="mb-3">
            <a href="/notes" class="btn btn-secondary">Мои заметки</a>
        </div>
        <hr>

        <#if notes?has_content>
            <ul class="list-group">
                <#list notes as note>
                    <li class="list-group-item">
                        <strong>${note.title}</strong> (Автор: ${note.author.name})<br>
                        <p class="mt-2 mb-2">${note.content}</p>
                        <small class="text-muted">Создано: ${(note.createdAt)!"Неизвестно"}</small>
                    </li>
                </#list>
            </ul>
        <#else>
            <p>Нет доступных публичных заметок.</p>
        </#if>
    </main>
</#macro>

<@display_page/>