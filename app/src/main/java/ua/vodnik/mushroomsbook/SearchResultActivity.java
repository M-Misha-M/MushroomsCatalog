package ua.vodnik.mushroomsbook;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
String query;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.search_results_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
             query = searchIntent.getStringExtra(SearchManager.QUERY);
            getSupportActionBar().setTitle(query);
            Toast.makeText(SearchResultActivity.this , query , Toast.LENGTH_LONG).show();
        }

         String[] mushroom = getResources().getStringArray(R.array.mushrooms);
        ArrayList<String> searchResults = new ArrayList<String>();
        for (int i = 0; i < mushroom.length; i++) {
            if (mushroom[i].toLowerCase().contains(query.toLowerCase())) {
                searchResults.add(mushroom[i]);
            }
        }
       ListView listView = (ListView) findViewById(R.id.listView_searchResults);
         adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, searchResults);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResultActivity.this, Information.class);
                intent.putExtra("id", position);
                intent.putExtra("title", adapter.getItemId(position));
                startActivityForResult(intent, 0);
            }
        });

    }
}
