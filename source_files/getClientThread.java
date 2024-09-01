import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class getClientThread implements Runnable
{
	private Socket socket;
	private InputStream serverInputStream;
	private OutputStream serverOutputStream;
	private BufferedInputStream Reader;
	private BufferedOutputStream Writer;
	private static UserData[] accountDataBase = new UserData[1000];
	private static int UserNum = 1;
	private static String[] currentUsersID = new String[1000];
	private static int currentUsers = 1;
	private static String[] waitingCombat = new String[2];
	private static int waitingFlag = 0;
	private static Queue<Integer> roomNum = new LinkedList<Integer>();
	private static battleRoomData[] roomData = new battleRoomData[10];
	
	public getClientThread(Socket socket) {
		this.socket = socket;
		//when first account is made, initialize roomNumber available queue.
		if(UserNum == 1) {
			//only 10 battle room will be running
			for(int i=0; i<10; i++) {
				roomNum.offer(i);
				roomData[i] = new battleRoomData();
			}
		}
		
	}
	
	
	@Override
	public synchronized void run()
	{
		String myID =null;
		String myPW = null;
		int myRoomNum = 0;
		int myBattleResult = 0;
		
		try {
			serverInputStream = socket.getInputStream();
			serverOutputStream = socket.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Reader = new BufferedInputStream(serverInputStream);
		Writer = new BufferedOutputStream(serverOutputStream);
		System.out.println("Client connected");
		
		while(true)
		{
			try {
				if(Reader.available() != 0)
				{
					byte[] Input = new byte [1000];
					Reader.read(Input);
					
					
				    //Create Account
				    if(Input[0] == 3)
				    {
				    	myID = new String(Arrays.copyOfRange(Input, 3, Input[1]+3));
						myPW = new String(Arrays.copyOfRange(Input, 3+Input[1], 3+Input[1]+Input[2]));
				    	if(UserNum > 10) {
				    		Writer.write(1);
				    	}
				    	else if(UserNum == 1) {
				    		accountDataBase[UserNum] = new UserData(myID, myPW);
				    		UserNum++;
				    		Writer.write(0);
				    	}
				    	//check if there is same ID, no ID then make Account.
				    	else
				    	{
				    		for(int i=1; i<UserNum; i++) {
				    			if(accountDataBase[i].getUserID().equals(myID)) {
				    				Writer.write(2);
				    				break;
				    			}
				    			if(i == UserNum-1) {
				    				accountDataBase[UserNum] = new UserData(myID, myPW);
						    		UserNum++;
						    		Writer.write(0);
				    				break;
				    			}
				    		}
				    	}
				    }
				    
				    
				    //Log In
				    else if(Input[0] == 4)
				    {
				    	myID = new String(Arrays.copyOfRange(Input, 3, Input[1]+3));
						myPW = new String(Arrays.copyOfRange(Input, 3+Input[1], 3+Input[1]+Input[2]));
						//If accounts are 0
						if(UserNum == 1) {
							Writer.write(1);
						}
						else
						{
							for(int i=1; i<UserNum; i++) {
					    		if(myID.equals(accountDataBase[i].getUserID())) {
					    			if(myPW.equals(accountDataBase[i].getUserPW())) {
					    				//when no Users now, LOGIN
					    				if(currentUsers == 1) {
					    					currentUsersID[currentUsers] = myID;
				    						currentUsers++;
				    						Writer.write(0);
					    				}
					    				//If there is current Users
					    				else {
					    					for(int j=1; j<currentUsers; j++) {
					    						//If already log in, FAIL
						    					if(currentUsersID[j].equals(myID)) {
						    						Writer.write(3);
						    						break;
						    					}
						    					//if not log in, LOGIN
						    					if(j == currentUsers-1) {
						    						currentUsersID[currentUsers] = myID;
						    						currentUsers++;
						    						Writer.write(0);
						    						break;
						    					}
						    				}
					    				}
					    			}
					    			//Exist ID, But Wrong PW, FAIL
					    			else {
					    				Writer.write(2);
					    			}
					    			break;
					    		}
					    		//If Checked all ID but does not exist, FAIL
					    		if(i == UserNum-1)
					    		{
					    			Writer.write(1);
					    			break;
					    		}
					    	}
						}
				    }
				    

				    //LogOut
				    else if(Input[0] == 5) {
				    	myID = new String(Arrays.copyOfRange(Input, 2, 2+Input[1]));
				    	for(int i=1; i<currentUsers; i++) {
				    		if(myID.equals(currentUsersID[i])) {
				    			for(int j=i; j<currentUsers-1; j++) {
				    				currentUsersID[j] = currentUsersID[j+1];
				    			}
				    			currentUsers--;
				    			break;
				    		}
				    		else if(i == currentUsers-1) {
				    			Writer.write(1);
				    		}
				    	}
				    	Writer.write(0);
				    }
				    
				    else if(Input[0] == 6) {
				    	//when no other waiting combat exists
				    	if(waitingFlag == 0) {
				    		waitingCombat[0] = myID;
				    		waitingFlag = 1;
				    		Writer.write(0);
				    	}
				    	//when waiting combat exists
				    	else {
				    		waitingCombat[1] = myID;
				    		waitingFlag = 0;
				    		Writer.write(1);
				    		//send combat name.....
				    		Writer.write(waitingCombat[0].length());
				    		Writer.write(waitingCombat[0].getBytes());
				    		myRoomNum = roomNum.peek();
				    	}
				    }
				    
				    //Exit Waiting Room (ExitButton Pressed.)
				    else if(Input[0] == 7) {
				    	waitingFlag=0;
				    }
				    
				    //Question if combat found....[1] No [0] yes
				    else if(Input[0] == 8) {
				    	if(waitingFlag == 1) {
				    		Writer.write(1);
				    	}
				    	else {
				    		Writer.write(0);
				    		//send combat name...
				    		Writer.write(waitingCombat[1].length());
				    		Writer.write(waitingCombat[1].getBytes());
				    		myRoomNum = roomNum.poll();
				    	}
				    }
				    
				    
				    //Battle ---------- get Motion From Client
				    //0: draw, 1: win, 2: lose
				    else if(Input[0] == 9) {
				    	//System.out.println("" + Input[0] +" " + Input[1]);
				    	//if(Input[1] == 0) Writer.write(2);
				    	//else Writer.write(0);
				    	roomData[myRoomNum].insertMotion(myID, new Motion(Input[1]));
				    	while(roomData[myRoomNum].isFull == false) {
				    		Thread.sleep(100);
				    	}
				    	Writer.write(roomData[myRoomNum].getResult(myID));
				    	Writer.write(roomData[myRoomNum].getCombatMotion(myID));
				    	roomData[myRoomNum].peekNum++;
				    	if(roomData[myRoomNum].peekNum == 2) {
				    		roomData[myRoomNum] = new battleRoomData();
				    	}
				    }
				    Writer.flush();
				}
				//end of when input stream is available
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		//loop End
		}
	}
}
