
import sun.reflect.annotation.ExceptionProxy;
import java.io.*;
import java.net.*;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.math.*;
//import TuLingAPI.java;
class get_img{
    public static String get_img(String url_)throws Exception{
        URL url = new URL(url_);
        String line =null ;
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();

        conn.setRequestMethod("GET");

        conn.setConnectTimeout(5 * 1000);
        line = conn.getHeaderField("set-cookie");

        String sessionId = line.substring(0,line.indexOf(";"));

        //System.out.println("Cookie"+conn.getHeaderField("set-cookie") );
        InputStream inStream = conn.getInputStream();

        byte[] data = readInputStream(inStream);

        File imageFile = new File("Login.jpg");

        FileOutputStream outStream = new FileOutputStream(imageFile);

        outStream.write(data);

        outStream.close();

        return sessionId ;
    }
    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];

        int len = 0;

        while( (len=inStream.read(buffer)) != -1 ){

            outStream.write(buffer, 0, len);
        }

        inStream.close();

        return outStream.toByteArray();
    }
}

class wait_scan{
    public static String wait_scan1(String line)throws Exception{
        line = line.replace("qrsig=","");
        //System.out.println("Line is:" +line);
        boolean flag = true ;
        int hash_str=0 ;
        int i ;
        int start, end ;

        String rk_line =null ;
        String ptcz_line =null ;
        String skez_line =null ;
        String uin_line = null ;
        String pt2gguin_line = null ;
        //String jump_url =null;
        String newurl =null;
        String cookies2="pgv_pvi=8874665984; pgv_si=s1921474560;" ;  //" ptisp=ctc; " ;

        //line ="2Nic7NLJrRZ2RuvG1XZNJVJMJL*bnVFOeZa*H1DgvdzN3CbpG8-xQUgwdlUwqFbf" ;
        String agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9; rv:27.0) Gecko/20100101 Firefox/27.0";
        for( i= 0 ; i<line.length() ; i++){
            //System.out.println( (int)line.charAt(i) );
            hash_str += hash_str*32+ (int)line.charAt(i);
        }
        hash_str =(int) (hash_str & 2147483647);
        //System.out.println(hash_str);

