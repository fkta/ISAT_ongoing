package function;

import entity.Department;
import entity.Department;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class CsvReader {
    public List<Department> read_dpt(String path){
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
}
