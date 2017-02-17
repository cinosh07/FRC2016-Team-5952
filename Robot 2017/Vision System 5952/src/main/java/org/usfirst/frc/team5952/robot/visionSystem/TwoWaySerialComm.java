package org.usfirst.frc.team5952.robot.visionSystem;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
 
public class TwoWaySerialComm {
 
  void connect( String portName, int baudrate, int databits, int stopbits, int parity ) throws Exception {
    CommPortIdentifier portIdentifier = CommPortIdentifier
        .getPortIdentifier( portName );
    if( portIdentifier.isCurrentlyOwned() ) {
      System.out.println( "Error: Port is currently in use" );
    } else {
      int timeout = 2000;
      CommPort commPort = portIdentifier.open( this.getClass().getName(), timeout );
 
      if( commPort instanceof SerialPort ) {
        SerialPort serialPort = ( SerialPort )commPort;
        serialPort.setSerialPortParams( baudrate,
        		databits,
        		stopbits,
        		parity );
 
        InputStream in = serialPort.getInputStream();
        OutputStream out = serialPort.getOutputStream();
 
        ( new Thread( new SerialReader( in ) ) ).start();
        ( new Thread( new SerialWriter( out ) ) ).start();
 
      } else {
        System.out.println( "Error: Only serial ports are handled by this example." );
      }
    }
  }
 
  public static class SerialReader implements Runnable {
 
    InputStream in;
 
    public SerialReader( InputStream in ) {
      this.in = in;
    }
 
    
  public void run() {
  	int bufferSize = 1024;
  	int total = 0;
    byte[] buffer = new byte[ bufferSize ];
    int len = -1;
    try {
      while( total < bufferSize  && ( len = this.in.read( buffer, total, bufferSize - total ) ) > -1  ) {
     
    	  CameraManager.getInstance().receiveCommandFromRemote(new String( buffer, 0, len ));
        
        total += len;
      }
    } catch( IOException e ) {
      e.printStackTrace();
    }
  }
}
  public static class SerialWriter implements Runnable {
 
    OutputStream out;
 
    public SerialWriter( OutputStream out ) {
      this.out = out;
    }
 
    public void run() {
      try {
        int c = 0;
        while( ( c = CameraManager.getInstance().sendCommandToRemote() ) > -1 ) {
          this.out.write( c );
        }
      } catch( IOException e ) {
        e.printStackTrace();
      }
    }
  }
}
