package fpt.vulq.demo02adr2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TodoDAO {

    private SQLiteDatabase db;

    public TodoDAO(Context context) {// ham khoi tao
        TodoDatabaseHelper dbhelper = new TodoDatabaseHelper(context); // goi ham tao csdl
        db = dbhelper.getWritableDatabase();

    }

    // ham them du lieu
    public long addTodo(Todo todo) {
        ContentValues values = new ContentValues(); // chua du lieu
        values.put("title", todo.getTitle()); // dua title vao doi tuong
        values.put("content", todo.getContent());
        values.put("date", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        // thuc thi insert du lieu
        return db.insert("todos", null, values);
    }
    // lay du lieu

    public List<Todo> getAllTodos() {
        List<Todo> todos = new ArrayList<>();
        // tao 1 con tro
        Cursor cursor = db.query("todos", null, null,
                null, null, null, "id DESC");
        if ((cursor.moveToFirst()))// di chuyen ve ban ghi dau tien
        {
            do {
                // tao 1 doi tuong
            @SuppressLint("Range") Todo todo = new Todo(
        cursor.getInt(cursor.getColumnIndex("id")),
        cursor.getString(cursor.getColumnIndex("title")),
        cursor.getString(cursor.getColumnIndex("content")),
        cursor.getString(cursor.getColumnIndex("date")),
        cursor.getString(cursor.getColumnIndex("type")),
        cursor.getInt(cursor.getColumnIndex("status"))
);
            // them doi tuong vao danh sach
                todos.add(todo);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return todos;
    }
    // ham update trang thai
    public  void updateTodoStatus(int id , int status){
        ContentValues values = new ContentValues();
        values.put("status",status);
        db.update("todos",values,"id=?",new String[]{String.valueOf(id)});
    }
    // ham update bang du lieu
    public  void  updateTodo(Todo todo){
        ContentValues values = new ContentValues();// doi tuong chua du lieu con update
        values.put("title", todo.getTitle()); // dua title vao doi tuong
        values.put("content", todo.getContent());
        values.put("date", todo.getDate());
        values.put("type", todo.getType());
        values.put("status", todo.getStatus());
        db.update("todos",values,"id=?",new String[]{String.valueOf(todo.getId())});
    }
// xoa du lieu
    public  void  deleteTodo (int id){
        db.delete("todos","id=?",new String[]{String.valueOf(id)});
    }
}
