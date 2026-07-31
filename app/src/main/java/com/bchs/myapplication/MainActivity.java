package com.bchs.myapplication;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TimeJSON TimeTable;
    private TimeJSON tjson;
    private HttpAct hact;
    private stInfo stDt;
    private Button setbtn, refbtn;
    private FragmentManager FragManager;
    SharedPreferences pref,listdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        setbtn=findViewById(R.id.settingBtn);
        refbtn=findViewById(R.id.refreshBtn);
        FragManager=getSupportFragmentManager();
        refbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTtable();
            }
        });
        setbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingFrag();
                //만약 이미 떠있는 경우
            }
        });

        waitforload(2000,10,true);
        if(stDt==null)
            stDt=stInfo.GetstInfoClass();
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.contains("grade"))
            stDt.Grade=Integer.parseInt(pref.getString("grade","").toString());
        else
            stDt.Grade=3;
        if(pref.contains("ban"))
            stDt.Ban=Integer.parseInt(pref.getString("ban","").toString());
        else
            stDt.Ban=1;
        listdata=getSharedPreferences("listdata", Context.MODE_PRIVATE);
        if(listdata!=null) {
            stDt.setSP(listdata);
            stDt.loadFile();
        }
        if(hact==null)
            hact=HttpAct.GetHttpST();
        hact.setTtable();

        //handler 만들어서 반복시키다가 로드 되면 반복 해제후 설정이 필요하면 설정 프래그 띄우기
    }
    private void waitforload(int waitingtime, int tunit,boolean setwait){
        if(stDt==null)
            stDt=stInfo.GetstInfoClass();
        if(hact==null)
            hact=HttpAct.GetHttpST();
        if(tjson==null)
            TimeJSON.GetSingleton();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int t=0;t<=waitingtime;t+=tunit){
                    try{
                        Thread.sleep(tunit);
                    }catch (InterruptedException e){
                        System.out.println(e);
                    }
                    if(hact.lastSuccess==false||hact.lastProcessingSuccess==false)
                        continue;
                    //System.out.println("hact load comp");
                    if(stDt.hasFile==true){
                        if(stDt.setted==false)
                            continue;
                        refresh();
                    }else{
                        if(setwait){
                            settingFrag();
                            //불 하나 만들어서 설정창 안 띄웠으면 띄우기 불 트루 넣고 setted 신호 대기
                            //setting
                            //setting save
                            //혹은 스레드 하나 더 만들어서 그냥 그거 호출
                            //refresh();
                        }else{
                            refresh();
                        }
                    }
                    return;
                }
                FragmentTransaction fragmentTransaction = FragManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView2, new OnLoding());
                fragmentTransaction.commit();
            }
        }).start();
    }
    public void settingFrag(){
        FragmentTransaction fragmentTransaction = FragManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView2, new settingwithtyping());
        fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resetTtable();
    }
    public void resetTtable(){
        if(hact==null)
            hact=HttpAct.GetHttpST();
        hact.setTtable();
        waitforload(1000,10,false);
    }

    public void refresh(){
        if(stDt==null)
            stDt=stInfo.GetstInfoClass();
        if(hact==null)
            hact=HttpAct.GetHttpST();
        if(!hact.lastSuccess)
            return;
        //refresh 조건 달성->frag replace
        FragmentTransaction fragmentTransaction = FragManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerView2, new TtableShow());
        fragmentTransaction.commit();
    }
}