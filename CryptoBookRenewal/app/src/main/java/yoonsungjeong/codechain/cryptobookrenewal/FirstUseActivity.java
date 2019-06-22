package yoonsungjeong.codechain.cryptobookrenewal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import java.util.ArrayList;

public class FirstUseActivity extends Activity {
    LinearLayout b_acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.first_use_intro);

        b_acc = (LinearLayout)findViewById(R.id.b_access);
        b_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TedPermission.with(view.getContext())
                        .setPermissionListener(permissionlistener)
                        .setRationaleMessage("앱 내 필요한 기능에 대한 접근 권한이 필요합니다.")
                        .setDeniedMessage("앱 내에서 접근 허용 버튼을 다시 클릭하시거나\n[설정] - [어플리케이션] 에서 접근 권한을 설정할 수 있습니다.")
                        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)
                        .check();
            }
        });
    }

    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            Intent intent = new Intent(FirstUseActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
        }
    };
}