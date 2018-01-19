package lin.wonder_for_muscle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kuma on 1/19/2018.
 */

class SQL_Listview_Adapter extends ArrayAdapter<SQL_Place> {

        // 畫面資源編號
        private int resource;
        // 包裝的記事資料
        private List<SQL_Place> sql_places;

        public SQL_Listview_Adapter(Context context, int resource, List<SQL_Place> items) {
            super(context, resource, items);
            this.resource = resource;
            this.sql_places = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout itemView;
            // 讀取目前位置的記事物件
            final SQL_Place sql_place = getItem(position);

            if (convertView == null) {
                // 建立項目畫面元件
                itemView = new LinearLayout(getContext());
                String inflater = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater li = (LayoutInflater)
                        getContext().getSystemService(inflater);
                li.inflate(resource, itemView, true);
            }
            else {
                itemView = (LinearLayout ) convertView;
            }

            // 讀取記事顏色、已選擇、標題與日期時間元件
            TextView id_listview = (TextView) itemView.findViewById(R.id.id_listview);
            TextView datetime_listview = (TextView) itemView.findViewById(R.id.datetime_listview);

            // 設定記事顏色

            // 設定標題與日期時間
            id_listview.setText(( int ) sql_place.getId());
            datetime_listview.setText(sql_place.getDatetime());


            return itemView;
        }

        // 設定指定編號的記事資料
        public void set(int index, SQL_Place item) {
            if (index >= 0 && index < sql_places.size()) {
                sql_places.set(index, item);
                notifyDataSetChanged();
            }
        }

        // 讀取指定編號的記事資料
        public SQL_Place get(int index) {
            return sql_places.get(index);
        }

}

