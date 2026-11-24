package com.example.modemtabs;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput;
    private Button loginButton;
    private long lastBackPressed = 0L;
    private static final long EXIT_INTERVAL = 2000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setEnabled(false);

        TextWatcher validator = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateForm();
            }
            @Override public void afterTextChanged(Editable s) {}
        };
        emailInput.addTextChangedListener(validator);
        passwordInput.addTextChangedListener(validator);

        loginButton.setOnClickListener(v -> {
            // Demo authentication only: no modem/server credentials are transmitted.
            startActivity(new Intent(MainActivity.this, ModeActivity.class));
        });
    }

    private void validateForm() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        boolean validEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        loginButton.setEnabled(validEmail && password.length() >= 6);
    }

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if (now - lastBackPressed < EXIT_INTERVAL) {
            finishAffinity();
        } else {
            lastBackPressed = now;
            Snackbar.make(findViewById(android.R.id.content),
                    "Press back again to exit", Snackbar.LENGTH_SHORT).show();
        }
    }
}
