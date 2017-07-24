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
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiBa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiDa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiGa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiKa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiMa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiMpa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiNa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiNga;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiNgka;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiNra;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiPa;
import com.semangatta.lontaraku.aksaradasar.dasarsatu.DialogBunyiTa;

public class DasarSatuFragment extends Fragment {

    private Animation animScale;
    ImageButton btnKa, btnGa, btnNga, btnNgka, btnPa, btnBa, btnMa, btnMpa, btnTa, btnDa, btnNa, btnNra;
    ImageView   panel, tombolKa, tombolGa, tombolNga, tombolNgka, tombolPa,
                tombolBa, tombolMa, tombolMpa, tombolTa, tombolDa, tombolNa, tombolNra;

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

    public DasarSatuFragment() {
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
        doBindService();
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_aksaradasar_satu, container, false);

        Animasi();

        panel = (ImageView) rootView.findViewById(R.id.Panel);
        Glide   .with(this)
                .load(R.drawable.panel) //Ganti
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
        tombolKa = (ImageView) rootView.findViewById(R.id.Ka);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolKa);
        tombolGa = (ImageView) rootView.findViewById(R.id.Ga);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolGa);
        tombolNga = (ImageView) rootView.findViewById(R.id.Nga);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolNga);
        tombolNgka = (ImageView) rootView.findViewById(R.id.Ngka);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolNgka);
        tombolPa = (ImageView) rootView.findViewById(R.id.Pa);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolPa);
        tombolBa = (ImageView) rootView.findViewById(R.id.Ba);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolBa);
        tombolMa = (ImageView) rootView.findViewById(R.id.Ma);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolMa);
        tombolMpa = (ImageView) rootView.findViewById(R.id.Mpa);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolMpa);
        tombolTa = (ImageView) rootView.findViewById(R.id.Ta);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolTa);
        tombolDa = (ImageView) rootView.findViewById(R.id.Da);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolDa);
        tombolNa = (ImageView) rootView.findViewById(R.id.Na);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolNa);
        tombolNra = (ImageView) rootView.findViewById(R.id.Nra);
        Glide   .with(this)
                .load(R.drawable.right) //GANTI
                .into(tombolNra);

        //tombol aksara bunyi
        btnKa = (ImageButton) tombolKa;
        btnGa = (ImageButton) tombolGa;
        btnNga = (ImageButton)tombolNga;
        btnNgka = (ImageButton)tombolNgka;
        btnPa = (ImageButton) tombolPa;
        btnBa = (ImageButton) tombolBa;
        btnMa = (ImageButton)tombolMa;
        btnMpa = (ImageButton)tombolMpa;
        btnTa = (ImageButton) tombolTa;
        btnDa = (ImageButton)tombolDa;
        btnNa = (ImageButton)tombolNa;
        btnNra = (ImageButton)tombolNra;

        btnKa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiKa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnGa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiGa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnNga.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiNga.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnNgka.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(), DialogBunyiNgka.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnPa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiPa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnBa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiBa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnMa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiMa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnMpa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiMpa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnTa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiTa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnDa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiDa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnNa.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiNa.class);
                ActivityOptions options = ActivityOptions.makeScaleUpAnimation(arg0, 0,0, arg0.getWidth(), arg0.getHeight());
                startActivity(intent, options.toBundle());
                arg0.startAnimation(animScale);
                mServTombol.startMusic();
                mServ.inMusic();
            }
        });
        btnNra.setOnClickListener(new ImageButton.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getContext(),DialogBunyiNra.class);
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

    public static DasarSatuFragment newInstance(String text) {
        DasarSatuFragment f = new DasarSatuFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}
