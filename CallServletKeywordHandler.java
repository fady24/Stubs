package com.csc.ignasia.selenium.keywords;

import java.util.Map;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;
import com.csc.ignasia.selenium.HtmlObject;
import com.csc.ignasia.selenium.KeywordResult;
import com.csc.ignasia.selenium.keywords.KeywordHandler;
import com.csc.fsg.ignasia.model.common.ComponentModel;



/**
Keyword Id = 'Sample'
Please see IGNASIA Java Docs at
http://ignasia-javadocs.s3-website-us-east-1.amazonaws.com
to know more about the referenced classes
**/


@Service("CallServletFunction")
public class CallServletFunctionKeywordHandler implements KeywordHandler {

	public KeywordResult process(WebDriver driver, HtmlObject htmlObject, ComponentModel componentModel, Map testData) {
		KeywordResult result = new KeywordResult();
		
		
		result.setResult(false);
		result.setMessage("Nothing executed.");
		URL url = null;
		HttpURLConnection con = null;
		int i = 2, rc = 0;
		String s =null, msg =null;
		Boolean flag = false;
		int p = 0;
		String address = null; 
		String function = null;
		String param[] = new String[5];
		String msg2 = "START... ";
		try {
			param[0] = testData.get(componentModel.getParam1()).toString();
			p++;
		if (param[0] == null || param[0].equals("")) {
			result.setResult(true);
			result.setMessage("GetDBValue skipped");
			return result;
		}
		else
		{
			try{
		param[1] = testData.get(componentModel.getParam2()).toString();
		p++;
		param[2] = testData.get(componentModel.getParam3()).toString();
		p++;
		param[3] = testData.get(componentModel.getParam4()).toString();
		p++;
		param[4] = testData.get(componentModel.getParam5()).toString();
		p++;
		msg2 = msg2 + "added all params... ";
			  }catch(Exception enull)
			  {

			  }
			/* TEST DATA 
			            String tablename = "tablename";
			            String columnname = "columnname";
			            String selector = "selector";
			*/			
			            String[] splitter = param[0].split(",");
			            address = splitter[0];
			            function = splitter[1];
			            msg2 = msg2 + "ADDRESS>" + address + "<";

			            msg2 = msg2 + "FUNCTION>" + function + "<";

			            url = new URL(address);
			            con = (HttpURLConnection) url.openConnection();
			            con.setRequestMethod("GET");
			            con.setRequestProperty("User-Agent", "USER_AGENT");
			            con.setRequestProperty("function",function);
			            con.setRequestProperty("params",p+"");
			            while(i<p)
			            {	
			            	con.setRequestProperty("param"+ i ,param[i]);
			            }
			            rc = con.getResponseCode();
			            s = con.getResponseMessage();
			           	
			            //String table= con.getHeaderField("result");
			            msg= con.getHeaderField("msg");
			            flag = Boolean.parseBoolean(con.getHeaderField("result"));
			          	
			            if(rc==200)
			            {		
			            	result.setResult(flag);
							result.setMessage(msg2 + msg);	
			            }else
			            {
							result.setResult(false);
							result.setMessage(msg2 + "The request to the servlet couldnt be completed. Code returned:" + rc);
			            }  
			        } 
	}catch (Exception e) {

		result.setResult(false);
		result.setMessage(msg2  + "Exception occured. Message: " + e.getMessage());
    }
		return result;
	}
	
}
