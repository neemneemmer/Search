package com.example.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {

    private ImageView image_single_view_activity;
    private TextView texyView_single_view_activity;
    private Button bntDelete;

    DatabaseReference ref , DataRef;
    StorageReference StorageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        image_single_view_activity = findViewById(R.id.image_single_view_activity);
        texyView_single_view_activity = findViewById(R.id.texyView_single_view_activity);
        bntDelete = findViewById(R.id.bntDelete);
        ref = FirebaseDatabase.getInstance().getReference().child("Groupss");

        String GroupssKey =getIntent().getStringExtra("GroupssKey");
        //set Database Class and Image
        DataRef = FirebaseDatabase.getInstance().getReference().child("Groupss").child(GroupssKey);
        StorageRef = FirebaseStorage.getInstance().getReference().child("GroupImage").child(GroupssKey + ".jpg");

        ref.child(GroupssKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String GroupName = snapshot.child("GroupName").getValue().toString();
                    String ImageUri = snapshot.child("ImageUri").getValue().toString();

                    Picasso.get().load(ImageUri).into(image_single_view_activity);
                    texyView_single_view_activity.setText(GroupName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Delete pic in firebase -Button-
        bntDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        StorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                            }
                        });
                    }
                });
            }
        });

    }
}