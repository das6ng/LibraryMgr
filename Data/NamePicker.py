#!/bin/baaash/env python
# -*- coding:utf-8 -*-

# Usage:
#     pick a name
#

import random

class NamePicker(object):
    def __init__(self):
        self.firstnames = []
        self.lastnames = []
        namef = open("./firstnames.txt",encoding='UTF-8')
        namel = open("./baijiaxing.txt",encoding='UTF-8')
        line = namef.readline()
        while line:
            self.firstnames += line.split()
            line = namef.readline()
        line = namel.readline()
        while line:
            self.lastnames += line.split()
            line = namel.readline()
        namef.close()
        namel.close()
    
    def showData(self):
        print("# first names: \n")
        for i in self.firstnames:
            print(str(i)+" ",end='')
        print("\n# last names: \n")
        for i in self.lastnames:
            print(str(i)+" ",end='')
    
    def pickName(self):
        name = random.sample(self.lastnames, 1)[0]
        name += random.sample(self.firstnames, 1)[0]
        return name
   
    def pickBirth(self,start=1995,end=2000):
        months = ['01','02','03','04','05','06','07','08','09','10','11','12']
        birth = str(random.randint(start,end))
        birth += random.sample(months, 1)[0]
        birth += random.sample(months, 1)[0]
        
        return birth
    
    def pickGender(self, extend=False):
        g = ['F', 'M']
        if extend:
            g = ['F', 'M', 'X']
        return random.sample(g,1)[0]
    
    def pickPerson(self,start=1995,end=2000,extG=False):
        return [self.pickName(), self.pickGender(extG), self.pickBirth(start,end)]
    
        
if __name__ == "__main__":
    np = NamePicker()
    #np.showData()
    #fd = open("names.txt","w")
    for i in range(0, 1000):
        j = np.pickPerson()
        #fd.write(" ".join(j)+"\n")
        print("#"+str(i)+" "+str(j[0])+" "+str(j[1])+" "+str(j[2]))
        

