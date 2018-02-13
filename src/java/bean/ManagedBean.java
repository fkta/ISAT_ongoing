package bean;

import ejb.SecretQuestionFacade;
import ejb.UserDataFacade;
import entity.SecretQuestion;
import entity.UserData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Named(value = "managedBean")
@RequestScoped
public class ManagedBean {
    private String userId;
    private String password;
    private String errorMessage;
    private boolean nicknameToggle;
    private String nickname;
    private List<SelectItem> secretQuestions;
    private int sqItem;
    private String sqAnswer;
    private String SecretQuestion;
    private String SecretQuestion2;
    
    //パスワードの再設定
    private String oldpass;
    private String newpass;
    private String confpass;
    
    //パスワード救済措置
    private String inputUserId;
    private String inputAnswer;
    private String outputSecret;
    protected String secretAnswer;
    private boolean output;
    
    
    //ajaxTest
    private String test;
    private String inputValue;
    
    // メッセージ
    protected FacesContext context = FacesContext.getCurrentInstance();
    
    @EJB
    UserDataFacade udf;
    @EJB
    SecretQuestionFacade sqf;
    @Inject
    UserDataManager udm;
          
    @PostConstruct
    public void init(){
        nicknameToggle = false;
        output = false;
        
        List<SecretQuestion> sqList;
        sqList = sqf.findAll();
        secretQuestions = new ArrayList<SelectItem>();
        for(SecretQuestion sq : sqList){
            // (int,String)
            SelectItem item = new SelectItem(sq.getQuestionId(),sq.getQuestion());
            secretQuestions.add(item);
            
        }
    }
    
    public void clearToPass(){
        this.newpass = null;
        this.oldpass = null;
        this.confpass = null;
    }
    
    /* ajaxTest */
    public void updateAjax(){
        test = String.valueOf(sqItem);
    }
    
    public void resetItems(){
        secretQuestions = new ArrayList<SelectItem>();
        for(SecretQuestion sq : sqf.findAll()){
            // (int,String)
            SelectItem item = new SelectItem(sq.getQuestionId(),sq.getQuestion());
            secretQuestions.add(item);
            
        }
    }
    
    public void updateValue(){
        this.inputValue = nickname;
    }
    
