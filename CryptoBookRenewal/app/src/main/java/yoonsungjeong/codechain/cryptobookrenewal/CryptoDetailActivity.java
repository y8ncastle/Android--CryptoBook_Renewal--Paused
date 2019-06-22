package yoonsungjeong.codechain.cryptobookrenewal;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CryptoDetailActivity extends Activity {
    public static Context context;

    /////////////////////////////////////////////////////////////////

    SQLiteDatabase db;

    String DBname = "CryptoBook";
    String tableName = "Balance";
    String tableName2 = "Settings";
    String sql;

    Cursor resultset, resultset2;

    /////////////////////////////////////////////////////////////////

    ImageView hd_refresh, d_icon;

    TextView d_name, d_s_name, d_amount, d_first_price, d_recent_price;
    TextView d_price_info, d_belonging, d_belonging_loc;
    TextView d_anticipate_date, d_list_on, d_custom_key;
    TextView d_custom_adr, d_platform, d_total_supply, d_contract;
    TextView d_decimal, d_ico_start, d_ico_end, d_web_adr, d_description;
    TextView d_delist, d_kyc_need, d_encash, d_register_date, d_change_date, d_memo;
    TextView bd_detail_encash, bd_detail_modi, bd_detail_delete;

    /////////////////////////////////////////////////////////////////

    DecimalFormat amount_form = new DecimalFormat("#,##0.########");
    DecimalFormat cur_form = new DecimalFormat("#,##0.##");

    /////////////////////////////////////////////////////////////////

    String data_name, data_s_name, data_amount, data_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.crypto_detail);

        context = this;

        init();

        Intent intent = getIntent();
        String temp_data = intent.getStringExtra("data");

        String temp[] = temp_data.split("/");

        data_name = temp[0];
        data_s_name = temp[1];
        data_amount = temp[2];
        data_date = temp[3];

        dataSet(data_name, data_s_name, data_amount, data_date);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void init() {
        hd_refresh = (ImageView)findViewById(R.id.home_d_refresh);
        d_icon = (ImageView)findViewById(R.id.detail_icon);

        d_name = (TextView)findViewById(R.id.detail_name);
        d_s_name = (TextView)findViewById(R.id.detail_s_name);
        d_amount = (TextView)findViewById(R.id.detail_amount);
        d_first_price = (TextView)findViewById(R.id.detail_first_price);
        d_recent_price = (TextView)findViewById(R.id.detail_recent_price);
        d_price_info = (TextView)findViewById(R.id.detail_price_info);
        d_belonging = (TextView)findViewById(R.id.detail_belonging);
        d_belonging_loc = (TextView)findViewById(R.id.detail_belonging_loc);
        d_anticipate_date = (TextView)findViewById(R.id.detail_anticipate_date);
        d_list_on = (TextView)findViewById(R.id.detail_list_on);
        d_custom_key = (TextView)findViewById(R.id.detail_custom_key);
        d_custom_adr = (TextView)findViewById(R.id.detail_custom_adr);
        d_platform = (TextView)findViewById(R.id.detail_platform);
        d_total_supply = (TextView)findViewById(R.id.detail_total_supply);
        d_contract = (TextView)findViewById(R.id.detail_contract);
        d_decimal = (TextView)findViewById(R.id.detail_decimal);
        d_ico_start = (TextView)findViewById(R.id.detail_ico_start);
        d_ico_end = (TextView)findViewById(R.id.detail_ico_end);
        d_web_adr = (TextView)findViewById(R.id.detail_web_adr);
        d_description = (TextView)findViewById(R.id.detail_description);
        d_delist = (TextView)findViewById(R.id.detail_delist);
        d_kyc_need = (TextView)findViewById(R.id.detail_kyc_need);
        d_encash = (TextView)findViewById(R.id.detail_encash);
        d_register_date = (TextView)findViewById(R.id.detail_register_date);
        d_change_date = (TextView)findViewById(R.id.detail_change_date);
        d_memo = (TextView)findViewById(R.id.detail_memo);
        bd_detail_encash = (TextView)findViewById(R.id.button_detail_encash);
        bd_detail_modi = (TextView)findViewById(R.id.button_detail_modi);
        bd_detail_delete = (TextView)findViewById(R.id.button_detail_delete);
    }

    public void dataSet(String name, String s_name, String amount, String date) {
        db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

        try {
            sql = "SELECT first_price, recent_price, belonging, " +
                    "belonging_loc, way_to_get, platform, exchange, custom_adr, custom_key, " +
                    "total_supply, contract, decimal, description, web_adr, ico_start, ico_end, " +
                    "list_bool, kyc_bool, encash_bool, memo, change_date, " +
                    "anticipate_date, custom_image FROM " + tableName + " WHERE name='" + name + "' AND " +
                    "s_name='" + s_name + "' AND amount='" + amount + "' AND register_date='" + date + "';";
            resultset = db.rawQuery(sql, null);

            resultset.moveToNext();

            String temp_first_price = resultset.getString(0);
            final String temp_recent_price = resultset.getString(1);
            int temp_belonging = resultset.getInt(2);
            String temp_belonging_loc = resultset.getString(3);
            int temp_way_to_get = resultset.getInt(4);
            String temp_platform = resultset.getString(5);
            String temp_exchange = resultset.getString(6);
            String temp_custom_adr = resultset.getString(7);
            String temp_custom_key = resultset.getString(8);
            String temp_total_supply = resultset.getString(9);
            String temp_contract = resultset.getString(10);
            int temp_decimal = resultset.getInt(11);
            String temp_description = resultset.getString(12);
            String temp_web_adr = resultset.getString(13);
            String temp_ico_start = resultset.getString(14);
            String temp_ico_end = resultset.getString(15);
            int temp_list_bool = resultset.getInt(16);
            int temp_kyc_bool = resultset.getInt(17);
            int temp_encash_bool = resultset.getInt(18);
            String temp_memo = resultset.getString(19);
            String temp_change_date = resultset.getString(20);
            String temp_anticipate_date = resultset.getString(21);
            String temp_custom_image = resultset.getString(22);

            d_name.setText(data_name);
            d_s_name.setText(data_s_name);

            String temp_s_name = "-";

            try {
                if (temp_custom_image.equals("-") == true)
                   temp_s_name = data_s_name;
                else
                    temp_s_name = temp_custom_image;

                File imgFile = new  File(Environment.getExternalStorageDirectory() + "/CryptoBook/", temp_s_name + ".png");
                File imgFile2 = new  File(Environment.getExternalStorageDirectory() + "/CryptoBook/", temp_s_name + ".jpg");

                if (imgFile.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    d_icon.setImageBitmap(myBitmap);
                } else if (imgFile2.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    d_icon.setImageBitmap(myBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            Double do_amount = Double.parseDouble(data_amount);
            d_amount.setText(amount_form.format(do_amount));

            if (temp_first_price.equals("0") == false) {
                Double do_first_price = Double.parseDouble(temp_first_price);
                d_first_price.setText(cur_form.format(do_first_price));
            }

            if (temp_recent_price.equals("0") == false) {
                Double do_recent_price = Double.parseDouble(temp_recent_price);
                d_recent_price.setText(cur_form.format(do_recent_price));
            }

            if (temp_belonging == 0) {
                if (temp_way_to_get == 0)
                    d_belonging.setText("보유 (ICO 참여)");
                else if (temp_way_to_get == 1)
                    d_belonging.setText("보유 (에어드랍, 바운티 참여)");
                else if (temp_way_to_get == 2)
                    d_belonging.setText("보유 (개인 투자)");
            } else if (temp_belonging == 1) {
                if (temp_way_to_get == 0)
                    d_belonging.setText("미보유 (ICO 참여)");
                else if (temp_way_to_get == 1)
                    d_belonging.setText("미보유 (에어드랍, 바운티 참여)");
                else if (temp_way_to_get == 2)
                    d_belonging.setText("미보유 (개인 투자)");
            }

            if (temp_belonging_loc.equals("-") == false)
                d_belonging_loc.setText(temp_belonging_loc);

            if (temp_anticipate_date.equals("0000.00.00") == false)
                d_anticipate_date.setText(temp_anticipate_date);

            if (temp_exchange.equals("-") == false)
                d_list_on.setText(temp_exchange);

            if (temp_custom_key.equals("-") == false)
                d_custom_key.setText(temp_custom_key);

            if (temp_custom_adr.equals("-") == false)
                d_custom_adr.setText(temp_custom_adr);

            if (temp_platform.equals("-") == false)
                d_platform.setText(temp_platform);

            if (temp_total_supply.equals("0") == false) {
                Double t_total_supply = Double.parseDouble(temp_total_supply);
                d_total_supply.setText(amount_form.format(t_total_supply));
            }

            if (temp_contract.equals("-") == false)
                d_contract.setText(temp_contract);

            if (temp_decimal != -1) {
                String t_decimal = Integer.toString(temp_decimal);
                d_decimal.setText(t_decimal);
            }

            if (temp_ico_start.equals("0000.00.00") == false)
                d_ico_start.setText(temp_ico_start);

            if (temp_ico_end.equals("0000.00.00") == false)
                d_ico_end.setText(temp_ico_end);

            if (temp_web_adr.equals("-") == false)
                d_web_adr.setText(temp_web_adr);

            if (temp_description.equals("-") == false)
                d_description.setText(temp_description);

            if (temp_list_bool == 0)
                d_delist.setText("비상장");
            else if (temp_list_bool == 1)
                d_delist.setText("상장");
            else if (temp_list_bool == 2)
                d_delist.setText("상장폐지");

            if (temp_kyc_bool == 0)
                d_kyc_need.setText("확인 필요");
            else if (temp_kyc_bool == 1)
                d_kyc_need.setText("필요");
            else if (temp_kyc_bool == 2)
                d_kyc_need.setText("차후 등록 필요");
            else if (temp_kyc_bool == 3)
                d_kyc_need.setText("등록 완료");

            if (temp_encash_bool == 0)
                d_encash.setText("불가능");
            else if (temp_encash_bool == 1)
                d_encash.setText("가능");

            d_register_date.setText(data_date);
            d_change_date.setText(temp_change_date);

            if (temp_memo.equals("-") == false)
                d_memo.setText(temp_memo);

            d_name.setSelected(true);
            d_s_name.setSelected(true);
            d_amount.setSelected(true);
            d_first_price.setSelected(true);
            d_recent_price.setSelected(true);
            d_total_supply.setSelected(true);

            bd_detail_encash.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(CryptoDetailActivity.this, R.style.SlideDialog);
                    dialog.setContentView(R.layout.dialog_crypto_encash);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.SlideDialog;
                    dialog.show();

                    TextView db_detail_encash = (TextView)dialog.findViewById(R.id.d_button_detail_encash);

                    db_detail_encash.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);


                            sql = "SELECT encashed FROM " + tableName2 + " WHERE info='CB';";
                            resultset2 = db.rawQuery(sql, null);

                            resultset2.moveToNext();

                            String temp_encahsed = resultset2.getString(0);

                            try {
                                if (temp_encahsed.equals("0") == true) {
                                    BigDecimal mul1 = new BigDecimal(data_amount);
                                    BigDecimal m_comp = new BigDecimal(temp_recent_price);
                                    BigDecimal result = mul1.multiply(m_comp);

                                    String t_result = String.valueOf(result);

                                    sql = "UPDATE " + tableName2 + " SET encashed='" + t_result + "' WHERE info='CB';";
                                    db.execSQL(sql);

                                    sql = "DELETE FROM " + tableName + " WHERE name='" + data_name + "' AND s_name='" + data_s_name + "' " +
                                            "AND amount='" + data_amount + "' AND register_date='" + data_date + "';";
                                    db.execSQL(sql);

                                    Toast.makeText(getApplicationContext(), "현금화 처리가 완료되었습니다", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                    finish();
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                    ((BalanceActivity)BalanceActivity.context).refresh();
                                } else {
                                    BigDecimal mul2 = new BigDecimal(data_amount);
                                    BigDecimal m_comp2 = new BigDecimal(temp_recent_price);
                                    BigDecimal result2 = mul2.multiply(m_comp2);

                                    String t_result2 = String.valueOf(result2);

                                    BigDecimal add1 = new BigDecimal(temp_encahsed);
                                    BigDecimal m_comp3 = new BigDecimal(t_result2);
                                    BigDecimal result3 = add1.add(m_comp3);

                                    String t_result3 = String.valueOf(result3);

                                    sql = "UPDATE " + tableName2 + " SET encashed='" + t_result3 + "' WHERE info='CB';";
                                    db.execSQL(sql);

                                    sql = "DELETE FROM " + tableName + " WHERE name='" + data_name + "' AND s_name='" + data_s_name + "' " +
                                            "AND amount='" + data_amount + "' AND register_date='" + data_date + "';";
                                    db.execSQL(sql);

                                    Toast.makeText(getApplicationContext(), "현금화 처리가 완료되었습니다", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                    finish();
                                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                                    ((BalanceActivity)BalanceActivity.context).refresh();
                                }

                                db.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });

            bd_detail_modi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CryptoDetailActivity.this, CryptoDetailModiActivity.class);
                    String temp = data_name + "/" + data_s_name + "/" + data_amount + "/" + data_date;
                    intent.putExtra("data", temp);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            bd_detail_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(CryptoDetailActivity.this, R.style.SlideDialog);
                    dialog.setContentView(R.layout.dialog_crypto_delete);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().getAttributes().windowAnimations = R.style.SlideDialog;
                    dialog.show();

                    TextView db_detail_del = (TextView)dialog.findViewById(R.id.d_button_detail_del);

                    db_detail_del.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            db = openOrCreateDatabase(DBname, MODE_PRIVATE, null);

                            sql = "DELETE FROM " + tableName + " WHERE name='" + data_name + "' AND s_name='" + data_s_name + "' " +
                                    "AND amount='" + data_amount + "' AND register_date='" + data_date + "';";
                            db.execSQL(sql);

                            Toast.makeText(getApplicationContext(), "삭제가 완료되었습니다", Toast.LENGTH_SHORT).show();

                            dialog.dismiss();
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            ((BalanceActivity)BalanceActivity.context).refresh();

                            db.close();
                        }
                    });
                }
            });

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DetailClickPack(View view) {
        switch (view.getId()) {
            case R.id.home_d_refresh:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}
