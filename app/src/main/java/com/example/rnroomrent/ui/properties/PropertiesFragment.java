package com.example.rnroomrent.ui.properties;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rnroomrent.AddProperty;
import com.example.rnroomrent.LoginScreenActivity;
import com.example.rnroomrent.MainActivity;
import com.example.rnroomrent.R;
import com.example.rnroomrent.databinding.FragmentPropertiesBinding;
import com.google.android.material.button.MaterialButton;

public class PropertiesFragment extends Fragment {

    private FragmentPropertiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PropertiesViewModel propertiesViewModel =
                new ViewModelProvider(this).get(PropertiesViewModel.class);

        binding = FragmentPropertiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        MaterialButton btnAddProperty = root.findViewById(R.id.btnAddProperty);
        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddProperty.class);
                startActivity(intent);
            }
        });

        //final TextView textView = binding.btnAddProperty;
        //propertiesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}