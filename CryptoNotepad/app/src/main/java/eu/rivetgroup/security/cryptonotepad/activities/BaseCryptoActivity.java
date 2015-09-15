package eu.rivetgroup.security.cryptonotepad.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import eu.rivetgroup.security.cryptonotepad.R;

public abstract class BaseCryptoActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_crypto_notes, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.about:
                moveToAboutActivity();
                break;
        }
        return true;
    }

    public void moveToAboutActivity()
    {
        Intent intent = new Intent(this, CryptoNotesAbout.class);
        startActivity(intent);
    }
}
