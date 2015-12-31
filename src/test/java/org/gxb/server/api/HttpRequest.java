package org.gxb.server.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import net.sf.json.JSONObject;

public class HttpRequest {
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		String result = "";
		BufferedReader in = null;
		try {
			// String urlNameString = url + "?" + param;
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();

			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();

			if (((HttpURLConnection) connection).getResponseCode() == 404) {
				result = String.valueOf(404);
			} else {

				// 获取所有响应头字段
				Map<String, List<String>> map = connection.getHeaderFields();
				// 遍历所有的响应头字段
				for (String key : map.keySet()) {
					System.out.println(key + "--->" + map.get(key));
				}
				// 定义 BufferedReader输入流来读取URL的响应
				// InputStream is = connection.getInputStream();

				in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					result += line;
				}
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static String SendHttpGet(String url) {
		// get请求返回结果
		StringBuilder  responseResult = new StringBuilder();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = client.execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				responseResult.append(String.valueOf(404));
			} else {
				/** 读取服务器返回过来的json字符串数据 **/
				responseResult.append(response.getStatusLine().getStatusCode());
				responseResult.append("&");
				responseResult.append(EntityUtils.toString(response.getEntity(), "UTF-8"));
			}
		} catch (IOException e) {
			System.out.println("get请求提交失败:" + url + "   " + e);
		}
		return responseResult.toString();
	}
	
	/**
	 * 发送delete请求
	 * 
	 * @param url
	 *            路径
	 * @return
	 */
	public static String SendHttpDelete(String url) {
		// get请求返回结果
		StringBuilder  responseResult = new StringBuilder();
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			// 发送get请求
			HttpDelete deleteRequest = new HttpDelete(url);
			HttpResponse response = client.execute(deleteRequest);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				responseResult.append(String.valueOf(HttpStatus.SC_NOT_FOUND));
			} else {
				/** 读取服务器返回过来的json字符串数据 **/
				responseResult.append(response.getStatusLine().getStatusCode());
				responseResult.append("&");
				responseResult.append(EntityUtils.toString(response.getEntity(), "UTF-8"));
			}
		} catch (IOException e) {
			System.out.println("delete请求提交失败:" + url + "   " + e);
		}
		return responseResult.toString();
	}
	

	/**
	 * post请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static String SendHttpPost(String url,  JSONObject jsonParam) {
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		StringBuilder  responseResult = new StringBuilder();
		HttpPost method = new HttpPost(url);		
		try {
			if (jsonParam != null) {
				// 解决中文乱码问题
				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				method.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(method);

			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				responseResult.append(HttpStatus.SC_NOT_FOUND);
			}
			else
			{
				try {
					/** 读取服务器返回过来的json字符串数据 **/
//					responseResult = EntityUtils.toString(result.getEntity(),"utf-8");		
					responseResult.append(result.getStatusLine().getStatusCode());
					responseResult.append("&");
					responseResult.append(EntityUtils.toString(result.getEntity(),"utf-8"));
				} catch (Exception e) {
					System.out.println("post请求提交失败:" + url + "  " + e);
				}
			}
			
		} catch (IOException e) {
			System.out.println("post请求提交失败:" + url + "  " + e);
		}
		return responseResult.toString();
	}

	/**
	 * put请求
	 * 
	 * @param url
	 *            url地址
	 * @param jsonParam
	 *            参数
	 * @param noNeedResponse
	 *            不需要返回结果
	 * @return
	 */
	public static String SendHttpPut(String url, String jsonParam) {
		// post请求返回结果
		DefaultHttpClient httpClient = new DefaultHttpClient();
		StringBuilder  responseResult = new StringBuilder();
		HttpPut httpPut = new HttpPut(url);

		try {
			if (jsonParam != null) {
				// 解决中文乱码问题
//				StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");
				StringEntity entity = new StringEntity(jsonParam, "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPut.setEntity(entity);
			}
			HttpResponse result = httpClient.execute(httpPut);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				responseResult.append(HttpStatus.SC_NOT_FOUND);
			}
			else
			{
				try {
					/** 读取服务器返回过来的json字符串数据 **/
//					responseResult = EntityUtils.toString(result.getEntity(),"utf-8");		
					responseResult.append(result.getStatusLine().getStatusCode());
					responseResult.append("&");
					responseResult.append(EntityUtils.toString(result.getEntity(),"utf-8"));
				} catch (Exception e) {
					System.out.println("put请求提交失败:" + url + "  " + e);
				}
			}
		} catch (IOException e) {
			System.out.println("post请求提交失败:" + url + "  " + e);
		}
		return responseResult.toString();
	}
	
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String sendHttp(String url, Map<String, String> params) {

		URL u = null;

		HttpURLConnection con = null;

		// 构建请求参数

		StringBuffer sb = new StringBuffer();

		if (params != null) {

			for (Entry<String, String> e : params.entrySet()) {

				sb.append(e.getKey());

				sb.append("=");

				sb.append(e.getValue());

				sb.append("&");

			}

			sb.substring(0, sb.length() - 1);

		}

//		System.out.println("send_url:" + url);

//		System.out.println("send_data:" + sb.toString());

		// 尝试发送请求

		try {

			u = new URL(url);

			con = (HttpURLConnection) u.openConnection();
			
			con.setRequestMethod("POST");

			con.setDoOutput(true);

			con.setDoInput(true);

			con.setUseCaches(false);

			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			
			OutputStreamWriter osw = new OutputStreamWriter(
					con.getOutputStream(), "UTF-8");
			
			osw.write(sb.toString());

			osw.flush();

			osw.close();

		} catch (Exception e) {

			e.printStackTrace();

		} finally {

			if (con != null) {

				con.disconnect();

			}

		}

		// 读取返回内容

		StringBuffer buffer = new StringBuffer();

		try {			
			BufferedReader br = new BufferedReader(new InputStreamReader(con

			.getInputStream(), "UTF-8"));

			String temp;

			while ((temp = br.readLine()) != null) {

				buffer.append(temp);

				buffer.append("\n");

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return buffer.toString();

	}

	public static String getCode(String surl) {
		String code = "Http状态码";
		try {
			// url="http://www.baidu.com";
			URL url = new URL(surl);
			URLConnection rulConnection = url.openConnection();
			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
			httpUrlConnection.setConnectTimeout(300000);
			httpUrlConnection.setReadTimeout(300000);
			httpUrlConnection.connect();
			code = new Integer(httpUrlConnection.getResponseCode()).toString();
			// String message = httpUrlConnection.getResponseMessage();
			// System.out.println("getResponseCode code ="+ code);
			// System.out.println("getResponseMessage message ="+ message);
			if (!code.startsWith("2")) {
				throw new Exception("ResponseCode is not begin with 2,code=" + code);
			}
		} catch (Exception ex) {
			// System.out.println(ex.getMessage());
		}
		return code;
	}
}