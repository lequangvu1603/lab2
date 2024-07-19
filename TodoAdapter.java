package fpt.vulq.demo02adr2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHoler> {
private List<Todo> todoList;//list
private OnItemClickListener listener;
interface  OnItemClickListener{
    void  onDeleteClick (int position);
    void onEditClick (int position);
    void onStatusChange (int position,boolean isDone);
}
public void setOnItemClickListener (OnItemClickListener listener){
    this.listener= listener;
}
public  TodoAdapter(List<Todo> todoList){// ham khoi tao
this.todoList = todoList;
}
    @NonNull
    @Override
    public TodoViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.demo21_item_view,parent,false);
    return new TodoViewHoler(view,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHoler holder, @SuppressLint("RecyclerView") int position) {
Todo currentTodo = todoList.get(position);// lay ve vi tri hien tai
        // dien du lieu vao truong title
        holder.tvTodoName.setText(currentTodo.getTitle());
        // dien du lieu vao truong checkbox
        holder.checkBox.setChecked(currentTodo.getStatus()==1);
        // xu ly su kien checkbox
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(listener!=null){
                    listener.onStatusChange(position,isChecked);
                }
            }
        });
    }
// lay tong so item
    @Override
    public int getItemCount() {
        return todoList.size();
    }
    public static class TodoViewHoler extends RecyclerView.ViewHolder{
TextView tvTodoName;
CheckBox checkBox;
Button btnEdit,btnDelete;


        public TodoViewHoler(@NonNull View itemView,final  OnItemClickListener listener) {
            super(itemView);
            //anh xa
            tvTodoName = itemView.findViewById(R.id.demo21_item_tvToDoName);
            btnEdit=itemView.findViewById(R.id.demo21_item_BtnEdit);
            btnDelete=itemView.findViewById(R.id.demo21_item_btnDelete);
            checkBox=itemView.findViewById(R.id.demo21_item_checkBox);
            //xu ly su kien delete
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int pos = getAdapterPosition();// lay ve vi tri con xoa
                        if (pos!=RecyclerView.NO_POSITION){
                            listener.onDeleteClick(pos);
                        }
                    }
                }
            });
            //xu ly su kien edit
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null){
                        int pos =getAdapterPosition();// lay ve vi tri con edit
                        if (pos!=RecyclerView.NO_POSITION){
                            listener.onEditClick(pos);
                        }
                    }
                }
            });
        }
    }
}
