import re

p_booknames = re.compile(r'<a href=".*">(.*)</a>')
p_authors = re.compile(r'<td class="views-field views-field-field-lr-author" >\s*(.*?)\s*</td>')

with open('tmp.txt',mode='r',encoding='UTF-8') as file:
	text = file.read()
	all_booknames = p_booknames.findall(text)
	all_authors = p_authors.findall(text)
	#print(all_booknames)
	#print(all_authors)
	with open('Books.txt', mode='a+', encoding='UTF-8') as out:
		for i in range(len(all_booknames)):
			line = all_booknames[i]+'|'+all_authors[i]+'\n'
			print(line)
			out.write(line)