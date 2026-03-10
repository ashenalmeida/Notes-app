package com.example.notebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.Timestamp;

public class NotesDetailsPage extends AppCompatActivity {

    ImageView BackButton;
    TextView Title , Content , PageTitle;
    Button DoneButton , DeleteButton;
    String EditTitle , EditContent , docId;

    boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notes_details_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         BackButton = findViewById(R.id.back_button);
         DoneButton = findViewById(R.id.save_btn);
         Title = findViewById(R.id.note_title);
         Content = findViewById(R.id.note_content);
         PageTitle = findViewById(R.id.page_title);
         DeleteButton = findViewById(R.id.delete_btn);


         //Receive Data
        EditTitle = getIntent().getStringExtra("title");
        EditContent = getIntent().getStringExtra("content");
        docId = getIntent().getStringExtra("noteId");

        Title.setText(EditTitle);
        Content.setText(EditContent);

        if(docId != null && !docId.isEmpty()){
            isEditMode = true;
            PageTitle.setText("Edit Note");
            DeleteButton.setVisibility(View.VISIBLE);

        }

        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(NotesDetailsPage.this, NotesPage.class);
            startActivity(intent);
            finish();
        });

        DoneButton.setOnClickListener(V ->saveNotes());
        DeleteButton.setOnClickListener(V -> deleteNotes());



    }

    void saveNotes() {
        String title = Title.getText().toString();
        String content = Content.getText().toString();

        if(title.isEmpty()){
            Snackbar.make(findViewById(R.id.note_content), "Title is Required", Snackbar.LENGTH_SHORT).show();
            return;
        }
        if(content.isEmpty()){
            Snackbar.make(findViewById(R.id.main), "Content is Required", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Notes notes = new Notes();
        notes.setTitle(title);
        notes.setContent(content);
        notes.setTime(Timestamp.now());
        saveNotesFireBase(notes);

    }

    public void saveNotesFireBase(Notes notes){

        DocumentReference documentReference;
        if(isEditMode){
            //update the note
            documentReference = Utility.getCollectionReferenceForNotes().document(docId);
        }else {
            //create a new note
            documentReference = Utility.getCollectionReferenceForNotes().document();
        }
        documentReference.set(notes).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NotesDetailsPage.this, "Notes Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NotesDetailsPage.this, NotesPage.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(NotesDetailsPage.this, "Fail to Add Notes", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public void deleteNotes(){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForNotes().document(docId);

        documentReference.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(NotesDetailsPage.this, "Notes Deleted Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NotesDetailsPage.this, NotesPage.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(NotesDetailsPage.this, "Fail to Delete Notes", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}