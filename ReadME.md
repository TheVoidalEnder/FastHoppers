# FasterHoppers

A straightforward plugin for Paper/Spigot servers that lets you speed up all hoppers globally. It includes custom configuration menus (GUIs), text inputs, and sounds so you don't have to deal with raw configuration files.

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
1. Download the `.jar` below.
2. Drop it into your server's `plugins` folder.
3. Restart your server.
4. (Optional) Use LuckPerms to assign `fasthopper.gui` or `fasthopper.admin` to your staff ranks.
