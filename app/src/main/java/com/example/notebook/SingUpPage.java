package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public class SingUpPage extends AppCompatActivity {

    EditText EditTextEmail , EditTextUserName , EditTextPassword , EditTextConformPassword;
    Button SingUpButton;
    FirebaseAuth mAuth;
    ProgressBar ProgressBar;
    TextView Login;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext() , NotesPage.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sing_up_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditTextEmail = findViewById(R.id.email_address);
        EditTextUserName = findViewById(R.id.user_name);
        EditTextPassword = findViewById(R.id.password);
        EditTextConformPassword = findViewById(R.id.conform_password);
        SingUpButton = findViewById(R.id.sing_up);
        mAuth = FirebaseAuth.getInstance();
        Login = findViewById(R.id.loginText);

        Login.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext() , MainActivity.class);
            startActivity(intent);
        });

        SingUpButton.setOnClickListener(view -> {
            String Email , UserName , Password , ConformPassword;

            Email = EditTextEmail.getText().toString().trim();
            UserName = EditTextUserName.getText().toString().trim();
            Password = EditTextPassword.getText().toString().trim();
            ConformPassword = EditTextConformPassword.getText().toString().trim();

            if(TextUtils.isEmpty(Email)){
                Toast.makeText(SingUpPage.this , "Please Enter Email" , Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(UserName)){
                Toast.makeText(SingUpPage.this , "Please Enter UserName" , Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(Password)){
                Toast.makeText(SingUpPage.this , "Please Enter Password" , Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(ConformPassword)){
                Toast.makeText(SingUpPage.this , "Please Enter Confirm Password" , Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Password.equals(ConformPassword)) {
                Toast.makeText(SingUpPage.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(Email, Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SingUpPage.this, "Account Created", Toast.LENGTH_SHORT).show();
                                // Go to NotesPage after saving
                                Intent intent = new Intent(getApplicationContext(), NotesPage.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(SingUpPage.this, "Email or Password is Invalid" ,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        });
    }
}
