package east1.south4.north1.com.ryukyuquest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(ExtraLayout.getInstance(ExtraLayout.class).getParenetView(R.layout.activity_main));
        ImageView image = (ImageView) findViewById(R.id.topImageView);
        image.setImageResource(R.mipmap.start_top);
        ButterKnife.bind(this);
        requestPermission();
    }

    private void requestPermission(){
        if(Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> permissions = ApplicationHelper.getSettingPermissions(this);
            boolean isRequestPermission = false;
            for(String permission : permissions){
                if(!ApplicationHelper.hasSelfPermission(this, permission)){
                    isRequestPermission = true;
                    break;
                }
            }
            if(isRequestPermission) {
                requestPermissions(permissions.toArray(new String[0]), REQUEST_CODE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.topImageView));
    }

    @OnClick(R.id.startButton)
    public void onClickStart(View v){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
