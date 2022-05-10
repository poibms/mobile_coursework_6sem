package com.example.project.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activity.SelectedTripActivity;
import com.example.project.Database.CollectDB;
import com.example.project.Database.DBHelper;
import com.example.project.Database.TagsDB;
import com.example.project.Helper.DishesAdapter;
import com.example.project.Helper.SystemHelper;
import com.example.project.Model.Trip;
import com.example.project.R;
import com.example.project.api.OnFetchDataListener;
import com.example.project.api.requestManager.RequestCollectionManager;
import com.example.project.api.requestManager.RequestTagsManager;
import com.example.project.models.Root;
import com.example.project.models.Tags;
import com.example.project.models.Collect;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;


public class SearchFragment extends Fragment {

    public EditText startDateEdit, fromEdit, toEdit, capacityEdit;
    private Calendar date;
    private Button searchBtn;
    private SQLiteDatabase db;
    private String from, to, startDate;
    private int capacity;
    private RecyclerView collectionRecycler;
    private CustomListAdapter customListAdapter;
    private TextView textViewResult;
    private Spinner spinner;
    private ListView listView;

    private Tags tagsArray;
    private ArrayList<Collect> collArray;



    TextView textView;
    boolean[] selectedLanguage;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] langArray = {"Java", "C++", "Kotlin", "C", "Python", "Javascript"};

    ArrayList<Trip> listTrip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    private final OnFetchDataListener<Tags> getTagsListener = new OnFetchDataListener<Tags>() {
        @Override
        public void onFetchData(Response<Tags> response) {
            if(response.isSuccessful()) {
                tagsArray = response.body();
                Log.d("JWT_DECODED", "body: " + tagsArray);

                TagsDB.deleteAllTags(db);
                TagsDB.addTags(db, tagsArray.tags);
            }
            else {
                Toast.makeText(getContext(), "Error by getting ", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFetchError(Throwable error) {
            Log.e("JWT_DECODED", "body: " + error);
            Toast.makeText(getContext(), "Error by getting dishes", Toast.LENGTH_LONG).show();
        }
    };

    private final OnFetchDataListener<ArrayList<Collect>> getCollListener = new OnFetchDataListener<ArrayList<Collect>>() {
        @Override
        public void onFetchData(Response<ArrayList<Collect>> response) {
            if(response.isSuccessful()) {
                collArray = response.body();
                displayCollect(collArray);

                CollectDB.deleteAllCollect(db);
                CollectDB.addCollections(db, collArray);
            }
            else {
                Toast.makeText(getContext(), "Error by getting ", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        public void onFetchError(Throwable error) {
            Log.e("JWT_DECODED", "body: " + error);
            Toast.makeText(getContext(), "Error by getting dishes", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fromEdit = (EditText) view.findViewById(R.id.fromEdit);
        toEdit = (EditText) view.findViewById(R.id.toEdit);
        capacityEdit = (EditText) view.findViewById(R.id.capacityEdit);
//        listView = (ListView) view.findViewById(R.id.tripListView);
        collectionRecycler = (RecyclerView) view.findViewById(R.id.collect_recycler);

        db = new DBHelper(getContext()).getReadableDatabase();

        listTrip = new ArrayList<>();

        getData();
        List<Root> lables = TagsDB.getTags(db);
        String[] array = new String[lables.size()];
        for (int i = 0; i < lables.size(); i++) {
            array[i] = lables.get(i).getId()+ " " + lables.get(i).getText();
        }
//        String[] array = lables.toArray(new String[] {});
//        String[] array = Arrays.copyOf(lables, lables.length, String[].class);
//        String[] array = new String[lables.size()];
//        int index = 0;
//        for (Object value : lables) {
//            array[index] = (String) value;
//            index++;
//        }
//        StringBuilder sb = new StringBuilder();
//        for (String s : lables)
//        {
//            sb.append(s);
//            sb.append("\t");
//        }
        Log.d("JWT_DECODED", "spin: " + Arrays.toString(langArray));
        Log.d("JWT_DECODED", "spin: " + Arrays.toString(array));

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(),
//                android.R.layout.simple_spinner_item, lables);
//
//        // Drop down layout style - list view with radio button
//        dataAdapter
//                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);


//        customListAdapter = new CustomListAdapter(getContext(), collArray);
//        listView.setAdapter(customListAdapter);


        // assign variable
        textView = view.findViewById(R.id.textView);

        // initialize selected language array
            selectedLanguage = new boolean[array.length];


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // set title
                builder.setTitle("Select tags");

                // set dialog non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(array, selectedLanguage, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position  in lang list
                            langList.add(i);
                            // Sort array list
                            Collections.sort(langList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            langList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();
                        // use for loop
                        for (int j = 0; j < langList.size(); j++) {
                            // concat array value
                            stringBuilder.append(array[langList.get(j)]);
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
    }

    public void getData() {
        if(SystemHelper.isNetworkAvailable(getContext())) {
            RequestTagsManager tagsManager = new RequestTagsManager(getContext());
            tagsManager.getTags(getTagsListener);
            RequestCollectionManager collectionManager = new RequestCollectionManager(getContext());
            collectionManager.getCollections(getCollListener);
        } else {

        }
    }

    private void displayCollect(ArrayList<Collect> collections) {
        collectionRecycler.setHasFixedSize(true);
        collectionRecycler.setLayoutManager(new GridLayoutManager(getContext(), 1));
        DishesAdapter dishesAdapter = new DishesAdapter(getContext(), collections);
        collectionRecycler.setAdapter(dishesAdapter);
    }


    public class CustomListAdapter extends BaseAdapter {

        private ArrayList<Collect> trips;
        private Context context;

        public CustomListAdapter(Context context, ArrayList<Collect> trips) {
            this.context = context;
            this.trips = trips;
        }

        @Override
        public int getCount() {return trips.size();}

        @Override
        public Object getItem(int i) {return null;}

        @Override
        public long getItemId(int i) {return 0;}

        public void updateStudentsList(ArrayList<Collect> trips) {
            this.trips.clear();
            this.trips.addAll(trips);
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int pos, View vw, ViewGroup viewGroup) {
            View view = getLayoutInflater().inflate(R.layout.list_item, null);

            TextView from = (TextView) view.findViewById(R.id.from);
            TextView to = (TextView) view.findViewById(R.id.to);
            TextView time = (TextView) view.findViewById(R.id.startTime);
            TextView transportType = (TextView) view.findViewById(R.id.transportType);
            TextView capacity = (TextView) view.findViewById(R.id.capacity);
            TextView price = (TextView) view.findViewById(R.id.price);

            from.setText(trips.get(pos).getTitle());
            to.setText(trips.get(pos).getDescription());
            time.setText(trips.get(pos).getId());
//            transportType.setText(trips.get(pos).getTransportType());
//            capacity.setText(trips.get(pos).getCapacity() + "");
//            price.setText(trips.get(pos).getPrice() + "");

            view.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), SelectedTripActivity.class);
                intent.putExtra("Trip", trips.get(pos));
                startActivity(intent);
            });

            return view;
        }
    }
}
