/**
 * Move.
 *
 * @author Chunhao Zou
 * @version 1.0
 */
public class Move implements PokemonUtility {
    private String name;
    private int power;
    private String type;
    private String picture;
    private int pictureDisplayMode;

    /**
     * Constructor.
     *
     * @param name               Name. If unspecified, will be the same as type.
     * @param power              Power. This will increase as you use the move more and more.
     * @param type               Type.
     * @param picture            Picture.
     * @param pictureDisplayMode Denotes where the picture will be displayed. (On enemy? On me? Both?)
     */
    public Move(String name, int power, String type, String picture, int pictureDisplayMode) {
        this.name = name;
        this.power = power;
        this.type = type;
        this.picture = picture;
        this.pictureDisplayMode = pictureDisplayMode;
    }

    /**
     * Constructor.
     *
     * @param power              Power. This will increase as you use the move more and more.
     * @param type               Type.
     * @param picture            Picture.
     * @param pictureDisplayMode Denotes where the picture will be displayed. (On enemy? On me? Both?)
     */
    public Move(int power, String type, String picture, int pictureDisplayMode) {
        this(type, power, type, picture, pictureDisplayMode);
    }

    /**
     * Constructor.
     *
     * @param type               Type.
     * @param picture            Picture.
     * @param pictureDisplayMode Denotes where the picture will be displayed. (On enemy? On me? Both?)
     */
    public Move(String type, String picture, int pictureDisplayMode) {
        this(0, type, picture, pictureDisplayMode);
    }

    /**
     * Get the picture for a pokemon.
     *
     * @return address to the picture.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Set the picture for a pokemon.
     *
     * @param picture Picture.
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * Get the display mode for the move picture.
     *
     * @return A constant denoting a specific mode (DISPLAY_ME_ONLY, DISPLAY_ENEMY_ONLY, DISPLAY_BOTH).
     */
    public int getPictureDisplayMode() {
        return pictureDisplayMode;
    }

    /**
     * Setter.
     *
     * @param pictureDisplayMode A constant denoting a specific mode
     *                           (DISPLAY_ME_ONLY, DISPLAY_ENEMY_ONLY, DISPLAY_BOTH).
     */
    public void setPictureDisplayMode(int pictureDisplayMode) {
        this.pictureDisplayMode = pictureDisplayMode;
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
     * @return Power.
     */
    public int getPower() {
        return power;
    }

    /**
     * Increase power by 1. The more power a move has, the more powerful it is.
     */
    public void incPower() {
        power++;
    }

    /**
     * Getter.
     *
     * @return Type.
     */
    public String getType() {
        return type;
    }
}
