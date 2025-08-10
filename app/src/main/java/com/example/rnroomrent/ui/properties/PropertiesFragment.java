package com.example.rnroomrent.ui.properties;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rnroomrent.AddProperty;
import com.example.rnroomrent.LoginScreenActivity;
import com.example.rnroomrent.MainActivity;
import com.example.rnroomrent.Properties;
import com.example.rnroomrent.R;
import com.example.rnroomrent.databinding.FragmentPropertiesBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class PropertiesFragment extends Fragment {

    private RecyclerView propertyList;
    private DatabaseReference propertiesReference;

    private FragmentPropertiesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PropertiesViewModel propertiesViewModel =
                new ViewModelProvider(this).get(PropertiesViewModel.class);

        binding = FragmentPropertiesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        propertyList = (RecyclerView) root.findViewById(R.id.propertyListRecycleView);
        propertyList.setHasFixedSize(true);
        propertyList.setLayoutManager(new LinearLayoutManager(requireContext()));

        Button btnAddProperty = root.findViewById(R.id.btnAddProperty);
        btnAddProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), AddProperty.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        propertiesReference = FirebaseDatabase.getInstance().getReference().child("properties");
        String userID = FirebaseAuth.getInstance().getUid();

        Query userProperties = propertiesReference.orderByChild("user_id").equalTo(userID);
        FirebaseRecyclerOptions<Properties> options = new FirebaseRecyclerOptions.Builder<Properties>()
                .setQuery(userProperties, Properties.class)
                .build();

        FirebaseRecyclerAdapter<Properties, PropertiesViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Properties, PropertiesViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull PropertiesViewHolder holder, int position, @NonNull Properties model) {
                        // Bind your data here
                        holder.setPropertyName(model.getProperty_name());
                        holder.setPropertyAddress(model.getProperty_address());
                        holder.setOccupiedRooms(model.getRooms_occupied());
                        holder.setTotalRooms(model.getTotal_rooms());
                        holder.setOccupiedShops(model.getShops_occupied());
                        holder.setTotalShops(model.getTotal_shops());
                        // etc.
                    }

                    @NonNull
                    @Override
                    public PropertiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.single_property_layout, parent, false);
                        return new PropertiesViewHolder(view);
                    }
                };

        firebaseRecyclerAdapter.startListening();
        propertyList.setAdapter(firebaseRecyclerAdapter);

    }

    public class PropertiesViewHolder extends RecyclerView.ViewHolder{

        View mView;
        public PropertiesViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setPropertyName(String propertyName){
            TextView propertyNameView = mView.findViewById(R.id.tvPropertyName);
            propertyNameView.setText(propertyName);
        }

        public void setPropertyAddress(String propertyAddress){
            TextView propertyNameView = mView.findViewById(R.id.tvPropertyAddress);
            propertyNameView.setText(propertyAddress);
        }

        public void setOccupiedRooms(String occupiedRooms){
            TextView propertyNameView = mView.findViewById(R.id.tvOccupiedRooms);
            propertyNameView.setText(occupiedRooms);
        }

        public void setTotalRooms(String totalRooms){
            TextView propertyNameView = mView.findViewById(R.id.tvTotalRooms);
            propertyNameView.setText(totalRooms);
        }

        public void setOccupiedShops(String occupiedShops){
            TextView propertyNameView = mView.findViewById(R.id.tvOccupiedShops);
            propertyNameView.setText(occupiedShops);
        }
        public void setTotalShops(String totalShops){
            TextView propertyNameView = mView.findViewById(R.id.tvTotalShops);
            propertyNameView.setText(totalShops);
        }
    }
}