<#include "base.ftl">

<#macro page_head>
    <title>Мои заметки</title>
</#macro>

<#macro page_body>
    <main class="container mt-4">
        <h2>Мои заметки</h2>
        <div class="mb-3">
            <a href="/notes/create" class="btn btn-primary">Создать заметку</a>
            <a href="/notes/public" class="btn btn-secondary">Публичные заметки</a>
        </div>
        <hr>

        <#if notes?has_content>
            <ul class="list-group">
                <#list notes as note>
                    <li class="list-group-item">
                        <strong>${note.title}</strong>
                        <span class="badge bg-${note.isPublic?string('success', 'secondary')}">
                            ${note.isPublic?string('Публичная', 'Приватная')}
                        </span>
                        <br>
                        <p class="mt-2 mb-2">${note.content}</p>

                        <div class="d-flex gap-2">
                            <a href="/notes/${note.id}/edit" class="btn btn-sm btn-warning">Редактировать</a>
                            <form action="/notes/${note.id}/delete" method="POST" style="margin: 0;">
                                <button type="submit" class="btn btn-sm btn-danger">Удалить</button>
                            </form>
                        </div>
                    </li>
                </#list>
            </ul>
        <#else>
            <p>У вас пока нет заметок.</p>
        </#if>
    </main>
</#macro>

<@display_page/>