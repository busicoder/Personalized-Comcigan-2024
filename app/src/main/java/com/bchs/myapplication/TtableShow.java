package com.bchs.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TtableShow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TtableShow extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView txtview[][]=new TextView[10][10];//y,x

    public TtableShow() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TtableShow.
     */
    // TODO: Rename and change types and number of parameters
    public static TtableShow newInstance(String param1, String param2) {
        TtableShow fragment = new TtableShow();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ttable_show, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        for(int i=1;i<=8;i++){
            for(int j=1;j<=5;j++){
                txtview[i][j]=view.findViewWithTag(j+"_"+i);
                //System.out.println(txtview[i][j]);
            }
        }
    }
    private static TimeJSON tjson;
    private static stInfo stDt;
    @Override
    public void onResume() {
        super.onResume();
        setView();
    }
    public void setView(){
        if(tjson==null)
            tjson=TimeJSON.GetSingleton();
        if(stDt==null)
            stDt=stInfo.GetstInfoClass();
        stDt.setMyTable();
        System.out.println("setView 시도");
        vertex blank=new vertex();
        blank.change=true;
        blank.rawNum=-1;
        for(int i=1;i<=5;i++){//yoil
            for(int j=1;j<=tjson.OriGoesiCnt[i];j++){
                if(j>tjson.GoesiCnt[i]){
                    setunit(i,j,blank);
                }else{
                    setunit(i,j,stDt.myTtable[i][j]);
                }
            }
        }
    }
    private void setunit(int yoil, int goesi,vertex v){
        if(v.change==true){
            txtview[goesi][yoil].setBackgroundColor(Color.parseColor("#58FAD0"));
        }else{
            txtview[goesi][yoil].setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if(v.rawNum==-1){
            txtview[goesi][yoil].setText("");
            return;
        }
        txtview[goesi][yoil].setText(v.clnm+'\n'+v.ClassGrade+'-'+v.ClassBan+'\n'+v.tnm);
    }
}