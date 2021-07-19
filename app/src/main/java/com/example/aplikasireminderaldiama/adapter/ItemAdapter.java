package com.example.aplikasireminderaldiama.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.aplikasireminderaldiama.R;
import com.example.aplikasireminderaldiama.database.DatabaseHelper;
import com.example.aplikasireminderaldiama.model.ModelData;

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ModelData> arrayList;

    public ItemAdapter(Context context, ArrayList<ModelData> arrayList) {
        super();
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert layoutInflater != null;
        convertView = layoutInflater.inflate(R.layout.list_todo, null);
        TextView titleTextView = convertView.findViewById(R.id.title);
        TextView dateTextView = convertView.findViewById(R.id.dateTitle);
        TextView timeTextView = convertView.findViewById(R.id.timeTitle);
        final ImageView delImageView = convertView.findViewById(R.id.delete);
        delImageView.setTag(position);

        // Delete Task From Database When Icon Clicked
        delImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = (int) v.getTag();
                deleteItem(pos);
            }
        });

        ModelData modelData = arrayList.get(position);
        titleTextView.setText(modelData.getTitle());
        dateTextView.setText(modelData.getDate());
        timeTextView.setText(modelData.getTime());
        return convertView;
    }

    // Deleting Task From List View
    private void deleteItem(int position) {
        deleteItemFromDb(arrayList.get(position).getId());
        arrayList.remove(position);
        notifyDataSetChanged();
    }

    // Deleting Task From Database
    private void deleteItemFromDb(int id) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        try {
            databaseHelper.deleteData(id);
            toastMsg("Tugas Telah Berhasil Dihapus");
        } catch (Exception e) {
            e.printStackTrace();
            toastMsg("Oppss.. Ada Kesalahan Saat Menghapus Data");
        }
    }

    // Show Toast Mess
    private void toastMsg(String msg) {
        Toast t = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER,0,0);
        t.show();
    }
}
