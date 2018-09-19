package top.palexu.shanxuntest;

import android.util.Log;

import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.security.MessageDigest;
import java.math.BigInteger;


/**
 * Created by xjy on 2016/11/6.
 */
public class SNAccount {
    final private String prefix="\r\n";
    final private String share_key="singlenet01";

    public String calc_pin(String username){
    	long time=(long)System.currentTimeMillis()/1000;
    	return calc_pin(username,time);
    }

    public String calc_pin(String username,long timestamp){
        username=username.toUpperCase();
        long time = timestamp;
        long time_div_by_five = time/5;

        long[] time_hash=new long[4];
        for(int i=0;i<4;i++){
            for(int j =0;j<8;j++){
                time_hash[i]=time_hash[i]+(((time_div_by_five >> (i+4*j))&1)<<(7-j));
            }
        }

        byte[] pin27_byte=new byte[6];
        pin27_byte[0] = (byte) ((time_hash[0] >> 2) & 0x3F);
        pin27_byte[1] = (byte) (((time_hash[0] & 0x03) << 4 & 0xff) | ((time_hash[1] >> 4) & 0x0F));
        pin27_byte[2] = (byte) (((time_hash[1] & 0x0F) << 2 & 0xff) | ((time_hash[2] >> 6) & 0x03));
        pin27_byte[3] = (byte) (time_hash[2] & 0x3F);
        pin27_byte[4] = (byte) ((time_hash[3] >> 2) & 0x3F);
        pin27_byte[5] = (byte) ((time_hash[3] & 0x03) << 4 & 0xff);

        for(int i=0;i<6;i++){
            if(((pin27_byte[i] + 0x20) & 0xff) < 0x40){
                pin27_byte[i]=(byte) ((pin27_byte[i] + 0x20) & 0xff);
            } else {
                pin27_byte[i]=(byte) ((pin27_byte[i] + 0x21) & 0xff);
            }
        }
        String pin27_str = "";
        pin27_str = new String(pin27_byte);

        byte[] before_md5 = append(long2bytes(time_div_by_five),string2bytes(username.split("@")[0]+ this.share_key)) ;
        String pin89_str = md5_hexdigest(before_md5).substring(0,2);

        String pin = prefix + pin27_str + pin89_str;

        Log.d("pin:",pin);
        return pin;
    }

    private byte[] long2bytes(long time_div_by_five){
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream d = new DataOutputStream(b);
        try{
        	int inttime=(int)time_div_by_five;
            d.writeInt(inttime);
        }catch(Exception e){

        }
        byte[] result = b.toByteArray();
        return result;
    }
    
    private byte[] string2bytes(String src){
    	byte[] rs = null;
    	try{
        rs= src.getBytes("utf-8");
    	}catch(Exception e){}
    	return rs;
    }

    private String test_2hexstring(String src){
        byte[] digest = null;
        try {
             digest = src.getBytes("UTF-8");
        }catch (Exception e){e.printStackTrace();}
        BigInteger bigInt = new BigInteger(1,digest);
        String hextext = bigInt.toString(16);
        return hextext;
    }

    private String md5_hexdigest(byte[] bytesIn){
        String hashtext="";
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesIn);
            BigInteger bigInt = new BigInteger(1,digest);
            hashtext = bigInt.toString(16);
            }catch(Exception e){
        }
        return hashtext;
    }
    
    private byte[] append(byte[] data1,byte[] data2){
    	byte[] data3 = new byte[data1.length+data2.length];
    	 System.arraycopy(data1,0,data3,0,data1.length);
    	 System.arraycopy(data2,0,data3,data1.length,data2.length);
    	 return data3;
    }

    public String makeUsername(String username){
        String pin = this.calc_pin(username);
        Log.d("realUsername:",pin+username);
        return pin+username;
    }

    public String makeUsername(String username,long time){
        String pin = this.calc_pin(username,time);
        return pin+username;
    }
    
    

    public static void main(String [] args){
        SNAccount sna=new SNAccount();
        String s=sna.calc_pin("15381126745",295687416);
         System.out.println(s);
        // sna.md5_hexdigest("hello");
//        String s=sna.struct_pack(295687416);
//        String d=sna.md5_hexdigest(s);
//        String d=sna.packmd5(295687416);
//        System.out.println(d);
    }

}
