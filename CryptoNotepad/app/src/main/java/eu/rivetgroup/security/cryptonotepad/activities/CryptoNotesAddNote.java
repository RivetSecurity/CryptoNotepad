package eu.rivetgroup.security.cryptonotepad.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import eu.rivetgroup.security.cryptonotepad.R;
import eu.rivetgroup.security.cryptonotepad.utils.CryptoNotesDB;

public class CryptoNotesAddNote extends BaseCryptoActivity {

    private static final String EXTRA_USER_ID = "userId"; //passed through indent as int
    private static final String EXTRA_NOTE_ID = "noteId";

    private static final int MODE_ADD = 1;
    private static final int MODE_EDIT = 2;

    private int userId;
    private int noteId;
    private int mode;
    private CryptoNotesDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notes_add_note);

        db = CryptoNotesDB.getInstance(this);

        if (getIntent().getExtras().containsKey(EXTRA_NOTE_ID)) {
            noteId = getIntent().getExtras().getInt(EXTRA_NOTE_ID);
            mode = MODE_EDIT;
        } else if (getIntent().getExtras().containsKey(EXTRA_USER_ID)) {
            userId = getIntent().getExtras().getInt(EXTRA_USER_ID);
            mode = MODE_ADD;
        }

        if (mode == MODE_EDIT) {
            EditText notesBox = (EditText) findViewById(R.id.noteEditBox);
            notesBox.setText(db.getNote(noteId));
        }
    }

    public void onClickSave(View v) {
        EditText notesBox = (EditText) findViewById(R.id.noteEditBox);
        String note = notesBox.getText().toString();

        if (note.length() == 0) {
            Toast.makeText(this, "Nie zapisano - brak tekstu", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mode == MODE_ADD) {
            db.addNote(userId, note);
        } else {
            db.updateNote(noteId, note);
        }

        setResult(RESULT_OK);
        finish();
    }

    public void onClickBack(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

}
