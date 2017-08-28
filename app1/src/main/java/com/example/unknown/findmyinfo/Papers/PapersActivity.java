package com.example.unknown.findmyinfo.Papers;

import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.unknown.findmyinfo.R;

public class PapersActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private FloatingActionButton fab;
    private ViewPager pager;
    private TabLayout tabLayout;
    private ActionBarDrawerToggle drawerToggle;

    PagerAdapterClass pagerAdapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_papers);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        pager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);

        pagerAdapterClass = new PagerAdapterClass(getSupportFragmentManager());

        fab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(findViewById(R.id.coordinator),"I'm Snack Bar",Snackbar.LENGTH_SHORT)
                                .setAction("Action", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Toast.makeText(PapersActivity.this,"Action Selected",Toast.LENGTH_SHORT).show();

                                    }
                                }).show();
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        pager.setAdapter(pagerAdapterClass);
        tabLayout.setupWithViewPager(pager);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    public class PagerAdapterClass extends FragmentStatePagerAdapter {
        public PagerAdapterClass(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PagerFragmentClass.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 3) {
                return "External";
            }
            return "Sessional " + (position + 1);
        }
    }


    public static class PagerFragmentClass extends Fragment{

        private static final String TAB_POSITION = "tab_position";

        public PagerFragmentClass(){

        }

        public static PagerFragmentClass newInstance(int position){
            PagerFragmentClass aClass = new PagerFragmentClass();
            Bundle bundle = new Bundle();
            bundle.putInt(TAB_POSITION,position);
            aClass.setArguments(bundle);
            return aClass;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            Bundle bundle = getArguments();
            int tabPosition = bundle.getInt(TAB_POSITION);
            TextView tv = new TextView(getActivity());
            tv.setGravity(Gravity.CENTER);
            tv.setText("This is " + tabPosition + " Tab");
            return tv;
        }
    }

}


