<#macro login path >
<#--https://www.youtube.com/watch?v=a51jGwoTNmI&t=6s&list=PLU2ftbIeotGpAYRP9Iv2KLIwK36-o_qYk&index=11
Тут он добавлял еще кнопку высвечивания ссылк AddUser ,если это форма регистрации-->
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-6">
            <input type="text" name="name" class="form-control" placeholder="User name" />
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password" />
        </div>
    </div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Create</button>
</form>
</#macro>
<#macro logout>
    <form action="/logout" method="post">
        <button class="btn btn-primary" type="submit">Sign Out</button>
        <input type = "hidden" name = "_csrf" value = "${_csrf.token}"/>
    </form>
</#macro>