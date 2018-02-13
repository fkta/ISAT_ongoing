package bean;

import ejb.DepartmentFacade;
import ejb.ScheduleFacade;
import ejb.ScheduleManageFacade;
import ejb.UserDataFacade;
import entity.Department;
import entity.Schedule;
import entity.ScheduleManage;
import entity.ScheduleManagePK;
import entity.UserData;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DualListModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named(value = "scheduleBean2")
@SessionScoped
public class ScheduleBean2 implements Serializable{
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    //共有チェックボックス
    private boolean share;
    
    //入力可否
    private boolean disable;
    
    // PickList
    private DualListModel<UserData> users;
    protected List<UserData> oldTarget;
    
    // セレクトボックスの中身
    private List<SelectItem> classList;
    private String pickItem;
    
    // メッセージ 
    protected FacesContext context = FacesContext.getCurrentInstance();
    
 
    
    
    @EJB
    ScheduleFacade sf;
    @EJB
    ScheduleManageFacade smf;
    @EJB
    UserDataFacade udf;
    @EJB
    DepartmentFacade df;
    @Inject
    UserDataManager udm;
    @Inject
    ManagedBean mb;
 
    
    public void resetEvent(){
        event = null;
        System.out.println("resetEvent is running");
    }
    
    public void resetPickList(){
        /* PickListの初期化 */
        List<UserData> source = new ArrayList<UserData>();
        List<UserData> target = new ArrayList<UserData>();
        
        users = new DualListModel<UserData>(source,target);
        
    }
    
    @PostConstruct
    public void init() {
        
        share = false;
        
        //セレクトメニューの中身を作る
        classList = new ArrayList<SelectItem>();
        for(Department d : df.findAll()){
            System.out.println("department : "+ d.getDepartmentName());
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
        
        
        eventModel = new DefaultScheduleModel();
        
        
        for(Schedule s : sf.searchSchedule(udm.getUser())){
            ScheduleEvent master;
            DefaultScheduleEvent ev = new DefaultScheduleEvent();
            ev.setTitle(s.getTitle());
            ev.setStartDate(s.getSDate());
            ev.setEndDate(s.getEDate());
            ev.setDescription(s.getContent());
            ev.setStyleClass(s.getScheduleId());
            ev.setAllDay(s.getAllday());
            ev.setData(s.getOwner());
            master = ev;
            eventModel.addEvent(master);
        }
        
        System.out.println("予定数 : "+sf.searchSchedule(udm.getUser()).size());
        
        // 1件以上共有された予定がある場合
        if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
            
            for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                // データの取得
                List<Schedule> s = new ArrayList<Schedule>();
                s = sf.findByScheduleId(sm.getScheduleManagePK().getScheduleId());
                
                ScheduleEvent master;
                DefaultScheduleEvent ev = new DefaultScheduleEvent();
                ev.setTitle(s.get(0).getTitle());
                ev.setStartDate(s.get(0).getSDate());
                ev.setEndDate(s.get(0).getEDate());
                ev.setDescription(s.get(0).getContent());
                ev.setData(s.get(0).getOwner());
                
                
                // StyleClassにScheduleIdを格納する
                ev.setStyleClass(s.get(0).getScheduleId());
                ev.setAllDay(s.get(0).getAllday());
                master = ev;
                eventModel.addEvent(master);
                
            }
        }
        // end
        
        System.out.println("共有されている予定数 : "+smf.findUserId(udm.getUser().getUserId()).size());
        
        /* 共有リストのデータを取得する */
        // 学生の一覧を取得する
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        for(UserData ud : udf.findByUserType("student")){
            
            String nendo = sdf.format(new Date());
            int now = Integer.parseInt(nendo.substring(0, 4));
            int month = Integer.parseInt(nendo.substring(4, 6));
            
            //年度変換する
            if(month < 4){
                now = now - 1;
            }
            
            // 学生の入学年度
            int year = ud.getEntranceYear();
            
            // 学年を算出して代入
            int gakunen = now - year;
            
        }*/
        /* ここまで */
        
        /* PickListの初期化 */
        List<UserData> source = new ArrayList<UserData>();
        List<UserData> target = new ArrayList<UserData>();
        
        users = new DualListModel<UserData>(source,target);
        System.out.println("init is running");
        
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
    
