package bean;

import ejb.TodoFacade;
import entity.Todo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.inject.Inject;

@Named(value = "todoBean")
@SessionScoped
public class todoBean implements Serializable{
    // 入力されるデータを格納する
    private Todo todo;
    
    // 選んだ進行中のtodoが入る
    private List<Todo> selectedTodo;
    
    // 選んだ完了したtodoが入る
    private List<Todo> selectedFinishing;
    
    @EJB
    TodoFacade tf;
    @Inject
    UserDataManager udm;
    @Inject
    ManagedBean mb;
    
    //todoの初期化
    public void resetTodo(){
        todo = new Todo();
        System.out.println("reset is running");
    }
    
    // 選択されたtodoを代入する
    public void replica(){
        if(selectedTodo.size() > 0){
            todo = selectedTodo.get(0);
            System.out.println("replica is running");
        }
        System.out.println("replica size : "+ selectedTodo.size());
        
    }
    
    /* データ操作処理 */
    // 新規作成
    public void createTodo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        todo.setTodoId("tod"+sdf.format(new Date()));
        todo.setOwner(udm.getUser());
        todo.setFinishing(false);
        tf.create(todo);
    }
    
    // 変更
    public void editTodo(){
        tf.edit(todo);
        mb.todoUpdateMessage();
        System.out.println("editTodo successfuly");
    }
    
    
    
    
    
    
    // 進行中のTodo情報を取得する
    public List<Todo> getGoingTodo(){
        System.out.println("GoindTodo is running");
        return tf.findByGoindTodo();
    }
    
    // 完了したTodo情報を取得する
    public List<Todo> getFinishingTodo(){
        System.out.println("FinishingTodo is running");
        return tf.findByFinishingTodo();
    }

    public List<Todo> getSelectedTodo() {
        return selectedTodo;
    }

    public void setSelectedTodo(List<Todo> selectedTodo) {
        System.out.println("setSelectedTodo is running");
        System.out.println("!!!セットされたデータ : "+selectedTodo.size());
        this.selectedTodo = selectedTodo;
    }

    public List<Todo> getSelectedFinishing() {
        return selectedFinishing;
    }

    public void setSelectedFinishing(List<Todo> selectedFinishing) {
        this.selectedFinishing = selectedFinishing;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
    
}
