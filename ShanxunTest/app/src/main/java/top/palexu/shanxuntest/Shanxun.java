package top.palexu.shanxuntest;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.util.Log;

public class Shanxun {
    String password = null;
    String username = null;
    String router = null;

    final private String url1 = "http://192.168.1.1/userRpm/PPPoECfgRpm.htm?wan=0&wantype=2&acc=";
    final private String url3 = "&psw=";
    final private String url4 = "&confirm=";
    final private String url5 = "&SecType=0&sta_ip=0.0.0.0&sta_mask=0.0.0.0&linktype=4&waittime2=0&Connect=%C1%AC+%BD%D3";
    final private String url6 = "&SecType=0&sta_ip=0.0.0.0&sta_mask=0.0.0.0&linktype=4&waittime2=0&Disconnect=%B6%CF+%CF%DF";

    //
    public Shanxun(String username,String password,String routPassword) throws UnsupportedEncodingException{
        SNAccount sna = new SNAccount();
        this.username = sna.makeUsername(username);
        this.username = Tools.bin2hex(this.username);

        this.password = password;

        String router_key = "admin:" + routPassword;
        this.router = Base64.encode(router_key.getBytes());
    }

    public void send() throws IOException {
        Log.d("send", "start");
        URL url = loginRouterUrl();
        setRouter(url);
    }

    private URL loginRouterUrl() throws MalformedURLException{
        String u = url1 + username + url3 + password + url4 + password + url5;
        URL url = new URL(u);
        return url;
    }

    private void setRouter(URL url) throws IOException{
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Host", "192.168.1.1");
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("Authorization", "Basic " + router);
        connection.setRequestProperty("Cookie", "Authorization=Basic " + router);
        connection.setRequestProperty("Accept",
                "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.76 Safari/537.36");
        connection.setRequestProperty("Referer", url1 + username + url3 + password + url4 + password + url6);
        connection.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");

        connection.setConnectTimeout(1000);

        connection.connect();
        connection.getHeaderFields();
    }

    public String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }
}