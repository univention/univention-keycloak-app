<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=!messagesPerField.existsError('password','password-confirm'); section>
    <#if section = "header">
        ${msg("updatePasswordTitle")}
    <#elseif section = "form">
        <form id="kc-passwd-update-form" action="${url.loginAction}" method="post">
            <div class="${properties.kcFormGroupClass!}">
                <p class="ucs-p">${msg("updatePasswordTitle")}</p>
            </div>
            <input type="text" id="username" name="username" value="${username}" autocomplete="username"
                   readonly="readonly" style="display:none;"/>

            <div class="${properties.kcFormGroupClass!}">
                <input type="password" id="password" placeholder="" name="password" class="${properties.kcInputClass!}"
                       autofocus autocomplete="current-password"
                       aria-invalid="<#if messagesPerField.existsError('password')>true</#if>"
                />
                <label for="password" class="${properties.kcLabelClass!}">${msg("password")}</label>
            </div>
            <div class="${properties.kcFormGroupClass!}">
                <input type="password" id="password-new" placeholder="" name="password-new" class="${properties.kcInputClass!}"
                       autofocus autocomplete="new-password"
                       aria-invalid="<#if messagesPerField.existsError('password-new','password-confirm')>true</#if>"
                />
                <label for="password-new" class="${properties.kcLabelClass!}">${msg("passwordNew")}</label>
            </div>

            <div class="${properties.kcFormGroupClass!}">
                <input type="password" id="password-confirm" placeholder="" name="password-confirm"
                       class="${properties.kcInputClass!}"
                       autocomplete="new-password"
                       aria-invalid="<#if messagesPerField.existsError('password-confirm')>true</#if>"
                />
                <label for="password-confirm" class="${properties.kcLabelClass!}">${msg("passwordConfirm")}</label>
            </div>

            <div class="${properties.kcFormGroupClass!}">
                <div id="kc-form-options" class="${properties.kcFormOptionsClass!}">
                    <div class="${properties.kcFormOptionsWrapperClass!}">
                        <#if isAppInitiatedAction??>
                            <div class="checkbox">
                                <label><input type="checkbox" id="logout-sessions" name="logout-sessions" value="on" checked> ${msg("logoutOtherSessions")}</label>
                            </div>
                        </#if>
                    </div>
                </div>

                <div id="kc-form-buttons" class="${properties.kcFormGroupClass!}">
                    <#if isAppInitiatedAction??>
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doSubmit")}" />
                        <button class="${properties.kcButtonClass!} ${properties.kcButtonDefaultClass!} ${properties.kcButtonLargeClass!}" type="submit" name="cancel-aia" value="true" />${msg("doCancel")}</button>
                    <#else>
                        <input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("doSubmit")}" />
                    </#if>
                </div>
            </div>
        </form>
    </#if>
</@layout.registrationLayout>
