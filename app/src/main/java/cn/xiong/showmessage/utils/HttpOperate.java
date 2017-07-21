package cn.xiong.showmessage.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;


import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;

public class HttpOperate {

	private static HttpOperate instance;

	public static HttpOperate getInstance() {
		if (instance == null) {
			instance = new HttpOperate();
		}
		return instance;
	}

	/*
	 * Get提交
	 */
	public String HttpGet(String urlString) {
		String result = "";
		try {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] data = new byte[1024];
			int len = 0;
			//URL url = new URL(URLEncoder.encode(urlString,"utf-8"));
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream inStream = conn.getInputStream();
			while ((len = inStream.read(data)) != -1) {
				outStream.write(data, 0, len);
			}
			inStream.close();
			result = new String(outStream.toByteArray(), "UTF-8"); // ͨ��out.Stream.toByteArray��ȡ��д������
		} catch (Exception e) {
			if (e.getMessage() != null) {
				Log.e("HomeSubData", e.getMessage());
				result = "";
			}
		}
		return result;
	}

	public String HttpGet(String urlString, String json) {
		String result = "";
		System.out.println("url-------->" + urlString);
		try {
			byte[] data = json.getBytes();
			//URL url = new URL(urlString);
			URL url = new URL(URLEncoder.encode(urlString, "utf-8"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(15 * 1000);
			OutputStream outStream = conn.getOutputStream();
			outStream.write(data);
			outStream.flush();
			outStream.close();
			if (conn.getResponseCode() == 200) {
				System.out.println("连接成功");
			}
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(100);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			result = new String(baf.toByteArray(), "UTF-8");
			System.out.println("数据------->" + result);
		} catch (Exception e) {
			if (e.getMessage() != null) {
				Log.e("HomeSubData", e.getMessage());
				result = "";
			}
		}
		return result;

	}

	/*
	 * Post提交
	 */
	public String HttpPost(String urlString, String json) {
		String result = "";
		System.out.println("url-------->" + urlString);
		try {
			byte[] data = json.getBytes();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			conn.setRequestProperty("Content-Length",
					String.valueOf(data.length));
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(15 * 1000);
			OutputStream outStream = conn.getOutputStream();
			outStream.write(data);
			outStream.flush();
			outStream.close();
			if (conn.getResponseCode() == 200) {
				System.out.println("连接成功");
			}
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(100);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			result = new String(baf.toByteArray(), "UTF-8");
			System.out.println("数据------->" + result);
		} catch (Exception e) {
			if (e.getMessage() != null) {
				Log.e("HomeSubData", e.getMessage());
				result = "";
			}
		}
		return result;

	}

	/*
	 * Post提交
	 */
	public String HttpPost(String urlString, String hearder, String body) {
		String result = "";
		System.out.println("url-------->" + urlString);
		try {
			byte[] data = body.getBytes();
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type",
					"application/json; charset=UTF-8");
			conn.setRequestProperty("sessionId", hearder);
			conn.setConnectTimeout(5 * 1000);
			conn.setReadTimeout(15 * 1000);
			OutputStream outStream = conn.getOutputStream();
			outStream.write(data);
			outStream.flush();
			outStream.close();
			if (conn.getResponseCode() == 200) {
				System.out.println("连接成功");
			}
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(100);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			result = new String(baf.toByteArray(), "UTF-8");
			System.out.println("数据------->" + result);
		} catch (Exception e) {
			if (e.getMessage() != null) {
				Log.e("HomeSubData", e.getMessage());
				result = "";
			}
		}
		return result;

	}
}