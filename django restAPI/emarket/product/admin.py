from django.contrib import admin

from .models import Product, ProductOutside

# Register your models here.

admin.site.register(Product)
admin.site.register(ProductOutside)