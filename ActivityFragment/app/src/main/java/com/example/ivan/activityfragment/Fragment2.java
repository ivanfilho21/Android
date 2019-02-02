package com.example.ivan.activityfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Fragment2 extends Fragment {

    private OnInformationChanged listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_2, container, false);

        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input and send it to parent activity
                try {
                    int id = ((RadioGroup) view.findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
                    String userInput = ((RadioButton) view.findViewById(id)).getText().toString();

                    listener.OnInformationChanged(userInput);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Check if parent (context) implements the listener this fragment knows of.
        if (context instanceof OnInformationChanged) {
            // Parent will listen for changes
            listener = (OnInformationChanged) context;
        } else {
            throw new ClassCastException();
        }
    }
    
}
