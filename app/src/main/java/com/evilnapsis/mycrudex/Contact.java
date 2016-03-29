package com.evilnapsis.mycrudex;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by evilnapsis on 3/29/16.
 */
public class Contact {
    public long id;
    public String name;
    public String lastname;
    public String address;
    public String email;
    public String phone;

    public long getId() {
        return id;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return name + " "+lastname;
    }
    private SQLiteDatabase database;
    private Database dbHelper;
    private String[] allColumns = { Database.COLUMN_ID,
            Database.COLUMN_NAME,
            Database.COLUMN_LASTNAME,
            Database.COLUMN_ADDRESS,
            Database.COLUMN_EMAIL,
            Database.COLUMN_PHONE};

    public Contact(Context context) {
        dbHelper = new Database(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Contact createContact(String name,String lastname, String address, String email, String phone) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_NAME, name);
        values.put(Database.COLUMN_LASTNAME, lastname);
        values.put(Database.COLUMN_ADDRESS, address);
        values.put(Database.COLUMN_EMAIL, email);
        values.put(Database.COLUMN_PHONE, phone);
        long insertId = database.insert(Database.TABLE_CONTACTS, null,
                values);
        Cursor cursor = database.query(Database.TABLE_CONTACTS,
                allColumns, Database.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Contact newContact= cursorToContact(cursor);
        cursor.close();
        return newContact;
    }

    public void updateContact(long id, String name,String lastname, String address, String email, String phone) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_NAME, name);
        values.put(Database.COLUMN_LASTNAME, lastname);
        values.put(Database.COLUMN_ADDRESS, address);
        values.put(Database.COLUMN_EMAIL, email);
        values.put(Database.COLUMN_PHONE, phone);
        String where = "id=?";
        String[] whereargs = new String[]{String.valueOf(id)};
        long insertId = database.update(Database.TABLE_CONTACTS,
                values,where,whereargs );
        Cursor cursor = database.query(Database.TABLE_CONTACTS,
                allColumns, Database.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        cursor.close();

    }

    public void deleteContact(long id) {
        System.out.println("Contact deleted with id: " + id);
        database.delete(Database.TABLE_CONTACTS, Database.COLUMN_ID
                + " = " + id, null);
    }

    public List<Contact> getAll() {
        List<Contact> comments = new ArrayList<Contact>();

        Cursor cursor = database.query(Database.TABLE_CONTACTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Contact contact= cursorToContact(cursor);
            comments.add(contact);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Contact cursorToContact(Cursor cursor) {
        Contact c = new Contact(null);
        c.id = cursor.getLong(0);
        c.name = cursor.getString(1);
        c.lastname = cursor.getString(2);
        c.address = cursor.getString(3);
        c.phone = cursor.getString(4);
        c.email = cursor.getString(5);
        return c;
    }
}
