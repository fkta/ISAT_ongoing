package bean;

import ejb.TodoFacade;
import entity.Todo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

@Named(value = "todoBean")
@RequestScoped
public class todoBean {
    // 入力されるデータを格納する
    private Todo todo;
    
    // 選んだ進行中のtodoが入る
    private List<Todo> selectedTodo;
    
    // 選んだ完了したtodoが入る
    private List<Todo> selectedFinishing;
    
    @EJB
    TodoFacade tf;
    
    // 選択されたtodoを代入する
    public void replica(){
        if(selectedTodo.size() > 0){
            todo = selectedTodo.get(0);
            System.out.println("replica is running");
        }
        System.out.println("replica size : "+ selectedTodo.size());
        
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
        selectedTodo = new ArrayList<Todo>();
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
