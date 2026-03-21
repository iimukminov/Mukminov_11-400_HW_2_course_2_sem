<#include "base.ftl">

<#macro page_head>
    <title><#if note.id??>Редактирование<#else>Создание</#if> заметки</title>
</#macro>

<#macro page_body>
    <main class="container mt-4">
        <h2><#if note.id??>Редактирование<#else>Создание</#if> заметки</h2>

        <form action="<#if note.id??>/notes/${note.id}/edit<#else>/notes/create</#if>" method="POST" style="max-width: 600px;">
            <div class="mb-3">
                <label class="form-label">Заголовок:</label>
                <input type="text" name="title" class="form-control" value="${(note.title)!''}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Содержание:</label>
                <textarea name="content" class="form-control" rows="5" required>${(note.content)!''}</textarea>
            </div>

            <div class="form-check mb-4">
                <input type="checkbox" name="isPublic" value="true" class="form-check-input" id="isPublicCheck" <#if (note.isPublic)!false>checked</#if>>
                <label class="form-check-label" for="isPublicCheck">
                    Сделать публичной
                </label>
            </div>

            <button type="submit" class="btn btn-success">Сохранить</button>
            <a href="/notes" class="btn btn-outline-secondary ms-2">Отмена</a>
        </form>
    </main>
</#macro>

<@display_page/>