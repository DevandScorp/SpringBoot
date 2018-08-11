<#import "parts/common.ftl" as c>

<@c.page>
UserEditor
<form action="/user" method = "post">
    <input type = "text" name="username" value="${user.name}"/>
    <#list roles as role>
        <div>
            <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked","")}>${role}</label>
        </div>
    </#list>
    <input type = "hidden" name = "_csrf" value = "${_csrf.token}"/>
    <input type="hidden" value="${user.id}" name = "UserId"/>
    <button type = "submit">Save</button>
</form>
</@c.page>