public class Posting implements Comparable<Posting> {
    private String titleString;
    private String title;
    private String postingPrice;
    private int price;

    public Posting(String titleString) {
        this.titleString = titleString;
    }

    public void findPrice() {
        //split by whitespace
        String[] titleTmp = titleString.split(" ");

        //first element in array is the price
        String priceTmp = titleTmp[0];
        this.postingPrice = priceTmp;

        //convert price from String to Integer
        if (priceTmp.contains("$")) {
            String priceOfPosting = priceTmp.substring(1);
            this.price = Integer.parseInt(priceOfPosting);
        }
    }

    public String getTitle() {
        return this.title;
    }

    public void findTitle() {
        //get title from postingTitle
        String tmp = titleString.substring(postingPrice.length());
        this.title = tmp.strip();
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public int compareTo(Posting posting) {
        return this.price - posting.price;
    }
}