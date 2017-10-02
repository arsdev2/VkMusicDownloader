#! /usr/bin/env python
# -*- coding: utf-8 -*-
import urllib, os, errno, urllib2, json
import subprocess
import traceback
headers = { 'User-Agent' : 'VKAndroidApp/4.13.1-1206 (Android 5.1.1; SDK 22; armeabi-v7a; samsung GT-I9300; uk)' }
req = urllib2.Request('https://api.vk.com/method/audio.get?access_token=b3616c1b7838834211ab23268152fb7c587ada64a9a7b6eb7f62fb10c7f003eb1917d5b05065583a627c1', None, headers)
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
