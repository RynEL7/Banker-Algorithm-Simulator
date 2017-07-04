/*
 * 		[ Banker(Safety and Request) Algorithm Simulator ] 
 * 		
 * 		All Algorithms Written by : 
 * 
 * 					Riyan Saputra Irawan(141105151104) 
 * 							UIKA BOGOR
 * 		  			       Copyright 2015
 * 		Menus Written by : 
 * 
 * 					Zainal Mubarok (141105151167) 
 * 							UIKA BOGOR
 * 		  			       Copyright 2015
 * 
 * 		Don't Change And Copy the codes without Author's Permission
 * 		Respect our work !!!
 */

//package TugasDeadLock;
import java.util.Scanner;
import java.io.IOException;

class Banker 
{
	protected static int n_proc,m_res;//Much Process and Resource
	protected static int[][] Allocation,Max,Need,Available;
	protected int a,b,c,d,e;
	
	protected static Scanner scn;
	
	//Input for much process and resources
	public void inProRes()
	{
		scn =new Scanner(System.in);
		System.out.println("\n\n\t[Banker Algorithm]\n\t[Input Process and Resources]");
		
		System.out.print("> How Much Processes\t: ");
		n_proc=scn.nextInt();
		System.out.print("> How Much Resources\t: ");
		m_res=scn.nextInt();
		
		Allocation=new int[n_proc][m_res];
		Max=new int[n_proc][m_res];
		Need=new int[n_proc][m_res];
		Available=new int[1][m_res];
	}
	
	//input for Allocation Matriks
	public void inAlloc()
	{
		System.out.println("\t[Input Allocation]");
		for(a=0;a<n_proc;a++)
		{
			for (b=0;b<m_res;b++)
			{
				System.out.print("Allocation P"+a+" on R"+b+"\t: ");
				Allocation[a][b]=scn.nextInt();
			}
		}
	}
	
	//input For Maximum Matriks
	public void inMax()
	{
		System.out.println("\t[Input Max]");
		for(a=0;a<n_proc;a++)
		{
			for (b=0;b<m_res;b++)
			{
				System.out.print("Max P"+a+" on R"+b+"\t: ");
				Max[a][b]=scn.nextInt();
			}
		}
	}
	
	//input For Available
	public void inAvail()
	{

		System.out.println("\t[Input Available]");
		for (b=0;b<m_res;b++)
		{
			System.out.print("Available on R"+b+"\t: ");
			Available[0][b]=scn.nextInt();
		}
	}
	
	//Counting Need Matriks(Max-Allocation)
	public void Count_Need()
	{
		for(a=0;a<n_proc;a++)
		{
			for (b=0;b<m_res;b++)
			{
				Need[a][b]=Max[a][b]-Allocation[a][b];
			}
		}
	}
	
	//Show All Stat
	public void showAll()
	{
		System.out.println("\n\t[All Status]\n");
		
		for(a=0;a<n_proc;a++)
		{
			//Allocation
			System.out.print("P"+a+" | Al(");
			for (b=0;b<m_res;b++)
			{
				System.out.print(Allocation[a][b]);
				if (b!=(m_res-1))
				{
					System.out.print(",");
				}
			}
			
			//Max
			System.out.print(")\tM(");
			for (b=0;b<m_res;b++)
			{
				System.out.print(Max[a][b]);
				if (b!=(m_res-1))
				{
					System.out.print(",");
				}
			}
			System.out.print(")");
			
			//Available
			if (a==0)
			{
				System.out.print("\tAv(");
				for (b=0;b<m_res;b++)
				{
					System.out.print(Available[0][b]);
					if (b!=(m_res-1))
					{
						System.out.print(",");
					}
				}
				System.out.print(")");
			}
			else
			{
				System.out.print("\t\t");
			}

			//Need
			System.out.print("\tN(");
			for (b=0;b<m_res;b++)
			{
				System.out.print(Need[a][b]);
				if (b!=(m_res-1))
				{
					System.out.print(",");
				}
			}
			System.out.println(")");
		}
		
		System.out.println("\n\nNote:\nAl\t=Allocation\nM\t=Max\nAv\t=Available\nN\t=Need");
	}
}

class Safety extends Banker
{
	//declaration for Finish and Work
	boolean[] Finish;
	int[][] Work;
	
	boolean Safe=false,canAlloc=false;
	//Make All Ready
	public void initializing()
	{
		System.out.println("\n\t[Safety Algorithm]");
		a=0;
		b=0;
		c=0;
		d=0;
		
		Finish=new boolean[n_proc];
		Work=new int[1][m_res];
		
		for (a=0;a<m_res;a++)
		{
			Work[0][a]=Available[0][a];
		}
		for (a=0;a<n_proc;a++)
		{
			Finish[a]=false;
		}
		System.out.println("\n\t>System is Safe to Allocated All Processes\t: "+isSafe());
	}

