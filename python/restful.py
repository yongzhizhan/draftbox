#!/usr/bin/env python
# -*- coding: UTF-8 -*-

from bottle import route, run, response
import json


@route("/test")
def test():
    response.headers['Access-Control-Allow-Origin'] = '*'
    retJsonStr = json.dumps({"test": "val"})

    return retJsonStr

if __name__ == '__main__':
    run(host='0.0.0.0', port=8080, debug=True)