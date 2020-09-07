import javafx.geometry.Pos;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    private final static int START = 25;
    private final static int END = 83;
    private final static String PRICE = "#listing > div > div > section.listing-summary-grid.xs-mt2.xs-mb3.md-mt3" +
            " > section.listing-price.sm-text-right.xs-flex-order-2 > " +
            "div.xs-text-2.sm-text-1.bold.xs-inline.sm-block.xs-mr05.sm-mr0 > span";

    private final static String DESCRIPTION = "#listing > div > div > section.section-listing.section-listing-pad.xs-mb5" +
            ".md-mb3.xs-border-bottom-none.expandable > div > div > span >" +
            " p:nth-child(1)";

    private final static String ADDRESS = "#listing > div > div > section.listing-summary-grid.xs-mt2.xs-mb3.md-mt3 >" +
            " section.listing-location.xs-flex-order-1.min-width-0 > h1";

    public static void main(String[] args) throws IOException {
        String url = "https://www.zolo.ca/surrey-real-estate/houses";
        List<String> links = new ArrayList<>();
        HashSet<String> allLinks = new HashSet<>();

        String test = null;
        Document doc = Jsoup.connect(url).get();

        Elements elements = doc.select("a[href]");

        for (Element e : elements) {
            links.add(e.attr("href"));
        }

        if (links.size() < 1) {
            System.out.println("NULL");
        } else {
            System.out.println("NOT NULL");
            for (int i = START; i < links.size() - END; i++) {
                allLinks.add(links.get(i));
            }
        }

        List<Posting> allPosts = new ArrayList<>();

        for(String s : allLinks){
            Document document = Jsoup.connect(s).get();

            String price = document.select(PRICE).text();
            int askingPrice = Integer.parseInt(stripCommas(price));
            String description = document.select(DESCRIPTION).text();
            String address = document.select(ADDRESS).text();

            Posting posting = new Posting(address, askingPrice, s, description);

            allPosts.add(posting);
        }

        for(Posting p : allPosts){
            System.out.println("LEFT OVER : " + p.leftOverPayment + " \nASKING PRICE : " + p.price + "\nNUM OF SUITES : " + p.suites.size());
        }
    }

    public static String stripCommas(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static void setSuites(String des, ArrayList<Integer> list) {
        String[] lines = des.split("\\.");
        int number = 0;

        for (int i = 0; i < lines.length; i++) {
            if (lines[i].matches(".*\\d.*") && lines[i].contains("suite") && !lines[i].contains("ensuite")|| lines[i].contains("basement") || lines[i].contains("mortgage helper") || lines[i].contains("rental unit")) {
                String check = lines[i].replaceAll("[^0-9]", "");
                if(check.equals("")){
                    continue;
                }
                number += Integer.parseInt(check);

            }
        }
        list.add(number);
    }
}
