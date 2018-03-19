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
            // һ�ζ�һ���ַ�
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // ����windows�£�\r\n�������ַ���һ��ʱ����ʾһ�����С�
                // ������������ַ��ֿ���ʾʱ���ỻ�����С�
                // ��ˣ����ε�\r����������\n�����򣬽������ܶ���С�
                if (((char) tempchar) != '\r') {
                    count++;
                }
            }
            str = fileName + ", �ַ�����"+count +"\r\n";
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
        int count = 0;//ͳ�Ƶ�����
        int word; //�Ƿ��е���
        Reader reader = null;
        try {
            // һ�ζ�һ���ַ�
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // ����windows�£�\r\n�������ַ���һ��ʱ����ʾһ�����С�
                // ������������ַ��ֿ���ʾʱ���ỻ�����С�
                // ��ˣ����ε�\r����������\n�����򣬽������ܶ���С�
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
            str = fileName + ", ��������"+ ++count +"\r\n";
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
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
                line++;
            }
            str = fileName + ", ������" + line +"\r\n";
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
    	
    	//ѭ�������ļ�
    	String inpath = "";
    	String outpath = "";
    	String output = "";
    	/* �Ƿ�ʵ��ĳ��ܵ�����
    	 * 0:-c  1:-w  2:-l  3:-a  4:-e  5:-s  6 -o  
    	*/
    	boolean[] func = {false,false,false,false,false,false,false};
    	//�Գ���Ҫ����Щ���ܽ���׼��
    	for(int i = 0;i<args.length;i++){
    		//�ַ���
    		if(args[i].equals("-c")){
    			func[0] = true;
    		}
    		//������
    		else if(args[i].equals("-w")){
    			func[1] = true;
    		}
    		//����
    		else if(args[i].equals("-l")){
    			func[2] = true;
    		}
    		//���
    		else if(args[i].equals("-o")){
    			if(i==(args.length-1)) {
    				System.out.println("��-o���������ļ�����");
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
    	
    	//ʵ�ֳ�����
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
	        //���û��ָ������ļ����������result.txt
	    else {
	        outPut(output,"result.txt");
	    }
    	System.out.println(output);
    }
}
