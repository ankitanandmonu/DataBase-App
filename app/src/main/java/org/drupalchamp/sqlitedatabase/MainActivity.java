package org.drupalchamp.sqlitedatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler dbHandler = new DBHandler(this);
        //Inserting Shop/Rows
        Log.d("Insert: ", "Inserting ..");
        dbHandler.addShop(new Shop(1,"Dunkin Donuts", "White Plains, NY 10601"));
        dbHandler.addShop(new Shop(2,"Pizza Porlar", "North West Avenue, Boston , USA"));
        dbHandler.addShop(new Shop(3,"Town Bakers", "Beverly Hills, CA 90210, USA"));
        // Reading all shops
        Log.d("Reading: ", "Reading all shops..");
        List<Shop> shops = dbHandler.getAllShops();

        for (Shop shop : shops) {
            String log = "Id: " + shop.getId() + " ,Name: " + shop.getName() + " ,Address: " + shop.getAddress();
        // Writing shops to log
            Log.d("Shop: : ", log);
        }
    }
}
