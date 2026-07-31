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

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link settingwithtyping#newInstance} factory method to
 * create an instance of this fragment.
 */
public class settingwithtyping extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public settingwithtyping() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settingwithtyping.
     */
    // TODO: Rename and change types and number of parameters
    public static settingwithtyping newInstance(String param1, String param2) {
        settingwithtyping fragment = new settingwithtyping();
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
        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settingconView, new SettingsActivity.SettingsFragment())
                    .commit();
        }
    }
    private boolean flagtogohome=true;
    @Override
    public void onStop() {
        super.onStop();
        System.out.println("setting fragment onStop");
        FragmentTransaction fm=getParentFragmentManager().beginTransaction();
        if(flagtogohome==true)
            fm.replace(R.id.fragmentContainerView2, new TtableShow());
        fm.commit();
    }

    private static stInfo stDt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_settingwithtyping, container, false);
        /*if (savedInstanceState == null) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settingconView, new SettingsActivity.SettingsFragment())
                    .commit();
        }*/
        if(stDt==null)
            stDt=stInfo.GetstInfoClass();
        Button addBtn=(Button) view.findViewById(R.id.addBtn);
        Button delBtn=(Button) view.findViewById(R.id.delete);
        ListView lview=(ListView) view.findViewById(R.id.listview);
        ArrayList<String> items=new ArrayList<String>();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_checked,items);
        lview.setAdapter(adapter);
        for(int i=0;i<stDt.verlist.size();i++){
            vertex v=stDt.verlist.get(i);
            items.add(v.clnm+" / "+Integer.toString(v.ClassGrade)+"-"+Integer.toString(v.ClassBan));
            adapter.notifyDataSetChanged();
        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagtogohome=false;
                getParentFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainerView2,new addingfrag())
                        .commit();
            }
        });
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stDt==null)
                    stDt=stInfo.GetstInfoClass();
                int count, checked;
                count=lview.getCheckedItemCount();
                if(count>0){
                    checked=lview.getCheckedItemPosition();
                    if(checked>-1&&checked<lview.getCount()){
                        items.remove(checked);
                        lview.clearChoices();
                        adapter.notifyDataSetChanged();
                        System.out.println("삭제: "+checked+"verlist"+stDt.verlist.get(checked).clnm);
                        stDt.verlist.remove(checked);
                        stDt.saveFile();
                    }
                }
            }
        });

        return view;
    }
}