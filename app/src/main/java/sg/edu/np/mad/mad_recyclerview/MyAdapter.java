package sg.edu.np.mad.mad_recyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private OnTextClickedListener listener;

    //Declare constant
    private final String TAG = "TaskList";

    //Declare Member variables
    private ArrayList<String> mTaskList;

    public MyAdapter(ArrayList<String> taskList) {
        mTaskList = taskList;
        this.listener = null;

        Log.d(TAG, "Total items: " + mTaskList.size());
    }

    public void setOnTextClicked(OnTextClickedListener onTextClickedListener){
        this.listener = onTextClickedListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Inflate the layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        //Set task name
        holder.mTextView.setText(mTaskList.get(position));

        //If text view is clicked delete the content
        //Click listener for the text
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trigger the text clicked call back
                listener.onTextClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mTaskList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void addItem(String task){
        mTaskList.add(task);

        //Informing the adapter and view of the new item
        notifyItemInserted(mTaskList.size());
        Log.d(TAG, "New Task added, " + task);
    }

    public void removeItem(int position){
        Log.d(TAG, "Removing " + mTaskList.get(position));

        mTaskList.remove(position);

        //Informing the adapter and view after removing
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mTaskList.size());

    }

    //Give back the current array list
    public ArrayList<String> getTaskList() {
        return mTaskList;
    }

    public String getTaskName(int position){
        return mTaskList.get(position);
    }
}

interface OnTextClickedListener {

    public void onTextClick(int postition);
}
