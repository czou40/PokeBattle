/**
 * Pokemon.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public abstract class Pokemon implements PokemonUtility {
    private String name;
    private int level;
    private double atk;
    private double maxHP;
    private double currentHP;
    private Move[] moves;
    private boolean lost;

    /**
     * Constructor.
     *
     * @param name  Name.
     * @param level Level.
     * @param atk   Atk.
     * @param maxHP Max HP.
     * @param moves Moves a pokemon has.
     */
    Pokemon(String name, int level, double atk, double maxHP, Move... moves) {
        this.name = name;
        this.level = level;
        this.atk = atk;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.moves = moves;
    }

    /**
     * Getter.
     *
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter.
     *
     * @return Level.
     */
    int getLevel() {
        return level;
    }

    /**
     * Getter.
     *
     * @return Atk.
     */
    public double getAtk() {
        return atk;
    }

    /**
     * Getter.
     *
     * @return Max HP.
     */
    double getMaxHP() {
        return maxHP;
    }

    /**
     * Getter.
     *
     * @return Current HP.
     */
    double getCurrentHP() {
        return currentHP;
    }

    /**
     * Getter.
     *
     * @return Moves a pokemon has.
     */
    Move[] getMoves() {
        return moves;
    }

    /**
     * Setter.
     *
     * @param currentHP Current HP.
     */
    private void setCurrentHP(double currentHP) {
        this.currentHP = Math.max(currentHP, 0);
        if (this.currentHP <= 0) {
            lost = true;
        }
    }

    /**
     * Calculate the raw effect of one move to the enemy. The raw effect only has to do with the move itself.
     * It is not related to the two pokemons.
     *
     * @param moveType Move type.
     * @param isReal   If true, we use random numbers to simulate real situations, where there are
     *                 good lucks, bad lucks, small variations in damage, etc.
     *                 If not true, we just calculate the "theoretical" effect of an attack.
     * @return
     */
    private static Effect calcRawEffect(String moveType, boolean isReal) {
        Effect effect;
        switch (moveType) {
        case AT:
            effect = new Effect(1, 0);
            break;
        case UL:
            effect = new Effect(4, 0, 1.0 / 3, 1);
            break;
        case FA:
            effect = new Effect(0, -0.3);
            break;
        case BM:
            effect = new Effect(0, 0, 1.25, 1);
            break;
        case UM:
            effect = new Effect(0, 0, 1, 0.8);
            break;
        default:
            effect = new Effect(0, 0);
        }
        if (isReal) {
            if (effect.getDamageToEnemy() != 0) {
                effect.setDamageToEnemy(effect.getDamageToEnemy() * (0.9 + Math.random() * 0.2));
            }
            if (effect.getDamageToMe() != 0) {
                effect.setDamageToMe(effect.getDamageToMe() * (0.9 + Math.random() * 0.2));
            }
            if (moveType.equals(AT)) {
                int x = (int) (Math.random() * 10);
                if (x == 0) { //Fatal Attack
                    effect.setDamageToEnemy(effect.getDamageToEnemy() * (1.8 + Math.random() * 0.4));
                    effect.setInfo(I_F);
                }
                if (x == 9) { //unlucky attack
                    effect.setDamageToEnemy(0);
                    effect.setInfo(I_U);
                }
            }
        }
        return effect;
    }

    /**
     * Automatically choose a move according to a sophisticated algorithm.
     *
     * @param enemy enemy Pokemon.
     * @return Move.
     */
    abstract Move autoChooseMove(Pokemon enemy);

    /**
     * A
     *
     * @param enemy  Enemy pokemon.
     * @param move   Move a pokemon would use.
     * @param isReal If true, that is a true attack, the two pokemon's status will be affected. If not true,
     *               We only calculate the "theoretical" damage without actually changing a pokemon's
     *               HP, atk, etc.
     * @return An effect object that contains information about this attack.
     */
    Effect attack(Pokemon enemy, Move move, boolean isReal) {
        Effect effect = calcRawEffect(move.getType(), isReal);
        double damageToEnemy = effect.getDamageToEnemy();
        double damageToMe = effect.getDamageToMe();
        double atkMultiplierToMe = effect.getAtkMultiplierToMe();
        double atkMultiplierToEnemy = effect.getAtkMultiplierToEnemy();
        String info = effect.getInfo();
        damageToEnemy = (int) ((10 * this.level + 100.0 * Math.pow(1.0 * this.level / enemy.level, 2.0 / 3)
                                + (this.atk - 1) * (100 + 10 * this.level)
                                + 4 * move.getPower()) * damageToEnemy);
        damageToMe = (int) ((2.5 + this.level + 25.0 * Math.pow(1.0 * this.level / enemy.level, 2.0 / 3)
                             + (this.atk - 1) * (25 + 2.5 * this.level)
                             + move.getPower()) * damageToMe);
        if (move.getType().equals(FA)) {
            damageToMe = damageToMe * 0.7 - (this.maxHP - this.currentHP) / 2.5 * 0.3;
        } else if (move.getType().equals(UL) || move.getType().equals(AT)) {
            if (damageToEnemy <= 1 && !effect.getInfo().equals(I_U)) {
                damageToEnemy = 1;
            }
        }
        if (!move.getType().equals(UL)) {
            move.incPower();
        }
        if (isReal) {
            this.atk *= atkMultiplierToMe;
            enemy.atk *= atkMultiplierToEnemy;
            enemy.setCurrentHP((int) Math.min(enemy.currentHP - damageToEnemy, enemy.maxHP));
            this.setCurrentHP((int) Math.min(this.currentHP - damageToMe, this.maxHP));
        }
        info = String.format(I, this.getName(), move.getName()) + info;
        return new Effect(damageToEnemy, damageToMe, atkMultiplierToMe, atkMultiplierToEnemy, info);
    }

    Effect attack(Pokemon enemy, Move move) {
        return attack(enemy, move, true);
    }

    /**
     * Getter.
     *
     * @return a boolean telling whether the pokemon is lost (HP == 0).
     */
    public boolean isLost() {
        return lost;
    }

    /**
     * Setter.
     *
     * @param lost If lost, true. Otherwise, false.
     */
    public void setLost(boolean lost) {
        this.lost = lost;
    }

    /**
     * Setter.
     *
     * @param atk Atk.
     */
    public void setAtk(double atk) {
        this.atk = atk;
    }

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    public abstract String getPicture();

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    public abstract String getWinPicture();

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    public abstract String getLosePicture();
}
