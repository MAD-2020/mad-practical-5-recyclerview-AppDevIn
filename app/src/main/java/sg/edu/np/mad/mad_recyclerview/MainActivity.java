package sg.edu.np.mad.mad_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    final String TAG = "TaskList";

    RecyclerView mRecyclerView;
    MyAdapter mMyAdapter;
    EditText mEdAddTask;
    Button mBtnAddTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "Creating GUI");

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);

        mMyAdapter = new MyAdapter(new ArrayList<String>(Arrays.asList("Do MAD", "Part 1", "Part 2", "Part 3")));
        mRecyclerView.setAdapter(mMyAdapter);

        //Find IDs for button and EditTask
        mEdAddTask = findViewById(R.id.txtTaskInput); //To enter the task name
        mBtnAddTask = findViewById(R.id.btnSubmit); // Used to submit the task name



    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "GUI ready");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "GUI in the foreground and interactive");

        //Click listener for the button to add text into the array
        mBtnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyAdapter.addItem(mEdAddTask.getText().toString().trim());

                showNewEntry(mRecyclerView, mMyAdapter.getTaskList());
            }
        });

        //When the text view clieked in will give a dialog
        mMyAdapter.setOnTextClicked(new OnTextClickedListener() {
            @Override
            public void onTextClick(int position) {
                Log.d(TAG, "Delete prompt is triggered for position " + position);

                //TODO: Add in the dialog
            }
        });
    }

    //TODO: To confirm whatever if you want it deleted

    /**
     * Upon calling this method, the keyboard will retract
     * and the recyclerview will scroll to the last item
     *
     * @param rv RecyclerView for scrolling to
     * @param data ArrayList that was passed into RecyclerView
     */
    private void showNewEntry(RecyclerView rv, ArrayList data){
        //scroll to the last item of the recyclerview
        rv.scrollToPosition(data.size());

        //auto hide keyboard after entry
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rv.getWindowToken(), 0);
    }
}
