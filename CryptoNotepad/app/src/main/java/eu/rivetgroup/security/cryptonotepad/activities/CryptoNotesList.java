package eu.rivetgroup.security.cryptonotepad.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import eu.rivetgroup.security.cryptonotepad.utils.NotesListAdapter;
import eu.rivetgroup.security.cryptonotepad.R;
import eu.rivetgroup.security.cryptonotepad.utils.CryptoNotesDB;

public class CryptoNotesList extends BaseCryptoActivity {

    private static final String EXTRA_USER_ID = "userId"; //passed through indent as int
    private static final String EXTRA_NOTE_ID = "noteId"; //passed through indent as int
    private static final int REQUEST_ADD_NOTE = 1;
    private static final int REQUEST_VIEW_NOTE = 2;

    private CryptoNotesDB db;
    private NotesListAdapter adapter;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notes_list);

        db = CryptoNotesDB.getInstance(this);

        ListView notesList = (ListView) findViewById(R.id.notesList);
        userId = getIntent().getExtras().getInt(EXTRA_USER_ID);

        Cursor c = db.getNotesForUser(userId);
        adapter = new NotesListAdapter(this, c);

        notesList.setAdapter(adapter);
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), CryptoNotesViewNote.class);
                intent.putExtra(EXTRA_NOTE_ID, (int) id);
                startActivityForResult(intent, REQUEST_VIEW_NOTE);
            }
        });
    }

    public void onClickBack(View v) {
        finish();
    }

    public void onClickNewNote(View v) {
        Intent intent = new Intent(this, CryptoNotesAddNote.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        startActivityForResult(intent, REQUEST_ADD_NOTE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode != RESULT_OK)
            return;

        Cursor c = db.getNotesForUser(userId);
        adapter.swapCursor(c);
        adapter.notifyDataSetChanged();
    }
}
