package com.example.auser.yvts18;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.jar.Attributes;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    EditText tv1, tv2, tv3;
    ListView listView;
    String pho;
    private DB mDbHelper;
    private long rowId;
    private EditText editText1;
    private String editString1;
    private int mNoteNumber = 1;
    protected static final int MENU_INSERT = Menu.FIRST;
    protected static final int MENU_DELETE = Menu.FIRST + 1;
    protected static final int MENU_UPDATE = Menu.FIRST + 2;
    protected static final int MENU_INSERT_WITH_SPECIFIC_ID = Menu.FIRST + 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (EditText) findViewById(R.id.tv1);
        tv2 = (EditText) findViewById(R.id.tv2);
        tv3 = (EditText) findViewById(R.id.tv3);
        listView = (ListView) findViewById(R.id.listView);
        listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener(this);
        setAdapter();
    }

    private void setAdapter() {
        mDbHelper = new DB(this).open();
        fillData();
    }

    private void fillData() {
        Cursor c = mDbHelper.getAll();
        startManagingCursor(c);
        SimpleCursorAdapter scAdapter = new SimpleCursorAdapter(
                this,
                R.layout.item,
                c,
                new String[]{"uname", "phone", "email"},
                new int[]{R.id.textView4, R.id.textView5, R.id.textView6},
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(scAdapter);
    }


    public void clickadd(View v) {
        mNoteNumber = listView.getCount() + 1;
        String name = tv1.getText().toString();
        String phone = tv2.getText().toString();
        String email = tv3.getText().toString();
        mDbHelper.create(name, phone, email);
        fillData();
    }

    public void clickre(View v) {
        String name = tv1.getText().toString();
        String phone = tv2.getText().toString();
        String email = tv3.getText().toString();
        if (!tv1.equals("")) {
            mDbHelper.update(rowId, name, phone, email);
        }
        fillData();
    }

    public void clickdelete(View v) {
        mDbHelper.delete(rowId);
        fillData();
    }

    public void clickphone(View v) {
        Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + pho));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(call);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        rowId = id;
        System.out.println("rowId = " + rowId);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("AlertDialog：" + (position + 1));
        builder.setMessage("修改資料/刪除資料 請按[確認] 後選擇下面[修改][刪除]");
        builder.setPositiveButton("確認", null);
        builder.show();

    }


}