package com.semangatta.lontaraku.bermainaksara.hurufsulit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.BermainAksara;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceBermain;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;
import com.semangatta.lontaraku.bermainaksara.DialogMenang;

import java.util.ArrayList;
import java.util.Random;

public class HSSoal4 extends AppCompatActivity {

    private Animation animScale;
    private BroadcastReceiver sendBroadcastReceiver;

    private boolean mIsBound, mIsBoundTombol, mIsBoundBermain, MusicPause = false;
    private MusicService mServ;
    private MusicServiceBermain mServBermain;
    private MusicServiceTombol mServTombol;

    int buttonSalah = 0;
    TextView Nomor;
    ImageButton btnKembali, btnBantuan, btnLewati, btnSatu, btnDua, btnTiga, btnEmpat ,btnLima ,btnEnam,
                btnTujuh, btnDelapan, btnBantuanTempa, btnTempaSatu, btnTempaDua, btnTempaTiga, btnTempaEmpat,
                btnTempaLima, btnTempaEnam, btnTempaTujuh, btnTempaDelapan, btnOKBantuan, btnYa, btnTidak;
    RelativeLayout DKeluar;
    ImageView bgHitam, bgBantuanB;

    private ServiceConnection Scon =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this,MusicService.class), Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if(mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    private ServiceConnection SconBermain =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServBermain = ((MusicServiceBermain.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name) {
            //mServBermain = null;
        }
    };

    void doBindServiceBermain(){
        bindService(new Intent(this,MusicServiceBermain.class), SconBermain, Context.BIND_AUTO_CREATE);
        mIsBoundBermain = true;
    }

    void doUnbindServiceBermain() {
        if(mIsBoundBermain) {
            unbindService(SconBermain);
            mIsBoundBermain = false;
        }
    }

    private ServiceConnection SconTombol =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder){
            mServTombol = ((MusicServiceTombol.ServiceBinder)binder).getService();
        }
        public void onServiceDisconnected(ComponentName name){
            mServTombol = null;
        }
    };

    void doBindServiceTombol(){
        bindService(new Intent(this,MusicServiceTombol.class), SconTombol, Context.BIND_AUTO_CREATE);
        mIsBoundTombol = true;
    }

    void doUnbindServiceTombol(){
        if(mIsBoundTombol){
            unbindService(SconTombol);
            mIsBoundTombol = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        doBindService();
        doBindServiceBermain();
        doBindServiceTombol();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hss_4);

        tampilNomor();

        Animasi();
        AnimasiBantuan();
        GambarAksaraDasar();
        TombolAksaraDasar();
        PopupBantuan();

        sendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //mServ.pauseMusic();
                mServBermain.pauseMusic();
                MusicPause=true;
            }
        }; registerReceiver(sendBroadcastReceiver , new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }

    public void Animasi(){
        animScale = AnimationUtils.loadAnimation(this, R.anim.anim_scale);
    }

    public void PopupBantuan(){
        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitamBtn);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        //4
        ImageView bgBantuanB = (ImageView) findViewById(R.id.bgBantuan);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann)
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(bgBantuanB);

        ImageView tombolOKBantuan = (ImageView) findViewById(R.id.OKBantuan);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolOKBantuan);
    }

    public void GambarDialogKeluar(){
        ImageView bgHitam = (ImageView) findViewById(R.id.bgHitamDKeluar);
        Glide   .with(this)
                .load(R.drawable.kolo)
                .into(bgHitam);

        ImageView background_bantuan = (ImageView) findViewById(R.id.bgDKeluar);
        Glide   .with(this)
                .load(R.drawable.dialogbantuann) //Ganti
                .placeholder(R.drawable.dialogbantuann)
                .dontAnimate()
                .into(background_bantuan);

        ImageView tombolHome = (ImageView) findViewById(R.id.btnYa);
        Glide   .with(this)
                .load(R.drawable.tombolinfo) //200x200 GANTI
                .into(tombolHome);

        ImageView tombolLanjut = (ImageView) findViewById(R.id.btnTidak);
        Glide   .with(this)
                .load(R.drawable.tombolmusik)
                .into(tombolLanjut);
    }

    public void AnimasiBantuan(){
        Runnable r = new Runnable(){
            public void run(){
                startAnimationButton();
            }
        };
        Handler h = new Handler();
        h.postDelayed(r, 8000);
    }

    private void startAnimationButton() {
        btnBantuan = (ImageButton) findViewById(R.id.bantuan);
        ObjectAnimator scaleYBel = ObjectAnimator.ofFloat(btnBantuan, "scaleY", 0.8f);
        ObjectAnimator scaleYBackBel = ObjectAnimator.ofFloat(btnBantuan, "scaleY", 1f);
        scaleYBel.setDuration(200);
        scaleYBackBel.setDuration(500);
        scaleYBackBel.setInterpolator(new BounceInterpolator());

        final AnimatorSet animatorBel = new AnimatorSet();
        animatorBel.setStartDelay(3000);
        animatorBel.playSequentially(scaleYBel, scaleYBackBel);
        animatorBel.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorBel.start();
            }
        }); animatorBel.start();
    }

    public void TombolDialogKeluar(){
        btnYa = (ImageButton) findViewById(R.id.btnYa);
        btnTidak = (ImageButton) findViewById(R.id.btnTidak);

        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnBantuan = (ImageButton) findViewById(R.id.bantuan);
        btnLewati = (ImageButton) findViewById(R.id.lewati);
        btnSatu = (ImageButton) findViewById(R.id.satu);
        btnDua = (ImageButton) findViewById(R.id.dua);
        btnTiga = (ImageButton) findViewById(R.id.tiga);
        btnEmpat = (ImageButton) findViewById(R.id.empat);
        btnLima = (ImageButton) findViewById(R.id.lima);
        btnEnam = (ImageButton) findViewById(R.id.enam);
        btnTujuh = (ImageButton) findViewById(R.id.tujuh);
        btnDelapan = (ImageButton) findViewById(R.id.delapan);

        btnKembali.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DKeluar = (RelativeLayout) findViewById(R.id.DialogKeluar);
                DKeluar.setVisibility(View.VISIBLE);
                btnKembali.setClickable(false);
                btnBantuan.setClickable(false);
                btnLewati.setClickable(false);
                btnSatu.setClickable(false);
                btnDua.setClickable(false);
                btnTiga.setClickable(false);
                btnEmpat.setClickable(false);
                btnLima.setClickable(false);
                btnEnam.setClickable(false);
                btnTujuh.setClickable(false);
                btnDelapan.setClickable(false);
                mServTombol.startMusic();
                arg0.startAnimation(animScale);
            }
        });

        btnYa.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                mServ.startMusic();
                mServBermain.stopMusic();
                mServTombol.startMusic();
                Intent intent = new Intent(HSSoal4.this,BermainAksara.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        btnTidak.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DKeluar = (RelativeLayout) findViewById(R.id.DialogKeluar);
                DKeluar.setVisibility(View.GONE);
                mServTombol.startMusic();
                btnKembali.setClickable(true);
                btnBantuan.setClickable(true);
                btnLewati.setClickable(true);
                btnSatu.setClickable(true);
                btnDua.setClickable(true);
                btnTiga.setClickable(true);
                btnEmpat.setClickable(true);
                btnLima.setClickable(true);
                btnEnam.setClickable(true);
                btnTujuh.setClickable(true);
                btnDelapan.setClickable(true);
            }
        });
    }

    public void GambarAksaraDasar() {
        ImageView background_image = (ImageView) findViewById(R.id.background_image);
        Glide   .with(this)
                .load(R.drawable.menuutamadua) //ganti
                .centerCrop()
                .into(background_image);

        //button menu belajar aksara
        ImageView tombolkembali = (ImageView) findViewById(R.id.tombolkembali);
        Glide   .with(this)
                .load(R.drawable.tombolkembali)
                .into(tombolkembali);

        GambarSoalSatu();
        GambarDialogKeluar();
    }

    public void TombolAksaraDasar() {
        TombolSoalSatu();
        TombolDialogKeluar();
    }

    //LAYOUT SATU
    public void GambarSoalSatu(){

        //tombol bantuan & lewati
        ImageView tombolBantuan = (ImageView) findViewById(R.id.bantuan);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolBantuan);
        ImageView tombolBantuanTempa = (ImageView) findViewById(R.id.bantuantempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolBantuanTempa);

        ImageView tombolLewati = (ImageView) findViewById(R.id.lewati);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolLewati);

        //jawaban soal
        ImageView tombolSatuTempa = (ImageView) findViewById(R.id.satutempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolSatuTempa);
        ImageView tombolSatu = (ImageView) findViewById(R.id.satu);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolSatu);

        ImageView tombolDuaTempa = (ImageView) findViewById(R.id.duatempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolDuaTempa);
        ImageView tombolDua = (ImageView) findViewById(R.id.dua);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolDua);

        ImageView tombolTigaTempa = (ImageView) findViewById(R.id.tigatempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolTigaTempa);
        ImageView tombolTiga = (ImageView) findViewById(R.id.tiga);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolTiga);

        ImageView tombolEmpatTempa = (ImageView) findViewById(R.id.empattempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolEmpatTempa);
        ImageView tombolEmpat = (ImageView) findViewById(R.id.empat);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolEmpat);

        //benar
        ImageView tombolLimaTempa = (ImageView) findViewById(R.id.limatempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolLimaTempa);
        ImageView tombolLima = (ImageView) findViewById(R.id.lima);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolLima);

        ImageView tombolEnamTempa = (ImageView) findViewById(R.id.enamtempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolEnamTempa);
        ImageView tombolEnam = (ImageView) findViewById(R.id.enam);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolEnam);

        ImageView tombolTujuhTempa = (ImageView) findViewById(R.id.tujuhtempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolTujuhTempa);
        ImageView tombolTujuh = (ImageView) findViewById(R.id.tujuh);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolTujuh);

        ImageView tombolDelapanTempa = (ImageView) findViewById(R.id.delapantempa);
        Glide   .with(this)
                .load(R.drawable.left) //GANTI
                .into(tombolDelapanTempa);
        ImageView tombolDelapan = (ImageView) findViewById(R.id.delapan);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolDelapan);
    }

    public void TombolSoalSatu(){

        //tempa
        btnBantuanTempa = (ImageButton) findViewById(R.id.bantuantempa);
        btnTempaSatu = (ImageButton) findViewById(R.id.satutempa);
        btnTempaDua = (ImageButton) findViewById(R.id.duatempa);
        btnTempaTiga = (ImageButton) findViewById(R.id.tigatempa);
        btnTempaEmpat = (ImageButton) findViewById(R.id.empattempa);
        btnTempaLima = (ImageButton) findViewById(R.id.limatempa);
        btnTempaEnam = (ImageButton) findViewById(R.id.enamtempa);
        btnTempaTujuh = (ImageButton) findViewById(R.id.tujuhtempa);
        btnTempaDelapan = (ImageButton) findViewById(R.id.delapantempa);

        //tombol
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnBantuan = (ImageButton) findViewById(R.id.bantuan);
        btnLewati = (ImageButton) findViewById(R.id.lewati);
        btnSatu = (ImageButton) findViewById(R.id.satu);
        btnDua = (ImageButton) findViewById(R.id.dua);
        btnTiga = (ImageButton) findViewById(R.id.tiga);
        btnEmpat = (ImageButton) findViewById(R.id.empat);
        btnLima = (ImageButton) findViewById(R.id.lima);
        btnEnam = (ImageButton) findViewById(R.id.enam);
        btnTujuh = (ImageButton) findViewById(R.id.tujuh);
        btnDelapan = (ImageButton) findViewById(R.id.delapan);

        //bantuan
        bgHitam = (ImageView) findViewById(R.id.bgHitamBtn);
        bgBantuanB = (ImageView) findViewById(R.id.bgBantuan);
        btnOKBantuan = (ImageButton) findViewById(R.id.OKBantuan);

        btnBantuan.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                btnBantuan.setVisibility(View.GONE);
                btnBantuanTempa.setVisibility(View.VISIBLE);

                bgHitam.setVisibility(View.VISIBLE);
                bgBantuanB.setVisibility(View.VISIBLE);
                bgBantuanB.setAnimation(animScale);
                btnOKBantuan.setVisibility(View.VISIBLE);
                btnOKBantuan.setAnimation(animScale);

                btnKembali.setClickable(false);
                btnBantuan.setClickable(false);
                btnLewati.setClickable(false);
                btnSatu.setClickable(false);
                btnDua.setClickable(false);
                btnTiga.setClickable(false);
                btnEmpat.setClickable(false);
                btnLima.setClickable(false);
                btnEnam.setClickable(false);
                btnTujuh.setClickable(false);
                btnDelapan.setClickable(false);

                btnOKBantuan.setOnClickListener(new ImageButton.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        bgHitam.setVisibility(View.GONE);
                        bgBantuanB.setVisibility(View.GONE);
                        btnOKBantuan.setVisibility(View.GONE);

                        btnKembali.setClickable(true);
                        btnBantuan.setClickable(true);
                        btnLewati.setClickable(true);
                        btnSatu.setClickable(true);
                        btnDua.setClickable(true);
                        btnTiga.setClickable(true);
                        btnEmpat.setClickable(true);
                        btnLima.setClickable(true);
                        btnEnam.setClickable(true);
                        btnTujuh.setClickable(true);
                        btnDelapan.setClickable(true);
                    }
                });
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
            }
        });

        btnLewati.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                mServTombol.startMusic();

                ArrayList<Class> activityList = new ArrayList<>();
                Bundle extras = getIntent().getExtras();
                activityList = ((ArrayList)extras.get("ACTIVITY_LIST"));

                if(activityList.size() == 5) {
                    dialogMenang();
                } else {
                    Random generator = new Random();
                    int number = generator.nextInt(activityList.size()) + 1;
                    Class activity = null;

                    switch(number) {
                        case 1: activity = activityList.get(0);
                            activityList.remove(0);
                            break;
                        case 2: activity = activityList.get(1);
                            activityList.remove(1);
                            break;
                        case 3: activity = activityList.get(2);
                            activityList.remove(2);
                            break;
                        case 4: activity = activityList.get(3);
                            activityList.remove(3);
                            break;
                        case 5: activity = activityList.get(4);
                            activityList.remove(4);
                            break;
                        case 6: activity = activityList.get(5);
                            activityList.remove(5);
                            break;
                        case 7: activity = activityList.get(6);
                            activityList.remove(6);
                            break;
                        case 8: activity = activityList.get(7);
                            activityList.remove(7);
                            break;
                        case 9: activity = activityList.get(8);
                            activityList.remove(8);
                            break;
                        default:
                            activity = activityList.get(9);
                            activityList.remove(9);
                            break;
                    }   Intent intent = new Intent(getBaseContext(), activity);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("ACTIVITY_LIST", activityList);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                }
            }
        });

        btnSatu.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                buttonSalah++;
                dialogKalah();
                mServTombol.startMusicWrong();
                btnSatu.setVisibility(View.GONE);
                btnTempaSatu.setVisibility(View.VISIBLE);
            }
        });

        btnDua.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                buttonSalah++;
                dialogKalah();
                mServTombol.startMusicWrong();
                btnDua.setVisibility(View.GONE);
                btnTempaDua.setVisibility(View.VISIBLE);

            }
        });

        btnTiga.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                buttonSalah++;
                dialogKalah();
                mServTombol.startMusicWrong();
                btnTiga.setVisibility(View.GONE);
                btnTempaTiga.setVisibility(View.VISIBLE);
            }
        });

        btnEmpat.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                buttonSalah++;
                dialogKalah();
                mServTombol.startMusicWrong();
                btnEmpat.setVisibility(View.GONE);
                btnTempaEmpat.setVisibility(View.VISIBLE);
            }
        });

        //benar
        btnLima.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                btnKembali.setClickable(false);
                btnBantuan.setClickable(false);
                btnLewati.setClickable(false);
                btnSatu.setClickable(false);
                btnDua.setClickable(false);
                btnTiga.setClickable(false);
                btnEmpat.setClickable(false);
                btnLima.setClickable(false);
                btnEnam.setClickable(false);

                btnLima.setVisibility(View.GONE);
                btnTempaLima.setVisibility(View.VISIBLE);
                arg0.startAnimation(animScale);
                mServTombol.startMusicCorrect();

                Runnable r = new Runnable(){
                    public void run(){

                ArrayList<Class> activityList = new ArrayList<>();
                Bundle extras = getIntent().getExtras();
                activityList = ((ArrayList)extras.get("ACTIVITY_LIST"));


                if(activityList.size() == 5) {
                    dialogMenang();
                } else {
                    Random generator = new Random();
                    int number = generator.nextInt(activityList.size()) + 1;
                    Class activity = null;

                    switch(number) {
                        case 1: activity = activityList.get(0);
                                activityList.remove(0);
                                break;
                        case 2: activity = activityList.get(1);
                                activityList.remove(1);
                                break;
                        case 3: activity = activityList.get(2);
                                activityList.remove(2);
                                break;
                        case 4: activity = activityList.get(3);
                                activityList.remove(3);
                                break;
                        case 5: activity = activityList.get(4);
                                activityList.remove(4);
                                break;
                        case 6: activity = activityList.get(5);
                                activityList.remove(5);
                                break;
                        case 7: activity = activityList.get(6);
                                activityList.remove(6);
                                break;
                        case 8: activity = activityList.get(7);
                                activityList.remove(7);
                                break;
                        case 9: activity = activityList.get(8);
                                activityList.remove(8);
                                break;
                        default:
                                activity = activityList.get(9);
                                activityList.remove(9);
                                break;
                    }   Intent intent = new Intent(getBaseContext(), activity);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("ACTIVITY_LIST", activityList);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                    }
                    }
                };
                Handler h = new Handler();
                h.postDelayed(r, 1000);
            }
        });

        btnEnam.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                buttonSalah++;
                dialogKalah();
                mServTombol.startMusicWrong();
                btnEnam.setVisibility(View.GONE);
                btnTempaEnam.setVisibility(View.VISIBLE);
            }
        });

        btnTujuh.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                buttonSalah++;
                dialogKalah();
                mServTombol.startMusicWrong();
                btnTujuh.setVisibility(View.GONE);
                btnTempaTujuh.setVisibility(View.VISIBLE);
            }
        });

        btnDelapan.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                arg0.startAnimation(animScale);
                buttonSalah++;
                dialogKalah();
                mServTombol.startMusicWrong();
                btnDelapan.setVisibility(View.GONE);
                btnTempaDelapan.setVisibility(View.VISIBLE);
            }
        });
    }

    public  void tampilNomor(){
        ArrayList<Class> activityList = new ArrayList<>();
        Bundle extras = getIntent().getExtras();
        activityList = ((ArrayList)extras.get("ACTIVITY_LIST"));

        if(activityList.size() == 9) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("1");
        } if(activityList.size() == 8) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("2");
        } if(activityList.size() == 7) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("3");
        } if(activityList.size() == 6) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("4");
        } if(activityList.size() == 5) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("5");
        } if(activityList.size() == 4) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("6");
        } if(activityList.size() == 3) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("7");
        } if(activityList.size() == 2) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("8");
        } if(activityList.size() == 1) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("9");
        } if(activityList.size() == 0) {
            Nomor = (TextView) findViewById(R.id.hitung);
            Nomor.setText("10");
        }
    }

    public void dialogKalah() {
        int tebakbuttonsalah = 3;
        if (tebakbuttonsalah == buttonSalah) {
            Intent intent = new Intent(HSSoal4.this, DialogKalahHS.class);
            buttonSalah = 0;
            mServBermain.inMusic();
            if(!mServBermain.isPlaying()) {
                mServTombol.stopMusicKalah();
            } else {
                mServTombol.playMusicKalah();
            } startActivity(intent);
        }
    }

    public void dialogMenang(){
        Intent intent = new Intent(HSSoal4.this, DialogMenang.class);
        mServBermain.inMusic();
        if(!mServBermain.isPlaying()) {
            mServTombol.stopMusicMenang();
        } else {
            mServTombol.playMusicMenang();
        } startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        doUnbindService();
        doUnbindServiceTombol();
        doUnbindServiceBermain();
        unregisterReceiver(sendBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public final void onResume() {
        if(MusicPause) {
            mServBermain.resumeMusic();
            mServ.resumeMusic2();
        }super.onResume();
    }

    @Override
    public void onBackPressed() {
        btnKembali = (ImageButton) findViewById(R.id.tombolkembali);
        btnBantuan = (ImageButton) findViewById(R.id.bantuan);
        btnLewati = (ImageButton) findViewById(R.id.lewati);
        btnSatu = (ImageButton) findViewById(R.id.satu);
        btnDua = (ImageButton) findViewById(R.id.dua);
        btnTiga = (ImageButton) findViewById(R.id.tiga);
        btnEmpat = (ImageButton) findViewById(R.id.empat);
        btnLima = (ImageButton) findViewById(R.id.lima);
        btnEnam = (ImageButton) findViewById(R.id.enam);
        btnTujuh = (ImageButton) findViewById(R.id.tujuh);
        btnDelapan = (ImageButton) findViewById(R.id.delapan);

        DKeluar = (RelativeLayout) findViewById(R.id.DialogKeluar);
        DKeluar.setVisibility(View.VISIBLE);

        btnKembali.setClickable(false);
        btnBantuan.setClickable(false);
        btnLewati.setClickable(false);
        btnSatu.setClickable(false);
        btnDua.setClickable(false);
        btnTiga.setClickable(false);
        btnEmpat.setClickable(false);
        btnLima.setClickable(false);
        btnEnam.setClickable(false);
        btnTujuh.setClickable(false);
        btnDelapan.setClickable(false);
    }
}
