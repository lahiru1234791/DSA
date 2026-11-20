/**
 * The classic "Rabbit Problem" (this is where the Fibonacci sequence
 * actually comes from historically).
 *
 * THE PROBLEM:
 * You start with 1 pair of baby rabbits. Rules:
 *   - A pair of rabbits takes 1 month to grow up (mature) before they
 *     can have babies.
 *   - Once mature, every pair produces exactly 1 new pair every month.
 *   - Rabbits never die.
 * Question: how many pairs of rabbits are there after N months?
 *
 * WHY THIS BECOMES FIBONACCI:
 * In any given month, the number of pairs equals:
 *   - all the pairs that already existed last month (none of them die), PLUS
 *   - one new pair born from every pair that was ALREADY MATURE two
 *     months ago (since it takes a month to mature, a pair born last
 *     month is too young to have babies yet).
 * So: pairs(month) = pairs(month - 1) + pairs(month - 2)
 * That is exactly the Fibonacci rule.
 *
 * Month:  1  2  3  4  5  6  7  8   9  10
 * Pairs:  1  1  2  3  5  8  13 21  34  55
 */
public class RabbitProblem {

    // Works out the number of rabbit pairs after the given number of
    // months, by building the sequence up from the start (iterative,
    // no repeated recalculation - this is much faster than plain
    // recursion for large month counts).
    public static int pairsAfterMonths(int months) {
        if (months <= 0) {
            throw new RuntimeException("Months must be a positive number");
        }
        if (months == 1 || months == 2) {
            return 1; // the starting pair, and still just 1 pair the next month
        }

        int monthsAgo2 = 1; // pairs(month - 2), starts as pairs(1)
        int monthsAgo1 = 1; // pairs(month - 1), starts as pairs(2)
        int current = 0;

        for (int month = 3; month <= months; month++) {
            current = monthsAgo1 + monthsAgo2; // this month = last month + month before that
            monthsAgo2 = monthsAgo1;
            monthsAgo1 = current;
        }

        return current;
    }

    // A second, slower way to solve exactly the same problem using plain
    // recursion, just to show the two approaches side by side. This one
    // recalculates smaller months over and over, so it gets slow for
    // large values of "months" - fine for a demo though.
    public static int pairsAfterMonthsRecursive(int months) {
        if (months <= 0) {
            throw new RuntimeException("Months must be a positive number");
        }
        if (months == 1 || months == 2) {
            return 1;
        }
        return pairsAfterMonthsRecursive(months - 1) + pairsAfterMonthsRecursive(months - 2);
    }

    public static void main(String[] args) {
        int totalMonths = 10;

        System.out.println("Rabbit pairs month by month (iterative method):");
        for (int month = 1; month <= totalMonths; month++) {
            System.out.println("  Month " + month + ": " + pairsAfterMonths(month) + " pair(s)");
        }

        System.out.println();
        System.out.println("Same answer using the recursive method (should match above):");
        for (int month = 1; month <= totalMonths; month++) {
            System.out.println("  Month " + month + ": " + pairsAfterMonthsRecursive(month) + " pair(s)");
        }
    }
}