package com.evilnapsis.mycrudex;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class ElementsActivity extends ListActivity {

    Button back;
    Contact data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elements);

        back = (Button) findViewById(R.id.back);
        data = new Contact(this);
        data.open();

        final List<Contact>  values = data.getAll();
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1,values);
        setListAdapter(adapter);

        ListView listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(ElementsActivity.this,EditActivity.class);
                i.putExtra("id",values.get(position).id);
                i.putExtra("name",values.get(position).name);
                i.putExtra("lastname",values.get(position).lastname);
                i.putExtra("address",values.get(position).address);
                i.putExtra("email",values.get(position).email);
                i.putExtra("phone",values.get(position).phone);
                startActivity(i);

            }

        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
        final List<Contact>  values = data.getAll();
        ArrayAdapter<Contact> adapter = new ArrayAdapter<Contact>(this, android.R.layout.simple_expandable_list_item_1,values);
        setListAdapter(adapter);
    }
}
