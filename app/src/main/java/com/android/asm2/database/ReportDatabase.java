package com.android.asm2.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.asm2.helper.BroadcastReceiverHelper;
import com.android.asm2.model.Report;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ReportDatabase extends SQLiteOpenHelper {
    private Context context;
    private static ReportDatabase reportDatabase;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "report.db";

    private ReportDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static ReportDatabase getInstance() {
        return reportDatabase;
    }

    public static ReportDatabase initAndGetInstance(Context context) {
        BroadcastReceiverHelper helper = BroadcastReceiverHelper.getInstance();
        IntentFilter filter = new IntentFilter();
        filter.addAction("Report updated");
        filter.addAction("Report added");
        context.registerReceiver(helper, filter);
        reportDatabase = new ReportDatabase(context);
        return reportDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS report(zone_id TEXT PRIMARY KEY, created TEXT, " +
                "tested INTEGER, volunteer INTEGER, sample INTEGER, positive_1st INTEGER, " +
                "positive INTEGER, note TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS report");
        onCreate(db);
    }

    public void clearData() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DELETE FROM report");
    }

    public Report getReportByZoneId(String zoneId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("report", new String[]{"zone_id", "created", "tested",
                        "volunteer", "sample", "positive_1st", "positive", "note"},
                "zone_id = ?",
                new String[]{zoneId}, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        else return null;
        if (cursor.getCount() == 0) return null;
        return new Report(cursor.getString(0), cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)),
                Integer.parseInt(cursor.getString(4)),
                Integer.parseInt(cursor.getString(5)),
                Integer.parseInt(cursor.getString(6)),
                cursor.getString(7));
    }

    public void updateReport(Report report) {
        Intent intent = new Intent("Report updated");
        context.sendBroadcast(intent);

        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("tested", report.getTested());
        values.put("volunteer", report.getVolunteer());
        values.put("sample", report.getSample());
        values.put("positive_1st", report.getPositive1st());
        values.put("positive", report.getPositive());
        values.put("note", report.getNote());
        db.update("report", values, "zone_id = ?",
                new String[]{String.valueOf(report.getZoneId())});
        db.close();
    }

    public void addReport(Report report) {
        Intent intent = new Intent("Report added");
        context.sendBroadcast(intent);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("zone_id", report.getZoneId());
        values.put("created", report.getCreated());
        values.put("tested", report.getTested());
        values.put("volunteer", report.getVolunteer());
        values.put("sample", report.getSample());
        values.put("positive_1st", report.getPositive1st());
        values.put("positive", report.getPositive());
        values.put("note", report.getNote());
        db.insert("report", null, values);
        db.close();
    }

    public void deleteReportByZoneId(String zoneId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("report", "zone_id = ?", new String[]{zoneId});
        db.close();
    }

    public boolean isEmpty() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from report", null);
        int count = cursor.getCount();
        cursor.close();
        return count == 0;
    }

    public ArrayList<Report> getAllReports() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from report", null);
        ArrayList<Report> reportArrayList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                reportArrayList.add(new Report(cursor.getString(0), cursor.getString(1),
                        Integer.parseInt(cursor.getString(2)),
                        Integer.parseInt(cursor.getString(3)),
                        Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        Integer.parseInt(cursor.getString(6)),
                        cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return reportArrayList;
    }

    public void handleData(Report newReport) {
        Report existingReport = getReportByZoneId(newReport.getZoneId());
        if (existingReport == null) addReport(newReport);
        else {
            Gson gson = new Gson();
            if (!gson.toJson(newReport).equals(gson.toJson(existingReport)))
                updateReport(newReport);
        }
    }
}
