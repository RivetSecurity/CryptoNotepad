package eu.rivetgroup.security.cryptonotepad.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import eu.rivetgroup.security.cryptonotepad.R;
import eu.rivetgroup.security.cryptonotepad.utils.CryptoNotesDB;

public class CryptoNotesViewNote extends AppCompatActivity {
    private static final String EXTRA_NOTE_ID = "noteId"; //passed through indent as int
    private static final int REQUEST_EDIT_NOTE = 3;

    private int noteId;
    private CryptoNotesDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notes_view_note);

        db = CryptoNotesDB.getInstance(this);
        noteId = getIntent().getExtras().getInt(EXTRA_NOTE_ID);
        setNote();
    }

    private void setNote() {
        String note = db.getNote(noteId);
        TextView noteView = (TextView) findViewById(R.id.notePreview);
        noteView.setText(note);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            setNote();
        }
    }
    public void onClickEdit(View v) {
        Intent intent = new Intent(this, CryptoNotesAddNote.class);
        intent.putExtra(EXTRA_NOTE_ID, noteId);
        startActivityForResult(intent, REQUEST_EDIT_NOTE);
    }

    public void onClickBack(View v) {
        finish();
    }

    public void onClickDelete(View v) {
        new AlertDialog.Builder(this)
                .setMessage("Czy na pewno chcesz usunąć?")
                .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteNote(noteId);
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setNegativeButton("Nie", null)
                .show();

    }

}