        while(flag){
            Thread.sleep(5000);
            try{
                newurl = "https://ssl.ptlogin2.qq.com/ptqrlogin?u1=http%3A%2F%2Fw.qq.com%2Fproxy.html&ptqrtoken="+(int)hash_str+"&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=0-1-"+(int)(Math.random()*900000+1000000)+"&js_ver=10230&js_type=1&login_sig=&pt_uistyle=40&aid=501004106&daid=164&mibao_css=m_webqq&";
                if(newurl.startsWith("\uFEFF")){ //line = line.substring(1);
                    line = line.replace("\uFEFF", "");
                }

            }catch(Exception e){}
            String refer = "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=16&mibao_css=m_webqq&appid=501004106&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fw.qq.com%2Fproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20131024001";
            URL url = new URL(newurl);
            String cookies ="pt_guid_sig=33f6cdad048c8c67369b09f933eb40cbbd07044165bd2d9c499cb53cc438f429; pgv_pvi=8874665984; pgv_si=s1921474560; qrsig=" +line;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //conn.setRequestProperty("Referer",refer);
            conn.setRequestProperty("cookie",cookies);

            InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
            //System.out.println(1);
            BufferedReader reader = new BufferedReader( input);
            String line1;



            while( (line1= reader.readLine() )!= null ) {

                //System.out.println( line1.indexOf(key));
                if(line1.indexOf("二维码已失效") > 0 )
                    flag =false ;
                else if((start = line1.indexOf("http") ) > 0){

                    end = line1.indexOf("'" , start);
                    get_recent_list.jump_url = line1.substring(start , end);
                    //System.out.println("The Url is :" + line1.substring(start , end));

                    flag= false;
                    List<String> temp ;
                    String key =null ;
                    int firstindex,lastindex;
                    //Map headers = conn.getHeaderFields("set-cookie");
                    Map<String , List<String>> es = conn.getHeaderFields();

                    for (Map.Entry<String, List<String>> entry : es.entrySet()) {
                        // System.out.println("Key : " + entry.getKey() +
                        //                  " ,Value : " + entry.getValue());
                        key = entry.getKey();
                        try{
                            if(key.equals("Set-Cookie"))
                            {
                                temp = entry.getValue();
                                //System.out.println(temp);
                                for(String name :temp){
                                    //GET SET COOKIE Ptcz :
                                    if((firstindex=name.indexOf("ptcz=")) >=0){
                                        lastindex = name.indexOf(";");
                                        if(lastindex > firstindex+6 ){
                                            // System.out.println(name.substring(firstindex,lastindex));
                                            // cookies2 += name.substring(firstindex ,lastindex) ;
                                            // cookies2 +="; " ;
                                            ptcz_line = name.substring(firstindex ,lastindex)+";" ;
                                        }
                                    }
                                    //GET SET COOKIE RK :
                                    if((firstindex=name.indexOf("RK=")) >=0){
                                        lastindex = name.indexOf(";");
                                        //if(lastindex > firstindex+6 ){
                                        //System.out.println(name.substring(firstindex,lastindex));
                                        // cookies2 += name.substring(firstindex ,lastindex) ;
                                        // cookies2 +="; " ;
                                        //}
                                        rk_line =name.substring(firstindex ,lastindex)+";";
                                    }
                                    //GET SET COOKIE Skey :
                                    if((firstindex=name.indexOf("skey=")) >=0){
                                        lastindex = name.indexOf(";");
                                        //if(lastindex > firstindex+6 ){
                                        //System.out.println(name.substring(firstindex,lastindex));
                                        //cookies2 += name.substring(firstindex ,lastindex) ;
                                        //}
                                        skez_line =name.substring(firstindex ,lastindex)+";";
                                    }
                                    //GET SET COOKIE Uin  :
                                    if((firstindex=name.indexOf("uin=")) >=0){
                                        lastindex = name.indexOf(";");
                                        //if(lastindex > firstindex+6 ){
                                        //cookies2 += name.substring(firstindex ,lastindex) ;
                                        //}
                                        uin_line =name.substring(firstindex ,lastindex)+";";
                                        //System.out.println("Test:"+uin_line);
                                    }

                                    //GET SET COOKIE pt2gguin  :
                                    if((firstindex=name.indexOf("pt2gguin=")) >=0){
                                        lastindex = name.indexOf(";");
                                        //if(lastindex > firstindex+6 ){
                                        //System.out.println("Test2: "+name.substring(firstindex+9,lastindex));
                                        //cookies2 += name.substring(firstindex ,lastindex) ;
                                        //}
                                        main_variables.uin_String = name.substring(firstindex+9,lastindex) ;
                                        main_variables.self_uin = name.substring(firstindex +10 , lastindex ) ;
                                        pt2gguin_line =name.substring(firstindex ,lastindex);
                                    }

                                }

                            }
                        }catch(Exception e){}
                    }
                }
                // System.out.println(line1);
            }
        }

        cookies2 =cookies2+ " "+skez_line +" ptisp=ctc; "+rk_line+" "+ptcz_line + uin_line + pt2gguin_line ;

        //System.out.println("\nThe Cookie = " + cookies2 +"\n");
        return cookies2 ;
    }
}


