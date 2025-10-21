---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# PlayBook User Guide

PlayBook (PB) is a **desktop app for semi-professional youth football coaches to manage their players' contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). 

--------------------------------------------------------------------------------------------------------------------

#### Table of Contents

<!-- * Table of Contents -->

- [PlayBook User Guide](#playbook-user-guide) 
    - [Introduction](#introduction)
        - [Who is PlayBook for?](#who-is-playbook-for)
        - [What you should know before using PlayBook](#what-you-should-know-before-using-playbook)
        - [Why choose PlayBook?](#why-choose-playbook)
    - [Quick start](#quick-start)
        - [Installation and setup](#installation-and-setup)
        - [Understanding the PlayBook GUI](#understanding-the-playbook-gui)
        - [Your First Commands](#your-first-commands)
    - [Features](#features)
        - [Viewing help: `help`](#viewing-help-help)
        - [Adding a team: `addteam`](#adding-a-team-addteam)
        - [Adding a player: `add`](#adding-a-player-add)
        - [Assign player to team: `assignteam`](#assign-player-to-team-assignteam)
        - [Assign injury to player: `assigninjury`](#assign-injury-to-player-assigninjury)
        - [Unassign injury from player: `unassigninjury`](#unassign-injury-from-player-unassigninjury)
        - [Creating a new position: `newposition`](#creating-a-new-position-newposition)
        - [Assigning a position to player: `assignposition`](#assigning-a-position-to-player-assignposition)
        - [Assigning player as captain: `makecaptain`](#assigning-player-as-captain-makecaptain)
        - [Unassigning player as captain: `stripcaptain`](#unassigning-player-as-captain-stripcaptain)
        - [Listing all players: `list`](#listing-all-players-list)
        - [Listing all teams: `listteam`](#listing-all-teams-listteam)
        - [Listing all positions: `listposition`](#listing-all-positions-listposition)
        - [Listing all injured players: `listinjured`](#listing-all-injured-players-listinjured)
        - [Listing all captains: `listcaptains`](#listing-all-captains-listcaptains)
        - [Editing a player: `edit`](#editing-a-player-edit)
        - [Locating players by name: `find`](#locating-players-by-name-find)
        - [Filtering players by team, injury and/or position: `filter`](#filtering-players-by-team-injury-andor-position-filter)
        - [Deleting a player: `delete`](#deleting-a-player-delete)
        - [Deleting a team: `deleteteam`](#deleting-a-team-deleteteam)
        - [Deleting a position: `deleteposition`](#deleting-a-position-deleteposition)
        - [Clearing all entries: `clear`](#clearing-all-entries-clear)
        - [Exiting the program: `exit`](#exiting-the-program-exit)
        - [Saving the data](#saving-the-data)
        - [Editing the data file](#editing-the-data-file)
        - [Generating sample data](#generating-sample-data)
        - [Archiving data files `[coming in v2.0]`](#archiving-data-files-coming-in-v20)
    - [FAQ](#faq)
    - [Known issues](#known-issues)
    - [Command summary](#command-summary)

<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Introduction

#### Who is PlayBook for?

PlayBook is designed specifically for **semi-professional youth football coaches** who:
* Manage multiple teams (e.g., U16, U18, U21 squads)
* Need to track 20-50+ players across different age groups
* Prefer keyboard-based workflows for faster data entry
* Are comfortable with basic command-line operations
* Need quick access to player availability, injuries, and team assignments

#### What you should know before using PlayBook

**Technical Requirements:**
* Basic familiarity with command-line interfaces (typing commands rather than clicking buttons)
* Basic ability to navigate folders and run programs from a terminal
* Java 17 installed on your computer

**No prior coding experience needed** - all commands are simple, English-based instructions like `add`, `delete`, and `list`.

#### Why choose PlayBook?

Unlike traditional contact management apps, PlayBook is **optimized for football team management**:
* **Speed**: Type commands faster than clicking through multiple menus
* **Team-focused**: Built-in support for teams, positions, injuries, and captaincy
* **Flexible filtering**: Quickly find available players by position, team, or injury status
* **Always accessible**: Works offline, with instant local storage

--------------------------------------------------------------------------------------------------------------------

## Quick start

#### Installation and setup

1. Ensure you have Java `17` or above installed in your Computer.

   <box type="tip" seamless>
   
   **Quick help: Checking your Java installation**

   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
    
   **Windows users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html).
    
   **Linux users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationLinux.html).

   You can check your Java version by running the command `java --version` in your command terminal.
   </box>

2. Download the latest `.jar` file from [here](https://github.com/AY2526S1-CS2103T-T13-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your PlayBook.

4. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar playbook.jar` command to run the application.<br>

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

   ![Ui](images/Ui.png)

#### Understanding the PlayBook GUI

The PlayBook window consists of:
* **Command Box** (top): Where you type your commands
* **Result Display**: Shows feedback messages after each command
* **Player List Panel**: Displays all players matching your current view
* **Player Card**: Shows individual player details including name, team, position, injury status, phone, email, address, and tags

#### Your First Commands

Type commands in the command box and press Enter to execute them. Here's a quick tutorial:

1. **Start fresh** - Type `clear` and press Enter to remove all sample data.
   
   <box type="info" seamless>

   **Expected output:** "Address book has been cleared!" and the player list becomes empty.
   </box>

2. **Create your first team** - Type `addteam tm/U16` and press Enter.

   <box type="info" seamless>

   **Expected output:** "New team added: U16"
   </box>

3. **Add your first player** - Type:
   ```
   add pl/Alex Tan p/87654321 e/alex@example.com a/123 Main St tm/U16
   ```

   <box type="info" seamless>

   **Expected output:** "New person added: Alex Tan; Phone: 87654321; Email: alex@example.com; Address: 123 Main St; Team: U16; Injury: FIT; Position: ; Tags: "
   
   You should now see Alex Tan's player card in the player list.
   </box>

4. **View all players** - Type `list` to see all players in your PlayBook.

<box type="tip" seamless>

**More example commands to try:**

* `addteam tm/U18`: Add another team named `U18` to the PlayBook.

* `add pl/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 tm/U16` : Add another player to the U16 team.

* `newposition ps/LW` : Create a new position named `LW` (Left Wing) in the PlayBook.

* `assignposition pl/Alex Tan ps/LW` : Assign the position `LW` to `Alex Tan` (create the position first using `newposition`).

* `assigninjury pl/Alex Tan i/ACL` : Mark `Alex Tan` as injured with an ACL injury.

* `filter i/FIT` : View only players who are fit (not injured).

* `delete pl/John Doe` : Delete `John Doe` from the PlayBook.

* `help` : Open the help window.

* `exit` : Exit the app.

</box>

Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add pl/PLAYER_NAME`, `PLAYER_NAME` is a parameter which can be used as `add pl/John Doe`.

* Items in square brackets are optional.<br>
  - e.g `pl/PLAYER_NAME [t/TAG]` can be used as `pl/John Doe t/friend` or as `pl/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `pl/PLAYER_NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER pl/PLAYER_NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>

### Viewing help: `help`

Shows a message explaining how to access the help page.

Format: `help`

![help message](images/helpMessage.png)


### Adding a team: `addteam`

Adds a team to the PlayBook.

Format: `addteam tm/TEAM_NAME`

**Requirements:**
* `TEAM_NAME` must not be the same as an existing team in the PlayBook.
* `TEAM_NAME` should contain only alphanumeric characters, with no spaces.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.
* `TEAM_NAME` should not be blank.

<box type="warning" seamless>

**Warning:** Team names cannot contain spaces. Use naming conventions like `U16`, `U18`, or `Reserves` instead of `U 16` or `U 18`.
</box>

**Examples:**
* `addteam tm/U16` - Creates a team for under-16 players
* `addteam tm/U18` - Creates a team for under-18 players
* `addteam tm/Reserves` - Creates a reserves team

<box type="info" seamless>

**Expected output:** "New team added: U16" (or the team name you specified)

![add team message](images/addTeamResult.png)
</box>

### Adding a player: `add`

Adds a player to the PlayBook.

Format: `add pl/PLAYER_NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS tm/TEAM_NAME [t/TAG]…​`

**Requirements:**
* `PLAYER_NAME` should contain alphanumeric characters and spaces only.
* `PLAYER_NAME` should not be blank.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` must not be the same as an existing player in the PlayBook.
* `PHONE_NUMBER` should only contain numbers.
* `PHONE_NUMBER` should be at least 3 digits long.
* `PHONE_NUMBER` should not be blank.
* `EMAIL` should not be blank.
* `ADDRESS` should not be blank.
* `TEAM_NAME` must be an existing team in the PlayBook. Use the `addteam` command to add a team first.
* `TEAM_NAME` should contain only alphanumeric characters, with no spaces.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.
* `TEAM_NAME` should not be blank.
* `TAG` should contain alphanumeric characters only.

<box type="warning" seamless>

**Warning:** You must create the team first using `addteam` before adding players to it. If the team doesn't exist, you'll get an error message.
</box>

<box type="tip" seamless>

**Tip:** A player can have any number of tags (including 0). Use tags to mark players as "scholarship", "youth", "newcomer", etc.
</box>

**Examples:**
* `add pl/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 tm/U16`
* `add pl/Betsy Crowe p/1234567 e/betsycrowe@example.com a/Newgate Prison tm/U16 t/friend t/scholarship`

<box type="info" seamless>

**Expected output:** "New person added: John Doe; Phone: 98765432; Email: johnd@example.com; Address: John street, block 123, #01-01; Team: U16; Injury: FIT; Position: ; Tags: "

The player will appear in the player list panel with a "FIT" injury status and no position assigned by default.

![add message](images/addPlayerResult.png)
</box>

### Assign player to team: `assignteam`

Assigns an existing player to another existing team.

Format: `assignteam pl/PLAYER_NAME tm/TEAM_NAME`

**Requirements:**
* `TEAM_NAME` must be an existing team in the PlayBook. Use the `addteam` command to add a team first.
* `TEAM_NAME` should contain only alphanumeric characters, with no spaces.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.
* `TEAM_NAME` should not be blank.
* `PLAYER_NAME` must be the same as an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* `PLAYER_NAME` must not already be assigned to `TEAM_NAME`.

<box type="tip" seamless>

**Tip:** Use this command when promoting players between age groups (e.g., moving a player from U16 to U18) or reassigning players to different squads.
</box>

**Examples:**
* `assignteam pl/John Doe tm/U16` - Moves John Doe to the U16 team
* `assignteam pl/Betsy Crowe tm/U18` - Moves Betsy Crowe to the U18 team

<box type="info" seamless>

**Expected output:** "Assigned John Doe to team: U16"

The player's team will be immediately updated in their player card.

![assign team message](images/assignTeamResult.png)
</box>

### Assign injury to player: `assigninjury`

Assigns an injury status to an existing player.

Format: `assigninjury pl/PLAYER_NAME i/INJURY`

**Requirements:**
* `PLAYER_NAME` must be the same as an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* `INJURY` is case-insensitive, e.g. `acl` is the same as `ACL`.
* `INJURY` should not be blank.
* `INJURY` should contain alphanumeric characters and spaces only.

<box type="warning" seamless>

**Warning:** The keyword `FIT` (in any letter case) is not allowed as a valid injury. Use `unassigninjury` instead to restore the player's injury status to `FIT`.
</box>

<box type="tip" seamless>

**Tips:**
* Players can have multiple concurrent injuries. Simply use `assigninjury` multiple times with different injury names.
* Use specific injury names (e.g., "ACL", "hamstring strain", "ankle sprain") for better tracking.
* After assigning an injury, use `listinjured` to see all injured players at a glance.
</box>

**Examples:**
* `assigninjury pl/John Doe i/ACL` - Marks John Doe with an ACL injury
* `assigninjury pl/Musiala i/fibula fracture` - Marks Musiala with a fibula fracture
* `assigninjury pl/John Doe i/hamstring strain` - Adds a second injury to John Doe

<box type="info" seamless>

**Expected output:** "Assigned injury ACL to John Doe"

The player's injury status will be updated immediately in their player card, replacing "FIT" with the injury name.

![assign injury message](images/assignInjuryResult.png)
</box>

### Unassign injury from player: `unassigninjury`

Removes an injury status currently assigned to an existing player. 

Format: `unassigninjury pl/PLAYER_NAME i/INJURY`

**Requirements:**
* `PLAYER_NAME` must be the same as an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* `INJURY` is case-insensitive, e.g. `acl` is the same as `ACL`.
* `INJURY` should not be blank.
* `INJURY` should contain alphanumeric characters and spaces only.
* `INJURY` must match an injury that is already assigned to the specified player.
* The player must not already have the default `FIT` status.

<box type="tip" seamless>

**Tips:**
* If the player has multiple injuries, removing one injury will keep the others. Only when all injuries are removed will the status return to `FIT`.
* The injury name must match exactly (case-insensitive) with what was assigned.
* Use `listinjured` to check current injury statuses before unassigning.
</box>

**Examples:**
* `unassigninjury pl/John Doe i/ACL` - Removes the ACL injury from John Doe
* `unassigninjury pl/Musiala i/fibula fracture` - Removes the fibula fracture from Musiala

<box type="info" seamless>

**Expected output:** "Removed injury ACL from John Doe"

If this was the player's only injury, their status will automatically return to "FIT".

![unassign injury message](images/unassignInjuryResult.png)
</box>

### Creating a new position: `newposition`

Creates a new position in the PlayBook.

Format: `newposition ps/POSITION_NAME`

**Requirements:**
* `POSITION_NAME` must not be the same as an existing position in the PlayBook.
* `POSITION_NAME` should contain only alphanumeric characters, with no spaces.
* `POSITION_NAME` is case-insensitive, e.g. `fw` is the same as `FW`.
* `POSITION_NAME` should not be blank.

<box type="tip" seamless>

**Tip:** Create standard football positions like GK (Goalkeeper), CB (Center Back), LW (Left Wing), ST (Striker), etc. for easier team management.
</box>

**Examples:**
* `newposition ps/LW` - Creates Left Wing position
* `newposition ps/ST` - Creates Striker position
* `newposition ps/GK` - Creates Goalkeeper position

<box type="info" seamless>

**Expected output:** "Position RWB has been created successfully!" (or the position name you specified)

![new position message](images/newPositionResult.png)
</box>

### Assigning a position to player: `assignposition`

Assigns an existing position to an existing player in the PlayBook.

Format: `assignposition pl/PLAYER_NAME ps/POSITION_NAME`

**Requirements:**
* `PLAYER_NAME` must be an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* `POSITION_NAME` must be an existing position in the PlayBook. Use the `newposition` command to add a position first.
* `POSITION_NAME` should contain only alphanumeric characters, with no spaces.
* `POSITION_NAME` is case-insensitive, e.g. `fw` is the same as `FW`.
* `POSITION_NAME` should not be blank.
* The player must not already be assigned to the same position.

<box type="warning" seamless>

**Warning:** You must create the position first using `newposition` before assigning it to players. If the position doesn't exist, you'll get an error message.
</box>

<box type="tip" seamless>

**Tip:** Players can be assigned multiple positions. Use this feature to track versatile players who can play in different roles.
</box>

**Examples:**
* `assignposition pl/John Doe ps/LW` - Assigns Left Wing position to John Doe
* `assignposition pl/Musiala ps/ST` - Assigns Striker position to Musiala

<box type="info" seamless>

**Expected output:** "John Doe has been successfully assigned position GK!"

The position will be immediately visible in the player's card.

![assign position message](images/assignPositionResult.png)
</box>

### Assigning player as captain: `makecaptain`

Assigns an existing player in the PlayBook to be captain.

Format: `makecaptain pl/PLAYER_NAME`

**Requirements:**
* `PLAYER_NAME` must be an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* The player must not already be an assigned captain.

<box type="tip" seamless>

**Tip:** You can have multiple captains for different teams. Use `listcaptains` to see all current team captains.
</box>

**Examples:**
* `makecaptain pl/John Doe` - Makes John Doe a captain
* `makecaptain pl/Sergio Ramos` - Makes Sergio Ramos a captain

<box type="info" seamless>

**Expected output:** "Jovan Doe is now a captain of ClementiRovers"

A captain badge or indicator will appear on the player's card.

![make captain message](images/makeCaptainResult.png)
</box>

### Unassigning player as captain: `stripcaptain`

Removes captain status from an existing player in the PlayBook.

Format: `stripcaptain pl/PLAYER_NAME`

**Requirements:**
* `PLAYER_NAME` must be an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* The player must already be an assigned captain.

**Examples:**
* `stripcaptain pl/John Doe` - Removes captain status from John Doe
* `stripcaptain pl/Sergio Ramos` - Removes captain status from Sergio Ramos

<box type="info" seamless>

**Expected output:** "Jovan Doe is no longer team captain"

The captain badge/indicator will be removed from the player's card.

![strip captain message](images/stripCaptainResult.png)
</box>

### Listing all players: `list`

Shows a list of all players in the PlayBook.

Format: `list`

<box type="tip" seamless>

**Tip:** Use this command to reset your view after using filters or searches. It displays all players regardless of team, position, or injury status.
</box>

<box type="info" seamless>

**Expected output:** All players in your PlayBook will be displayed in the player list panel.

![list message](images/listResult.png)
</box>

### Listing all teams: `listteam`

Shows a list of all teams in the PlayBook.

Format: `listteam`

<box type="tip" seamless>

**Tip:** Use this to quickly see all teams you've created. Helpful for verifying team names before adding new players.
</box>

<box type="info" seamless>

**Expected output:** A list of all team names will be displayed in the result box (e.g., "U16, U18, Reserves").

![list team message](images/listTeamResult.png)
</box>

### Listing all positions: `listposition`

Shows a list of all positions in the PlayBook.

Format: `listposition`

<box type="tip" seamless>

**Tip:** Use this to check which positions you've already created before assigning positions to players.
</box>

<box type="info" seamless>

**Expected output:** A list of all position names will be displayed (e.g., "LW, ST, GK, CB").

![list position message](images/listPositionResult.png)
</box>

### Listing all injured players: `listinjured`

Shows a list of all injured players in the PlayBook.

Format: `listinjured`

<box type="tip" seamless>

**Tips:**
* Use this command before match day to quickly see who's unavailable.
* Combine with team information in the player cards to see which teams are affected by injuries.
* This shows all players with any injury status (not "FIT").
</box>

<box type="info" seamless>

**Expected output:** Only players with injuries (non-FIT status) will be displayed in the player list panel. Their injury details will be visible on their player cards.

![list injured message](images/listInjuredResult.png)
</box>

### Listing all captains: `filtercaptains`

Shows a list of all captains in the PlayBook.

Format: `filtercaptains`

<box type="tip" seamless>

**Tip:** Useful for quickly identifying team leaders across all your squads.
</box>

<box type="info" seamless>

**Expected output:** Only players designated as captains will be displayed in the player list panel.

![filter captains message](images/filterCaptainsResult.png)
</box>

### Editing a player: `edit`

Edits an existing player in the PlayBook. Existing values will be updated to the input values.

Format: `edit pl/PLAYER_NAME [n/NEW_PLAYER_NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [tm/TEAM_NAME] [t/TAG]…​`

**Requirements:**
* At least one of the optional fields must be provided.
* `PLAYER_NAME` must be the same as an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* `NEW_PLAYER_NAME` should contain alphanumeric characters and spaces only.
* `NEW_PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `NEW_PLAYER_NAME` must not be the same as an existing player in the PlayBook.
* `PHONE_NUMBER` should only contain numbers.
* `PHONE_NUMBER` should be at least 3 digits long.
* `TEAM_NAME` must be an existing team in the PlayBook. Use the `addteam` command to add a team first.
* `TEAM_NAME` should contain only alphanumeric characters, with no spaces.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.
* `TEAM_NAME` should not be blank.
* `TAG` should contain alphanumeric characters only.

<box type="warning" seamless>

**Warning:** When editing tags, all existing tags will be replaced with the new ones. If you want to keep existing tags, you must include them in the edit command.
</box>

<box type="tip" seamless>

**Tips:**
* You can edit multiple fields at once (e.g., both phone and email).
* To remove all tags, use `t/` without any tag names after it.
* You cannot edit injury status or position through this command - use dedicated commands instead.
</box>

**Examples:**
* `edit pl/John Doe p/91234567 e/johndoe@example.com` - Updates phone and email
* `edit pl/John Doe n/Betsy Crower t/` - Renames player and removes all tags
* `edit pl/John Doe tm/U18 t/captain t/veteran` - Moves to U18 team and adds tags

<box type="info" seamless>

**Expected output:** "Edited Player: John Doe; Phone: 91234567; Email: johndoe@example.com; Address: John street, block 123, #01-01; Team: U16; Injuries: [FIT]; Tags: "

The player card will immediately reflect all changes.

![edit message](images/editResult.png)
</box>

### Locating players by name: `find`

Finds players whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

**Requirements:**
* `PLAYER_NAME` must be the same as an existing player in the PlayBook.
* `PLAYER_NAME` is case-insensitive, e.g. `john doe` is the same as `John Doe`.
* `PLAYER_NAME` should not be blank.
* `PLAYER_NAME` will only match full words e.g. `Han` will not match `Hans`.
* Players matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.

<box type="tip" seamless>

**Tips:**
* Use `find` when you remember part of a player's name but not the full name.
* To return to viewing all players after a search, use the `list` command.
* For more advanced filtering by team, position, or injury, use the `filter` command instead.
</box>

**Examples:**
* `find John` - Returns players like "John Doe" and "John Smith"
* `find alex david` - Returns "Alex Yeoh" and "David Li" (matches either keyword)

<box type="info" seamless>

**Expected output:** "X persons listed!" where X is the number of matching players. The player list panel will show only the matching players.

![result for 'find Doe'](images/findResult.png)
</box>

### Filtering players by team, injury and/or position: `filter`

Filter players by team, injury and/or position.

Format: `filter [tm/TEAM_NAME] [i/INJURY] [ps/POSITION_NAME]`

**Requirements:**
* At least one of the optional fields must be provided.
* `TEAM_NAME` must be an existing team in the PlayBook. Use the `addteam` command to add a team first.
* `TEAM_NAME` should contain only alphanumeric characters, with no spaces.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.
* `TEAM_NAME` should not be blank.
* `INJURY` is case-insensitive, e.g. `acl` is the same as `ACL`.
* `INJURY` should not be blank.
* `INJURY` should contain alphanumeric characters and spaces only.
* `INJURY` must match an injury that is already assigned to the specified player.
* `INJURY` will only match full words e.g. `ACL` will not match `ACLS`.
* `INJURY` matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Leg Arm` will return `Leg Broken`, `Arm Fractured`.
* `POSITION_NAME` must be an existing position in the PlayBook. Use the `newposition` command to add a position first.
* `POSITION_NAME` should contain only alphanumeric characters, with no spaces.
* `POSITION_NAME` is case-insensitive, e.g. `fw` is the same as `FW`.
* `POSITION_NAME` should not be blank.

<box type="tip" seamless>

**Tips:**
* **Pre-match planning**: Use `filter tm/U16 i/FIT` to see all available U16 players.
* **Position planning**: Use `filter ps/ST i/FIT` to find all fit strikers across teams.
* **Injury tracking**: Use `filter tm/U18 i/ACL` to see all U18 players with ACL injuries.
* Combine multiple filters for precise results. All conditions must be met (AND logic).
</box>

**Examples:**
* `filter tm/U16 ps/FW` - Shows U16 players who play Forward
* `filter ps/FW tm/U17 i/FIT` - Shows fit Forwards from U17 team
* `filter i/Leg Broken ps/MF` - Shows Midfielders with leg broken injury
* `filter tm/Chelsea` - Shows all Chelsea team players
* `filter tm/Manchester i/Leg Arm` - Shows all Manchester team players with an injuries with the words Leg or Arm in them

<box type="info" seamless>

**Expected output:** "X persons listed!" where X is the number of players matching all filter criteria. Only matching players will be displayed.

![result for filter](images/filterResult.png)
</box>

### Deleting a player: `delete`

Deletes the specified player from the PlayBook.

Format: `delete pl/PLAYER_NAME`

**Requirements:**
* `PLAYER_NAME` is case-insensitive, e.g `hans` will match `Hans`.
* The player to be deleted must exist in the PlayBook.

<box type="warning" seamless>

**Warning:** This action cannot be undone! The player and all their information (team, position, injuries, tags) will be permanently removed. Consider making a backup of your data file before bulk deletions.
</box>

<box type="info" seamless>

**Note:** The command can only delete one player at a time.
</box>

**Examples:**
* `delete pl/John Doe` - Permanently deletes John Doe from PlayBook
* `delete pl/Betsy Crowe` - Permanently deletes Betsy Crowe from PlayBook

<box type="info" seamless>

**Expected output:** "Deleted Person: John Doe; Phone: 98765432; Email: johnd@example.com; Address: John street, block 123, #01-01; Team: U16; Injury: FIT; Position: LW; Tags: "

The player card will immediately disappear from the player list.

![delete player message](images/deletePlayerResult.png)
</box>

### Deleting a team: `deleteteam`

Deletes the specified team from the PlayBook.

Format: `deleteteam tm/TEAM_NAME`

**Requirements:**
* `TEAM_NAME` must be an existing team in the PlayBook.
* `TEAM_NAME` should contain only alphanumeric characters, with no spaces.
* `TEAM_NAME` is case-insensitive, e.g. `u16` is the same as `U16`.
* `TEAM_NAME` should not be blank.
* The team to be deleted must exist in the PlayBook.

<box type="warning" seamless>

**Warning:** You can only delete a team if there are no players assigned to it. Please remove all players from the team before attempting to delete it.
</box>

<box type="info" seamless>

**Note:** The command can only delete one team at a time.
</box>

**Examples:**
* `deleteteam tm/U16` - Deletes the U16 team from PlayBook
* `deleteteam tm/Reserves` - Deletes the Reserves team from PlayBook

<box type="info" seamless>

**Expected output:** "Team U100 has been deleted successfully!"

All players previously assigned to this team will have their team assignment removed.

![delte team message](images/deleteTeamResult.png)
</box>

### Deleting a position: `deleteposition`

Deletes the specified position from the PlayBook.

Format: `deleteposition ps/POSITION_NAME`

**Requirements:**
* `POSITION_NAME` must be an existing position in the PlayBook.
* `POSITION_NAME` should contain only alphanumeric characters, with no spaces.
* `POSITION_NAME` is case-insensitive, e.g. `fw` is the same as `FW`.
* `POSITION_NAME` should not be blank.
* The position to be deleted must exist in the PlayBook.

<box type="warning" seamless>

**Warning:** Deleting a position will remove it from all players who were assigned to that position. Make sure this is intentional before proceeding.
</box>

<box type="info" seamless>

**Note:** The command can only delete one position at a time.
</box>

**Examples:**
* `deleteposition ps/LW` - Deletes Left Wing position from PlayBook
* `deleteposition ps/ST` - Deletes Striker position from PlayBook

<box type="info" seamless>

**Expected output:** "Position LWB has been deleted successfully!"

All players previously assigned to this position will have it removed from their cards.

![delete position message](images/deletePositionResult.png)
</box>

### Clearing all entries: `clear`

Clears all entries from the PlayBook.

Format: `clear`

<box type="warning" seamless>

**WARNING: IRREVERSIBLE ACTION!**

This command will permanently delete:
* All players and their information
* All teams
* All positions
* All assignments (injuries, captaincy, etc.)

**There is no undo.** Make sure you have a backup of your data file (located under `/data/addressbook.json`) before using this command!
</box>

<box type="tip" seamless>

**Tip:** Use this command at the start of a new season to begin with a clean slate, but always backup your previous season's data first.

</box>

<box type="info" seamless>

**Expected output:** "Address book has been cleared!"

The player list panel will be completely empty.
</box>

### Exiting the program: `exit`

Exits the program.

Format: `exit`

<box type="info" seamless>

**Expected output:** The PlayBook application window will close immediately. Your data is safely stored in `addressbook.json`.
</box>

### Saving the data

PlayBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

<box type="tip" seamless>

**Tip:** Find your data at `/data/addressbook.json`

</box>

### Editing the data file

PlayBook data are saved automatically as a JSON file `/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
* If your changes to the data file make its format invalid, PlayBook will discard all data and start with an empty data file at the next run.
* Always make a backup of the file before editing it manually.
* Certain edits can cause PlayBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range).
* Only edit the data file if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q: How do I transfer my data to another Computer?**<br>
**A:** Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous PlayBook home folder.

**Q: Can I manage multiple teams at the same time?**<br>
**A:** Yes! PlayBook is designed for coaches managing multiple teams. Simply create different teams using `addteam` (e.g., `addteam tm/U16`, `addteam tm/U18`) and assign players accordingly.

**Q: What happens if I accidentally delete a player?**<br>
**A:** Unfortunately, there's no undo feature. However, since PlayBook automatically saves your data, you can manually restore from a backup of the `addressbook.json` file if you made one. We recommend making regular backups of your data file.

**Q: Can a player be in multiple teams?**<br>
**A:** No, each player can only be assigned to one team at a time. If you need to move a player to a different team, use the `assignteam` command.

**Q: How do I quickly find all available players for a match?**<br>
**A:** Use the `filter i/FIT` command to show only players with no injuries. You can combine this with team filtering: `filter tm/U16 i/FIT` to see all fit players in the U16 team.

**Q: Can I track multiple injuries for one player?**<br>
**A:** Yes! Players can have multiple concurrent injuries. Use `assigninjury` multiple times for different injuries, and use `unassigninjury` to remove specific injuries when they recover.

**Q: What if I make a typo in a player's name?**<br>
**A:** Use the `edit` command to correct it. For example: `edit pl/Jon Doe n/John Doe` will rename "Jon Doe" to "John Doe".

**Q: Why can't I delete a team?**<br>
**A:** Teams can only be deleted if they have no players assigned. First reassign all players to other teams using `assignteam`, or delete the players, then delete the team using `deleteteam`.

**Q: Does PlayBook work offline?**<br>
**A:** Yes! PlayBook works completely offline and stores all data locally on your computer.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action                          | Format, Examples                                                                                                                                                                                                    |
|---------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Add Player**                  | `add pl/PLAYER_NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS tm/TEAM_NAME [i/INJURY] [t/TAG]…​` <br> e.g., `add pl/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 tm/u16 i/ACL t/friend t/colleague` |
| **Add Team**                    | `addteam tm/TEAM_NAME` <br> e.g., `addteam tm/u16`                                                                                                                                                                  |
| **Assign Player to Team**       | `assignteam pl/PLAYER_NAME tm/TEAM_NAME` <br> e.g., `assignteam pl/John Doe tm/u16`                                                                                                                                 |
| **Assign Injury to Player**     | `assigninjury pl/PLAYER_NAME i/INJURY` <br> e.g., `assigninjury pl/John Doe i/ACL`                                                                                                                                  |
| **Unassign Injury from Player** | `unassigninjury pl/PLAYER_NAME` <br> e.g., `unassigninjury pl/John Doe`                                                                                                                                             |
| **Create New Position**         | `newposition ps/POSITION_NAME` <br> e.g., `newposition ps/LW`                                                                                                                                                       |
| **Assign Position to Player**   | `assignposition pl/PLAYER_NAME ps/POSITION_NAME` <br> e.g., `assignposition pl/John Doe ps/LW`                                                                                                                      |
| **Clear**                       | `clear`                                                                                                                                                                                                             |
| **Delete Player**               | `delete pl/PLAYER_NAME`<br> e.g., `delete pl/James Ho`                                                                                                                                                              |
| **Delete Team**                 | `deleteteam tm/TEAM_NAME`<br> e.g., `deleteteam tm/u16`                                                                                                                                                             |
| **Delete Position**             | `deleteposition ps/POSITION_NAME`<br> e.g., `deleteposition ps/LW`                                                                                                                                                  |
| **Edit**                        | `edit pl/PLAYER_NAME [n/NEW_PLAYER_NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [tm/TEAM_NAME] [i/INJURY] [t/TAG]…​`<br> e.g.,`edit pl/John Doe n/James Lee e/jameslee@example.com`                                        |
| **Find**                        | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                          |
| **Filter Players**              | `filter [tm/TEAM_NAME] [i/INJURY] [ps/POSITION]`<br> e.g.,`filter tm/U16 i/ACL ps/FW`                                                                                                                               |
| **List**                        | `list`                                                                                                                                                                                                              |
| **Make Captain**                | `makecaptain pl/PLAYER_NAME` <br> e.g., `makecaptain pl/John Doe`                                                                                                                                                   |
| **Strip Captain**               | `stripcaptain pl/PLAYER_NAME` <br> e.g., `stripcaptain pl/John Doe`                                                                                                                                                 |
| **Filter Captains**             | `filtercaptains`                                                                                                                                                                                                    |
| **List Teams**                  | `listteam`                                                                                                                                                                                                          |
| **List Positions**              | `listposition`                                                                                                                                                                                                      |
| **List Injured Players**        | `listinjured`                                                                                                                                                                                                       |
| **Help**                        | `help`                                                                                                                                                                                                              |
