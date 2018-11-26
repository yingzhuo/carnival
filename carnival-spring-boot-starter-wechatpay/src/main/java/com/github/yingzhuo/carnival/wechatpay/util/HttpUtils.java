package com.github.yingzhuo.carnival.wechatpay.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.util.HttpAsyncClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;

/**
 * Created by admin on 2015/6/30.
 */
public class HttpUtils {

	private static final String DEFAULT_CHARSET = "UTF-8";

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

	private static final int TIME_OUT = 20 * 1000;

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		LOGGER.info(">> url = {}", url);
//		StringBuilder bufferRes = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			String response = restTemplate.getForObject(url, String.class);
			LOGGER.info("<< response = {}", response);
			return response;
//			URL urlGet = new URL(url);
//			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
//			// 连接超时
//			http.setConnectTimeout(25000);
//			// 读取超时 --服务器响应比较慢，增大时间
//			http.setReadTimeout(25000);
//			http.setRequestMethod("GET");
//			http.setRequestProperty("Content-Type", "application/json");
//			http.setDoOutput(true);
//			http.setDoInput(true);
//			http.connect();
//
//			InputStream in = http.getInputStream();
//			BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
//			String valueString;
//			bufferRes = new StringBuilder();
//			while ((valueString = read.readLine()) != null) {
//				bufferRes.append(valueString);
//			}
//			in.close();
//
//			// 关闭连接
//			http.disconnect();
//			LOGGER.info("<<");
//
//			return bufferRes.toString();
		} catch (Exception e) {

			LOGGER.error("<< url = {}", url, e);
			return null;
		}
	}

	/**
	 * 发送Get请求
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, Map<String, String> params) {
		String res = get(initParams(url, params));
		return res;
	}

	/**
	 * 发送form格式的post请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String syncFormPost(String url, Map<String, String> params) {
		LOGGER.info(">> url = {}, params = {}", url, params);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		params.forEach((k,v) -> {
			map.add(k, v);
		});

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		String res = response.getBody();
		LOGGER.info("<< res = {}", res);
		return res;
	}
	
	public static String syncXmlPost(String url, String xml) {
		LOGGER.info(">> url = {}, xml = {}", url, xml);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType(MediaType.APPLICATION_XML, Charset.forName("utf-8")));
//		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//		headers.setContentType(type);
//		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> request = new HttpEntity<>(xml, headers);


//		RestTemplate restTemplate = new RestTemplate();

		//SpringBoot中，RestTemplate中文乱码解决方案 StringHttpMessageConverter类，默认是的编码是ISO-8859-1：
		SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
		httpRequestFactory.setReadTimeout(35000);
		httpRequestFactory.setConnectTimeout(5000);
//		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		/** 解决乱码的converter */
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName
				("UTF-8"));
		messageConverters.add(stringHttpMessageConverter);
		messageConverters.add(new ResourceHttpMessageConverter());
		messageConverters.add(new SourceHttpMessageConverter());
		messageConverters.add(new AllEncompassingFormHttpMessageConverter());
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		restTemplate.setMessageConverters(messageConverters);

		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
