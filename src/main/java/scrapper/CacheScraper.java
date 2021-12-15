package scrapper;

import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CacheScraper implements Scraper {
    @Override @SneakyThrows
    public Home parse(String url) {
        // Created connection to DB
        Connection connection = DriverManager.getConnection("jdbc:sqlite:./housesDb");
        Statement statement = connection.createStatement();

        // Execute query
        String query = String.format("select count(*) as count from Houses where url='%s';", url);
        ResultSet rs = statement.executeQuery(query);

        // Extract result
        Home home;
        if (rs.getInt("count") > 0) {
            query = String.format("select Price, Beds, Baths, Garage from Houses where url='%s';", url);
            ResultSet resultSet = statement.executeQuery(query);
            home = new Home(resultSet.getDouble("Price"), resultSet.getDouble("Beds"), resultSet.getDouble("Baths"), resultSet.getDouble("Garage"));
        } else {
            DefaultScraper defaultScraper = new DefaultScraper();
            home = defaultScraper.parse(url);
            query = String.format("insert into Houses(url, Price, Beds, Baths, Garage) values('%s', %f, %f, %f, %f);", url, home.getPrice(), home.getBeds(), home.getBathes(), home.getGarages());
            statement.executeUpdate(query);
        }
        return home;
    }
}
