package com.example.citybus;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citybus.AdapterClasses.RvSearchStopAdapter;
import com.example.citybus.ConstantClass.Model1;
import com.example.citybus.ModelClases.CourseModel;

import java.util.ArrayList;

public class SelectStop extends AppCompatActivity {
    // List View object
    ListView listView;

    // Define array adapter for ListView
//    ArrayAdapter<String> adapter;

    // Define array List for List View data
    ArrayList<String> mylist;
    private RecyclerView searchStopRV;

    // variable for our adapter
    // class and array list
    RvSearchStopAdapter searchStopAdapter;
    private ArrayList<String> searchStopList;
    Context context;
    String abc = "abc";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchstop_layout);
        context = getApplicationContext();
        searchStopRV = findViewById(R.id.recyclerView);
        // calling method to
        // build recycler view.
        buildRecyclerView();
// initialise ListView with id
//        listView = findViewById(R.id.listView);
//        // Add items to Array List
//        mylist = new ArrayList<>();
//        mylist.add("C");
//        mylist.add("C++");
//        mylist.add("C#");
//        mylist.add("Java");
//        mylist.add("Advanced java");
//        mylist.add("Interview prep with c++");
//        mylist.add("Interview prep with java");
//        mylist.add("data structures with c");
//        mylist.add("data structures with java");
//
//        // Set adapter to ListView
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String s = listView.getItemAtPosition(i).toString();
//
//                startActivity(new Intent(SelectStop.this,MainActivity.class).putExtra("SelectStop",s));
//            }
//        });

    }

    // calling on create option menu
    // layout to inflate our menu file.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.searchoption_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(Model1.SelectType);
        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<String> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (String item : searchStopList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            searchStopAdapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

       // below line we are creating a new array list
//        courseModelArrayList = new ArrayList<CourseModel>();

        // below line is to add data to our array list.
//        courseModelArrayList.add(new CourseModel("DSA"));
//        courseModelArrayList.add(new CourseModel("JAVA"));
//        courseModelArrayList.add(new CourseModel("C++"));
//        courseModelArrayList.add(new CourseModel("Python"));
//        courseModelArrayList.add(new CourseModel("Fork CPP"));

        searchStopList = new ArrayList<>();
        searchStopList.add("a");
        searchStopList.add("b");
        searchStopList.add("c");
        searchStopList.add("d");
        searchStopList.add("e");

        // initializing our adapter class.
        searchStopAdapter = new RvSearchStopAdapter(searchStopList, SelectStop.this);
        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        searchStopRV.setHasFixedSize(true);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(context, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(getResources().getDrawable(R.drawable.rcdevider_layout));
        searchStopRV.addItemDecoration(itemDecorator);
        // setting layout manager
        // to our recycler view.
        searchStopRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        searchStopRV.setAdapter(searchStopAdapter);
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.searchoption_menu,menu);
//
//        // Initialise menu item search bar
//        // with id and take its object
//        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            // Override onQueryTextSubmit method which is call when submit query is searched
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                // If the list contains the search query than filter the adapter
//                // using the filter method with the query as its argument
//                if (mylist.contains(query)) {
//                    adapter.getFilter().filter(query);
//                } else {
//                    // Search query not found in List View
//                    Toast.makeText(SelectStop.this, "Not found", Toast.LENGTH_LONG).show();
//                }
//                return false;
//            }
//
//            // This method is overridden to filter the adapter according
//            // to a search query when the user is typing search
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapter.getFilter().filter(newText);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
}
