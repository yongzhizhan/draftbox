#!/usr/bin/env python
# -*- coding: UTF-8 -*-

from bottle import route, run, response, request
import json


@route("/test", method="POST")
def test():
    headers = request.headers
    response.headers['Access-Control-Allow-Origin'] = '*'
    retJsonStr = json.dumps({"test": "val"})

    return retJsonStr

if __name__ == '__main__':
    run(host='0.0.0.0', port=8080, debug=True)