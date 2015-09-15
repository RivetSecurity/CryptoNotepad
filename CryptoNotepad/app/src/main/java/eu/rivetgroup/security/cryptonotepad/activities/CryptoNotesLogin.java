package eu.rivetgroup.security.cryptonotepad.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eu.rivetgroup.security.cryptonotepad.R;
import eu.rivetgroup.security.cryptonotepad.utils.CryptoNotesDB;

public class CryptoNotesLogin extends BaseCryptoActivity {

    private static final String EXTRA_USER_ID = "userId"; //passed through indent as int

    CryptoNotesDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notes_login);

        db = CryptoNotesDB.getInstance(this);
        Cursor logins = db.getLogins();

        if (logins.getCount() == 0) {
            moveToAddAccountActivity();
        }

    }

    public void onClickButton(View v) {
        if (v instanceof Button) {
            Button b = (Button) v;
            String buttonText = (String) b.getText();

            EditText pinNumberBox = (EditText) findViewById(R.id.pinNumber);
            Editable txt = pinNumberBox.getText();
            txt.append(buttonText);
        }
    }

    public void onClickClearLast(View v) {
        if (v instanceof Button) {
            EditText pinNumberBox = (EditText) findViewById(R.id.pinNumber);
            Editable txt = pinNumberBox.getText();
            if (txt.length() > 0) {
                int length = txt.length();
                txt.delete(length - 1, length);
            }
        }
    }

    public void onClickOK(View v) {

        if (v instanceof Button) {
            EditText pinNumberBox = (EditText) findViewById(R.id.pinNumber);
            Editable txt = pinNumberBox.getText();
            pinNumberBox.setText("");

            EditText loginEditText = (EditText) findViewById(R.id.login);
            String username = loginEditText.getText().toString();

            int userId = db.checkPasswordAndGetId(username, txt.toString());

            if (userId != -1) {
                Intent intent = new Intent(this, CryptoNotesList.class);
                intent.putExtra(EXTRA_USER_ID, userId);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Błędne dane logowania", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void moveToAddAccountActivity() {
        Intent intent = new Intent(this, CryptoNotesAddUser.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_crypto_notes_login, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_account:
                moveToAddAccountActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

