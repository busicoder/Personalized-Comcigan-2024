package com.bchs.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link addingfrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class addingfrag extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public addingfrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment addingfrag.
     */
    // TODO: Rename and change types and number of parameters
    public static addingfrag newInstance(String param1, String param2) {
        addingfrag fragment = new addingfrag();
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
    private static stInfo stDt;
    private static TimeJSON tjson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("adding frag 생성");
        View view=inflater.inflate(R.layout.fragment_addingfrag, container, false);
        if(stDt==null)
            stDt=stInfo.GetstInfoClass();
        if(tjson==null){
            tjson=TimeJSON.GetSingleton();
        }
        ListView subview=(ListView) view.findViewById(R.id.subject);
        ArrayList<String> items=new ArrayList<String>();
        ArrayAdapter adapter2=new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_single_choice,items);
        subview.setAdapter(adapter2);
        for(int i=0;i<tjson.Subnm.size();i++){
            String t=tjson.Subnm.get(i);
            items.add(t);
            adapter2.notifyDataSetChanged();
        }
        Button cancelBtn=(Button) view.findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                manager.remove(addingfrag.this);
                manager.replace(R.id.fragmentContainerView2,new settingwithtyping()).commit();
            }
        });
        Button applyBtn=(Button) view.findViewById(R.id.applyBtn);
        TextInputEditText grade=view.findViewById(R.id.gradeedit);
        TextInputEditText ban=view.findViewById(R.id.banedit);
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count, checked;
                String subname="";
                count=subview.getCheckedItemCount();
                if(count>0){
                    checked=subview.getCheckedItemPosition();
                    System.out.println(checked);
                    if(checked>-1&&checked<subview.getCount()){
                        subname=subview.getItemAtPosition(checked).toString();
                        System.out.println(subname);
                    }
                }else{
                    return;
                }
                if(grade.getText().toString().isEmpty()||ban.getText().toString().isEmpty())
                    return;
                vertex newone=new vertex();
                newone.ClassGrade=Integer.parseInt(grade.getText().toString());
                newone.ClassBan=Integer.parseInt(ban.getText().toString());
                newone.change=true;
                newone.clnm=subname;
                stDt.verlist.add(newone);
                stDt.saveFile();
                FragmentTransaction manager = getActivity().getSupportFragmentManager().beginTransaction();
                manager.remove(addingfrag.this);
                manager.replace(R.id.fragmentContainerView2,new settingwithtyping()).commit();
            }
        });

        return view;
    }
}