//		LOGGER.info("请求响应结果:{}",response);
		String res = response.getBody();
		LOGGER.info("<<xml请求的返回结果:{}", res);
		return res;
	}



	/**
	 * @param url
	 * @param params
	 * @return
	 */
	public static String initParams(String url, Map<String, String> params) {
		if (null == params || params.isEmpty()) {
			return url;
		}
		StringBuilder sb = new StringBuilder(url);
		if (url.indexOf("?") == -1) {
			sb.append("?");
		} else {
			sb.append("&");
		}
		boolean first = true;
		for (Entry<String, String> entry : params.entrySet()) {
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append("=");
			if (StringUtils.isNotEmpty(value)) {
				try {
					sb.append(URLEncoder.encode(value, DEFAULT_CHARSET));
				} catch (UnsupportedEncodingException e) {

					LOGGER.error("url = {}, error = {}", url, e.getMessage(), e);
				}
			}
		}
		return sb.toString();
	}



	public static void asyncPostJson(String strUrl, String jsonBody, FutureCallback<String> callback) {
		LOGGER.debug(">> url = " + strUrl);
		LOGGER.debug("jsonBody = " + jsonBody);
		HttpUtils.asyncPostJson(strUrl, null, jsonBody, callback);
		LOGGER.debug("<<");
	}

	public static void asyncPostJson(String strUrl, Map<String, String> headers, String jsonBody,
			FutureCallback<String> callback) {
		LOGGER.debug(">> url = " + strUrl);
		LOGGER.debug("jsonBody = " + jsonBody);
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault(); // NOSONAR
		try {

			// Start the client
			httpclient.start();

			// Execute request
			HttpPost httpPost = new HttpPost(strUrl);
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT)
					.setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
			httpPost.setConfig(requestConfig);

			if (headers != null) {
				Iterator<Entry<String, String>> iter = headers.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> kv = iter.next();
					httpPost.setHeader(kv.getKey(), kv.getValue());
				}
			}
			if (StringUtils.isNotEmpty(jsonBody)) {
				StringEntity input = new StringEntity(jsonBody, Charset.forName("UTF-8"));
				input.setContentType("application/json;charset=utf-8");
				httpPost.setEntity(input);
			}

			httpclient.execute(httpPost, new FutureCallback<HttpResponse>() {
				@Override
				public void cancelled() {
					callback.cancelled();
					HttpAsyncClientUtils.closeQuietly(httpclient);
				}

				@Override
				public void completed(HttpResponse httpRes) {
					String msg = HttpUtils.getHttpResponseContent(httpRes);
					callback.completed(msg);
					HttpAsyncClientUtils.closeQuietly(httpclient);

				}

				@Override
				public void failed(Exception e) {
					callback.failed(e);
					HttpAsyncClientUtils.closeQuietly(httpclient);
				}

			});

		} catch (Exception e) {
			LOGGER.warn("failed to send post request.", e);
		}
		LOGGER.debug("<<");
	}
	
//	public static Flux<String> asyncGet02(String url,Map<String, String> headers, Map<String, String> params ) {
//		LOGGER.info("url = {}", url);
//		LOGGER.info("headers = {}", headers);
//		LOGGER.info("params = {}", params);
//		WebClient.Builder builder = WebClient.builder().baseUrl(url);
//		
//		headers.forEach((k,v) -> {
//			builder.defaultHeader(k, v);
//		});
//		
//		WebClient webClient = builder.build();
//		MultiValueMap<String, String> paramsX = new LinkedMultiValueMap<>();
//		params.forEach((k, v) -> paramsX.add(k, v));
//		return webClient.get().uri(uriBuilder -> uriBuilder.queryParams(paramsX).build()).retrieve().bodyToFlux(String.class);
//	}

	public static void asyncGet(String strUrl, Map<String, String> headers, Map<String, String> params,
			FutureCallback<String> callback) {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault(); // NOSONAR

		try {
			// Start the client
			httpclient.start();

			// Execute request
			HttpGet httpGet = new HttpGet(initParams(strUrl, params));
			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT)
					.setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
			httpGet.setConfig(requestConfig);

			if (headers != null) {
				Iterator<Entry<String, String>> iter = headers.entrySet().iterator();
				while (iter.hasNext()) {
					Entry<String, String> kv = iter.next();
					httpGet.setHeader(kv.getKey(), kv.getValue());
				}
			}

			httpclient.execute(httpGet, new FutureCallback<HttpResponse>() {
				@Override
				public void cancelled() {
					callback.cancelled();
					HttpAsyncClientUtils.closeQuietly(httpclient);
				}

				@Override
				public void completed(HttpResponse httpRes) {
					String msg = HttpUtils.getHttpResponseContent(httpRes);
					callback.completed(msg);
					HttpAsyncClientUtils.closeQuietly(httpclient);
				}

				@Override
				public void failed(Exception e) {
					callback.failed(e);
					HttpAsyncClientUtils.closeQuietly(httpclient);
				}

			});

		} catch (Exception e) {
			LOGGER.warn("failed to send post request.", e);
		} finally {
			// try {
			// httpclient.close();
			// } catch (IOException e) {
			// LOGGER.error(e.getMessage(), e);
			// }
		}
		LOGGER.debug("<<");
	}

	public static String syncJsonPost(String strUrl, String jsonBody) {
		return HttpUtils.syncJsonPost(strUrl, new HashMap<String, String>(), jsonBody);
//		LOGGER.debug(">> url = " + strUrl);
//		LOGGER.debug("request body = " + jsonBody);
//		String msg = "";
//		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
//			CloseableHttpResponse response;
//			HttpPost httpPost = new HttpPost(strUrl);
//			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT)
//					.setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
//			httpPost.setConfig(requestConfig);
//			if (StringUtils.isNotEmpty(jsonBody)) {
//				StringEntity input = new StringEntity(jsonBody, Charset.forName("UTF-8"));
//				input.setContentType("application/json;charset=utf-8");
//				httpPost.setEntity(input);
//			}
//			response = httpclient.execute(httpPost);
//			msg = HttpUtils.getHttpResponseContent(response);
//			response.close();
//		} catch (Exception e) {
//			LOGGER.error("<< error", e);
//		}
//		LOGGER.debug("<<");
//		return msg;
	}

	public static String syncJsonPost(String strUrl, Map<String, String> headers, String jsonBody) {
		LOGGER.debug("url = " + strUrl);
		LOGGER.debug("request body = " + jsonBody);
		HttpHeaders headersX = new HttpHeaders();
		headersX.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.forEach((k,v) -> {
			headersX.add(k, v);
		});

		HttpEntity<String> request = new HttpEntity<>(jsonBody, headersX);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(strUrl, request, String.class);
		String res = response.getBody();
		LOGGER.info("<< res = {}", res);
		return res;
		
//		String msg = "";
//		try {
//			URL url = new URL(strUrl);
//			if ("HTTPS".equalsIgnoreCase(url.getProtocol())) {
//				return syncJsonPostHttps(strUrl, headers, jsonBody);
//			}
//		} catch (MalformedURLException e1) {
//			LOGGER.error(e1.getMessage(), e1);
//			return msg;
//		}
//
//		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
//
//			CloseableHttpResponse response;
//
//			HttpPost httpPost = new HttpPost(strUrl);
//			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT)
//					.setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
//			httpPost.setConfig(requestConfig);
//			Iterator<Entry<String, String>> iter = headers.entrySet().iterator();
//			while (iter.hasNext()) {
//				Entry<String, String> kv = iter.next();
//				httpPost.setHeader(kv.getKey(), kv.getValue());
//			}
//
//			if (StringUtils.isNotEmpty(jsonBody)) {
//				StringEntity input = new StringEntity(jsonBody, Charset.forName("UTF-8"));
//				input.setContentType("application/json;charset=utf-8");
//				httpPost.setEntity(input);
//			}
//			response = httpclient.execute(httpPost);
//			msg = HttpUtils.getHttpResponseContent(response);
//			response.close();
//			httpclient.close();
//		} catch (Exception e) {
//			LOGGER.error("error", e);
//		}
//		return msg;
	}

