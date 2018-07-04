package p2p;

import java.lang.reflect.Field;

public class TestMain {
	{
		System.out.println("num 1");
	}
	
	static {
		System.out.println("static");
	}
	
	{
		System.out.println("num 2");
	}

	public static void main(String[] args) {
		
		new TestMain();
		new TestMain();
		
		
		/*Integer a = 1;
		Integer b = 2;
		
		System.out.println("before: a:"+a+" b:"+b);
		try {
			swap(a,b);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("after: a:"+a+" b:"+b);
		
		String s1 = "a";
		String s2 = "b";
		
		System.out.println("before: a:"+s1+" b:"+s2);
		try {
			swap(s1,s2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("after: a:"+s1+" b:"+s2);*/
		

	}
	
	public static void swap(Integer a,Integer b) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = Integer.class.getDeclaredField("value");
		field.setAccessible(true);
		
		Integer temp = new Integer(a);
		field.setInt(a, b);
		field.setInt(b, temp);

		
	}
	
	public static void swap(String a,String b) throws Exception {
		Field field = String.class.getDeclaredField("value");
		field.setAccessible(true);
		String temp = new String(a);
		
		field.set(a,b.toCharArray());
		field.set(b, temp.toCharArray());
	    
		
	}

}
