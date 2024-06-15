package com.example.b3s.account;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.b3s.R;
import com.example.b3s.models.ViewModel;

public class CreateAccount extends AppCompatActivity {
    private Button continueBtn;
    private EditText emailEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        continueBtn = findViewById(R.id.continueBtn);
        // Find EditText views by their IDs
        emailEditText = findViewById(R.id.emailL);
        firstNameEditText = findViewById(R.id.first_name);
        lastNameEditText = findViewById(R.id.last_name);
        passwordEditText = findViewById(R.id.passwordL);

        // Now you can access any attribute of the EditTexts
        // For example:



        continueBtn.setOnClickListener(view -> {
            createAccount();
        });



    }

    private void createAccount() {
        String email = emailEditText.getText().toString();
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        //ViewModel viewModel = new ViewModelProvider(this, factory).get(ViewModel.class);
        //viewModel.setRegistred(getApplicationContext(), new User(email, firstName, lastName, password));
    }
}