import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Scraper{
    public static void main(String[] args) throws IOException {
        //test url
        String url = "https://vancouver.craigslist.org/search/cta?query=range+rover";
        ArrayList<Posting> posts = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        String title = null;

        //select table to extract info
        for(Element e : doc.select("#sortable-results > ul")){
            title = e.select("li.result-row").select("a").text();
        }

        // "restore restore this posting" is in between posting titles, split to get individual titles and prices
        String[] split = title.split(" restore restore this posting ");

        instantiatePostings(split, posts);
        removeDuplicates(posts);
        sort(posts);
        showPostings(posts);
    }

    public static boolean validPosting(Posting post){
        if(post.getPrice() <= 0){
            //setup for garbage collection
            post = null;
            return false;
        }
        return true;
    }

    public static void sort(ArrayList<Posting> posts){
        Collections.sort(posts);
    }

    public static void removeDuplicates(ArrayList<Posting> posts){
        HashSet <Posting> p = new HashSet<>();
        for(Posting posting : posts){
            if(!p.contains(posting)){
                p.add(posting);
            }
        }
        //remove all posting in array
        posts.clear();

        //add postings back to array with no duplicates
        for(Posting posting : p){
            posts.add(posting);
        }
    }

    public static void showPostings(ArrayList<Posting> posts){
        for(Posting p : posts){
            System.out.println("Title : " + p.getTitle());
            System.out.println("Price : $" + p.getPrice() + "\n");
        }
    }

    public static void instantiatePostings(String[] titles, ArrayList<Posting> posts){
        for(String s : titles){
            Posting posting = new Posting(s);
            posting.findPrice();
            posting.findTitle();

            if(validPosting(posting)){
                posts.add(posting);
            }
        }
    }
}