package scrapper;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class DefaultScraper implements Scraper {
    private static final String PRICE_SELECTOR = ".detail__info-xlrg";
    private static final String BEDS_SELECTOR = ".nhs_BedsInfo";
    private static final String BATHS_SELECTOR = ".nhs_BathsInfo";
    private static final String GARAGE_SELECTOR = ".nhs_GarageInfo";

    @Override @SneakyThrows
    public Home parse(String url) {
        Document doc = Jsoup.connect(url).get();
        return new Home(getPrice(doc), getBeds(doc), getBaths(doc), getGarage(doc));
    }

    private static double getPrice(Document doc) {
        Element priceTag = doc.selectFirst(PRICE_SELECTOR);
        assert priceTag != null;
        String priceText = priceTag.text().replaceAll("[^0-9]", "");
        return Double.parseDouble(priceText);
    }

    private static double getBeds(Document doc) {
        Element bedTag = doc.selectFirst(BEDS_SELECTOR);
        if (bedTag == null) {
            return -1.0;
        }
        String bedText = bedTag.text().replaceAll("[^0-9.,]", "");
        return Double.parseDouble(bedText);
    }

    private static double getBaths(Document doc) {
        Element bathTag = doc.selectFirst(BATHS_SELECTOR);
        if (bathTag == null) {
            return -1.0;
        }
        String bathText = bathTag.text().replaceAll("[^0-9.,]", "");
        return Double.parseDouble(bathText);
    }

    private static double getGarage(Document doc) {
        Element garageTag = doc.selectFirst(GARAGE_SELECTOR);
        if (garageTag == null) {
            return -1.0;
        }
        String garageText = garageTag.text().replaceAll("[^0-9,.]", "");
        return Double.parseDouble(garageText);
    }
}
