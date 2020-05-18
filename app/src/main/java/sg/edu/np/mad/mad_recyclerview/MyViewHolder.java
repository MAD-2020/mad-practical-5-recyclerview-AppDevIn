package sg.edu.np.mad.mad_recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView mTextView;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        mTextView = itemView.findViewById(R.id.textView);

    }
}
