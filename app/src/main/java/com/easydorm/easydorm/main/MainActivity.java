package com.easydorm.easydorm.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.LoginActivity;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.UserInfoActivity;
import com.easydorm.easydorm.Utils.UserUtil;
import com.easydorm.easydorm.annotation.LoginRequired;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.easydorm.easydorm.main.adapter.MainPagerAdapter;
import com.easydorm.easydorm.main.fragment.AttentionFragment;
import com.easydorm.easydorm.main.fragment.DormFragment;
import com.easydorm.easydorm.main.fragment.MessageFragment;
import com.easydorm.easydorm.main.fragment.RecommendFragment;
import com.easydorm.easydorm.main.fragment.SearchFragment;

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
    PopupMenu popupMenu;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(UserUtil.loginCheck() == 0) {
            startActivity(new Intent(this, LoginActivity.class));
        }
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


    @SuppressLint("RestrictedApi")
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
        popupMenu = new PopupMenu(this, actionMenuView);
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
                switch (menuItem.getItemId()) {
                    case R.id.navigation_user_info:
                        startUserInfo();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "别点啦！还没做呢", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        toolBarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
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
                            viewPager.setCurrentItem(0, false);
                        }
                        break;
                    case R.id.navigation_dorm:
                        tabLayout.setVisibility(View.INVISIBLE);
                        textView.setText("宿舍");
                        viewPager.setCurrentItem(3, false);
                        break;
                    case R.id.navigation_message:
                        tabLayout.setVisibility(View.INVISIBLE);
                        textView.setText("消息");
                        viewPager.setCurrentItem(4, false);
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

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        actionMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        popupMenu.inflate(R.menu.toolbar_main_menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_share:
                //TODO
                Toast.makeText(MainActivity.this, "这里是分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_transaction:
                //TODO
                Toast.makeText(MainActivity.this, "这边是交易", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @LoginRequired
    private void startUserInfo() {
        startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
    }

}
