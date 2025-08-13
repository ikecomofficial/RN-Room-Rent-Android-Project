package com.example.rnroomrent;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class EditProperty extends AppCompatActivity {

    private EditText etPropertyName, etPropertyAddress, etDefaultRentAmount;
    private String property_id, property_name, property_address;
    private long default_rent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_property);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        property_id = getIntent().getStringExtra("property_id");
        property_name = getIntent().getStringExtra("property_name");
        property_address = getIntent().getStringExtra("property_address");
        default_rent = getIntent().getLongExtra("default_rent",0);

        etPropertyName = findViewById(R.id.etUpdatePropertyName);
        etPropertyAddress = findViewById(R.id.etUpdatePropertyAddress);
        etDefaultRentAmount = findViewById(R.id.etUpdateDefaultRent);


        etPropertyName.setText(property_name);
        etPropertyAddress.setText(property_address);
        etDefaultRentAmount.setText(String.valueOf(default_rent));

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