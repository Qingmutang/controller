package com.bjpowernode.p2p.java8;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class PredicateMain {

	public static void main(String[] args) {
		
		Predicate<String> predicate = (String str)->{return str.equals("hello");};
		
		Function<String,String> fun1 = (String str1)->{return str1.toUpperCase();};
		
		
		Supplier<String> suppler =()->{return "aaa";}; 
		
		Consumer<String> consumer = (String str)->{ System.out.println(str);};
		
		String out = fun1.apply("aaa");
		System.out.println(out);
		
		System.out.println(suppler.get());
		consumer.accept("ss");
		
		Consumer<String> con2 = System.out::println;
		
		
		

	}

}
