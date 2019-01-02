function jump(url) {
    window.location.href = url;
}

function jumpWithParam(url, key, value) {
    window.location.href = url + '?' + key + '=' + value;
}

function jumpWithUsername(url) {
    window.location.href = '/jump?url=' + url + '&username' + username;
}