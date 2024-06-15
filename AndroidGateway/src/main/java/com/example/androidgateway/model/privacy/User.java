package com.example.androidgateway.model.privacy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String first_name;
    private String last_name;
    private String email;
    private String password;
}
