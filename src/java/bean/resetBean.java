package bean;

import ejb.UserDataFacade;
import entity.UserData;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Inject;

@Named(value = "resetBean")
@SessionScoped
public class resetBean implements Serializable {

    //パスワード救済措置
    private String inputUserId;
    private String inputAnswer;
    private String outputSecret;
    protected String secretAnswer;
    private boolean output;
    
    @EJB
    UserDataFacade udf;
    @Inject
    ManagedBean mb;
    
    @PostConstruct
    public void init() {
        output = true;
        
    }
    
    public void clear(){
        inputUserId = null;
        inputAnswer = null;
        outputSecret = null;
        secretAnswer = null;
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
                    output = false;
                    
                }else{
                    output = true;
                    
                }
            }
            
        }else{
            output = false;
        }
    }
    
    public String checkAnswer(){
        System.out.println("sAns : "+secretAnswer);
        System.out.println("iAns : "+inputAnswer);
        if(secretAnswer.equals(inputAnswer)){
            System.out.println("Mached");
            return "/resetpass.xhtml?faces-redirect=true";
            
        }else{
            mb.secretNotMatchMessage();
            return null;
        }
    }
    
    /* セッターゲッター */
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
