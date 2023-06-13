/**
 * 페이지 이동 ('뒤로가기'로 현재 페이지 이동 가능)
 * @param url
 */
let fnLocationHref = (url) => {
    location.href = url;
}

let fnIsEmptyWithMessageAndFocus = (name, label) => {
    const message = `${label} 값을 입력해 주세요.`;
    if (fnIsEmpty(name)) {
        alert(message);
        fnMoveFocus(name);
        return true;
    }

    return false;
}

let fnIsEmpty =  (name) => {
    let isEmpty = true;
    let targetElements = document.getElementsByName(name);

    if (targetElements[0].type === "radio"
        || targetElements[0].type === "checkbox") {
        for (let targetElement of targetElements) {
            if (targetElement.checked) {
                isEmpty = false;
                break;
            }
        }
    } else {
        isEmpty = targetElements[0].value.trim().length === 0;
    }

    return isEmpty;
}

let fnMoveFocus  = (name) => {
    let targetElement = document.getElementsByName(name);
    targetElement.focus();
}

const cookieUtil = {
    fnGetCookie: function (name) {
        let matches = document.cookie.match(new RegExp(
            "(?:^|; )" + name.replace(/([.$?*|{}()\[\]\\\/+^])/g, "\\$1")
            + "=([^;]*)"));
        return matches ? decodeURIComponent(matches[1]) : undefined;
    },

    fnDeleteCookie: function (name) {
        cookieUtil.fnSetCookie(name, "", {
            "max-age": -1
        })
    },

    fnSetCookie: function (name, value, options = {}) {

        options = {
            path: "/", // 필요한 경우, 호출 시 옵션 설정
            ...options
        };

        if (options.expires instanceof Date) {
            options.expires = options.expires.toUTCString();
        }

        let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(
            value);

        for (let optionKey in options) {
            updatedCookie += "; " + optionKey;
            let optionValue = options[optionKey];
            if (optionValue !== true) {
                updatedCookie += "=" + optionValue;
            }
        }

        document.cookie = updatedCookie;
    }

};