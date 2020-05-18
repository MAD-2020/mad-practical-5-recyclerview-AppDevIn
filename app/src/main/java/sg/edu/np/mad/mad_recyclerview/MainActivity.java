package sg.edu.np.mad.mad_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

        //When the text view clicked in will give a dialog
        mMyAdapter.setOnTextClicked(new OnTextClickedListener() {
            @Override
            public void onTextClick(int position) {
                Log.d(TAG, "Delete prompt is triggered for position " + position);

                //TODO: Add in the dialog
                confirmDeleteDialog(position);
            }
        });
    }


    //To confirm whatever if you want it deleted
    @SuppressLint("SetTextI18n")
    public void confirmDeleteDialog(final int position) {

        Log.d(TAG, "Deletion prompt is building");

        //Create a alert builder
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_with_textview_and_imageview, null);

        //Set the message in the view
        TextView txtMessage = dialogLayout.findViewById(R.id.txtMessage);// Find id in the custom dialog
        txtMessage.setText("Are you sure you want to delete\n" + mMyAdapter.getTaskName(position) + "?");

        //Set trash in image view
        ImageView imgTrash = dialogLayout.findViewById(R.id.imgTrash); //Find the image view in the custom dialog
        imgTrash.setImageResource(android.R.drawable.ic_menu_delete); //Set the image from the android library delete


        builder.setTitle(R.string.delete); //Set the title of the dialog

        //Set a positive button: Yes
        //Method should remove the  item
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mMyAdapter.removeItem(position); //Remove item from the data in adapter
                Log.d(TAG, mMyAdapter.getTaskName(position) + "is deleted");
            }
        });

        //Set negative button: No
        //Method should close the dialog
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d(TAG, mMyAdapter.getTaskName(position) + "task not getting deleted");
            }
        });

        builder.setCancelable(false); //To prevent user from existing when clicking outside of the dialog
        builder.setView(dialogLayout);//Set the custom view
        builder.show();//Show the view to the user
        Log.d(TAG, "Deletion prompt is shown");
    }

    /**
     * Upon calling this method, the keyboard will retract
     * and the recyclerview will scroll to the last item
     *
     * @param rv RecyclerView for scrolling to
     * @param data ArrayList that was passed into RecyclerView
     */
    private void showNewEntry(RecyclerView rv, ArrayList data){
        //scroll to the last item of the recyclerview
        rv.smoothScrollToPosition(data.size());

        //auto hide keyboard after entry
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rv.getWindowToken(), 0);
    }
}
