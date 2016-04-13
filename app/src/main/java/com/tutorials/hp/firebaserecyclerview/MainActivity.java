package com.tutorials.hp.firebaserecyclerview;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.tutorials.hp.firebaserecyclerview.Data.Movie;
import com.tutorials.hp.firebaserecyclerview.Recycler.MyAdapter;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static String DB_URL="https://wedata.firebaseio.com/";
    EditText nameEditText,descEditText;
    Button saveBtn;
    ArrayList<Movie> movies=new ArrayList<>();
    RecyclerView rv;
    MyAdapter adapter;
    Firebase fire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        rv= (RecyclerView) findViewById(R.id.mRecyclerID);
        rv.setLayoutManager(new LinearLayoutManager(this));


        //INITIALIZE
        Firebase.setAndroidContext(this);

        //INSANTIATE
        fire=new Firebase(DB_URL);

        //REFRESH
        this.refreshData();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              displayDialog();
            }
        });
    }

    private void displayDialog()
    {
        Dialog d=new Dialog(this);
        d.setTitle("Save Online");
        d.setContentView(R.layout.dialoglayout);

        nameEditText= (EditText) d.findViewById(R.id.nameEditText);
        descEditText= (EditText) d.findViewById(R.id.descEditText);
        saveBtn= (Button) d.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnline(nameEditText.getText().toString(),descEditText.getText().toString());

                nameEditText.setText("");
                descEditText.setText("");
            }
        });

        //SHOW
        d.show();

    }

    //SAVE DATA
    private void saveOnline(String name,String desc)
    {
        Movie m=new Movie();
        m.setName(name);
        m.setDescription(desc);

        fire.child("Movie").push().setValue(m);
    }

    //RETRIEVE
    private void refreshData()
    {
         fire.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                  getUpdates(dataSnapshot);
             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getUpdates(dataSnapshot);
             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(FirebaseError firebaseError) {

             }
         });
    }

    private void getUpdates(DataSnapshot dataSnapshot)
    {
        movies.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Movie m=new Movie();
            m.setName(ds.getValue(Movie.class).getName());
            m.setDescription(ds.getValue(Movie.class).getDescription());

            movies.add(m);
        }

        if(movies.size()>0)
        {
            adapter=new MyAdapter(MainActivity.this,movies);
            rv.setAdapter(adapter);
        }else {
            Toast.makeText(MainActivity.this,"No data",Toast.LENGTH_SHORT).show();
        }

    }






}
