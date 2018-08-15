<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Message Editor
</a>
    <div class = "collapse <#if message??>show</#if>" id = "collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype = "multipart/form-data">
                <input type = "hidden" name = "_csrf" value = "${_csrf.token}"/>
                <!--id-шник сообщения в скрытом поле-->
                <input type = "hidden" name = "id" value = "<#if message??>${message.id}</#if>"/>
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
                <div class="form-group"><button type="submit">Save</button></div>
            </form>
        </div>
    </div>