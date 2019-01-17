package com.easydorm.easydorm.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.easydorm.easydorm.AboutActivity;
import com.easydorm.easydorm.BaseActivity;
import com.easydorm.easydorm.EasyDormApp;
import com.easydorm.easydorm.R;
import com.easydorm.easydorm.userinfo.UserInfoActivity;
import com.easydorm.easydorm.Utils.ActivityCollector;
import com.easydorm.easydorm.Utils.NetWorkUtil;
import com.easydorm.easydorm.Utils.ToastUtil;
import com.easydorm.easydorm.main.adapter.MainPagerAdapter;
import com.easydorm.easydorm.posts.activity.WritePostActivity;
import com.easydorm.easydorm.posts.fragment.AttentionFragment;
import com.easydorm.easydorm.dorm.DormFragment;
import com.easydorm.easydorm.chat.MessageFragment;
import com.easydorm.easydorm.posts.fragment.RecommendFragment;
import com.easydorm.easydorm.posts.fragment.SearchFragment;
import com.easydorm.easydorm.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar_main) Toolbar toolbar;
    @BindView(R.id.navigation) NavigationView navigationView;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.drawer_layout_main) DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_tab) TabLayout tabLayout;
    @BindView(R.id.view_pager_main) ViewPagerPlus viewPager;
    @BindView(R.id.text_title) TextView textView;

    @BindView(R.id.toolbar_menu_view) ActionMenuView actionMenuView;
    @BindView(R.id.toolbar_icon) CircleImageView toolBarIcon;
    CircleImageView userAvatarView;
    View navigationHeader;
//    PopupWindow popupWindow;
//    TextView shareTextView,transactionTextView;

    ArrayList<Fragment> fragmentsList;
    MainPagerAdapter mainPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityCollector.addActivity(this);

        initView();
        initListener();
        initData();

    }

    private void initData() {
        NetWorkUtil.checkNetWork();

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
        navigationHeader = navigationView.getHeaderView(0);
        userAvatarView = navigationHeader.findViewById(R.id.user_avatar);
        setSupportActionBar(toolbar);

//        View contentView = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_window_up, null);
//        shareTextView = contentView.findViewById(R.id.pop_share);
//        transactionTextView = contentView.findViewById(R.id.pop_transaction);
//        popupWindow = new PopupWindow(contentView, 296, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setOutsideTouchable(true);

//        ViewUtil.setDrawerLeftEdgeSize(this, drawerLayout, 1.0f);

    }

    private void initListener() {

        userAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.toast(">_<");
                //TODO
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //TODO
                switch (menuItem.getItemId()) {
                    case R.id.navigation_user_info:
                        startActivity(new Intent(MainActivity.this, UserInfoActivity.class));
                        break;
                    case R.id.setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                    case R.id.about:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    default:
                        ToastUtil.toast("还没做");
                }
                return true;
            }
        });

        toolBarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
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
                        if (viewPager.getCurrentItem() > 2) {
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

        actionMenuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WritePostActivity.class));
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        String avatarPath = EasyDormApp.getUser().getUserInfo().getAvatarPath();
        String avatarUrl = EasyDormApp.getUser().getUserInfo().getAvatarUrl();
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.avatar)
                .error(R.mipmap.avatar);
        if(avatarPath != null && !avatarPath.equals("")) {
            Glide.with(this).load(avatarPath).apply(options).into(userAvatarView);
            Glide.with(this).load(avatarPath).apply(options).into(toolBarIcon);
        } else if(avatarUrl != null && !avatarUrl.equals("")) {
            Glide.with(this).load(avatarUrl).apply(options).into(userAvatarView);
            Glide.with(this).load(avatarUrl).apply(options).into(toolBarIcon);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
