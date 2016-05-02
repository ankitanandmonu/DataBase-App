package org.drupalchamp.sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user
 * Date: 5/2/2016
 * SQliteDatabase
 */
public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "shopsInfo";
    // Contacts table name
    private static final String TABLE_SHOPS = "shops";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SH_ADDR = "shop_address";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SHOPS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SH_ADDR + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
        // Creating tables again
        onCreate(db);
    }

    //adding new shop
    public void addShop(Shop shop){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME,shop.getName()); //shop name
        values.put(KEY_SH_ADDR,shop.getAddress());//address name
        //Inserting row
        database.insert(TABLE_SHOPS,null,values);
        database.close();
    }

    //Getting Shop
    public Shop getShop(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_SHOPS, new String[]{KEY_ID,KEY_NAME,KEY_SH_ADDR},KEY_ID +"=?",
                new String[]{String.valueOf(id)},null,null,null,null);
        if (cursor != null)
            cursor.moveToFirst();
            Shop shop = new Shop(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
        //return shop
        return shop;

    }
    //getting all shops
    public List<Shop> getAllShops(){
        List<Shop> shopList = new ArrayList<Shop>();
        //Select all query
        String selectQuery = "SELECT * FROM "+TABLE_SHOPS;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()){
            do {
                Shop shop = new Shop();
                shop.setId(Integer.parseInt(cursor.getString(0)));
                shop.setName(cursor.getString(1));
                shop.setAddress(cursor.getString(2));
                // Adding contact to list
                shopList.add(shop);
            }while (cursor.moveToNext());
        }
        return shopList;
    }
    // Getting shops Count
    public int getShopsCount(){
        String countQuery = "SELECT * FROM " + TABLE_SHOPS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating a shop
    public int updateShop(Shop shop){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, shop.getName());
        values.put(KEY_SH_ADDR, shop.getAddress());
        // updating row
        return db.update(TABLE_SHOPS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(shop.getId())});
    }
    // Deleting a shop
    public void deleteShop(Shop shop) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SHOPS, KEY_ID + " = ?",
                new String[] { String.valueOf(shop.getId()) });
        db.close();
    }
}
