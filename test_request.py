import requests

kevin_bacon_url = 'https://en.wikipedia.org/wiki/Kevin_Bacon'

r = requests.get(url=kevin_bacon_url)

data = str(r.content, 'utf-8')

print(data)

