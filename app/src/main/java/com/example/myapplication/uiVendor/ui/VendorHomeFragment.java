package com.example.myapplication.uiVendor.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.RequestList;
import com.example.myapplication.VendorAppointmentStatus;
import com.example.myapplication.VendorHistoryStatus;

public class VendorHomeFragment extends Fragment {


    GridLayout mainGrid;

    public static VendorHomeFragment getInstance()
    {
        VendorHomeFragment homefragment =new VendorHomeFragment();

        return homefragment;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.vendor_fragment_home, container, false);

        mainGrid=(GridLayout)root.findViewById(R.id.mainGrid);


        int i;
        for(i = 0; i<mainGrid.getChildCount(); i++){

            CardView cardView = (CardView)mainGrid.getChildAt(i);

            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (finalI == 0) {

                        Intent newi= new Intent(getActivity(), RequestList.class);
                        startActivity(newi);

                    }

                    if (finalI == 1) {

                        Intent intoappoint= new Intent(getActivity(), VendorAppointmentStatus.class);
                        startActivity(intoappoint);

                    }

                    if (finalI == 2) {

                        Intent intohist= new Intent(getActivity(), VendorHistoryStatus.class);
                        startActivity(intohist);

                    }


                }
            });
        }

        return root;
    }


}





