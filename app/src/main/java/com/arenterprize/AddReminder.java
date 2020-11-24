package com.arenterprize;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Objects;

public class AddReminder extends AppCompatActivity {
    private String location;
    private String placename;
    private String placeadd;
    private FirebaseAuth mAuth;
    private double logitude;
    private double latitude;
    private EditText rmname, rmdate, rmtime;
    private Button Addlocation, Addremainder;
    int PLACE_PICKER_REQUEST = 1;
    String rname;
    String rdate;
    String rtime;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        mAuth = FirebaseAuth.getInstance();

        rmname = (EditText) findViewById(R.id.rmadd);
        rmdate = (EditText) findViewById(R.id.rmdate);
        rmtime = (EditText) findViewById(R.id.rmtime);

        Addremainder = (Button) findViewById(R.id.addremainder);
        Addlocation = (Button) findViewById(R.id.btnlocation);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setTitle("Add Reminder");
        rmdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleDateButton();
            }
        });
        rmtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTimeButton();
            }
        });
        Addlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    Intent intent = builder.build(Objects.requireNonNull(AddReminder.this));
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    Log.d("placepicker:", "myerror1");
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }


            }

        });

        Addremainder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (latitude>0&&logitude>0)
                {
                    String current_user_id;
                    final DatabaseReference customerRef;
                    current_user_id = mAuth.getCurrentUser().getUid();
                    customerRef = FirebaseDatabase.getInstance().getReference().child("Post").child(current_user_id);
                    customerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            rname = rmname.getText().toString().trim();
                            rdate = rmdate.getText().toString().trim();
                            rtime = rmtime.getText().toString().trim();

                            String lat = String.valueOf(latitude);
                            String lng = String.valueOf(logitude);
                            Post post = new Post(rname, rdate, rtime, lat, lng);
                            customerRef.push().setValue(post);
                            Toast.makeText(AddReminder.this, "Saved", Toast.LENGTH_SHORT).show();
                            rmname.setText("");
                            rmdate.setText("");
                            rmtime.setText("");
                            latitude=logitude=0.0;

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(AddReminder.this, "Error Fetching result please add location again", Toast.LENGTH_SHORT).show();
                }



            }
        });
    }
    private void handleDateButton() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                rmdate.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();




    }

    private void handleTimeButton() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Log.i("TAG", "onTimeSet: " + hour + minute);
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                rmtime.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(Objects.requireNonNull(AddReminder.this), data);
                placename = (String) place.getName();
                placeadd = (String) place.getAddress();
                logitude = place.getLatLng().longitude;
                latitude = place.getLatLng().latitude;
                Log.d("Locations", "onActivityResult: " + logitude + " " + latitude);
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id==android.R.id.home)
        {
            startActivity(new Intent(AddReminder.this,HomeActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}