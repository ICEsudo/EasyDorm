package com.easydorm.easydorm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easydorm.easydorm.adapter.MainPagerAdapter;
import com.easydorm.easydorm.fragment.AttentionFragment;
import com.easydorm.easydorm.fragment.DormFragment;
import com.easydorm.easydorm.fragment.MessageFragment;
import com.easydorm.easydorm.fragment.RecommendFragment;
import com.easydorm.easydorm.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    CircleImageView userAvatarView, toolBarIcon;
    View navigationHeader;
    DrawerLayout drawerLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    MainPagerAdapter mainPagerAdapter;
    ArrayList<Fragment> fragmentsList;
    TextView textView;
    ActionMenuView actionMenuView;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, LoginActivity.class));

        initView();
        initListener();
        initData();

    }

    private void initData() {
        sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);

        fragmentsList = new ArrayList<>();
        fragmentsList.add(new RecommendFragment());
        fragmentsList.add(new AttentionFragment());
        fragmentsList.add(new SearchFragment());
        fragmentsList.add(new DormFragment());
        fragmentsList.add(new MessageFragment());

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mainPagerAdapter.setListViews(fragmentsList);
        viewPager.setAdapter(mainPagerAdapter);


    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar_main);

        navigationView = findViewById(R.id.navigation);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        navigationHeader = navigationView.getHeaderView(0);
        userAvatarView = navigationHeader.findViewById(R.id.user_avatar);
        drawerLayout = findViewById(R.id.drawer_layout_main);
        toolBarIcon = toolbar.findViewById(R.id.toolbar_icon);
        viewPager = findViewById(R.id.view_pager_main);
        tabLayout = findViewById(R.id.toolbar_tab);
        textView = findViewById(R.id.text_title);
        actionMenuView = toolbar.findViewById(R.id.toolbar_menu_view);
        setSupportActionBar(toolbar);
        Log.d("debug", "initView");
    }

    private void initListener() {

        userAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "别点我>_<", Toast.LENGTH_SHORT).show();
                //TODO
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //TODO
                Toast.makeText(MainActivity.this, "别点啦！还没做呢", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        toolBarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(Gravity.START)) {
                    drawerLayout.closeDrawer(Gravity.START);
                } else {
                    drawerLayout.openDrawer(Gravity.START);
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        textView.setText("");
                        tabLayout.setVisibility(View.VISIBLE);
                        if(viewPager.getCurrentItem() > 2) {
                            viewPager.setCurrentItem(0);
                        }
                        break;
                    case R.id.navigation_dorm:
                        tabLayout.setVisibility(View.INVISIBLE);
                        textView.setText("宿舍");
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.navigation_message:
                        tabLayout.setVisibility(View.INVISIBLE);
                        textView.setText("消息");
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        viewPager.setCurrentItem(0);
                        break;
                    case 1:
                        viewPager.setCurrentItem(1);
                        break;
                    case 2:
                        viewPager.setCurrentItem(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        Objects.requireNonNull(tabLayout.getTabAt(0)).select();
                        break;
                    case 1:
                        Objects.requireNonNull(tabLayout.getTabAt(1)).select();
                        break;
                    case 2:
                        Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_dorm);
                        break;
                    case 4:
                        bottomNavigationView.setSelectedItemId(R.id.navigation_message);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


        actionMenuView.setOnMenuItemClickListener(new ActionMenuView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return onOptionsItemSelected(menuItem);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("debug", "onCreateMenu");

        getMenuInflater().inflate(R.menu.toolbar_main_menu, actionMenuView.getMenu());
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("debug", "onClick");
        switch (item.getItemId()) {
            case R.id.toolbar_share:
                //TODO
                Log.d("debug", "share");
                Toast.makeText(MainActivity.this, "这里是分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_transaction:
                //TODO
                Log.d("debug", "jiaoyi");
                Toast.makeText(MainActivity.this, "这边是交易", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
