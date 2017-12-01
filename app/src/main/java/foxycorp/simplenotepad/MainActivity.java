package foxycorp.simplenotepad;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText Data;
    String DataMain="";
    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    TextViewUndoRedo mTextViewUndoRedo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Data = (EditText) findViewById(R.id.editText);
        mTextViewUndoRedo = new TextViewUndoRedo(Data);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1941549848807363~1728941935");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final SQLiteDatabase db;
        db = openOrCreateDatabase("Notepad.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.setVersion(1);
        db.setLocale(Locale.getDefault());
        db.execSQL("CREATE TABLE IF NOT EXISTS NotePadData(data VARCHAR);");

        Cursor cursor = db.rawQuery("Select * from NotePadData", null);
        if (cursor.moveToFirst()) {
            do {
                DataMain = cursor.getString(0);
            } while (cursor.moveToNext());
        }

        Data.setText(DataMain);
        DataMain = Data.getText().toString();
        Data.setSelection(Data.getText().length());

        Data.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                DataMain = Data.getText().toString();
                db.execSQL("INSERT INTO NotePadData VALUES('" + DataMain + "');");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Ads:
                Intent AdIntent = new Intent(this, AdActivity.class);
                startActivity(AdIntent);
                break;
            case R.id.Save:
                try {
                    File myFile = new File(path, "notepad.txt");
                    FileOutputStream fOut = new FileOutputStream(myFile, true);
                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
                    myOutWriter.append(DataMain);
                    myOutWriter.append("\n\r");
                    myOutWriter.append("*************************");
                    myOutWriter.append("\n\r");
                    myOutWriter.close();
                    fOut.close();
                    Toast toast = Toast.makeText(getApplicationContext(), "Your data has been Saved to notepad.txt in your downloads folder", Toast.LENGTH_LONG);
                    toast.show();
                } catch (Exception e) {
                    int PERMISSION_ALL = 1;
                    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS, PERMISSION_ALL);
                    Toast toast = Toast.makeText(getApplicationContext(), "Your data has been Saved to notepad.txt in your downloads folder", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            case R.id.Share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = DataMain;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "*****NotePad*****");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                break;

            case R.id.Undo:
                mTextViewUndoRedo.undo();
                break;

            case R.id.Redo:
                mTextViewUndoRedo.redo();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
