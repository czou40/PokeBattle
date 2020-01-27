/**
 * Yellow Pokemon wrapped in a class.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public class BullDogs extends Pokemon {

    /**
     * Constructor.
     *
     * @param level Level.
     */
    BullDogs(int level) {
        super("Georgia Bulldogs", level, 1, 1000 + level * 200,
                new AttackMove(), new FirstAidMove(), new BoostMoraleMove(),
                new UnderminingMove(), new LuckMove(), new UltimateMove());
    }

    /**
     * Constructor.
     */
    BullDogs() {
        this(1);
    }

    /**
     * Automatically choose a move according to a sophisticated algorithm.
     *
     * @param enemy enemy Pokemon.
     * @return Move.
     */
    @Override
    Move autoChooseMove(Pokemon enemy) {
        Effect effect = attack(enemy, new UltimateMove(), false);
        if (effect.getDamageToEnemy() >= enemy.getCurrentHP()) {
            return new UltimateMove();
        }
        effect = attack(enemy, new AttackMove(), false);
        if (effect.getDamageToEnemy() >= 0.06 * enemy.getCurrentHP()) {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(effect.getDamageToEnemy() / enemy.getCurrentHP(),
                    0.2, 8)) {
                return new AttackMove();
            }
        }
        if (this.getAtk() >= 1.25) {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(this.getAtk(), 1.8, 2)) {
                return new UltimateMove();
            }
        }

        if (enemy.getAtk() >= 1.5) {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(enemy.getAtk(), 2.4, 1.75)) {
                return new UnderminingMove();
            }
        }
        Effect enemyEffect = enemy.attack(this, new UltimateMove(), false);
        if (enemyEffect.getDamageToEnemy() >= 0.9 * this.getCurrentHP()) {
            if (this.getCurrentHP() < 0.4 * this.getMaxHP()) {
                return new FirstAidMove();
            } else {
                return new UnderminingMove();
            }
        }
        enemyEffect = enemy.attack(this, new AttackMove(), false);
        if (enemyEffect.getDamageToEnemy() < Math.abs(attack(enemy, new FirstAidMove(), false).getDamageToMe())
                && this.getCurrentHP() < 1.0 / 3 * this.getMaxHP()) {
            return new FirstAidMove();
        }
        if (enemyEffect.getDamageToEnemy() > 1.7 / 10 * this.getMaxHP()) {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(enemyEffect.getDamageToEnemy() / this.getMaxHP(),
                    0.2, 10)) {
                return new UnderminingMove();
            }
        }
        if (this.getAtk() < 1) {
            return new BoostMoraleMove();
        } else {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(this.getAtk(), 1.5, 0.5)) {
                return new AttackMove();
            } else {
                return new BoostMoraleMove();
            }
        }
    }

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    @Override
    public String getPicture() {
        int random = (int) (Math.random() * 9) + 1;
        return String.format("file:src/%d.gif", random);
    }

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    @Override
    public String getWinPicture() {
        return "file:src/uga_win.gif";
    }

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    @Override
    public String getLosePicture() {
        return "file:src/uga_lose.gif";
    }
}
