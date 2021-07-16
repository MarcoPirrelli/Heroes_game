/**
 * Allows an object to be notified when and achievement is obtained.
 */
public interface GameListener {
    /**
     * Method is called when an achievement is obtained.
     *
     * @param achievement String with the name of the achievement.
     */
    void achievementObtained(String achievement);

    /**
     * Method is called when the hero age and years of service increase.
     */
    void heroAged();
}
