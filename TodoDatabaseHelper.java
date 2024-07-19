package fpt.vulq.demo02adr2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TodoDatabaseHelper extends SQLiteOpenHelper {
    public TodoDatabaseHelper(@Nullable Context context) {
        super(context, "todoList.db ", null, 1); // tao csdl
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
String CREATE_TABLE = "CREATE TABLE todos(\n" +
        "id integer PRIMARY key AUTOINCREMENT,\n" +
        "  title text,\n" +
        "  content text ,\n" +
        "  date text,\n" +
        "  type text,\n" +
        "  status integer\n" +
        ");";
db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS tods");
    }
}
