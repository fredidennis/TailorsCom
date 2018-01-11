package com.tailorscom.fred.tailorscom;


import android.app.Fragment;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        TextView t2 = (TextView) view.findViewById(R.id.infoTxtCredits);
        t2.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
