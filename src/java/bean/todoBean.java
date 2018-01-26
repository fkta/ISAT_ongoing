package bean;

import ejb.DepartmentFacade;
import ejb.TodoFacade;
import ejb.TodoManageFacade;
import ejb.UserDataFacade;
import entity.Department;
import entity.Todo;
import entity.TodoManage;
import entity.UserData;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.inject.Inject;
import org.primefaces.model.DualListModel;

@Named(value = "todoBean")
@SessionScoped
public class todoBean implements Serializable{
    private List<Todo> goingTodoList;
    
    // 入力されるデータを格納する
    private Todo todo;
    
    // 選んだ進行中のtodoが入る
    private List<Todo> selectedTodo;
    
    // 選んだ完了したtodoが入る
    private List<Todo> selectedFinishing;
    
    // 選んだ期限切れのtodoが入る
    private List<Todo> selectedExpired;
    
    // 共有チェック
    private boolean share;
    protected List<UserData> oldTarget;
    
    // セレクトボックスの中身
    private List<SelectItem> classList;
    private String pickItem;
    
    // PickList
    private DualListModel<UserData> users;
    
    @EJB
    TodoFacade tf;
    @EJB
    DepartmentFacade df;
    @EJB
    UserDataFacade udf;
    @EJB
    TodoManageFacade tmf;
    
    @Inject
    UserDataManager udm;
    @Inject
    ManagedBean mb;
    
    
    @PostConstruct
    public void init() {
        share = false;
        
        
        //セレクトメニューの中身を作る
        classList = new ArrayList<SelectItem>();
        for(Department d : df.findAll()){
            if(d.getDepartmentCode().toString().equals("T")){
                SelectItem item = new SelectItem("teacher","先生");
                classList.add(item);
            }else if(!d.getDepartmentCode().toString().equals("A")){
                for(int i = 1; i <= d.getYears(); i++){
                String outputValue;
                outputValue = String.valueOf(d.getDepartmentCode());
                outputValue = outputValue.concat(String.valueOf(i)+d.getClassCode());
                System.out.println("outputValue : "+outputValue);
                SelectItem item = new SelectItem(outputValue,outputValue);
                classList.add(item);
                }
            
            }
            
        }
        //ここまで
        
        /* PickListの初期化 */
        List<UserData> source = new ArrayList<UserData>();
        List<UserData> target = new ArrayList<UserData>();
        
        users = new DualListModel<UserData>(source,target);
        System.out.println("users : "+users.getTarget().toString());
        
        System.out.println("init");
    }
    
    /* 進行中のtodoリストを更新する */
    public void updateGoingTodoList(){
        goingTodoList = new ArrayList<Todo>();
        
        /* データの取得処理 */
        if(tf.findByGoindTodo(udm.getUser()).size() > 0){
            for(Todo t : tf.findByGoindTodo(udm.getUser())){
                goingTodoList.add(t);
            }
        }
        
        if(tmf.findByUserId(udm.getUser().getUserId()).size() > 0){
            for(TodoManage tm : tmf.findByUserId(udm.getUser().getUserId())){
                Todo t = new Todo();
                t = tf.findBySharedTodo(tm.getTodoManagePK().getTodoId()).get(0);
                goingTodoList.add(t);
            }
        }
        
    }
    
    
    /*PickListを初期化する*/
    public void resetPickList(){
        /* PickListの初期化 */
        List<UserData> source = new ArrayList<UserData>();
        List<UserData> target = new ArrayList<UserData>();
        
        users = new DualListModel<UserData>(source,target);
    }
    
