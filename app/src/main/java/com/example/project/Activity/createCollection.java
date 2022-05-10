package com.example.project.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.Database.DBHelper;
import com.example.project.Database.TagsDB;
import com.example.project.R;
import com.example.project.models.Collect;
import com.example.project.models.Root;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class createCollection extends AppCompatActivity {
    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Java", "C++", "Kotlin", "C", "Python", "Javascript"};
    Button button, submitBtn;
    EditText titleEdit, descriptionEdit;
    String title, description;
    Collect newColl;
    String[] array = null;

    StringBuilder stringBuilder;
    List<Root> lables = null;
    ArrayList<Root> checkRoot = null;
    Uri selectedImage;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_collection);

        // assign variable
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        titleEdit = findViewById(R.id.titleEdit);
        descriptionEdit = findViewById(R.id.descriptionEdit);
        findViewById(R.id.button3).setOnClickListener(view -> createColl());

        db = new DBHelper(this).getReadableDatabase();

        lables = TagsDB.getTags(db);
//        array = lables.toArray(new String[0]);
        List<Root> lables = TagsDB.getTags(db);
        String[] array = new String[lables.size()];
        for (int i = 0; i < lables.size(); i++) {
            array[i] = lables.get(i).getId()+ " " + lables.get(i).getText();
        }

        // initialize selected language array
        selectedLanguage = new boolean[array.length];

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(createCollection.this);

                // set title
                builder.setTitle("Select Tags");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(array, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            Log.d("JWT_DECODED", "body: " + lables.get(i).getId() + lables.get(i).getText() );
                            langList.add(i);
//                            langList.add(array[langList.get(i).getId()] + array[langList.get(i).getText()]);
                            for(Root root : lables) {
                                root.setId(i+1);
                                root.setText(lables.get(i).getText());
                                checkRoot.add(root);
                            }
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                            for(Root root : lables) {
                                root.setId(i+1);
                                root.setText(lables.get(i).getText());
                                checkRoot.remove(root);
                            }
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        stringBuilder = new StringBuilder();
                        ArrayList<Root> root = new ArrayList<>();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
//                            for(Root tag : langList) {
//
//                            }
                            // concat array value
                            stringBuilder.append(array[langList.get(j)]);

                            Log.d("JWT_DECODED", "body: " + array[langList.get(j)]);
                            // check condition
                            if (j != langList.size() - 1) {
                                // When j value  not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        textView.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedLanguage.length; j++) {
                            // remove all selection
                            selectedLanguage[j] = false;
                            // clear language list
                            langList.clear();
                            // clear text view value
                            textView.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }

    public void createColl() {
        getData();

        newColl.setTitle(titleEdit.getText().toString());
        newColl.setDescription(descriptionEdit.getText().toString());
        newColl.setTags(checkRoot);
//        newColl.setImage();
//        ArrayList<Root> tags = new ArrayList<>();
//        for(Root root : ) {
//            newColl.setTags();
//        }
        Log.d("JWT_DECODED", "body: " + langList);
        ArrayList<Root> root = new ArrayList<Root>();
//            for(int i = 0; i < langList.toArray().length; i++ ) {
////                Log.d("JWT_DECODED", "body: " + i);
//                root.add(new Root())
//            }
//        for(Root tags: lables) {
//            tags.setId();
//        }
//        newColl.setTags(root);


    }

    private void getData() {
        titleEdit.setText(titleEdit.getText().toString());
        descriptionEdit.setText(descriptionEdit.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {
            selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageURI(selectedImage);
        }
    }
}