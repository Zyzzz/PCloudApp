package imu.pcloud.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class Information {
	public String getErrorInfo(int errorCode) {
		Properties properties = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/errorCode.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("oh,it looks something is wrong!");
		}
		return properties.getProperty(errorCode + "", "null");
	}
	public String getConfigInfo(String param){
		Properties properties = new Properties();
		InputStream in = this.getClass().getResourceAsStream("/config.properties");
		try {
			properties.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("oh,it looks something is wrong!");
		}
		return properties.getProperty(param, "null");
	}
	
	
}
