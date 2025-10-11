---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# PlayBook User Guide

PlayBook (PB) is a **desktop app for semi-professional youth football coaches to manage their players' contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, PB can get your contact management tasks done faster than traditional GUI apps.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T13-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar playbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.
   
   * `addteam n/u16`: Adds a team named `u16` to the Address Book.

   * `add n/John Doe p/98765432 e/johnd@example.com tm/u16 a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book (make sure to add the team `u16` first).

   * `delete pl/John Doe` : Deletes `John Doe` from the Address Book.

   * `clear` : Deletes all players' contacts and teams from the Address Book.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

### Adding a team: `addteam`

Adds a team to the address book.

Format: `addteam n/TEAM_NAME`

* `TEAM_NAME` must not be the same as an existing team in the address book.
* `TEAM_NAME` should contain alphanumeric characters and spaces only.
* `TEAM_NAME` should not be blank.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.

Examples:
* `addteam n/u16`

### Adding a player: `add`

Adds a player to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL tm/TEAM a/ADDRESS [t/TAG]…​`

* `TEAM` must be an existing team in the address book. Use the `addteam` command to add a team first.

<box type="tip" seamless>

**Tip:** A player can have any number of tags (including 0)
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com tm/u16 a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com tm/u16 a/Newgate Prison p/1234567 t/criminal`

### Assign player to team: `assignteam`

Assigns an existing player to another existing team.

Format: `assignteam pl/PLAYER_NAME tm/TEAM_NAME`

* `TEAM_NAME` must be the same as an existing team in the address book.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.
* `TEAM_NAME` should not be blank.
* `PLAYER_NAME` must be the same as an existing player in the address book.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* `PLAYER_NAME` must not already be assigned to `TEAM_NAME`.

Examples:
* `assignteam pl/John Doe tm/u16`
* `assignteam pl/Betsy Crowe tm/u16`

### Listing all players : `list`

Shows a list of all players in the address book.

Format: `list`

### Editing a player : `edit`

Edits an existing player in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the player at the specified `INDEX`. The index refers to the index number shown in the displayed player list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the player will be removed i.e adding of tags is not cumulative.
* You can remove all the player’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st player to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd player to be `Betsy Crower` and clears all existing tags.

### Locating players by name: `find`

Finds players whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`.
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`.
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`.
* Players matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a player : `delete`

Deletes the specified player from the address book.

Format: `delete pl/PLAYER_NAME`

* Deletes the player with the specified `PLAYER_NAME` from the address book.
* `PLAYER_NAME` is case-insensitive, e.g `hans` will match `Hans`.
* The player to be deleted must exist in the address book.
* The command can only delete one player at a time.

Examples:
* `delete pl/John Doe` deletes the player named `John Doe` from the address book.
* `delete pl/Betsy Crowe` deletes the player named `Betsy Crowe` from the address book.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                    | Format, Examples                                                                                                                                                                     |
|---------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Player**            | `add n/NAME p/PHONE_NUMBER e/EMAIL tm/TEAM a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com tm/u16 a/123, Clementi Rd, 1234665 t/friend t/colleague` |
| **Add Team**              | `addteam n/NAME` <br> e.g., `addteam n/u16`                                                                                                                                          |
| **Assign Player to Team** | `assignteam pl/PLAYER_NAME tm/TEAM_NAME` <br> e.g., `assignteam pl/John Doe tm/u16`                                                                                                  |
| **Clear**                 | `clear`                                                                                                                                                                              |
| **Delete Player**         | `delete pl/PLAYER`<br> e.g., `delete pl/James Ho`                                                                                                                                    |
| **Edit**                  | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                          |
| **Find**                  | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                           |
| **List**                  | `list`                                                                                                                                                                               |
| **Help**                  | `help`                                                                                                                                                                               |
