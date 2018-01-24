package bean;

import ejb.TodoFacade;
import entity.Todo;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    
    public void resetSelectedTodo(){
        selectedTodo = new ArrayList<Todo>();
    }
    
    public void resetSelectedFinishing(){
        selectedFinishing = new ArrayList<Todo>();
    }
    
    // 選択されたtodoを代入する
    public void replica(){
        if(selectedTodo.size() > 0){
            todo = selectedTodo.get(0);
            System.out.println("replica is running");
        }
        System.out.println("replica size : "+ selectedTodo.size());
        
    }
    
    public void replicaFinishing(){
        if(selectedTodo.size() > 0){
            todo = selectedFinishing.get(0);
            System.out.println("replicaFinishing is running");
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
        mb.todoCreateMessage();
        System.out.println("todo userId : "+todo.getOwner()+" udm userId : "+udm.getUser().getUserId());
        System.out.println("todoを作成しました : "+todo.getLabel());
    }
    
    // 変更
    public void editTodo(){
        tf.edit(todo);
        mb.todoUpdateMessage();
        System.out.println("次のtodoを変更しました : "+todo.getLabel());
    }
    
    // 削除
    public void deleteTodo(){
        if(selectedTodo.size() > 0){
            for(Todo t : selectedTodo){
            tf.remove(t);
            System.out.println("次のtodoを削除しました : "+t.getLabel());
            }
            //メッセージの表示
            mb.todoDeleteMessage();
            
        }else{
            mb.todoDeleteErrorMessage();
        }
        
    }
    
    public void deleteFinishingTodo(){
        if(selectedFinishing.size() > 0){
            for(Todo t : selectedFinishing){
            tf.remove(t);
            System.out.println("次のtodoを削除しました : "+t.getLabel());
            }
            //メッセージの表示
            mb.todoDeleteMessage();
            
        }else{
            mb.todoDeleteErrorMessage();
            System.out.println("削除対象がありません");
        }
        
    }
    
    //完了
    public void finishTodo(){
        if(selectedTodo.size() > 0){
            for(Todo t : selectedTodo){
                t.setFinishing(true);
                tf.edit(t);
                System.out.println("次のtodoを完了済に変更しました : "+t.getLabel());
            }
            
            mb.todoUpdateMessage();
            
        }else{
            mb.todoDeleteErrorMessage();
        }
        
    }
    
    public void unFinishTodo(){
        if(selectedFinishing.size() > 0){
            for(Todo t : selectedFinishing){
                t.setFinishing(false);
                tf.edit(t);
                System.out.println("次のtodoを未完了に変更しました : "+t.getLabel());
            }
            
            mb.todoUpdateMessage();
            
        }else{
            mb.todoDeleteErrorMessage();
        }
        
    }
    
    
    
    
    
    
    // 進行中のTodo情報を取得する
    public List<Todo> getGoingTodo(){
        //System.out.println("GoindTodo is running");
        return tf.findByGoindTodo();
    }
    
    // 完了したTodo情報を取得する
    public List<Todo> getFinishingTodo(){
        //System.out.println("FinishingTodo is running");
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
        
        System.out.println("!!!セットされたデータ : "+selectedFinishing.size());
        this.selectedFinishing = selectedFinishing;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
    
}
