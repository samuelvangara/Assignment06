package foxycorp.simplenotepad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AdActivity extends AppCompatActivity {

    private Button mNextLevelButton;
    private Button Back;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad);

        mNextLevelButton = ((Button) findViewById(R.id.next_level_button));
        Back = ((Button) findViewById(R.id.Back));
        mNextLevelButton.setEnabled(false);
        Back.setEnabled(false);
        mNextLevelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMainActivity();
            }
        });
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mNextLevelButton.setEnabled(true);
                Back.setEnabled(true);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                mNextLevelButton.setEnabled(true);
                Back.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                goToMainActivity();
            }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load,Please Click on the Show Ad button again!", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    private void loadInterstitial() {
        mNextLevelButton.setEnabled(false);
        Back.setEnabled(false);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }

    private void goToNextLevel() {
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
    }

    public void goToMainActivity() {
        Intent AdIntent = new Intent(this, MainActivity.class);
        AdIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(AdIntent);
    }
}