package comandos;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mojang.datafixers.types.templates.List;

import main.MiPlugin;

public class Comandos implements CommandExecutor {

	private MiPlugin plugin;

	public Comandos(MiPlugin miPlugin) {
		this.plugin = miPlugin;
	}

	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(plugin.nombre + "No se puede ejecutar desde la consola");
			return false;
		} else {

			Player jugador = (Player) sender;
			if (args.length == 1) {
				jugador.sendMessage("Especifica numero de dados y sus caras");
			} else if (args.length > 1) {
				if (Integer.parseInt(args[0]) <= 10) {
					String strListNumeros = "&4" + jugador.getName() + " &ba sacado: ";
					for (int i = 0; i < Integer.parseInt(args[0]); i++) {
						int aleatorio1 = (int) Math.abs(Math.random() * Integer.parseInt(args[1])) + 1;
						String strAleatorio1 = Integer.toString(aleatorio1);
						int numero = i + 1;
						strListNumeros += " &" + numero + strAleatorio1 + "&r";
					}
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', strListNumeros));
				} else {
					jugador.sendMessage("ESTAS DEMENTE!, quieres explotar el servidor verdad?");
					jugador.sendMessage("No puedes tirar mas de 10 dados");
				}
			} else {
				int aleatorio = (int) Math.abs(Math.random() * 6) + 1;
				Bukkit.broadcastMessage(
						ChatColor.translateAlternateColorCodes('&', "&bhas sacado &4" + aleatorio + " &r&bEn el dado"));
			}

			return true;
		}

	}

}
