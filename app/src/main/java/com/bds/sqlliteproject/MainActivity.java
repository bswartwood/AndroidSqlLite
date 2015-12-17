package com.bds.sqlliteproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button addDataButton;
    private Button viewAllDataButton;
    private Button updateDataButton;
    private Button deleteDataButton;
    private DatabaseHelper dbHelper;
    private EditText nameEditText;
    private EditText surnameEditText;
    private EditText marksEditText;
    private EditText idEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        marksEditText = (EditText) findViewById(R.id.marksEditText);
        idEditText = (EditText) findViewById(R.id.idEditText);

        addDataButton = (Button) findViewById(R.id.addDataButton);
        addDataButton.setOnClickListener(createAddDataOnClickListener());
        viewAllDataButton = (Button) findViewById(R.id.viewAllButton);
        viewAllDataButton.setOnClickListener(createViewAllDataOnClickListener());
        updateDataButton = (Button) findViewById(R.id.updateButton);
        updateDataButton.setOnClickListener(createUpdateDataOnClickListener());
        deleteDataButton = (Button) findViewById(R.id.deleteButton);
        deleteDataButton.setOnClickListener(createDeleteDataOnClickListener());

        dbHelper = new DatabaseHelper(this);
    }

    public View.OnClickListener createAddDataOnClickListener() {
        return (new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = dbHelper.insertData(nameEditText.getText().toString(),
                        surnameEditText.getText().toString(),
                        marksEditText.getText().toString());
                if(isInserted) {
                    Toast.makeText(MainActivity.this,
                            "Data Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Error inserting data", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public View.OnClickListener createViewAllDataOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor results = dbHelper.getAllData();
                if(results.getCount() == 0 ) {
                    showMessage("Error", "No data was found");
                } else {
                    StringBuffer buffer = new StringBuffer();
                    while(results.moveToNext()) {
                        buffer.append(DatabaseHelper.ID_COLUMN + ": " + results.getString(0));
                        buffer.append("\n" + DatabaseHelper.NAME_COLUMN + ": " + results.getString(1));
                        buffer.append("\n" + DatabaseHelper.SURNAME_COLUMN + ": " + results.getString(2));
                        buffer.append("\n" + DatabaseHelper.MARKS_COLUMN + ": " + results.getString(3));
                        showMessage("Data", buffer.toString());
                    }
                }

            }
        };
    }

    public View.OnClickListener createUpdateDataOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean result =  dbHelper.updateData(idEditText.getText().toString(),
                       nameEditText.getText().toString(),
                       surnameEditText.getText().toString(),
                       marksEditText.getText().toString());

                if(result) {
                    Toast.makeText(MainActivity.this, "Data updated", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public View.OnClickListener createDeleteDataOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer result =  dbHelper.deleteData(idEditText.getText().toString());

                if(result > 0) {
                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Data not deleted", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
