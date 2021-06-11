package pk.shop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class Initial extends MainActivity {

    private static int SPLASH_SCREEN = 2000;
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.initial);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image = findViewById(R.id.imageView);
        txt = findViewById(R.id.textView);
        image.setAnimation(topAnim);
        txt.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Initial.this, Login.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}