public class Main{
    public static ArrayList<String> temp = new ArrayList<String>();
    public static void main(String[] args) throws Exception
    {
        String line =null;
        String newCookie=null ;
        get_img x = new get_img();
        get_content newone =new get_content();
        wait_scan y = new wait_scan();
        line =  x.get_img("https://ssl.ptlogin2.qq.com/ptqrshow?appid=501004106&e=0&l=M&s=5&d=72&v=4&t=13123123122314");
        newCookie=y.wait_scan1(line);

        get_recent_list try_ = new get_recent_list();
        String final_cookie = try_.get_recent_list(newCookie) ;
        // System.out.println( final_cookie) ;
        if(true){
            get_self_info self_info = new get_self_info();
            self_info.get_self_info(final_cookie);

            get_uin_psessionid uin_ = new get_uin_psessionid();
            uin_.get_clientid(final_cookie);

            get_user_friends friends = new get_user_friends();
            friends.get_user_friends(final_cookie);

            get_group_name group_list = new get_group_name();
            group_list.get_group_name(final_cookie);


            //get_message message = new get_message();
            if(true){
                Runnable task1 = new Message(final_cookie);
                Runnable task2 = new Message(final_cookie);
                Runnable task3 = new Message(final_cookie);
                Runnable task4 = new Message(final_cookie);
                Thread thread1 =new Thread(task1);
                Thread thread2 =new Thread(task2);
                Thread thread3 =new Thread(task3);
                Thread thread4 =new Thread(task4);
                thread1.start();
                thread2.start();
                thread3.start();
                thread4.start();
            }

            if(false){
                System.out.println("Test Send_message");
                Send_message newSend = new Send_message() ;
                newSend.Send_message(final_cookie,"Hello","3111854659");
                System.out.println("Send_message finished!");
            }

        }

        // while(true){
        //       message.get_message(final_cookie);
        //      Thread.sleep(100);
        //}
    }
}

class Message implements Runnable{
    public static String _cookie ;
    public Message(String cookies ) {
        _cookie =cookies ;
    }
    public void run(){
        get_message message = new get_message();
        while(true){
            try{
                message.get_message(_cookie);
                Thread.sleep(1000);
            }catch(Exception e){}
        }
    }
}


class Reply_Message implements Runnable{
    public static String _cookie ;
    public static String _content;
    public static String _from_uin ;
    public Reply_Message(String cookie , String content ,String from_uin){
        _cookie = cookie ;
        _content = content;
        _from_uin = from_uin ;
    }
    public void run(){
        if(true){
            try{
                Send_message  newreply = new Send_message();
                //System.out.println("_content : "  +  _content);
                newreply.Send_message(_cookie , _content , _from_uin );
                System.out.println("Reply the message from "+ main_variables.map.get(_from_uin) +"Successfully !");
            }catch(Exception e){}
        }
    }
}


