package main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import comandos.Comandos;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;

public class MiPlugin extends JavaPlugin {
	PluginDescriptionFile pdffile = getDescription();
	
	public String version =pdffile.getVersion();
	public String nombre = ChatColor.BLUE+pdffile.getName();
	
	//primero que se ejuca cunado se inicia el plugin
	//mensaje por consola cuando se activa el plugin
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+"Se Activo el plugin Dado :D"+ version);
		comandosRegistrados();
	}
	
	//desactiva el plugin
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+"Se desactivo el plugin mi Dado :("+ version);
	}
	
	//Se registran los comandos que se quiene escuchar
	public void comandosRegistrados() {
		this.getCommand("dado").setExecutor(new Comandos(this));
		
	}
	
}
