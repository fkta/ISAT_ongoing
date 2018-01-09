package bean;

import ejb.ScheduleFacade;
import entity.Schedule;
import entity.UserData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

@Named(value = "scheduleBean")
@RequestScoped
public class ScheduleBean {
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    private Date stDate = new Date();
    private Date enDate = new Date();
    private String title;
    private String place;
    private String content;
    private boolean allDay;
    private Date startDate;
    private Date endDate;
    
    @EJB
    ScheduleFacade sf;
    @Inject
    UserDataManager udm;
    @Inject
    ScheduleDataManager sdm;
    
    
    public void deleteSchedule(Schedule ds){
        sf.remove(ds);
    }

    public String findSchedule() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String cDate = sdf.format(stDate);
        cDate = cDate.substring(0,11);
        stDate = sdf.parse(cDate.concat("00:00:00"));
        System.out.println("開始日"+stDate);
        enDate = sdf.parse(cDate.concat("23:59:59"));
        System.out.println("終了日"+enDate);
        sdm.setScheduleList(sf.searchSchedule(stDate, enDate));
        System.out.println("取得した予定 "+sdm.getScheduleList().get(0).getTitle());
        return "deleteschedule.xhtml?faces-redirect=true";
    }
    
    public String create(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
        Schedule sch = new Schedule();
        sch.setOwner(udm.getUser());
        sch.setScheduleId("sch"+sdf.format(new Date()));
        sch.setTitle(title);
        sch.setSDate(startDate);
        sch.setEDate(endDate);
        sch.setContent(content);
        sch.setPlace(place);
        sf.create(sch);
        return null;
    }
 
    @PostConstruct
    public void init() {
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        Date d = cal.getTime();
        cal.add(Calendar.WEEK_OF_YEAR, 2);
        Date d2 = cal.getTime();*/
        
        /* スケジュールの取得 */
        eventModel = new DefaultScheduleModel();
        List<Schedule> schList = getAllSch();
        for(Schedule schData : schList){
            eventModel.addEvent(new DefaultScheduleEvent(schData.getTitle(),schData.getSDate(),schData.getEDate()));
        }
        
         
        lazyEventModel = new LazyScheduleModel() {
             
            @Override
            public void loadEvents(Date start, Date end) {
                Date random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));
                 
                random = getRandomDate(start);
                addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));
            }   
        };
    }
     
    public Date getRandomDate(Date base) {
        Calendar date = Calendar.getInstance();
        date.setTime(base);
        date.add(Calendar.DATE, ((int) (Math.random()*30)) + 1);    //set random day of month
         
        return date.getTime();
    }
     
    public Date getInitialDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);
         
        return calendar.getTime();
    }
     
    public ScheduleModel getEventModel() {
        return eventModel;
    }
     
    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }
     
    public ScheduleEvent getEvent() {
        return event;
    }
 
    public void setEvent(ScheduleEvent event) {
        this.event = event;
    }
     
    public void addEvent(ActionEvent actionEvent) {
        if(event.getId() == null)
            eventModel.addEvent(event);
        else
            eventModel.updateEvent(event);
         
        event = new DefaultScheduleEvent();
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

    /* データ取得 */
    public List<Schedule> getAllSch(){
        return sf.findAll();
    }
    
    /*セッターゲッター*/

    public Date getStDate() {
        return stDate;
    }

    public void setStDate(Date stDate) {
        this.stDate = stDate;
    }

    public Date getEnDate() {
        return enDate;
    }

    public void setEnDate(Date enDate) {
        this.enDate = enDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
