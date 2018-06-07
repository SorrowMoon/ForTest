package com.test.wgy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Date;
import java.util.UUID;

public class SongBehaveReport {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(dataFlow());

	}	
	
	public static String dataFlow() throws Exception {
		int plat = 0;
		int version = 0;
		String uuid = UUID.randomUUID().toString().toLowerCase().replace("-", "");
		String device_id = "123456";		
		long _t = new Date().getTime();
		long kguid = 985805792;
		String token = "cdb7ebf0b57863963644bc3694c3e7d6632746a8ab1fcb413e1efc1e6190a6db";
		int appid = 88;		
		String toMD5 = "_t"+_t+"appid"+appid+"device_id"+device_id+"kguid"+kguid+"plat"+plat+"token"+token+"uuid"+uuid+"version"+version+"R6snCXJgbCaj9WFRJKefTMIFp0ey6Gza";		
		String postData = "[{\"t\":\"p\",\"i\":[\"105437708,240,90\"]},{\"t\":\"d\",\"i\":[105537708]}]";
		
		MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((toMD5+postData).getBytes("UTF-8"));
        byte[] md5Array = md5.digest();        
		String sign = bytesToHex1(md5Array);

		
		String url1 = "http://newsong.kugou.com/api/v1/dataflow/index?"+"&plat="+plat+"&version="+version+"&uuid="+uuid+"&device_id="+device_id+
				"&_t="+_t+"&kguid="+kguid+"&token="+token+"&appid="+appid+"&sign="+sign;
		//newsong.kgidc.cn
		
		URL url2 = new URL(url1);
		HttpURLConnection con = (HttpURLConnection)url2.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		
		PrintWriter pw = new PrintWriter(con.getOutputStream());
		pw.write(postData);
		pw.flush();
		pw.close();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String tempStr = null;
		while((tempStr=br.readLine())!=null) {
			sb.append(tempStr);
		}
		
		return sb.toString();
			
		
		
	}
    public static String bytesToHex1(byte[] md5Array) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < md5Array.length; i++) {
            int temp = 0xff & md5Array[i];
            String hexString = Integer.toHexString(temp);
            if (hexString.length() == 1) {//如果是十六进制的0f，默认只显示f，此时要补上0
                strBuilder.append("0").append(hexString);
            } else {
                strBuilder.append(hexString);
            }
        }
        return strBuilder.toString();
    }
    //通过java提供的BigInteger 完成byte->HexString
    public static String bytesToHex2(byte[] md5Array) {
        BigInteger bigInt = new BigInteger(1, md5Array);
        return bigInt.toString(16);
    }

    //通过位运算 将字节数组到十六进制的转换
    public static String bytesToHex3(byte[] byteArray) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[byteArray.length * 2];
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }

}
