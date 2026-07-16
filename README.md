# FastHoppers

A lightweight Paper plugin to change global hopper speeds and transfer amounts. Features interactive clean GUIs, chat/command manual overrides, custom sound effects, and built-in permission nodes.

## Features

* **Global Controls:** Changes the speed and transfer amounts for every hopper on the server at once.
* **Separated GUIs:** Keeps settings clean by separating item amounts and tick speeds into their own menus.
* **Custom Chat Overrides:** Click the anvil in any GUI to type a custom number directly into chat, or use commands.
* **Bulk Transfer Limits:** Safely bundle and transfer up to **320 items at a time** (5 stacks) per tick.
* **Information Signs:** Every menu features an info sign in the bottom right corner with instructions and current settings.
* **Sound Effects:** Added clicking sounds, menu opening cues, and error tones for clear feedback.

## Commands & Usage

* `/hopperadmin` - Opens the main setup GUI dashboard. (Requires `fasthopper.gui`)
* `/fasthoppers amount <1-320>` - Manually overrides the item transfer amount. (Requires `fasthopper.admin`)
* `/fasthoppers ticks <1-200>` - Manually overrides the hopper cooldown ticks. (Requires `fasthopper.admin`)

## Permissions

* `fasthopper.gui` - Allows users to run `/hopperadmin` to view current speeds in read-only mode.
* `fasthopper.admin` - Allows users to change speeds, click menus, and type custom values into chat.
* `fasthopper.*` - Gives full access to both viewing and editing permissions.

## Installation

1. Download the `.jar` from the [releases](https://github.com/TheVoidalEnder/FastHoppers/releases).
2. Drop it into your server's `plugins` folder.
3. Restart your server.
4. (Optional) Use LuckPerms to assign `fasthopper.gui` or `fasthopper.admin` to your staff ranks.

## Requirements

* **Java 17+**
* **Paper 1.20.4+** (or compatible Spigot/Bukkit server)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
