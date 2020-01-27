/**
 * An object of Effect contains the information of the effect of a move.
 * For example, how much damage an attack would inflict on an enemy, etc.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public class Effect implements PokemonUtility {
    private double damageToEnemy;
    private double damageToMe;
    private double atkMultiplierToMe;
    private double atkMultiplierToEnemy;
    private String info;

    /**
     * Constructor.
     *
     * @param damageToEnemy        Damage to Enemy.
     * @param damageToMe           Damage to Me.
     * @param atkMultiplierToMe    Denotes how much my atk will be multiplied after this move.
     * @param atkMultiplierToEnemy Denotes how much the enemy's atk will be multiplied after this move.
     * @param info                 Strings containing the info, e.g. Fatal attack, unlucky attack, etc.
     */
    public Effect(double damageToEnemy, double damageToMe, double atkMultiplierToMe,
                  double atkMultiplierToEnemy, String info) {
        this.damageToEnemy = damageToEnemy;
        this.damageToMe = damageToMe;
        this.atkMultiplierToMe = atkMultiplierToMe;
        this.atkMultiplierToEnemy = atkMultiplierToEnemy;
        this.info = info;
    }

    /**
     * Constructor.
     *
     * @param damageToEnemy        Damage to Enemy.
     * @param damageToMe           Damage to Me.
     * @param atkMultiplierToMe    Denotes how much my atk will be multiplied after this move.
     * @param atkMultiplierToEnemy Denotes how much the enemy's atk will be multiplied after this move.
     */
    public Effect(double damageToEnemy, double damageToMe, double atkMultiplierToMe, double atkMultiplierToEnemy) {
        this(damageToEnemy, damageToMe, atkMultiplierToMe, atkMultiplierToEnemy, "");
    }

    /**
     * Constructor.
     *
     * @param damageToEnemy Damage to Enemy.
     * @param damageToMe    Damage to Me.
     */
    public Effect(double damageToEnemy, double damageToMe) {
        this(damageToEnemy, damageToMe, 1, 1, "");
    }

    /**
     * Getter.
     *
     * @return Damage to Enemy.
     */
    public double getDamageToEnemy() {
        return damageToEnemy;
    }

    /**
     * Setter.
     *
     * @param damageToEnemy Damage to Enemy.
     */
    public void setDamageToEnemy(double damageToEnemy) {
        this.damageToEnemy = damageToEnemy;
    }

    /**
     * Getter.
     *
     * @return Damage to Me.
     */
    public double getDamageToMe() {
        return damageToMe;
    }

    /**
     * Setter.
     *
     * @param damageToMe Damage to Me.
     */
    public void setDamageToMe(double damageToMe) {
        this.damageToMe = damageToMe;
    }

    /**
     * Getter.
     *
     * @return Atk Multiplier to Me.
     */
    public double getAtkMultiplierToMe() {
        return atkMultiplierToMe;
    }

    /**
     * Setter.
     *
     * @param atkMultiplierToMe Atk Multiplier to Me.
     */
    public void setAtkMultiplierToMe(double atkMultiplierToMe) {
        this.atkMultiplierToMe = atkMultiplierToMe;
    }

    /**
     * Getter.
     *
     * @return Atk Multiplier to Enemy.
     */
    public double getAtkMultiplierToEnemy() {
        return atkMultiplierToEnemy;
    }

    /**
     * Setter.
     *
     * @param atkMultiplierToEnemy Atk Multiplier to Enemy.
     */
    public void setAtkMultiplierToEnemy(double atkMultiplierToEnemy) {
        this.atkMultiplierToEnemy = atkMultiplierToEnemy;
    }

    /**
     * Getter.
     *
     * @return Atk Multiplier to Me.
     */
    public String getInfo() {
        return info;
    }

    /**
     * Setter.
     *
     * @param info Info.
     */
    public void setInfo(String info) {
        this.info = info;
    }
}
