<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
<span><a href="/user">UserList</a></span>
    <div>
        <@l.logout ></@l.logout>
    </div>
    <div class="text-center">${sometext}</div>
    <div>
        <form method="post" enctype = "multipart/form-data">
            <input type = "hidden" name = "_csrf" value = "${_csrf.token}"/>
            <input type="text" name="text" placeholder="Введите сообщение" />
            <input type="file" name = "file"/>
            <input type="text" name="tag" placeholder="Тэг">
            <button type="submit">Добавить</button>
        </form>
    </div>
    <form method="get" action="/main">
        <!--Вот эти скрытые поля нужны только для post-запросов-->
        <input type="text" name="${filter?ifExists}">
        <button type="submit">Найти</button>
    </form>
    <div>Список сообщений</div>
    <#list messages as message>
        <b>${message.id}</b>
        <span>${message.text}</span>
        <i>${message.tag}</i>
        <strong>${message.author}</strong>
        <div>
            <#if message.filename??>
                <img src="/img/${message.filename}" alt="">
            </#if>
        </div>
    <#else >
    No message
    </#list>
</@c.page>