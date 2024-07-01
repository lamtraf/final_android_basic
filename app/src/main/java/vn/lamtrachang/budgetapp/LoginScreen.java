package vn.lamtrachang.budgetapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginScreen extends AppCompatActivity {

    private TextView email_login;
    private TextView password_login;
    private Button loginButton;
    private TextView forgetPassword;
    private TextView registerLink;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        loginDetails();

    }

    private void loginDetails(){
        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        loginButton = findViewById(R.id.login_button);
        forgetPassword = findViewById(R.id.forget_password);
        registerLink = findViewById(R.id.signup_reg);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_login.getText().toString().trim();
                String password = password_login.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    email_login.setError("Email is required");
                    email_login.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    email_login.setError("Please enter a valid email");
                    email_login.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    password_login.setError("Password is required");
                    password_login.requestFocus();
                    return;
                }
                if (databaseHelper.checkUser(email, password)) {
                    //Intent intent = new Intent(LoginScreen.this, HomeScreen.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterScreen.class);
                startActivity(intent);
            }
        });

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPassActivity.class);
                startActivity(intent);
            }
        });

    }
}