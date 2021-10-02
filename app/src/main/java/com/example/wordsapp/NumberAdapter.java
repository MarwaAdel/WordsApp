package com.example.wordsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marwa Adel on 10/2/2021.
 */
public class NumberAdapter extends ListAdapter<Integer, NumberAdapter.CourseHolder> {

    Context context;
    WordAdapter.OnItemClickedListner listner;
    List<Integer> receiveReturnOrderList = new ArrayList<>();
    List<Integer> itemsListAlll = new ArrayList<>();


    public NumberAdapter(Context context, List<Integer> receiveReturnOrderList) {
        super(diffCallback);
        this.context = context;
        this.receiveReturnOrderList = receiveReturnOrderList;
        itemsListAlll.addAll(receiveReturnOrderList);
        listner = (WordAdapter.OnItemClickedListner) context;
    }

    private static final DiffUtil.ItemCallback<Integer> diffCallback = new DiffUtil.ItemCallback<Integer>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Integer oldItem, @NonNull @NotNull Integer newItem) {
            return oldItem.equals(newItem);

        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Integer oldItem, @NonNull @NotNull Integer newItem) {
            return oldItem.equals(newItem);
        }


//        @Override
//        public boolean areContentsTheSame(@NonNull String  oldItem, @NonNull String newItem) {
//            return  oldItem.getWordName().equals(newItem.getWordName());
//        }
    };

    @NonNull
    @Override
    public NumberAdapter.CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order2, parent, false);
        return new NumberAdapter.CourseHolder(itemview);
    }

    public Integer GetCourseAt(int position) {
        return getItem(position);
    }

    @Override
    public void onBindViewHolder(@NonNull NumberAdapter.CourseHolder holder, int position) {
        Integer item = getItem(position);
        if (item != null) {
            holder.Number.setText(String.valueOf(item));
//            holder.Title.setText(String.valueOf(item));

        }
    }

//
//    @Override
//    public Filter getFilter() {
//        return myFilter;
//    }
//    Filter myFilter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence charSequence) {
//
//            List<ReceiveReturnOrder> filteredList = new ArrayList<>();
//
//            if (charSequence == null || charSequence.length() == 0) {
//                filteredList.addAll(itemsListAlll);
//            } else {
//                for (ReceiveReturnOrder items : itemsListAlll) {
//                    if ((String.format("%s%s", context.getString(R.string.supplier), String.valueOf(items.getSupplierID()))).toLowerCase().contains(charSequence.toString().toLowerCase())
//                            ||
//                            items.getPONumber().toLowerCase().contains(charSequence.toString().toLowerCase())
//                            ||
//                            items.getPOTitle().toLowerCase().contains(charSequence.toString().toLowerCase())
//
//                            ||
//                            getDate(items.getDateCreated())
//                                    .toLowerCase().contains(charSequence.toString().toLowerCase())
//                    ) {
//
//                        filteredList.add(items);
//                    }
//                    //  }
//                }
//            }
//
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredList;
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            receiveReturnOrderList.clear();
//            receiveReturnOrderList.addAll((Collection<? extends ReceiveReturnOrder>) filterResults.values);
//            notifyDataSetChanged();
//        }
//    };


    protected class CourseHolder extends RecyclerView.ViewHolder {
        TextView Number;
//        TextView Title;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            Number = itemView.findViewById(R.id.number_repeating);
//            Title = itemView.findViewById(R.id.word_name);

        }
    }

    public interface OnItemClickedListner {
        // TODO: Update argument type and name

    }
}


