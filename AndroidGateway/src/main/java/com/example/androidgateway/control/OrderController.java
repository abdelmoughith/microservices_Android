package com.example.androidgateway.control;

import com.example.androidgateway.model.order.Order;
import com.example.androidgateway.model.order.OrderResponse;
import com.example.androidgateway.model.privacy.User;
import com.example.androidgateway.model.product.Product;
import com.example.androidgateway.model.product.ProductSent;
import com.example.androidgateway.model.product.ProductsList;
import com.example.androidgateway.util.Links;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/new")
    public String newArticle(@RequestHeader("Authorization") String authorizationHeader,
                             @RequestBody Order order) throws JsonProcessingException {

        String jsonUser = objectMapper.writeValueAsString(order);

        // Set headers
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        // Create request entity with JSON user
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonUser, headers);

        // Forward the request to the Django microservice responsible for registration
        ResponseEntity<Order> responseEntity = restTemplate.exchange(
                Links.DJANGO_URL_ORDER_NEW,
                HttpMethod.POST,
                requestEntity,
                Order.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return "Product created successfully";
        else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
            return "Permission denied";
        else
            return "Failed";

    }
    @PostMapping("add")
    public String newOrder(@RequestHeader("Authorization") String authorizationHeader,
                             @RequestBody Order order) throws JsonProcessingException {

        String jsonUser = objectMapper.writeValueAsString(order);

        // Set headers
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        // Create request entity with JSON user
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonUser, headers);

        // Forward the request to the Django microservice responsible for registration
        ResponseEntity<Order> responseEntity = restTemplate.exchange(
                Links.DJANGO_URL_ORDER_NEW,
                HttpMethod.POST,
                requestEntity,
                Order.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return "Order created successfully";
        else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
            return "Permission denied";
        else
            return "Failed";

    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrders(@RequestHeader("Authorization") String authToken) {
        String url = Links.DJANGO_URL_ORDER_GET; // Replace with your actual Django service URL

        String token = authToken.substring("Bearer ".length()).trim();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<List<OrderResponse>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to fetch orders: " + response.getStatusCode());
        }
    }


}
