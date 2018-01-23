package function;

import entity.Category;
import entity.Department;
import entity.SecretQuestion;
import entity.Service;
import entity.UserData;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CsvReader {
    public List<Department> readDpt(String path){
        List<Department> list = new ArrayList<Department>();
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            StringTokenizer token;
            while((line = br.readLine()) != null){
                token = new StringTokenizer(line,",");
                while(token.hasMoreTokens()){
                    Department dpt = new Department();
                    dpt.setDepartmentName(token.nextToken());
                    dpt.setDepartmentCode(token.nextToken().charAt(0));
                    dpt.setClassCode(token.nextToken());
                    dpt.setYears(Integer.parseInt(token.nextToken()));
//                    System.out.println("departmentId : "+dpt.getDepartmentId()+""+user.getName());
                    list.add(dpt);
                }
            }
            br.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<SecretQuestion> readSqu(String path){
        List<SecretQuestion> list = new ArrayList<SecretQuestion>();
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            StringTokenizer token;
            while((line = br.readLine()) != null){
                token = new StringTokenizer(line,",");
                while(token.hasMoreTokens()){
                    SecretQuestion sq = new SecretQuestion();
                    sq.setQuestion(token.nextToken());
                    list.add(sq);
                }
            }
            br.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    
    public List<UserData> readUd(String path){
        List<UserData> list = new ArrayList<UserData>();
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            StringTokenizer token;
            while((line = br.readLine()) != null){
                System.out.println("csv read start");
                token = new StringTokenizer(line,",");
                while(token.hasMoreTokens()){
                    UserData ud = new UserData();
                    ud.setUserId(token.nextToken());
                    System.out.println("point0");
                    ud.setEntranceYear(Short.parseShort(token.nextToken()));
                    ud.setGuraduationYear(Short.parseShort(token.nextToken()));
                    /*try{
                        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy");
                        Date date = sdFormat.parse(token.nextToken());
                        ud.setEntranceYear(date);
                        date = sdFormat.parse(token.nextToken());
                        ud.setGuraduationYear(date);
                    }catch(ParseException e){
                        e.printStackTrace();
                    }*/
                    System.out.println("point0.1");
                    
                    System.out.println("point1");
                    Department dp = new Department();
                    System.out.println("point2");
                    dp.setDepartmentId(Integer.parseInt(token.nextToken()));
                    System.out.println("point3");
                    ud.setDepartmentId(dp);
                    System.out.println("point4");
                    ud.setName(token.nextToken());
                    ud.setGender(token.nextToken().charAt(0));
                    ud.setUsertype(token.nextToken());
                    ud.setPassword(token.nextToken());
                    list.add(ud);
                    System.out.println("point5");
                }
            }
            br.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    
    /*Category*/
    public List<Category> readCat(String path){
        List<Category> list = new ArrayList<Category>();
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            StringTokenizer token;
            while((line = br.readLine()) != null){
                token = new StringTokenizer(line,",");
                while(token.hasMoreTokens()){
                    Category cat = new Category();
                    cat.setCategoryName(token.nextToken());
                    list.add(cat);
                }
            }
            br.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
    
    /*Service*/
    public List<Service> readSrv(String path){
        List<Service> list = new ArrayList<Service>();
        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            
            String line;
            StringTokenizer token;
            while((line = br.readLine()) != null){
                token = new StringTokenizer(line,",");
                while(token.hasMoreTokens()){
                    Service srv = new Service();
                    srv.setServiceName(token.nextToken());
                    list.add(srv);
                }
            }
            br.close();
            
        }catch(IOException e){
            e.printStackTrace();
        }
        return list;
    }
}
