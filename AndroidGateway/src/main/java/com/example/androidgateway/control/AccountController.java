package com.example.androidgateway.control;


import com.example.androidgateway.model.privacy.Token;
import com.example.androidgateway.model.privacy.Usename;
import com.example.androidgateway.model.privacy.User;
import com.example.androidgateway.util.Links;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class AccountController {
    @Autowired
    private RestTemplate restTemplate;
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
                Links.DJANGO_URL_REGISTER,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        // Return the response from the Django microservice
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }

    @PostMapping("/login")
    public Token login(@RequestBody Usename user) throws JsonProcessingException {
        //System.out.println("username "+user.getUsername()+ " end");
        //System.out.println("password "+user.getPassword()+ " end");
        // Convert User object to JSON
        String jsonUser = objectMapper.writeValueAsString(user);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create request entity with JSON user
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonUser, headers);

        // Forward the request to the Django microservice responsible for registration
        ResponseEntity<Token> responseEntity = restTemplate.exchange(
                Links.DJANGO_URL_LOGIN,
                HttpMethod.POST,
                requestEntity,
                Token.class
        );

        // Return the response from the Django microservice
        return responseEntity.getBody();
    }

    @GetMapping("/userinfo")
    public User getUserInfo(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        // Set headers with the extracted token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);


        // Create a request entity with headers
        HttpEntity<?> requestEntity = new HttpEntity<>(headers);

        // Send GET request to microservice
        ResponseEntity<User> responseEntity = restTemplate.exchange(
                Links.DJANGO_URL_USER_INFO,
                HttpMethod.GET,
                requestEntity,
                User.class
        );

        // Return the user information from the microservice
        return responseEntity.getBody();
    }

    @PutMapping("/userinfo/update")
    public ResponseEntity<String> updateUser(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestBody User user) throws JsonProcessingException {
        // Convert User object to JSON
        String jsonUser = objectMapper.writeValueAsString(user);
        // Extract the token from the Authorization header
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        // Create request entity with JSON user
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonUser, headers);

        // Forward the request to the Django microservice responsible for user update
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                Links.DJANGO_URL_USER_INFO_UPDATE,
                HttpMethod.PUT,
                requestEntity,
                String.class
        );

        // Return the response from the Django microservice
        return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
    }






}
