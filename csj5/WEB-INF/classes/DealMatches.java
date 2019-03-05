import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

// Please note that print operations are done on the home page as requested in the assignment.
public class DealMatches extends HttpServlet
{
	static List<String> lineList = new ArrayList<String>();
	public void init()
	{
		try
		{
			String fileName = "C:\\apache-tomcat-7.0.34\\webapps\\csj5\\DealMatches.txt", line;
			BufferedReader br = new BufferedReader(new FileReader (fileName));
			while((line = br.readLine()) != null)
			{
				lineList.add(line);
			}
			setLineList(lineList);
			System.out.println("------------------------"+lineList + lineList.size());
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}  
	
	public void setLineList(List lineList)
	{
		lineList = lineList;
	}
	public static List getLineList()
	{
		return lineList;
	}

}