	//Checking if Need(i) <= Work
	public boolean canAllocated(int d)
	{
		for (c=0;c<m_res;c++)
		{
			if (Need[d][c]<=Work[0][c])
			{
				canAlloc=true;
			}
			else
			{
				canAlloc=false;
				break;
			}
		}
		return(canAlloc);
	}
	public boolean isSafe()
	{
		e=0;
		d=0;
		
		System.out.println("\t[Checking Safety Processes to Allocated]");
		
		//Looping till All Processes Allocated
		while (d < n_proc)
		{
			//Finding Processes that unfinished and can Allocated
			for (a=e;a<n_proc;a++)
			{
				if (Finish[a]==false && canAllocated(a)==true)
				{
					System.out.print("\n>Safety Allocated Process\t:"+a+"\n>Work Now is\t: Av(");
					
					//Adding Work Value Work+=Allocation(i) and set Process Finished
					for (b=0;b<m_res;b++)
					{
						Work[0][b]+=Allocation[a][b];
						
						System.out.print(Work[0][b]);
						if (b!=(m_res-1))
						{
							System.out.print(",");
						}
						else
						{
							System.out.print(")");
						}
					}
					Finish[a]=true;
					System.out.println("\n>P"+a+" is Finished\t: "+Finish[a]);

					d+=1;
					e=a;
					break;
				}
				
			}
			
			//Continuous Finding...
			if (e<n_proc)
			{
				e+=1;
			}
			else
			{
				e=0;
			}
		
		}
		
		//returning safe stat of finished all processes
		for (a=0;a<Finish.length;a++)
		{
			if (Finish[a]==true)
			{
				Safe=true;
			}
			else
			{
				Safe=false;
				break;
			}
		}
		return(Safe);
	}
}


class Alloc_Req extends Safety
{
	//Process Request Index
	int proIndex=0;
	int[] Request;
	boolean ClaimOK=false,AvailOK=false;
	
	//input Request Values
	public void inReq()
	{
		System.out.print("\nRequest For Process\t: ");
		proIndex=scn.nextInt();
		
		Request=new int[m_res];
		for (a=0;a<m_res;a++)
		{
			System.out.print("Process-"+proIndex+" on Resource-"+a+"\t: ");
			Request[a]=scn.nextInt();
		}
		
	}
	
	//Check Claim Accepted for Request(Request<=Need(i))
	public boolean isClaimOK()
	{
		for (a=0;a<m_res;a++)
		{
			if (Request[a]<=Need[proIndex][a])
			{
				ClaimOK=true;
			}
			else
			{
				ClaimOK=false;
				break;
			}
		}
		
		return(ClaimOK);
	}
	//Check Available Accepted for Request(Request <= Available)
	public boolean isAvailOK()
	{
		for (a=0;a<m_res;a++)
		{
			if (Request[a]<=Available[0][a])
			{
				AvailOK=true;
			}
			else
			{
				AvailOK=false;
				break;
			}
		}
		
		return(AvailOK);
	}
	//Allocating Request
	public void AllocatingReq()
	{
		if (isClaimOK()==true)
		{
			if (isAvailOK()==true)
			{
				//Allocating Request
				for (a=0;a<m_res;a++)
				{
					Available[0][a]-=Request[a];
					Allocation[proIndex][a]+=Request[a];
					Need[proIndex][a]-=Request[a];
				}
				//Show Status
				showAll();
				//Check System isSafe
				initializing();
			}
			else
			{
				System.out.println("\n\n>Request Waiting for Released Resources\n\n");
			}
		}
		else
		{
			System.out.println("\n\n>Request Can't Allocating on System\n\n");
		}
	}
	
	public void Request()
	{
		System.out.println("\n\n\t[Request Algorithm]");
		inReq();
		AllocatingReq();
	}
}

class Main 
{
	static Scanner scn;
	static Safety safe = new Safety();
	static Alloc_Req Req = new Alloc_Req();
	static Banker banker = new Banker();
	
	public static void main(String[] args)
	{
		scn=new Scanner(System.in);
		Menu();
		
		
	}
	
	static public void Menu()
	{
		int mn;
		do
		{
			System.out.print("\n\t[ Banker Simulator ]\n\t1>Start Simulator\n\t2>About\n\t3>Exit\n\tYour Decision\t:");
			mn=scn.nextInt();
	
			switch (mn) 
			{
				case 1: 
				{
					banker.inProRes();
					banker.inAlloc();
					banker.inMax();
					banker.inAvail();
					banker.Count_Need();
					banker.showAll();
					safe.initializing();
					SubMenu1();
					break;
				}
				case 2: 
				{

					System.out.print("\n\t=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"
						+ "\n\tAbout Creator\n\n\tProgram Writer & Design" + "\n\tRiyan Saputra Irawan(141105151104)"
						+ "\n\tZainal Mubarok(141105151167)" + "\n\n\tSupport" + "\n\tM.Naufal Fahmi(141105150720)"
						+ "\n\tGhamal Nasser(141105150809)" + "\n\tAbdul EriSusanto (141205151454)\n"+ "\n\tNuryati (131205150557)\n"
						+ "\n\tAll Rights Reserved" + "\n\tUIKA Bogor 2015"
						+ "\n\t=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n\n\tEnter To Back...");
					try {System.in.read();} catch (IOException e) {}
					Menu();
					break;
				}
				case 3: 
				{
					System.exit(0);
					break;
				}
			
			}
		}while(mn>3 || mn <1);
	}
	
	static void SubMenu1() 
	{
		int smn;
		do 
		{
			System.out.print("\n\t[ Banker Simulator ]\n\t1>Add Next Request\n\t2>Main Menu\n\t3>Exit\n\tYour Decision\t:");
			smn = scn.nextInt();

			switch (smn) 
			{
				case 1: 
				{
					Req.Request();
					SubMenu1();
					break;
				}
				case 2: 
				{
					Menu();
					break;
				}
				case 3: 
				{
					System.exit(0);
					break;
				}
			}
		} while (smn > 3 || smn<1);
	}
}
