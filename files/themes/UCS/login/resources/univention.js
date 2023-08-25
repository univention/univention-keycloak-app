const getCookie = (name) => {
    const value = " " + document.cookie;
    const parts = value.split(" " + name + "=");
    return parts.length < 2 ? undefined : parts.pop().split(";").shift();
};


const setCookie = function (name, value, expiryDays, domain, path, secure) {
    const exdate = new Date();
    exdate.setHours(exdate.getHours() + (typeof expiryDays !== "number" ? 365 : expiryDays) * 24);
    document.cookie =
        name +
        "=" +
        value +
        ";expires=" +
        exdate.toUTCString() +
        ";path=" +
        (path || "/") +
        (domain ? ";domain=" + domain : "") +
        (secure ? ";secure" : "");
};

const disableTabs = function() {
    // disable tabbing for all focusable elements
    // with tabindex -1 and save the original value
    // for tabindex
    const selector = "a[href], button:not(.cookie-banner-button), input, textarea, select, details, [tabindex]:not([tabindex='-1'])"
    const keyboardfocusableElements = [
        ...document.getElementsByClassName("login-pf-page")[0].querySelectorAll(selector),
    ].filter(el => !el.hasAttribute('disabled') && !el.getAttribute('aria-hidden'));
    keyboardfocusableElements.forEach(function(element){
        element.setAttribute("tabindexOrig", element.getAttribute("tabindex"));
        element.setAttribute("tabindex", "-1");
    });
    return keyboardfocusableElements;
};

const enableTabs = function(keyboardfocusableElements) {
    // restore tabindex for elements
    keyboardfocusableElements.forEach(function(element){
        var orig = element.getAttribute("tabindexOrig");
        element.removeAttribute("tabindexOrig");
        if (orig === null) {
            element.removeAttribute("tabindex");
        } else {
            element.setAttribute("tabindex", orig);
        }
    });
};

const cookieBanner = function (metaJson, locale) {
    // check banner settings and show banner
    const cookieData = metaJson.cookieBanner || null;
    const fqdn = metaJson.fqdn || null;
    if (cookieData) {
        const show = cookieData.show || false;
        const cookieName = cookieData.cookie || "univentionCookieSettingsAccepted";
        const title = cookieData.title[locale] || "Cookie Settings";
        const domains = cookieData.domains || [];
        var domain = null;
        const text = cookieData.text[locale] || "We use cookies in order to provide you with certain functions and to be able to guarantee an unrestricted service. By clicking on \"Accept\", you consent to the collection of information on this portal.";
        if (show) {
            document.getElementById("cookie-text").innerHTML = text;
            document.getElementById("cookie-title").innerHTML = title;
            const banner = document.querySelector(".cookie-banner");
            const bannerButton = banner.querySelector("button");
            const hasCookies = getCookie(cookieName);
            var keyboardfocusableElements = [];
            if (domains.length > 0) {
                // return if not at least one of the configured domains
                // matches the current domain, no banner in this case
                if (! domains.some(function(dom) {return document.domain.endsWith(dom);})) {
                    console.log("banner disabled by umc/cookie-banner/domains")
                    return;
                }
                // instead of the current domain use matching domain from
                // umc/cookie-banner/domains for domain in cookie
                domains.some(function(dom) {
                    if (document.domain.endsWith(dom)) {
                        domain = dom;
                        return true;
                    }
                });
            }
            if (!hasCookies) {
                // show the cookie
                banner.classList.remove("hidden");
                keyboardfocusableElements = disableTabs();
                bannerButton.focus();
                bannerButton.addEventListener("click", () => {
                    setCookie(cookieName, "do-not-change-me", "365", domain);
                    banner.remove();
                    enableTabs(keyboardfocusableElements);
                });
            }
        }
    }
};

const displayCookieBanner = function (metaJsonPath, locale) {
    const fetchPromise = fetch(metaJsonPath);
        fetchPromise.then(response => {
        return response.json();
    }).then(metaJson => {
        cookieBanner(metaJson, locale);
    }).catch(function (error) {
        console.log(error);
    });
};
