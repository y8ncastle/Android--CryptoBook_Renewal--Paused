package yoonsungjeong.codechain.cryptobookrenewal;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {
    SQLiteDatabase db;

    int DBcount;

    String DBname = "CryptoBook";
    String tableName = "Settings";
    String sql;

    Cursor resultset;

    /////////////////////////////////////////////////////////////////

    SimpleDateFormat ex_dateFormat = new SimpleDateFormat("yyyyMMdd");
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    String rtc = ex_dateFormat.format(date);

    /////////////////////////////////////////////////////////////////

    String password, password_input;

    int push_bool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int write_check = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int read_check = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int internet_check = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        if ((write_check == PackageManager.PERMISSION_GRANTED) && (read_check == PackageManager.PERMISSION_GRANTED) && (internet_check == PackageManager.PERMISSION_GRANTED)) {
            db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

            try {
                sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "password VARCHAR2(15), " +
                        "push_bool INTEGER, " +
                        "ex_usd VARCHAR2(35), " +
                        "ex_jpy VARCHAR2(35), " +
                        "ex_cny VARCHAR2(35), " +
                        "ex_date VARCHAR2(35), " +
                        "ex_date_comp VARCHAR2(170), " +
                        "cur_order INTEGER, " +
                        "no_dist_display INTEGER, " +
                        "search_order INTEGER, " +
                        "add_count INTEGER, " +
                        "biggest VARCHAR2(85), " +
                        "encashed VARCHAR2(85), " +
                        "first_add VARCHAR2(65), " +
                        "recent_add VARCHAR2(65), " +
                        "info VARCHAR2(10));";
                db.execSQL(sql);

                sql = "SELECT password, push_bool FROM " + tableName + ";";

                resultset = db.rawQuery(sql, null);
                DBcount = resultset.getCount();

                if (DBcount != 0) {
                    resultset.moveToNext();

                    String temp_password = resultset.getString(0);
                    int temp_push_bool = resultset.getInt(1);

                    password = temp_password;
                    push_bool = temp_push_bool;

                    if (password.equals("CB") == true) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finish();
                    } else {

                    }
                } else {
                    sql = "INSERT INTO " + tableName + " (" +
                            "password, " +
                            "push_bool, " +
                            "ex_usd, " +
                            "ex_jpy, " +
                            "ex_cny, " +
                            "ex_date, " +
                            "ex_date_comp, " +
                            "cur_order, " +
                            "no_dist_display, " +
                            "search_order, " +
                            "add_count, " +
                            "biggest, " +
                            "encashed, " +
                            "first_add, " +
                            "recent_add, " +
                            "info) VALUES (" +
                            "'CB', " +
                            "0, " +
                            "'1000', " +
                            "'1000', " +
                            "'200', " +
                            "'0000.00.00 00:00:00', " +
                            "'1000/1000/200/" + rtc +
                            "', 0, " +
                            "0, " +
                            "0, " +
                            "0, " +
                            "'0', " +
                            "'0', " +
                            "'0000.00.00 00:00:00', " +
                            "'0000.00.00 00:00:00', " +
                            "'CB');";
                    db.execSQL(sql);

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }

                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Intent intent = new Intent(SplashActivity.this, FirstUseActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}