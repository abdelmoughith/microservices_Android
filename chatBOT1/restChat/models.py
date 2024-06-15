from django.db import models

# Create your models here.
class Question(models.Model):
    question = models.TextField(max_length=1000, default="", blank=False)
    createAt = models.DateTimeField(auto_now_add=True)
    #user = models.ForeignKey(User, null=True, on_delete=models.CASCADE)
    answer = models.TextField(max_length=1000, default="", blank=False)

    def __str__(self):
        return self.question

    class Meta:
        app_label = 'restChat'
