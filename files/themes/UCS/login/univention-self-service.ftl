<#import "template.ftl" as layout>
<@layout.registrationLayout; section>
    <#if section = "header">
	${msg("accountNotVerifiedMsg")?no_esc}
    <#elseif section = "form">
			<form id="univention-self-service-form" class="${properties.kcFormClass!}" action="${url.loginAction}" method="post">
				<div class="${properties.kcFormGroupClass!}">
					<div class="${properties.kcLabelWrapperClass!}">
						<p class="ucs-p">${msg("accountNotVerifiedMsg")?no_esc}</p>
					</div>
				</div>
				<div class="${properties.kcFormGroupClass!}">
					<div id="kc-form-buttons" class="${properties.kcFormButtonsClass!}">
						<input class="${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!} ${properties.kcButtonBlockClass!} ${properties.kcButtonLargeClass!}" type="submit" value="${msg("tryAgain")}"/>
					</div>
				</div>
			</form>
    </#if>
</@layout.registrationLayout>
