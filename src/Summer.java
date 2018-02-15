import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.csvreader.CsvWriter;


public class Summer {
	static String urlstr = "http://www.workopolis.com";
	
	public static void main(String[] args) {
		HashMap<String, String> anchors = new HashMap<String, String>();
		String outputFile = "summer.csv";
		URL obj;
		HttpURLConnection con = null;
		final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";
		boolean alreadyExists = new File(outputFile).exists();
		try{
			CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');
			
			if (!alreadyExists)
			{
				csvOutput.write("JOB LINK");
				csvOutput.write("JOB TITLE");
				csvOutput.write("COMPANY");
				csvOutput.write("LOCATION");
				csvOutput.write("Date Posted");
				csvOutput.write("Job Type");
				csvOutput.write("Career Level");
				csvOutput.write("Experience");
				csvOutput.write("Education");
				csvOutput.write("Industry");
				csvOutput.write("Job Function");
				csvOutput.write("Website");
				csvOutput.write("Job Details");
				csvOutput.endRecord();
			}
			
			for(int i=1;i<=12;i++){
			try{
				String url = "http://www.workopolis.com/jobsearch/find-jobs?cl=2%7c1&pt=8&lg=en&pn="+i;
				obj = new URL(url);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", USER_AGENT);
				con.setRequestProperty("Host", "www.workopolis.com");
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				con.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
				con.setRequestProperty("http.referer",url);
				//con.setDoOutput(true);
				//con.setDoInput(true);
				//con.setInstanceFollowRedirects(false);
				//con.setUseCaches(false);
				con.setReadTimeout(30000);
				con.setConnectTimeout(30000);

				BufferedReader in2 = null;
				try {
					GZIPInputStream gzipInputStream = new GZIPInputStream(con.getInputStream());

					// in2 = new BufferedReader(new
					// InputStreamReader(con.getInputStream()));
					in2 = new BufferedReader(new InputStreamReader(gzipInputStream, Charset.defaultCharset()));
				} catch (Exception e) {
					in2 = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.defaultCharset()));
					// TODO: handle exception
				}
				StringBuilder stringBuilder = new StringBuilder();
				String inputstr;

				while ((inputstr = in2.readLine()) != null) {
					stringBuilder.append(inputstr + "\n");
				}

				in2.close();
				
				Document doc = Jsoup.parse(stringBuilder.toString());
		        
		        Elements elementsLevel1 = doc.select("a[class=jsResultTitle]");
				for (Element elementLevel1 : elementsLevel1) {
					String str = elementLevel1.attr("href");
					if (str != null && !str.trim().equals("") && elementLevel1.text() != null && !elementLevel1.text().trim().equals("")) {
						anchors.put(elementLevel1.text(), EntryLevel.urlstr + str);
						System.out.println(EntryLevel.urlstr + str);
					}
				}
				}catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}finally{
					if(con!=null){
						con.disconnect();
					}
				}
			}
			
			Iterator<String> iterator = anchors.values().iterator();
			Connection conn = null;
			Class.forName("com.mysql.jdbc.Driver");
			//conn = DriverManager.getConnection("jdbc:mysql://localhost/studeol8_jobportal?user=root&password=password");
			conn = DriverManager.getConnection("jdbc:mysql://student-jobs.ca/studeol8_jobPortal?user=studeol8_scrape&password=root123");
			while (iterator.hasNext()) {
			try
				{
				String TITLE = null,COMPANY = null,LOCATION = "",DATE = null,TYPE=null,LEVEL=null,EXP=null,EDU=null,INDUSTRY=null,JOBFUNCTION=null,WEBSITE=null,DESC = null;
				String key = iterator.next();
				System.out.println(key);
				csvOutput.write(key);
				
				String url = key;
				obj = new URL(url);
				con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				con.setRequestProperty("User-Agent", USER_AGENT);
				con.setRequestProperty("Host", "www.workopolis.com");
				con.setRequestProperty("Connection", "keep-alive");
				con.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				con.setRequestProperty("Accept-Encoding", "gzip,deflate,sdch");
				con.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
				con.setRequestProperty("http.referer",url);
				//con.setDoOutput(true);
				//con.setDoInput(true);
				//con.setInstanceFollowRedirects(false);
				//con.setUseCaches(false);
				con.setReadTimeout(30000);
				con.setConnectTimeout(30000);

				BufferedReader in2 = null;
				try {
					GZIPInputStream gzipInputStream = new GZIPInputStream(con.getInputStream());

					// in2 = new BufferedReader(new
					// InputStreamReader(con.getInputStream()));
					in2 = new BufferedReader(new InputStreamReader(gzipInputStream, Charset.defaultCharset()));
				} catch (Exception e) {
					in2 = new BufferedReader(new InputStreamReader(con.getInputStream(), Charset.defaultCharset()));
					// TODO: handle exception
				}
				StringBuilder stringBuilder = new StringBuilder();
				String inputstr;

				while ((inputstr = in2.readLine()) != null) {
					stringBuilder.append(inputstr + "\n");
				}

				in2.close();
				
				Document doc = Jsoup.parse(stringBuilder.toString());
		        Elements elementsLevel1 = doc.select("h1[class=jobTitle]");
				for (Element elementLevel1 : elementsLevel1) {
					String str = elementLevel1.text();
					if (str != null && !str.trim().equals("") && elementLevel1.text() != null && !elementLevel1.text().trim().equals("")) {
						System.out.println("jobtitle = "+str);
						csvOutput.write(str);
						TITLE = str.replaceAll("/", "-").replaceAll("!", "");
					}
				}
				
				
				Elements elementsLevel2 = doc.select("h2[class=jobCompany]");
				for (Element elementLevel1 : elementsLevel2) {
					String str = elementLevel1.text();
					if (str != null && !str.trim().equals("") && elementLevel1.text() != null && !elementLevel1.text().trim().equals("")) {
						//System.out.println("company = "+str);
						csvOutput.write(str);
						COMPANY = str;
					}
				}
			
				
				Elements elementsLevel3 = doc.select("div[class=jobLocation]");
				for (Element elementLevel1 : elementsLevel3) {
					String str = elementLevel1.text();
					if (str != null && !str.trim().equals("") && elementLevel1.text() != null && !elementLevel1.text().trim().equals("")) {
						//System.out.println("location = "+str);
						csvOutput.write(str);
						LOCATION = str;
					}
				}
				
				
				Elements elementsLevel4 = doc.select("ul[id=job-facets]");
				for (Element elementLevel1 : elementsLevel4) {
					Elements elementsLevel5 = elementLevel1.select("li[id=job-post-date]");
					if(elementsLevel5.size() == 0){
						csvOutput.write("");
						DATE = "0000-00-00";
					}
					for (Element job_post_date : elementsLevel5) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						DATE = str;
					}
					
					Elements elementsLevel6 = elementLevel1.select("li[id=job-position-type]");
					if(elementsLevel6.size() == 0){
						csvOutput.write("");
						TYPE = "";
					}
					for (Element job_post_date : elementsLevel6) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						TYPE = str;
					}
					
					Elements elementsLevel7 = elementLevel1.select("li[id=job-career-level]");
					if(elementsLevel7.size() == 0){
						csvOutput.write("");
						LEVEL = "";
					}
					for (Element job_post_date : elementsLevel7) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						LEVEL = str;
					}
					
					Elements elementsLevel8 = elementLevel1.select("li[id=job-experience]");
					if(elementsLevel8.size() == 0){
						csvOutput.write("");
						EXP = "";
					}
					for (Element job_post_date : elementsLevel8) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						EXP = str;
					}
					
					Elements elementsLevel9 = elementLevel1.select("li[id=job-education]");
					if(elementsLevel9.size() == 0){
						csvOutput.write("");
						EDU = "";
						
					}
					
					for (Element job_post_date : elementsLevel9) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						EDU = str;
					}
					
					Elements elementsLevel10 = elementLevel1.select("li[id=job-industry]");
					if(elementsLevel10.size() == 0){
						csvOutput.write("");
						INDUSTRY = "";
					}
					for (Element job_post_date : elementsLevel10) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						INDUSTRY = str;
					}
					
					Elements elementsLevel11 = elementLevel1.select("li[id=job-category]");
					if(elementsLevel11.size() == 0){
						csvOutput.write("");
						JOBFUNCTION = "";
					}
					for (Element job_post_date : elementsLevel11) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						JOBFUNCTION = str;
					}
					
					Elements elementsLevel12 = elementLevel1.select("li[id=job-company-website]");
					if(elementsLevel12.size() == 0){
						csvOutput.write("");
						WEBSITE = "";
					}
					for (Element job_post_date : elementsLevel12) {
						Elements elementsLevelfinal = job_post_date.select("ul[class=string-list]");
						String str = elementsLevelfinal.text();
						//System.out.println(str);
						csvOutput.write(str);
						WEBSITE = str;
					}
				}
				
				Elements jobdetails = doc.select("div[id=job-details]");
				
				Whitelist whitelist = new Whitelist();
				whitelist.addTags("font");
				whitelist.addTags("ul");
				whitelist.addTags("ol");
				whitelist.addTags("li");
				whitelist.addTags("h1");
				whitelist.addTags("h2");
				whitelist.addTags("h3");
				whitelist.addTags("h4");
				whitelist.addTags("h5");
				whitelist.addTags("h6");
				whitelist.addTags("p");
				whitelist.addTags("br");
				
				for (Element elementLevel1 : jobdetails) {
					String str = elementLevel1.html();
					
					Cleaner cleaner = new Cleaner(whitelist);
					Document doc2 = Jsoup.parse(str);
					Document doc3 =  cleaner.clean(doc2);
					
					/*System.out.println("------------  Old str -----------");
					System.out.println(str);*/
					str = doc3.select("body").html();
					/*System.out.println("------------  new str -----------");
					System.out.println(str);*/
					
					
					if (str != null && !str.trim().equals("") && elementLevel1.text() != null && !elementLevel1.text().trim().equals("")) {
						DESC = str;
						csvOutput.write(str);
					}
				}
				//System.out.println("========================");
				csvOutput.endRecord();
				
				PreparedStatement Selectstmt = null;
				Selectstmt = conn.prepareStatement("SELECT * from `wp_job` WHERE `job_title`=? AND `experience`=? AND `education_level`=? AND `career_level`=? AND `company_name`=? AND `functional_area`=? AND `industry`=? AND `category`=?");
				Selectstmt.setString(1, TITLE);
				Selectstmt.setString(2, EXP);
				Selectstmt.setString(3, EDU);
				Selectstmt.setString(4, LEVEL);
				Selectstmt.setString(5, COMPANY);
				Selectstmt.setString(6, "Summer");
				Selectstmt.setString(7, INDUSTRY);
				Selectstmt.setString(8, JOBFUNCTION);
				ResultSet rs = Selectstmt.executeQuery();
				if(rs.next()){
					PreparedStatement stmt = null;
					stmt = conn.prepareStatement("UPDATE `wp_job` SET `job_city`=?, `job_desc`=?  WHERE `job_title`=? AND `experience`=? AND `education_level`=? AND `career_level`=? AND `company_name`=? AND `functional_area`=? AND `industry`=? AND `category`=?");
					stmt.setString(1, LOCATION);
					stmt.setString(2, DESC+"<br/><a href='"+key+"' target='_blank' rel='nofollow'>click here to see job </a>" + " <br></br> <a href='"+WEBSITE +"' target='_blank' rel='nofollow'>" + COMPANY + "</a>");
					stmt.setString(3, TITLE);
					stmt.setString(4, EXP);
					stmt.setString(5, EDU);
					stmt.setString(6, LEVEL);
					stmt.setString(7, COMPANY);
					stmt.setString(8, "Summer");
					stmt.setString(9, INDUSTRY);
					stmt.setString(10, JOBFUNCTION);
					stmt.executeUpdate();
				}else{
					PreparedStatement stmt = null;
						stmt = conn.prepareStatement("INSERT INTO `wp_job`" +
								"(`job_title`,`company_name`,`job_city`,`display_start_date`,`functional_area`,`career_level`,`experience`,`education_level`,`industry`,`category`," +
								"`job_desc`,`start_date`,`end_date`,`display_end_date`,`job_insert_date`,`last_update`) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						stmt.setString(1, TITLE);
						stmt.setString(2, COMPANY);
						stmt.setString(3, LOCATION);
						stmt.setString(4, "2014-01-01");
						stmt.setString(5, "Summer");
						stmt.setString(6, "");
						stmt.setString(7, EXP);
						stmt.setString(8, EDU);
						stmt.setString(9, INDUSTRY);
						stmt.setString(10, JOBFUNCTION);
						
						/*try{
							
							String pattern = "[<][d][i][v]";
							Pattern r = Pattern.compile(pattern);
							Matcher m = r.matcher(DESC);
							int counter = 0 ;
							while (m.find()) {
								counter++;
							}
							System.out.println("no of div : " + counter);
							System.out.println("**************************");
							String pattern1 = "[<][/][d][i][v]";
							Pattern r1 = Pattern.compile(pattern1);
							Matcher m1 = r1.matcher(DESC);
							int counter1 = 0 ;
							while (m1.find()) {
								counter1++;
							}
							System.out.println("no of /div : " + counter1);
							if(counter == counter1)   {
								System.out.println("No of div matches each other");
							}else if(counter1 < counter)  {
								for(int count = counter - counter1 ; count > 0 ; count--){
									DESC = DESC + "</div>";
								}
							}else if(counter1 > counter)  {
								for(int count = counter1 - counter ; count > 0 ; count--){
									DESC =  "<div>" + DESC;
								}
							}
							
						}catch (Exception e) {
							e.printStackTrace();
						}
						*/
						
						
						stmt.setString(11, DESC+"<br/><a href='"+key+"' target='_blank' rel='nofollow'>click here to see job </a>" + " <br></br> <a href='"+WEBSITE +"' target='_blank' rel='nofollow'>" + COMPANY + "</a>");
						stmt.setString(12, "2014-01-01");
						stmt.setString(13, "2014-12-01");
						stmt.setString(14, "2014-12-01");
						
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date date = new Date();
						simpleDateFormat.format(date);
						stmt.setString(15, simpleDateFormat.format(date));
						stmt.setString(16, simpleDateFormat.format(date));
						
						stmt.executeUpdate();
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}finally{
					if(con!=null){
						con.disconnect();
					}
				}
			}
			csvOutput.close();
			conn.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