    public void outputTest(){
        System.out.println("ajax is running");
        
    }
    
    
    //ダイアログで終日を選んだ時に使用する
    public void changeCheckBox() throws ParseException{
        if(event.isAllDay()){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            
            String cDate = sdf.format(new Date());
            System.out.println("AllDay : "+event.isAllDay());
            cDate = sdf.format(event.getStartDate());
            cDate = cDate.substring(0,8);
            Date eDate = (sdf.parse(cDate.concat("235959")));
            
            // event変数を更新する(変わるのはEndDateのみ)
            DefaultScheduleEvent ev = new DefaultScheduleEvent();
            ev.setTitle(event.getTitle());
            ev.setStartDate(event.getStartDate());
            ev.setEndDate(eDate);
            ev.setDescription(event.getDescription());
            ev.setStyleClass(event.getStyleClass());
            ev.setAllDay(event.isAllDay());
            ScheduleEvent master = ev;
            event = master;
        }
        
    }
     
    
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent() throws ParseException {
        if(event.getId() == null){
            create();
            eventModel.addEvent(event);
            mb.scheduleAddMessage();
        }else{
            update();
            eventModel.updateEvent(event);
            mb.scheduleUpdateMessage();
        }
        event = new DefaultScheduleEvent();
    }
    
    public void removeEvent(){
        eventModel.deleteEvent(event);
        delete();
        mb.scheduleDeleteMessage();
        
        event = new DefaultScheduleEvent();
    }
    
    public void create() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        
        Schedule schedule = new Schedule();
        schedule.setScheduleId("sch"+sdf.format(new Date()));
        schedule.setTitle(event.getTitle());
        schedule.setOwner(udm.getUser());
        schedule.setContent(event.getDescription());
        schedule.setSDate(event.getStartDate());
        
        if(event.isAllDay()){
//            sdf.applyPattern("yyyy/MM/dd HH:mm:ss");

            String cDate = sdf.format(new Date());
            System.out.println("AllDay : "+event.isAllDay());
            cDate = sdf.format(event.getStartDate());
            cDate = cDate.substring(0,8);
            schedule.setEDate(sdf.parse(cDate.concat("235959")));
            
            // event変数を更新する(変わるのはEndDateとStyleClass)
            DefaultScheduleEvent ev = new DefaultScheduleEvent();
            ev.setTitle(event.getTitle());
            ev.setStartDate(event.getStartDate());
            ev.setEndDate(schedule.getEDate());
            ev.setDescription(event.getDescription());
            ev.setStyleClass(schedule.getScheduleId());
            ev.setData(schedule.getOwner());
            ev.setAllDay(event.isAllDay());
            ScheduleEvent master = ev;
            event = master;
            
            System.out.println("StyleClass : "+master.getStyleClass());
            
            schedule.setAllday(true);
        }else{
            // event変数を更新する(変わるのはStyleClassのみ)
            DefaultScheduleEvent ev = new DefaultScheduleEvent();
            ev.setTitle(event.getTitle());
            ev.setStartDate(event.getStartDate());
            ev.setEndDate(event.getEndDate());
            ev.setDescription(event.getDescription());
            ev.setStyleClass(schedule.getScheduleId());
            ev.setData(schedule.getOwner());
            ev.setAllDay(event.isAllDay());
            ScheduleEvent master = ev;
            event = master;
            
            schedule.setEDate(event.getEndDate());
            schedule.setAllday(false);
        }
        
        
        sf.create(schedule);
        
        /* 共有設定 */
        if(share){
            System.out.println("共有者登録 開始");
            for(UserData ud : users.getTarget()){
                
                if(!ud.getUserId().equals(udm.getUser().getUserId())){
                    ScheduleManage sm = new ScheduleManage();
                    ScheduleManagePK smPK = new ScheduleManagePK();
                    smPK.setScheduleId(schedule.getScheduleId());
                    smPK.setUserId(ud.getUserId());
                    sm.setScheduleManagePK(smPK);

                    System.out.println("target : "+users.getTarget().get(0).getUserId());
                    System.out.println("共有者 : "+ud.getName());
                    smf.create(sm);
                }
                
            }
            
        }
        
        /* 共有設定end */
        
