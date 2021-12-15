package com.android.asm2.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.asm2.helper.BroadcastReceiverHelper;
import com.android.asm2.model.User;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

/***
 * User Database to handle data modification to user.db
 */
public class UserDatabase extends SQLiteOpenHelper {
    private Context context;
    private static UserDatabase userDatabase;
    private static User currentUser;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";

    private UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static UserDatabase getInstance() {
        return userDatabase;
    }

    public static UserDatabase initAndGetInstance(Context context) {
        BroadcastReceiverHelper helper = BroadcastReceiverHelper.getInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction("User updated");
        filter.addAction("User added");
        context.registerReceiver(helper, filter);
        userDatabase = new UserDatabase(context);
        return userDatabase;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserDatabase.currentUser = currentUser;
    }

    private String arrayListToString(ArrayList<String> arr) {
        String delimiter = "_,_";
        String result = "";
        for (int i = 0; i < arr.size(); i++) {
            result += arr.get(i);
            if (i < arr.size() - 1) result += delimiter;
        }
        return result;
    }

    private ArrayList<String> stringToArrayList(String str) {
        if (str.equals("")) return new ArrayList<>();
        String delimiter = "_,_";
        return new ArrayList<>(Arrays.asList(str.split(delimiter)));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user(username TEXT PRIMARY KEY, password TEXT," +
                " email TEXT, phone TEXT, name TEXT, role TEXT, joined_zones TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    public void clearData() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM user");
    }

    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("user",
                new String[]{"username", "password", "email", "phone", "name", "role", "joined_zones"},
                "username = ?",
                new String[]{username}, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        else return null;
        if (cursor.getCount() == 0) return null;
        return new User(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                stringToArrayList(cursor.getString(6)));
    }

    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.query("user",
                new String[]{"username", "password", "email", "phone", "name", "role", "joined_zones"},
                "email = ?",
                new String[]{email}, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        else return null;
        if (cursor.getCount() == 0) return null;
        return new User(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                cursor.getString(3), cursor.getString(4), cursor.getString(5),
                stringToArrayList(cursor.getString(6)));
    }

    public void updateUser(User user) {
        Intent intent = new Intent("User updated");
        context.sendBroadcast(intent);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("name", user.getName());
        values.put("joined_zones", arrayListToString(user.getJoinedZones()));
        db.update("user", values, "username = ?",
                new String[]{String.valueOf(user.getUsername())});
        db.close();
    }

    public void addUser(User user) {
        Intent intent = new Intent("User added");
        context.sendBroadcast(intent);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("name", user.getName());
        values.put("role", user.getRole());
        values.put("joined_zones", arrayListToString(user.getJoinedZones()));
        db.insert("user", null, values);
        db.close();
    }

    public void deleteUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("user", "username = ?", new String[]{username});
        db.close();
    }

    public boolean isEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user", null);
        int count = cursor.getCount();
        cursor.close();
        return count == 0;
    }

    public ArrayList<User> getAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user", null);
        ArrayList<User> userArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                userArrayList.add(new User(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getString(4),
                        cursor.getString(5), stringToArrayList(cursor.getString(6))));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userArrayList;
    }

    public void handleData(User newUser) {
        User existingUser = getUserByUsername(newUser.getUsername());
        if (existingUser == null) addUser(newUser);
        else {
            Gson gson = new Gson();
            if (!gson.toJson(newUser).equals(gson.toJson(existingUser)))
                updateUser(newUser);
        }
    }
}