class get_uin_psessionid{
    public static void get_clientid(String cookies) throws Exception{
        //System.out.println("\n\n\nuin_Test"+cookies);
        int firstindex,lastindex;
        String clientid_url = "http://d1.web2.qq.com/channel/login2" ;
        String line=null;
        String temp=null;
        Map<String,String> map = new HashMap<String,String>();
        URL url = new URL(clientid_url);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        // conn.setRequestProperty("Charset","UTF-8");
        //conn.setRequestProperty("ptwebqq","");
        //conn.setRequestProperty("clientid","53999199");
        //conn.setRequestProperty("psessionid","");
        //conn.setRequestProperty("status","online");
        conn.setRequestProperty("cookie",cookies);
        conn.setRequestProperty("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
        conn.setRequestProperty("Origin","http://d1.web2.qq.com");
        conn.setDoOutput(true);
        StringBuffer params = new StringBuffer();
        map.put("ptwebqq","");
        map.put("clientid","53999199");
        map.put("psessionid","");
        map.put("status","online");

        String data = "{\"ptwebqq\":\"\",\"clientid\":53999199,\"psessionid\":\"\", \"status\":\"online\"}";

        params.append("r").append("=").append(data);//String param="&ptwebqq="+""+"&clientid="+"53999199"+"&psessionid="+""+"&status"+"online";
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        //OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        //osw.write(param);
        //osw.flush();
        //osw.close();

        InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
        //System.out.println("Start:");
        BufferedReader reader = new BufferedReader( input);
        while((line = reader.readLine() ) !=null){
            //System.out.println(line);
            if((firstindex=line.indexOf("psessionid")) >= 0)
            {
                lastindex=line.indexOf(",",firstindex);
                //System.out.println(temp=line.substring(firstindex,lastindex));
                temp=line.substring(firstindex,lastindex);
                temp = temp.replace("psessionid\":\"","").replace("\"","");
                //System.out.println(temp);
                main_variables.psessionid = temp ;
                System.out.println("----variables  psessionid Is Ready !");
            }
            if((firstindex=line.indexOf("uin")) >= 0)
            {
                lastindex=line.indexOf(",",firstindex);
                // System.out.println(temp=line.substring(firstindex,lastindex));
                temp=line.substring(firstindex,lastindex);
                temp = temp.replace("uin\":","");
                //System.out.println(temp);
                main_variables.uin = temp ;
                main_variables.hash = Qhash.qhash(temp) ;
                System.out.println(main_variables.hash);
                System.out.println("----variables  uin Is Ready !");
            }
        }
    }
}


class get_recent_list{  //find the finall cookies
    public static String jump_url ;
    public static String get_recent_list(String cookies)throws Exception{


        //System.out.println("Jump_url = "+ jump_url) ;
        //String new_cookie = cookie + "p_uin=o3039649003; p_skey=PCK6WPBN4KZDUyeOAJQC9vJUPuPhdcDdvN5vYjpeuEA_; pt4_token=3kh4Mr76Yk5MIaHjIcpXPtxaEADniVdcgRSBu1e2S9I_";
        //String second_url = "http://s.web2.qq.com/api/get_self_info2?t=1508925880358";

        //System.out.println("\n"+new_cookie);
        String final_cookie =cookies +"; ";
        //System.out.println("\nrequest_url:"+jump_url) ;

        if(jump_url.startsWith("\uFEFF")){ //line = line.substring(1);
            jump_url = jump_url.replace("\uFEFF", "");
        }

        // System.out.println("\nrequest_cookie:" +cookies);
        URL url = new URL(jump_url);
        String line =null ;
        HttpURLConnection conn2 = (HttpURLConnection)url.openConnection();
        //conn2.setRequestMethod("GET");
        conn2.setRequestProperty("cookie",cookies);
        conn2.setInstanceFollowRedirects(false);
        //conn2.setRequestProperty("Host","ptlogin2.web2.qq.com");
        //conn2.setRequestProperty("Upgrade-Insecure-Requests","1");
        //conn2.setRequestProperty("Connection","keep-alive");
        //conn2.setRequestProperty("Accept-Encoding","gzip, deflate");
        //conn2.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
        //conn2.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        //conn2.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");
        //conn2.connect();
        InputStreamReader input = new InputStreamReader(conn2.getInputStream(),"utf-8");
        //System.out.println("Start:");
        BufferedReader reader = new BufferedReader( input);
        String line2;
        String key ;
        //System.out.println(conn.getHeaderField("Set-Cookie"));

        //while((line2= reader.readLine())!= null){
        //System.out.println(line2);
        Map<String , List<String>> es = conn2.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : es.entrySet()) {
            int firstindex , lastindex;
            List<String> temp ;
            // System.out.println("Key : " + entry.getKey() +
            //                  " ,Value : " + entry.getValue());
            key = entry.getKey();
            // System.out.println("Key:"+key+entry.getValue());


            try{
                if(key.equals("Set-Cookie"))
                {
                    temp = entry.getValue();

                    //System.out.println(temp);
                    for(String name :temp){

                        if((firstindex=name.indexOf("p_skey=")) >=0){
                            lastindex = name.indexOf(";");
                            if(lastindex > firstindex+7 ){
                                //System.out.println(name.substring(firstindex,lastindex));
                                final_cookie += name.substring(firstindex,lastindex)+"; " ;
                                // cookies2 += name.substring(firstindex ,lastindex) ;
                                // cookies2 +="; " ;
                                // ptcz_line = name.substring(firstindex ,lastindex) ;
                            }
                        }

                        if((firstindex=name.indexOf("pt4_token=")) >=0){
                            lastindex = name.indexOf(";");
                            if(lastindex > firstindex+10 ){
                                // System.out.println(name.substring(firstindex,lastindex));
                                final_cookie += name.substring(firstindex,lastindex) +"; ";
                                // cookies2 += name.substring(firstindex ,lastindex) ;
                                // cookies2 +="; " ;
                                // ptcz_line = name.substring(firstindex ,lastindex) ;
                            }
                        }



                    }

                }
            }catch(Exception e){}




        }
        // System.out.println("finall: "+ final_cookie);
        return final_cookie+" p_uin="+main_variables.uin_String ;
    }

}

