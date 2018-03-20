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
            	// ���ж�����������
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
            // i ��ѭ���м�������ʵ���϶Կ���ѭ�����á�����Ӧ�Կ�ͷ���Ƿָ��������
            if((tempchar = reader.read()) == -1) {
            	str = fileName + ",��������0";
            	return str;
            }
            reader = new InputStreamReader(new FileInputStream(file));
            for (int i = 1;(tempchar = reader.read()) != -1;i++) {
            	//ÿ�ζ��´ʣ�word��0
            	word = 0;
                while (((char) tempchar) == ' ' || ((char)tempchar) == ',' || ((char)tempchar) == '\r' ||((char)tempchar) == '\n' ||((char)tempchar) == '\t') {
                	//���iΪ1��˵���ǿ�ͷ�ķָ�����ֱ������
                	if(i==1) {
            		count--;
                		break;
                	}
                	else {
	                	word = 1;
	                    if((tempchar = reader.read()) != -1) {
	                    	continue;
	                    }
	                    //��������ļ�ĩβ��
	                    else {
	                    	count--;
	                    	break;
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
            String tempString = null;//��ǰ��������
            int codeLine = 0;
            int blankLine = 0;
            int noteLine = 0;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = reader.readLine()) != null) {
            	//�������ַ�������С��1��Ϊ����
            	if(tempString.length()<1)
            	{
            		blankLine++;
            		continue;
            	}
            	int uselessChar = 0;//'{' '}' �ĸ���
            	int useChar = 0;
            	//���ַ���
            	for(int i = 0;i<tempString.length();i++){
            		//���� '{' '}'��uselessChar++
            		if(tempString.charAt(i)=='{' || tempString.charAt(i)=='}'){
            			uselessChar++;
            		}
            		//����ע�ͷ�
            		else if(tempString.charAt(i)=='/' && (tempString.charAt(i+1)=='/'||tempString.charAt(i+1)=='*')){
            			//��� '{' '}'����С��2����Ϊע����
            			if(uselessChar<2){
            				noteLine++;
            				break;
            			}
            			//2����Ϊ������
            			else {
            				codeLine++;
            				break;
            			}
            		}
            		//����ע���ҷ�
            		else if(tempString.charAt(i)=='*' && tempString.charAt(i+1)=='/') {
            			//λ����ĩ��ע����
            			if(i==(tempString.length()-2)) {
            				noteLine++;
            				break;
            			}
            			//λ����ĩ-1��λ������ĩ�ַ�Ϊ'{' '}'��ע����
            			else if(i==(tempString.length()-3) && (tempString.charAt(i+2)=='{' || tempString.charAt(i+2)=='}')) {
            				noteLine++;
            				break;
            			}
            			//���������
            			else {
            				codeLine++;
            				break;
            			}
            		}
            		//�����û���������ַ����Ҳ�Ϊ�ո���ôΪ��Ч�ַ���Ϊ�����С�
            		else if(tempString.charAt(i)!=' '){
            			useChar++;
            		}
            		//���������ĩ��
            		if((i==tempString.length()-1)){
            			// '{' '}'����С��2��Ϊ����
                		if(uselessChar<2 && useChar <1) {
                			blankLine++;
                		}
                		//2���ϣ�������
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
    	String all = "";//�ڶ�ͣ�ôʱ�ʱ���Ȱ������ļ���������ַ�����
        String word = "";//��ͣ�ôʱ�ʱ�������浱ǰ�����ĵ���
		List<String> stopList = new ArrayList<>();
		BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(stopfile));
            String tempString = null;
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����������all�ַ�����
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
        
        //��ʼ���ַ�ѭ����ͣ�ôʱ�
        for(int i = 0;i<all.length();i++) {
        	//���������Ч�ַ���ӵ�word��
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
        //��word��ӵ�stopList��
        stopList.add(word);
        
        /*
         * �ٶ�infileָ�����ļ��ĵ��ʣ���stopList���жԱȡ�
         */
        File file = new File(infile);
        String str = "";
        word = "";
        int hasword;//���޵��ʣ�0�ޣ�1��
        int count = 0;//ͳ�Ƶ�����
        BufferedReader buttferedReader = null;
        try {
        	buttferedReader = new BufferedReader(new FileReader(file));
            String tempString = null;
            String whole = buttferedReader.readLine();//�������ļ�����whole�ַ������whole���в���
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����
            while ((tempString = buttferedReader.readLine()) != null) {
                whole = whole +'\n'+ tempString;
            }
            for(int i = 0;i<whole.length();i++) {
            	hasword = 0;
            	//������Ч�ַ�����ӵ�word��
            	if(whole.charAt(i)!=' ' && whole.charAt(i)!='\r' && whole.charAt(i)!='\n' && whole.charAt(i)!='\t' && whole.charAt(i)!=',') {
            		word += whole.charAt(i);
            		if(i==whole.length()-1) {
            			if(!word.isEmpty()) {
            				//���word��Ϊ�����word��ȥ��stopList�Ƚ�
            				hasword = 1;
            				for(int j = 0;j<stopList.size();j++) {
            					if(word.equals(stopList.get(j))) {
            						hasword = 0;
            					}
            				}
            				//�����word�ÿ�
            				word = "";
            			}
            		}
            	}
            	//�����ָ���
            	else {
            		//ѭ����
            		while(whole.charAt(i)==' ' || whole.charAt(i)=='\r' || whole.charAt(i)=='\n' || whole.charAt(i)=='\t' || whole.charAt(i)==',') {
            			if(!word.isEmpty()) {
            				//���word��Ϊ�����word��ȥ��stopList�Ƚ�
            				hasword = 1;
            				for(int j = 0;j<stopList.size();j++) {
            					if(word.equals(stopList.get(j))) {
            						hasword = 0;
            					}
            				}
            				//�����word�ÿ�
            				word = "";
            			}
            			if(i<whole.length()-1){
            				i++;
            			}
            			else {
            				break;
            			}
            		}
            		//�Ͷ���������ͬ���ĵ���
            		if(whole.charAt(i)!=' ' && whole.charAt(i)!='\r' && whole.charAt(i)!='\n' && whole.charAt(i)!='\t' && whole.charAt(i)!=',') {
            			i--;
            		}
            	}
            	count += hasword;
            }
            str = infile + ", ��������" + count + "\r\n";
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
    
    public static void main(String args[]){
    	
    	//ѭ�������ļ�
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
    				while(i<(args.length)) {
    					if(args[i].equals("-e") || args[i].equals("-o")) {
    						i--;
    						break;
    					}
    					filePathList.add(args[i++]);
    				}
    			}
    			else {
    				filePathList.add(args[i]);
    			}
    		}
    	}
    	
    	//ʵ�ֳ�����
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
    	}
    	if(func[6]){
	       	outPut(output,outpath);
	    }
    	else {
    		outPut(output,"result.txt");
    	}
    	System.out.println(output);
    }
}
