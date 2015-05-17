package test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionTest {

	public static void main(String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		
		ReflectionTest test = new ReflectionTest();
		//test.abc();
		String a = "xxx";
		String b = "yyy";
		
		Method method[] = test.getClass().getMethods();
		
		for(int i =0; i<method.length;i++)
		{
		//	System.out.println(method[i].getName());
		
		if(method[i].getName().equals("abc"))
			method[i].invoke(test,a,b);
		}
	}
	
	public void abc(String x, String y){
		
		System.out.println("inside abc " + x + y);
		
	}
	
	public void xyz(){
		
		System.out.println("inside xyz");
		
	}
}
