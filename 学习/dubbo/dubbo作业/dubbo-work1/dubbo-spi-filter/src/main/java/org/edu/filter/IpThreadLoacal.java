package org.edu.filter;


public final class IpThreadLoacal {

    private static final ThreadLocal<String> ipLocal=new ThreadLocal<String>();

    public static String getIp(){
        return ipLocal.get();
    }

    public static void setIp(String ip){
        ipLocal.set(ip);
    }

    public static void removeIp(){
        ipLocal.remove();
    }
}
