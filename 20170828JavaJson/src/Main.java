/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fl
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.Date;

import java.util.Random;
import java.util.StringTokenizer;
public class Main {
    public static void main(String[] args) throws Exception{
        System.out.println("实例1:simple the test toJson and From Json");
        Gson gson = new Gson();
        String jsonstring = gson.toJson(new JsonTest());

        System.out.println(jsonstring);
        jsonstring = gson.toJson(new JsonTest());
        System.out.println(jsonstring);
        JsonTest testjoson = gson.fromJson(jsonstring, JsonTest.class);
        
        System.out.println("实例2:simple the test toJson value Format");
        //解析普通的文本信息
        String jsonNumber = gson.toJson(100); 
        System.out.println(jsonNumber);
        
        System.out.println("实例3:simple the test JsonReader use io Stream ");
        String json = "{\"name\":\"怪盗kidou\",\"age\":\"24\"}";
        User user = new User();  
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.beginObject();
        while (reader.hasNext()) {
             String s = reader.nextName();
             switch (s) {
                 case "name":
                       user.name = reader.nextString();
                 break;
                 case "age":
                       user.age = reader.nextInt(); //自动转换
                 break;
                 case "email":
                      user.email = reader.nextString();
                break;
            }
        }
    reader.endObject(); // throws IOException
    System.out.println(user.name);  // 怪盗kidou
    System.out.println(user.age);   // 24
    System.out.println(user.email); // ikidou@example.com
    
    
    //手动构造Json数据
    System.out.println("实例4:simple the test JsonWriter use io Stream OutputStreamWriter ");
    JsonWriter jsonwrite = new JsonWriter( new OutputStreamWriter(System.out));
    jsonwrite.beginObject().name("h").value("hhh").name("test'").beginArray().value("1").value(true).endArray().endObject().flush();
    
    
    //Json数据有空值和时间格式问题
    System.out.println("\n实例5:simple the test null value or date format ");
    User userOne =  new User("<alert>",11);
    System.err.println( gson.toJson(userOne));
//    GsonBuilder
    Gson gson2 = new GsonBuilder()
            .serializeNulls()
            .serializeSpecialFloatingPointValues()
            .setPrettyPrinting()
            .setDateFormat("YY:MM:dd")
          
            .disableInnerClassSerialization()
            .disableHtmlEscaping()
            .create();
      System.err.println( gson2.toJson(userOne));
         System.out.println("\n实例5:simple the test use Annotation");
        Gson gson3 = new GsonBuilder()
            .serializeNulls()
            .serializeSpecialFloatingPointValues()
            .setPrettyPrinting()
            .setDateFormat("YY:MM:dd")
             .excludeFieldsWithoutExposeAnnotation()
//                .excludeFieldsWithModifiers()
            .disableInnerClassSerialization()
            .disableHtmlEscaping()
            .create();
        String testJson3 = gson3.toJson(userOne);
         System.err.println( testJson3);
         System.out.println( gson.fromJson(testJson3,User.class).name);
        System.err.println( gson3.toJson( gson3.fromJson("{\"name1\":\"你说\"}", User.class) )); 
        
        
        
     String str = "This is a  test, this is another test.";
    String delimiters = "  ,."; // a space and a comma
    StringTokenizer st = new StringTokenizer(str, delimiters,true);
    System.out.println("Tokens  using a  StringTokenizer:");
    String token = null;
    while (st.hasMoreTokens()) {
      token = st.nextToken();
      System.out.println(token);
    }
    }
}

class User{
    @Expose(deserialize = true,serialize = true)     //暴露属性配置， 一个是反序列化，另一个是序列化
    @SerializedName( value ="name1" ,alternate = {"NAme","NAMe","NAME"} )  //使用注解的方式
    public String name;
    public int age;
    public String email;
    public Date date;
    public  User(){ }
    public User( String name , int age ){
        this.name = name;
        this.age = age;
        this.date = new Date(2017,5,17);
    }
}

class JsonBeanOne{
    public int id;
    private String name;
    public JsonBeanOne(){
        this.id = 1;
        this.name=  "我是jsonbena";
    }
}
 class JsonTest {
    public int id;
    public String  name;
    public int age;
    public String[] message;
    private JsonBeanOne  jsonnone;
    public  JsonTest(){
        this.id = new Random().nextInt(100);
        this.name= "掌上"+ this.id;
        this.age  = id*100%90;
        this.message = new String[]{
            "123","123","我是睡"
        };
        this.jsonnone = new JsonBeanOne();
    }
    //该函数不会
    public void sayHellow(){
        System.out.println("json.sayHellow()");
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

