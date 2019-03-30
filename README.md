# ActionBar-Coords | Neat plugin that shows toggleable coords on your action bar
![Image](https://i.gyazo.com/9bbfed1286f8f5eb3f37e49bf3bcb00a.png)
![Image](https://cdn.discordapp.com/attachments/558511258857635841/561452369683021834/97a965d6fa057f440dab8ab487342e0c.png)

## Support
If you need any help, please contact us on our support [Discord](https://discord.gg/mGgfyaS)

## Commands/Permissions
**/coords** - Opens the menu to enable/disable ActionBar Coords for the player - `actionbarcoods.base`

**/coords discord** - Links to our support Discord 

**/coords reload** - Reloads the configuration file - `actionbarcoords.reload`

## Any issues? Suggestions?
Feel free to contact me on [Discord](https://discord.gg/mGgfyaS) or leave an issue on GitHub.

## Configuration
```
Action-Bar {
    # Should ActionBar Coords be enabled by default? True or false
    enabled-by-default="false"
    # Set action bar message which will appear for the player. Available placeholders: {x}, {y}, {z}
    message="&aX: &6{x} &aY: &6{y} &aZ: &6{z}"
}
LuckPerms {
    # This is useful for LuckPerms network setups, but ''global'' will work fine.
    server-context=global
}
Pagination {
    padding="&8&m-"
}
Toggle-Menu {
    # Insert disabled message for {toggle} here!
    disabled="&cdisabled"
    # Insert enabled message for {toggle} here!
    enabled="&aenabled"
    # Insert hover message here!
    hover="&7ActionBar Coords are currently {toggle}"
    # Insert toggle menu message here
    message="&7Hello &b{playername}&7\n&7ActionBar Coords are currently {toggle}&7, click me to toggle!"
    # Insert toggle menu title here
    title="&6ActionBar Coords&8: {toggle}"
}
```


