package faustina.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;


public class database extends ActionBarActivity {

    private SQLiteDatabase db;
    EditText e1, e2, e3,e4,e5,e6;
    Button b1,b2;
    int i=0;
    int len,flag=1;
    String abc;
    String[] res=new String[10];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        e1=(EditText)findViewById(R.id.name1);
        e2=(EditText)findViewById(R.id.phone1);
        e3=(EditText)findViewById(R.id.name2);
        e4=(EditText)findViewById(R.id.phone2);
        e5=(EditText)findViewById(R.id.name3);
        e6=(EditText)findViewById(R.id.phone3);
        b1=(Button)findViewById(R.id.save);
        b2=(Button)findViewById(R.id.show);
        db = openOrCreateDatabase("contacts", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS contactsdata(name TEXT,mobile TEXT)");
        b1.setOnClickListener(new B());
        b2.setOnClickListener(new B());
    }
    class B implements View.OnClickListener {
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.save:
                    String s1, s2, s3, s4, s5, s6, sql1, sql2, sql3;


                    s1 = e1.getText().toString();
                    s2 = e2.getText().toString();
                    s3 = e3.getText().toString();
                    s4 = e4.getText().toString();
                    s5 = e5.getText().toString();
                    s6 = e6.getText().toString();
                    if (s2.length() == 10 && s4.length() == 10 && s6.length() == 10) {
                        sql1 = "Insert into contactsdata values('" + s1 + "','" + s2 + "')";
                        db.execSQL(sql1);
                        sql2 = "Insert into contactsdata values('" + s3 + "','" + s4 + "')";
                        db.execSQL(sql2);
                        sql3 = "Insert into contactsdata values('" + s5 + "','" + s6 + "')";
                        db.execSQL(sql3);
                        Toast.makeText(getApplicationContext(), "Added Succesfully", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "One of the contacts entered is invalid", Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.show:
                    String sql,str="";
                    sql="Select * FROM contactsdata";
                    Cursor c=db.rawQuery(sql,null);
                    Toast.makeText(getApplicationContext(),"Contact details:", Toast.LENGTH_LONG).show();
                    while (c.moveToNext())
                    {
                        str+=c.getString(0)+":"+c.getString(1)+"\n";

                        if(flag==1)
                        {
                            len=str.length();
                            abc=str.substring(len-10,len);
                            flag=2;
                        }

                    }
                    Toast.makeText(getApplicationContext(),str, Toast.LENGTH_LONG).show();
                    break;

            }
        }
    }

}
