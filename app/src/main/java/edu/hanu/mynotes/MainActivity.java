package edu.hanu.mynotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mynotes.adapters.NoteAdapter;
import edu.hanu.mynotes.db.DbHelper;
import edu.hanu.mynotes.db.NoteRepository;
import edu.hanu.mynotes.models.Note;

public class MainActivity extends AppCompatActivity {
    DbHelper dbHelper;
    NoteRepository noteRepository;
    private List<Note> notes;
    private final int REQUEST_CODE = 105;
    NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        noteRepository = NoteRepository.getInstance(getApplicationContext());
        notes = noteRepository.all();

        //recycler view
        RecyclerView rvNotes = findViewById(R.id.rvNotes);
        noteAdapter = new NoteAdapter(notes);
        rvNotes.setAdapter(noteAdapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private List<Note> allNotes() {
        //connect db
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //manupilate db - crud
        List<Note> notes = new ArrayList<>();
        //query db for all notes
        String sql = "select * from notes";
        Cursor cursor =  db.rawQuery(sql, null);
        //foreach record
        int indexId = cursor.getColumnIndex("id");
        int indexContent = cursor.getColumnIndex("content");
        while(cursor.moveToNext()) {
            //convert into Note object
            long id = cursor.getLong(indexId);
            String content = cursor.getString(indexContent);

            Note note = new Note(id, content);
            //add into notes
            notes.add(note);
        }
        cursor.close();

        db.close();

        return notes;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Switching on the item id of the menu item
        Intent in = new Intent(MainActivity.this, AddActivity.class);
        startActivity(in);
        return true;
        /*switch (item.getItemId()) {
            case R.id.
                // Code to be executed when the add button is clicked
                Toast.makeText(this, "Menu Item is Pressed", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent in) {
        super.onActivityResult(requestCode, resultCode, in);
        System.out.println(requestCode);
        System.out.println(resultCode);
        if (resultCode == RESULT_OK) {
            String content = in.getStringExtra("content");
            Note newNote = new Note(content);
            notes.add(newNote);
            noteAdapter.notifyDataSetChanged();
            noteRepository.addNote(content);
        }
    }

}