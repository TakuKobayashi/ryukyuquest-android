package east1.south4.north1.com.ryukyuquest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.UUID;

public class BattleActivity extends AppCompatActivity {
    private MediaPlayer mBg;
    private ImageView mEnemyBar;
    private ImageView mUserBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(ExtraLayout.getInstance(ExtraLayout.class).getParenetView(R.layout.battle_view));

        ImageView bg = (ImageView) findViewById(R.id.battleBgImage);
        bg.setImageResource(R.mipmap.background);

        ImageView enemyBg = (ImageView) findViewById(R.id.enemybarbgImage);
        enemyBg.setImageResource(R.mipmap.enemy_bar);

        mEnemyBar = (ImageView) findViewById(R.id.enemybarImage);
        mEnemyBar.setImageResource(R.mipmap.enemy_hp_bar);

        ImageView userBg = (ImageView) findViewById(R.id.userbarbgImage);
        userBg.setImageResource(R.mipmap.user_bar);

        mUserBar = (ImageView) findViewById(R.id.userbarImage);
        mUserBar.setImageResource(R.mipmap.stamina);

        ImageView commentImage = (ImageView) findViewById(R.id.battleComment);
        commentImage.setImageResource(R.mipmap.coment_bar);

        ImageView enemy = (ImageView) findViewById(R.id.enemyImage);
        enemy.setImageResource(R.mipmap.enemy);

        mBg = MediaPlayer.create(this, R.raw.lastboss);
        mBg.setLooping(true);
        mBg.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.enemyImage));
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.battleComment));
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.userbarbgImage));
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.enemybarbgImage));
        ApplicationHelper.releaseImageView((ImageView) findViewById(R.id.battleBgImage));
        ApplicationHelper.releaseImageView(mEnemyBar);
        ApplicationHelper.releaseImageView(mUserBar);
        mBg.release();
    }
}
