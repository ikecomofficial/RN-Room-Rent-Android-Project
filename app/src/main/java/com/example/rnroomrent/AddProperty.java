package com.example.rnroomrent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.rnroomrent.ui.properties.PropertiesFragment;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class AddProperty extends AppCompatActivity {

    private EditText etPropertyName, etPropertyAddress;
    private TextView textTotalRooms, textTotalShops;
    private int currTotalRooms = 0;
    private int currTotalShops = 0;
    private int roomsSortOrder = 1;

    private FirebaseAuth mAuth;
    private String userId;
    private String pid;
    String currTimestamp;
    private DatabaseReference propertyReference;
    private DatabaseReference roomsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_property);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userId = user.getUid();

        propertyReference = FirebaseDatabase.getInstance().getReference().child("properties");

        etPropertyName = findViewById(R.id.editTextPropertyName);
        etPropertyAddress = findViewById(R.id.editTextPropertyAddress);
        ImageView imgRoomsMinus = findViewById(R.id.imgRoomsMinus);
        textTotalRooms = findViewById(R.id.textTotalRooms);
        ImageView imgRoomsPlus = findViewById(R.id.imgRoomsPlus);
        ImageView imgShopsMinus = findViewById(R.id.imgShopsMinus);
        textTotalShops = findViewById(R.id.textTotalShops);
        ImageView imgShopsPlus = findViewById(R.id.buttonPlus);
        Button btnCreateProperty = findViewById(R.id.btnCreateProperty);

        textTotalRooms.setText(String.valueOf(currTotalRooms));
        textTotalShops.setText(String.valueOf(currTotalShops));

        btnCreateProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePropertyToFirebase();
                createRoomsShopsInFirebase();
            }
        });
        //Minus Buttons Action On click
        imgRoomsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currTotalRooms > 0){
                    currTotalRooms--;
                    textTotalRooms.setText(String.valueOf(currTotalRooms));
                }
            }
        });
        imgShopsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currTotalShops > 0){
                    currTotalShops--;
                    textTotalShops.setText(String.valueOf(currTotalShops));
                }
            }
        });

        // Plus Buttons Action On click
        imgRoomsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currTotalRooms++;
                textTotalRooms.setText(String.valueOf(currTotalRooms));
            }
        });
        imgShopsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currTotalShops++;
                textTotalShops.setText(String.valueOf(currTotalShops));
            }
        });

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void savePropertyToFirebase(){

        String propertyName = etPropertyName.getText().toString().trim();
        String propertyAddress = etPropertyAddress.getText().toString().trim();

        currTimestamp = String.valueOf(System.currentTimeMillis());

        if (propertyName.isEmpty()) {
            etPropertyName.setError("Enter property name");
            return;
        }
        if (propertyAddress.isEmpty()) {
            etPropertyAddress.setError("Enter city/address");
            return;
        }

        // Create unique property ID
        pid = propertyReference.push().getKey();

        HashMap<String, String> propertyMap = new HashMap<>();
        propertyMap.put("property_name", propertyName);
        propertyMap.put("property_address", propertyAddress);
        propertyMap.put("user_id", userId);
        propertyMap.put("total_rooms", String.valueOf(currTotalRooms));
        propertyMap.put("total_shops", String.valueOf(currTotalShops));
        propertyMap.put("rooms_occupied", "0");
        propertyMap.put("shops_occupied", "0");
        propertyMap.put("property_created_on", currTimestamp);

        if (pid != null){
            propertyReference.child(pid).setValue(propertyMap)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Property added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void createRoomsShopsInFirebase(){
        roomsReference = FirebaseDatabase.getInstance().getReference().child("rooms");

        for (int i = 1; i<= currTotalRooms; i++){
            String room_id = roomsReference.push().getKey();
            if (room_id != null){
                HashMap<String, String> roomsMap = new HashMap<>();
                roomsMap.put("room_no", String.valueOf(i));
                roomsMap.put("roomName", String.format(Locale.US, "Room %02d", i));
                roomsMap.put("user_id", userId);
                roomsMap.put("property_id", pid);
                roomsMap.put("is_room", String.valueOf(true));
                roomsMap.put("is_occupied", String.valueOf(false));
                roomsMap.put("property_created_on", currTimestamp);

                roomsReference.child(room_id).setValue(roomsMap)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Rooms Added", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Rooms Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }
        for (int i = 1; i<= currTotalShops; i++){
            String room_id = roomsReference.push().getKey();
            if (room_id != null){
                HashMap<String, String> roomsMap = new HashMap<>();
                roomsMap.put("room_no", String.valueOf(currTotalRooms + i));
                roomsMap.put("roomName", String.format(Locale.US, "Shop %02d", i));
                roomsMap.put("user_id", userId);
                roomsMap.put("property_id", pid);
                roomsMap.put("is_room", String.valueOf(false));
                roomsMap.put("is_occupied", String.valueOf(false));
                roomsMap.put("property_created_on", currTimestamp);

                roomsReference.child(room_id).setValue(roomsMap)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(this, "Rooms Added", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Rooms Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }

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


}