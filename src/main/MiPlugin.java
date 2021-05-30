package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import comandos.Comandos;
import io.netty.handler.codec.mqtt.MqttPublishVariableHeader;

public class MiPlugin extends JavaPlugin {
	PluginDescriptionFile pdffile = getDescription();
	
	
	public String version =pdffile.getVersion();
	public String nombre = ChatColor.BLUE+pdffile.getName();
	
	private FileConfiguration messages=null;
	private File messagesFile=null;


	private String rutaHistorial;
	
	
	//primero que se ejuca cunado se inicia el plugin
	//mensaje por consola cuando se activa el plugin
	public void onEnable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+"Se Activo el plugin Dado :D"+ version);
		comandosRegistrados();
		registerHistorial();
	}
	
	//desactiva el plugin
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage(nombre+ChatColor.RED+"Se desactivo el plugin mi Dado :("+ version);
	}
	
	//Se registran los comandos que se quiene escuchar
	public void comandosRegistrados() {
		this.getCommand("dado").setExecutor(new Comandos(this));
		
	}
	
	
	public FileConfiguration getHistorial(){
		if(messages == null){
			reloadMessages();
		}
		return messages;
	}
 
	public void reloadMessages(){
		if(messages == null){
			messagesFile = new File(getDataFolder(),"historial.yml");
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);
		Reader defConfigStream;
		try{
			defConfigStream = new InputStreamReader(this.getResource("historial.yml"),"UTF8");
			if(defConfigStream != null){
				YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
				messages.setDefaults(defConfig);
			}			
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}
 
	public void saveHistorial(){
		try{
			messages.save(messagesFile);			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
 
	public void registerHistorial(){
		messagesFile = new File(this.getDataFolder(),"historial.yml");
		rutaHistorial = messagesFile.getPath();
		if(!messagesFile.exists()){
			this.getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
}
