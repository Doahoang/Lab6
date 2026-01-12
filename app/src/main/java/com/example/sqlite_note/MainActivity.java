package com.example.sqlite_note;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    EditText edtNote;
    Button btnSave;
    ListView listViewNotes;

    DatabaseHandler db;
    ArrayList<String> noteList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNote = findViewById(R.id.edtNote);
        btnSave = findViewById(R.id.btnSave);
        listViewNotes = findViewById(R.id.listViewNotes);

        db = new DatabaseHandler(this);

        loadNotes();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = edtNote.getText().toString();
                if (!note.isEmpty()) {
                    db.addNote(note);
                    edtNote.setText("");
                    loadNotes();
                }
            }
        });

        // Xóa ghi chú khi click giữ
        listViewNotes.setOnItemLongClickListener((parent, view, position, id) -> {
            db.deleteNote(noteList.get(position));
            loadNotes();
            return true;
        });
    }

    private void loadNotes() {
        noteList = db.getAllNotes();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, noteList);
        listViewNotes.setAdapter(adapter);
    }
}