import requests
import bs4

def scrape_jumia_products(query):
    def is_product(p):
        return bool(p.select('div .prc'))

    query = query.replace(' ', '+')
    res = requests.get('https://www.jumia.ma/catalog/?q', params={'q': query})
    soup = bs4.BeautifulSoup(res.text, features='html.parser')
    products = soup.select('article')
    products = list(filter(is_product, products))

    def scrape_product(product):
        dic = {}

        if product.select('div .old'):
            dic['old'] = product.select('div .old')[0].getText()
            dic['reduction'] = product.select('div.bdg._dsct._sm')[0].getText()
        # info
        if product.select('div.bdg._mall._xs'):
            dic['boutique'] = product.select('div.bdg._mall._xs')[0].getText()

        dic['new'] = product.select('div .prc')[0].getText()
        # image of products
        dic['image'] = product.select('.img')[0].get('data-src')
        dic['name'] = product.select('h3.name')[0].getText()
        dic['link'] = 'https://www.jumia.ma'+product.select('a.core')[0]['href']

        return dic

    return [scrape_product(product) for product in products]

# Example usage:
#query = 'smart-tv'
#products = scrape_jumia_products(query)
#print(products)
#print(len(products))
