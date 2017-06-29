function readCookie(name) {
    let nameEQ = name + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
    }
    return null;
}

let csrf_parameterName = '_csrf';
let csrf_token = readCookie('XSRF-TOKEN');

let csrfField = document.getElementById('csrf-field');

if (csrfField) {
    csrfField.setAttribute('name', csrf_parameterName);
    csrfField.setAttribute('value', csrf_token);
}