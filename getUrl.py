#!/usr/bin/python
# -*- coding: UTF-8 -*-
import urllib2
import webbrowser
import re
text = 'H停车券'
for num in range(619500,619999):
	url = 'https://m.honglingjin.cn/user/coupon/info/'+str(num)+'_0.html?coupon_source=offline'
	response = urllib2.urlopen(url)
	html = response.read()
	m = re.search("<title>.*</title>", html)
	# print m.group() # 这里输出结果 <title>Apple</title>
	print str(num)+' : '+m.group().strip("</title>") #问题应该出现在这个正则
	if text in m.group():
		webbrowser.open(url)