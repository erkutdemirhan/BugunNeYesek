package com.erkutdemirhan.bugunneyesek.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.erkutdemirhan.bugunneyesek.R;
import com.erkutdemirhan.bugunneyesek.domain.RecipeType;


import java.util.ArrayList;

/**
 * Implements the main activity screen of the application
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar                mToolbar;
    private NavigationView         mNavigationView;
    private DrawerLayout           mDrawerLayout;
    private ActionBarDrawerToggle  mDrawerToggle;

    private ArrayList<String>      mDrawerList;
    private ArrayAdapter<String>   mDrawerArrayAdapter;
    private ListView               mDrawerListView;

    private ViewPager              mViewPager;
    private ViewPagerAdapter       mViewPagerAdapter;
    private TabLayout              mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        if(mToolbar != null) {
            mToolbar.setTitle(R.string.app_name);
            setSupportActionBar(mToolbar);
        }
        mNavigationView = (NavigationView) findViewById(R.id.main_navigationview);
        mDrawerLayout   = (DrawerLayout)   findViewById(R.id.main_drawer);
        mDrawerToggle   = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mDrawerListView = (ListView) findViewById(R.id.main_listview);
        initDrawerList();
        mDrawerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDrawerList);
        mDrawerListView.setAdapter(mDrawerArrayAdapter);
        mDrawerListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), this);
        mTabLayout        = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager        = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_download_screen, menu);
        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initDrawerList() {
        mDrawerList = new ArrayList<>();
        mDrawerList.add(getString(R.string.drawer_list_allrecipes));
        ArrayList<RecipeType> allRecipeTypes = BugunNeYesek.getInstance().getRecipeTypeList();
        for(RecipeType recipeType:allRecipeTypes) {
            mDrawerList.add(recipeType.getTypeName());
        }
    }
}
