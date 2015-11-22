package east1.south4.north1.com.ryukyuquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        ImageButton startButton = (ImageButton) findViewById(R.id.startButton);
        startButton.setImageResource(R.mipmap.start_button);
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
        ApplicationHelper.releaseImageView((ImageButton) findViewById(R.id.startButton));
    }

    @OnClick(R.id.startButton)
    public void onClickStart(View v){
        SharedPreferences sp = Preferences.getCommonPreferences(this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("authToken", sp.getString("authToken", ""));
        httpRequest(Request.Method.POST, Config.ROOT_URL + "login", params, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                User user = gson.fromJson(response, User.class);
                Preferences.saveCommonParam(MainActivity.this, "authToken", user.authToken);
                Log.d(Config.DEBUG_KEY, "au:" + user.authToken);
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
    }

    private void httpRequest(int method, String url , final Map<String, String> params, Response.Listener response){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(method ,url, response, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.d(Config.DEBUG_KEY, "error:" + error.getMessage());
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        queue.add(request);
    }
}
