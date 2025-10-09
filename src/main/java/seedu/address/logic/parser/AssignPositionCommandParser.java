package seedu.address.logic.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AssignPositionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignPositionCommandParser implements Parser<AssignPositionCommand> {
    private static final Pattern ARG_PATTERN =
            Pattern.compile("(?i).*\\bp/(?<player>[^\\s].*?)\\s+ps/(?<position>\\S+)\\s*");

    @Override
    public AssignPositionCommand parse(String args) throws ParseException {
        if (args == null || args.trim().isEmpty()) {
            throw new ParseException(AssignPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        Matcher m = ARG_PATTERN.matcher(args.trim());
        if (!m.matches()) {
            boolean hasP = args.contains("p/");
            boolean hasPs = args.contains("ps/");
            if (!hasP) {
                throw new ParseException(AssignPositionCommand.MESSAGE_MISSING_PLAYER_FLAG);
            }
            if (!hasPs) {
                throw new ParseException(AssignPositionCommand.MESSAGE_MISSING_POSITION_FLAG);
            }
            throw new ParseException(AssignPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        String player = m.group("player").trim();
        String position = m.group("position").trim();
        if (player.isEmpty()) {
            throw new ParseException(AssignPositionCommand.MESSAGE_INVALID_FORMAT);
        }
        return new AssignPositionCommand(player, position);
    }
}


