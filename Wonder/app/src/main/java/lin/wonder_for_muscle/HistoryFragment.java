package lin.wonder_for_muscle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    List<Person> personList;
    MyOpenHelper mOpenHelper;
    SQLiteDatabase db;
    MyAdapter myAdapter;


    public HistoryFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history, container, false);

        ListView lv = ( ListView ) view.findViewById(R.id.listView);

        personList = new ArrayList<>();
        // 创建MyOpenHelper实例
        mOpenHelper = new MyOpenHelper(getContext());
        // 得到数据库
        db = mOpenHelper.getReadableDatabase();

        // 插入数据
        Insert();
        // 查询数据
        Query();
        // 创建MyAdapter实例
         myAdapter = new MyAdapter(getContext(),R.layout.csdn_listitem,personList);
        // 向listview中添加Adapter
        lv.setAdapter(myAdapter);

        return view;

    }
    static class LayoutHandler{
        private TextView txt_name, txt_phone,txt_salary;
    }
    class MyAdapter extends ArrayAdapter {
        private Context context;
        private LayoutInflater inflater;
        List<Person>mlist;
        public MyAdapter(Context context, int resource,List<Person> list ) {
            super(context,resource);
            mlist=list;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return mlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
/*
            if(converview==null)
            {
                converview=inflater.inflate(R.layout.csdn_listitem,null);
            }
            final TextView txt_name=(TextView)converview.findViewById(R.id.tv_name);
            txt_name.setText(personList.get(position).getName());
            final TextView txt_phone=(TextView)converview.findViewById(R.id.tv_phone);
            txt_phone.setText(personList.get(position).getName());
            final TextView txt_salary=(TextView)converview.findViewById(R.id.tv_salary);
            txt_salary.setText(personList.get(position).getName());
*/
            View mview = convertView;
            LayoutHandler layoutHandler;
            if(mview==null)
            {
                LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                mview=inflater.inflate(R.layout.csdn_listitem,parent,false);
                layoutHandler=new LayoutHandler();
                layoutHandler.txt_name=mview.findViewById(R.id.tv_name);
                layoutHandler.txt_phone=mview.findViewById(R.id.tv_phone);
                layoutHandler.txt_salary=mview.findViewById(R.id.tv_salary);
                mview.setTag(layoutHandler);
            }
            else
            {
                layoutHandler=(LayoutHandler)mview.getTag();
            }
            Person p = (Person)this.getItem(position);


            //向TextView中插入数据
            layoutHandler.txt_name.setText(p.getName());
            layoutHandler.txt_phone.setText(p.getPhone());
            layoutHandler.txt_salary.setText(p.getSalary());
            return mview;
        }

    }


    // 插入数据
    public void Insert() {

            ContentValues values = new ContentValues();
            values.put("name", "张三" );
            values.put("salary", "123" );
            values.put("phone", "151" );
            db.insert("person", null, values);

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
