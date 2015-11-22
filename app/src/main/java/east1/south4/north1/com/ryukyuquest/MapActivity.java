package east1.south4.north1.com.ryukyuquest;

import android.media.MediaPlayer;
import android.os.Bundle;
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

public class MapActivity extends AppCompatActivity {
    private MediaPlayer mBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_view);
        WebView webview = (WebView) findViewById(R.id.mapWebView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(Config.ROOT_URL + "map");
        mBg = MediaPlayer.create(this, R.raw.field);
        mBg.setLooping(true);
        mBg.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationHelper.releaseWebView((WebView) findViewById(R.id.mapWebView));
        mBg.release();
    }
}
