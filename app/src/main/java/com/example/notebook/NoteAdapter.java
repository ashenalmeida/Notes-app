package com.example.notebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NoteAdapter extends FirestoreRecyclerAdapter<Notes , NoteAdapter.NotesViewHolder> {
    Context context;

    public NoteAdapter(@NonNull FirestoreRecyclerOptions<Notes> options , Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull NotesViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull Notes notes) {
    holder.titleTextView.setText(notes.title);
    holder.contentTextView.setText(notes.content);
    holder.timeTextView.setText(Utility.timestampToString(notes.time));

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, NotesDetailsPage.class);
            intent.putExtra("title", notes.title);
            intent.putExtra("content", notes.content);

            // In a lambda, "this" refers to NoteAdapter
            String noteId = getSnapshots().getSnapshot(position).getId();

            intent.putExtra("noteId", noteId);
            context.startActivity(intent);
        });

    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_page_item , parent , false);
        return new NotesViewHolder(view);
    }

    static class  NotesViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView , contentTextView , timeTextView;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title_text);
            contentTextView = itemView.findViewById(R.id.content_text);
            timeTextView = itemView.findViewById(R.id.time_text_view);

        }
    }
}
