package bean;

import ejb.BulletinBoardFacade;
import ejb.CurriculumFacade;
import ejb.InfoFacade;
import ejb.ScheduleFacade;
import ejb.ScheduleManageFacade;
import ejb.TodoFacade;
import ejb.TodoManageFacade;
import entity.BulletinBoard;
import entity.Curriculum;
import entity.Info;
import entity.Schedule;
import entity.ScheduleManage;
import javax.inject.Named;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@Named(value = "topBean")
@RequestScoped
public class topBean {
    
    
    @EJB
    CurriculumFacade cf;
    @EJB
    TodoFacade tf;
    @EJB
    TodoManageFacade tmf;
    @EJB
    InfoFacade inf;
    @EJB
    BulletinBoardFacade bbf;
    @EJB
    ScheduleFacade sf;
    @EJB
    ScheduleManageFacade smf;
    
    @Inject
    UserDataManager udm;
    
    private List<Curriculum> curriculumList;
    private List<BulletinBoard> threadMessage;
    private String todoInfoMessage;
    private String todoShareMessage;
    private List<String> infoMessage;
    private List<String> weekdate;
    private List<Schedule> sunSchedule;
    private List<Schedule> monSchedule;
    private List<Schedule> tueSchedule;
    private List<Schedule> wedSchedule;
    private List<Schedule> thuSchedule;
    private List<Schedule> friSchedule;
    private List<Schedule> satSchedule;
    
    //カレンダー
    protected Calendar cal = Calendar.getInstance();
    private String currentWeek;
    
