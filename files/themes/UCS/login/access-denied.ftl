<#import "template.ftl" as layout>
<@layout.registrationLayout displayInfo=true; section>
    <#if section = "header">
        ${kcSanitize(msg("accessDeniedMsg", clientDisplayName))}
    <#elseif section = "form">
<@layout.registrationLayout displayInfo=true; section>
    <#if section = "header">
        ${kcSanitize(msg("accessDeniedMsg", clientDisplayName))?no_esc}
    <#elseif section = "form">
        <p class="ucs-p">${kcSanitize(msg("accessDeniedMsg", clientDisplayName))?no_esc}</p>
    </#if>
</@layout.registrationLayout>