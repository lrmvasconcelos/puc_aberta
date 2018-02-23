package br.pucminas.pucaberta;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import br.pucminas.pucaberta.R;

/**
 * Created by lucas on 05/05/17.
 */

public class SplashActivity extends Activity
{

    private final int PRESENTATION_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mIntent = new Intent(SplashActivity.this, MainActivity.class);

                SplashActivity.this.startActivity(mIntent);

                SplashActivity.this.overridePendingTransition(R.anim.push_right_out, R.anim.push_right_in);

                SplashActivity.this.finish();

            }
        }, PRESENTATION_TIME);

    }


}
