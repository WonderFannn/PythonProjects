#!/usr/bin/python
#	Filename: break.py
while True:
	s = raw_input('Enter Something:')
	if s == 'quit':
		break
	elif s == 'con':
		continue
	print 'lething of the string is',len(s)
print 'Done'