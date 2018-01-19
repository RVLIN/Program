package lin.wonder_for_muscle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kuma on 1/19/2018.
 */

public class MyOpenHelper  extends SQLiteOpenHelper {

    public MyOpenHelper(Context context) {
        //创建数据库
        super(context, "person.db", null, 1);
        // TODO Auto-generated constructor stub
        System.out.println("MyOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        //创建表
        db.execSQL("create table person(_id integer primary key autoincrement, name char(10), salary char(20), phone integer(20) )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }


}
