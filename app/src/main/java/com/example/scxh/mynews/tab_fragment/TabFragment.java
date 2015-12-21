package com.example.scxh.mynews.tab_fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.scxh.mynews.R;
import com.example.scxh.mynews.uitl.StrDataInter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    private TabCallBack tabCallBack;
    private RadioGroup mRadioGroup;
    public interface TabCallBack{
        public void getMsg(String msg);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TabCallBack){
            tabCallBack = (TabCallBack) activity;

        }else{
            throw new RuntimeException("activity not implements  interface TabCallBack");
        }

    }

    public static TabFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.tab_RadioGroup);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tab_newsBtn:
                        tabCallBack.getMsg(StrDataInter.TAB_NEWS);
                        break;
                    case R.id.tab_picturBtn:
                        tabCallBack.getMsg(StrDataInter.TAB_PRCTURE);
                        break;
                    case R.id.tab_radioBtn:
                        tabCallBack.getMsg(StrDataInter.TAB_REDIO);
                        break;
                    case R.id.tab_findBtn:
                        tabCallBack.getMsg(StrDataInter.TAB_WEATHER);
                        break;
                }
            }
        });
        return view;
    }

}
