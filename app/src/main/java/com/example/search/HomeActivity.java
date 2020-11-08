package com.example.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    EditText inputSearch;
    RecyclerView recylerView;
    FloatingActionButton floatingbtn;
    FirebaseRecyclerOptions<Groupss> options;
    FirebaseRecyclerAdapter<Groupss,MyViewHolder> adapter;
    DatabaseReference Dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Dataref = FirebaseDatabase.getInstance().getReference().child("Groupss");
        inputSearch = findViewById(R.id.inputSearch);
        recylerView = findViewById(R.id.recylerView);
        floatingbtn = findViewById(R.id.floatingbtn);
        recylerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recylerView.setHasFixedSize(true);
        floatingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        LoadData(" ");
        //LoadData();

        //search1 input data to Search
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            //chack text in input search
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString()!= null){
                    LoadData(editable.toString());
                }else {
                    LoadData(" ");
                }

            }
        });

    }

    private void LoadData(String data) {
        //search2 Query data from Groupss
        Query query = Dataref.orderByChild("GroupName").startAt(data).endAt(data+"\uf8ff");
        //Query from class Group pass Get Set
        options = new FirebaseRecyclerOptions.Builder<Groupss>().setQuery(query,Groupss.class).build();
        adapter = new FirebaseRecyclerAdapter<Groupss, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, final int position, @NonNull Groupss model) {
                holder.texyView_single_view.setText(model.getGroupName());
                Picasso.get().load(model.getImageUri()).into(holder.image_single_view);
                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(HomeActivity.this,ViewActivity.class);
                        intent.putExtra("GroupssKey",getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_view, parent,false);

                return new MyViewHolder(view);
            }
        };
        adapter.startListening();
        recylerView.setAdapter(adapter);
    }
}