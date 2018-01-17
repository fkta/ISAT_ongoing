/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import ejb.ScheduleFacade;
import entity.Schedule;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author glowo
 */
@Named(value = "scheduleBean2")
@SessionScoped
public class ScheduleBean2 implements Serializable{
    private ScheduleModel eventModel;
    private ScheduleEvent event = new DefaultScheduleEvent();
    
    // メッセージ 
    protected FacesContext context = FacesContext.getCurrentInstance();
    
 
    
    
    @EJB
    ScheduleFacade sf;
    @Inject
    UserDataManager udm;
    @Inject
    ManagedBean mb;
 
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        
        
        for(Schedule s : sf.searchSchedule(udm.getUser())){
            ScheduleEvent master;
            DefaultScheduleEvent ev = new DefaultScheduleEvent();
            ev.setTitle(s.getTitle());
            ev.setStartDate(s.getSDate());
            ev.setEndDate(s.getEDate());
            ev.setDescription(s.getContent());
            ev.setData(s);
            ev.setStyleClass(s.getScheduleId());
            ev.setAllDay(s.getAllday());
            master = ev;
            eventModel.addEvent(master);
        }
        
        System.out.println("予定数 : "+sf.searchSchedule(udm.getUser()).size());
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
            cDate = sdf.format(event.getEndDate());
            cDate = cDate.substring(0,8);
            schedule.setEDate(sdf.parse(cDate.concat("235959")));
            
            DefaultScheduleEvent dse = new DefaultScheduleEvent();
            
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
        
        if(event.isAllDay()){
            System.out.println("AllDay : "+event.isAllDay());
            String cDate = sdf.format(event.getEndDate());
            cDate = cDate.substring(0,8);
            schedule.setEDate(sdf.parse(cDate.concat("235959")));
            schedule.setAllday(true);
        }else{
            schedule.setEDate(event.getEndDate());
            schedule.setAllday(false);
        }
        
        schedule.setScheduleId(event.getStyleClass());
        System.out.println("scheduleId : "+ event.getStyleClass());
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
}
