package com.example.epsilonnutrition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryAdapter extends ArrayAdapter<DiaryEntry> {
  public DiaryAdapter(Context context, ArrayList<DiaryEntry> results) {
    super(context, 0, results);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    // Get the data item for this position
    DiaryEntry entry = getItem(position);
    // Check if an existing view is being reused, otherwise inflate the view
    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.diary_entry, parent, false);
    }
    // Lookup view for data population
    TextView entryTitle = convertView.findViewById(R.id.entry_title);
    TextView entryDate = convertView.findViewById(R.id.entry_date);
    TextView entryContent = convertView.findViewById(R.id.entry_content);
    // Populate the data into the template view using the data object
    entryTitle.setText(entry.title);
    entryDate.setText(String.format("%s %s", entry.date, entry.time));
    entryContent.setText(entry.entry);
    // Return the completed view to render on screen
    return convertView;
  }
}

