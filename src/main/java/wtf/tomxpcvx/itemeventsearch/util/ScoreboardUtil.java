package wtf.tomxpcvx.itemeventsearch.util;

import wtf.tomxpcvx.itemeventsearch.Constants;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class ScoreboardUtil {

    public static void initializeScoreboard(ItemEventPlayer iep) {
        ScoreboardUtil.create(iep);
        ScoreboardUtil.set(iep);
    }

    public static void create(ItemEventPlayer iep) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        String boardName = "E" + iep.trimPlayerName(13);

        Objective objective = board.registerNewObjective(boardName, "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Constants.msg.get("Scoreboard.Title"));

        Score map = objective.getScore(Constants.msg.get("Scoreboard.EventItemFind"));
        map.setScore(iep.getLocatedEventItemCount());
        Score cookies = objective.getScore(Constants.msg.get("Scoreboard.EventItemExist"));
        cookies.setScore(Constants.eventItemCount);

        iep.setScoreboard(board);
    }

    public static void set(ItemEventPlayer iep) {
        iep.getPlayer().setScoreboard(iep.getScoreboard());
    }

    public static void leave(ItemEventPlayer iep) {
        if (iep.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
            iep.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
            iep.setScoreboard(null);
        }
    }

    public static void update(ItemEventPlayer iep) {
        Scoreboard board = iep.getScoreboard();
        String BoardName = "E" + iep.trimPlayerName(13);

        Objective objective = board.getObjective(BoardName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(Constants.msg.get("Scoreboard.Title"));


        board.resetScores(Constants.msg.get("Scoreboard.EventItemFind"));
        Score s = objective.getScore(Constants.msg.get("Scoreboard.EventItemFind"));

        if (objective.getScore(Constants.msg.get("Scoreboard.EventItemExist")).getScore() != Constants.eventItemCount) {
            board.resetScores(Constants.msg.get("Scoreboard.EventItemExist"));
            Score hidden = objective.getScore(Constants.msg.get("Scoreboard.EventItemExist"));
            hidden.setScore(Constants.eventItemCount);
        }
        s.setScore(iep.getLocatedEventItemCount());
    }
}
