package eu.rivetgroup.security.cryptonotepad.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import eu.rivetgroup.security.cryptonotepad.R;

import static android.content.Intent.ACTION_VIEW;

public class CryptoNotesAbout extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_notes_about);

        TextView about_app = (TextView)findViewById(R.id.about_app);
        about_app.setText(Html.fromHtml(getString(R.string.info_app)));

        TextView about_company = (TextView)findViewById(R.id.about_company);
        about_company.setText(Html.fromHtml(getString(R.string.info_rvg)));

        TextView about_group = (TextView)findViewById(R.id.about_group);
        about_group.setText(Html.fromHtml(getString(R.string.info_security)));

        TextView about_other = (TextView)findViewById(R.id.about_other);
        about_other.setText(Html.fromHtml(getString(R.string.info_other)));
    }

    public void openRivet(View view)
    {
        goToUrl("http://rivetgroup.eu");
    }

    public void openSecurity(View view)
    {
        goToUrl("http://security.rivetgroup.eu");
    }

    public void openKnow(View view)
    {
        goToUrl("http://know.rivetgroup.eu");
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
