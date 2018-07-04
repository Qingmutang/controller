package com.bjpowernode.p2p.java8;

public class LamdbaMain {

	public static void main(String[] args) {
		IterfaceJava8 inter =()->{System.out.println("hello");};
		
       Interface2 inter2 = (String msg)->{System.out.println(msg); return msg;};
       
       String result = inter2.doString("helloworld?");
       
       Thread thread = new Thread(new Runnable() {

		@Override
		public void run() {
		   System.out.println("haha");
			
		}
    	   
    	   
       });
       
       thread.start();
       
       Thread thread2 = new Thread(
    		   ()->{
    			   System.out.println("hello");
    		   }
    		   );
       
       thread2.start();
       
	}

}
