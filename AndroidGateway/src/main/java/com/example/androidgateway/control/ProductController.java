package com.example.androidgateway.control;

import com.example.androidgateway.model.product.*;
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
public class ProductController {
    @Autowired
    private RestTemplate restTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/products")
    public ProductsList getProducts() {
        StringBuilder urlBuilder = new StringBuilder(Links.DJANGO_URL_PRODUCTS);


        ResponseEntity<ProductsList> response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @GetMapping("/productsq")
    public ProductsList getProductsQuery(@RequestParam Map<String, String> params) {
        StringBuilder urlBuilder = new StringBuilder(Links.DJANGO_URL_PRODUCTS);

        if (!params.isEmpty()) {
            urlBuilder.append("?");
            params.forEach((key, value) -> urlBuilder.append(key).append("=").append(value).append("&"));
            urlBuilder.setLength(urlBuilder.length() - 1); // Remove trailing '&'
        }

        ResponseEntity<ProductsList> response = restTemplate.exchange(
                urlBuilder.toString(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );
        return response.getBody();
    }

    @GetMapping("/products/{id}")
    public Product getArticleById(@PathVariable int id) {
        String url = Links.DJANGO_URL_PRODUCTS + id + "/";
        ResponseEntity<Map<String, Product>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                }
        );

        Product product = response.getBody().get("product");
        return product;
    }

    @PostMapping("/create")
    public String newArticle(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestBody ProductSent productSent) throws JsonProcessingException {

        String jsonUser = objectMapper.writeValueAsString(productSent);

        // Set headers
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        // Create request entity with JSON user
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonUser, headers);

        // Forward the request to the Django microservice responsible for registration
        ResponseEntity<Product> responseEntity = restTemplate.exchange(
                Links.DJANGO_URL_ADD_PRODUCTS,
                HttpMethod.POST,
                requestEntity,
                Product.class
        );

        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return "Product " + productSent.getName() + " created successfully";
        else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR)
            return "Permission denied";
        else
            return "Failed";

    }

    @PutMapping("/update/{id}")
    public String updateProduct(@RequestHeader("Authorization") String authorizationHeader,
                                @RequestBody ProductSent productSent,
                                @PathVariable("id") Long id) throws JsonProcessingException {
        // Convert the product data to JSON
        String jsonUser = objectMapper.writeValueAsString(productSent);

        // Set headers
        String token = authorizationHeader.substring("Bearer ".length()).trim();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        // Create request entity with JSON user
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonUser, headers);

        // Forward the request to the Django microservice responsible for updating the product
        ResponseEntity<Product> responseEntity = restTemplate.exchange(
                Links.DJANGO_URL_UPDATE_PRODUCTS + id + "/",
                HttpMethod.PUT,
                requestEntity,
                Product.class
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK)
            return "Product " + productSent.getName() + " updated successfully";
        else if (responseEntity.getStatusCode() == HttpStatus.FORBIDDEN)
            return "Permission denied";
        else
            return "Failed";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, ?>> deleteProduct(@RequestHeader("Authorization") String authorizationHeader,
                                                        @PathVariable("id") Long id) {
        // Set headers
        String token = authorizationHeader.substring("Bearer ".length()).trim();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // Create request entity with headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Forward the request to the Django microservice responsible for deleting the product
        String url = Links.DJANGO_URL_DELETE_PRODUCTS + id + "/";
        ResponseEntity<Map<String, ?>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        // Return the response from the Django service
        return responseEntity;
    }

    @GetMapping("/get_outside")
    public ResponseEntity<?> getAllOutside(@RequestParam(value = "q", required = false) String keyword) {
        try {
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Create request entity with headers
            HttpEntity<String> requestEntity = new HttpEntity<>(headers);

            String url = Links.DJANGO_URL_GET_ALL_OUTSIDE;
            if (keyword != null) {
                url += "?q=" + keyword;
            }

            // Forward the request to the Django microservice responsible for scraping
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );

            // Log the raw response
            String rawResponse = responseEntity.getBody();
            //System.out.println("Raw response: " + rawResponse);

            // Deserialize the raw response into ProductScrappingResponse
            ObjectMapper objectMapper = new ObjectMapper();
            ProductScrappingResponse productScrappingResponse = objectMapper.readValue(rawResponse, ProductScrappingResponse.class);

            // Return the list of products from the Django service
            return ResponseEntity.ok(productScrappingResponse.getProducts());
        } catch (Exception e) {
            // Log the error and return an appropriate response
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}
