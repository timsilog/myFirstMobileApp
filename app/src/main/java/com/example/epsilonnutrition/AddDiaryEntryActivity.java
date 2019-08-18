package com.example.epsilonnutrition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class AddDiaryEntryActivity extends Fragment {
  Database db;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //Calls XML Layout File
    return inflater.inflate(R.layout.add_diary_entry_page, container, false);
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // Header Title
    getActivity().setTitle("Epsilon Nutrition - Add Diary Entry");

    getDb();

    //do button stuff
    Button b = getActivity().findViewById(R.id.button_submit_entry);
    b.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        EditText title = getActivity().findViewById(R.id.edit_title);
        EditText data = getActivity().findViewById(R.id.edit_entry);
        DiaryEntry entry = new DiaryEntry(title.getText().toString(), data.getText().toString());
        db.diary.add(entry);
        writeToDb();
        Fragment fragment = new DiaryActivity();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
      }
    });
  }

  public void getDb() {
    try {
      Context ctx = getActivity().getApplicationContext();
      FileInputStream fi = ctx.openFileInput("db");
      ObjectInputStream oi = new ObjectInputStream(fi);
      db = (Database)oi.readObject();
      Log.e("TIM_ISIN", "got a db in add diaries");
      fi.close();
      oi.close();
    } catch (FileNotFoundException e) {
      Log.e("TIM_FILENOTFOUND", e.toString());
      Intent welcome = new Intent(getActivity(), WelcomeActivity.class);
      startActivity(welcome);
      getActivity().finish();
    } catch (IOException e) {
      Log.e("TIM_IOEXCEPTION", e.toString());
    } catch (ClassNotFoundException e) {
      Log.e("TIM_CLASSNOTFOUND", e.toString());
    }
  }

  public void writeToDb() {
    try {
      File file = new File(getActivity().getFilesDir(), "db");
      FileOutputStream f = new FileOutputStream(file);
      ObjectOutputStream o = new ObjectOutputStream(f);
      o.writeObject(db);
      o.close();
      f.close();
    } catch(FileNotFoundException er) {
      Log.d("TIM_STILL_FILENOTFOUND", er.toString());
    } catch(IOException er) {
      Log.d("TIM_STILL_IOERROR", er.toString());
    }
  }
}
