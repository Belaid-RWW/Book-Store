package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button update_book, delete_button;

    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_book = findViewById(R.id.update_book);
        delete_button = findViewById(R.id.delete_book);

        getIntentData();

        ActionBar ab = getSupportActionBar();
        ab.setTitle(title);

        update_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.updateData(id, title, author, pages);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
    }

    void getIntentData(){
        if(getIntent().hasExtra("ID") && getIntent().hasExtra("Titre") && getIntent().hasExtra("Auteur")
            && getIntent().hasExtra("Pages")) {
                id = getIntent().getStringExtra("ID");
                title = getIntent().getStringExtra("Titre");
                author = getIntent().getStringExtra("Auteur");
                pages = getIntent().getStringExtra("Pages");

                title_input.setText(title);
                author_input.setText(author);
                pages_input.setText(pages);

        } else{
            Toast.makeText(this, "No data", Toast.LENGTH_LONG).show();
        }
    }



    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Supprimer " + title + "?");
        builder.setMessage("Voulez vous vraiment supprimez le livre "+title+"?");
        builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteData(id);
                finish();
            }
        });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}