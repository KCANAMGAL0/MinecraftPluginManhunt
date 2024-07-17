package org.manhunt.MhClass;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.manhunt.Main;
import org.manhunt.player.actionbar.ActionbarManager;
import org.manhunt.player.playerManager;
import org.manhunt.player.team.MhTeam;
import org.manhunt.player.team.attackOnPlayer;

import java.util.HashMap;

public class Vampire extends MhClassManager {
    public static HashMap<Player,Integer> Blooding;
    public static HashMap<Player,Long> BloodingCoolDown;
    public static HashMap<Player,Integer> ParticleTimer;
    static {
        Blooding = new HashMap<>();
        BloodingCoolDown = new HashMap<>();
        ParticleTimer = new HashMap<>();
    }
    public void BloodingSkill(Player player,Player victim ,EntityDamageByEntityEvent e){
        double a = 1;
        playerManager pM = new playerManager();
        if (pM.hasEffect(player, PotionEffectType.INCREASE_DAMAGE, 0)) {
            a -= 0.4348;
        }
        if (new MhClassManager().getPlayerClass(victim).equals("Cac") && new Cactus().isGetMoreDamage(victim)){
            a += 0.3;
        }
        e.setDamage(e.getDamage() * a);
        if (Blooding.get(player) <= 1){
            Blooding.put(player, Blooding.get(player) + 1);
        }else if (Blooding.get(player) == 2){
            Blooding.put(player,3);
        }else if (Blooding.get(player) == 3){
            player.playSound(player.getLocation(), Sound.WITHER_SHOOT,1.0F,1.0F);
            player.sendMessage(ChatColor.GREEN + "你的吸血技能已激活!");
            pM.Heal(player,e.getFinalDamage());
            Blooding.put(player,0);
        }
    }

    public void BloodingSkill0(Player player){
        MhTeam mhTeam = new MhTeam();
        int a = 0;
        playerManager pM = new playerManager();
        if (BloodingCoolDown.containsKey(player) && BloodingCoolDown.get(player) > System.currentTimeMillis()){
            return;
        }else {
            for (Player player1 : mhTeam.getHunterPlayers()){
                if (player1.getLocation().getWorld().equals(player.getLocation().getWorld())){
                    if (getDistance(player1.getLocation(),player.getLocation()) <= 5){
                        new attackOnPlayer().setPlayerLastDamager(player1,player);
                        player1.damage(3d);
                        if (super.getPlayerClass(player1).equals(MhClasses.Doc.getName())) new Doctor().addDamage(player1,3d);
                        pM.addEffect(player1,PotionEffectType.WEAKNESS,120,3,false);
                        a++;
                    }
                }
            }
            pM.Heal(player,a * 3d);
            player.playSound(player.getLocation(), Sound.WITHER_IDLE,1.0F,1.0F);
            player.sendMessage(ChatColor.GREEN + "你的鲜血汲取技能已激活!本次恢复" + (a * 3) + "点生命值");
            BloodingCoolDown.put(player,System.currentTimeMillis() + 40 * 1000L);
            ParticleTimer.put(player,40);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (ParticleTimer.get(player) == 0){
                        this.cancel();
                    }else {
                        ParticleTimer.put(player,ParticleTimer.get(player) - 1);
                        player.getWorld().playEffect(new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY() + 2,player.getLocation().getZ()), Effect.HEART,0);
                    }
                }
            }.runTaskTimer((Main.getPlugin(Main.class)),1L,1L);
        }
    }
    public void resetBloodingSkill(Player player){
        Blooding.put(player,0);
    }

    public double getDistance(Location l1,Location l2){
        double[] a = new double[]{l1.getX(),l1.getY(),l1.getZ()};
        double[] b = new double[]{l2.getX(),l2.getY(),l2.getZ()};
        double c = 0;
        for (int i = 0;i <= 2; i++){
            c = (a[i] - b[i])*(a[i] - b[i]);
        }
        return Math.sqrt(c);

    }

    public void VampireSkillDisplay(Player player){
        ActionbarManager actionbarManager = new ActionbarManager();
        String g = ChatColor.GRAY.toString();
        if (BloodingCoolDown.containsKey(player) && BloodingCoolDown.get(player) > System.currentTimeMillis()) {
            String a = String.valueOf((int) ((BloodingCoolDown.get(player) - System.currentTimeMillis()) /1000));
            if (Blooding.get(player) == 0) {
                actionbarManager.sendActionbar(player, g + "0/" + ChatColor.GREEN + "3", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GRAY + a + "秒 " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            } else if (Blooding.get(player) == 1) {
                actionbarManager.sendActionbar(player, g + "1/" + ChatColor.GREEN + "3", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GRAY + a + "秒 " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            } else if (Blooding.get(player) == 2) {
                actionbarManager.sendActionbar(player, g + "2/" + ChatColor.GREEN + "3", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GRAY + a + "秒 " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            } else if (Blooding.get(player) == 3) {
                actionbarManager.sendActionbar(player, ChatColor.GREEN + ChatColor.BOLD.toString() + "✔", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GRAY + a + "秒 " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            }
        }else {
            if (Blooding.get(player) == 0) {
                actionbarManager.sendActionbar(player, g + "0/" + ChatColor.GREEN + "3", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            } else if (Blooding.get(player) == 1) {
                actionbarManager.sendActionbar(player, g + "1/" + ChatColor.GREEN + "3", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            } else if (Blooding.get(player) == 2) {
                actionbarManager.sendActionbar(player, g + "2/" + ChatColor.GREEN + "3", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            } else if (Blooding.get(player) == 3) {
                actionbarManager.sendActionbar(player, ChatColor.GREEN + ChatColor.BOLD.toString() + "✔", ChatColor.RED + ChatColor.BOLD.toString() + "鲜血汲取 " + ChatColor.GREEN + ChatColor.BOLD.toString() + "✔ " + ChatColor.RED + ChatColor.BOLD.toString() + "吸血 ");
            }
        }
    }
}
