<#import "template.ftl" as layout>
<@layout.registrationLayout displayMessage=false; section>
    <#if section = "header">
        ${kcSanitize(msg("errorTitle"))?no_esc}
    <#elseif section = "form">
        <div id="kc-error-message">
            <div class="alert-${message.type} ${properties.kcAlertClass!} pf-m-<#if message.type = 'error'>danger<#else>${message.type}</#if>">
                <div class="pf-c-alert__icon">
                    <#if message.type = 'success'><span class="${properties.kcFeedbackSuccessIcon!}"></span></#if>
                    <#if message.type = 'warning'><span class="${properties.kcFeedbackWarningIcon!}"></span></#if>
                    <#if message.type = 'error'><span class="${properties.kcFeedbackErrorIcon!}"></span></#if>
                    <#if message.type = 'info'><span class="${properties.kcFeedbackInfoIcon!}"></span></#if>
                </div>
                <#if (errorDetail!accountState!"")?has_content && message.summary == msg("accountDisabledMessage")>
                    <span class="${properties.kcAlertTitleClass!}">${kcSanitize(msg(errorDetail!accountState))?no_esc}</span>
                <#else>
                    <span class="${properties.kcAlertTitleClass!}">${kcSanitize(message.summary)?no_esc}</span>
                </#if>
            </div>
            <#if traceId??>
                <p class="instruction" id="traceId">${msg("traceIdSupportMessage", traceId)}</p>
            </#if>
            <#if skipLink??>
            <#elseif url.loginRestartFlowUrl?contains("tab_id=")>
                <p><a id="backToLogin" href="${url.loginRestartFlowUrl}">${msg("backToLogin")?remove_beginning("« ")}</a></p>
            <#elseif client?? && client.baseUrl?has_content>
                <p><a id="backToApplication" href="${client.baseUrl}">${msg("backToApplication")?remove_beginning("« ")}</a></p>
            </#if>
        </div>
    </#if>
</@layout.registrationLayout>
