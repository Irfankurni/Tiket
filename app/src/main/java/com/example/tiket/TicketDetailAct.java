package com.example.tiket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailAct extends AppCompatActivity {
    Button btn_buy_ticket;
    TextView title_ticket, location_ticket, photo_spot_ticket,
            wifi_ticket, festival_ticket, short_desc_ticket;
    ImageView header_tiket_detail;
    DatabaseReference reference;
    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_back = findViewById(R.id.btn_back);

        title_ticket = findViewById(R.id.title_ticket);
        location_ticket = findViewById(R.id.location_ticket);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket = findViewById(R.id.festival_ticket);
        short_desc_ticket = findViewById(R.id.short_desc_ticket);

        header_tiket_detail = findViewById(R.id.header_ticket_detail);

        //mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");

        //mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //menimpa data yang ada dengan data baru
                title_ticket.setText(snapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(snapshot.child("lokasi").getValue().toString());
                photo_spot_ticket.setText(snapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(snapshot.child("is_wifi").getValue().toString());
                festival_ticket.setText(snapshot.child("is_festival").getValue().toString());
                short_desc_ticket.setText(snapshot.child("short_desc").getValue().toString());
                Picasso.with(TicketDetailAct.this).load(snapshot.child("url_thumbnail")
                        .getValue().toString()).centerCrop().fit().into(header_tiket_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocheckout = new Intent(TicketDetailAct.this, TicketCheckoutAct.class);
                gotocheckout.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotocheckout);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}