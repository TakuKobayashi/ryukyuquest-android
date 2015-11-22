package east1.south4.north1.com.ryukyuquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class MapActivity extends AppCompatActivity {
    private MediaPlayer mBg;
    private static final int GotoNextTime = 1000;
    private Handler mHandler;
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_view);
        mHandler = new Handler();
        webview = (WebView) findViewById(R.id.mapWebView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webview.loadUrl(Config.ROOT_URL + "map");
        mBg = MediaPlayer.create(this, R.raw.field);
        mBg.setLooping(true);
        mBg.start();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mBg.release();
                        mBg = null;
                        mBg = MediaPlayer.create(MapActivity.this, R.raw.lastboss);
                        mBg.setLooping(true);
                        mBg.start();
                        recoverActivity();
                    }
                });
            }
        };
        timer.schedule(task, GotoNextTime);
    }

    private void recoverActivity(){
        Intent intent = new Intent(this, BattleActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationHelper.releaseWebView(webview);
        mBg.release();
    }
}
