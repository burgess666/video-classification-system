package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.lucene.analysis.Token; 
import org.apache.lucene.analysis.TokenStream; 
import net.paoding.analysis.analyzer.PaodingAnalyzer;
import org.apache.lucene.analysis.Analyzer;


public class Train  implements Serializable {  
		    
	    private static final long serialVersionUID = 1L;  
	    public final static String SERIALIZABLE_PATH = "/Train/SogouC.mini.20061102/Sample/Train.ser";  
	    // the path of train data set  
	    private String trainPath = "/Train/SogouC.mini.20061102/Sample";  
	    // the number of type and the real name  
	    private  Map<String, String> classMap = new HashMap<String, String>();  
	    // the type and text count
	    private Map<String, Integer> classP = new ConcurrentHashMap<String, Integer>();  
	    // all text count  
	    private AtomicInteger actCount = new AtomicInteger(0);      
	    // type to dictionary and frequency
	    private Map<String, Map<String, Double>> classWordMap = new ConcurrentHashMap<String, Map<String, Double>>();  
	    // classifier  
	    private transient Analyzer analyzer;  
	    private static Train trainInstance = new Train();  
	  
	    public static Train getInstance() {  
	        trainInstance = new Train();  
	  
	        // read the object of file
	        FileInputStream fis;  
	        try {  
	            File f = new File(SERIALIZABLE_PATH);  
	            if (f.length() != 0) {  
	                fis = new FileInputStream(SERIALIZABLE_PATH);  
	                ObjectInputStream oos = new ObjectInputStream(fis);  
	                trainInstance = (Train) oos.readObject();  
	                trainInstance.analyzer = new PaodingAnalyzer();  
	            } else {  
	                trainInstance = new Train();  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return trainInstance;  
	    }  
	  
	    private Train() {  
	        this.analyzer = new PaodingAnalyzer(); 
	    }  
	  
	    //read text
	    public String readtxt(String path) {  
	        BufferedReader br = null;  
	        StringBuilder str = null;  
	        try {  
	            br = new BufferedReader(new FileReader(path));  
	  
	            str = new StringBuilder();  
	  
	            String r = br.readLine();  
	  
	            while (r != null) {  
	                str.append(r);  
	                r = br.readLine();  
	            }  
	  
	            return str.toString();  
	        } catch (IOException ex) {  
	            ex.printStackTrace();  
	        } finally {  
	            if (br != null) {  
	                try {  
	                    br.close();  
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                }  
	            }  
	            str = null;  
	            br = null;  
	        }  
	  
	        return "";  
	    }  
	  
	    /** 
	     * train data
	     */  
	    public void realTrain() {  
	        // Initial  
	        classMap = new HashMap<String, String>();  
	        classP = new HashMap<String, Integer>();  
	        actCount.set(0);  
	        classWordMap = new HashMap<String, Map<String, Double>>();  
	        // set types in detail 
			 classMap.put("C000007", "Car");  
		     classMap.put("C000008", "Finance");  
		     classMap.put("C000010", "IT");  
		     classMap.put("C000013", "Health");  
		     classMap.put("C000014", "Sports");  
		     classMap.put("C000016", "Travel");  
		     classMap.put("C000020", "Education");  
		     classMap.put("C000022", "Employment");  
		     classMap.put("C000023", "Culture");  
		     classMap.put("C000024", "Military"); 
	  
	        //calculate sample counts for each type
	        Set<String> keySet = classMap.keySet();  
	  
	        // take all words to calculate the frequency of words   
	        final Set<String> allWords = new HashSet<String>();  
	  
	        // save words content for each type
	        final Map<String, List<String[]>> classContentMap = new ConcurrentHashMap<String, List<String[]>>();  
	  
	        for (String classKey : keySet) {  
	        	Analyzer participle = new PaodingAnalyzer();  
	            Map<String, Double> wordMap = new HashMap<String, Double>();  
	            File f = new File(trainPath + File.separator + classKey);  
	            File[] files = f.listFiles(new FileFilter() {  
	  
	                public boolean accept(File pathname) {  
	                    if (pathname.getName().endsWith(".txt")) {  
	                        return true;  
	                    }  
	                    return false;  
	                }  
	  
	            });  
	  
	            //  save the vendor of words for each type
	            List<String[]> fileContent = new ArrayList<String[]>();  
	            if (files != null) {  
	                for (File txt : files) {  
	                    String content = readtxt(txt.getAbsolutePath());  
	                    String[] word_arr = tokenStream(content);  
	                    if(word_arr!=null){
	                    	 fileContent.add(word_arr);  
	 	                    // count the frequency of words  
	 	                    for (String word : word_arr) {  
	 	                        if (wordMap.containsKey(word)) {  
	 	                            Double wordCount = wordMap.get(word);  
	 	                            wordMap.put(word, wordCount + 1);  
	 	                        } else {  
	 	                            wordMap.put(word, 1.0);  
	 	                        }     
	 	                    }  
	                    }
	                }  
	            }  
	  
	            // type to dictionary and frequency 
	            classWordMap.put(classKey, wordMap);  
	  
	            // text counts for each type 
	            classP.put(classKey, files.length);  
	            actCount.addAndGet(files.length);  
	            classContentMap.put(classKey, fileContent);  
	  
	        }  
	  
	        // Serializable trainer object to local space
	        FileOutputStream fos;  
	        try {  
	            fos = new FileOutputStream(SERIALIZABLE_PATH);  
	            ObjectOutputStream oos = new ObjectOutputStream(fos);  
	            oos.writeObject(this);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	  
	    }  
	  
	    /** 
		 * classification 
		 *  
		 * @param text 
		 * @return return the probability value for each type 
		 */  
		public Map<String, Double> classify(String text) {  
		   
		    String[] text_words = tokenStream(text); 
		    Map<String, Double> frequencyOfType = new HashMap<String, Double>();  
		    Set<String> keySet = classMap.keySet();  
		    for (String classKey : keySet) {  
		        double typeOfThis = 1.0;  
		        Map<String, Double> wordMap = classWordMap.get(classKey);  
		        for (String word : text_words) {  
		            Double wordCount = wordMap.get(word);  
		            int articleCount = classP.get(classKey);  
		
		            /* 
		             * Double wordidf = idfMap.get(word); if(wordidf==null){ 
		             * wordidf=0.001; }else{ wordidf = Math.log(actCount / wordidf); } 
		             */  
		
		            // give minimal value, if there has no the word in all samples 
		            double term_frequency = (wordCount == null) ? ((double) 1 / (articleCount + 1))  
		                    : (wordCount / articleCount);  
		
		            // using feature vector to do statistics for the probability of text type 
		            // p = word1/article counts * word2/article counts * ........
		
		            typeOfThis = typeOfThis * term_frequency * 10;  
		            typeOfThis = ((typeOfThis == 0.0) ? Double.MIN_VALUE  
		                    : typeOfThis);  
		             System.out.println(typeOfThis+" : "+term_frequency+" : "+actCount);  
		        }  
		
		        typeOfThis = ((typeOfThis == 1.0) ? 0.0 : typeOfThis);  
		
		        //  the frequency of this type article  
		        double classOfAll = classP.get(classKey) / actCount.doubleValue();  
		        // Naive Bayes $(A|B)=S(B|A)*S(A)/S(B),$(B) is constant  
		        frequencyOfType.put(classKey, typeOfThis * classOfAll);  
		    }  
		
		    return frequencyOfType;  
		}

		 
	  //print result
	    public void pringAll() {  
	        Set<Entry<String, Map<String, Double>>> classWordEntry = classWordMap  
	                .entrySet();  
	        for (Entry<String, Map<String, Double>> ent : classWordEntry) {  
	            System.out.println("Type£º " + ent.getKey());  
	            Map<String, Double> wordMap = ent.getValue();  
	            Set<Entry<String, Double>> wordMapSet = wordMap.entrySet();  
	            for (Entry<String, Double> wordEnt : wordMapSet) {  
	                System.out.println(wordEnt.getKey() + ":" + wordEnt.getValue());  
	            }  
	        }  
	    }  
	    
	    private String[] tokenStream(String indexStr){
	    	List<String> list = new ArrayList<String>();
	    	try{
	    		 
	    		 StringReader reader = new StringReader(indexStr); 
		         TokenStream ts = analyzer.tokenStream(indexStr, reader); 
		         Token t = ts.next(); 
		         while (t != null) { 
		        	 //System.out.print(t.termText()+"     "); 
		             list.add(t.termText());
		             t = ts.next();
		         }
	    	}catch(Exception e){
	    		e.printStackTrace();
	    	}
	         return (String[])list.toArray(new String[list.size()]);
	    }
	  
	    public Map<String, String> getClassMap() {  
	        return classMap;  
	    }  
	  
	    public void setClassMap(Map<String, String> classMap) {  
	        this.classMap = classMap;  
	    }  
	    
	    public static String getType(Map<String, Double> resultMap){
	          String keys="";
	          double top=0d;
	    	  for (String key : resultMap.keySet()) {
	    		   double temp = resultMap.get(key);
	    		   if(temp>top){
	    			   keys = key;
	    			   top = temp;
	    		   }
			  }
	    	  Map<String, String> classMap = new HashMap<String, String>();  
			  classMap.put("C000007", "Car");  
		      classMap.put("C000008", "Finance");  
		      classMap.put("C000010", "IT");  
		      classMap.put("C000013", "Health");  
		      classMap.put("C000014", "Sports");  
		      classMap.put("C000016", "Travel");  
		      classMap.put("C000020", "Education");  
		      classMap.put("C000022", "Employment");  
		      classMap.put("C000023", "Culture");  
		      classMap.put("C000024", "Military");  
	    	  return classMap.get(keys);
	    }
	    
		public static void main(String[] args) {
			Train train = Train.getInstance();
			//train.realTrain();
			Map<String, Double> resultMap = train.classify("ÀºÇò±ÈÈü");
			  for (String key : resultMap.keySet()) {
			     System.out.println("key= "+ key + " and value= " + resultMap.get(key));
			  }
			  System.out.println(getType(resultMap));	  
		} 
}
