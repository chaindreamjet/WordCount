package testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringWriter; 

public class WordCount {

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
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
            	word = 0;
                while (((char) tempchar) == ' ' || ((char)tempchar) == ',' || ((char)tempchar) == '\r' ||((char)tempchar) == '\n') {
                	word = 1;
                    if((tempchar = reader.read()) != -1) {
                    	continue;
                    }
                    else {
                    	count--;
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
    
    public static void main(String args[]){
    	
    	//循环处理文件
    	String inpath = "";
    	String outpath = "";
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
    			inpath = args[i];
    		}
    	}
    	
    	//实现程序功能
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
	    if(func[6]){
	       	//-o
	       	outPut(output,outpath);
	    }
	        //如果没有指定输出文件，则输出到result.txt
	    else {
	        outPut(output,"result.txt");
	    }
    	System.out.println(output);
    }
}
