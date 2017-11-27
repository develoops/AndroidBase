package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import java.util.List;

import fragments.CompanyDirectoryFragment;


import fragments.DirectiveFragment;
import fragments.LoadDataFragment;
import fragments.MeetingsFragment;
import fragments.NewsFragment;

/**
 * Created by Alvaro on 3/3/15.
 */
public class PagerFragmentAdapter extends FragmentStatePagerAdapter



{
    List <String> tabUIs; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    FragmentManager fragmentManager;
    public PagerFragmentAdapter(FragmentManager fm,List views, int mNumbOfTabsumb) {
        super(fm);
        this.fragmentManager = fm;
        this.tabUIs = views;
        this.NumbOfTabs = mNumbOfTabsumb;
        Log.i("TABS", String.valueOf(NumbOfTabs));

    }

    @Override
    public void destroyItem(View collection, int position, Object o) {
        View view = (View)o;
        ((ViewPager) collection).removeView(view);
        view = null;
    }


    @Override
    public int getCount()
    {
        return NumbOfTabs;
    }
    @Override
    public Fragment getItem(int position)
    {


        if(tabUIs.get(position).equals("Congresos")){
            return MeetingsFragment.newInstance(LoadDataFragment.actividades);
        }
        else if(tabUIs.get(position).equals("Sociedad")){
            return CompanyDirectoryFragment.newInstance(LoadDataFragment.com, false);
        }
        else if(tabUIs.get(position).equals("Directiva")){
            return DirectiveFragment.newInstance(LoadDataFragment.staff);
        }
//        else if(tabUIs.get(position).equals("navAbout")){
//            return mCongressFragment.newInstance(LoadDataFragment.mobiCongress);
//        }
        else {
            return new NewsFragment();
        }



    }
    @Override
    public String getPageTitle(int position) {



               return tabUIs.get(position);



    }




}
