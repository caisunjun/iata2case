import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class JustTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}
	
	private void test2(){
		String str = "啊“顶顶顶顶顶”棒";
		Pattern p = Pattern.con;
		
		
	}
	
	
	private void test1(){
//		System.out.println("请输入一行字符串：");
//		Scanner reader = new Scanner(System.in);
//		String str = reader.nextLine();
		int[] num = new int[5];
		int i = 0;
		int Num;
		String str = "个“的罚款32341！！”相关话题";
	
		Pattern p = Pattern.compile("(\\d+)个");
		Matcher m = p.matcher(str);		
		while(m.find()){
			num[i] = Integer.parseInt(m.group());
			System.out.println(num[i]);
			i++;		
//			Num = Integer.parseInt(m.group());
//			System.out.println(Num);
			
//			if(Num == 0){
//				System.out.println("房源数为0!");
//			}
//			else{
//				System.out.println("房源数为:"+Num);
//			}
//		}
		
//		System.out.println(Num());
//		Pattern p = Pattern.compile("\\D+");
//		String[] spl = p.split(str);
//		Integer num; 
//		for(int i=0;i<spl.length;i++){
//			System.out.println(spl[i]);
//			num = Integer.getInteger(spl[i]);
//			System.out.println(num);
//		}		
		
//		String str1[];
//		str1 = str.split("\\s");
//		System.out.println(str1[1]);
				
//		String str1 = "你们好";
//		String str2 = "你们好";
//		System.out.println(str1.equals(str2));
//	
		}
		int n = Integer.toString(num[0]).length();
		System.out.println(str.substring(n+2, str.length()-5));
		
	}
}
