/**
 * Yellow Pokemon wrapped in a class.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public class YellowJacket extends Pokemon {
    YellowJacket(int level) {
        super("Georgia Tech Yellow Jacket", level, 1, 1000 + level * 200,
                new AttackMove(), new FirstAidMove(), new BoostMoraleMove(),
                new UnderminingMove(), new LuckMove(), new UltimateMove());
    }

    YellowJacket() {
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
        if (effect.getDamageToEnemy() >= 0.1 * enemy.getCurrentHP()) {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(effect.getDamageToEnemy() / enemy.getCurrentHP(),
                    0.2, 8) - 0.1) {
                return new AttackMove();
            }
        }
        effect = attack(enemy, new UltimateMove(), false);
        if (this.getAtk() >= 1.5) {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(this.getAtk(), 1.7, 3)) {
                if (enemy.getCurrentHP() - effect.getDamageToEnemy() / enemy.getMaxHP() > 0.3) {
                    return new UltimateMove();
                }
            }
        }
        effect = attack(enemy, new AttackMove(), false);
        if (enemy.getAtk() >= 1.5) {
            double random = Math.random();
            if (random <= PokemonUtility.logisticFunction(enemy.getAtk(), 2.1, 1.75)) {
                return new UnderminingMove();
            }
        }
        Effect enemyEffect = enemy.attack(this, new UltimateMove(), false);
        if (enemyEffect.getDamageToEnemy() >= 0.9 * this.getCurrentHP()) {
            if (this.getCurrentHP() < 2.0 / 5 * this.getMaxHP()) {
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
        return "file:src/gt.gif";
    }

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    @Override
    public String getWinPicture() {
        return "file:src/gt_win.gif";
    }

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    @Override
    public String getLosePicture() {
        return "file:src/gt_lose.gif";
    }
}
