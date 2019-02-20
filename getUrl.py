#!/usr/bin/python
# -*- coding: UTF-8 -*-
import urllib2
import webbrowser
import re
text = 'H停车券'
text2 = '高新天虹'
text3 = '成都'
text4 = '高新区'
text5 = '奥克斯'
fileName = "getNum.txt"
f = open(fileName)
num = 620365
while 1:
    line = f.readline()
    if not line:
        break
    num = int(line)
f.close()
flag = 0
while 1:
	url = 'https://m.honglingjin.cn/user/coupon/info/'+str(num)+'_0.html?coupon_source=offline'
	try:
		pass
		response = urllib2.urlopen(url)
		html = response.read()
		m = re.search("<title>.*</title>", html)
		title = m.group().strip("</title>")
	except Exception as e:
		continue
	print str(num)+' : '+title
	if title:
		flag = 0
	else:
		flag += 1
	if text in m.group():
		webbrowser.open(url)
	if text2 in html or text3 in html or text4 in html or text5 in html:
		webbrowser.open(url)
	if flag == 30:
		f = open(fileName, "w")
		print >> f, "%d" % (num - 29)
		f.close()
		break
	num += 1