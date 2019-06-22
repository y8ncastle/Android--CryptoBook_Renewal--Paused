package yoonsungjeong.codechain.cryptobookrenewal;

import android.app.Activity;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CryptoDetailModiActivity extends Activity {
    SQLiteDatabase db;

    String DBname = "CryptoBook";
    String tableName = "Balance";
    String sql;

    Cursor resultset;

    /////////////////////////////////////////////////////////////////

    ImageView hdm_refresh, dm_icon;

    EditText dm_name, dm_s_name, dm_amount, dm_first_price, dm_recent_price;
    EditText dm_belonging_loc, dm_list_on, dm_custom_key;
    EditText dm_custom_adr, dm_platform, dm_total_supply, dm_contract;
    EditText dm_decimal, dm_web_adr, dm_description, dm_memo;

    TextView dm_belonging, dm_anticipate_date, dm_ico_start, dm_ico_end;
    TextView dm_delist, dm_kyc_need, dm_encash, dm_register_date, dm_change_date, bdm_modi;

    /////////////////////////////////////////////////////////////////

    int t_belonging, t_way_to_get, t_decimal, t_delist, t_kyc_need, t_encash;

    String tp_name, tp_s_name, tp_amount, tp_first_price, tp_recent_price;
    String tp_belonging, tp_way_to_get, tp_decimal, tp_delist, tp_kyc_need, tp_encash;
    String tp_belonging_loc, tp_anticipate_date, tp_list_on, tp_custom_key, tp_custom_adr;
    String tp_platform, tp_total_supply, tp_contract, tp_ico_start, tp_ico_end, tp_web_adr;
    String tp_register_date, tp_change_date, tp_memo, tp_description, tp_custom_image;

    /////////////////////////////////////////////////////////////////

    String data_name, data_s_name, data_amount, data_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.crypto_detail_modi);

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
        hdm_refresh = (ImageView)findViewById(R.id.home_dm_refresh);
        dm_icon = (ImageView)findViewById(R.id.detail_modi_icon);

        dm_name = (EditText)findViewById(R.id.detail_modi_name);
        dm_s_name = (EditText)findViewById(R.id.detail_modi_s_name);
        dm_amount = (EditText)findViewById(R.id.detail_modi_amount);
        dm_first_price = (EditText)findViewById(R.id.detail_modi_first_price);
        dm_recent_price = (EditText)findViewById(R.id.detail_modi_recent_price);
        dm_belonging_loc = (EditText)findViewById(R.id.detail_modi_belonging_loc);
        dm_list_on = (EditText)findViewById(R.id.detail_modi_list_on);
        dm_custom_key = (EditText)findViewById(R.id.detail_modi_custom_key);
        dm_custom_adr = (EditText)findViewById(R.id.detail_modi_custom_adr);
        dm_platform = (EditText)findViewById(R.id.detail_modi_platform);
        dm_total_supply = (EditText)findViewById(R.id.detail_modi_total_supply);
        dm_contract = (EditText)findViewById(R.id.detail_modi_contract);
        dm_decimal = (EditText)findViewById(R.id.detail_modi_decimal);
        dm_web_adr = (EditText)findViewById(R.id.detail_modi_web_adr);
        dm_description = (EditText)findViewById(R.id.detail_modi_description);
        dm_memo = (EditText)findViewById(R.id.detail_modi_memo);

        dm_belonging = (TextView)findViewById(R.id.detail_modi_belonging);
        dm_anticipate_date = (TextView)findViewById(R.id.detail_modi_anticipate_date);
        dm_ico_start = (TextView)findViewById(R.id.detail_modi_ico_start);
        dm_ico_end = (TextView)findViewById(R.id.detail_modi_ico_end);
        dm_delist = (TextView)findViewById(R.id.detail_modi_delist);
        dm_kyc_need = (TextView)findViewById(R.id.detail_modi_kyc_need);
        dm_encash = (TextView)findViewById(R.id.detail_modi_encash);
        dm_register_date = (TextView)findViewById(R.id.detail_modi_register_date);
        dm_change_date = (TextView)findViewById(R.id.detail_modi_change_date);
        bdm_modi = (TextView)findViewById(R.id.button_modi_detail_modi);
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

            dm_name.setText(data_name);
            dm_s_name.setText(data_s_name);

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
                    dm_icon.setImageBitmap(myBitmap);
                } else if (imgFile2.exists()) {
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    dm_icon.setImageBitmap(myBitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            dm_amount.setText(data_amount);

            if (temp_first_price.equals("0") == false)
                dm_first_price.setText(temp_first_price);

            if (temp_recent_price.equals("0") == false)
                dm_recent_price.setText(temp_recent_price);

            if (temp_belonging == 0) {
                if (temp_way_to_get == 0)
                    dm_belonging.setText("보유 (ICO 참여)");
                else if (temp_way_to_get == 1)
                    dm_belonging.setText("보유 (에어드랍, 바운티 참여)");
                else if (temp_way_to_get == 2)
                    dm_belonging.setText("보유 (개인 투자)");
            } else if (temp_belonging == 1) {
                if (temp_way_to_get == 0)
                    dm_belonging.setText("미보유 (ICO 참여)");
                else if (temp_way_to_get == 1)
                    dm_belonging.setText("미보유 (에어드랍, 바운티 참여)");
                else if (temp_way_to_get == 2)
                    dm_belonging.setText("미보유 (개인 투자)");
            }

            if (temp_belonging_loc.equals("-") == false)
                dm_belonging_loc.setText(temp_belonging_loc);

            if (temp_anticipate_date.equals("0000.00.00") == false)
                dm_anticipate_date.setText(temp_anticipate_date);

            if (temp_exchange.equals("-") == false)
                dm_list_on.setText(temp_exchange);

            if (temp_custom_key.equals("-") == false)
                dm_custom_key.setText(temp_custom_key);

            if (temp_custom_adr.equals("-") == false)
                dm_custom_adr.setText(temp_custom_adr);

            if (temp_platform.equals("-") == false)
                dm_platform.setText(temp_platform);

            if (temp_total_supply.equals("-") == false)
                dm_total_supply.setText(temp_total_supply);

            if (temp_contract.equals("-") == false)
                dm_contract.setText(temp_contract);

            if (temp_decimal != -1) {
                String t_decimal = Integer.toString(temp_decimal);
                dm_decimal.setText(t_decimal);
            }

            if (temp_ico_start.equals("0000.00.00") == false)
                dm_ico_start.setText(temp_ico_start);

            if (temp_ico_end.equals("0000.00.00") == false)
                dm_ico_end.setText(temp_ico_end);

            if (temp_web_adr.equals("-") == false)
                dm_web_adr.setText(temp_web_adr);

            if (temp_description.equals("-") == false)
                dm_description.setText(temp_description);

            if (temp_list_bool == 0)
                dm_delist.setText("비상장");
            else if (temp_list_bool == 1)
                dm_delist.setText("상장");
            else if (temp_list_bool == 2)
                dm_delist.setText("상장폐지");

            if (temp_kyc_bool == 0)
                dm_kyc_need.setText("확인 필요");
            else if (temp_kyc_bool == 1)
                dm_kyc_need.setText("필요");
            else if (temp_kyc_bool == 2)
                dm_kyc_need.setText("차후 등록 필요");
            else if (temp_kyc_bool == 3)
                dm_kyc_need.setText("등록 완료");

            if (temp_encash_bool == 0)
                dm_encash.setText("불가능");
            else if (temp_encash_bool == 1)
                dm_encash.setText("가능");

            dm_register_date.setText(data_date);
            dm_change_date.setText(temp_change_date);

            if (temp_memo.equals("-") == false)
                dm_memo.setText(temp_memo);

            bdm_modi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tp_name = dm_name.getText().toString();
                    tp_s_name = dm_s_name.getText().toString();
                    tp_amount = dm_amount.getText().toString();
                    tp_first_price = dm_first_price.getText().toString();
                    tp_recent_price = dm_recent_price.getText().toString();

                    int tp_amount_length = tp_amount.length();
                    int tp_first_price_length = tp_first_price.length();
                    int tp_recent_price_length = tp_recent_price.length();

                    tp_total_supply = dm_total_supply.getText().toString();
                    int tp_total_supply_length = tp_total_supply.length();

                    tp_belonging = dm_belonging.getText().toString();
                    t_belonging = Integer.parseInt(tp_belonging);

                    t_way_to_get = Integer.parseInt(tp_way_to_get);

                    tp_belonging_loc = dm_belonging_loc.getText().toString();
                    tp_platform = dm_platform.getText().toString();
                    tp_list_on = dm_list_on.getText().toString();
                    tp_custom_adr = dm_custom_adr.getText().toString();
                    tp_custom_key = dm_custom_key.getText().toString();
                    tp_contract = dm_contract.getText().toString();
                    tp_decimal = dm_decimal.getText().toString();
                    t_decimal = Integer.parseInt(tp_decimal);

                    tp_description = dm_description.getText().toString();
                    tp_web_adr = dm_web_adr.getText().toString();

                    t_kyc_need = Integer.parseInt(tp_kyc_need);
                    t_delist = Integer.parseInt(tp_delist);
                    t_encash = Integer.parseInt(tp_encash);

                    tp_memo = dm_memo.getText().toString();

                    if (tp_name.length() == 0)
                        Toast.makeText(getApplicationContext(), "암호화폐명을 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (tp_name.toCharArray()[0] == ' ')
                        Toast.makeText(getApplicationContext(), "암호화폐명의 맨 앞 글자에 공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if (tp_s_name.length() == 0)
                        Toast.makeText(getApplicationContext(), "암호화폐명 약어를 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (tp_s_name.toCharArray()[0] == ' ')
                        Toast.makeText(getApplicationContext(), "암호화폐명 약어의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if (tp_amount.length() == 0)
                        Toast.makeText(getApplicationContext(), "암호화폐 개수를 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (tp_amount.equals("0") == true)
                        Toast.makeText(getApplicationContext(), "암호화폐 개수를 1개 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (tp_amount.toCharArray()[0] == '.')
                        Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if (tp_amount.toCharArray()[tp_amount_length-1] == '.')
                        Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_amount.length() > 1) && (tp_amount.toCharArray()[0] == '0') && (tp_amount.toCharArray()[1] != '.'))
                        Toast.makeText(getApplicationContext(), "암호화폐 개수의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if (tp_first_price.length() == 0)
                        Toast.makeText(getApplicationContext(), "최초 가치를 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (tp_first_price.toCharArray()[0] == '.')
                        Toast.makeText(getApplicationContext(), "최초 가치의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if (tp_first_price.toCharArray()[tp_first_price_length-1] == '.')
                        Toast.makeText(getApplicationContext(), "최초 가치의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_first_price.length() > 1) && (tp_first_price.toCharArray()[0] == '0') && (tp_first_price.toCharArray()[1] != '.'))
                        Toast.makeText(getApplicationContext(), "최초 가치의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if (tp_recent_price.length() == 0)
                        Toast.makeText(getApplicationContext(), "최근 가치를 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if (tp_recent_price.toCharArray()[0] == '.')
                        Toast.makeText(getApplicationContext(), "최근 가치의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if (tp_recent_price.toCharArray()[tp_recent_price_length-1] == '.')
                        Toast.makeText(getApplicationContext(), "최근 가치의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_recent_price.length() > 1) && (tp_recent_price.toCharArray()[0] == '0') && (tp_recent_price.toCharArray()[1] != '.'))
                        Toast.makeText(getApplicationContext(), "최근 가치의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_platform.length() > 0) && (tp_platform.toCharArray()[0] == ' '))
                        Toast.makeText(getApplicationContext(), "기반 플랫폼의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_total_supply.length() > 0) && (tp_total_supply.equals("0") == true))
                        Toast.makeText(getApplicationContext(), "총 발행량을 1개 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if ((tp_total_supply.length() > 0) && (tp_total_supply.toCharArray()[0] == '.'))
                        Toast.makeText(getApplicationContext(), "총 발행량의 맨 앞 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_total_supply.length() > 1) && (tp_total_supply.toCharArray()[tp_total_supply_length-1] == '.'))
                        Toast.makeText(getApplicationContext(), "총 발행량의 맨 뒷 수치에\n소수 표시점(.)이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_total_supply.length() > 1) && (tp_total_supply.toCharArray()[0] == '0') && (tp_total_supply.toCharArray()[1] != '.'))
                        Toast.makeText(getApplicationContext(), "총 발행량의 맨 앞 수치에 0이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_contract.length() > 0) && (tp_contract.toCharArray()[0] == ' '))
                        Toast.makeText(getApplicationContext(), "컨트랙트 주소의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_belonging_loc.length() > 0) && (tp_belonging_loc.toCharArray()[0] == ' '))
                        Toast.makeText(getApplicationContext(), "보유 위치의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_list_on.length() > 0) && (tp_list_on.toCharArray()[0] == ' '))
                        Toast.makeText(getApplicationContext(), "상장 거래소 이름의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_custom_adr.length() > 0) && (tp_custom_adr.toCharArray()[0] == ' '))
                        Toast.makeText(getApplicationContext(), "커스텀 주소의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_custom_key.length() > 0) && (tp_custom_key.toCharArray()[0] == ' '))
                        Toast.makeText(getApplicationContext(), "커스텀 키의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else if ((tp_custom_image.length() > 0) && (tp_custom_image.toCharArray()[0] == ' '))
                        Toast.makeText(getApplicationContext(), "커스텀 이미지 약어의 맨 앞 글자에\n공백이 올 수 없습니다", Toast.LENGTH_SHORT).show();
                    else {
                        if (t_belonging == 0) {
                            if (t_way_to_get == 0)
                                tp_belonging = "보유 (ICO 참여)";
                            else if (t_way_to_get == 1)
                                tp_belonging = "보유 (에어드랍, 바운티 참여)";
                            else if (t_way_to_get == 2)
                                tp_belonging = "보유 (개인 투자)";
                        } else if (t_belonging == 1) {
                            if (t_way_to_get == 0)
                                tp_belonging = "미보유 (ICO 참여)";
                            else if (t_way_to_get == 1)
                                tp_belonging = "미보유 (에어드랍, 바운티 참여)";
                            else if (t_way_to_get == 2)
                                tp_belonging = "미보유 (개인 투자)";
                        }

                        if (t_delist == 0)
                            tp_delist = "비상장";
                        else if (t_delist == 1)
                            tp_delist = "상장";
                        else if (t_delist == 2)
                            tp_delist = "상장폐지";

                        if (t_kyc_need == 0)
                            tp_kyc_need = "확인 필요";
                        else if (t_kyc_need == 1)
                            tp_kyc_need = "필요";
                        else if (t_kyc_need == 2)
                            tp_kyc_need = "차후 등록 필요";
                        else if (t_kyc_need == 3)
                            tp_kyc_need = "등록 완료";

                        if (t_encash == 0)
                            tp_encash = "불가능";
                        else if (t_encash == 1)
                            tp_encash = "가능";

                        Toast.makeText(getApplicationContext(), "통과", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DetailModiClickPack(View view) {
        switch (view.getId()) {
            case R.id.home_dm_refresh:
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}