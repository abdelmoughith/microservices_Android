from rest_framework.decorators import api_view
from rest_framework.response import Response

from .gemini import gemini_generate_response
from .serializers import QuestionSerializer


# Create your views here.

@api_view(['POST'])
def ask_question(request):
    data = request.data
    serializer = QuestionSerializer(data=data)

    if serializer.is_valid():
        default_answer = "Default response"  # Choose an appropriate default answer
        #answer = gemini_generate_response(data.get('question', default_answer))
        answer = gemini_generate_response(data.get('question', default_answer))
        question = serializer.save(answer=answer)
        res = QuestionSerializer(question, many=False)
        return Response(res.data['answer'])
    else:
        return Response(serializer.errors)
