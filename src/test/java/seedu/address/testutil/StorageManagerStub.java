package seedu.address.testutil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * A stub class for StorageManager to be used in tests.
 */
public class StorageManagerStub extends StorageManager {

    /**
     * Creates a {@code StorageManagerStub} with the given {@code AddressBook} and {@code UserPrefs}.
     */
    public StorageManagerStub() {
        super(new JsonAddressBookStorage(Path.of("nonexistent.json")),
              new JsonUserPrefsStorage(Path.of("nonexistent.json")));
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath)
            throws DataLoadingException {
        return Optional.of(new AddressBookBuilder().build());
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataLoadingException {
        return Optional.of(new UserPrefs());
    }


    @Override
    public void saveAddressBook(ReadOnlyAddressBook ab, Path filePath) throws IOException {
        // no-op
    }

    @Override
    public void saveUserPrefs(seedu.address.model.ReadOnlyUserPrefs userPrefs)
            throws IOException {
        // no-op
    }
}
