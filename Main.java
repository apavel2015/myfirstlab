import java.io.*;
import java.util.Scanner;
import java.util.*;
public class Main {
public static void Hashtag(AllTweets tweets,Console console){
    
    console.printf("Vvedite heshteg:\n");
    String tag=console.readLine();
    ArrayList<Integer> list=tweets.have_hashtag(tag);
    if(list==null) console.printf("Netu Sovpadeniy\n");
    else {
        console.printf("Sovpadeniya:\n");
        for(int i=0;i<list.size();i++)
        {
            tweets.Print(list.get(i));
            console.printf("\n");
        }
        }
}
public static void EmoVes(AllTweets tweets,Console console){
    String Ot,Do;
    console.printf("Vvedite promezhutok vremeni (v formate YYYY.MM.DD.HH:MM:SS):\nOt:\n");
    Ot=console.readLine();
    console.printf("Do:\n");
    Do=console.readLine();
    int ves=tweets.EmotionsInTime(new Time(Ot),new Time(Do));
    console.printf("Iskomiy ves: %d",ves);
}
public static void MostTweetsFromOneState(AllTweets tweets,Console console){
    String Ot,Do;
    console.printf("Vvedite promezhutok vremeni (v formate YYYY.MM.DD.HH:MM:SS):\nOt:\n");
    Ot=console.readLine();
    console.printf("Do:\n");
    Do=console.readLine();
    String state=tweets.MostTweets(new Time(Ot),new Time(Do));
    if(state==null) console.printf("Net tvitov v zadanniy promezhutok vremeni");
    console.printf(state);
}
	public static void main(String[] args) {
		
		AllTweets tweets=new AllTweets("D:\\Users\\User2\\Documents\\NetBeansProjects\\JavaApplication1\\src\\all_tweets.txt",
				"D:\\Users\\User2\\Documents\\NetBeansProjects\\JavaApplication1\\src\\sentiments.csv",
				"D:\\Users\\User2\\Documents\\NetBeansProjects\\JavaApplication1\\src\\states.json");
		Console console=System.console();
                Integer K;
                do{
                console.printf("Vyberite:\n");
                console.printf("0.Vyhod\n");
                console.printf("1.Opredelit vse tvity, soderzhaschie heshteg\n");
                console.printf("2.Opredelit emotsionalniy ves tvitov za zadanniy promezhutok vremeni\n");
                console.printf("3.Opredelit shtat s naib. kolichestvom tvitov\n");
                K=Integer.parseInt(console.readLine());
                if(K==1) Hashtag(tweets,console);
                if(K==2) EmoVes(tweets,console);
                if(K==3) MostTweetsFromOneState(tweets,console);
                }
                while(K!=0);
	}

}
