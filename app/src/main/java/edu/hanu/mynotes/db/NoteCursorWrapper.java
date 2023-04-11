package edu.hanu.mynotes.db;


import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;
import java.util.List;

import edu.hanu.mynotes.models.Note;

public class NoteCursorWrapper extends CursorWrapper {

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        Cursor cursor = getWrappedCursor();
        int idCol = cursor.getColumnIndex("id");
        int contentCol = cursor.getColumnIndex("content");
        cursor.moveToNext();
        int id = cursor.getInt(idCol);
        String content = cursor.getString(contentCol);
        Note note = new Note(id, content);
        return note;
    }

    public List<Note> getNotes(){
        List<Note> notes = new ArrayList<>();
        Cursor cursor = getWrappedCursor();
        int idCol = cursor.getColumnIndex("id");
        int contentCol = cursor.getColumnIndex("content");


        while (cursor.moveToNext()){

            int id = cursor.getInt(idCol);
            String text = cursor.getString(contentCol);
            Note note = new Note(id, text);
            notes.add(note);
        }

        return notes;
    }

}