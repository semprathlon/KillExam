package com.example.yuwei.killexam;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.example.yuwei.killexam.taskFragments.CreateTaskFragment;
import com.example.yuwei.killexam.tools.Task;


public class MainActivity extends ActionBarActivity
        implements com.example.yuwei.killexam.NavigationDrawerFragment.NavigationDrawerCallbacks{

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        getBelongInCreateTask();
    }

    private void getBelongInCreateTask(){
        Intent intent = getIntent();
        String enterFragment = intent.getStringExtra("enterFragment");

        if (enterFragment != null) {
            switch (enterFragment) {
                case "CreateTaskFragment":
                    enterCreateTask(intent);
                    break;
            }
        }
    }

    private void enterCreateTask(Intent intent){

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment targetFragment = getTargetFragment(2);

        getNewTask((CreateTaskFragment)targetFragment, intent);
        fragmentManager.beginTransaction()
                .replace(R.id.container, targetFragment)
                .commit();

    }

    private void getNewTask(CreateTaskFragment fragment, Intent intent){
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            Task task = (Task) bundle.get("task");
            fragment.setNewTask(task);
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment targetFragment = getTargetFragment(position);
        fragmentManager.beginTransaction()
                .replace(R.id.container, targetFragment)
                .commit();
    }

    private Fragment getTargetFragment(int position){
        int sectionNumber = position + 1;
        Fragment targetFragment = PlaceholderFragment.newInstance(sectionNumber);
        if(sectionNumber == 1){
            targetFragment = new CreateTaskFragment(sectionNumber);
        }
        else if(sectionNumber == 2){
            targetFragment = new CreateTaskFragment(sectionNumber);
        }
        else if(sectionNumber == 3){
            targetFragment = new CreateTaskFragment(sectionNumber);
        }
        return targetFragment;
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }



}
