package com.dreamj.caliphcole.monasocial;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class InfiniteScrollAdapter<T> extends ArrayAdapter<T>{

    private Activity context;
    private T[] values;
    private int count;
    private int stepNumber;
    private int startCount;


    public InfiniteScrollAdapter(Activity context,T[] values,int startCount, int stepNumber) {
        super(context, R.layout.row_layout, values);
        this.context = context;
        this.values = values;
        this.startCount = Math.min(startCount, values.length);
        this.count = this.startCount;
        this.stepNumber = stepNumber;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.row_layout, null);
            view.setTag(view.findViewById(R.id.textView1));
        }else{
            view = convertView;
        }

        TextView textView = (TextView) view.getTag();
        textView.setText(values[position].toString());

        return view;
    }

    /**
     * Show more views, or the bottom
     * @return true if the entire data set is being displayed, false otherwise
     */
    public boolean showMore(){
        if(count == values.length) {
            return true;
        }else{
            count = Math.min(count + stepNumber, values.length); //don't go past the end
            notifyDataSetChanged(); //the count size has changed, so notify the super of the change
            return endReached();
        }
    }

    /**
     * @return true if then entire data set is being displayed, false otherwise
     */
    public boolean endReached(){
        return count == values.length;
    }

    /**
     * Sets the ListView back to its initial count number
     */
    public void reset(){
        count = startCount;
        notifyDataSetChanged();
    }

}


