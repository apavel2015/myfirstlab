import java.io.*;
import java.util.*;
import java.util.regex.*;

class AllTweets implements IHelper {
    
	class Tweet{
		String text;
		Time Time;
		Tweet(String input){
			Pattern pattern=Pattern.compile("((\\s|\\S)*\")|(:(\\s|\\S)*)");
			Matcher matcher=pattern.matcher(input);
			matcher.find();
			text=matcher.group();
			text=text.substring(1,text.length()-1);
			matcher.find();
			Time=new Time(matcher.group());
		}
		
	}
	public Sentiments sentiments;
	public States states;
	Boolean error=true;
	public Boolean IsError(){return error;}
	private ArrayList<Tweet> elements=null;
	public int n(){return elements.size();}
	public int EmotionsInTime(Time Start,Time End){
		int IS=0,IE=0;
		if(Time.Sravnit(Start,End)<2) return 0;
		if(Time.Sravnit(End,elements.get(0).Time)==2) return 0;
		if(Time.Sravnit(End,elements.get(0).Time)==0) return sentiments.Get(0);
		Time S=null,E=null;
                if(Time.Sravnit(Start, elements.get(0).Time)==0 || 
                        Time.Sravnit(Start, elements.get(0).Time)==2) {
                    S=elements.get(0).Time;
                    IS=0;
                }
		for(int i=1;i<elements.size();i++){
			if(S==null && Time.Sravnit(elements.get(i).Time,Start)!=2) {S=elements.get(i).Time; IS=i;}
                        if(Time.Sravnit(elements.get(i).Time,End)==0) {E=elements.get(i).Time; IE=i; break;}
			if(Time.Sravnit(elements.get(i).Time,End)==1) {E=elements.get(i-1).Time; IE=i-1;break;}
			
		}
		if(S==null) return 0;
		if(E==null) {E=elements.get(elements.size()-1).Time; IE=elements.size()-1;}
		int sum=0;
                
		for(int i=IS;i<=IE;i++){
			sum+=sentiments.Get(i);
		}
		return sum;
	}
	AllTweets(String tweets,String sent,String st){
		Tweet TEMP;
		String temp;
		elements=new ArrayList<Tweet>();
		try(FileReader fr=new FileReader(tweets); BufferedReader br=new BufferedReader(fr)){
			while((temp=br.readLine())!=null) {
				TEMP=new Tweet(temp);
				elements.add(TEMP);}
		}
		catch(Exception ex){}
		error=false;
		sentiments=new Sentiments(sent);
		states=new States(st);
	}
        public String MostTweets(Time Start,Time End){
		int IS=0,IE=0;
                Time S=null,E=null;
		if(Time.Sravnit(Start,End)<2) return null;
		if(Time.Sravnit(End,elements.get(0).Time)==2) return null;
		if(Time.Sravnit(End,elements.get(0).Time)==0) {
                    E=elements.get(0).Time; 
                    IE=0;}
                if(Time.Sravnit(Start, elements.get(0).Time)==0 || 
                        Time.Sravnit(Start, elements.get(0).Time)==2) {
                    S=elements.get(0).Time;
                    IS=0;
                }
		for(int i=1;i<elements.size();i++){
			if(S==null && Time.Sravnit(elements.get(i).Time,Start)!=2) {S=elements.get(i).Time; IS=i;}
			if(E==null && Time.Sravnit(elements.get(i).Time,End)==1) {E=elements.get(i-1).Time; IE=i-1;break;}
			if(E==null && Time.Sravnit(elements.get(i).Time,End)==0) {E=elements.get(i).Time; IE=i; break;}
		}
		if(S==null) return null;
		if(E==null) {E=elements.get(elements.size()-1).Time; IE=elements.size()-1;}
                int n=states.states.size();
                Integer[] maxcount=new Integer[n];
                Integer[] mess;
		for(int i=0;i<n;i++){
                    mess=states.messages.get(i);
                    for(int j=0;j<mess.length;j++){
                        if(mess[j]>=IS && mess[j]<=IE) maxcount[i]++;
                    }
                }
                int max=0;
                for(int i=0;i<n;i++){
                    for(int j=i+1;j<n;j++){
                        if(maxcount[j]>maxcount[max])max=j;
                    }
                }
                return states.states.get(max);
	}
	public Tweet Get(int index){
		try{return elements.get(index);}
		catch(Throwable th){return null;}
	}
	public void Print(int index){
		Console console=System.console();
		try{console.printf((elements.get(index)).text);}
		catch(Throwable th){console.printf("False index");}
	}
	public ArrayList<Integer> have_hashtag(String tag){
		Pattern pattern=Pattern.compile(tag);
		ArrayList<Integer> indexes=new ArrayList<Integer>();
		for(int i=0;i<elements.size();i++){
			if(pattern.matcher(elements.get(i).text).find()==true) indexes.add(i);
		}
		if(indexes.isEmpty()) return null;
		return indexes;
		
	}
	
}