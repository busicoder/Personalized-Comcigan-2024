package com.bchs.myapplication;

import java.util.List;

public class vertex {
    public int rawNum,rawclnum,rawtnum;
    public int ClassGrade,ClassBan;
    public String clnm;//class name
    public String tnm;//teacher name;
    public boolean change;
    public boolean Moving=false;
    public void convert(int num, int SPnum, List<String> Subnm, List<String> Tnm,int clgr,int clban){
        //System.out.println(num);
        rawNum=num;
        int th=num%SPnum;
        int Sb= num/SPnum;
        int tt=Sb/SPnum;
        Sb%= SPnum;
        if(tt>0){
            Moving=true;
        }
        rawclnum=Sb;
        rawtnum=th;
        if(rawclnum-1>=0)
            clnm=Subnm.get(rawclnum-1);
        tnm=Tnm.get(rawtnum);
        ClassGrade=clgr;
        ClassBan=clban;
        //System.out.println(clnm.toString());
    }
}