class get_self_info{
    public static void get_self_info(String cookies)throws Exception{
        String self_info_url = "http://s.web2.qq.com/api/get_self_info2?t=1518933696964";
        int firstindex,lastindex;
        URL url = new URL(self_info_url);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //conn.setRequestProperty("Referer",refer);
        conn.setRequestProperty("cookie",cookies);
        conn.setRequestProperty("Referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
        // System.out.println("\nSelf:"+cookies);

        InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");

        BufferedReader reader = new BufferedReader( input);
        String line1;
        while((line1 = reader.readLine())!=null ){
            if((firstindex=line1.indexOf("vfwebqq")) >=0)
            {
                lastindex = line1.indexOf(",",firstindex) ;
                //System.out.println(line1=line1.substring(firstindex,lastindex));
                line1=line1.substring(firstindex,lastindex);
                line1 = line1.replace("vfwebqq\":\""  ,"");
                line1 = line1.replace("\"","");
                //System.out.println(line1);
                main_variables.vfwebqq = line1 ;
                System.out.println("----variables  vfwebqq Is Ready !");

            }
        }
    }
}

class get_user_friends{
    public static void get_user_friends(String cookies ) throws Exception{
        String line ;
        String line2;
        //String list[] ;
        //String[] result =null;

        int i =0;
        int firstindex , lastindex ;
        URL url = new URL("http://s.web2.qq.com/api/get_user_friends2");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("cookie",cookies);
        conn.setRequestProperty("Referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
        conn.setRequestProperty("Origin","http://s.web2.qq.com");
        conn.setDoOutput(true);
        StringBuffer params = new StringBuffer();
        //0052003E00EA0006
        //00F0006E001300A0

        String data = "{\"vfwebqq\":\"" + main_variables.vfwebqq+ "\",\"hash\":\""+ main_variables.hash +"\"}" ;
        //System.out.println(data);
        params.append("r").append("=").append(data);//String param="&ptwebqq="+""+"&clientid="+"53999199"+"&psessionid="+""+"&status"+"online";
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        //OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        //osw.write(param);
        //osw.flush();
        //osw.close();

        InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
        //System.out.println("Start:");
        BufferedReader reader = new BufferedReader( input);
        while( (line =reader.readLine() )!= null)
        {
            if((firstindex=line.indexOf("\"info"))>=0 ) {
                lastindex = line.indexOf("]",firstindex);
                line2 = line.substring(firstindex,lastindex);
                System.out.println("------------------Getting your friends lists:------------------");

            }
            else continue;
            String[] list = line2.split("\\{");
            for(String temp : list)
            {
                //System.out.println(temp);
                if(true){
                    if(temp.indexOf("u") >= 0 )
                    {
                        if((firstindex = temp.indexOf("nick"))  >= 0 )
                        {
                            lastindex = temp.indexOf("}",firstindex);
                        }
                        //System.out.println(temp.substring(firstindex, lastindex ));

                        temp = temp.substring(firstindex,lastindex) ;
                        //get the nick clean
                        firstindex = temp.indexOf("nick\":");
                        lastindex  =temp.indexOf("," ,firstindex);
                        String nick = temp.substring(firstindex+6,lastindex);
                        // System.out.println(nick);

                        //get the nick clean
                        firstindex = temp.indexOf("uin\":");
                        //lastindex  =temp.indexOf("," ,firstindex);
                        String uin = temp.substring(firstindex+5);
                        // System.out.println(uin);
                        //System.out.println(temp.substring(firstindex+5));
                        // result[i++] = temp ;
                        main_variables.map.put(uin,nick);
                    }
                }
            }

            //System.out.println(line);
        }
        for(String key: main_variables.map.keySet() )
            System.out.println(""+key+":"+main_variables.map.get(key));
    }
}

class get_group_name{
    public static void get_group_name(String cookies ) throws Exception{
        String line ;
        String line2;
        //String list[] ;
        String[] result =null;

        int i =0;
        int firstindex , lastindex ;
        URL url = new URL("http://s.web2.qq.com/api/get_group_name_list_mask2");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("cookie",cookies);
        conn.setRequestProperty("Referer","http://s.web2.qq.com/proxy.html?v=20130916001&callback=1&id=1");
        conn.setRequestProperty("Origin","http://s.web2.qq.com");
        conn.setDoOutput(true);
        StringBuffer params = new StringBuffer();
        //0052003E00EA0006
        //00F0006E001300A0
        String data = "{\"vfwebqq\":\"" + main_variables.vfwebqq+ "\",\"hash\":\""+main_variables.hash+"\"}" ;
        //System.out.println(data);
        params.append("r").append("=").append(data);//String param="&ptwebqq="+""+"&clientid="+"53999199"+"&psessionid="+""+"&status"+"online";
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        //OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        //osw.write(param);
        //osw.flush();
        //osw.close();

        InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
        //System.out.println("Start:");
        BufferedReader reader = new BufferedReader( input);
        while( (line =reader.readLine() )!= null)
        {
            // System.out.println(line);
            if((firstindex=line.indexOf("\"gnamelist"))>=0 ) {
                lastindex = line.indexOf("]",firstindex);
                line2 = line.substring(firstindex,lastindex);
                System.out.println("------------------Getting your Grop lists:------------------");

            }
            else continue;
            String[] list = line2.split("\\{");
            for(String temp : list)
            {
                if(temp.indexOf("flag") >= 0 )
                {
                    if((firstindex = temp.indexOf("name"))  >= 0 )
                    {
                        lastindex = temp.indexOf("}",firstindex);
                    }
                    System.out.println(temp.substring(firstindex, lastindex ));
                    // result[i++] = temp ;
                }
            }

            //System.out.println(line);
        }
    }
}

class get_message{
    public static void get_message(String cookies ) throws Exception{
        String line ;
        String line2;
        //String list[] ;
        String[] result =null;

        int i =0;
        int firstindex , lastindex ;
        URL url = new URL("http://d1.web2.qq.com/channel/poll2");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("cookie",cookies);
        conn.setRequestProperty("Referer","http://d1.web2.qq.com/proxy.html?v=20151105001&callback=1&id=2");
        conn.setRequestProperty("Origin","http://d1.web2.qq.com");
        conn.setDoOutput(true);
        StringBuffer params = new StringBuffer();

        String data = "{ \"ptwebqq\":\"\",\"clientid\":53999199,\"psessionid\":\"" + main_variables.psessionid+ "\",\"key\":\"\"}" ;
        //System.out.println(data);
        params.append("r").append("=").append(data);//String param="&ptwebqq="+""+"&clientid="+"53999199"+"&psessionid="+""+"&status"+"online";
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes);// 输入参数
        //OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        //osw.write(param);
        //osw.flush();
        //osw.close();

        InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
        //System.out.println("Start:");
        BufferedReader reader = new BufferedReader( input);
        while( (line =reader.readLine() )!= null)
        {
            // System.out.println(line);
            if(line.indexOf("errmsg") < 0 ){
                firstindex = line.indexOf("\"value");
                lastindex = line.indexOf("}");
                lastindex = line.indexOf("}",lastindex+1);
                //System.out.println(line.substring(firstindex,lastindex+1));
                line = line.substring(firstindex,lastindex+1) ;
                firstindex = line.indexOf("}");
                lastindex = line.indexOf("}" , firstindex+1 );
                //System.out.println(line.substring(firstindex ,lastindex+1));
                line = line.substring(firstindex ,lastindex+1) ;
                //get the content clean
                firstindex = line.indexOf("\"") ;
                lastindex = line.indexOf("\"" , firstindex +1 ) ;
                String content = line.substring(firstindex+1 , lastindex );
                //get the from_uin clean
                firstindex = line.indexOf("from_uin\":");
                lastindex  = line.indexOf("," , firstindex) ;
                String from_uin = line.substring(firstindex+10 , lastindex ) ;
                //get the to_uin clean
                firstindex = line.indexOf("to_uin\":");
                lastindex =line.indexOf("}" , firstindex ) ;
                String to_uin = line.substring(firstindex+8 , lastindex ) ;
                //  System.out.println(to_uin);


                if(!from_uin.equals(main_variables.self_uin)){
                    System.out.println("Receive Content from " + main_variables.map.get(from_uin) +" : " + content);
                    System.out.println("from_uin :"+from_uin + "content : " +content  );
                    TuLingAPI cpi_ = new TuLingAPI(content) ;
                    content = cpi_.GetReply();

                    System.out.println(content );
                    Thread thread4 = new Thread(new Reply_Message(cookies , content , from_uin )) ;
                    thread4.start();
                    thread4.join();
                }




            }
        }

    }
}

class Send_message{
    public void Send_message(String cookies , String message ,String to_uin)throws Exception{
        String requestUrl = "http://d1.web2.qq.com/channel/send_buddy_msg2";
        int i = 0 ;
        String msg_id ="22420000";


        URL url = new URL(requestUrl) ;
        String line ;
        HttpURLConnection conn =(HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("cookie" , cookies);
        conn.setRequestProperty("Referer","http://d1.web2.qq.com/cfproxy.html?v=20151105001&callback=1");
        conn.setRequestProperty("Origin","http://d1.web2.qq.com");
        conn.setDoOutput(true) ;
        StringBuffer params =new StringBuffer();

        String data = "{\"to\":" +to_uin+",\"content\":\"[\\\""   + message+   "\\\",[\\\"font\\\",{\\\"name\\\":\\\"宋体\\\",\\\"size\\\":10,\\\"style\\\":[0,0,0],\\\"color\\\":\\\"000000\\\"}]]\", \"face\":522,\"clientid\":53999199,\"msg_id\":" + msg_id +",\"psessionid\":\""+main_variables.psessionid+"\"}";
        //  System.out.println(data) ;
        params.append("r").append("=").append(data);
        byte[] bypes = params.toString().getBytes();
        conn.getOutputStream().write(bypes) ;
        InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
        BufferedReader reader = new BufferedReader(input);

        // while((line = reader.readLine()) != null){
        //      System.out.println(line);

        // }

    }
}



class main_variables{
    public static String vfwebqq;
    public static String uin ;
    public static String psessionid;
    public static String clientid = "53999199";
    public static String uin_String ;
    public static String self_uin ;
    public static String hash ;
    public static Map<String,String> map = new HashMap<String,String>();


}


class get_content{
    public static void get_content(String url_)throws Exception{
        URL url = new URL(url_);
        HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
        InputStreamReader input = new InputStreamReader(httpConn.getInputStream(),"utf-8");
        BufferedReader reader = new BufferedReader( input);
        StringBuilder sb = new StringBuilder();
        String line ;
        String Filename;
        int lastindex ;
        int firstindex;
        String[] info ;
        OutputStreamWriter writer =null;



        while( (line= reader.readLine() )!= null ){
            sb.append(line);
            if((firstindex=line.indexOf("class=\"mbtitle\">"))>0)
            {

                lastindex= line.indexOf("</div>");
                try {
                    Filename =line.substring(firstindex +16, lastindex);

                    File fp = new File(Filename +".txt");
                    FileOutputStream out = new FileOutputStream(fp);
                    writer =new OutputStreamWriter(out,"utf-8");

                    //writer.append(Filename+"\n");
                    //
                    //System.out.println(line.substring(firstindex +16, lastindex));
                }catch (Exception e){}
            }
            //  System.out.println(line);
        }
        if((firstindex=sb.toString().indexOf("class=\"mbtitle\">"  )) >0){
            line =sb.toString().substring(firstindex);
            // System.out.println(line);
            line=line.replaceAll("</p>", "");
            info = line.split("<p>");
            for(int i =1 ; i<info.length -1 ; i++) {
                writer.append(info[i]+"\n");
                //System.out.println(info[i]);
            }
        }

    }

}

class TuLingAPI{
    public static String _content ;
    public TuLingAPI(String content) {
        _content = content ;
    }
    public static String GetReply() throws Exception{
        URL url = new URL ("http://www.tuling123.com/openapi/api");
        //URLEncoder.encode(value,"UTF-8");
        String line ;
        String result =null;
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        StringBuffer params = new StringBuffer();
        String line1 = "1";
        String data = "key=a1019f21b92a4b3bb6261a3e760c2415&info="+_content+"&userid=123456" ;
        //String charset = "UTF-8";
        System.out.println(data);
        params.append(data);//String param="&ptwebqq="+""+"&clientid="+"53999199"+"&psessionid="+""+"&status"+"online";
        //params.append("key").append("=").append("a1019f21b92a4b3bb6261a3e760c2415").append("&").append("\"info\"").append(":").append("\"1\"").append("&").append("userid").append(":").append("124567");
        byte[] bypes = params.toString().getBytes();
        //OutputStreamWriter writer = new OutputStreamWriter(cnnn.getOutputStream(), charset);
        conn.getOutputStream().write(bypes);
        InputStreamReader input = new InputStreamReader(conn.getInputStream(),"utf-8");
        //System.out.println("Start:");
        BufferedReader reader = new BufferedReader( input);
        while((line = reader.readLine()) != null)
        {
            int firstindex , lastindex ;
            if((firstindex=line.indexOf("text\":") )>=0 )
            {
                lastindex = line.indexOf("\"" , firstindex+7) ;
                result = line.substring(firstindex+7 , lastindex );
            }

        }
        return result;
    }


}

class Qhash{
    public static String qhash(String y ){
        BigInteger x= new BigInteger(y);

        int len = x.toString(2).length();

        //String x = "3039649003" ;
        int a[] = { 0 , 0 , 0 , 0 } ;
        int b[] = { 0 , 0 , 0 , 0 } ;
        String c = "ECOK" ;
        int d[] ={ 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0};
        //b[0] = ((3039649003>>>24) & 255) ^ 69 ;
        //b[1] = ((3039649003>>16) & 255)  ^ 67 ;
        //b[2] = ((3039649003>>8) & 255)  ^ 79 ;
        //b[3] = ((3039649003>>0) & 255)  ^ 75 ;
/***************************************************************************/
        String line  = x.toString(2).substring(0,len-24);

        line = line.substring(line.length()-8,line.length());

        int result = 0 ;
        for(int i = 0 ; i <line.length() ; i ++){
            //System.out.println("result:"+ result + "&" + (line.charAt(i) - '0' ));
            result = result*2 + (line.charAt(i)-'0');
        }
        result = result^69 ;
        b[0] = result ;

/***************************************************************************/
/***************************************************************************/
        line  = x.toString(2).substring(0,len-16);

        line = line.substring(line.length()-8,line.length());

        result = 0 ;
        for(int i = 0 ; i <line.length() ; i ++){
            //System.out.println("result:"+ result + "&" + (line.charAt(i) - '0' ));
            result = result*2 + (line.charAt(i)-'0');
        }
        result = result^67 ;
        b[1] = result ;
/***************************************************************************/
        line  = x.toString(2).substring(0,len-8);

        line = line.substring(line.length()-8,line.length());

        result = 0 ;
        for(int i = 0 ; i <line.length() ; i ++){
            //System.out.println("result:"+ result + "&" + (line.charAt(i) - '0' ));
            result = result*2 + (line.charAt(i)-'0');
        }
        result = result^79 ;
        b[2] = result ;
/***************************************************************************/
        line  = x.toString(2).substring(0,len-0);

        line = line.substring(line.length()-8,line.length());

        result = 0 ;
        for(int i = 0 ; i <line.length() ; i ++){
            //System.out.println("result:"+ result + "&" + (line.charAt(i) - '0' ));
            result = result*2 + (line.charAt(i)-'0');
        }
        result = result^75 ;
        b[3] = result ;
/***************************************************************************/


        for(int j = 0 ; j < 8 ; j++){
            if(j % 2 == 0 )
                d[j] = a[j>>1];
            else
                d[j] = b[j>>1];
        }


        String  N1 = "0123456789ABCDEF"  ;
        String  V1 = "" ;
        for( int i = 0 ; i < 8 ; i ++){
            //System.out.println((d[i]>>4) & 15);
            V1 +=  N1.charAt((d[i]>>4) & 15);
            V1 +=  N1.charAt((d[i]>>0) & 15);
        }
        System.out.println(V1);
        return V1 ;
        // System.out.println(V1) ;

    }
}