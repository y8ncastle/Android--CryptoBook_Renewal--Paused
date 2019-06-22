package yoonsungjeong.codechain.cryptobookrenewal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {
    SQLiteDatabase db;

    int DBcount;

    String DBname = "CryptoBook";
    String tableName = "Balance";
    String tableName2 = "Settings";
    String sql;

    Cursor resultset;

    /////////////////////////////////////////////////////////////////

    Intent intent;

    /////////////////////////////////////////////////////////////////

    ImageView h_refresh, sets;
    ImageView m_home, m_to_b, m_plus, m_to_s, m_to_w;

    LinearLayout p_main_7, p_main_71, p_main_72;

    TextView cur_sel, sum_ass, p_ratio, air_c, ico_c, invest_c;
    TextView be_crypto, nd_crypto, cash, usd_r, jpy_r, cny_r, ex_info;
    TextView add_f, add_d;

    /////////////////////////////////////////////////////////////////

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss");
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    String today = dateFormat.format(date);

    SimpleDateFormat ex_dateFormat = new SimpleDateFormat("yyyyMMdd");
    Date date2 = new Date(now);
    String rtc = ex_dateFormat.format(date2);

    DecimalFormat amount_form = new DecimalFormat("#,##0.########");
    DecimalFormat cur_form = new DecimalFormat("#,##0.##");

    /////////////////////////////////////////////////////////////////

    private final long finish_time = 2000;
    private long back_pressed_time = 0;

    private String Auth_key = "isFaZsuYj9zcbabx5XpfPVxT8TDAixzw";

    /////////////////////////////////////////////////////////////////

    int menu_check, add_fast_name_check, belong, way_tg, sum_count;
    int add_detail_name_check;
    int a_count, i_count, in_count, b_count, ratio;
    int ab_count, ib_count, inb_count;

    double usd_price, jpy_price, cny_price, usd_price_y, jpy_price_y, cny_price_y;

    String sum_asset, be_crypt, no_dist_crypt, cash_crypt, biggest, standard_date;
    String usd_ex, jpy_ex, cny_ex;
    String temp_usd, temp_jpy, temp_cny, temp_date;

    /////////////////////////////////////////////////////////////////

    int[] belongings, way_to_gets, list_bools, encash_bools;

    String[] amounts, recent_prices;
    String[] exchanges, custom_adrs, custom_keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);

        init();
        dataSet();

        /////////////////////////////////////////////////////////////////

        m_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation fade = new AlphaAnimation(0, 1);
                fade.setDuration(1000);

                if (menu_check == 0) {
                    Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation2);
                    m_plus.startAnimation(rotation);

                    m_home.setVisibility(View.GONE);
                    m_to_b.setVisibility(View.GONE);
                    m_to_s.setVisibility(View.GONE);
                    m_to_w.setVisibility(View.GONE);
                    p_main_7.setVisibility(View.VISIBLE);
                    p_main_71.setVisibility(View.VISIBLE);
                    p_main_72.setVisibility(View.VISIBLE);
                    add_f.setVisibility(View.VISIBLE);
                    add_d.setVisibility(View.VISIBLE);
                    add_f.setText("빠른 추가");
                    add_d.setText("상세 추가");

                    p_main_7.setAnimation(fade);

                    menu_check = 1;
                } else if (menu_check == 1) {
                    Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation2_reverse);
                    m_plus.startAnimation(rotation);

                    m_home.setVisibility(View.VISIBLE);
                    m_to_b.setVisibility(View.VISIBLE);
                    m_to_s.setVisibility(View.VISIBLE);
                    m_to_w.setVisibility(View.VISIBLE);
                    p_main_7.setVisibility(View.GONE);
                    p_main_71.setVisibility(View.GONE);
                    p_main_72.setVisibility(View.GONE);
                    add_f.setVisibility(View.GONE);
                    add_d.setVisibility(View.GONE);

                    m_home.setAnimation(fade);
                    m_to_b.setAnimation(fade);
                    m_to_s.setAnimation(fade);
                    m_to_w.setAnimation(fade);

                    menu_check = 0;
                } else if (menu_check == 2) {
                    Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation2_reverse);
                    m_plus.startAnimation(rotation);

                    m_plus.setImageResource(R.drawable.plus_icon);

                    m_home.setVisibility(View.VISIBLE);
                    m_to_b.setVisibility(View.VISIBLE);
                    m_to_s.setVisibility(View.VISIBLE);
                    m_to_w.setVisibility(View.VISIBLE);
                    p_main_7.setVisibility(View.GONE);
                    p_main_71.setVisibility(View.GONE);
                    p_main_72.setVisibility(View.GONE);
                    add_f.setVisibility(View.GONE);
                    add_d.setVisibility(View.GONE);

                    m_home.setAnimation(fade);
                    m_to_b.setAnimation(fade);
                    m_to_s.setAnimation(fade);
                    m_to_w.setAnimation(fade);

                    menu_check = 0;
                }
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
        h_refresh = (ImageView)findViewById(R.id.home_refresh);
        sets = (ImageView)findViewById(R.id.settings);
        m_home = (ImageView)findViewById(R.id.main_home);
        m_to_b = (ImageView)findViewById(R.id.main_to_balance);
        m_plus = (ImageView)findViewById(R.id.main_plus);
        m_to_s = (ImageView)findViewById(R.id.main_to_statistic);
        m_to_w = (ImageView)findViewById(R.id.main_to_wallet);

        /////////////////////////////////////////////////////////////////

        p_main_7 = (LinearLayout)findViewById(R.id.plate_main_7);
        p_main_71 = (LinearLayout)findViewById(R.id.plate_main_7_1);
        p_main_72 = (LinearLayout)findViewById(R.id.plate_main_7_2);

        /////////////////////////////////////////////////////////////////

        cur_sel = (TextView)findViewById(R.id.cur_selector);
        sum_ass = (TextView)findViewById(R.id.sum_of_asset);
        p_ratio = (TextView)findViewById(R.id.part_ratio);
        air_c = (TextView)findViewById(R.id.airdrop_count);
        ico_c = (TextView)findViewById(R.id.ico_count);
        invest_c = (TextView)findViewById(R.id.invest_count);
        be_crypto = (TextView)findViewById(R.id.belong_crypto);
        nd_crypto = (TextView)findViewById(R.id.no_dist_crypto);
        cash = (TextView)findViewById(R.id.encash);
        usd_r = (TextView)findViewById(R.id.usd_rate);
        jpy_r = (TextView)findViewById(R.id.jpy_rate);
        cny_r = (TextView)findViewById(R.id.cny_rate);
        ex_info = (TextView)findViewById(R.id.exchange_info);
        add_f = (TextView)findViewById(R.id.add_fast);
        add_d = (TextView)findViewById(R.id.add_detail);
    }

    public void dataSet() {
        sum_asset = "0";

        a_count = 0;
        i_count = 0;
        in_count = 0;

        ab_count = 0;
        ib_count = 0;
        inb_count = 0;

        ratio = 0;
        b_count = 0;

        be_crypt = "0";
        no_dist_crypt = "0";
        cash_crypt = "0";

        usd_ex = "0";
        jpy_ex = "0";
        cny_ex = "0";

        usd_price = 0;
        jpy_price = 0;
        cny_price = 0;

        usd_price_y = 0;
        jpy_price_y = 0;
        cny_price_y = 0;

        /////////////////////////////////////////////////////////////////

        db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

        try {
            sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "name VARCHAR2(65), " +
                    "s_name VARCHAR2(35), " +
                    "amount VARCHAR2(65), " +
                    "first_price VARCHAR2(50), " +
                    "recent_price VARCHAR2(50), " +
                    "belonging INTEGER, " +
                    "belonging_loc VARCHAR2(3005), " +
                    "way_to_get INTEGER, " +
                    "platform VARCHAR2(65), " +
                    "exchange VARCHAR2(305), " +
                    "custom_adr VARCHAR2(3005), " +
                    "custom_key VARCHAR2(3005), " +
                    "total_supply VARCHAR2(80), " +
                    "contract VARCHAR2(305), " +
                    "decimal INTEGER, " +
                    "description VARCHAR2(3005), " +
                    "web_adr VARCHAR2(3005), " +
                    "ico_start VARCHAR2(35), " +
                    "ico_end VARCHAR2(35), " +
                    "list_bool INTEGER, " +
                    "kyc_bool INTEGER, " +
                    "encash_bool INTEGER, " +
                    "memo VARCHAR2(4505), " +
                    "register_date VARCHAR2(65), " +
                    "change_date VARCHAR2(65), " +
                    "anticipate_date VARCHAR2(35), " +
                    "custom_image VARCHAR2(50));";
            db.execSQL(sql);

            sql = "SELECT amount, recent_price, belonging, way_to_get, exchange, " +
                    "custom_adr, custom_key, list_bool, encash_bool FROM " + tableName + ";";
            resultset = db.rawQuery(sql, null);

            DBcount = resultset.getCount();

            if (DBcount != 0) {
                amounts = new String[DBcount];
                recent_prices = new String[DBcount];
                belongings = new int[DBcount];
                way_to_gets = new int[DBcount];
                exchanges = new String[DBcount];
                custom_adrs = new String[DBcount];
                custom_keys = new String[DBcount];
                list_bools = new int[DBcount];
                encash_bools = new int[DBcount];

                for (int i=0; i<DBcount; i++) {
                    resultset.moveToNext();

                    String temp_amount = resultset.getString(0);
                    String temp_recent_price = resultset.getString(1);
                    int temp_belonging = resultset.getInt(2);
                    int temp_way_to_get = resultset.getInt(3);
                    String temp_exchange = resultset.getString(4);
                    String temp_custom_adr = resultset.getString(5);
                    String temp_custom_key = resultset.getString(6);
                    int temp_list_bool = resultset.getInt(7);
                    int temp_encash_bool = resultset.getInt(8);

                    amounts[i] = temp_amount;
                    recent_prices[i] = temp_recent_price;
                    belongings[i] = temp_belonging;
                    way_to_gets[i] = temp_way_to_get;
                    exchanges[i] = temp_exchange;
                    custom_adrs[i] = temp_custom_adr;
                    custom_keys[i] = temp_custom_key;
                    list_bools[i] = temp_list_bool;
                    encash_bools[i] = temp_encash_bool;

                    if (belongings[i] == 0) {
                        b_count++;

                        BigDecimal addi = new BigDecimal(be_crypt);
                        BigDecimal a_comp = new BigDecimal(amounts[i]);
                        be_crypt = String.valueOf(addi.add(a_comp));

                        String temp_sum_asset;

                        BigDecimal mul = new BigDecimal(amounts[i]);
                        BigDecimal m_comp = new BigDecimal(recent_prices[i]);
                        temp_sum_asset = String.valueOf(mul.multiply(m_comp));

                        BigDecimal addi2 = new BigDecimal(sum_asset);
                        BigDecimal a2_comp = new BigDecimal(temp_sum_asset);
                        sum_asset = String.valueOf(addi2.add(a2_comp));

                        String temp_cash_crypt;

                        if (encash_bools[i] == 1) {
                            BigDecimal mul2 = new BigDecimal(amounts[i]);
                            BigDecimal m2_comp = new BigDecimal(recent_prices[i]);
                            temp_cash_crypt = String.valueOf(mul2.multiply(m2_comp));

                            BigDecimal addi3 = new BigDecimal(cash_crypt);
                            BigDecimal a3_comp = new BigDecimal(temp_cash_crypt);
                            cash_crypt = String.valueOf(addi3.add(a3_comp));
                        }

                        if (way_to_gets[i] == 0)
                            ib_count++;
                        else if (way_to_gets[i] == 1)
                            ab_count++;
                        else if (way_to_gets[i] == 2)
                            inb_count++;
                    } else if (belongings[i] == 1) {
                        BigDecimal addi4 = new BigDecimal(no_dist_crypt);
                        BigDecimal a4_comp = new BigDecimal(amounts[i]);
                        no_dist_crypt = String.valueOf(addi4.add(a4_comp));
                    }

                    if (way_to_gets[i] == 0)
                        i_count++;
                    else if (way_to_gets[i] == 1)
                        a_count++;
                    else if (way_to_gets[i] == 2)
                        in_count++;
                }

                ratio = (b_count * 100) / DBcount;
                String temp_ratio = Integer.toString(ratio);

                if (ratio > 0)
                    p_ratio.setText(temp_ratio + " %");

                if (a_count > 0) {
                   String temp_a_count = Integer.toString(ab_count);
                   air_c.setText(temp_a_count + "  /  " + a_count);
                }

                if (i_count > 0) {
                    String temp_i_count = Integer.toString(ib_count);
                    ico_c.setText(temp_i_count + "  /  " + i_count);
                }

                if (in_count > 0) {
                    String temp_in_count = Integer.toString(inb_count);
                    invest_c.setText(temp_in_count + "  /  " + in_count);
                }

                Double temp_sum_ass = Double.parseDouble(sum_asset);
                sum_ass.setText(cur_form.format(temp_sum_ass));

                Double temp_be_crypto = Double.parseDouble(be_crypt);
                be_crypto.setText(amount_form.format(temp_be_crypto));

                Double temp_nd_crypto = Double.parseDouble(no_dist_crypt);
                nd_crypto.setText(amount_form.format(temp_nd_crypto));

                Double temp_cash_crypto = Double.parseDouble(cash_crypt);
                cash.setText(cur_form.format(temp_cash_crypto));
            }

            /////////////////////////////////////////////////////////////////

            try {
                new JsonTask().execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (usd_ex.equals("0") == false && jpy_ex.equals("0") == false && cny_ex.equals("0") == false) {
                String temp_usd_ex = usd_ex.replace(",", "");
                String temp_jpy_ex = jpy_ex.replace(",", "");
                String temp_cny_ex = cny_ex.replace(",", "");

                sql = "SELECT ex_usd, ex_jpy, ex_cny, ex_date_comp FROM " + tableName2 + " WHERE info='CB';";
                resultset = db.rawQuery(sql, null);

                resultset.moveToNext();

                String ex_usd_c = resultset.getString(0);
                String ex_jpy_c = resultset.getString(1);
                String ex_cny_c = resultset.getString(2);
                String ex_date_c = resultset.getString(3);

                String ex[] = ex_date_c.split("/");

                temp_usd = ex[0];
                temp_jpy = ex[1];
                temp_cny = ex[2];
                temp_date = ex[3];

                String temp_today = String.valueOf(today.toCharArray()[11]) + String.valueOf(today.toCharArray()[12]);

                int temp_today_c = Integer.parseInt(temp_today);

                if ((temp_date.equals(rtc) == false) && (temp_today_c >= 11 && temp_today_c <= 23)) {
                    String ex_comp = ex_usd_c + "/" + ex_jpy_c + "/" + ex_cny_c + "/" + rtc;

                    sql = "UPDATE " + tableName2 + " SET ex_date_comp='" + ex_comp + "' WHERE info='CB';";
                    db.execSQL(sql);

                    sql = "UPDATE " + tableName2 + " SET ex_usd='" + temp_usd_ex + "' WHERE info='CB';";
                    db.execSQL(sql);

                    sql = "UPDATE " + tableName2 + " SET ex_jpy='" + temp_jpy_ex + "' WHERE info='CB';";
                    db.execSQL(sql);

                    sql = "UPDATE " + tableName2 + " SET ex_cny='" + temp_cny_ex + "' WHERE info='CB';";
                    db.execSQL(sql);

                    sql = "UPDATE " + tableName2 + " SET ex_date='" + today + "' WHERE info='CB';";
                    db.execSQL(sql);
                }

                /////////////////////////////////////////////////////////////////

                usd_price_y = Double.parseDouble(temp_usd);
                jpy_price_y = Double.parseDouble(temp_jpy);
                cny_price_y = Double.parseDouble(temp_cny);

                usd_price = Double.parseDouble(temp_usd_ex);
                jpy_price = Double.parseDouble(temp_jpy_ex);
                cny_price = Double.parseDouble(temp_cny_ex);

                if (Double.compare(usd_price_y, usd_price) == 0) {
                    usd_r.setText(cur_form.format(usd_price));
                    usd_r.setTextColor(Color.parseColor("#FFFFFF"));
                } else if (Double.compare(usd_price_y, usd_price) > 0) {
                    usd_r.setText("▼  " + cur_form.format(usd_price));
                    usd_r.setTextColor(Color.parseColor("#8FDBF2"));
                } else if (Double.compare(usd_price_y, usd_price) < 0) {
                    usd_r.setText("▲  " + cur_form.format(usd_price));
                    usd_r.setTextColor(Color.parseColor("#F47D8C"));
                }

                if (Double.compare(jpy_price_y, jpy_price) == 0) {
                    jpy_r.setText(cur_form.format(jpy_price));
                    jpy_r.setTextColor(Color.parseColor("#FFFFFF"));
                } else if (Double.compare(jpy_price_y, jpy_price) > 0) {
                    jpy_r.setText("▼  " + cur_form.format(jpy_price));
                    jpy_r.setTextColor(Color.parseColor("#8FDBF2"));
                } else if (Double.compare(jpy_price_y, jpy_price) < 0) {
                    jpy_r.setText("▲  " + cur_form.format(jpy_price));
                    jpy_r.setTextColor(Color.parseColor("#F47D8C"));
                }

                if (Double.compare(cny_price_y, cny_price) == 0) {
                    cny_r.setText(cur_form.format(cny_price));
                    cny_r.setTextColor(Color.parseColor("#FFFFFF"));
                } else if (Double.compare(cny_price_y, cny_price) > 0) {
                    cny_r.setText("▼  " + cur_form.format(cny_price));
                    cny_r.setTextColor(Color.parseColor("#8FDBF2"));
                } else if (Double.compare(cny_price_y, cny_price) < 0) {
                    cny_r.setText("▲  " + cur_form.format(cny_price));
                    cny_r.setTextColor(Color.parseColor("#F47D8C"));
                }
            } else {
                sql = "SELECT ex_usd, ex_jpy, ex_cny, ex_date FROM " + tableName2 + " WHERE info='CB';";
                resultset = db.rawQuery(sql, null);

                resultset.moveToNext();

                String t_usd_ex_y = resultset.getString(0);
                String t_jpy_ex_y = resultset.getString(1);
                String t_cny_ex_y = resultset.getString(2);
                String temp_ex_date = resultset.getString(3);

                if (temp_ex_date.equals("0000.00.00 00:00:00") == false) {
                    usd_price_y = Double.parseDouble(t_usd_ex_y);
                    jpy_price_y = Double.parseDouble(t_jpy_ex_y);
                    cny_price_y = Double.parseDouble(t_cny_ex_y);

                    usd_r.setText(cur_form.format(usd_price_y));
                    jpy_r.setText(cur_form.format(jpy_price_y));
                    cny_r.setText(cur_form.format(cny_price_y));

                    ex_info.setText(temp_ex_date + " 최종 갱신");
                }
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void MainClickPack(View view) {
        switch (view.getId()) {
            case R.id.home_refresh:
                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
            case R.id.main_to_balance:
                intent = new Intent(MainActivity.this, BalanceActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
                break;
            case R.id.add_fast:
                m_plus.setImageResource(R.drawable.main_plus_icon_crossed);
                p_main_72.setVisibility(View.INVISIBLE);
                add_d.setVisibility(View.INVISIBLE);
                add_f.setText("추가 등록");
                menu_check = 2;

                add_fast_name_check = 0;
                belong = 0;
                way_tg = 0;
                sum_count = 0;
                biggest = "0";
                standard_date = "0000.00.00 00:00:00";

                /////////////////////////////////////////////////////////////////

                final Dialog dialog = new Dialog(MainActivity.this, R.style.SlideDialog);
                dialog.setContentView(R.layout.dialog_add_fast);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.SlideDialog;
                dialog.show();

                final EditText tc_name = (EditText)dialog.findViewById(R.id.crypto_name);
                final EditText tc_s_name = (EditText)dialog.findViewById(R.id.crypto_s_name);
                final TextView tc_name_check = (TextView)dialog.findViewById(R.id.crypto_name_check);
                final EditText tc_amount = (EditText)dialog.findViewById(R.id.crypto_amount);
                final EditText tc_first_price = (EditText)dialog.findViewById(R.id.crypto_first_price);
                final RadioButton tc_part_ico = (RadioButton)dialog.findViewById(R.id.part_ico);
                final RadioButton tc_part_airdrop = (RadioButton)dialog.findViewById(R.id.part_airdrop);
                final RadioButton tc_part_invest = (RadioButton)dialog.findViewById(R.id.part_invest);
                final CheckBox tc_check_belonging = (CheckBox)dialog.findViewById(R.id.check_belonging);
                TextView tc_add_register = (TextView)dialog.findViewById(R.id.add_fast_register);

                /////////////////////////////////////////////////////////////////

                tc_name_check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_tcn = tc_name.getText().toString();
                        String temp_tc_sn = tc_s_name.getText().toString();

                        if (add_fast_name_check == 3)
                            add_fast_name_check = 0;

                        db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

                        try {
                            sql = "SELECT amount FROM " + tableName + " WHERE name='" + temp_tcn + "' AND s_name='" + temp_tc_sn + "';";
                            resultset = db.rawQuery(sql, null);
                            DBcount = resultset.getCount();

                            if (DBcount > 0) {
                                add_fast_name_check = 2;
                                tc_name_check.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dialog_gradient_g));
                                Toast.makeText(getApplicationContext(), "중복된 내용이 존재하지만 등록이 가능합니다", Toast.LENGTH_SHORT).show();
                            } else {
                                if (temp_tcn.length() > 0 && temp_tc_sn.length() > 0) {
                                    add_fast_name_check = 1;
                                    tc_name_check.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.round_shape_lr_wg_i_p_gra));
                                    Toast.makeText(getApplicationContext(), "중복된 내용이 없습니다", Toast.LENGTH_SHORT).show();
                                } else {
                                    tc_name_check.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dialog_gradient_g));
                                    Toast.makeText(getApplicationContext(), "암호화폐명 또는 약어를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }

                            db.close();
                        } catch (Exception e) {
                            if (temp_tcn.length() > 0 && temp_tc_sn.length() > 0) {
                                add_fast_name_check = 1;
                                tc_name_check.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.round_shape_lr_wg_i_p_gra));
                                Toast.makeText(getApplicationContext(), "중복된 내용이 없습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                tc_name_check.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dialog_gradient_g));
                                Toast.makeText(getApplicationContext(), "암호화폐명 또는 약어를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }

                            e.printStackTrace();
                        }
                    }
                });

                /////////////////////////////////////////////////////////////////

                tc_add_register.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_tcn = tc_name.getText().toString();
                        String temp_tc_sn = tc_s_name.getText().toString();
                        String temp_tca = tc_amount.getText().toString();
                        String temp_tc_fp = tc_first_price.getText().toString();

                        int temp_tca_length = temp_tca.length();
                        int temp_tc_fp_length = temp_tc_fp.length();

                        if (temp_tcn.length() == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐명을 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tcn.toCharArray()[0] == ' ')
                            Toast.makeText(getApplicationContext(), "암호화폐명의 맨 앞 글자에 공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_sn.length() == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐명 약어를 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_sn.toCharArray()[0] == ' ')
                            Toast.makeText(getApplicationContext(), "암호화폐명 약어의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tca.length() == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐 개수를 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tca.equals("0") == true)
                            Toast.makeText(getApplicationContext(), "암호화폐 개수를 1개 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tca.toCharArray()[0] == '.')
                            Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tca.toCharArray()[temp_tca_length-1] == '.')
                            Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tca_length > 1) && (temp_tca.toCharArray()[0] == '0') && (temp_tca.toCharArray()[1] != '.'))
                            Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_fp.length() == 0)
                            Toast.makeText(getApplicationContext(), "최초 가치를 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_fp.toCharArray()[0] == '.')
                            Toast.makeText(getApplicationContext(), "최초 가치의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_fp.toCharArray()[temp_tc_fp_length-1] == '.')
                            Toast.makeText(getApplicationContext(), "최초 가치의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tc_fp_length > 1) && (temp_tc_fp.toCharArray()[0] == '0') && (temp_tc_fp.toCharArray()[1] != '.'))
                            Toast.makeText(getApplicationContext(), "최초 가치의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((tc_part_ico.isChecked() == false) && (tc_part_airdrop.isChecked() == false) && (tc_part_invest.isChecked() == false))
                            Toast.makeText(getApplicationContext(), "참여 방식을 선택해주세요", Toast.LENGTH_SHORT).show();
                        else if (add_fast_name_check == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐명 중복 확인을 해주세요", Toast.LENGTH_SHORT).show();
                        else if (add_fast_name_check == 2) {
                            add_fast_name_check = 3;
                            Toast.makeText(getApplicationContext(), "동일한 이름의 암호화폐명이 있습니다\n그래도 계속하시려면 등록을 누르시고\n새로운 내용으로 등록하려는 경우\n다시 중복 검사를 눌러주세요", Toast.LENGTH_LONG).show();
                        } else {
                            if (tc_check_belonging.isChecked() == true)
                                belong = 0;
                            else
                                belong = 1;

                            if (tc_part_ico.isChecked() == true)
                                way_tg = 0;
                            else if (tc_part_airdrop.isChecked() == true)
                                way_tg = 1;
                            else if (tc_part_invest.isChecked() == true)
                                way_tg = 2;

                            db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

                            try {
                                sql = "INSERT INTO " + tableName + " (" +
                                        "name, " +
                                        "s_name, " +
                                        "amount, " +
                                        "first_price, " +
                                        "recent_price, " +
                                        "belonging, " +
                                        "belonging_loc, " +
                                        "way_to_get, " +
                                        "platform, " +
                                        "exchange, " +
                                        "custom_adr, " +
                                        "custom_key, " +
                                        "total_supply, " +
                                        "contract, " +
                                        "decimal, " +
                                        "description, " +
                                        "web_adr, " +
                                        "ico_start, " +
                                        "ico_end, " +
                                        "list_bool, " +
                                        "kyc_bool, " +
                                        "encash_bool, " +
                                        "memo, " +
                                        "register_date, " +
                                        "change_date, " +
                                        "anticipate_date, " +
                                        "custom_image) VALUES ('" +
                                        temp_tcn + "', '" +
                                        temp_tc_sn + "', '" +
                                        temp_tca + "', '" +
                                        temp_tc_fp + "', '" +
                                        temp_tc_fp + "', " +
                                        belong + ", " +
                                        "'-', " +
                                        way_tg + ", " +
                                        "'-', " +
                                        "'-', " +
                                        "'-', " +
                                        "'-', " +
                                        "'0', " +
                                        "'-', " +
                                        "-1, " +
                                        "'-', " +
                                        "'-', " +
                                        "'0000.00.00', " +
                                        "'0000.00.00', " +
                                        "0, " +
                                        "0, " +
                                        "0, " +
                                        "'-', '" +
                                        today + "', '" +
                                        today + "', " +
                                        "'0000.00.00', " +
                                        "'-');";
                                db.execSQL(sql);

                                sql = "SELECT add_count, biggest, first_add FROM " + tableName2 + " WHERE info='CB';";
                                resultset = db.rawQuery(sql, null);

                                resultset.moveToNext();

                                int temp_add_count = resultset.getInt(0);
                                String temp_biggest = resultset.getString(1);
                                String temp_first_add = resultset.getString(2);

                                sum_count = temp_add_count;
                                sum_count++;

                                biggest = temp_biggest;

                                BigDecimal mul = new BigDecimal(temp_tc_fp);
                                BigDecimal m_comp = new BigDecimal(temp_tca);
                                String temp_mul = String.valueOf(mul.multiply(m_comp));

                                BigDecimal big = new BigDecimal(biggest);
                                BigDecimal b_comp = new BigDecimal(temp_mul);

                                if (big.compareTo(b_comp) == -1)
                                    biggest = temp_mul;

                                if (temp_first_add.equals("0000.00.00 00:00:00") == false)
                                    standard_date = temp_first_add;
                                else
                                    standard_date = today;

                                sql = "UPDATE " + tableName2 + " SET add_count=" + sum_count + ", " +
                                        "biggest='" + biggest + "', " +
                                        "first_add='" + standard_date + "', " +
                                        "recent_add='" + today + "' WHERE info='CB';";
                                db.execSQL(sql);

                                Toast.makeText(getApplicationContext(), "등록이 완료되었습니다", Toast.LENGTH_SHORT).show();

                                db.close();
                                dataSet();
                                dialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                break;
            case R.id.add_detail:
                m_plus.setImageResource(R.drawable.main_plus_icon_crossed);
                p_main_71.setVisibility(View.INVISIBLE);
                add_f.setVisibility(View.INVISIBLE);
                add_d.setText("추가 등록");
                menu_check = 2;

                add_detail_name_check = 0;
                belong = 0;
                way_tg = 0;
                sum_count = 0;
                biggest = "0";
                standard_date = "0000.00.00 00:00";

                /////////////////////////////////////////////////////////////////

                final Dialog dialog2 = new Dialog(MainActivity.this, R.style.SlideDialog);
                dialog2.setContentView(R.layout.dialog_add_detail);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.getWindow().getAttributes().windowAnimations = R.style.SlideDialog;
                dialog2.show();

                final EditText tc_name2 = (EditText)dialog2.findViewById(R.id.crypto_name2);
                final EditText tc_s_name2 = (EditText)dialog2.findViewById(R.id.crypto_s_name2);
                final TextView tc_name_check2 = (TextView)dialog2.findViewById(R.id.crypto_name_check2);
                final EditText tc_amount2 = (EditText)dialog2.findViewById(R.id.crypto_amount2);
                final EditText tc_first_price2 = (EditText)dialog2.findViewById(R.id.crypto_first_price2);
                final RadioButton tc_part_ico2 = (RadioButton)dialog2.findViewById(R.id.part_ico2);
                final RadioButton tc_part_airdrop2 = (RadioButton)dialog2.findViewById(R.id.part_airdrop2);
                final RadioButton tc_part_invest2 = (RadioButton)dialog2.findViewById(R.id.part_invest2);
                final EditText tc_platform = (EditText)dialog2.findViewById(R.id.crypto_platform);
                final EditText tc_total_supply = (EditText)dialog2.findViewById(R.id.crypto_total_supply);
                final EditText tc_contract = (EditText)dialog2.findViewById(R.id.crypto_contract);
                final EditText tc_decimal = (EditText)dialog2.findViewById(R.id.crypto_decimal);
                final CheckBox tc_check_belonging2 = (CheckBox)dialog2.findViewById(R.id.check_belonging2);
                final EditText tc_belonging_loc = (EditText)dialog2.findViewById(R.id.crypto_belonging_loc);
                TextView tc_add_register2 = (TextView)dialog2.findViewById(R.id.add_detail_register);

                /////////////////////////////////////////////////////////////////

                tc_name_check2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_tcn2 = tc_name2.getText().toString();
                        String temp_tc_sn2 = tc_s_name2.getText().toString();

                        if (add_detail_name_check == 3)
                            add_detail_name_check = 0;

                        db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

                        try {
                            sql = "SELECT amount FROM " + tableName + " WHERE name='" + temp_tcn2 + "' AND s_name='" + temp_tc_sn2 + "';";
                            resultset = db.rawQuery(sql, null);
                            DBcount = resultset.getCount();

                            if (DBcount > 0) {
                                add_detail_name_check = 2;
                                tc_name_check2.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dialog_gradient_g));
                                Toast.makeText(getApplicationContext(), "중복된 내용이 존재하지만 등록이 가능합니다", Toast.LENGTH_SHORT).show();
                            } else {
                                if (temp_tcn2.length() > 0 && temp_tc_sn2.length() > 0) {
                                    add_detail_name_check = 1;
                                    tc_name_check2.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.round_shape_lr_wg_i_b_gra));
                                    Toast.makeText(getApplicationContext(), "중복된 내용이 없습니다", Toast.LENGTH_SHORT).show();
                                } else {
                                    tc_name_check2.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dialog_gradient_g));
                                    Toast.makeText(getApplicationContext(), "암호화폐명 또는 약어를 입력해주세요", Toast.LENGTH_SHORT).show();
                                }
                            }

                            db.close();
                        } catch (Exception e) {
                            if (temp_tcn2.length() > 0 && temp_tc_sn2.length() > 0) {
                                add_detail_name_check = 1;
                                tc_name_check2.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.round_shape_lr_wg_i_b_gra));
                                Toast.makeText(getApplicationContext(), "중복된 내용이 없습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                tc_name_check2.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.dialog_gradient_g));
                                Toast.makeText(getApplicationContext(), "암호화폐명 또는 약어를 입력해주세요", Toast.LENGTH_SHORT).show();
                            }

                            e.printStackTrace();
                        }
                    }
                });

                /////////////////////////////////////////////////////////////////

                tc_add_register2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp_tcn2 = tc_name2.getText().toString();
                        String temp_tc_sn2 = tc_s_name2.getText().toString();
                        String temp_tca2 = tc_amount2.getText().toString();
                        String temp_tc_fp2 = tc_first_price2.getText().toString();
                        String temp_tc_p = tc_platform.getText().toString();
                        String temp_tc_ts = tc_total_supply.getText().toString();
                        String temp_tcc = tc_contract.getText().toString();
                        String temp_tcd = tc_decimal.getText().toString();
                        String temp_tc_bl = tc_belonging_loc.getText().toString();

                        int temp_tca2_length = temp_tca2.length();
                        int temp_tc_fp2_length = temp_tc_fp2.length();
                        int temp_tc_ts_length = temp_tc_ts.length();

                        String temp_platform = "-";
                        String temp_total_supply = "0";
                        String temp_contract = "-";
                        String temp_belonging_loc = "-";

                        if (temp_tcn2.length() == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐명을 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tcn2.toCharArray()[0] == ' ')
                            Toast.makeText(getApplicationContext(), "암호화폐명의 맨 앞 글자에 공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_sn2.length() == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐명 약어를 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_sn2.toCharArray()[0] == ' ')
                            Toast.makeText(getApplicationContext(), "암호화폐명 약어의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tca2.length() == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐 개수를 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tca2.equals("0") == true)
                            Toast.makeText(getApplicationContext(), "암호화폐 개수를 1개 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tca2.toCharArray()[0] == '.')
                            Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tca2.toCharArray()[temp_tca2_length-1] == '.')
                            Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tca2_length > 1) && (temp_tca2.toCharArray()[0] == '0') && (temp_tca2.toCharArray()[1] != '.'))
                            Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_fp2.length() == 0)
                            Toast.makeText(getApplicationContext(), "최초 가치를 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_fp2.toCharArray()[0] == '.')
                            Toast.makeText(getApplicationContext(), "최초 가치의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if (temp_tc_fp2.toCharArray()[temp_tc_fp2_length-1] == '.')
                            Toast.makeText(getApplicationContext(), "최초 가치의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tc_fp2_length > 1) && (temp_tc_fp2.toCharArray()[0] == '0') && (temp_tc_fp2.toCharArray()[1] != '.'))
                            Toast.makeText(getApplicationContext(), "최초 가치의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((tc_part_ico2.isChecked() == false) && (tc_part_airdrop2.isChecked() == false) && (tc_part_invest2.isChecked() == false))
                            Toast.makeText(getApplicationContext(), "참여 방식을 선택해주세요", Toast.LENGTH_SHORT).show();
                        else if (add_detail_name_check == 0)
                            Toast.makeText(getApplicationContext(), "암호화폐명 중복 확인을 해주세요", Toast.LENGTH_SHORT).show();
                        else if (add_detail_name_check == 2) {
                            add_detail_name_check = 3;
                            Toast.makeText(getApplicationContext(), "동일한 이름의 암호화폐명이 있습니다\n그래도 계속하시려면 등록을 누르시고\n새로운 내용으로 등록하려는 경우\n다시 중복 검사를 눌러주세요", Toast.LENGTH_LONG).show();
                        } else if ((temp_tc_p.length() > 0) && (temp_tc_p.toCharArray()[0] == ' '))
                            Toast.makeText(getApplicationContext(), "기반 플랫폼의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tc_ts.length() > 0) && (temp_tc_ts.equals("0") == true))
                            Toast.makeText(getApplicationContext(), "총 발행량을 1개 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                        else if ((temp_tc_ts.length() > 0) && (temp_tc_ts.toCharArray()[0] == '.'))
                            Toast.makeText(getApplicationContext(), "총 발행량의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tc_ts.length() > 1) && (temp_tc_ts.toCharArray()[temp_tc_ts_length-1] == '.'))
                            Toast.makeText(getApplicationContext(), "총 발행량의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tc_ts.length() > 1) && (temp_tc_ts.toCharArray()[0] == '0') && (temp_tc_ts.toCharArray()[1] != '.'))
                            Toast.makeText(getApplicationContext(), "총 발행량의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tcc.length() > 0) && (temp_tcc.toCharArray()[0] == ' '))
                            Toast.makeText(getApplicationContext(), "컨트랙트 주소의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else if ((temp_tc_bl.length() > 0) && (temp_tc_bl.toCharArray()[0] == ' '))
                            Toast.makeText(getApplicationContext(), "보유 위치의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                        else {
                            if (tc_check_belonging2.isChecked() == true)
                                belong = 0;
                            else
                                belong = 1;

                            if (tc_part_ico2.isChecked() == true)
                                way_tg = 0;
                            else if (tc_part_airdrop2.isChecked() == true)
                                way_tg = 1;
                            else if (tc_part_invest2.isChecked() == true)
                                way_tg = 2;

                            if (temp_tc_p.length() > 0)
                                temp_platform = temp_tc_p;

                            if (temp_tc_ts.length() > 0)
                                temp_total_supply = temp_tc_ts;

                            if (temp_tcc.length() > 0)
                                temp_contract = temp_tcc;

                            int temp_decimal = -1;

                            if (temp_tcd.length() > 0)
                                temp_decimal = Integer.parseInt(temp_tcd);

                            if (temp_tc_bl.length() > 0)
                                temp_belonging_loc = temp_tc_bl;

                            db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

                            try {
                                sql = "INSERT INTO " + tableName + " (" +
                                        "name, " +
                                        "s_name, " +
                                        "amount, " +
                                        "first_price, " +
                                        "recent_price, " +
                                        "belonging, " +
                                        "belonging_loc, " +
                                        "way_to_get, " +
                                        "platform, " +
                                        "exchange, " +
                                        "custom_adr, " +
                                        "custom_key, " +
                                        "total_supply, " +
                                        "contract, " +
                                        "decimal, " +
                                        "description, " +
                                        "web_adr, " +
                                        "ico_start, " +
                                        "ico_end, " +
                                        "list_bool, " +
                                        "kyc_bool, " +
                                        "encash_bool, " +
                                        "memo, " +
                                        "register_date, " +
                                        "change_date, " +
                                        "anticipate_date, " +
                                        "custom_image) VALUES ('" +
                                        temp_tcn2 + "', '" +
                                        temp_tc_sn2 + "', '" +
                                        temp_tca2 + "', '" +
                                        temp_tc_fp2 + "', '" +
                                        temp_tc_fp2 + "', " +
                                        belong + ", '" +
                                        temp_belonging_loc + "', " +
                                        way_tg + ", '" +
                                        temp_platform + "', " +
                                        "'-', " +
                                        "'-', " +
                                        "'-', '" +
                                        temp_total_supply + "', '" +
                                        temp_contract + "', " +
                                        temp_decimal + ", " +
                                        "'-', " +
                                        "'-', " +
                                        "'0000.00.00', " +
                                        "'0000.00.00', " +
                                        "0, " +
                                        "0, " +
                                        "0, " +
                                        "'-', '" +
                                        today + "', '" +
                                        today + "', " +
                                        "'0000.00.00', " +
                                        "'-');";
                                db.execSQL(sql);

                                sql = "SELECT add_count, biggest, first_add FROM " + tableName2 + " WHERE info='CB';";
                                resultset = db.rawQuery(sql, null);

                                resultset.moveToNext();

                                int temp_add_count2 = resultset.getInt(0);
                                String temp_biggest2 = resultset.getString(1);
                                String temp_first_add2 = resultset.getString(2);

                                sum_count = temp_add_count2;
                                sum_count++;

                                biggest = temp_biggest2;

                                BigDecimal mul = new BigDecimal(temp_tc_fp2);
                                BigDecimal m_comp = new BigDecimal(temp_tca2);
                                String temp_mul = String.valueOf(mul.multiply(m_comp));

                                BigDecimal big = new BigDecimal(biggest);
                                BigDecimal b_comp = new BigDecimal(temp_mul);

                                if (big.compareTo(b_comp) == -1)
                                    biggest = temp_mul;

                                if (temp_first_add2.equals("0000.00.00 00:00:00") == false)
                                    standard_date = temp_first_add2;
                                else
                                    standard_date = today;

                                sql = "UPDATE " + tableName2 + " SET add_count=" + sum_count + ", " +
                                        "biggest='" + biggest + "', " +
                                        "first_add='" + standard_date + "', " +
                                        "recent_add='" + today + "' WHERE info='CB';";
                                db.execSQL(sql);

                                Toast.makeText(getApplicationContext(), "등록이 완료되었습니다", Toast.LENGTH_SHORT).show();

                                db.close();
                                dataSet();
                                dialog2.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                break;
        }
    }

    public String getExchangeData() {
        StringBuffer sb = new StringBuffer();

        try {
            String url = getStringFromURL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + Auth_key + "&searchdate=" + rtc + "&data=AP01");

            JSONArray result = new JSONArray(url);
            int array_length = result.length();

            String exchange_country;
            String exchange_price;

            for (int i=0; i<array_length; i++) {
                JSONObject inObject = result.getJSONObject(i);

                exchange_country = inObject.getString("cur_unit");
                exchange_price = inObject.getString("deal_bas_r");

                if (exchange_country.equals("USD") == true) {
                    usd_ex = exchange_price;
                    sb.append("#");
                }
                else if (exchange_country.equals("JPY(100)") == true) {
                    jpy_ex = exchange_price;
                    sb.append("#");
                }
                else if (exchange_country.equals("CNH") == true) {
                    cny_ex = exchange_price;
                    sb.append("#");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public String getStringFromURL(String url) throws UnsupportedEncodingException {
        BufferedReader br = new BufferedReader(new InputStreamReader(getInputStreamFromUrl(url), "UTF-8"));

        StringBuffer sb = new StringBuffer();

        try {
            String line = null;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static InputStream getInputStreamFromUrl(String url) {
        InputStream is = null;

        try {
            HttpClient client = new DefaultHttpClient();
            HttpResponse response = client.execute(new HttpGet(url));
            is = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return is;
    }

    private class JsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String ... str) {
            return getExchangeData();
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("###") == true)
                ex_info.setText(today + " 갱신");
        }
    }
}