    /* PickListを更新する */
    public void updatePickList(){
        System.out.println("pickItem : "+pickItem);
        
        List<UserData> source = new ArrayList<UserData>();
        
        switch(pickItem){
            case "none" :
                break;
            
            case "teacher":
                for(UserData ud : udf.findByUserType(pickItem)){
                    // ログインしているユーザは表示しない
                    if(!udm.getUser().getUserId().equals(ud.getUserId())){
                        source.add(ud);
                        System.out.println("追加したデータ : "+ud.getName());
                    }

                }

                users.setSource(source);
                break;
                
            default :
                // 学科コードとクラスコードをもとに生徒を洗い出す
                List<Department> dptList;
                String classCode = pickItem.substring(2);

                // 学年を1引く(処理に向けて)
                int gakunen = Integer.parseInt(pickItem.substring(1, 2));
                gakunen = gakunen - 1;

                dptList = df.findByClass(pickItem.charAt(0),classCode);
                System.out.println("取得した学科名 : "+dptList.get(0).getDepartmentName());

                //取得した学科コードに属する学生のデータを取得する
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                int nowNendo;


                //現在の月が4月未満なら現在年から1を引く
                if(Integer.parseInt(sdf.format(new Date()).substring(4)) < 4){
                    //現在年から1を引く
                    nowNendo = Integer.parseInt(sdf.format(new Date()).substring(0,4));
                    nowNendo = nowNendo - 1;


                }else{
                    //現在年をそのまま代入する
                    nowNendo = Integer.parseInt(sdf.format(new Date()).substring(0,4));

                }

                for(UserData ud : udf.findByDepartment(dptList.get(0),nowNendo,gakunen)){
                    // ログインしているユーザは表示しない
                    if(!udm.getUser().getUserId().equals(ud.getUserId())){
                        source.add(ud);
                        System.out.println("データ名 : "+ud.getName());
                    }
                    
                    
                    
                }

                users.setSource(source);
                
                break;
        }
        
        if(users.getTarget().size() > 0){
            System.out.println("target size: "+users.getTarget().size());
        }
        
        System.out.println("SourceSize : "+users.getSource().size());
        
        
    }
    
    /* PickList End */
    
    
    //todoの初期化
    public void resetTodo(){
        todo = new Todo();
        share = false;
        System.out.println("reset is running");
    }
    
    public void resetSelectedTodo(){
        selectedTodo = new ArrayList<Todo>();
    }
    
    public void resetSelectedFinishing(){
        selectedFinishing = new ArrayList<Todo>();
    }
            
    public void resetSelectedExpired(){
        selectedExpired = new ArrayList<Todo>();
    }
    
    
    // 選択されたtodoを代入する
    public void replica(){
        if(selectedTodo.size() > 0){
            todo = selectedTodo.get(0);
            System.out.println("共有 : "+tmf.findByTodoId(todo.getTodoId()).size());
            
            
            
            /* 共有されているかどうかの確認 */
            if(tmf.findByTodoId(todo.getTodoId()).size() > 0){
                // コピーを生成する
                oldTarget = users.getTarget();
                
                List<UserData> target = new ArrayList<UserData>();
                
                for(TodoManage tm : tmf.findByTodoId(todo.getTodoId())){
                    UserData ud = udf.findUserId(tm.getTodoManagePK().getUserId());
                    target.add(ud);
                }
                
                // 取得した共有者一覧をピックリストにセットする
                List<UserData> source = new ArrayList<UserData>();
                users = new DualListModel<UserData>(source, target);
                System.out.println("共有者情報取得完了");
                share = true;
            }else{
                resetPickList();
                share = false;
                oldTarget = new ArrayList<UserData>();
            }
            /* 共有処理 end */
            
            
            System.out.println("replica is running");
        }
        System.out.println("replica size : "+ selectedTodo.size());
        
       ; 
    }
    
