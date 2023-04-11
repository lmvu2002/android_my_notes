package edu.hanu.mynotes.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.List;

import edu.hanu.mynotes.models.Note;

public class NoteRepository {
    public static NoteRepository getInstance(Context context) {
        if (instance == null){
            instance = new NoteRepository(context);
        }
        return instance;
    }
    private static NoteRepository instance;
    private DbHelper dbHelper;
    private Context context;
    SQLiteDatabase db;
    private NoteRepository(Context context) {
        this.context = context;
        this.dbHelper = new DbHelper(this.context);
    }



    public List<Note> all() {

        // load from db
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getReadableDatabase();


        SQLiteStatement statement = db.compileStatement("INSERT INTO notes (content) VALUES (?)");
        statement.bindString(1, "Hello");

        String sql = " SELECT * FROM notes";
        Cursor cursor = db.rawQuery(sql, null);

        NoteCursorWrapper noteCursorWrapper = new NoteCursorWrapper(cursor);
        // get all notes in db
        List<Note> notes = noteCursorWrapper.getNotes();
        cursor.close();
        db.close();

        return notes;
    }

    public void removeNote(String content ){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("DELETE FROM notes WHERE content = ? ");
        statement.bindString(1, content);
        statement.executeUpdateDelete();
        statement.close();
        db.close();
    }

    public  int getId(String content){
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        int recc=0;

        String sql = "SELECT id  FROM  notes WHERE content= '" + content + "'";
        Cursor mCursor = db.rawQuery( sql , null);

        if (mCursor != null)
        {
            mCursor.moveToFirst();
            recc=mCursor.getInt(0);
        }
        db.close();
        return recc;


    }

    public  void editNote(int id, String content) {
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement("UPDATE notes SET content = ? WHERE id = ? ");
        statement.bindString(1, content);
        statement.bindLong(2, id);
        statement.executeUpdateDelete();
        statement.close();
        db.close();
    }

    public void addNote(String content) {
        dbHelper = new DbHelper(this.context);
        db = dbHelper.getWritableDatabase();
        SQLiteStatement statement = db.compileStatement(  "INSERT INTO notes (content) VALUES (?)");
        statement.bindString(1,content);
        statement.executeInsert();

        db.close();
    }
}
