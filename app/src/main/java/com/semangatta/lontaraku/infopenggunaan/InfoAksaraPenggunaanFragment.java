package com.semangatta.lontaraku.infopenggunaan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.semangatta.lontaraku.R;

import static com.semangatta.lontaraku.R.drawable.panel;

public class InfoAksaraPenggunaanFragment extends Fragment {

    ImageView panel1, panel2;

    public InfoAksaraPenggunaanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_info_aksara_penggunaan, container, false
        );

        panel1 = (ImageView) rootView.findViewById(R.id.Panel);
        Glide   .with(this)
                .load(R.drawable.panel) //ganti kemungkinan bakal pake placeholder & dontanimate
                .centerCrop()
                .into(panel1);

        ImageView pJudul = (ImageView) rootView.findViewById(R.id.panelJudul);
        Glide   .with(this)
                .load(R.drawable.patas)
                .into(pJudul);

        ImageView Judul = (ImageView) rootView.findViewById(R.id.Judul);
        Glide   .with(this)
                .load(R.drawable.tbpenggunaan)
                .into(Judul);

        panel2 = (ImageView) rootView.findViewById(R.id.Panel2);
        Glide   .with(this)
                .load(R.drawable.panel) //ganti kemungkinan bakal pake placeholder & dontanimate
                .centerCrop()
                .into(panel2);

        return rootView;
    }

    public static InfoAksaraPenggunaanFragment newInstance(String text) {
        InfoAksaraPenggunaanFragment f = new InfoAksaraPenggunaanFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);
        return f;
    }
}