        List<UserData> target = new ArrayList<UserData>();
        users.setTarget(target);
        System.out.println("スケジュール登録成功");
    }
    
    public void update() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Schedule schedule = new Schedule();
        schedule.setTitle(event.getTitle());
        schedule.setOwner(udm.getUser());
        schedule.setContent(event.getDescription());
        schedule.setSDate(event.getStartDate());
        schedule.setScheduleId(event.getStyleClass());
        
        if(event.isAllDay()){
            System.out.println("AllDay : "+event.isAllDay());
            String cDate = sdf.format(event.getStartDate());
            cDate = cDate.substring(0,8);
            schedule.setEDate(sdf.parse(cDate.concat("235959")));
            
            // event変数を更新する(変わるのはEndDateのみ)
            DefaultScheduleEvent ev = new DefaultScheduleEvent();
            ev.setTitle(event.getTitle());
            ev.setStartDate(event.getStartDate());
            ev.setEndDate(schedule.getEDate());
            ev.setDescription(event.getDescription());
            ev.setStyleClass(event.getStyleClass());
            ev.setAllDay(event.isAllDay());
            ScheduleEvent master = ev;
            event = master;
            
            System.out.println("StyleClass : "+master.getStyleClass());
            
            schedule.setAllday(true);
        }else{
            schedule.setEDate(event.getEndDate());
            schedule.setAllday(false);
        }
        
        /* 共有設定 */
        if(share){
            System.out.println("共有者登録 開始");
            // チェックされているが、targetリストが空の場合
            System.out.println("ターゲットサイズ : "+users.getTarget().size());
            
            
            for(UserData ud : users.getTarget()){
                boolean exist = false;
                
                for(UserData oldUd : oldTarget){
                    // ユーザが変更されたかの確認を行う　データベースの整合性を保つ処理
                    if(oldUd.getUserId().equals(ud.getUserId())){
                        //一致するユーザがいた場合
                       exist = true;
                       break;
                    }
                }
                
                if(exist == false){
                    // 登録されていないユーザがいた場合の新規作成処理
                    
                    if(!ud.getUserId().equals(udm.getUser().getUserId())){
                        ScheduleManage sm = new ScheduleManage(schedule.getScheduleId(), ud.getUserId());
                        smf.create(sm);
                        System.out.println("新たに共有情報を作成しました : "+ud.getUserId());
                    }
                    
                }
                
            }
            
            // 登録済みの共有情報を削除している場合
            for(UserData oldUd : oldTarget){
                boolean exist = false;
                for(UserData ud : users.getTarget()){
                    if(oldUd.getUserId().equals(ud.getUserId())){
                        exist = true;
                        break;
                    }
                }
                
                if(exist == false){
                    // 削除する
                    ScheduleManage sm = new ScheduleManage(schedule.getScheduleId(), oldUd.getUserId());
                    smf.remove(sm);
                    System.out.println("変更された共有データを削除しました : "+sm.getScheduleManagePK().getUserId());
                }
            }
            
        }else{
            // 共有のチェックをはずした場合
            for(UserData ud : users.getTarget()){
                ScheduleManage sm = new ScheduleManage(schedule.getScheduleId(), ud.getUserId());
                smf.remove(sm);
                System.out.println("次の共有データを削除しました : "+ud.getUserId());
            }
        }
        
        /* 共有設定end */
        
        
        System.out.println("UPDATE scheduleId : "+ event.getStyleClass() + "EDate : " + schedule.getEDate());
        sf.edit(schedule);
    }
    
    public void delete(){
        if(udm.getUser().getUserId().equals(event.getData().toString())){
            // 予定の作成者なら予定の削除を行う
            Schedule schedule = new Schedule();
            schedule.setScheduleId(event.getStyleClass());
            schedule.setTitle(event.getTitle());
            schedule.setOwner(udm.getUser());
            schedule.setSDate(event.getStartDate());
            schedule.setEDate(event.getEndDate());
            schedule.setContent(event.getDescription());
            schedule.setAllday(event.isAllDay());
            sf.remove(schedule);
            System.out.println("予定を削除しました : " + schedule.getTitle());
        }else{
            // 共有者なら
            ScheduleManage sm = new ScheduleManage(event.getStyleClass(), udm.getUser().getUserId());
            smf.remove(sm);
            System.out.println("共有情報を削除しました : "+sm.getScheduleManagePK().getScheduleId());
        }
        
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
        //共有者がいる予定なら共有者を表示する
        System.out.println(event.getStyleClass());
        System.out.println("description : "+event.getDescription());
        
        if(udm.getUser().getUserId().equals(event.getData().toString())){
            System.out.println("あなたが予定の作成者です");
            disable = false;
        }else{
            System.out.println("共有された予定です");
            System.out.println("操作者のID : "+udm.getUser().getUserId());
            System.out.println("Data : "+event.getData());
            disable = true;
        }
        
        if(smf.findByScheduleId(event.getStyleClass()).size() > 0){
            List<UserData> target = new ArrayList<UserData>();
            oldTarget = new ArrayList<UserData>();
            
            
            for(ScheduleManage sm : smf.findByScheduleId(event.getStyleClass())){
                target.add(udf.findUserId(sm.getScheduleManagePK().getUserId()));
                System.out.println("共有している人 : "+sm.getScheduleManagePK().getUserId());
            }
            
            //コピーする
            oldTarget = target;
            
            users.setTarget(target);
            share = true;
        }else{
            resetPickList();
            oldTarget = new ArrayList<UserData>();
            share = false;
        }
        
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        UserData ud = new UserData();
        ud.setName("-");
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject(),ud);
        resetPickList();
        disable = false;
        share = false;
        System.out.println("日付を選択しました");
    }
     
    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());
         
        addMessage(message);
    }
     
    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }    

    public DualListModel<UserData> getUsers() {
        return users;
    }

    public void setUsers(DualListModel<UserData> users) {
        this.users = users;
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

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }
    
    
}
