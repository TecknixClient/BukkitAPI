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

package com.tecknix.api.command;

import com.tecknix.api.TecknixAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClientCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for players.");
            return false;
        }

        Player player = (Player) sender;

        if (args.length > 0) {
            // Gets the player from the argument and checks if they are not null.
            player = Bukkit.getPlayer(args[0]);

            if (player == null) {
                sender.sendMessage(ChatColor.RED + "That player was not found.");
                return false;
            }
        }

        // If the player is running tecknix send the message saying they are. Otherwise send the not running message.
        if (TecknixAPI.getInstance().isOnTecknix(player)) {
            sender.sendMessage(ChatColor.DARK_PURPLE + player.getDisplayName() + " is running Tecknix Client!");
        } else {
            sender.sendMessage(ChatColor.RED + player.getDisplayName() + " is not running Tecknix Client!");
        }
        return true;
    }
}
