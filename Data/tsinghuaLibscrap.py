import re

p_booknames = re.compile(r'<a href=".*">(.*)</a>')
p_authors = re.compile(r'<td class="views-field views-field-field-lr-author" >\s*(.*?)\s*</td>')
p_press = re.compile(r'<td class="views-field views-field-field-lr-publisher" >\s*(.*?)\s*</td>')

with open('tmp.txt',mode='r',encoding='UTF-8') as file:
	text = file.read()
	all_booknames = p_booknames.findall(text)
	all_authors = p_authors.findall(text)
	all_presses = p_press.findall(text)
	#print(all_booknames)
	#print(all_authors)
	with open('Books.txt', mode='a+', encoding='UTF-8') as out:
		for i in range(len(all_booknames)):
			line = all_booknames[i]+'|'+all_authors[i]+'|'+all_presses[i]+'\n'
			print(line)
			out.write(line)
	nums = (len(all_booknames),len(all_authors),len(all_presses))
	print("Total: %d books, %d authors, %d presses" % nums)