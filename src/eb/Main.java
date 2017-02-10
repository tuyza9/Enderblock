package eb;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
    
    public static FileConfiguration conf;
    public static File cf;
    
    public class status{
        public String status = conf.getString("enable");
    }
    
    public void onEnable(){
        
        conf = getConfig();
        conf.options().copyDefaults(true);
        saveDefaultConfig();
        
        cf = new File(getDataFolder(), "config.yml");
        
    PluginManager manager = this.getServer().getPluginManager();
    manager.registerEvents(this, this);
    manager.registerEvents((Listener) new Commands(),this);
    regCmd();
}
    
    public void onDisable(){
        conf = YamlConfiguration.loadConfiguration(cf);
        Bukkit.getServer().getPluginManager().disablePlugins();
    }
    
    public void regCmd(){
        getCommand("ebreload").setExecutor(new Commands());
    }
    
      @EventHandler
  public void onPlayerLogin(EntityPortalEnterEvent event) {
      status s = new status();
      if(s.status == "true"){
    if ((event.getEntity() instanceof Player)) ((Player)event.getEntity()).performCommand("spawn");
  }else if(s.status == "false"){
      Bukkit.broadcastMessage(ChatColor.DARK_BLUE + "Enderblock be has unavailable." + "\n" + ChatColor.RESET + "edit with config.yml");
  }
  }
  
  public class Commands implements Listener,CommandExecutor{

        @Override
        public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
            if(Sender instanceof Player){
                if(Sender.isOp()){
            if(cmd.getName().equalsIgnoreCase("ebreload")){
                conf = YamlConfiguration.loadConfiguration(cf);
                Sender.sendMessage(ChatColor.GREEN + "reload succss");
            }
                }else{
                    Sender.sendMessage(ChatColor.DARK_RED + "You don't has admin.");
                }
            }else{
                Sender.sendMessage(ChatColor.DARK_RED + "As the only players.");
            }
            return true;
        }
      
  }
  
}
