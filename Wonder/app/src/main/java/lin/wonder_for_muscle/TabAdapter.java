package lin.wonder_for_muscle;
//import android.app.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Kuma on 12/14/2017.
 */

 public class TabAdapter extends FragmentStatePagerAdapter {
    String [] title=new String[]{"訓練","紀錄"};

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                TraningFragment traningFragment=new TraningFragment();
                return  traningFragment;
            case 1:
                HistoryFragment historyFragment=new HistoryFragment();
                return historyFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
