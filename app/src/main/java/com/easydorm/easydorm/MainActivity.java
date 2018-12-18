package com.easydorm.easydorm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.easydorm.easydorm.adapter.MainPagerAdapter;
import com.easydorm.easydorm.fragment.AttentionFragment;
import com.easydorm.easydorm.fragment.DormFragment;
import com.easydorm.easydorm.fragment.MessageFragment;
import com.easydorm.easydorm.fragment.RecommendFragment;
import com.easydorm.easydorm.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity {

    Toolbar toolbar;
    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;
    CircleImageView userAvatarView, toolBarIcon;
    View navigationHeader;
    DrawerLayout drawerLayout;
    ViewPager viewPager;
    MainPagerAdapter mainPagerAdapter;
    ArrayList<Fragment> fragmentsList;

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

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //TODO
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        Toast.makeText(MainActivity.this, "这是主页", Toast.LENGTH_SHORT).show();
                        if(viewPager.getCurrentItem() > 2) {
                            viewPager.setCurrentItem(0);
                        }
                        break;
                    case R.id.navigation_dorm:
                        Toast.makeText(MainActivity.this, "这是您的宿舍", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.navigation_message:
                        Toast.makeText(MainActivity.this, "您没有收到消息", Toast.LENGTH_SHORT).show();
                        viewPager.setCurrentItem(4);
                        break;
                }
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

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                    case 1:
                    case 2:
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

    }

}
