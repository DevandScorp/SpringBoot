<#import "parts/common.ftl" as c>
<@c.page>
    <div class="form-row">
        <form method="get" action="/main" class = "form-inline">
            <!--Вот эти скрытые поля нужны только для post-запросов-->
            <input type="text" name="${filter?ifExists}" placeholder="SearchByTag">
            <button type="submit" class = "btn btn-primary ml-2">Найти</button>
        </form>
    </div>
    <#include "parts/messageEdit.ftl"/>
    <#include "parts/messageList.ftl"/>
</@c.page>