import sys
def fibs(num = 0):
	result = [0,1]
	for x in range(num - 2):
		result.append(result[-2]+result[-1])
	return result

print fibs(int(sys.argv[1]))