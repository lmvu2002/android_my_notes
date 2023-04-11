package edu.hanu.mynotes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import edu.hanu.mynotes.db.DbHelper;

public class AddActivity extends AppCompatActivity {

    DbHelper dbHelper;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_activity);
            Intent in = getIntent();

        }

        public void add(View view) {
            EditText content = findViewById(R.id.note_content);
            Intent in = new Intent(AddActivity.this, MainActivity.class);
            in.putExtra("content", content.getText().toString());
            setResult(RESULT_OK, in);
            finish();
        }
}