//	public static String syncJsonPostHttps(String strUrl, Map<String, String> headers, String jsonBody) {
//		LOGGER.debug("url = " + strUrl);
//		LOGGER.debug("request body = " + jsonBody);
//		String msg = "";
//
//		try (CloseableHttpClient httpclient = HttpClients.custom().build()) {
//			CloseableHttpResponse response;
//			HttpPost httpPost = new HttpPost(strUrl);
//			RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(TIME_OUT)
//					.setConnectTimeout(TIME_OUT).setSocketTimeout(TIME_OUT).build();
//			httpPost.setConfig(requestConfig);
//			Iterator<Entry<String, String>> iter = headers.entrySet().iterator();
//			while (iter.hasNext()) {
//				Entry<String, String> kv = iter.next();
//				httpPost.setHeader(kv.getKey(), kv.getValue());
//			}
//
//			if (StringUtils.isNotEmpty(jsonBody)) {
//				StringEntity input = new StringEntity(jsonBody, Charset.forName("UTF-8"));
//				input.setContentType("application/json;charset=utf-8");
//				httpPost.setEntity(input);
//			}
//			response = httpclient.execute(httpPost);
//			msg = HttpUtils.getHttpResponseContent(response);
//			response.close();
//			httpclient.close();
//		} catch (Exception e) {
//			LOGGER.error("error", e);
//		}
//		return msg;
//	}

	public static String getHttpResponseContent(HttpResponse response) {
		String msg = "";
		org.apache.http.HttpEntity entity = response.getEntity();
		if (entity != null) {
			InputStream instream = null;
			try {
				instream = entity.getContent();
				BufferedReader bfr = new BufferedReader(new InputStreamReader(instream));
				StringBuffer buffer = new StringBuffer();
				String line = "";
				while ((line = bfr.readLine()) != null){
				    buffer.append(line);
                }
				msg = buffer.toString();
				LOGGER.debug("msg = " + msg);
			} catch (IllegalStateException e) {
				LOGGER.error("Exception", e);
			} catch (IOException e) {
				LOGGER.error("Exception", e);
			} finally {
				try {
					if (instream != null) {
						instream.close();
					}
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
		return msg;
	}

}
