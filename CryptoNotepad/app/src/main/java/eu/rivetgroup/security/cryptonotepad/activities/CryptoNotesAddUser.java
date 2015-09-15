package eu.rivetgroup.security.cryptonotepad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import eu.rivetgroup.security.cryptonotepad.R;
import eu.rivetgroup.security.cryptonotepad.utils.CryptoNotesDB;

public class CryptoNotesAddUser extends BaseCryptoActivity {

    CryptoNotesDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notes_add_user);

        db = CryptoNotesDB.getInstance(this);
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

    public void onClickAddUser(View v) {
        if (v instanceof Button) {
            EditText loginBox = (EditText) findViewById(R.id.login);
            EditText pinNumberBox = (EditText) findViewById(R.id.pinNumber);

            String login = loginBox.getText().toString();
            String pinNumber = pinNumberBox.getText().toString();

            if (db.addUser(login, pinNumber)) {
                Toast.makeText(this, "Konto zostało dodane.", Toast.LENGTH_SHORT).show();
                pinNumberBox.setText("");
                finish();
            } else {
                Toast.makeText(this, "Login lub hasło niepoprawne.", Toast.LENGTH_SHORT).show();
            }

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
        onClickAddUser(v);
    }

}
