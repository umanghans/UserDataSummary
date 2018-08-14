import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;


public class UserSummary{
	//Declaring Global Variables and Arrays
	static int[] ageArray = new int[1000];
	static int i = 0;
	static int[] numFriends = new int[1000];
	static int recCount = 0;
	static double[] balArray = new double[1000];
	static String str;
	static String msgStr;
	static double balance=0.0;
	static int activeCount = 0;
	static int[] numMessages;
	static int messages = 0;
	static int messageSum = 0;
	static boolean active = true;
	static boolean state;
	static String gender;
	static int userCount = 0;
 //	static String[][] yearName = new String[1000][2];
	static int[] numberUsers = new int[1000];
	static int[] new_array= new int[1000];
	static int[] times = new int[1000];
	static String Key;
	static int key2;
	static String userName;
	static int m=0,n=0;
	
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        String fileName = "userdata.json";
        Path path = Paths.get(fileName);
        
        try (Reader reader = Files.newBufferedReader(path, 
                StandardCharsets.UTF_8)) {

            JsonParser parser = new JsonParser();
            JsonElement tree = parser.parse(reader);

            JsonArray array = tree.getAsJsonArray();

            for (JsonElement element : array) {

                if (element.isJsonObject()) {

                    JsonObject user = element.getAsJsonObject();
                    
                    //Users in particular years----------
                    //Getting the Year of registration 
                    Key = user.get("registered").getAsString().substring(0,4);
                    key2 = Integer.parseInt(Key);
                    numberUsers[i] = key2;
                    
                    //Balance------------
                    //Get balance in numerical form for conversion purposes
                    str = user.get("balance").getAsString().substring(1).replaceAll("[^\\d.]+", "");
                    balance = Double.parseDouble(str);
                    balArray[i] = balance;
                    
                    //Friends-------------
                    JsonArray friends = user.getAsJsonArray("friends");
                    //Getting the number of friends
                    numFriends[i]=friends.size();
                    
                    //Age---------------
                    //Getting age of users
                    ageArray[i] = user.get("age").getAsInt();
                    
                    //Unread messages-------
                    //Checking state of users
                    state = user.get("isActive").getAsBoolean(); 
                    gender = user.get("gender").getAsString();
                    if((state ==active)&&(gender.equals("female"))){
                	 activeCount++;
                	//Getting unread number of messages in string format from Json
                	 msgStr = user.get("greeting").getAsString().replaceAll("[^\\d]+", "");
                	 messages = Integer.parseInt(msgStr);
                	 messageSum += messages;
                    }
                    i++;
                    //Printing Summary of records
                    if(i==1000) {
                    	recCount++;
                    	System.out.println("Summary of "+(recCount-1)+"000 records to "+recCount+"000 records :- ");
                    	usersInYears();
                    	MedianFriends();
                    	MedianAge();
                    	MeanBalance();
                    	MeanUnreadMessagesFemale();
                    	
                    	System.out.println("_________________________________________________________");
                    }
                    

                }
            }
        }
    }
//  *---------------*
    public static void usersInYears() {
    	if(i==1000) {
    		i=i-1;
    		 HashMap<Integer, Integer> repetitions = new HashMap<Integer, Integer>();

    		  for (int j = 0; j < numberUsers.length; ++j) {
    		      int item = numberUsers[j];

    		      if (repetitions.containsKey(item))
    		          repetitions.put(item, repetitions.get(item) + 1);
    		      else
    		          repetitions.put(item, 1);
    		  }

    		  // Now getting the repetitions counted
    		  StringBuilder sb = new StringBuilder();

    		  int overAllCount = 0;

    		  for (Map.Entry<Integer, Integer> e : repetitions.entrySet()) {
    		      if (e.getValue() > 1) {
    		          overAllCount += 1;

    		          sb.append("\n");
    		          sb.append(e.getKey());
    		          sb.append(": ");
    		          sb.append(e.getValue());
    		      }
    		  }

    		  if (overAllCount > 0) {
    		      sb.insert(0, " years and these many users registered:");
    		      sb.insert(0, overAllCount);
    		      sb.insert(0, "There are ");
    		  }

    		  System.out.print(sb.toString());
    		

    		}
    	i=i+1;
    }
    	
//  *---------------*
    public static void MedianFriends() {
    	if(i==1000) {
    	i=i-1;
    	int median = 0;
		int low = 0;
		int high = numFriends.length - 1;
		quickSort(numFriends, low, high);
		median = (numFriends[i/2]+numFriends[(i+2)/2])/2; // Since we are taking 1000 records at a time median for even number of elements.
		System.out.println("");
		System.out.println("The median number of friends is : " + median);
		i+=1;
    	}
    }
//  *---------------*
    public static void MedianAge() {
//To Check the summary prints for every 1000 records
    	if(i==1000)
    	{	i=i-1;
    		int median = 0;
    		int low = 0;
    		int high = ageArray.length - 1;
    		quickSort(ageArray, low, high);
    		median = (ageArray[i/2]+ageArray[(i+2)/2])/2;
    		System.out.println("The median Age is : " + median);
    		i+=1;
    		}
    }
//  *---------------*
    public static void MeanBalance() {
    	if(i==1000) {
    		i=i-1;
    		double mean = 0.0;;
    		double sum = 0.0;
    		for (int j=(balArray.length-1000); j<balArray.length; j++) {
    	        sum += balArray[j];
    	    }
    	    mean = sum / balArray.length;
    	    DecimalFormat numberFormat = new DecimalFormat("#.00");	    
    	    System.out.println("Mean Balance is : $" + numberFormat.format(mean));
    	    i+=1;
    	 }
    }
//  *---------------*
    public static void MeanUnreadMessagesFemale() {
    	if(i==1000) {
    		int meanMessages = 0;
    		meanMessages = messageSum/activeCount;
    		System.out.println("Mean for number of Unread messages for Active females is :" + meanMessages);
    		messageSum = 0;//Reset the sum and counters for next iteration
    		activeCount = 0;
    		i=0;
    	}
    }
//  *---------------*
    public static void quickSort(int[] arr, int low, int high) {
		if (arr == null || arr.length == 0)
			return;
 
		if (low >= high)
			return;
 
		// pick the pivot
		int middle = low + (high - low) / 2;
		int pivot = arr[middle];
 
		// make left < pivot and right > pivot
		int i = low, j = high;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
 
			while (arr[j] > pivot) {
				j--;
			}
 
			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
 
		// recursively sort two sub parts
		if (low < j)
			quickSort(arr, low, j);
 
		if (high > i)
			quickSort(arr, i, high);
	}

}
