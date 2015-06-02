package com.db.circularcounterdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.db.circularcounter.CircularCounter;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment
        extends Fragment
        implements View.OnClickListener{

    private CircularCounter meter;

    private String[] colors;

    private Handler handler;

    private Runnable r;

    private Button btnMore;

    private Button btnLess;

    private Button btnAuto;

    private boolean isBtnAutoOn;


    public MainActivityFragment() {
        handler = new Handler();
        r = new Runnable(){
            int currV = 0;
            boolean go = true;
            public void run(){
                if(currV == 60 && go)
                    go = false;
                else if(currV == -60 && !go)
                    go = true;

                if(go)
                    currV++;
                else
                    currV--;

                meter.setValues(currV, currV*2, currV*3);
                handler.postDelayed(this, 50);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isBtnAutoOn=false;

        btnMore=(Button)view.findViewById(R.id.btnMore);
        btnLess=(Button)view.findViewById(R.id.btnLess);
        btnAuto=(Button)view.findViewById(R.id.btnAuto);

        btnMore.setOnClickListener(this);
        btnLess.setOnClickListener(this);
        btnAuto.setOnClickListener(this);

        colors = getResources().getStringArray(R.array.colors);

        meter = (CircularCounter) view.findViewById(R.id.meter);

        meter.setFirstWidth(getResources().getDimension(R.dimen.first))
                .setFirstColor(Color.parseColor(colors[0]))

                .setSecondWidth(getResources().getDimension(R.dimen.second))
                .setSecondColor(Color.parseColor(colors[1]))

                .setThirdWidth(getResources().getDimension(R.dimen.third))
                .setThirdColor(Color.parseColor(colors[2]))

                .setBackgroundColor(-14606047);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLess:
                meter.setValues(meter.getValue1()-1,
                        meter.getValue2()-2,
                        meter.getValue3()-3);
                break;

            case R.id.btnMore:
                meter.setValues(meter.getValue1()+1,
                        meter.getValue2()+2,
                        meter.getValue3()+3);
                break;

            case R.id.btnAuto:
                if(!isBtnAutoOn){
                    isBtnAutoOn=true;
                    handler.postDelayed(r,500);
                    Toast.makeText(getActivity(),"auto on",Toast.LENGTH_SHORT).show();
                }else{
                    isBtnAutoOn=false;
                    handler.removeCallbacks(r);
                    Toast.makeText(getActivity(),"auto off",Toast.LENGTH_SHORT).show();
                }
        }
    }
}
