package tw.org.iii.brad.brad11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private MyHelper myHelper;
    private EditText keyword;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        keyword = findViewById(R.id.keyword);
        tv = findViewById(R.id.data);

        myHelper = new MyHelper(this, "iii", null,1);
        db = myHelper.getReadableDatabase();
    }


    public void query(View view) {
        // SELECT * FROM cust
        Cursor cursor = db.query("cust", null,
                "cname like ? or tel like ? or birthday like ?",
                new String[]{"%"+keyword.getText().toString()+"%",
                        "%"+keyword.getText().toString()+"%",
                        "%"+keyword.getText().toString()+"%"},
                null,null,null);
        //Log.v("brad", "col count:" + cursor.getColumnCount());

        StringBuffer sb = new StringBuffer();
        while (cursor.moveToNext()){
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String cname = cursor.getString(cursor.getColumnIndex("cname"));
            String tel = cursor.getString(cursor.getColumnIndex("tel"));
            String birthday = cursor.getString(cursor.getColumnIndex("birthday"));
            String row = id + ":" + cname + ":" + tel +":" + birthday;
            sb.append(row + "\n");
        }
        tv.setText(sb);
    }

    public void insert(View view) {
        //db.execSQL("INSERT INTO cust (cname,tel,birthday) VALUES ('brad','123','1999-01-02')");
        ContentValues data = new ContentValues();
        data.put("cname", "Tony");
        data.put("tel", "456");
        data.put("birthday", "1988-02-03");
        db.insert("cust", null, data);
        query(null);
    }

    public void del(View view) {
        // DELETE FROM cust WHERE id = 1 and cname = 'brad'
        db.delete("cust",
                "id = ? and cname = ?", new String[]{"1","brad"} );
        query(null);
    }

    public void update(View view) {
        // UPDATE cust SET cname='Eric', tel='789' WHERE id = 3;
        ContentValues data = new ContentValues();
        data.put("cname", "Eric");
        data.put("tel", "789");
        db.update("cust", data,
                "id = ?", new String[]{"3"} );
        query(null);
    }
}
