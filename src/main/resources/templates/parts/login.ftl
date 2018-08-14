<#macro login path >
<#--https://www.youtube.com/watch?v=a51jGwoTNmI&t=6s&list=PLU2ftbIeotGpAYRP9Iv2KLIwK36-o_qYk&index=11
Тут он добавлял еще кнопку высвечивания ссылк AddUser ,если это форма регистрации-->
<form action="${path}" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">User Name :</label>
        <div class="col-sm-6">
            <input type="text" name="name" class="form-control" placeholder="User name" />
            <#if nameError??>
                <div class="invaild-feedback">
                    ${nameError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Password:</label>
        <div class="col-sm-6">
            <input type="password" name="password" class="form-control" placeholder="Password" />
            <#if passwordError??>
                <div class="invaild-feedback">
                    ${password2Error}
                </div>
            </#if>
            <#if samePasswordError??>
                <div class="invaild-feedback">
                    ${samePasswordError}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Confirm password:</label>
        <div class="col-sm-6">
            <input type="password" name="password2" class="form-control" placeholder="Confirm password" />
            <#if password2Error??>
                <div class="invaild-feedback">
                    ${password2Error}
                </div>
            </#if>
        </div>
    </div>
    <div class="form-group row">
        <label class="col-sm-2 col-form-label">Email:</label>
        <div class="col-sm-6">
            <input type="email" name="email" class="form-control" placeholder="some@some.com" />
            <#if emailError??>
                <div class="invaild-feedback">
                    ${emailError}
                </div>
            </#if>
        </div>
    </div>
    <div>
    <div class="g-recaptcha" data-sitekey="6LdW7GkUAAAAAJ1qmQ3ROtO-PfLShvWllc_ePr77"></div>
        <#if captchaError??>
                <div class="alert alert-danger">
                    ${captchaError}
                </div>
        </#if>
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