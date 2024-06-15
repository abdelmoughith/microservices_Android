API_KEY = 'AIzaSyAP01mb90kVmqUwapLlfiN-yYZ0dkSW0kg'

import google.generativeai as genai
genai.configure(api_key=API_KEY)
model = genai.GenerativeModel('gemini-pro')

def gemini_generate_response(qst:str):
    response = model.generate_content(qst)
    return response.text