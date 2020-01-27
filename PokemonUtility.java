/**
 * Pokemon Utility. Contains some constants and static methods.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public interface PokemonUtility {
    double STAGE_WIDTH = 1280;
    double STAGE_HEIGHT = 720;
    double[] FULL_SPAN = {100};
    double[] CUSTOM_ROW_SPAN = {10, 65, 10, 15};
    int MOVE_BUTTON_EACH_ROW = 3;
    double MARGIN = 10;
    int[] COLOR_FULL_HP = {38, 125, 255};
    int[] COLOR_ZERO_HP = {255, 71, 71};
    String AT = "Attack!";
    String D_AT = "Attack your enemy physically!";
    String FA = "First Aid";
    String D_FA = "Recover your HP! Notice: When your HP is low, this move will be very effective!";
    String UL = "Ultimate Move";
    String D_UL = "This move can inflict four times the regular damage to your enemy. However, your atk will "
                  + "decrease by 67% after using this move. ";
    String BM = "Boost Morale";
    String D_BM = "Increase your atk by 25%! ";
    String FL = "I'm feeling lucky!";
    String D_FL = "Let computer choose which move to use! PS: This is determined by sophisticated algorithms!";
    String UM = "Undermining";
    String D_UM = "Decrease your enemy's atk by 20%! ";
    String I = "%s uses %s. ";
    String I_F = "It launches a fatal attack! ";
    String I_U = "Unfortunately, it misses its target. ";
    String INFO = "LV: %s    HP: %.0f/%.0f    ATK: %.2f";
    String PROGRESS_BAR_COLOR = "-fx-accent:rgb(%d,%d,%d);";
    String D_E = "Easy";
    String D_I = "Intermediate";
    String D_H = "Hard";
    String MY_NAME = "Me: %s";
    String ENEMY_NAME = "Enemy: %s";
    String LOST = "You lost the battle! The program will exit in %d %s.";
    String NEXT = "You won the battle! The next battle will begin in %d %s.\nIt will be more difficult!";
    String S_S = "second";
    String S_P = "seconds";
    int DISPLAY_ME_ONLY = 0;
    int DISPLAY_ENEMY_ONLY = 1;
    int DISPLAY_BOTH = 2;

    /**
     * Generate an array for the Constraint of a grid pane.
     * The array specifies how the space will be equally divided.
     *
     * @param x How many parts we will divide our grid pane into.
     * @return An array that specifies how the space will be equally divided.
     */
    static double[] getSpan(int x) {
        double[] result = new double[x];
        for (int i = 0; i < x; i++) {
            result[i] = 100.0 / x;
            result[i] = 100.0 / x;
        }
        return result;
    }

    /**
     * Get the description for a specific type of move.
     *
     * @param type Move type.
     * @return Description.
     */
    static String getDescription(String type) {
        switch (type) {
        case AT:
            return D_AT;
        case FA:
            return D_FA;
        case UL:
            return D_UL;
        case BM:
            return D_BM;
        case FL:
            return D_FL;
        case UM:
            return D_UM;
        default:
            return null;
        }
    }

    /**
     * Get progress bar color with respect to the percentage of HP a pokemon has left.
     * When a pokemon's HP decreases, the color of the progress bar will be more reddish.
     *
     * @param progress percentage of HP a pokemon has left.
     * @return An array describing the RGB of a color.
     */
    static int[] getProgressBarColor(double progress) {
        int r = (int) (COLOR_FULL_HP[0] * progress + COLOR_ZERO_HP[0] * (1 - progress));
        int g = (int) (COLOR_FULL_HP[1] * progress + COLOR_ZERO_HP[1] * (1 - progress));
        int b = (int) (COLOR_FULL_HP[2] * progress + COLOR_ZERO_HP[2] * (1 - progress));
        return new int[] {r, g, b};
    }

    /**
     * Used to calculate the possibility of a pokemon using a specific move.
     *
     * @param x         The variable that matters.
     * @param offset    The center of the logistic function.
     * @param steepness How steep the logistic function is.
     * @return The possibility.
     */
    static double logisticFunction(double x, double offset, double steepness) {
        return 1.0 / (1 + Math.exp(-steepness * (x - offset)));
    }
}
