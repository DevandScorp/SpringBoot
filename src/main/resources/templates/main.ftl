<#import "parts/common.ftl" as c>
<@c.page>
    <div class="form-row">
        <form method="get" action="/main" class = "form-inline">
            <!--Вот эти скрытые поля нужны только для post-запросов-->
            <input type="text" name="${filter?ifExists}" placeholder="SearchByTag">
            <button type="submit" class = "btn btn-primary ml-2">Найти</button>
        </form>
    </div>
    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new Message
    </a>
    <div class = "collapse <#if message??>show</#if>" id = "collapseExample">
        <div class="form-group mt-3">
        <form method="post" enctype = "multipart/form-data">
            <input type = "hidden" name = "_csrf" value = "${_csrf.token}"/>
            <div class="form-group">
                <input type="text" class = "form-control ${(textError??)?string('is-invalid','')}" value = "<#if message??>${message.text}</#if>" name="text" placeholder="Введите сообщение" />
                <#if textError??>
                <div class="invaild-feedback">
                    ${textError}
                </div>
                </#if>
            </div>

            <div class="form-group"><input type="file" name = "file"/></div>
            <div class="form-group">
                <input type="text" name="tag" placeholder="Тэг">
                <#if tagError??>
                <div class="invaild-feedback">
                    ${tagError}
                </div>
                </#if>
            </div>
            <div class="form-group"><button type="submit">Добавить</button></div>
        </form>
        </div>
    </div>
    <div class="card-columns">
         <#list messages as message>
    <div class="card my-3">
        <#if message.filename??>
        <img src="/img/${message.filename}" class="card-img-top">
        </#if>
        <div class="m-2">
            <span>${message.text}</span>
            <i>${message.tag}</i>
        </div>
        <div class="card-footer text-muted">
            ${message.author}
        </div>
    </div>
         <#else>
    No message
         </#list>
    </div>
</@c.page>