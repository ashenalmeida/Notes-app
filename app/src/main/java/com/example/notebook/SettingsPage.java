package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SettingsPage extends AppCompatActivity {

    Button LoginOut;
    ImageView BackButton;
    TextView usernameTextView , emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sttings_page);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameTextView = findViewById(R.id.user_name);
        emailTextView = findViewById(R.id.user_email);

        LoginOut = findViewById(R.id.login_out);
        BackButton = findViewById(R.id.back_button);

        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(SettingsPage.this, NotesPage.class);
            startActivity(intent);
            finish();
        });

        LoginOut.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(SettingsPage.this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SettingsPage.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
