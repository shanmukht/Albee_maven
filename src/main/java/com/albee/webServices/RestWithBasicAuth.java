package com.albee.webServices;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.codec.binary.Base64;

import com.albee.DataBeans.WebserviceData;

public class RestWithBasicAuth {
	

		public String restApiwithAuth(WebserviceData wd) {
			
			try {
			String webPage = "http://localhost:8082/service/test/Ashish";
//				String name = "nisum1";
//				String password = "nisum1";
				String endpointUrlUnique="";
				String authString="";
				for(int i=0;i<=(wd.getEndpointUrl().length);i++){
			//		endpointUrlUnique= wd.getEndpointUrl()[i];
					if ((wd.getEndpointUrl()[i]==webPage)&&(wd.getApiType()[i]=="Basic_Authentication")&&(wd.getContent_type()[i]=="application/text")) {
//						
						endpointUrlUnique=wd.getEndpointUrl()[i];
						authString=wd.getAuthUsername()[i]+":"+wd.getAuthPassword()[i];
						System.out.println("Endpoint URL first:" +  endpointUrlUnique);
					break;
					}
				}
				System.out.println("Endpoint URL first:" +  endpointUrlUnique);

			//	String authString = wd.getAuthUsername() + ":" + wd.getAuthPassword();
		
			//	System.out.println("Endpoint URL first:" +  webPage);
			//	String authString = name + ":" + password;
				System.out.println("auth string: " + authString);
				byte[] authEncBytes = Base64.encodeBase64(authString.getBytes()); //encrypting username and password
				String authStringEnc = new String(authEncBytes);
				System.out.println("Base64 encoded auth string: " + authStringEnc);
				System.out.println("Endpoint URL:" +  wd.getEndpointUrl());
			
				

				URL url = new URL(endpointUrlUnique);
			//	URL url = new URL(webPage);
				URLConnection urlConnection = url.openConnection();
				urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
				InputStream is = urlConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				String result = sb.toString();

				System.out.println("*** BEGIN ***");
				System.out.println(result);
				
				System.out.println("*** END ***");
				return result;
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "Successful execution of authorization rest api";
			
		}

	}


