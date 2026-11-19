// Common interface for all drinks.
// Every drink must have these two methods, but each type of drink
// can write them in its own way (polymorphism).
public interface DrinkPreparation {
    void prepareDrink();
    double calculateUnitPrice();
}
