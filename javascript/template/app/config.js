var config = {
    init: function () {
        var baseUrl = this.getQueryString("baseUrl");
        if (!baseUrl)
            baseUrl = "./modules";
        else
            baseUrl = this.pathDecode(baseUrl);

        requirejs.config({
            baseUrl: baseUrl,
            paths: {
                'jquery': '../libs/jquery',
                'base64': "../libs/jbase64"
            }
        });

        return true;
    },

    pathEncode: function(str){
        var encodeStr = "";
        for(var i=0; i<str.length; i++){
            encodeStr += str.charCodeAt(i).toString(16);
        }

        return encodeStr;
    },

    pathDecode: function(str){
        var decodeStr = "";
        for(i=0;i<str.length;i+=2) {
            var val = '0x' + str[i] + str[i + 1];
            decodeStr += String.fromCharCode(val).toString();
        }

        return decodeStr;
    },

    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }
}

config.init();