    public void replicaFinishing(){
        if(selectedTodo.size() > 0){
            todo = selectedFinishing.get(0);
            
            /* 共有されているかどうかの確認 */
            if(tmf.findByTodoId(todo.getTodoId()).size() > 0){
                // コピーを生成する
                oldTarget = users.getTarget();
                
                List<UserData> target = new ArrayList<UserData>();
                
                for(TodoManage tm : tmf.findByTodoId(todo.getTodoId())){
                    UserData ud = udf.findUserId(tm.getTodoManagePK().getUserId());
                    target.add(ud);
                }
                
                // 取得した共有者一覧をピックリストにセットする
                List<UserData> source = new ArrayList<UserData>();
                users = new DualListModel<UserData>(source, target);
                System.out.println("共有者情報取得完了");
                share = true;
            }else{
                resetPickList();
                oldTarget = new ArrayList<UserData>();
                share = false;
            }
            /* 共有処理 end */
            
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
        
        /* 共有処理 */
        //共有者がいるかを確認
        if(share == true && users.getTarget().size() > 0){
            for(UserData ud : users.getTarget()){
                TodoManage tm = new TodoManage(todo.getTodoId(), ud.getUserId());
                tmf.create(tm);
                System.out.println("次のユーザに共有設定を行いました : "+ud.getName());
            }
            
            //PickListを初期化する
            resetPickList();
            
            mb.todoShareMessage();
        }
        /* 共有処理終了 */
        
        
    }
    
    // 変更
    public void editTodo(){
        tf.edit(todo);
        mb.todoUpdateMessage();
        System.out.println("次のtodoを変更しました : "+todo.getLabel());
        System.out.println("old size : "+oldTarget.size());
        
        /* 共有処理 */
        //共有者がいるかを確認
        if(share == true && users.getTarget().size() > 0){
            
            // 新規作成の処理
            for(UserData ud : users.getTarget()){
                
                Boolean flag = false;
                
                for(UserData old : oldTarget){
                    //新規登録処理 (新たに共有者として設定したユーザがいる場合)
                    if(ud.getUserId().equals(old.getUserId())){
                        // 過去の共有者と一致した場合
                        flag = true;
                    }
                    
                }
                
                // 一致しない共有者が存在した場合
                if(flag == false){
                    TodoManage tm = new TodoManage(todo.getTodoId(), ud.getUserId());
                    tmf.create(tm);
                    System.out.println("次のユーザに共有設定を行いました : "+ud.getName());
                }
                
            }
            // 新規作成処理 end
            
            // 削除処理
            for(UserData old : oldTarget){
                Boolean flag = false;


                // 新しい共有者リストの中にすでに登録済みのユーザがいるかを確認する
                for(UserData ud : users.getTarget()){
                    if(old.getUserId().equals(ud.getUserId())){
                        flag = true;
                    }
                }
                
                
                // 共有者を削除する処理
                if(!flag){
                    TodoManage tm = new TodoManage(todo.getTodoId(), old.getUserId());
                    tmf.remove(tm);
                    System.out.println("次のユーザを共有者から除外しました : "+old.getName());
                    
                }
            }
            
            
            //PickListを初期化する
            resetPickList();
            
            mb.todoShareMessage();
        }
        /* 共有処理終了 */
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
    
    public void deleteExpiredTodo(){
        if(selectedExpired.size() > 0){
            for(Todo t : selectedExpired){
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
        return tf.findByGoindTodo(udm.getUser());
    }
    
    // 完了したTodo情報を取得する
    public List<Todo> getFinishingTodo(){
        //System.out.println("FinishingTodo is running");
        return tf.findByFinishingTodo();
    }

    // 期限切れのTodo情報を取得する
    public List<Todo> getExpiredTodo(){
        return tf.findByExpiredTodo();
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

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public List<SelectItem> getClassList() {
        return classList;
    }

    public void setClassList(List<SelectItem> classList) {
        this.classList = classList;
    }

    public String getPickItem() {
        return pickItem;
    }

    public void setPickItem(String pickItem) {
        this.pickItem = pickItem;
    }

    public DualListModel<UserData> getUsers() {
        return users;
    }

    public void setUsers(DualListModel<UserData> users) {
        this.users = users;
    }

    public List<Todo> getSelectedExpired() {
        return selectedExpired;
    }

    public void setSelectedExpired(List<Todo> selectedExpired) {
        this.selectedExpired = selectedExpired;
    }

    public List<Todo> getGoingTodoList() {
        return goingTodoList;
    }

    public void setGoingTodoList(List<Todo> goingTodoList) {
        this.goingTodoList = goingTodoList;
    }
    
}
