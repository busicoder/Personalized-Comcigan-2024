package com.bchs.myapplication;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ListIterator;

import android.content.SharedPreferences;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class stInfo {
    public static stInfo StData;
    public static stInfo GetstInfoClass(){
        if(StData==null)
            StData=new stInfo();
        return StData;
    }
    public stInfo(){
        resetMT();
    }
    private void resetMT(){
        myTtable=new vertex[20][10];
        for(int i=0;i<20;i++)
            for(int j=0;j<10;j++)
                myTtable[i][j]=new vertex();
    }
    public int Grade;
    public int Ban;
    private TimeJSON tjson;
    public List<vertex> verlist=new ArrayList<vertex>();
    public vertex myTtable[][]=new vertex[20][10];//yoil, goesi
    public boolean setted=false;
    public void setMyTable(){
        if(tjson==null) {
            tjson=TimeJSON.GetSingleton();
        }
        if(!tjson.setted)
            return;
        //System.out.println("setmytable");
        resetMT();
        for (int i = 1; i <= tjson.yoilCnt; i++) {
            //System.out.println(tjson.GoesiCnt[i]);
            for (int j = 1; j <= tjson.GoesiCnt[i]; j++) {
                //System.out.println(i+" "+j+" "+tjson.Ttable[Grade][Ban]);
                myTtable[i][j]=tjson.Ttable[Grade][Ban][i][j];
            }
        }
        if(verlist!=null) {
            for(int i=0;i<verlist.size();i++)
                System.out.println(verlist.get(i).clnm);
            setted=false;
            for (int n = 0; n < verlist.size(); n++) {
                for (int i = 1; i <= tjson.yoilCnt; i++) {
                    //System.out.println(tjson.GoesiCnt[i]);
                    for (int j = 1; j <= tjson.GoesiCnt[i]; j++) {
                        if (tjson.Ttable[verlist.get(n).ClassGrade][verlist.get(n).ClassBan][i][j].clnm.equals(verlist.get(n).clnm)) {
                            myTtable[i][j] = tjson.Ttable[verlist.get(n).ClassGrade][verlist.get(n).ClassBan][i][j];
                        }
                    }
                }
            }
            setted=true;
        }
    }

    public boolean hasFile=false;
    private HttpAct hact;
    public SharedPreferences prefdata;

    public void setSP(SharedPreferences pref){
        prefdata=pref;
        System.out.println("setting complete");
    }
    public void loadFile() {
        if(hasFile){
            System.out.println("이미 파일이 로드되었습니다.");
            return;
        }
        if(prefdata==null){
            System.out.println("SharedPreference didnt be loaded");
            return;
        }
        String result=prefdata.getString("List","-");
        if(result.equals("-")){
            System.out.println("파일 없음");
            return;
        }
        Gson gson=new GsonBuilder().setVersion(1.0).create();
        DtUnit filedata=gson.fromJson(result,DtUnit.class);
        verlist=filedata.movingclass;
        hasFile=true;
        //System.out.println(filedata);
    }
    public void saveFile(){
        SharedPreferences.Editor editor;
        editor=prefdata.edit();
        if(editor==null){
            System.out.println("editor didnt be loaded");
            return;
        }
        DtUnit saveDt=new DtUnit();
        saveDt.movingclass=verlist;
        Gson gson = new GsonBuilder().setVersion(1.0).create();
        String json=gson.toJson(saveDt);
        editor.putString("List",json);
        editor.commit();
    }

    private class DtUnit{
        List<vertex> movingclass;
    }


}
