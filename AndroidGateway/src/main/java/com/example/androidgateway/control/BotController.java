package com.example.androidgateway.control;


import com.example.androidgateway.model.chatbot.AnswerResponse;
import com.example.androidgateway.model.chatbot.QuestionRequest;
import com.example.androidgateway.util.Links;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
//@RequestMapping("/bot")
public class BotController {


    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/ask")
    public String askQuestion(@RequestBody QuestionRequest questionRequest) {


        RestTemplate restTemplate = new RestTemplate();

        String answerResponse = restTemplate.postForObject(Links.DJANGO_URL_bot,
                questionRequest, String.class);

        // Return the answer received from Django
        return answerResponse;


    }

    /*

    @GetMapping("/products")
    public List<Product> getProducts() {
        ResponseEntity<List<Product>> response = restTemplate.exchange(
                Links.django_url_products,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {}
        );
        return response.getBody();
    }








    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) throws JsonProcessingException {
            // Convert User object to JSON
            String jsonUser = objectMapper.writeValueAsString(user);

            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity with JSON user
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonUser, headers);

            // Forward the request to the Django microservice responsible for registration
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    Links.django_url_register,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            );

            // Return the response from the Django microservice
            return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());

    }

*/

}
