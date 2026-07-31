package com.bchs.myapplication;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

public class TimeJSON {
    private static TimeJSON singleton;
    private TimeJSON(){}
    public static TimeJSON GetSingleton(){
        if(singleton==null){
            singleton=new TimeJSON();
        }
        return singleton;
    }
    public boolean setted=false;
    public int Tcnt,SPnum;//Teacher count
    public final int GradeCnt=3,yoilCnt=5;
    public List<String> ClassCnt;
    public int GoesiCnt[]=new int[10], OriGoesiCnt[]=new int[10];
    public List<String> Tnm,Subnm;//Teacher name
    public vertex Ttable[][][][]=new vertex[10][20][20][10];//grade, ban, yoil, goesi
    public void setTtable(JSONObject json) throws JSONException {
        for(int i=0;i<10;i++)
            for(int j=0;j<20;j++)
                for(int k=0;k<20;k++)
                    for (int m=0;m<10;m++)
                        Ttable[i][j][k][m]=new vertex();
        Tcnt=(int)json.get("교사수");
        SPnum=(int)json.get("분리");
        ClassCnt=Arrays.asList(json.getJSONArray("학급수").toString().substring(1,json.getJSONArray("학급수").toString().lastIndexOf(']')-1).split(","));
        //Tnm= (List<String>) json.get("자료446");
        Tnm= Arrays.asList(json.getJSONArray("자료446").toString().substring(2,json.getJSONArray("자료446").toString().lastIndexOf(']')-1).split("\",\""));
        json.getJSONArray("자료492").remove(0);
        Subnm= Arrays.asList(json.getJSONArray("자료492").toString().substring(2,json.getJSONArray("자료492").toString().lastIndexOf(']')-1).split("\",\""));
        for(int i=1;i<=(int)json.getJSONArray("자료147").get(0);i++){//grade   하루가 통째로 없으면 그날은 change가 안 바뀜+ ttable이 null임
            for (int j=1;j<=(int)json.getJSONArray("자료147").getJSONArray(i).get(0);j++){//ban
                for(int k=1;k<=(int)json.getJSONArray("자료147").getJSONArray(i).getJSONArray(j).get(0);k++){//yoil
                    GoesiCnt[k]=(int)json.getJSONArray("자료147").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(0);
                    OriGoesiCnt[k]=(int)json.getJSONArray("자료481").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(0);
                    //System.out.println(GoesiCnt[k]);
                    for (int m=1;m<=(int)json.getJSONArray("자료147").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(0);m++){
                        if((int)json.getJSONArray("자료147").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(m)==0) {
                            GoesiCnt[k]=m-1;
                            break;
                        }
                        Ttable[i][j][k][m]=new vertex();
                        if(m>OriGoesiCnt[k])
                            Ttable[i][j][k][m].change=true;
                        else
                            Ttable[i][j][k][m].change=((int)json.getJSONArray("자료147").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(m)==(int)json.getJSONArray("자료481").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(m))?false:true;
                        Ttable[i][j][k][m].convert((int)json.getJSONArray("자료147").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(m),SPnum,Subnm,Tnm,i,j);
                        //System.out.println(i*1000000+j*10000+k*100+m);
                        //System.out.println(Ttable[i][j][k][m].tnm);
                        //System.out.println((int)json.getJSONArray("자료147").getJSONArray(i).getJSONArray(j).getJSONArray(k).get(m));
                    }
                }
            }
        }//자료147(오늘(변동포함)자료)로 Ttable 초기화
        setted=true;
    }
}
