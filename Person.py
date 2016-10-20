class Person:
    population = 0

    def __init__(self,name = 'Jerry'):
        self.name = name
        Person.population += 1

    def printCount(self):
        print 'There are %d persons'%Person.population

    def sayHi(self):
        print 'Hello, how are you?','Im',self.name