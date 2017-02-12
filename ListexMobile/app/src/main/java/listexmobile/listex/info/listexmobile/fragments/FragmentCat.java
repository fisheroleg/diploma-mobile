package listexmobile.listex.info.listexmobile.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import listexmobile.listex.info.listexmobile.R;
import listexmobile.listex.info.listexmobile.views.SlidingTabLayout;

public class FragmentCat extends ListFragment {

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private ViewGroup mContainer;

    public FragmentCat() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_cat, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());

        // Give the SlidingTabLayout the ViewPager, this must be
        // done AFTER the ViewPager has had it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setBackgroundColor(Color.WHITE);

        DisplayMetrics metrics = getActivity().getBaseContext().getResources().getDisplayMetrics();

        mSlidingTabLayout.setViewPager(mViewPager, metrics.widthPixels);
    }

    ListView lvMain;

    // Adapter
    class SamplePagerAdapter extends PagerAdapter {

        /**
         * Return the number of pages to display
         */

        private int count = 5;
        @Override
        public int getCount() {
            return count;
        }

        /**
         * Return true if the value returned from is the same object as the View
         * added to the ViewPager.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        /**
         * Return the title of the item at position. This is important as what
         * this method returns is what is displayed in the SlidingTabLayout.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            switch(position)
            {
                case 0: return getActivity().getResources().getString(R.string.item_cat_root);
            }

            return "Уровень " + (position);
        }

        /**
         * Instantiate the View which should be displayed at position. Here we
         * inflate a layout from the apps resources and then change the text
         * view to signify the position.
         */

        String[] names = { "Косметика", "Непродовольственные", "Продовольственные", "Стройка/ремонт", "Фармация", "Одежда/обувь", "Спортивный инвентарь", "Техника", "Услуги" };

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = getActivity().getLayoutInflater().inflate(R.layout.cat_pager_item,
                    container, false);
            // Add the newly created View to the ViewPager
            container.addView(view);

            mContainer = container;

            // находим список
            lvMain = (ListView) view.findViewById(R.id.lvCat);
            // создаем адаптер
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.cat_list_item, names);
            // присваиваем адаптер списку
            lvMain.setAdapter(adapter);

            lvMain.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSlidingTabLayout.addTabToStrip();

                    View newView = getActivity().getLayoutInflater().inflate(R.layout.cat_pager_item,
                            mContainer, false);
                    // Add the newly created View to the ViewPager
                    mContainer.addView(newView);

                    String item = lvMain.getItemAtPosition(position).toString();
                    Toast.makeText(getActivity(), "Перейти на \"" + item +"\"", Toast.LENGTH_SHORT).show();
                }
            });

            // Retrieve a TextView from the inflated View, and update it's text
            //TextView title = (TextView) view.findViewById(R.id.item_title);
            //title.setText(String.valueOf(position + 1));

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the ViewPager. In our case this is simply
         * removing the View.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}