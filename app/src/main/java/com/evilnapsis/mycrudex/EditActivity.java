package com.evilnapsis.mycrudex;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    Button back,upd_el,del_btn;
    EditText name,lastname,address,phone,email;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        back = (Button) findViewById(R.id.back2);
        upd_el = (Button) findViewById(R.id.upd_element);
        del_btn = (Button) findViewById(R.id.del_btn);
        name= (EditText) findViewById(R.id.name);
        lastname= (EditText) findViewById(R.id.lastname);
        address= (EditText) findViewById(R.id.address);
        email= (EditText) findViewById(R.id.email);
        phone= (EditText) findViewById(R.id.phone);

        Intent i = getIntent();
        id = i.getLongExtra("id",0);
        name.setText(i.getStringExtra("name"));
        lastname.setText(i.getStringExtra("lastname"));
        phone.setText(i.getStringExtra("phone"));
        address.setText(i.getStringExtra("address"));
        email.setText(i.getStringExtra("email"));

        upd_el.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() > 0 && lastname.getText().toString().length() > 0) {


                    Contact c = new Contact(getBaseContext());
                    c.open();
                    c.updateContact(id, name.getText().toString(), lastname.getText().toString(), address.getText().toString(), email.getText().toString(), phone.getText().toString());
                    Toast.makeText(getBaseContext(), "Elemento Actualizado!!", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(getBaseContext(), "Error!!", Toast.LENGTH_LONG).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditActivity.this);

                builder.setTitle(" - Confirmar - ");
                builder.setMessage("Estas seguro que deseas eliminar ?");

                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Contact c = new Contact(getBaseContext());
                        c.open();
                        c.deleteContact(id);
                        finish();
                        dialog.dismiss();
                        Toast.makeText(getBaseContext(), "Elemento eliminado !!", Toast.LENGTH_LONG).show();

                    }

                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }
}
