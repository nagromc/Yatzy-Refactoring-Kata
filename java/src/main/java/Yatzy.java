import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Yatzy {

    private static final int PAIR = 2;
    private static final int THREE_OF_A_KIND = 3;
    private static final int FOUR_OF_A_KIND = 4;
    private final List<Integer> dice;

    public Yatzy(int d1, int d2, int d3, int d4, int d5) {
        dice = List.of(d1, d2, d3, d4, d5);
    }

    public int chance() {
        return sumOfAllDice();
    }

    private int sumOfAllDice() {
        return dice.stream().mapToInt(Integer::intValue).sum();
    }

    public int ones() {
        return sumOfValues(1);
    }

    public int twos() {
        return sumOfValues(2);
    }

    public int threes() {
        return sumOfValues(3);
    }

    public int fours() {
        return sumOfValues(4);
    }

    public int fives() {
        return sumOfValues(5);
    }

    public int sixes() {
        return sumOfValues(6);
    }

    private int sumOfValues(int number) {
        return dice.stream().mapToInt(Integer::intValue)
            .filter(d -> d == number)
            .sum();
    }

    public int smallStraight() {
        if (diceAre12345())
            return 15;
        return 0;
    }

    private boolean diceAre12345() {
        return diceAre(List.of(1, 2, 3, 4, 5));
    }

    private boolean diceAre(List<Integer> requiredDice) {
        return dice.stream().mapToInt(Integer::intValue).boxed()
            .collect(Collectors.toList())
            .containsAll(requiredDice);
    }

    public int largeStraight() {
        if (diceAre23456())
            return 20;
        return 0;
    }

    private boolean diceAre23456() {
        return diceAre(List.of(2, 3, 4, 5, 6));
    }

    public int yatzy() {
        if (allDiceAreTheSame())
            return 50;
        return 0;
    }

    private boolean allDiceAreTheSame() {
        return dice.stream().mapToInt(Integer::intValue)
            .distinct()
            .count() == 1;
    }

    public int score_pair() {
        return sumDiceByFrequency(1, PAIR);
    }

    public int two_pair() {
        return sumDiceByFrequency(2, PAIR);
    }

    public int three_of_a_kind() {
        return sumDiceByFrequency(1, THREE_OF_A_KIND);
    }

    public int four_of_a_kind() {
        return sumDiceByFrequency(1, FOUR_OF_A_KIND);
    }

    public int fullHouse() {
        return sumDiceByFrequency(1, PAIR) + sumDiceByFrequency(1, THREE_OF_A_KIND);
    }

    private int sumDiceByFrequency(int numberOfDice, int frequency) {
        return dice.stream()
            .collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()))
            .entrySet().stream()
            .filter(e -> e.getValue() >= frequency)
            .sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
            .limit(numberOfDice)
            .mapToInt(e -> e.getKey() * frequency)
            .sum();
    }

}



