<#assign known = Session.SPRING_SECURITY_CONTEXT??>
<#--Тут ты объявил переменную,которая будет определять,существует ли сессия-->
<#if known>
    <#--Так мы получаем текущего пользователя из сессии и можем вызывать любые методы из
    этой переменной-->
    <#assign user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
             name = user.getName()
             isAdmin = user.isAdmin()>
    <#else>
    <#assign name = "unknown"
             isAdmin = false>
</#if>