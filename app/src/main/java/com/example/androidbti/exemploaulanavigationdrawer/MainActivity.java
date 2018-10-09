package com.example.androidbti.exemploaulanavigationdrawer;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements PlanetAdapter.onItemClickListener{

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private Planet planetSelected;

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerList = findViewById(R.id.left_drawer);

        drawerList.setAdapter(new PlanetAdapter(this, this));
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                setTitle(R.string.title_choose);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if(planetSelected != null){
                    setTitle(planetSelected.getName());
                } else {
                    setTitle(R.string.app_name);
                }
                invalidateOptionsMenu();
            }
        };
        if(savedInstanceState == null){
            selectItem(null);
        } else {
            planetSelected = (Planet) savedInstanceState.getSerializable("planet");
            selectItem(planetSelected);
        }

        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public void onClick(Planet planet) {

        selectItem(planet);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        searchMenu.setVisible(!drawerOpen && planetSelected != null);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        if(item.getItemId() == R.id.action_search){
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, "Planeta: " + planetSelected.getName());
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void selectItem(Planet planet){
        if (planet != null){
            this.planetSelected = planet;
            Fragment fragment = PlanetFragment.newInstance(planet);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment);
            fragmentTransaction.commit();
            setTitle(planet.getName());
        }
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable("planet", planetSelected);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
}
