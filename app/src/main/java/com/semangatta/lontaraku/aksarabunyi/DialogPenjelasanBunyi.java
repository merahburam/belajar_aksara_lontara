package com.semangatta.lontaraku.aksarabunyi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.R;

public class DialogPenjelasanBunyi extends Activity {

    //private Animation animScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_penjelasan_bunyi);
        overridePendingTransition(R.anim.anim_scale, R.anim.anim_scale);

        //Animasi();
        GambarDialogBunyi();
        TombolDialogBunyi();
    }

    /*public void Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
    }*/

    public void GambarDialogBunyi(){
        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitam);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        ImageView background_bantuan = (ImageView) findViewById(R.id.background_bantuan);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann)
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(background_bantuan);

        //content button
        ImageView tombolbatal = (ImageView) findViewById(R.id.tombolkeluar);
        Glide   .with(this)
                .load(R.drawable.tombolbataldua) //150
                .into(tombolbatal);
    }

    public void TombolDialogBunyi(){
        ImageButton btnKeluar = (ImageButton) findViewById(R.id.tombolkeluar);
        btnKeluar.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(DialogPenjelasanBunyi.this,AksaraBunyi.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public final void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DialogPenjelasanBunyi.this,AksaraBunyi.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
