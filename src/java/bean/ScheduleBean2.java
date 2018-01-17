package bean;

import ejb.DepartmentFacade;
import ejb.ScheduleFacade;
import ejb.ScheduleManageFacade;
import ejb.UserDataFacade;
import entity.Department;
import entity.Schedule;
import entity.ScheduleManage;
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
    
    // 
    private DualListModel<UserData> users;
    
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
 
    @PostConstruct
    public void init() {
        
        //セレクトメニューの中身を作る
        classList = new ArrayList<SelectItem>();
        for(Department d : df.findAll()){
            if(d.getDepartmentCode().toString().equals("T")){
                SelectItem item = new SelectItem("Teacher","先生");
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
            master = ev;
            eventModel.addEvent(master);
        }
        
        System.out.println("予定数 : "+sf.searchSchedule(udm.getUser()).size());
        
        // 1件以上共有された予定がある場合
        if(smf.findUserId(udm.getUser()).size() > 0){
            for(ScheduleManage sm : smf.findUserId(udm.getUser())){
                // データの取得
                List<Schedule> s = new ArrayList<Schedule>();
                s = sf.findByScheduleId(sm.getScheduleId());
                
                ScheduleEvent master;
                DefaultScheduleEvent ev = new DefaultScheduleEvent();
                ev.setTitle(s.get(0).getTitle());
                ev.setStartDate(s.get(0).getSDate());
                ev.setEndDate(s.get(0).getEDate());
                ev.setDescription(s.get(0).getContent());
                
                // StyleClassにScheduleIdを格納する
                ev.setStyleClass(s.get(0).getScheduleId());
                ev.setAllDay(s.get(0).getAllday());
                master = ev;
                eventModel.addEvent(master);
                
            }
        }
        // end
        
        System.out.println("共有されている予定数 : "+smf.findUserId(udm.getUser()).size());
        
        /* 共有リストのデータを取得する */
        // 学生の一覧を取得する
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        for(UserData ud : udf.findByUserType("student")){
            
            String nendo = sdf.format(new Date());
            int now = Integer.parseInt(nendo.substring(0, 4));
            int month = Integer.parseInt(nendo.substring(4, 6));
            
            //年度変換する
            if(month < 4){
                now = now - 1;
            }
            
            // 学生の入学年度
            int year = Integer.parseInt(ud.getEntranceYear());
            
            // 学年を算出して代入
            int gakunen = now - year;
            
        }
        /* ここまで */
        
        /* PickListの初期化 */
        List<UserData> source = new ArrayList<UserData>();
        List<UserData> target = new ArrayList<UserData>();
        
        source.add(udm.getUser());
        
        users = new DualListModel<UserData>(source,target);
        
    }
    
    /* PickListを更新する */
    public void updatePickList(){
        List<UserData> source = new ArrayList<UserData>();
        List<UserData> target = new ArrayList<UserData>();
        
        // 学科コードとクラスコードをもとに生徒を洗い出す
        List<Department> dptList;
        String classCode = pickItem.substring(2);
        int gakunen = Integer.parseInt(pickItem.substring(1, 2));
        dptList = df.findByClass(pickItem.charAt(0),classCode);
        System.out.println("取得した学科名 : "+dptList.get(0).getDepartmentName());
        
        
        
        users = new DualListModel<UserData>(source,target);
    }
    
    /* PickList End */
    
    
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
        
        schedule.setScheduleId("sch"+sdf.format(new Date()));
        sf.create(schedule);
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
        
        
        System.out.println("UPDATE scheduleId : "+ event.getStyleClass() + "EDate : " + schedule.getEDate());
        sf.edit(schedule);
    }
    
    public void delete(){
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
    }
     
    public void onEventSelect(SelectEvent selectEvent) {
        event = (ScheduleEvent) selectEvent.getObject();
    }
     
    public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
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
    
    
}
