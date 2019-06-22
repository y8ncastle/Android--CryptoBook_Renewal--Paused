package yoonsungjeong.codechain.cryptobookrenewal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BalanceActivity extends Activity {
    public static Context context;

    /////////////////////////////////////////////////////////////////

    SQLiteDatabase db;

    int DBcount;

    String DBname = "CryptoBook";
    String tableName = "Balance";
    String sql;

    Cursor resultset;

    /////////////////////////////////////////////////////////////////

    Intent intent;

    ListView b_list;

    /////////////////////////////////////////////////////////////////

    EditText s_window;

    ImageView hb_refresh, b_or, b_to_m, b_to_s, b_to_w, b_refresh, b_cancel, find;

    LinearLayout e_plate;

    TextView cur_sel2;

    /////////////////////////////////////////////////////////////////

    private final long finish_time = 2000;
    private long back_pressed_time = 0;

    DecimalFormat amount_form = new DecimalFormat("#,##0.########");
    DecimalFormat cur_form = new DecimalFormat("#,##0.##");

    /////////////////////////////////////////////////////////////////

    String[] names, s_names, amounts, prices, exchanges, custom_adrs, custom_keys, register_dates;
    int[] belongings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.balance);

        context = this;

        init();
        dataSet();

        /////////////////////////////////////////////////////////////////

        final CustomList cl = new CustomList(BalanceActivity.this);
        b_list = (ListView)findViewById(R.id.crypto_list);
        b_list.setAdapter(cl);

        b_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

        b_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(BalanceActivity.this, CryptoDetailActivity.class);
                String temp = names[i] + "/" + s_names[i] + "/" + amounts[i] + "/" + register_dates[i];
                intent.putExtra("data", temp);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        long temp_time = System.currentTimeMillis();
        long interval_time = temp_time - back_pressed_time;

        if (0 <= interval_time && finish_time >= interval_time)
            super.onBackPressed();
        else {
            back_pressed_time = temp_time;
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }

    public void init() {
        s_window = (EditText)findViewById(R.id.search_window);

        hb_refresh = (ImageView)findViewById(R.id.home_b_refresh);
        b_or = (ImageView)findViewById(R.id.order);
        b_to_m = (ImageView)findViewById(R.id.balance_to_main);
        b_to_s = (ImageView)findViewById(R.id.balance_to_statistic);
        b_to_w = (ImageView)findViewById(R.id.balance_to_wallet);
        b_refresh = (ImageView)findViewById(R.id.balance_refresh);
        b_cancel = (ImageView)findViewById(R.id.balance_cancel);
        find = (ImageView)findViewById(R.id.search);

        e_plate = (LinearLayout)findViewById(R.id.empty_plate);

        cur_sel2 = (TextView)findViewById(R.id.cur_selector2);
    }

    public void dataSet() {
        Animation rotation = AnimationUtils.loadAnimation(BalanceActivity.this, R.anim.rotation);
        b_refresh.startAnimation(rotation);

        db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

        try {
            sql = "SELECT name, s_name, amount, recent_price, exchange, custom_adr, custom_key, register_date FROM " + tableName + ";";
            resultset = db.rawQuery(sql, null);

            DBcount = resultset.getCount();

            if (DBcount != 0)
                e_plate.setVisibility(View.GONE);
            else
                e_plate.setVisibility(View.VISIBLE);

            names = new String[DBcount];
            s_names = new String[DBcount];
            amounts = new String[DBcount];
            prices = new String[DBcount];
            exchanges = new String[DBcount];
            custom_adrs = new String[DBcount];
            custom_keys = new String[DBcount];
            register_dates = new String[DBcount];

            for (int i=0; i<DBcount; i++) {
                resultset.moveToNext();

                String temp_name = resultset.getString(0);
                String temp_s_name = resultset.getString(1);
                String temp_amount = resultset.getString(2);
                String temp_price = resultset.getString(3);
                String temp_exchange = resultset.getString(4);
                String temp_custom_adr = resultset.getString(5);
                String temp_custom_key = resultset.getString(6);
                String temp_register_date = resultset.getString(7);

                names[i] = temp_name;
                s_names[i] = temp_s_name;
                amounts[i] = temp_amount;
                prices[i] = temp_price;
                exchanges[i] = temp_exchange;
                custom_adrs[i] = temp_custom_adr;
                custom_keys[i] = temp_custom_key;
                register_dates[i] = temp_register_date;
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refresh() {
        intent = new Intent(BalanceActivity.this, BalanceActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void BalanceClickPack(View view) {
        switch (view.getId()) {
            case R.id.home_b_refresh:
                intent = new Intent(BalanceActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.balance_cancel:
                s_window.setText("");
                break;
            case R.id.balance_refresh:
                dataSet();
                break;
            case R.id.balance_to_main:
                intent = new Intent(BalanceActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                break;
        }
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;

        public CustomList(Activity context) {
            super(context, R.layout.balance_list, names);
            this.context = context;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            LayoutInflater li = context.getLayoutInflater();
            View result = li.inflate(R.layout.balance_list, null, true);
            ImageView image = (ImageView)result.findViewById(R.id.list_image);
            TextView name = (TextView)result.findViewById(R.id.list_name);
            TextView s_name = (TextView)result.findViewById(R.id.list_s_name);
            TextView amount = (TextView)result.findViewById(R.id.list_amount);
            TextView price = (TextView)result.findViewById(R.id.list_price);
            TextView ex = (TextView)result.findViewById(R.id.list_ex);
            View ex_c = (View)result.findViewById(R.id.ex_color);

            try {
                File imgFile = new  File(Environment.getExternalStorageDirectory() + "/CryptoBook/", s_names[position] + ".png");
                File imgFile2 = new  File(Environment.getExternalStorageDirectory() + "/CryptoBook/", s_names[position] + ".jpg");

                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    image.setImageBitmap(myBitmap);
                } else if (imgFile2.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    image.setImageBitmap(myBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            name.setText(names[position]);
            s_name.setText(s_names[position]);

            Double temp_amount = Double.parseDouble(amounts[position]);
            amount.setText(amount_form.format(temp_amount));

            BigDecimal mul = new BigDecimal(amounts[position]);
            BigDecimal m_comp = new BigDecimal(prices[position]);
            String temp_sum = String.valueOf(mul.multiply(m_comp));

            Double temp_price = Double.parseDouble(temp_sum);
            price.setText(cur_form.format(temp_price));

            name.setSelected(true);
            s_name.setSelected(true);
            amount.setSelected(true);
            price.setSelected(true);

            if (exchanges[position].equals("-") == true && custom_adrs[position].equals("-") == true && custom_keys[position].equals("-") == true) {
                ex_c.setBackgroundDrawable(ContextCompat.getDrawable(BalanceActivity.this, R.drawable.round_shape_lr_wg_i_g));
                ex.setText("비상장");
            }

            return result;
        }
    }
}