package com.example.ivan.activityfragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Fragment1 extends Fragment {

    private OnInformationChanged listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_1, container, false);

        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the user input and send it to parent activity
                String userInput = ((EditText) view.findViewById(R.id.editText)).getText().toString();
                listener.OnInformationChanged(userInput);
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
