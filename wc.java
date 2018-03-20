package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter; 

public class wc {

	// -c
    public static String fileChars(String fileName) {
        File file = new File(fileName);
        String str = "";
        int count = 0;
        Reader reader = null;
        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    count++;
                }
            }
            str = fileName + ", 字符数："+count +"\r\n";
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }

    // -w
	public static String fileWords(String fileName) {
        File file = new File(fileName);
        String str = "";
        int count = 0;//统计单词数
        int word; //是否有单词
        Reader reader = null;
        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            // i 在循环中计数，但实际上对控制循环无用。用来应对开头就是分隔符的情况
            if((tempchar = reader.read()) == -1) {
            	str = fileName + ",单词数：0";
            	return str;
            }
            reader = new InputStreamReader(new FileInputStream(file));
            for (int i = 1;(tempchar = reader.read()) != -1;i++) {
            	//每次读新词，word置0
            	word = 0;
                while (((char) tempchar) == ' ' || ((char)tempchar) == ',' || ((char)tempchar) == '\r' ||((char)tempchar) == '\n' ||((char)tempchar) == '\t') {
                	//如果i为1则说明是开头的分隔符，直接跳过
                	if(i==1) {
                		count--;
                		break;
                	}
                	else {
	                	word = 1;
	                    if((tempchar = reader.read()) != -1) {
	                    	continue;
	                    }
	                    //如果读到文件末尾了
	                    else {
	                    	count--;
	                    	break;
	                    }
                	}
                }
                count += word;
            }
            str = fileName + ", 单词数："+ ++count +"\r\n";
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }
    
    //-l
    public static String fileLines(String fileName) {
        File file = new File(fileName);
        String str = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                line++;
            }
            str = fileName + ", 行数：" + line +"\r\n";
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }
    
    // -o
    public static void outPut(final String str, final String fileName)
    {
    	try {
    		File file = new File(fileName);
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(str);
			fileWriter.close();
		} 
    	catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // -a
    public static String fileMoreLines(String fileName) {
        File file = new File(fileName);
        String str = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;//当前读到的行
            int codeLine = 0;
            int blankLine = 0;
            int noteLine = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
            	//读到的字符串长度小于1，为空行
            	if(tempString.length()<1)
            	{
            		blankLine++;
            		continue;
            	}
            	int uselessChar = 0;//'{' '}' 的个数
            	//逐字符读
            	for(int i = 0;i<tempString.length();i++){
            		//读到 '{' '}'则uselessChar++
            		if(tempString.charAt(i)=='{' || tempString.charAt(i)=='}'){
            			uselessChar++;
            		}
            		//读到注释符
            		else if(tempString.charAt(i)=='/' && (tempString.charAt(i+1)=='/'||tempString.charAt(i+1)=='*')){
            			//如果 '{' '}'个数小于2，则为注释行
            			if(uselessChar<2){
            				noteLine++;
            				break;
            			}
            			//2以上为代码行
            			else {
            				codeLine++;
            				break;
            			}
            		}
            		//读到注释右符
            		else if(tempString.charAt(i)=='*' && tempString.charAt(i+1)=='/') {
            			//位于行末，注释行
            			if(i==(tempString.length()-2)) {
            				noteLine++;
            				break;
            			}
            			//位于行末-1的位置且行末字符为'{' '}'，注释行
            			else if(i==(tempString.length()-3) && (tempString.charAt(i+2)=='{' || tempString.charAt(i+2)=='}')) {
            				noteLine++;
            				break;
            			}
            			//否则代码行
            			else {
            				codeLine++;
            				break;
            			}
            		}
            		//如果都没读到上述字符，且不为空格，那么为有效字符，为代码行。
            		else if(tempString.charAt(i)!=' '){
            			codeLine++;
            			break;
            		}
            		//如果读到行末了
            		if((i==tempString.length()-1)){
            			// '{' '}'个数小于2，为空行
                		if(uselessChar<2) {
                			blankLine++;
                		}
                		//2以上，代码行
                		else {
                			codeLine++;
                		}
                	}
            	}
            }
            str = fileName + ", 代码行/空行/注释行 ：" + codeLine +"/" + blankLine +"/" + noteLine +"\r\n";
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }
    
    //-e
    public static String stopWords(String infile, String stopfile){
    	/*
    	 * 先把stopfile指定的文件的单词读到stopList里
    	 */
    	String all = "";//在读停用词表时，先把整个文件读到这个字符串里
        String word = "";//读停用词表时，用来存当前读到的单词
		List<String> stopList = new ArrayList<>();
		BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(stopfile));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束，读到all字符串里
            while ((tempString = reader.readLine()) != null) {
                all = all + " " + tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        
        //开始逐字符循环读停用词表
        for(int i = 0;i<all.length();i++) {
        	//如果读到有效字符则加到word中
        	if(all.charAt(i)!=' ' && all.charAt(i)!='\r' && all.charAt(i)!='\n' && all.charAt(i)!='\t') {
        		word += all.charAt(i);
        	}
        	else {
        		if(word != "") {
        			stopList.add(word);
        			word = "";
        		}
        	}
        }
        //将word添加到stopList中
        stopList.add(word);
        
        /*
         * 再读infile指定的文件的单词，与stopList进行对比。
         */
        File file = new File(infile);
        String str = "";
        word = "";
        int hasword;//有无单词，0无，1有
        int count = 0;//统计单词数
        BufferedReader buttferedReader = null;
        try {
        	buttferedReader = new BufferedReader(new FileReader(file));
            String tempString = null;
            String whole = buttferedReader.readLine();//把整个文件读到whole字符串里，对whole进行操作
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = buttferedReader.readLine()) != null) {
                whole = whole +'\n'+ tempString;
            }
            for(int i = 0;i<whole.length();i++) {
            	hasword = 0;
            	//读到有效字符，添加到word中
            	if(whole.charAt(i)!=' ' && whole.charAt(i)!='\r' && whole.charAt(i)!='\n' && whole.charAt(i)!='\t' && whole.charAt(i)!=',') {
            		word += whole.charAt(i);
            	}
            	//读到分隔符
            	else {
            		//循环读
            		while(whole.charAt(i)==' ' || whole.charAt(i)=='\r' || whole.charAt(i)=='\n' || whole.charAt(i)=='\t' || whole.charAt(i)==',') {
            			if(!word.isEmpty()) {
            				//如果word不为空则把word拿去和stopList比较
            				hasword = 1;
            				for(int j = 0;j<stopList.size();j++) {
            					if(word.equals(stopList.get(j))) {
            						hasword = 0;
            					}
            				}
            				//比完把word置空
            				word = "";
            			}
            			i++;
            		}
            		//和读单词数中同样的道理
            		if(whole.charAt(i)!=' ' && whole.charAt(i)!='\r' && whole.charAt(i)!='\n' && whole.charAt(i)!='\t' && whole.charAt(i)!=',') {
            			i--;
            		}
            	}
            	count += hasword;
            }
            str = infile + ", 单词数：" + ++count + "\r\n";
            buttferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (buttferedReader != null) {
                try {
                	buttferedReader.close();
                } catch (IOException e1) {
                }
            }
        }
        return str;
    }
    
    //获取menupath目录下文件的扩展名为extension的所有文件 返回一个文件路径数组
    public static ArrayList<String> menu(String menupath, String extension){
    	ArrayList<String> filePathList=new ArrayList<>();
    	File file=new File(menupath);
    	File[] files=file.listFiles();
    	if(files==null)
        {
            if(file.isFile())
                filePathList.add(menupath);
            else
                return null;
        }
        for(File f:files)
        {
            if(f.isFile())
            {
                String curFileName=f.getName();
                String regex=extension.replace("?","[0-9a-z]");
                regex=regex.replace("*","[0-9a-z]{0,}"); //正则匹配
                if(f.getName().matches(regex))
                    filePathList.add(f.getAbsolutePath());
            }
            else if(f.isDirectory())
            {
                ArrayList<String> subDirectoryFiles=menu(f.getAbsolutePath(),extension);
                filePathList.addAll(subDirectoryFiles);
            }
        }
    	return filePathList;
    }
    
    
    public static void main(String args[]){
    	
//    	Scanner scan = new Scanner(System.in);
//    	ArrayList<String> s = new ArrayList<String>();
//    	String next = scan.next();
//    	while(!next.equals(".")){
//    		s.add(next);
//    		next = scan.next();
//    	}
    	
    	//循环处理文件
    	String inpath = "";
    	String outpath = "";
    	String stoppath = "";
    	String extension = "";//要匹配的文件扩展名
    	ArrayList<String> filePathList = new ArrayList<>();//文件路径数组
    	String output = "";
    	/* 是否实现某项功能的数组
    	 * 0:-c  1:-w  2:-l  3:-a  4:-e  5:-s  6 -o  
    	*/
    	boolean[] func = {false,false,false,false,false,false,false};
    	//对程序将要做哪些功能进行准备
    	for(int i = 0;i<args.length;i++){
    		//字符数
    		if(args[i].equals("-c")){
    			func[0] = true;
    		}
    		//单词数
    		else if(args[i].equals("-w")){
    			func[1] = true;
    		}
    		//行数
    		else if(args[i].equals("-l")){
    			func[2] = true;
    		}
    		//更多行数信息
    		else if(args[i].equals("-a")) {
    			func[3] = true;
    		}
    		//停用词表
    		else if(args[i].equals("-e")) {
    			if(i==(args.length-1)) {
    				System.out.println("‘-e’后必须跟文件名。");
    				func[4] = false;
    			}
    			else {
    				func[4] = true;
    				stoppath = args[++i];
    			}
    		}
    		else if(args[i].equals("-s")) {
    			func[5] = true;
    		}
    		//输出
    		else if(args[i].equals("-o")){
    			if(i==(args.length-1)) {
    				System.out.println("‘-o’后必须跟文件名。");
    				func[6] = false;
    			}
    			else {
    				func[6] = true;
    				outpath = args[++i];
    			}
    		}
    		else{
    			if(func[5]) {
    				if(i==(args.length-1) || args[i+1].equals("-e") || args[i+1].equals("-o")) {
    					inpath = System.getProperty("user.dir");
    					extension = args[i];
    				}
    				else {
    					inpath = args[i];
    					extension = args[++i];
    				}
    			}
    			else {
    				inpath = args[i];
    			}
    		}
    	}
    	
    	//实现程序功能
    	if(func[5]) {
    		filePathList = menu(inpath,extension);
    		for(int i = 0;i<filePathList.size();i++) {
    			if(func[0]){
    	    		//-c
    	    		output += fileChars(filePathList.get(i));
    	    	}
    			if(func[4]){
    	    		//-e
    	            output += stopWords(filePathList.get(i),stoppath);
    	    	}
    			else if(func[1]){
    	    		//-w
    	    		output += fileWords(filePathList.get(i));
    	    	}
    	    	if(func[2]){
    	    		//-l
    	    		output += fileLines(filePathList.get(i));
    	    	}
    	    	if(func[3]){
    	    		//-a
    	    		output += fileMoreLines(filePathList.get(i));
    	    	}
    	    	
    	        if(func[6]){
    	           	outPut(output,outpath);
    	        }
    		}
    	}
    	else {
	    	if(func[0]){
	    		//-c
	    		output += fileChars(inpath);
	    	}
	    	if(func[1]){
	    		//-w
	    		output += fileWords(inpath);
	    	}
	    	if(func[2]){
	    		//-l
	    		output += fileLines(inpath);
	    	}
	    	if(func[3]){
	    		//-a
	    		output += fileMoreLines(inpath);
	    	}
	    	if(func[4]){
	    		//-e
	            output += stopWords(inpath,stoppath);
	    	}
	        if(func[6]){
	        	//-o
	           	outPut(output,outpath);
	        }
	        //如果没有指定输出文件，则输出到result.txt
	        else {
	        	outPut(output,"result.txt");
	        }
    	}
    	System.out.println(output);
    }
}
