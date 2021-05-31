package comandos;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


import com.mojang.datafixers.types.templates.List;

import main.MiPlugin;

public class Comandos implements CommandExecutor {

	private MiPlugin plugin;

	public Comandos(MiPlugin miPlugin) {
		this.plugin = miPlugin;
	}

	public boolean onCommand(CommandSender sender, Command comando, String label, String[] args) {
		// si se ejecuta desde la consola
		if (!(sender instanceof Player)) {
			Bukkit.getConsoleSender().sendMessage(plugin.nombre + "No se puede ejecutar desde la consola");
			return false;
		} else {
			Player jugador = (Player) sender;

			// si solo introducen un argumento
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
					jugador.sendMessage("Se recargo el plugin");
				} else {
					jugador.sendMessage("Especifica numero de dados y sus caras");
				}
			}
			// para mas de 1 atributo
			else if (args.length > 1) {
				if (args[0].equalsIgnoreCase("historial")) {
					if (Integer.parseInt(args[1]) > 0 && Integer.parseInt(args[1])<20) {
						String path = "Historial.lista";
						FileConfiguration hist = plugin.getHistorial();
						java.util.List<String> listHistoria = hist.getStringList(path);
						
						jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l-- History ------------------"));
						for (int i = listHistoria.size(); i > listHistoria.size() - Integer.parseInt(args[1]); i--) {
							
							String texto =listHistoria.get(i-1);
							jugador.sendMessage(texto.toString());							
						}
						jugador.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&l-- Fin-history ------------------"));					

					} else {
						jugador.sendMessage("Especifique el tamaño del historial que desea ver");
						jugador.sendMessage("Entre 1 a 10,");
					}

				} else if (Integer.parseInt(args[0]) < 9) { // no mas de 8 dados
					String strHistNum = "";
					String strListNumeros = "&4" + jugador.getName() + " &bha sacado: ";
					for (int i = 1; i < Integer.parseInt(args[0])+1; i++) { // crea los aleatorios dependiendo el
																			// argumento
						int aleatorio1 = (int) Math.abs(Math.random() * Integer.parseInt(args[1])) + 1;
						String strAleatorio1 = Integer.toString(aleatorio1); // los concatena en el formato de & para el
																				// color
						strHistNum += strAleatorio1 + " ";
						int numero = i + 1;
						strListNumeros += " &" + numero + strAleatorio1 + "&r";
					}
					guardHistorial(jugador, strHistNum);
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', strListNumeros)); // imprime la
																											// lista de
																											// los
																											// resultados
																											// concatenados
																											// anteriormente
				} else {// si colocan mas de 10 dados
					jugador.sendMessage("ESTAS DEMENTE!, quieres explotar el servidor verdad?");
					jugador.sendMessage("No puedes tirar mas de 10 dados");
				}
			}
			// si solo escribe /dado esto sera el por defecto
			else {
				int aleatorio = (int) Math.abs(Math.random() * 6) + 1;
				String strAleatorio = Integer.toString(aleatorio);
				Bukkit.broadcastMessage(
						ChatColor.translateAlternateColorCodes('&',"&4"+jugador.getName()+ "&b ha sacado &4" + aleatorio ));
				guardHistorial(jugador, strAleatorio);

			}
			return true;
		}
	}

	public void guardHistorial(Player jugadors, String strAleatorio) {
		FileConfiguration hist = plugin.getHistorial();
		String path = "Historial.lista";
		String strHistorial = jugadors.getName() + " " + strAleatorio;

		if (hist.contains(path)) {
			java.util.List<String> listHistoria = hist.getStringList(path);
			listHistoria.add(strHistorial);
			hist.set(path, listHistoria);
			plugin.saveHistorial();

		} else {
			java.util.List<String> listHistoria = new java.util.ArrayList<String>();
			listHistoria.add(strHistorial);
			hist.set(path, listHistoria);
			plugin.saveHistorial();

		}

	}
}
