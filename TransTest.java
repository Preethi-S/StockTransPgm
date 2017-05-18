package trnStock;

import static org.junit.Assert.*;
import org.junit.Test;
import java.time.*;


public class TransTest
 {
	
	LocalDateTime timeNow = LocalDateTime.now();
	String dtStr = timeNow.toString();
	int duration = timeNow.getMinute();
	int mint = timeNow.getMinute();
	int hour = timeNow.getHour();
	String destStr;
	public static final String secs = ":00";
	
	@Test
	public void testcalcVolWgtStk() {
		Trans[] t = new Trans[3];
	    t[0] = new Trans("POP",'B',5,10);
	    destStr = dtStr.substring(0, 14) + String.valueOf(mint) + secs;
		t[0].transdate = LocalDateTime.parse(destStr);
		t[1] = new Trans("POP",'B',55,90);
		destStr = dtStr.substring(0, 14) + String.valueOf(mint-1) + secs;
		t[1].transdate = LocalDateTime.parse(destStr);
		t[2] = new Trans("POP",'B',5,20);
		destStr = dtStr.substring(0, 14) + String.valueOf(mint-2) + secs;
		t[2].transdate = LocalDateTime.parse(destStr);
		assertEquals(78.4615, Trans.calcVolWgtStk(t, "POP"), 0.01);
		
	}


	
	@Test
	public void testStkType() 
	{
		
		assertEquals(false, Trans.chkInpTran("",'B',10,25));
      
     
	}
	@Test
	public void testBS() 
	{
		
		assertEquals(false, Trans.chkInpTran("POP",'X',10,25));
      
     
	}
	public void testQTY() 
	{
		
		assertEquals(false, Trans.chkInpTran("POP",'B',-1,25));
      
     
	}
	public void testTrdP() 
	{
		
		assertEquals(false, Trans.chkInpTran("POP",'B',10,-1));
      
     
	}
}
