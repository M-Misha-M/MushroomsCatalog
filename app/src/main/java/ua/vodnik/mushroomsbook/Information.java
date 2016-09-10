package ua.vodnik.mushroomsbook;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class Information extends AppCompatActivity {
int id =0;
    public TextView textView;
private DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);



       getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        db =  new DataBaseHelper(this);


        String item = getIntent().getExtras().getString("title");
        id = getIntent().getExtras().getInt("id", 0);
        getSupportActionBar().setTitle(item);


         textView = (TextView) findViewById(R.id.textView1);
           textView.setMovementMethod(new ScrollingMovementMethod());
   textView.setText(db.getInfromation(id));

        String uri = "drawable/pic" + String.valueOf(id);
        int imageResource = getResources().getIdentifier(uri , null , getPackageName());
        ImageView imageView = (ImageView) findViewById(R.id.myIm1);
        Drawable image  = getResources().getDrawable(imageResource);
        imageView.setImageDrawable(image);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.maps:
                Intent intent = new Intent(Information.this , GoogleMapsActivity.class);
                startActivity(intent);
                break;


            case android.R.id.home:
                Intent intent1 = new Intent(this , MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
