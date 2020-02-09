package com.example.myapplication.uiClient.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Requests;
import com.example.myapplication.R;

public class clienthomefragment extends Fragment {

    public static String serviceselected;


    GridLayout mainGrid;

    public static clienthomefragment getInstance()
    {
        clienthomefragment clienthomefragment =new clienthomefragment();

        return clienthomefragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.client_home, container, false);

        mainGrid=(GridLayout)root.findViewById(R.id.mainGrid);


        int i;
        for(i = 0; i<mainGrid.getChildCount(); i++){

            CardView cardView = (CardView)mainGrid.getChildAt(i);

            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(finalI==0)
                    {
                        serviceselected = "Appliances";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==1)
                    {
                        serviceselected = "Electrical";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==2)
                    {
                        serviceselected = "Plumbing";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==3)
                    {
                        serviceselected = "Home Cleaning";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==4)
                    {
                        serviceselected = "Tutoring";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==5)
                    {
                        serviceselected = "Packaging Moving";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==6)
                    {
                        serviceselected = "Computer repair";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==7)
                    {
                        serviceselected = "Home repair";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }

                    if(finalI==8)
                    {
                        serviceselected = "Pest Control";
                        Intent j;
                        j = new Intent(getActivity(), Requests.class);
                        startActivity(j);

                    }
                }
            });
        }

        return root;
    }


}





