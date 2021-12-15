package scrapper;

public class Main {
    public static void main(String[] args) {
        String url = "https://www.newhomesource.com/specdetail/130-victoria-peak-loop-dripping-springs-tx-78620/2108550";
        DefaultScraper defaultScraper = new DefaultScraper();
        Home home = defaultScraper.parse(url);
        System.out.println(home.getPrice());
        System.out.println(home.getBeds());
        System.out.println(home.getBathes());
        System.out.println(home.getGarages());
        System.out.println();

        CacheScraper cacheScraper = new CacheScraper();
        Home home1 = cacheScraper.parse(url);
        Home home2 = cacheScraper.parse(url);
        System.out.println(home1.toString());
        System.out.println(home2.toString());
    }
}