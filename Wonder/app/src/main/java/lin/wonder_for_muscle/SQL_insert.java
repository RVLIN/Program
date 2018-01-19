package lin.wonder_for_muscle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SQL_insert extends AppCompatActivity {

    private EditText latitude_edit;
    private EditText longitude_edit;
    private EditText accuracy_edit;
    private EditText note_edit;

    private SQL_PlaceDAO sql_placeDAO;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_insert);

        processViews();

        // 取得資料庫物件
        sql_placeDAO = new SQL_PlaceDAO(this);
    }

    private void processViews() {
        latitude_edit = (EditText) findViewById(R.id.latitude_edit);
        longitude_edit = (EditText) findViewById(R.id.longitude_edit);
        accuracy_edit = (EditText) findViewById(R.id.accuracy_edit);
        note_edit = (EditText) findViewById(R.id.note_edit);
    }

    public void clickOk(View view) {
        // 讀取使用者輸入的資料
        double latitudeValue = Double.parseDouble(latitude_edit.getText().toString());
        double longitudeValue = Double.parseDouble(longitude_edit.getText().toString());
        double accuracyValue = Double.parseDouble(accuracy_edit.getText().toString());
        String note = note_edit.getText().toString();

        // 建立準備新增資料的物件
        SQL_Place sql_place = new SQL_Place();

        // 把讀取的資料設定給物件
        sql_place.setLatitude(latitudeValue);
        sql_place.setLongitude(longitudeValue);
        sql_place.setAccuracy(accuracyValue);
        sql_place.setDatetime(System.currentTimeMillis());
        sql_place.setNote(note);

        // 新增
        sql_place = sql_placeDAO.insert(sql_place);

        // 顯示新增成功
        Toast.makeText(this, "Insert success!", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        // 設定回傳結果
        setResult(Activity.RESULT_OK, intent);
        // 結束
        finish();
    }

    public void clickCancel(View view) {
        // 結束
        finish();
    }

}