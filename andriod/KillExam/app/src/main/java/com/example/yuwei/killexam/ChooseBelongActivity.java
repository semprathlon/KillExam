package com.example.yuwei.killexam;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.yuwei.killexam.database.MyDatabaseHelper;
import com.example.yuwei.killexam.adapter.BelongTaskAdapter;
import com.example.yuwei.killexam.tools.Task;

import java.util.ArrayList;


public class ChooseBelongActivity extends ActionBarActivity {

    private Task newTask;
    private String newTaskAttributeName;

    private String belongTasksAttributeName;

    private ArrayList<String> belongTasksNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getNewTaskInfo();
        getBelongTasksAttribute();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_belong);

        setBelongTasksView();

    }

    private void getNewTaskInfo(){
        Intent newTaskIntent = getIntent();
        Bundle newTaskBundle = newTaskIntent.getExtras();
        if (newTaskBundle != null) {
            newTask = (Task) newTaskBundle.get("task");
            newTaskAttributeName = newTask.getTaskAttribute().getSelectedName();
        }
        else{
            Log.e("danger", "chooseBelongActivity has no newTask");
        }
    }

    private void getBelongTasksAttribute(){
        if (newTaskAttributeName.equals("二级")){
            belongTasksAttributeName = "一级";
        }
        else if (newTaskAttributeName.equals("三级")){
            belongTasksAttributeName = "二级";
        }
        else if (newTaskAttributeName.equals("四级")){
            belongTasksAttributeName = "三级";
        }
    }

    private void setBelongTasksView(){
        initBelongTasks();

        BelongTaskAdapter adapter = new BelongTaskAdapter(this, R.layout.belong_task_item, belongTasksNames);

        ListView belongTaskListView = (ListView) findViewById(R.id.BelongTasksListView);
        belongTaskListView.setAdapter(adapter);

        belongTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String belongTaskName = belongTasksNames.get(position);
                newTask.setHasBelong(true);
                newTask.setBelongName(belongTaskName);
                quit();
            }
        });

    }

    private void initBelongTasks(){
         belongTasksNames = MyDatabaseHelper.getBelongTasksNames(this, belongTasksAttributeName);
    }

    private void quit(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("task", newTask);
        intent.putExtra("enterFragment", "CreateTaskFragment");
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_belong, menu);
        return true;
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
}
