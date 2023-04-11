package edu.hanu.mynotes.adapters;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import edu.hanu.mynotes.R;

import java.util.List;

import edu.hanu.mynotes.db.DbHelper;
import edu.hanu.mynotes.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    List<Note> notes;
    TextView textView;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
    }


    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate item_Note.xml -> display into parent
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.notes_recycler, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        // get data
        Note note = notes.get(position);
        // bind data to view
        holder.bind(note, position);
        View itemView = holder.itemView;

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteHolder extends RecyclerView.ViewHolder {
        public NoteHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Note note, int position) {
            textView = itemView.findViewById(R.id.textView);
            textView.setText(note.getName());

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //delete note from db
                    deleteNote(note.getId());
                    //remove from dataset
                    notes.remove(note);
                    //refresh recycler view
                    notifyItemChanged(position);
                    return false;
                }
            });
        }
        // TODO: handle event

        private boolean deleteNote(long id) {
            DbHelper dbHelper = new DbHelper(itemView.getContext());
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            SQLiteStatement statement = db.compileStatement("delete from notes where id=? ");
            statement.bindLong(1, id);
            int noAffectedRows = statement.executeUpdateDelete();
            db.close();
            return noAffectedRows > 0;
        }

        private boolean addNote(long id) {
            return false;
        }
    }
}