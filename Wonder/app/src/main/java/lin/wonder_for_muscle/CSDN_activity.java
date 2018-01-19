package lin.wonder_for_muscle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CSDN_activity extends AppCompatActivity {

    List<Person> personList;
    MyOpenHelper mOpenHelper;
    SQLiteDatabase db;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_csdn_activity);
        ListView lv = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<Person>();
        // 创建MyOpenHelper实例
        mOpenHelper = new MyOpenHelper(this);
        // 得到数据库
        db = mOpenHelper.getWritableDatabase();
        // 插入数据
        Insert();
        // 查询数据
        Query();
        // 创建MyAdapter实例
        myAdapter = new MyAdapter(this);
        // 向listview中添加Adapter
        lv.setAdapter(myAdapter);
    }

    // 创建MyAdapter继承BaseAdapter
    class MyAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;

        public MyAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return personList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 从personList取出Person
            Person p = personList.get(position);
            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.csdn_listitem, null);
                viewHolder.txt_name = (TextView) convertView
                        .findViewById(R.id.tv_name);
                viewHolder.txt_phone = (TextView) convertView
                        .findViewById(R.id.tv_phone);
                viewHolder.txt_salary = (TextView) convertView
                        .findViewById(R.id.tv_salary);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            //向TextView中插入数据
            viewHolder.txt_name.setText(p.getName());
            viewHolder.txt_phone.setText(p.getPhone());
            viewHolder.txt_salary.setText(p.getSalary());

            return convertView;
        }
    }

    static class ViewHolder {
       // private TextView txt_name;
        //private TextView txt_phone;
        //private TextView txt_salary;
        private TextView txt_name;
        private TextView txt_phone;
        private TextView txt_salary;
    }

    // 插入数据
    public void Insert() {
        Bundle bundle = getIntent().getExtras();
        ContentValues values = new ContentValues();
        if(bundle!=null) {
            String name = bundle.getString("name");
            values.put("name", name);
            db.insert("person", null, values);
        }


    }

    // 查询数据
    public void Query() {
        Cursor cursor = db.query("person", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String _id = cursor.getString(0);
            String name = cursor.getString(1);
            String salary = cursor.getString(2);
            String phone = cursor.getString(3);
            Person person = new Person(_id, name, phone, salary);
            personList.add(person);
        }
    }

}
