package vn.lamtrachang.budgetapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterScreen extends AppCompatActivity {

    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private TextView loginBackLink;
    private DatabaseHelper databaseHelper;
    private EditText passwordConfirm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_screen);
        registration();

    }
    private void registration(){
        nameEditText = findViewById(R.id.name_register);
        emailEditText = findViewById(R.id.email_register);
        passwordEditText = findViewById(R.id.password_register);
        passwordConfirm = findViewById(R.id.confirm_password_register);
        registerButton = findViewById(R.id.register_button);
        loginBackLink = findViewById(R.id.login_reg);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirm_password = passwordConfirm.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    nameEditText.setError("Name is required");
                    nameEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Please provide valid email");
                    emailEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    passwordEditText.setError("Password is required");
                    passwordEditText.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    passwordEditText.setError("Password must be at least 6 characters");
                    passwordEditText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(confirm_password)) {
                    passwordConfirm.setError("Confirm password is required");
                    passwordConfirm.requestFocus();
                    return;
                }
                if (!password.equals(confirm_password)) {
                    passwordConfirm.setError("Password does not match");
                    passwordConfirm.requestFocus();
                    return;
                }
                   if (databaseHelper.checkUser(email, password)) {
                      Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                 } else {
                      databaseHelper.addUser(name, email, password);
                      Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
//                      Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
//                      startActivity(intent);
                       finish();
                 }
            }
});
        loginBackLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
//                startActivity(intent);
                finish();
            }
        });
    }

}