    /* メッセージ作成処理 */
    public void scheduleAddMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "予定を追加しました") );
        
    }
    
    public void scheduleDeleteMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "予定を削除しました") );
        
    }
    
    public void scheduleUpdateMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "予定を更新しました") );
        
    }
    
    // todo関連
    public void todoUpdateMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "todoを更新しました") );
        
    }
    
    public void todoCreateMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "todoを作成しました") );
        
    }
    
    public void todoShareMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "共有設定を行いました") );
    }
    
    public void todoDeleteMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "todoを削除しました") );
    }
    
    public void todoDeleteErrorMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "todoが選択されていません") );
    }
    
    public void todoFinishingMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "選択されたTodoを完了しました") );
    }
    
    public void todoShareUpdateMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success", "共有者を変更しました") );
    }
    
    public void todoNoSelectErrorMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "予定を選択してください") );
    }
    
    public void secretNotMatchMessage(){
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "答えが一致しません") );
    }
    
    
    /* パスワード再設定処理 */
    public void findSecretQuestion(){
        //検索結果があるかを判定
        if(udf.findSecret(inputUserId).size() > 0){
            for(UserData ud : udf.findSecret(inputUserId)){
                //秘密の質問が設定されているかどうか
                if(null != ud.getQuestionId()){
                    outputSecret = ud.getQuestionId().getQuestion();
                    secretAnswer = ud.getQanswer();
                    
                    inputUserId = null;
                    output = true;
                    
                }else{
                    output = false;
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Fail", "秘密の質問が設定されていません") );
                    
                }
            }
            
        }else{
            output = false;
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Error", "ユーザが存在しません") );
        }
    }
    
    public String checkAnswer(){
        System.out.println("sAns : "+secretAnswer);
        System.out.println("iAns : "+inputAnswer);
        if(secretAnswer.equals(inputAnswer)){
            
            return "resetpass.xhtml?faces-redirect=true";
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "答えが一致しません") );
            return null;
        }
    }
    
    
    /* ユーザ認証処理 */
    public String userAuth(){
        System.out.println("ユーザ認証開始");
        List<UserData> ud = udf.userAuth(userId, password);
        if(!ud.isEmpty()){
            udm.setUser(ud.get(0));
            if(udm.getUser().getUsertype().equals("admin")||udm.getUser().getUsertype().equals("teacher")){
                return "/toppage/top.xhtml?faces-redirect=true";
            }else if(udm.getUser().getUsertype().equals("student")){
                return "/toppage/top.xhtml?faces-redirect=true";
            }else{
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "ユーザIDもしくはパスワードが間違っています") );
                errorMessage = "ユーザIDもしくはパスワードが間違っています2";
                System.out.println("input data user id :"+userId + "password : "+password);
                System.out.println("errorMessage : " + errorMessage);
                return null;
            }
            
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error", "ユーザIDもしくはパスワードが間違っています") );
            errorMessage = "ユーザIDもしくはパスワードが間違っています";
            System.out.println("input data user id :"+userId + "password : "+password);
            System.out.println("errorMessage : " + errorMessage);
            return null;
       }
    }
    
    /* リロード処理 */
    public void reload() throws IOException { 
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI()); 
    } 
    
    /* ログアウト処理 */
    public String logout(){
        invalidate();
        return "/login/newlogin.xhtml?faces-redirect=true";
    }
    
    // セッション破棄
    public void invalidate(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession)ec.getSession(false);
        if(session != null){
            try{
                session.invalidate();
                System.out.println("session invalidate success");
            }catch(IllegalStateException e){
                System.out.println("error : "+e);
            }
        }
    }
    
    /*info関連*/
    public String transToInfoList(){
        if(udm.getUser().getUsertype().equals("admin")||udm.getUser().getUsertype().equals("teacher")){
            System.out.println("インフォ一覧 userId : "+udm.getUser().getUsertype());
            return "/infomation/infolist_upper.xhtml?faces-redirect=true";
        }else if(udm.getUser().getUsertype().equals("student")){
            System.out.println("インフォ一覧 userId : "+udm.getUser().getUsertype());
            return "/infomation/info_list.xhtml?faces-redirect=true";
        }else{
            System.out.println("ユーザ種別が不正です。");
            udm.setErrorMessage("セッションがタイムアウトしました　再度ログインしてください。");
            return "/login/login.xhtml?faces-redirect=true";
        }
    }
    
    /* Profile関連 */
    public String transProfilePage(){
        return "/profiles/profile_index.xhtml?faces-redirect=true";
    }
    
    public String updateUserData(){
        UserData ud = udm.getUser();
        ud.setNickname(nickname);
        udf.edit(ud);
        System.out.println("ニックネームを更新しました");
        udm.setUser(ud);
        System.out.println("変更後のニックネーム : "+udm.getUser().getNickname());
        return null;
    }
    
    public void toggleNickname(){
        if(nicknameToggle = true){
            nickname = null;
        }else{
            getNickname();
        }
    }
    
    public void changePassword(){
        // パスワードが一致したら
        if(oldpass.equals(udm.getUser().getPassword())){
            //確認用のパスワードが一致しているか
            if(newpass.equals(confpass)){
                //変更処理
                UserData ud = udm.getUser();
                ud.setPassword(newpass);
                udm.setUser(ud);
                
                udf.edit(ud);
                System.out.println("変更後のパスワード : "+ud.getPassword());
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"Success","パスワードを変更しました"));
                
            }else{
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error","確認パスワードが一致しません"));
            }
            
        }else{
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Error","パスワードが一致しません"));
        }
        
    }
    
    public void updateSecretQuestion(){
        System.out.println("sqItem : "+sqItem+" sqAnswer : "+sqAnswer);
        UserData ud = udm.getUser();
        SecretQuestion sq = new SecretQuestion(sqItem);
        ud.setQuestionId(sq);
        ud.setQanswer(sqAnswer);
        udf.edit(ud);
        udm.setUser(udf.findUserId(ud.getUserId()));
        getSecretQuestion();
        System.out.println("秘密の質問を更新しました 質問 : "+udm.getUser().getQuestionId().getQuestion());
        System.out.println("答え : "+udm.getUser().getQanswer());
        
        //初期化処理
        sqAnswer = null;
        
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO ,"秘密の質問を設定しました",""));
    }
    
    /* スケジュール関連 */
        public String transToSchedule(){
            return "/schedule/schedule.xhtml?faces-redirect=true";
        }
       
    /* 掲示板関連 */
        public String transToBoard(){
            return "/board/threadlist.xhtml?faces-redirect=true";
        }
        
    /* 値の初期化 */
    public void clear(){
        this.userId = null;
        this.password = null;
    }
    
    /* 値の取得 */
    
    
    public String getSecretQuestion(){
        String sq;
        if(udm.getUser().getQuestionId() != null){
            sq = udm.getUser().getQuestionId().getQuestion();
        }else{
            sq = "設定されていません!";
        }
        this.SecretQuestion = sq;
        return SecretQuestion;
    }
    
    public String getSecretQuestion2(){
        String sq;
        if(udm.getUser().getQuestionId2() != null){
            sq = udm.getUser().getQuestionId2().getQuestion();
        }else{
            sq = "設定されていません!!";
        }
        return sq;
    }
    
    /* セッターゲッター */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isNicknameToggle() {
        return nicknameToggle;
    }

    public void setNicknameToggle(boolean nicknameToggle) {
        System.out.println("setnicknameToggle : "+nicknameToggle);
        this.nicknameToggle = nicknameToggle;
    }
    
    public String getNickname(){
        if(udm.getUser().getNickname() != null){
            nickname = udm.getUser().getNickname();
        }else{
            nickname = "設定されていません";
        }
            
        return nickname;
    }
    
    public void setNickname(String nickname){
        System.out.println("setnickname : "+nickname);
        this.nickname = nickname;
    }

    public List<SelectItem> getSecretQuestions() {
        return secretQuestions;
    }

    public void setSecretQuestions(List<SelectItem> secretQuestions) {
        this.secretQuestions = secretQuestions;
    }

    public int getSqItem() {
        return sqItem;
    }

    public void setSqItem(int sqItem) {
        this.sqItem = sqItem;
    }

    public String getSqAnswer() {
        return sqAnswer;
    }

    public void setSqAnswer(String sqAnswer) {
        System.out.println("setSqAnswer : "+sqAnswer);
        this.sqAnswer = sqAnswer;
    }
    
    public void clearToSqAnswer(){
        System.out.println("sqAnswer clear complete");
        this.sqAnswer = null;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getInputValue() {
        this.inputValue = this.nickname;
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getNewpass() {
        return newpass;
    }

    public void setNewpass(String newpass) {
        this.newpass = newpass;
    }

    public String getConfpass() {
        return confpass;
    }

    public void setConfpass(String confpass) {
        this.confpass = confpass;
    }

    public String getInputUserId() {
        return inputUserId;
    }

    public void setInputUserId(String inputUserId) {
        this.inputUserId = inputUserId;
    }

    public String getInputAnswer() {
        return inputAnswer;
    }

    public void setInputAnswer(String inputAnswer) {
        this.inputAnswer = inputAnswer;
    }

    public String getOutputSecret() {
        return outputSecret;
    }

    public void setOutputSecret(String outputSecret) {
        this.outputSecret = outputSecret;
    }

    public boolean isOutput() {
        return output;
    }

    public void setOutput(boolean output) {
        this.output = output;
    }
    
    
}
