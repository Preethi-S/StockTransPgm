package trnStock;

import static org.junit.Assert.*;
//import java.util.Scanner;
import org.junit.Test;
//import java.util.Arrays;

public class SSStockTest {
	SSStock scomm	= new SSStock("POP","COMMON",8,0,100,12);
	SSStock spref	= new SSStock("ALE","PREFERRED",8,2,100,24);
	@Test
	public void testgetStckInputs() 
	{
	
      assertEquals(0.66666666, scomm.calcDivyield(), 0.01);
	}
	@Test
	public void testcalcDivyieldcom() 
	{
      assertEquals(0.66666666, scomm.calcDivyield(), 0.01);
	}
	@Test
	public void testcalcDivyieldpref() 
	{
      assertEquals(8.3333333, spref.calcDivyield(), 0.01);
	}
	@Test
	public void testcalcPEcom() 
	{
      assertEquals(1.5, scomm.calcPERatio(), 0.01);
	}
	@Test
	public void testcalcPEpref() 
	{
      assertEquals(3.0, spref.calcPERatio(), 0.01);
	}
	
	@Test
	public void testallSharIndex() 
	{
      SSStock[] sArray = new SSStock[3];
      sArray[0] = new SSStock("GIN","COMMON",23,0,100,10);
      sArray[1] = new SSStock("POP","COMMON",8,0,60,20);
      sArray[2] = new SSStock("TEA","COMMON",13,2,100,30);
      double shrIdx = SSStock.calAllShrIdx(sArray);
      assertEquals(18.1712,shrIdx,0.01);
     
	}
	@Test
	public void testStckInputsStckSymbol() 
	{
		
		assertEquals(false, SSStock.chckInputs("","COMMON",8,0,100,12));
      
     
	}
	@Test
	public void testStckInputsType() 
	{
		
		assertEquals(false, SSStock.chckInputs("POP","COMMN",8,0,100,12));
      
     
	}
	@Test
	public void testLDivInputsType() 
	{
		
		assertEquals(false, SSStock.chckInputs("POP","COMMON",-1,0,100,12));
      
     
	}
	@Test
	public void testLFDivInputsType() 
	{
		
		assertEquals(false, SSStock.chckInputs("POP","COMMON",8,-1,100,12));
      
     
	}
	@Test
	public void testPARInputsType() 
	{
		
		assertEquals(false, SSStock.chckInputs("POP","COMMON",8,0,-1,12));
      
     
	}
	@Test
	public void testmPriceInputsType() 
	{
		
		assertEquals(false, SSStock.chckInputs("POP","COMMON",8,0,100,-1));
      
     
	}
}

