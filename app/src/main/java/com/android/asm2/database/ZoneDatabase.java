package com.android.asm2.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.asm2.model.Zone;

import java.util.ArrayList;

public class ZoneDatabase extends SQLiteOpenHelper {
    private static ZoneDatabase zoneDatabase;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "zone.db";

    private ZoneDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static ZoneDatabase getInstance() {
        return zoneDatabase;
    }

    public static ZoneDatabase initAndGetInstance(Context context) {
        zoneDatabase = new ZoneDatabase(context);
        return zoneDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS zone(id TEXT PRIMARY KEY, name TEXT, " +
                "latitude REAL, longitude REAL, duration REAL, quantity INTEGER, leader TEXT, " +
                "created_date TEXT, closed_date TEXT, start_date TEXT, start_time TEXT, " +
                "description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS zone");
        onCreate(db);
    }

    public void clearData() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM zone");
    }

    public Zone getZoneById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("zone",
                new String[]{"id", "name", "latitude", "longitude", "duration", "quantity",
                        "leader", "created_date", "closed_date", "start_date", "start_time",
                        "description"},
                "id = ?",
                new String[]{id}, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        else return null;
        if (cursor.getCount() == 0) return null;
        return new Zone(cursor.getString(0), cursor.getString(1),
                Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)),
                Float.parseFloat(cursor.getString(4)), Integer.parseInt(cursor.getString(5)),
                cursor.getString(6), cursor.getString(7), cursor.getString(8),
                cursor.getString(9), cursor.getString(10), cursor.getString(11));
    }

    public void updateZone(Zone zone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", zone.getName());
        values.put("latitude", zone.getLatitude());
        values.put("longitude", zone.getLongitude());
        values.put("duration", zone.getDuration());
        values.put("quantity", zone.getQuantity());
        values.put("closed_date", zone.getClosedDate());
        values.put("start_date", zone.getStartDate());
        values.put("start_time", zone.getStartTime());
        values.put("description", zone.getDescription());
        db.update("zone", values, "id = ?",
                new String[]{String.valueOf(zone.getId())});
        db.close();
    }

    public void addZone(Zone zone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", zone.getId());
        values.put("name", zone.getName());
        values.put("latitude", zone.getLatitude());
        values.put("longitude", zone.getLongitude());
        values.put("duration", zone.getDuration());
        values.put("quantity", zone.getQuantity());
        values.put("leader", zone.getLeader());
        values.put("closed_date", zone.getClosedDate());
        values.put("created_date", zone.getCreatedDate());
        values.put("start_date", zone.getStartDate());
        values.put("start_time", zone.getStartTime());
        values.put("description", zone.getDescription());
        db.insert("zone", null, values);
        db.close();
    }

    public void deleteZoneById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("zone", "id = ?", new String[]{id});
        db.close();
    }

    public boolean isEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from zone", null);
        int count = cursor.getCount();
        cursor.close();
        return count == 0;
    }

    public ArrayList<Zone> getAllZones() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from zone", null);
        ArrayList<Zone> zoneArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                zoneArrayList.add(new Zone(cursor.getString(0), cursor.getString(1),
                        Float.parseFloat(cursor.getString(2)),
                        Float.parseFloat(cursor.getString(3)),
                        Float.parseFloat(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return zoneArrayList;
    }
}