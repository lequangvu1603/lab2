package fpt.vulq.demo02adr2;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtTitle, txtContent, txtDate, txtType;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private TodoDAO todoDAO;
    private List<Todo> todoList;
    private Todo currentEditingTodo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo2_main);
        txtTitle = findViewById(R.id.demo21TxtTitle);
        txtContent = findViewById(R.id.demo21TxtContent);
        txtDate = findViewById(R.id.demo21TxtDate);
        txtType = findViewById(R.id.demo21TxtType);
        btnAdd = findViewById(R.id.demo21BtnAdd);
        recyclerView = findViewById(R.id.demo21RecyclerView);
// tao moi cac doi tuong
        todoDAO = new TodoDAO(this);
        todoList = todoDAO.getAllTodos();// lay ve danh sanh
        adapter = new TodoAdapter(todoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if (currentEditingTodo==null){
    addTodo();
}
else {
    updateTodo();
}
            }
        });

        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {

                deleteTodo(position);
            }

            @Override
            public void onEditClick(int position) {

                editTodo(position);
            }

            @Override
            public void onStatusChange(int position, boolean isDone) {

              updateTodoStatus(position,isDone);
            }
        });
    }
    private void updateTodoStatus(int pos,boolean isDone){
        Todo todo = todoList.get(pos);
        todo.setStatus(isDone?1:0);
        todoDAO.updateTodoStatus(todo.getId(),todo.getStatus());
//        adapter.notifyItemChanged(pos);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                adapter.notifyItemChanged(pos);
            }
        });
        Toast.makeText(this, "Update thanh cong ", Toast.LENGTH_SHORT).show();
    }
    private  void addTodo(){
        String title = txtTitle.getText().toString();
        String content = txtContent.getText().toString();
        String date = txtDate.getText().toString();
        String type = txtType.getText().toString();
        Todo todo = new Todo(0,title,content,date,type,0);
        todoDAO.addTodo(todo);
        todoList.add(0,todo);
        adapter.notifyItemInserted(0);
        recyclerView.scrollToPosition(0);
        clearFields();
    }
    private  void updateTodo(){
        String title = txtTitle.getText().toString();
        String content = txtContent.getText().toString();
        String date = txtDate.getText().toString();
        String type = txtType.getText().toString();
        currentEditingTodo.setTitle(title);
        currentEditingTodo.setContent(content);
        currentEditingTodo.setDate(date);
        currentEditingTodo.setType(type);
        todoDAO.updateTodo(currentEditingTodo);
        int pos = todoList.indexOf(currentEditingTodo);
        adapter.notifyItemChanged(pos);
        currentEditingTodo= null;
        btnAdd.setText("Add");
        clearFields();
    }
    private  void editTodo(int pos){
        currentEditingTodo= todoList.get(pos);
        txtTitle.setText(currentEditingTodo.getTitle());
        txtContent.setText(currentEditingTodo.getContent());
        txtDate.setText(currentEditingTodo.getDate());
        txtType.setText(currentEditingTodo.getType());
        btnAdd.setText("Update");
    }
    private  void deleteTodo(int pos){
        Todo todo = todoList.get(pos);
        todoDAO.deleteTodo(todo.getId());
        todoList.remove(pos);
        adapter.notifyItemRemoved(pos);
    }
    private  void  clearFields(){
        txtTitle.setText("");
        txtContent.setText("");
        txtDate.setText("");
        txtType.setText("");
    }
}