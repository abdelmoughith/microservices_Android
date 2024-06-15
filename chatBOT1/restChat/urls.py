from django.urls import path

from . import views

urlpatterns = [
    path('question/', views.ask_question, name='question'),
]