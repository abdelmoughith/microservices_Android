from django.db import models
from django.contrib.auth.models import User
# Create your models here.

class Category(models.TextChoices):
    COMPUTERS = 'Computers'
    Electronics = "electronics"
    jewelery = "jewelery"
    men_s_clothing = "men's clothing"
    women_s_clothing = "women's clothing"

class Product(models.Model):
    name = models.CharField(max_length=200,default="",blank=False)
    image = models.CharField(max_length=255,default="",blank=False)
    description = models.TextField(max_length=1000,default="",blank=False)
    price = models.DecimalField(max_digits=7,decimal_places=2,default=0)
    brand = models.CharField(max_length=200,default="",blank=False)
    category = models.CharField(max_length=40,choices=Category.choices, default="")
    ratings = models.DecimalField(max_digits=3,decimal_places=2,default=0)
    stock = models.IntegerField(default=0)
    createAt = models.DateTimeField(auto_now_add=True)
    user = models.ForeignKey(User, null=True, default=1, on_delete=models.SET_NULL)
    """
    label = models.CharField(max_length=200, default="", blank=False)
    description = models.CharField(max_length=255, default="", blank=False)
    price = models.DecimalField(max_digits=7, decimal_places=2, default=0)
    quantity = models.IntegerField(default=0)
    poster = models.CharField(max_length=200, default="", blank=False)
    category_id = models.ForeignKey(Category, null=True, on_delete=models.SET_NULL)
    create_at = models.DateTimeField(auto_now_add=True)
    updated_at = models.DateTimeField(auto_now_add=True)
    """

    def __str__(self):
        return self.name
    


class Review(models.Model):
    product = models.ForeignKey(Product, null=True, on_delete=models.CASCADE,related_name='reviews')
    user = models.ForeignKey(User, null=True, on_delete=models.SET_NULL)
    rating = models.IntegerField(default=0)
    comment = models.TextField(max_length=1000,default="",blank=False) 
    createAt = models.DateTimeField(auto_now_add=True) 

    def __str__(self):
        return self.comment

class ProductOutside(models.Model):
    old = models.FloatField(max_length=200,blank=True)
    reduction = models.FloatField(max_length=200,blank=True)
    new = models.FloatField(max_length=200)
    boutique = models.CharField(max_length=200,default="")
    image = models.CharField(max_length=200, default="")
    name = models.CharField(max_length=200,default="", blank=False)
    link = models.CharField(max_length=200,default="", blank=False)
