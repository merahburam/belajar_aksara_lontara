package com.semangatta.lontaraku.aksaradasar;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.MusicService;
import com.semangatta.lontaraku.MusicServiceTombol;
import com.semangatta.lontaraku.R;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiAa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiCa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiHa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiJa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiLa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiNca;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiNya;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiRa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiSa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiWa;
import com.semangatta.lontaraku.aksaradasar.dasardua.DialogBunyiYa;

public class DasarDuaFragment extends Fragment {

    private Animation animScale;
    ImageButton btnCa, btnJa, btnNya, btnNca, btnYa, btnRa, btnLa, btnWa, btnSa, btnAa, btnHa;
    ImageView   panel, tombolCa, tombolJa, tombolNya, tombolNca, tombolYa,
                tombolRa, tombolLa, tombolWa, tombolSa, tombolAa, tombolHa;

    private boolean mIsBound, mIsBoundTombol = false;
    private MusicService mServ;
    private MusicServiceTombol mServTombol;

    private ServiceConnection Scon =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        } public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        getActivity().bindService(new Intent(getContext(),MusicService.class), Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if(mIsBound) {
            getActivity().unbindService(Scon);
            mIsBound = false;
        }
    }

    private ServiceConnection SconTombol =new ServiceConnection(){
        public void onServiceConnected(ComponentName name, IBinder binder){
            mServTombol = ((MusicServiceTombol.ServiceBinder)binder).getService();
        } public void onServiceDisconnected(ComponentName name){
            mServTombol = null;
        }
    };

    void doBindServiceTombol(){
        getActivity().bindService(new Intent(getContext(),MusicServiceTombol.class), SconTombol, Context.BIND_AUTO_CREATE);
        mIsBoundTombol = true;
    }

    void doUnbindServiceTombol(){
        if(mIsBoundTombol){
            getActivity().unbindService(SconTombol);
            mIsBoundTombol = false;
        }
    }

    public DasarDuaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        doBindService();
        doBindServiceTombol();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_aksaradasar_dua, container, false);

        Animasi();

        panel = (ImageView) rootView.findViewById(R.id.Panel);
        Glide   .with(this)
                .load(R.drawable.panel) //ganti
                .centerCrop()
                .into(panel);

        ImageView pJudul = (ImageView) rootView.findViewById(R.id.panelJudul);
        Glide   .with(this)
                .load(R.drawable.patas)
                .into(pJudul);

        ImageView Judul = (ImageView) rootView.findViewById(R.id.Judul);
        Glide   .with(this)
                .load(R.drawable.tbdasar)
                .into(Judul);

        //gambar tombol
        tombolCa = (ImageView) rootView.findViewById(R.id.Ca);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolCa);
        tombolJa = (ImageView) rootView.findViewById(R.id.Ja);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolJa);
        tombolNya = (ImageView) rootView.findViewById(R.id.Nya);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolNya);
        tombolNca = (ImageView) rootView.findViewById(R.id.Nca);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolNca);
        tombolYa = (ImageView) rootView.findViewById(R.id.Ya);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolYa);
        tombolRa = (ImageView) rootView.findViewById(R.id.Ra);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolRa);
        tombolLa = (ImageView) rootView.findViewById(R.id.La);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolLa);
        tombolWa = (ImageView) rootView.findViewById(R.id.Wa);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolWa);
        tombolSa = (ImageView) rootView.findViewById(R.id.Sa);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolSa);
        tombolAa = (ImageView) rootView.findViewById(R.id.AA);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolAa);
        tombolHa = (ImageView) rootView.findViewById(R.id.Ha);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolHa);

        //tombol aksara bunyi
        btnCa = (ImageButton) tombolCa;
        btnJa = (ImageButton) tombolJa;
        btnNya = (ImageButton)tombolNya;
        btnNca = (ImageButton)tombolNca;
        btnYa = (ImageButton) tombolYa;
        btnRa = (ImageButton) tombolRa;
        btnLa = (ImageButton)tombolLa;
        btnWa = (ImageButton)tombolWa;
        btnSa = (ImageButton) tombolSa;
        btnAa = (ImageButton)tombolAa;
        btnHa = (ImageButton)tombolHa;

        btnCa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiCa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnJa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiJa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnNya.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiNya.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnNca.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiNca.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnYa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiYa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnRa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiRa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnLa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiLa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnWa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiWa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnSa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiSa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnAa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiAa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnHa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiHa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        return rootView;
    }

    @Override
    public void onDestroy() {
        doUnbindService();
        doUnbindServiceTombol();
        super.onDestroy();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void Animasi(){
        animScale = AnimationUtils.loadAnimation(getContext(), R.anim.anim_scale);
    }

    public static DasarDuaFragment newInstance(String text) {
        DasarDuaFragment f = new DasarDuaFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}
