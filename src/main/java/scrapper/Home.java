package scrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor @ToString @Getter
public class Home {
    private double price;
    private double beds;
    private double bathes;
    private double garages;
}