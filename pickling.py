#!/usr/bin/python
# Filename: pickling.py
import cPickle as p

shoplistfile = 'shoplist.data'

shoplist = ['apple', 'mango','banana']
text = 'asssdfghjkl'
f = file(shoplistfile,'a')
p.dump(shoplist,f)
p.dump(shoplist,f)
p.dump(shoplist,f)
p.dump(text,f)
f.close()

del shoplist

f = file(shoplistfile)
a = p.load(f)

print a