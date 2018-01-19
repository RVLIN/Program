package lin.wonder_for_muscle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import static lin.wonder_for_muscle.R.id.tvname;


/**
 * A simple {@link Fragment} subclass.
 */
public class TraningFragment extends Fragment implements AdapterView.OnItemClickListener{

    private ListView listV;
    //List<TraningListView> Traning_list = new ArrayList<TraningListView>();
    int[]resIds=new int[]{
            R.drawable.shoulder_muscle,
            R.drawable.upper_arm_muscle,
            R.drawable.lower_arm_muscles,
            R.drawable.chest_muscle,
            R.drawable.back_muscle,
            R.drawable.abdominal_muscle,
            R.drawable.thigh_muscle,
            R.drawable.calf_muscle,
            R.drawable.gluteal_muscle
    };
    String[] name=new String[]{
            "肩膀",
            "上臂",
            "下臂",
            "胸肌",
            "背肌",
            "腹肌",
            "大腿",
            "小腿",
            "臀肌"
    };
    String[] id=new String[]{
            "開始訓練",
            "開始訓練",
            "開始訓練",
            "開始訓練",
            "開始訓練",
            "開始訓練",
            "開始訓練",
            "開始訓練",
            "開始訓練"
    };
    public TraningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_traning,null,false);
        listV=(ListView)view.findViewById(R.id.lvCustomListView);

        Adapter adapter = new MyAdpter(getActivity());
        listV.setAdapter(( ListAdapter ) adapter);
        listV.setOnItemClickListener(this);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long l) {
        Toast.makeText(getActivity(), "" + position, Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        switch (position)
        {
            case 0:
                Intent intent=new Intent();
                intent.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",0);
                startActivity(intent);
                break;
            case 1:
                Intent intent1=new Intent();
                intent1.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",1);
                startActivity(intent1);
                break;
            case 2:
                Intent intent2=new Intent();
                intent2.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",2);
                startActivity(intent2);
                break;
            case 3:
                Intent intent3=new Intent();
                intent3.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",3);
                startActivity(intent3);
                break;
            case 4:
                Intent intent4=new Intent();
                intent4.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",4);
                startActivity(intent4);
                break;
            case 5:
                Intent intent5=new Intent();
                intent5.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",5);
                startActivity(intent5);
                break;
            case 6:
                Intent intent6=new Intent();
                intent6.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",6);
                startActivity(intent6);
                break;
            case 7:
                Intent intent7=new Intent();
                intent7.setClass(getContext(),Bluetooth.class);
                bundle.putInt("type",7);
                startActivity(intent7);
                break;
            default:
                break;

        }
    }

    public class MyAdpter extends BaseAdapter
    {
        private LayoutInflater myinflater;
        public MyAdpter(Context c){
            myinflater=LayoutInflater.from(c);
        }


        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View converview, ViewGroup parent) {
            converview=myinflater.inflate(R.layout.listview_style,null);
            ImageView img=(ImageView)converview.findViewById(R.id.ivmg);
            TextView txtName=(TextView)converview.findViewById(tvname);
            TextView txtid=(TextView)converview.findViewById(R.id.tvid);
            img.setImageResource(resIds[position]);
            txtName.setText(name[position]);
            txtid.setText(id[position]);

            return converview;
        }
    }

}

