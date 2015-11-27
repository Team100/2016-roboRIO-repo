#people = {"Henry" : {"city": "rwc", "state" : "CA"}, "Micheal" : 16, "me" : 4, "you" : 7}
#print people
#for p in people:
#    print p, people[p]
#    if p == "Henry":
#            print people["Henry"]["state"]

class Animal(object):
    
    def __init__(self,n):
        self.name = n
    def printName(self):
        print self.name
    
panda = Animal("infinite_oreos")
panda.printName()