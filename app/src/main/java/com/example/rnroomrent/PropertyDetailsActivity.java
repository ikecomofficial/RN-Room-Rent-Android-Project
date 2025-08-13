package com.example.rnroomrent;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PropertyDetailsActivity extends AppCompatActivity {

    private TextView tvPropertyName, tvPropertyAddress;

    private String property_name, property_address;
    private long property_default_rent;

    private String property_id;
    private String room_id;
    private DatabaseReference propertyDatabase;
    private DatabaseReference roomsDatabase;
    private DatabaseReference tenantsDatabase;
    private DatabaseReference rentDatabase;
    private DatabaseReference billsDatabase;
    private RoomCardAdapter roomCardAdapter;


    private RecyclerView roomsRecyclerView;
    private List<RoomCardModel> roomList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_property_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        property_id = getIntent().getStringExtra("property_id");
        propertyDatabase = FirebaseDatabase.getInstance().getReference().child("properties").child(property_id);

        roomsDatabase = FirebaseDatabase.getInstance().getReference("rooms");
        tenantsDatabase = FirebaseDatabase.getInstance().getReference("tenants");
        rentDatabase = FirebaseDatabase.getInstance().getReference("rents");
        billsDatabase = FirebaseDatabase.getInstance().getReference("e-bills");

        tvPropertyName = (TextView) findViewById(R.id.tvPropertyName);
        tvPropertyAddress = (TextView) findViewById(R.id.tvPropertyAddress);

        roomsRecyclerView = findViewById(R.id.roomsListRecyclerView);
        roomsRecyclerView.setHasFixedSize(true);
        roomsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        roomList = new ArrayList<>();
        roomCardAdapter = new RoomCardAdapter(this, roomList);
        roomsRecyclerView.setAdapter(roomCardAdapter);

        loadRoomsForProperty();

        propertyDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                property_name = snapshot.child("property_name").getValue().toString();
                property_address = snapshot.child("property_address").getValue().toString();
                property_default_rent = snapshot.child("property_default_rent").getValue(Long.class);

                tvPropertyName.setText(property_name);
                tvPropertyAddress.setText(property_address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     }

    private void loadRoomsForProperty() {

        roomsDatabase.orderByChild("property_id").equalTo(property_id)
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                roomList.clear();
                                for (DataSnapshot roomSnap : snapshot.getChildren()) {
                                    RoomCardModel model = new RoomCardModel();

                                    room_id = roomSnap.getKey();
                                    model.room_id = room_id;

                                    model.setRoom_name(roomSnap.child("room_name").getValue(String.class));
                                    model.setCustom_rent(roomSnap.child("custom_rent").getValue(Integer.class));

                                    // Tenant defaults
                                    model.setTenant_name("No Tenant");

                                    // Rent defaults
                                    model.setRent_status("N/A");

                                    // Bill defaults
                                    model.setUnit_paid_up_to("0");

                                    // Now fetch tenant, rent, and bill if available
                                    fetchTenantData(room_id, model);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
    }

    private void fetchTenantData(String room_id, RoomCardModel model) {
        tenantsDatabase.orderByChild("room_id").equalTo(room_id)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot tenantSnap : snapshot.getChildren()) {
                                model.setTenant_name(tenantSnap.child("tenant_name").getValue(String.class));
                                break; // Only one active tenant expected
                            }
                        }
                        fetchRentData(room_id, model);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        fetchRentData(room_id, model);
                    }
                });
    }

    private void fetchRentData(String room_id, RoomCardModel model) {
        String currentMonthKey = "2025-08"; // Example, ideally generate dynamically

        rentDatabase.child(room_id).child(currentMonthKey)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            model.setRent_status(snapshot.child("rent_status").getValue(String.class));
                        }
                        fetchBillData(room_id, model);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        fetchBillData(room_id, model);
                    }
                });
    }

    private void fetchBillData(String room_id, RoomCardModel model) {
        billsDatabase.child(room_id)
                .orderByChild("status").equalTo("paid")
                .limitToLast(1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot billSnap : snapshot.getChildren()) {
                                String unitsPaidUpTo = billSnap.child("unit_paid_up_to").getValue(String.class);
                                if (unitsPaidUpTo != null) {
                                    model.setUnit_paid_up_to(unitsPaidUpTo);
                                }
                                break;
                            }
                        }
                        roomList.add(model);
                        roomCardAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        roomList.add(model);
                        roomCardAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.property_details_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_edit_property) {
            Intent editPropertyIntent = new Intent(PropertyDetailsActivity.this, EditProperty.class);
            editPropertyIntent.putExtra("property_id", property_id);
            editPropertyIntent.putExtra("property_name", property_name);
            editPropertyIntent.putExtra("property_address", property_address);
            editPropertyIntent.putExtra("default_rent", property_default_rent);
            startActivity(editPropertyIntent);
            // Open Edit Property screen
            return true;
        }
        else if (id == R.id.action_delete_property) {
            // Confirm and delete property
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}