<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
login page
<form action="/login" method="post">
    <!--Я так понимаю,что так нужно было сделать по шаблону.Потом доделать-->
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <!--так нужно делать в каждой форме для протоколов безопасности-->
    <input type = "hidden" name = "_csrf" value = "${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form>
<a href="/registration">Add new user</a>
</@c.page>