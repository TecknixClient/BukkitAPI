# Tecknix Client Bukkit API
## This is a fully featured open source Bukkit API.

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

## Features

- Disable 1.8x click delay timer.
- Send a welcome message to the user.
- Get a list of players running Tecknix Client for any usage.
- Send notifications to players clients.
- Have server waypoints that can be added and removed.
- Easy to use API code with event based implementation.

## Installation:

Tecknix Bukkit API requires a [Bukkit Server](https://getbukkit.org/) running on a java 1.8+ vm to work.

```sh
1: Place TecknixAPI.jar into the "Plugins" folder
2: Run the server.
```

## Development:

For people who wish to customize or add to our API for their server.
```sh
1: Clone the repository.
2: Open the maven project in an IDE of choice.
3: Get programming!
```

## Code:

An overview of the code structure and usages.

- JavaDoc:
```
 /**
 * At the top of the documentation you will see an explanation of what the method does.
 *
 * @param (the parameter) This is something that is passed through the method and a breif explanation of what it does is given.
 */
```

- Notifications:
```java
//The notification needs to be declared.
private final TCNotification exampleNotification = new TCNotification(TCNotification.Type.INFO, "This is a notification!", 5);
//The player that the notification is being sent to needs to be defined.
final Player player = Bukkit.getPlayer("Frostei");
//Fire the exampleNotification#sendPacket(player); where you wish to send them the notification.
this.exampleNotification.sendPacket(player);
```

- Cps Cooldown:
```java
//Declare a player that will receive the packet.
final Player player = Bukkit.getPlayer("Frostei");
//Send the packet to the player.
TecknixAPI.getInstance().sendPacket(player, new TCPacketCpsCooldown(true);
//Note "true" disables the cooldown and "false" enables it!
```

- Waypoints:
```java
//In TecknixAPI.java there is a list of waypoints you can add a waypoint to this list with:
TecknixAPI.getInstance().getWaypoints().add(new TCWaypoint("name", "world", "server", x, y, z, red, green, blue);
//Or:
TecknixAPI.getInstance().getWaypoints().add(new TCWaypoint("name", "world", "server", x, y, z, integerColor);
//Declare a player.
final Player player = Bukkit.getPlayer("Frostei");   
//Send the packet to the player.
for (TCWaypoint waypoint : TecknixAPI.getInstance().getWaypoints()) {
    waypoint.sendAddPacket(player);
}
   
//Removal...
//Follow previous steps but use the below instead when sending the packet:
waypoint.sendRemovePacket(player);
```
## License

```
/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2021 Tecknix Software.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
```

