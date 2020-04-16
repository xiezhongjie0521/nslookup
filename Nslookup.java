
import com.sun.jndi.dns.DnsContextFactory;
import sun.security.x509.IPAddressName;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.*;

public class Nslookup {
    public static void main(String[] args) {
        try {
//            IPAddressName ipAddressName = new IPAddressName("127.0.0.1");
//            System.out.println(ipAddressName.getName());
//            System.out.println(InetAddress.getLocalHost().getHostName());
//            InetAddress[] xx = Inet4Address.getAllByName("YHG1FYPYSUEM7ZC");
//            System.out.println(Inet4Address.getByName("YHG1FYPYSUEM7ZC"));
//            for (InetAddress inetAddress : xx) {
//                System.out.println(inetAddress.getHostAddress());
//            }
            getDNSRecs("YHG1FYPYSUEM7ZC", "192.168.31.1", new String[]{"A"}, 5000, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getDNSRecs(String domain, String provider,
                                               String [] types, int timeout, int retryCount) throws NamingException {

        ArrayList<String> results = new ArrayList<String>(15);

        Hashtable<String, String> env = new Hashtable<String, String>();

        // 通过环境属性指定Context的工厂类
//        env.put( "java.naming.factory.initial" ,
//                "com.sun.jndi.dns.DnsContextFactory" );
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");

        //设置域名服务器
        env.put(Context.PROVIDER_URL, "dns://" + provider);

        // 连接时间
        env.put( "com.sun.jndi.dns.timeout.initial" , String.valueOf(timeout));

        // 连接次数
        env.put( "com.sun.jndi.dns.timeout.retries" , String.valueOf(retryCount));

        DirContext ictx = new InitialDirContext(env);
        Attributes attrs = ictx.getAttributes(domain, types);
//        Attributes attrsAll = ictx.getAttributes(domain);
//        System.out.println("---------------attrs A---------------");
//        System.out.println(attrs);
//        System.out.println("---------------attrsAll--------------");
//        System.out.println(attrsAll);
//        System.out.println("------------------END-------------------");


        for (Enumeration e = attrs.getAll(); e.hasMoreElements();) {

            Attribute a = (Attribute) e.nextElement();
            System.out.println(a);
            int size = a.size();
            for (int i = 0; i < size; i++) {
                results.add((String) a.get(i));
            }
        }
        return results;
    }
}
