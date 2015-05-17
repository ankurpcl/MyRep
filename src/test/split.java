package test;

public class split {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String data="col|username";
		
		String temp[] = data.split("\\|");

		System.out.println(temp[0]);
		System.out.println(temp[1]);
		System.out.println(data.split("\\|")[1]);
		
	}

}
