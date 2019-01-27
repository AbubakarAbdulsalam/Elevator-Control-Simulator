package test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import main.Elevator;
import main.Floor;
import main.FloorRequest;
public class FloorTest {

	Timestamp timestamp = null;
	Date parsedDate = null;
	
	@SuppressWarnings("deprecation")
	@Test 
	// start listening to the Floor thread
	public void testRegistration()
	{
		FloorRequest fr = new FloorRequest();
		Thread floorThread = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				Floor floor = new Floor();
			}
		});
		
		byte[] regist = new byte[100];
		
		try {
			DatagramPacket registration = new DatagramPacket(regist,regist.length);
			DatagramSocket getRegist = new DatagramSocket(45);
			floorThread.start();
			getRegist.receive(registration);
			// read in buffer 
			FloorRequest result = (FloorRequest) fr.getObjectFromBytes(regist);
			assertTrue(result.getFloor() == 2);
			assertTrue(result.getCarButton() == 4);
			SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");
		    
			try {
				parsedDate = dateFormat.parse("14:05:15.0");
			} catch (ParseException e) {
			
				e.printStackTrace();
			}
		    timestamp = new Timestamp(parsedDate.getTime());
			assertTrue(result.getTimestamp() == timestamp);
			assertTrue(result.getFloorButton() =="UP");
			
			getRegist.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertTrue((int)regist[0] == 0);
		assertTrue((int)regist[4] == 33);
		
		floorThread.stop();
	}
//	@Test
//	public void testReceiveRequest()
//	{
//		
//		Thread floorThread = new Thread(new Runnable()
//		{
//		@Override
//		public void run()
//		{
//			Floor f = new Floor();
//			f.start();
//		}
//		
//	});
//
//		byte[] request = new byte[] {0};  
//		try {
//			DatagramPacket requestPckt = new DatagramPacket(request,request.length,
//					InetAddress.getLocalHost(),45);
//			DatagramSocket sendReq = new DatagramSocket();
//			floorThread.start();
//			sendReq.send(requestPckt);
//			sendReq.close();
//		} catch (IOException e) {
//		
//			fail("Did not send ");
//			e.printStackTrace();
//		} 
//		
//		byte[] arrival = new byte[100];
//		try {
//			Thread.sleep(19500);
//			DatagramSocket getArrival = new DatagramSocket(69);
//			
//			DatagramPacket pck = new DatagramPacket(arrival,arrival.length);
//			getArrival.receive(pck);
//			getArrival.close();
//			
//			
//		} catch (InterruptedException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		assertTrue((int)arrival[0] == 4);
//		assertTrue((int)arrival[1] == 0);
//		
//		
//	}
//	
		
		
	

	
}
	


