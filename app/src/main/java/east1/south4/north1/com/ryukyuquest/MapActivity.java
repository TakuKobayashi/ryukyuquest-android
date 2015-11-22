package east1.south4.north1.com.ryukyuquest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity_view);
        WebView webview = (WebView) findViewById(R.id.mapWebView);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(Config.ROOT_URL + "map");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationHelper.releaseWebView((WebView) findViewById(R.id.mapWebView));
    }
}
