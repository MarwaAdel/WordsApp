package com.example.wordsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Marwa Adel on 10/2/2021.
 */
public class WordsAdapter extends BaseAdapter implements Filterable {
    private ArrayList mData = new ArrayList();
    List<String> itemsListAlll = new ArrayList<>();
    private ArrayList<HashMap<String, Integer>> originalData;

    public WordsAdapter(Map<String, Integer> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        originalData = mData;
//        itemsListAlll.addAll(mData);

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, Integer> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, Integer> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(R.id.wordName)).setText(item.getKey());
        ((TextView) result.findViewById(R.id.repeatedNumber)).setText(String.valueOf(item.getValue()));

        return result;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence == null || charSequence.length() == 0) {
                results.values = originalData;
                results.count = originalData.size();
            } else {
                ArrayList<HashMap<String, Integer>> filterResultsData = new ArrayList<HashMap<String, Integer>>();

                for (HashMap<String, Integer> data : originalData) {
                    if (data.containsKey(charSequence.toString().toLowerCase())) {
                        filterResultsData.add(data);
                    }
                }
                results.values = filterResultsData;
//                results.count = filterResultsData.size();
            }

            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//            originalData.clear();
//            mData.addAll((Collection<? extends String>) filterResults.values);
//            mData = (ArrayList) filterResults.values;
            originalData = (ArrayList<HashMap<String, Integer>>) filterResults.values;
            notifyDataSetChanged();
        }
    };
}