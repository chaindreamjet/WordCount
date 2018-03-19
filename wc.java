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
            for (int i = 1;(tempchar = reader.read()) != -1;i++) {
                // ����windows�£�\r\n�������ַ���һ��ʱ����ʾһ�����С�
                // ������������ַ��ֿ���ʾʱ���ỻ�����С�
                // ��ˣ����ε�\r����������\n�����򣬽������ܶ���С�
            	word = 0;
                while (((char) tempchar) == ' ' || ((char)tempchar) == ',' || ((char)tempchar) == '\r' ||((char)tempchar) == '\n') {
                	if(i==1) {
                		break;
                	}
                	else {
	                	word = 1;
	                    if((tempchar = reader.read()) != -1) {
	                    	continue;
	                    }
	                    else {
	                    	count--;
	                    }
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
    
    // -a
    public static String fileMoreLines(String fileName) {
        File file = new File(fileName);
        String str = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int codeLine = 0;
            int blankLine = 0;
            int noteLine = 0;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
            	if(tempString.length()<1)
            	{
            		blankLine++;
            		continue;
            	}
            	int uselessChar = 0;//'{' '}' �ĸ���
            	for(int i = 0;i<tempString.length();i++){
            		if(tempString.charAt(i)=='{' || tempString.charAt(i)=='}'){
            			uselessChar++;
            		}
            		else if(tempString.charAt(i)=='/' && (tempString.charAt(i+1)=='/'||tempString.charAt(i+1)=='*')){
            			if(uselessChar<2){
            				noteLine++;
            				break;
            			}
            			else {
            				codeLine++;
            				break;
            			}
            		}
            		else if(tempString.charAt(i)=='*' && tempString.charAt(i+1)=='/') {
            			if(i==(tempString.length()-2)) {
            				noteLine++;
            				break;
            			}
            			else if(i==(tempString.length()-3) && (tempString.charAt(i+2)=='{' || tempString.charAt(i+2)=='}')) {
            				noteLine++;
            				break;
            			}
            			else {
            				codeLine++;
            				break;
            			}
            		}
            		else if(tempString.charAt(i)!=' '){
            			codeLine++;
            			break;
            		}
            		if((i==tempString.length()-1)){
                		if(uselessChar<2) {
                			blankLine++;
                		}
                		else {
                			codeLine++;
                		}
                	}
            	}
            }
            str = fileName + ", ������/����/ע���� ��" + codeLine +"/" + blankLine +"/" + noteLine +"\r\n";
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
    	 * �Ȱ�stopfileָ�����ļ��ĵ��ʶ���stopList��
    	 */
    	String all = "";
        String word = "";
		List<String> stopList = new ArrayList<>();
		BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(stopfile));
            String tempString = null;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
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
        
        for(int i = 0;i<all.length();i++) {
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
        stopList.add(word);
        
        /*
         * �ٶ�infileָ�����ļ��ĵ��ʣ���stopList���жԱȡ�
         */
        File file = new File(infile);
        String str = "";
        word = "";
        int hasword;
        int count = 0;//ͳ�Ƶ�����
        BufferedReader buttferedReader = null;
        try {
        	buttferedReader = new BufferedReader(new FileReader(file));
            String tempString = null;
            String whole = buttferedReader.readLine();
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = buttferedReader.readLine()) != null) {
                whole = whole +'\n'+ tempString;
            }
            for(int i = 0;i<whole.length();i++) {
            	hasword = 0;
            	if(whole.charAt(i)!=' ' && whole.charAt(i)!='\r' && whole.charAt(i)!='\n' && whole.charAt(i)!='\t' && whole.charAt(i)!=',') {
            		word += whole.charAt(i);
            	}
            	else {
            		while(whole.charAt(i)==' ' || whole.charAt(i)=='\r' || whole.charAt(i)=='\n' || whole.charAt(i)=='\t' || whole.charAt(i)==',') {
            			if(!word.isEmpty()) {
            				hasword = 1;
            				for(int j = 0;j<stopList.size();j++) {
            					if(word.equals(stopList.get(j))) {
            						hasword = 0;
            					}
            				}
            				word = "";
            			}
            			i++;
            		}
            		if(whole.charAt(i)!=' ' && whole.charAt(i)!='\r' && whole.charAt(i)!='\n' && whole.charAt(i)!='\t' && whole.charAt(i)!=',') {
            			i--;
            		}
            	}
            	count += hasword;
            }
            str = infile + ", ��������" + ++count + "\r\n";
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
    
    //��ȡmenupathĿ¼���ļ�����չ��Ϊextension�������ļ� ����һ���ļ�·������
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
                regex=regex.replace("*","[0-9a-z]{0,}"); //����ƥ��
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
    	
    	//ѭ�������ļ�
    	String inpath = "";
    	String outpath = "";
    	String stoppath = "";
    	String extension = "";//Ҫƥ����ļ���չ��
    	ArrayList<String> filePathList = new ArrayList<>();//�ļ�·������
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
    		//����������Ϣ
    		else if(args[i].equals("-a")) {
    			func[3] = true;
    		}
    		//ͣ�ôʱ�
    		else if(args[i].equals("-e")) {
    			if(i==(args.length-1)) {
    				System.out.println("��-e���������ļ�����");
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
    	
    	//ʵ�ֳ�����
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
	        //���û��ָ������ļ����������result.txt
	        else {
	        	outPut(output,"result.txt");
	        }
    	}
    	System.out.println(output);
    }
}
