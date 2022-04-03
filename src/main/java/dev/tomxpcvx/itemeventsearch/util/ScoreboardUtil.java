package dev.tomxpcvx.itemeventsearch.util;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;
import dev.tomxpcvx.itemeventsearch.domain.ItemEventPlayer;

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
        objective.setDisplayName(ItemEventSearchUtil.messages.get("Scoreboard.Title"));

        Score map = objective.getScore(ItemEventSearchUtil.messages.get("Scoreboard.EventItemFind"));
        map.setScore(iep.getLocatedEventItemCount());
        Score cookies = objective.getScore(ItemEventSearchUtil.messages.get("Scoreboard.EventItemExist"));
        cookies.setScore(ItemEventSearchUtil.eventItemCount);

        iep.setScoreboard(board);
    }

    public static void set(ItemEventPlayer iep) {
        iep.getPlayer().setScoreboard(iep.getScoreboard());
    }

    public static void leave(ItemEventPlayer iep) {
        if(iep.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR) != null) {
            iep.getPlayer().getScoreboard().getObjective(DisplaySlot.SIDEBAR).unregister();
            iep.setScoreboard(null);
        }
    }

    public static void update(ItemEventPlayer iep) {
        Scoreboard board = iep.getScoreboard();
        String BoardName = "E" + iep.trimPlayerName(13);

        Objective objective = board.getObjective(BoardName);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(ItemEventSearchUtil.messages.get("Scoreboard.Title"));


        board.resetScores(ItemEventSearchUtil.messages.get("Scoreboard.EventItemFind"));
        Score s = objective.getScore(ItemEventSearchUtil.messages.get("Scoreboard.EventItemFind"));

        if(objective.getScore(ItemEventSearchUtil.messages.get("Scoreboard.EventItemExist")).getScore() != ItemEventSearchUtil.eventItemCount) {
            board.resetScores(ItemEventSearchUtil.messages.get("Scoreboard.EventItemExist"));
            Score hidden = objective.getScore(ItemEventSearchUtil.messages.get("Scoreboard.EventItemExist"));
            hidden.setScore(ItemEventSearchUtil.eventItemCount);
        }
        s.setScore(iep.getLocatedEventItemCount());
    }
}
