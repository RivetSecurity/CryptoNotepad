package eu.rivetgroup.security.cryptonotepad.utils;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class NotesListAdapter extends SimpleCursorAdapter {

    private final static String[] from = {
            CryptoNotesDB.FIELD_NOTE_TEXT
    };

    private final static int[] to = {
            android.R.id.text1
    };

    public NotesListAdapter(Context context, Cursor c) {
        super(context, android.R.layout.simple_list_item_1, c, from, to, 0);
    }

    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, decryptText(text));
    }

    private String decryptText(String text) {
        text = CryptoHelper.decrypt(text);
        text = text.replaceAll("(\\r|\\n|\\r\\n)+", " ");
        int length = text.length();

        if (length > 30) {
            text = text.substring(0, 30);
            text = text + "...";
        }

        return text;
    }
}
