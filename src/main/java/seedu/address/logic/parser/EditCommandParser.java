package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException If the user input does not conform to the expected format.
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        checkEmptyArguments(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_PLAYER, PREFIX_NAME, PREFIX_PHONE,
                PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);

        checkCompulsoryPlayerPrefix(argMultimap);
        verifyNoDuplicatePrefixes(argMultimap);

        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        Name playerName = ParserUtil.parseName(argMultimap.getValue(PREFIX_PLAYER).get());

        parsePlayerFieldsForEdit(argMultimap, editPersonDescriptor);
        parseTagsForEdit(argMultimap.getAllValues(PREFIX_TAG)).ifPresent(editPersonDescriptor::setTags);

        validateFieldsEdited(editPersonDescriptor);
        checkEmptyPreamble(argMultimap);

        return new EditCommand(playerName, editPersonDescriptor);
    }

    private void checkEmptyArguments(String args) throws ParseException {
        if (args.trim().isEmpty()) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_EMPTY_COMMAND, EditCommand.COMMAND_WORD)));
        }
    }

    private void checkCompulsoryPlayerPrefix(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYER)) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_MISSING_PLAYER_PREFIX, EditCommand.COMMAND_WORD)));
        }
    }

    private void verifyNoDuplicatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_PLAYER, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                    PREFIX_ADDRESS);
        } catch (ParseException exception) {
            throw new ParseException(formatParseErrorMessage(exception.getMessage()));
        }
    }

    private void parsePlayerFieldsForEdit(ArgumentMultimap argMultimap, EditPersonDescriptor editPersonDescriptor)
            throws ParseException {
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editPersonDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editPersonDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editPersonDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editPersonDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
    }

    private void validateFieldsEdited(EditPersonDescriptor editPersonDescriptor) throws ParseException {
        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(formatParseErrorMessage(Messages.MESSAGE_NOT_EDITED));
        }
    }

    private void checkEmptyPreamble(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    formatParseErrorMessage(
                            String.format(Messages.MESSAGE_NON_EMPTY_PREAMBLE, EditCommand.COMMAND_WORD)));
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForEdit(Collection<String> tags) throws ParseException {
        requireNonNull(tags);

        try {
            if (tags.isEmpty()) {
                return Optional.empty();
            }
            Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
            return Optional.of(ParserUtil.parseTags(tagSet));
        } catch (ParseException exception) {
            throw new ParseException(exception.getMessage());
        }
    }

    private String formatParseErrorMessage(String message) {
        return String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, message + "\n" + EditCommand.MESSAGE_USAGE);
    }
}
