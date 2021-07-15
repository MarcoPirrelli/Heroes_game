/**
 * Allows an object to be notified when and achievement is obtained.
 */
public interface AchievementListener {
    /**
     * Method is called when an achievement is obtained.
     *
     * @param achievement String with the name of the achievement.
     */
    public abstract void achievementObtained(String achievement);
}
