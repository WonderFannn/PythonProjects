from Person import Person

p = Person('Tom')
p.sayHi()
p.printCount()

a = p
a.printCount()

a = Person()
a.printCount()