<#import "parts/common.ftl" as c>

<@c.page>
<h1>List of users</h1>
<#list users as user>
    ${user.id} <br>
    ${user.name} <br>
    Roles <br>
    <#list user.roles as role>
        ${role} <br>
    </#list>
    <a href="/user/${user.id}">edit</a>
    <br>
</#list>
</@c.page>