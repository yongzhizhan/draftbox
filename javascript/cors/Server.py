#!/usr/bin/env python
# coding:utf-8

from flask import Flask, request, render_template
import httplib
from base64 import b64encode

import json
import random

app = Flask(__name__)


@app.route("/", methods=["GET"])
def index():
    randomVal = random.randint(0, 1000)
    return render_template("index.html", randomVal = randomVal)
	
@app.route("/jquery.iecors.js", methods=["GET"])
def js():
    randomVal = random.randint(0, 1000)
    return render_template("jquery.iecors.js", randomVal = randomVal)

@app.route("/get", methods=['GET'])
def get():
    url = request.args.get('url')
    c = httplib.HTTPConnection("svn.atomview.net")
    # we need to base 64 encode it
    # and then decode it to acsii as python 3 stores it as a byte string
    userAndPass = b64encode(b"zhanyongzhi:00").decode("ascii")
    headers = {'Authorization': 'Basic %s' % userAndPass}
    # then connect
    c.request('GET', url, headers=headers)
    # get the response back
    res = c.getresponse()
    # at this point you could check the status etc
    # this gets the page text
    data = res.read()

    return data





if __name__ == "__main__":
    app.run()
