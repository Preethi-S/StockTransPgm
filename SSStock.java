package trnStock;

import java.util.Scanner;
import java.time.*;
import java.lang.Math;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

	/* Performs the following operations  */
	/* 1.Stores Stock Data with market price*/
	/* 2.Calculates Dividend Price & P/E Ratio for the given stock */
	/* 3.Record the transaction details for the given stock */
	/* 4. Calculate the Volume Weighted Stock Price based on trades for the past 15 mins.-
	 *                For testing purpose modified to 15 mins.*/
	/* 5. Calculate the GBCE All Share Index using the geometric mean of the prices for all stocks */

	class Trans
	{	
		
		String transtckSymbol;	
		LocalDateTime transdate;
		 int qtyShare;
		 char bsInd;
		 int trdPrice;
		 static Logger Logger = LoggerFactory.getLogger(Trans.class);
		 Trans(String trnSymbol, char Ind,int qty,int trdPr)
		 {
			 transtckSymbol = trnSymbol;
			 transdate = LocalDateTime.now();
			 qtyShare = qty;
			 bsInd = Ind;
			 trdPrice = trdPr;
		 }
		 
		 public static double calcVolWgtStk(Trans[] trn,String trnSym)
		 {
			 double volWeightedStkPrice =0.0;
				int tranPrice = 0;
				int totQty = 0;
				for(int i=0;i< trn.length;i++)
				{
					int duration;
					LocalDateTime timeNow;
					timeNow = LocalDateTime.now();
					duration = timeNow.getMinute() - trn[i].transdate.getMinute();
					if ((duration < 15) && (trn[i].transtckSymbol.equalsIgnoreCase(trnSym)))
					{						
						tranPrice += trn[i].qtyShare *trn[i].trdPrice;
						totQty += trn[i].qtyShare;
					}	
				}
			   	
			volWeightedStkPrice = (double)tranPrice /(double) totQty;
			return(volWeightedStkPrice);
		 }
		 public static void getTranInputs(Trans trn[])
	     {
			 String trnSym;
				char bsInd;
				int qtyShr;
				int tPrice;
				 boolean chkInpTrn = true; 
			 for (int i=0;i < trn.length;i++)
			 {
			   System.out.println("Enter the Stock Symbol:");
	           trnSym=SSStock.sc.next();
			   System.out.println("Enter the B/S Indicator:");
			   bsInd= SSStock.sc.next().charAt(0);
			   System.out.println("Enter the Quantity:");
			   qtyShr=SSStock.sc.nextInt();
			   System.out.println("Enter the Trade Price:");
			   tPrice=SSStock.sc.nextInt();
			  chkInpTrn = chkInpTran(trnSym,bsInd,qtyShr,tPrice);
			
				if (chkInpTrn)
				{
					  trn[i] = new Trans(trnSym,bsInd,qtyShr,tPrice);
				}
				else
				  Logger.info("Failed Validation of Input TRansactions");
			   }	 
				
	     		}
	     public static boolean chkInpTran(String tSy, char bs,int qtyShr,int tPrice)
	     {
	    	 if (tSy.isEmpty())
	    	   {
	    		 Logger.info("Enter Valid Tran Stock Symbol, Cant accept spaces");
	    		 return false;
	    	   }
	    	 else
	    	      if ((bs  != 'B') && (bs != 'S'))
	    	         {
					 Logger.info("Not Valid BS Indicator");
					 return false;
	    	      }
	    	     else
	    	    	 if ((qtyShr < 0 ))
		    	      {
	    	    		Logger.info("Not Valid Last Quantity");
						 return false;
		    	      }
	    	    	 else
	    	    		 if (tPrice < 0 )
			    	      {
							 Logger.info("Not Valid Trade Price");
							 return false;
			    	      } 	 
	    	    		 else
	                    	 return true;
	     }
	}
	public class SSStock {
		String stockSymbol;
	 	String typeCF;
	 	int lastDividend;
	 	int fixDividend;
	 	int parValue;
	 	int mPrice;
	 	public static String sCommon = "COMMON";
	 	public static 	Scanner sc = new Scanner(System.in);
	 	static Logger Logger = LoggerFactory.getLogger(SSStock.class);
	 	
	 	SSStock(String sS,String type,int lDiv,int fixDiv,int parV,int mPri)
	 	{
	 		  stockSymbol = sS;
	 		  typeCF = type;
	 		  lastDividend=lDiv;
	 		  fixDividend=fixDiv;
	 		  parValue=parV;
	 		  mPrice=mPri;
	 	  }  
    double calcDivyield()
	  {
    	
	     if (this.typeCF.equalsIgnoreCase(sCommon))
		  	return ((double)this.lastDividend / (double) this.mPrice);
	     else
		  
	   		return((double)(this.fixDividend * this.parValue) / (double) this.mPrice);
		}
	   
	  
	 double calcPERatio()
	  {
	     return ((double)this.mPrice / (double)this.lastDividend);
	  }
	  
	
	 public static double calAllShrIdx(SSStock[] stk)
	 {
			double allShareInd =0.0;
			double totPrice = 1.0;
		
			for (int i=0; i < stk.length; i++ )
		       {
				totPrice *= stk[i].mPrice;
		       }
			double nroot = (double)1/(double)stk.length;
			allShareInd = Math.pow(totPrice,nroot);
			return allShareInd;
			
	 }
	     public static void main (String[] args)
	   {
		int comput = 0;
		String istkSym = " ";
		System.out.println("Enter the number of the Stocks");
		comput = sc.nextInt();
		SSStock s[]	= new SSStock[comput];
		if (comput <= 0 )
		Logger.info("No Stocks entered!!!");
		else
		{

			getStckInputs(s);
		}
        System.out.println("Enter Choice of Computation (1/2/3)");
    	System.out.println("1. Calculate Dividend Yield & P/E Ratio");
		System.out.println("2. Record Transactions");
		System.out.println("3. Calculate GBSE All Share Index");
		comput = sc.nextInt();
		if (comput == 1)
			{
			System.out.println("Enter the Stock Symbol");
			istkSym = sc.next();
	        for (int i=0; i < s.length; i++ )
			    {
				    	if(s[i].stockSymbol.equalsIgnoreCase(istkSym))
				    	{
				    	System.out.println("Dividend Yield for Stock Symbol  "+ s[i].stockSymbol + ":  " + s[i].calcDivyield());
				    	System.out.println("PE Ratio for Stock Symbol  "+ s[i].stockSymbol + ":  " + s[i].calcPERatio());
				    	}
				     }
				}
				else if (comput == 2)
				{
					int tranCnt = 0;
				    char bsInd =' ';
				    String trnSym;
					System.out.println("Enter the number of Stock Transactions to be recorded:");
					tranCnt = sc.nextInt();
					Trans tran[] = new Trans[tranCnt];
					if (tranCnt <= 0 )
						Logger.info("No Stock Transactions entered!!!");
					else
					{
					   Trans.getTranInputs(tran);
					   Logger.info("String Length" + tran.length);
					   bsInd=' ';
					   System.out.println("Do you want to calculate the Volume Weighted Stock Priced:");
					   bsInd=sc.next().charAt(0);
					   if ((bsInd == 'y')||(bsInd == 'Y'))
					   {
						System.out.println("Enter the Stock Symbol : ");
						trnSym = sc.next();				
						System.out.println("Volume weighted trade in the past 15mins");
						double volStock = Trans.calcVolWgtStk(tran,trnSym);
						System.out.println(volStock);
				   	   }
					   else
						System.out.println("No options entered");
					}
					System.out.println("End of Program");
				}
				else  if (comput == 3)
					{
					if (s.length > 0)
					{
					 System.out.println("GBSE All share index: ");
					 double shrIdx = calAllShrIdx(s);
					 System.out.println(shrIdx);
					}
					else
					{
						Logger.info("No Stock tranactions");
					}
			       
				}
				else
				{
					Logger.info("No inputs for transactions !!!" + Logger.getName());
				}
				sc.close();
			} 	  
	     public static void getStckInputs(SSStock st[])
			{
	 		String stkSymbol;
	 	 	String typCF;
	 	 	int lDiv;
	 	 	int fDiv;
	 	 	int parVal;
	 	 	int mPrice;
	 	 	boolean chkInp = true;
	 	 	for(int i = 0; i < st.length;i++)
	 	 	{
			System.out.println("Enter the Stock Symbol:");
			stkSymbol = sc.next();
			System.out.println("Enter the Type (COMMON or PREFERRED):");
			typCF = sc.next();
			System.out.println("Enter the last Div:");
			lDiv=sc.nextInt();
			System.out.println("Enter the fixed Div:");
			fDiv=sc.nextInt();
			System.out.println("Enter the parValue:");
			parVal=sc.nextInt();
			System.out.println("Enter the Market Price:");
			mPrice = sc.nextInt();
			chkInp = chckInputs(stkSymbol,typCF,lDiv,fDiv,parVal,mPrice);
			if (chkInp)
			st[i]=new SSStock(stkSymbol,typCF,lDiv,fDiv,parVal,mPrice);
			else
				Logger.info("Failed Input Validity");
	 	 	}
			}
	     public static boolean chckInputs(String Symbol, String type,int chLDiv, int fDiv, int PValue,int mPrice){
	    	 if (Symbol.isEmpty())
	    	   {
	    		 Logger.info("Enter Valid Stock Symbol, Cant accept spaces");
	    		 return false;
	    	   }
	    	 else
	          if (!type.contentEquals("COMMON") && !type.contentEquals("PREFERRED"))
	    	      {
	    	       	 Logger.info("Not Valid Stock Type");
					 return false; 	          
	    	            
	    	      }
	    	     else
	    	    	 if ((chLDiv < 0 ))
		    	      {
	    	    		Logger.info("Not Valid Last Dividend");
						 return false;
		    	      }
	    	    	 else
	    	    		 if (fDiv < 0 )
			    	      {
							 Logger.info("Not Valid Fixed Dividend");
							 return false;
			    	      } 	 
	    	    		 else
	    	    			 if (PValue < 0 )
				    	      {
								 Logger.info("Not Valid Par Value");
								 return false;
				    	      } 
	    	    			 else
	    	    			  if (mPrice < 0 )
	   			    	      {
	   							 Logger.info("Not Valid Market Price");
	   							 return false;
	   			    	      } 
	    	    			  else
	    	    				  return true;
	     						}
	     
	    
	     
	}

