#! /usr/bin/env python
# -*- coding: utf-8 -*-
import urllib, os, errno, urllib2, json
import subprocess
import traceback
headers = { 'User-Agent' : 'ТУТ_ВАШ_ЮЗЕР_АГЕНТ' }
req = urllib2.Request('https://api.vk.com/method/audio.get?access_token=ТУТ_ВАШ_ТОКЕН', None, headers)
html = urllib2.urlopen(req).read()
file = open('out.json','w') 
file.write(str(html)) 
file.close() 
with open('out.json') as data_file:    
    data = json.load(data_file)
try:
    os.makedirs('/music')
except OSError as e:
    if e.errno != errno.EEXIST:
        raise
for i in range(len(data["response"])):
    element = data["response"]
    try:
      url = element[i]["url"]
      print "Downloading: ", element[i]["artist"], "-", element[i]["title"]
      name = element[i]["artist"] + element[i]["title"] + ".mp3"
      destination = "/music"
      urllib.urlretrieve(url, os.path.join(destination,name))
    except Exception:
      traceback.print_exc()
      print "Нет юрл для: ", element[i]["artist"], "-", element[i]["title"]
      pass
process = subprocess.Popen(['sh','remove.sh'])
process.wait()