    @PostConstruct
    public void init(){
        
        /* カレンダー生成処理 */
        
        //カレンダーの見出しを生成
        currentWeek = String.valueOf(cal.get(Calendar.YEAR)) + "年 ";
        currentWeek += String.valueOf(cal.get(Calendar.MONTH) + 1) + "月 ";
        currentWeek += String.valueOf(cal.get(Calendar.WEEK_OF_MONTH) + "週目");
        
        
        //週初め(日曜)と週終わり(土曜)のCalendarインスタンスを生成する
        Calendar startDate = (Calendar)cal.clone();
        Calendar endDate = (Calendar)cal.clone();
        Calendar endClone;
        
        startDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        
        //終了日に1を足す
//        endDate.add(Calendar.DAY_OF_YEAR, 1);
        
        //初期化
        weekdate = new ArrayList<>();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        
        /*一週間分の予定を取得する*/
        while(startDate.before(endDate)){
            //終了日時を格納する変数
            endClone = (Calendar)startDate.clone();
            endClone.set(Calendar.HOUR_OF_DAY, 23);
            endClone.set(Calendar.MINUTE, 59);
            endClone.set(Calendar.SECOND, 59);
            
            
            System.out.println("次の日付の予定を取得します " + startDate.get(Calendar.DAY_OF_MONTH) + "日");
            System.out.println("渡す日付 開始 : "+sdf.format(startDate.getTime())+ " 終了 : "+sdf.format(endClone.getTime()));
            
            //日付を取得
            weekdate.add(String.valueOf(startDate.get(Calendar.DAY_OF_MONTH)));
            
            //曜日によって代入する変数を振り分ける
            switch(startDate.get(Calendar.DAY_OF_WEEK)){
                case 1:
                    sunSchedule = sf.findByWeekSchedule(udm.getUser(), startDate.getTime());
                    //共有された予定の取得
                    if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
                        for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                            if(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).size() > 0){
                                sunSchedule.add(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).get(0));
                            }
                            
                        }
                    }
                    break;
                    
                case 2:
                    monSchedule = sf.findByWeekSchedule(udm.getUser(), startDate.getTime());
                    //共有された予定の取得
                    if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
                        for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                            if(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).size() > 0){
                                monSchedule.add(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).get(0));
                            }
                        }
                    }
                    break;
                    
                case 3:
                    tueSchedule = sf.findByWeekSchedule(udm.getUser(), startDate.getTime());
                    //共有された予定の取得
                    if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
                        for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                            if(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).size() > 0){
                                tueSchedule.add(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).get(0));
                            }
                        }
                    }
                    break;
                    
                case 4:
                    wedSchedule = sf.findByWeekSchedule(udm.getUser(), startDate.getTime());
                    //共有された予定の取得
                    if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
                        for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                            if(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).size() > 0){
                                wedSchedule.add(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).get(0));
                            }
                        }
                    }
                    break;
                    
                case 5:
                    thuSchedule = sf.findByWeekSchedule(udm.getUser(), startDate.getTime());
                    //共有された予定の取得
                    if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
                        for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                            if(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).size() > 0){
                                thuSchedule.add(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).get(0));
                            }
                        }
                    }
                    break;
                    
                case 6:
                    friSchedule = sf.findByWeekSchedule(udm.getUser(), startDate.getTime());
                    //共有された予定の取得
                    if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
                        for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                            if(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).size() > 0){
                                friSchedule.add(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).get(0));
                            }
                        }
                    }
                    break;
                    
                case 7:
                    satSchedule = sf.findByWeekSchedule(udm.getUser(), startDate.getTime());
                    //共有された予定の取得
                    if(smf.findUserId(udm.getUser().getUserId()).size() > 0){
                        for(ScheduleManage sm : smf.findUserId(udm.getUser().getUserId())){
                            if(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).size() > 0){
                                satSchedule.add(sf.findByShareSchedule(sm.getScheduleManagePK().getScheduleId(),startDate.getTime()).get(0));
                            }
                        }
                    }
                    break;
                    
                default:
                    System.out.println("曜日がどのケースにも一致しませんでした : "+startDate.get(Calendar.DAY_OF_WEEK));
                    
            }
            
            //1日加算する
            startDate.add(Calendar.DAY_OF_YEAR, 1);
            
            
        }
        
        
        /* 時間割の取得 */
        curriculumList = new ArrayList<Curriculum>();
        if(cf.findByDayCurriculum(udm.getUser().getDepartmentId().getDepartmentId(),String.valueOf(1).charAt(0)).size() > 0){
            for(int i = 1; i < 5; i++){
                curriculumList.add(cf.findByDayCurriculum(udm.getUser().getDepartmentId().getDepartmentId(),String.valueOf(i).charAt(0)).get(0));
                System.out.println("時限 : " + curriculumList.get(i-1).getCurriculumPK().getPeriod() + "科目 : "+curriculumList.get(i-1).getSubject() + " 教室: " + curriculumList.get(i-1).getClassroom());
            }
        }
        
        
        /* 掲示板のお知らせを作成 */
        threadMessage = bbf.orderByPostDateLimit3();
        
        
        /* infoのお知らせを作成 */
        infoMessage = new ArrayList<String>();
        try {
            if(inf.findAllInfoLimit3().size() > 0){
                for(Info inf : inf.findAllInfoLimit3()){
                    infoMessage.add(inf.getTitle());
                }
            }else{
                infoMessage = null;
            }
        } catch (ParseException ex) {
            Logger.getLogger(topBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /* todoのお知らせを作成 */
        if(tf.findByExpiredTodo().size() > 0){
            todoInfoMessage = "期限切れのTodoが "+tf.findByExpiredTodo().size()+"件あります";
        }else{
            todoInfoMessage = "期限切れのTodoはありません";
        }
        
        /*if(tmf.findByUserId(udm.getUser().getUserId()).size() > 0){
            todoShareMessage = "共有されたTodoが "+tmf.findByUserId(udm.getUser().getUserId()).size()+"件あります";
        
        }else{
            todoShareMessage = "共有されたTodoはありません";
        }*/
        
    }

    public List<Curriculum> getCurriculumList() {
        return curriculumList;
    }

    public void setCurriculumList(List<Curriculum> curriculumList) {
        this.curriculumList = curriculumList;
    }

    public String getTodoInfoMessage() {
        return todoInfoMessage;
    }

    public void setTodoInfoMessage(String todoInfoMessage) {
        this.todoInfoMessage = todoInfoMessage;
    }

    public List<String> getInfoMessage() {
        return infoMessage;
    }

    public void setInfoMessage(List<String> infoMessage) {
        this.infoMessage = infoMessage;
    }

    public String getTodoShareMessage() {
        return todoShareMessage;
    }

    public void setTodoShareMessage(String todoShareMessage) {
        this.todoShareMessage = todoShareMessage;
    }

    public List<BulletinBoard> getThreadMessage() {
        return threadMessage;
    }

    public void setThreadMessage(List<BulletinBoard> threadMessage) {
        this.threadMessage = threadMessage;
    }

    public String getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(String currentWeek) {
        this.currentWeek = currentWeek;
    }

    public List<Schedule> getSunSchedule() {
        return sunSchedule;
    }

    public void setSunSchedule(List<Schedule> sunSchedule) {
        this.sunSchedule = sunSchedule;
    }

    public List<Schedule> getMonSchedule() {
        return monSchedule;
    }

    public void setMonSchedule(List<Schedule> monSchedule) {
        this.monSchedule = monSchedule;
    }

    public List<Schedule> getTueSchedule() {
        return tueSchedule;
    }

    public void setTueSchedule(List<Schedule> tueSchedule) {
        this.tueSchedule = tueSchedule;
    }

    public List<Schedule> getWedSchedule() {
        return wedSchedule;
    }

    public void setWedSchedule(List<Schedule> wedSchedule) {
        this.wedSchedule = wedSchedule;
    }

    public List<Schedule> getThuSchedule() {
        return thuSchedule;
    }

    public void setThuSchedule(List<Schedule> thuSchedule) {
        this.thuSchedule = thuSchedule;
    }

    public List<Schedule> getFriSchedule() {
        return friSchedule;
    }

    public void setFriSchedule(List<Schedule> friSchedule) {
        this.friSchedule = friSchedule;
    }

    public List<Schedule> getSatSchedule() {
        return satSchedule;
    }

    public void setSatSchedule(List<Schedule> satSchedule) {
        this.satSchedule = satSchedule;
    }

    public List<String> getWeekdate() {
        return weekdate;
    }

    public void setWeekdate(List<String> weekdate) {
        this.weekdate = weekdate;
    }

    
    
    
            
}
