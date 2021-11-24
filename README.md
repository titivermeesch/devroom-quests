# Devroom Quests

This plugin has been created for the Devroom team as proof of Java, Databases and Memory management knowledge.

## Specifications

Each quest need to have one of the following types:

- WALK
- BREAK
- PLACE
- COMMAND
- KILL_MOBS

A quest is a combination of a type, a threshold and a set of commands (e.g. Execute command x and command y when user
placed 200 blocks)

## Installation

1. Add your Mongo link or use the default one provided
2. Create a database called `devroom_quests`. If your Mongo client requires a default collection, you can call
   it `player_stats`.
3. Reload/restart your server

## Configuration

Here is an example of the configuration file

```yaml
quests:
  - quest_1:
      type: "BREAK"
      threshold: 5
      commands:
        - say YOU DID IT! You received 40 coins
        - eco give %player_name% 40
```

## Possible improvements

These are improvements that could be done if the requirements of the plugin changes:

- Multi collection support (only 1 is possible now because the plugin does not require more collections)

## Debugging

The plugin comes with a logger that can be enabled in the config.yml file. This will provide you with a set of logs
triggered at different places to understand if the issues comes from a configuration issue or from the plugin itself