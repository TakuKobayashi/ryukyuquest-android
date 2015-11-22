package east1.south4.north1.com.ryukyuquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecoverActivity extends AppCompatActivity {
    private MediaPlayer mBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(ExtraLayout.getInstance(ExtraLayout.class).getParenetView(R.layout.recover_view));

        ImageView bg = (ImageView) findViewById(R.id.recoverBgImage);
        bg.setImageResource(R.mipmap.background);

        ImageView image = (ImageView) findViewById(R.id.recoverCommentImage);
        image.setImageResource(R.mipmap.recovery_comment);

        SharedPreferences sp = Preferences.getCommonPreferences(this);
        if(sp.getString("recoverWord", null) == null) {
            Preferences.saveCommonParam(this, "recoverWord", UUID.randomUUID().toString());
        }
        ImageButton yesButton = (ImageButton) findViewById(R.id.recoverYesButton);
        yesButton.setImageResource(R.mipmap.recovery_yes_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = Preferences.getCommonPreferences(RecoverActivity.this);
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setPackage("com.twitter.android");
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_TEXT, "誰か助けて〜!!復活の呪文はこちら:" + sp.getString("recoverWord", ""));
                startActivity(intent);
                finish();
            }
        });

        ImageButton noButton = (ImageButton) findViewById(R.id.recoverNoButton);
        noButton.setImageResource(R.mipmap.recovery_no_button);
        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBg = MediaPlayer.create(this, R.raw.dead);
        mBg.setLooping(true);
        mBg.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.recoverCommentImage));
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.recoverBgImage));
        ApplicationHelper.releaseImageView((ImageButton) findViewById(R.id.recoverYesButton));
        ApplicationHelper.releaseImageView((ImageButton) findViewById(R.id.recoverNoButton));
        mBg.release();
    }
}
