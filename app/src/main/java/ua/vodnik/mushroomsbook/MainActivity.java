package ua.vodnik.mushroomsbook;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {


    private ListView listView;
    private DataBaseHelper db;
    ArrayAdapter<String> adapter;
    int id = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);





        //myToolbar.setTitleTextColor(Color.WHITE);


        db = new DataBaseHelper(this);

        db.createdatabase();
        db.copydatabase();

        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        File database = getApplicationContext().getDatabasePath("mushrooms.db");

        if (!database.exists()) {
            // Database does not exist so copy it from assets here
            Log.i("Database", "Not Found");
        } else {
            Log.i("Database", "Found");
        }

        listView = (ListView) findViewById(R.id.lvChapters);
        final String[] mushrooms = {
                "Білий гриб",
                "Моховик",
                "Лисички",
                "Опеньки",
                "Маслюк",
                "Підберезник",
                "Рижик",
                "Підосичник",
                "Їжовик жовтуватий",
                "Глива",
                "Печериця садова",
                "Гладиш звичайний",
                "Ковпак кільчастий",
                "Груздь"

        };
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.mushrooms));
        listView.setAdapter(adapter);
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Information.class);
                intent.putExtra("id", position);
                intent.putExtra("title", adapter.getItem(position));
                startActivityForResult(intent, 0);
            }
        });


    }


    @Override
    public void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {


            case R.id.maps:
                Intent intent = new Intent(MainActivity.this , GoogleMapsActivity.class);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            db.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }




    }
}
