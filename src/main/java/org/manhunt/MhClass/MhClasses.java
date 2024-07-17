package org.manhunt.MhClass;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public enum MhClasses {

    Miner("Miner",ChatColor.GRAY + "[矿工]",ChatColor.GREEN + "矿工",new MhClassLore().getClassLore("Miner")),
    Vam("Vam",ChatColor.GRAY + "[吸血鬼]",ChatColor.GREEN + "吸血鬼",new MhClassLore().getClassLore("Vam")),
    Arc("Arc",ChatColor.GRAY + "[弓箭手]",ChatColor.GREEN + "弓箭手",new MhClassLore().getClassLore("Arc")),
    Pal("Pal",ChatColor.GRAY + "[圣骑士]",ChatColor.GREEN + "圣骑士",new MhClassLore().getClassLore("Pal")),
    Irg("Irg",ChatColor.GRAY + "[铁傀儡]",ChatColor.GREEN + "铁傀儡",new MhClassLore().getClassLore("Irg")),
    Ass("Ass",ChatColor.GRAY + "[刺客]",ChatColor.GREEN + "刺客",new MhClassLore().getClassLore("Ass")),
    Doc("Doc",ChatColor.GRAY + "[医师]",ChatColor.GREEN + "医师",new MhClassLore().getClassLore("Doc")),
    Esc("Esc",ChatColor.GRAY + "[炎魔]",ChatColor.GREEN + "炎魔",new MhClassLore().getClassLore("Esc")),
    Lng("Lng",ChatColor.GRAY + "[雷神]",ChatColor.GREEN + "雷神",new MhClassLore().getClassLore("Lng")),
    Cac("Cac",ChatColor.GRAY + "[仙人掌]",ChatColor.GREEN + "仙人掌",new MhClassLore().getClassLore("Cac")),
    Sac("Sac",ChatColor.GRAY + "[魔龙祭祀]",ChatColor.GREEN + "魔龙祭祀",new MhClassLore().getClassLore("Sac")),
    Gua("Gua",ChatColor.GRAY + "[守卫者]",ChatColor.GREEN + "守卫者",new MhClassLore().getClassLore("Gua")),
    Rob("Rob",ChatColor.GRAY + "[不法者]",ChatColor.GREEN + "不法者",new MhClassLore().getClassLore("Rob")),
    MM("MM",ChatColor.GRAY + "[机械天才]",ChatColor.GREEN + "机械天才",new MhClassLore().getClassLore("MM"));

    private String name;
    private String displayName;
    private List<String> Lore;
    private String menuName;

    public String getName(){
        return name;
    }
    public String getDisplayName(){
        return displayName;
    }
    public List<String> getLore(){
        return Lore;
    }
    public String getClassMenuName(){
        return menuName;
    }
    MhClasses(String name, String displayName, String ClassMenuName,List<String> Lore){
        this.name = name;
        this.displayName = displayName;
        this.Lore = Lore;
        this.menuName = ClassMenuName;
    }
}

class MhClassLore {
    public List<String> getClassLore(String name){
        List<String> a = new ArrayList<>();
        a.add("");
        switch (name){
            case "Doc":
                a.add(ChatColor.AQUA + "自愈:" + ChatColor.GRAY + "承受伤害累计达10点后获得3秒生命恢复III,冷却12秒");
                a.add(ChatColor.AQUA + "治疗箭矢:" + ChatColor.GRAY + "使用弓箭命中队友时为队友恢复4点生命值,冷却3秒");
                break;
            case "Vam":
                a.add(ChatColor.AQUA + "吸血:" + ChatColor.GRAY + "近战攻击3次后下一次攻击将恢复等额伤害的生命值");
                a.add(ChatColor.AQUA + "鲜血汲取:" + ChatColor.GRAY + "双击SHIFT对周围的敌人造成3点真实伤害与6");
                a.add(ChatColor.GRAY + "秒虚弱IV,每个被汲取的敌人都将为你恢复3点生命值,冷却40秒");
                break;
            case "Miner":
                a.add(ChatColor.AQUA + "效率铁镐:" + ChatColor.GRAY + "游戏开始时你将获得一把效率I的铁镐");
                a.add(ChatColor.AQUA + "急迫:" + ChatColor.GRAY + "本局你将获得急迫II效果");
                a.add(ChatColor.AQUA + "幸运:" + ChatColor.GRAY + "挖掘金矿时必定掉落2个金锭,挖掘绿宝石矿时额外掉落1个钻石");
                break;
            case "Arc":
                a.add(ChatColor.AQUA + "强化箭矢:" + ChatColor.GRAY + "拉满弓时射出的箭矢伤害提升20%");
                a.add(ChatColor.GRAY + "并且此箭矢命中后造成3秒缓慢1");
                a.add(ChatColor.AQUA + "箭矢回收:" + ChatColor.GRAY + "触发强化箭矢技能时返还1支箭矢");
                break;
            case "Irg":
                a.add(ChatColor.AQUA + "击飞:" + ChatColor.GRAY + "潜行攻击敌人可将其击飞,冷却10秒");
                a.add(ChatColor.AQUA + "钢铁之击" + ChatColor.GRAY + "双击SHIFT将附近的敌人击飞并造成5点真实伤害与短暂失明效果，冷却30秒");
                break;
            case "Esc":
                a.add(ChatColor.AQUA + "岩浆桶:" + ChatColor.GRAY + "双击SHIFT获得1个岩浆桶,冷却60秒");
                a.add(ChatColor.AQUA + "灼烧:" + ChatColor.GRAY + "近战攻击着火的敌人时伤害提高15%");
                break;
            case "Pal":
                a.add(ChatColor.AQUA + "圣光庇护:" + ChatColor.GRAY + "双击SHIFT使所有勇者获得15秒抗性提升II与伤害吸收II" + ChatColor.YELLOW + "(4❤)" + ChatColor.GRAY + "冷却50秒");
                a.add(ChatColor.AQUA + "坚韧:" + ChatColor.GRAY + "受到的弹射物攻击伤害减少25%并获得3秒抗性提升I");
                break;
            case "Ass":
                a.add(ChatColor.AQUA + "隐匿身形:");
                a.add(ChatColor.GRAY + "双击SHIFT获得6秒隐身与速度I效果,冷却45秒，隐身期间若近战攻击敌人则伤害提");
                a.add(ChatColor.GRAY + "高50%并取消隐身效果，若隐身期间未进行攻击，则隐身效果消失后获得8秒生命恢复I");
                a.add(ChatColor.AQUA + "背刺:");
                a.add(ChatColor.GRAY + "从后方近战攻击敌人时伤害提高15%并减少3秒'隐匿身形'的冷却时间");
                break;
            case "Gua":
                a.add(ChatColor.AQUA + "充能:" );
                a.add(ChatColor.GRAY + "近战攻击获得5点能量，弓箭命中获得10点能量,上限100点");
                a.add(ChatColor.AQUA + "深海诅咒:" );
                a.add(ChatColor.GRAY + "双击SHIFT或左键弓消耗50点能量发射一道射线对沿途的敌人造成1.5点伤害与3秒剧毒II、挖掘疲劳IV效果");
                a.add(ChatColor.AQUA + "神经元:" + ChatColor.GRAY + "血量低于最大生命值的50%时受到近战伤害将生成1个怪物为你作战并清空能量，冷却20秒");
                break;
            case "Lng":
                a.add(ChatColor.AQUA + "雷击:" + ChatColor.GRAY + "近战攻击5次后下一次攻击将召唤闪电并额外附加3点真实伤害");
                break;
            case "Cac":
                a.add(ChatColor.AQUA + "反伤:" + ChatColor.GRAY + "受到玩家攻击时对攻击者造成相同的伤害并获得5秒速度I,冷却15秒");
                a.add(ChatColor.AQUA + "突进:" + ChatColor.GRAY + "近战攻击后0.5秒内使用剑格挡可向前突进一段距离");
                a.add(ChatColor.AQUA + "易损:" + ChatColor.GRAY + "触发突进后3秒内你受到所有来自的玩家伤害将提升30%");
                break;
            case "Sac":
                a.add(ChatColor.AQUA + "末影水晶:" );
                a.add(ChatColor.GRAY + "在末地时双击SHIFT生成1个持续30秒的末影水晶，冷却75秒，若持");
                a.add(ChatColor.GRAY + "续时间内此末影水晶未被摧毁，则持续时间结束后末影龙恢复25点血量");
                a.add(ChatColor.AQUA + "魔龙火焰:" + ChatColor.GRAY + "拉满弓时射出的箭将带有火矢效果");
                break;
            case "Rob":
                a.add(ChatColor.AQUA + "充能:" + ChatColor.GRAY + "使用弓箭命中敌人获得10点能量，近战攻击敌人获得8点能量,上限100点");
                a.add(ChatColor.AQUA + "抓钩:");
                a.add(ChatColor.GRAY + "能量达到60点时左键弓消耗全部能量向前扔出一个抓钩并飞向");
                a.add(ChatColor.GRAY + "它，能量不满时抓钩的飞行距离仅为满能力时的一半，冷却30秒");
                a.add(ChatColor.AQUA + "撕裂箭矢:");
                a.add(ChatColor.GRAY + "使用弓箭命中敌人将会在其体内留下1支箭，上限8支，能量达到");
                a.add(ChatColor.GRAY + "100点时双击SHIFT消耗全部能量拔出附近敌人身上所有的箭且");
                a.add(ChatColor.GRAY + "你将获得10秒虚弱II，每拔出1支箭将造成1.25点伤害，冷却15秒");
                break;
            case "MM":
                a.add(ChatColor.AQUA + "制导箭矢:" + ChatColor.GRAY + "潜行时拉满弓射箭时将附带自瞄效果");
                a.add(ChatColor.AQUA + "加农炮:" + ChatColor.GRAY + "双击SHIFT生成一个持续15秒的加农炮塔，冷却50秒");
                a.add(ChatColor.GOLD + "   炮塔:" + ChatColor.GRAY + "HP: 不可摧毁，伤害: 2HP，射速: 1秒/发");
                a.add(ChatColor.GRAY + "         攻击范围: 7.5格，索敌: 单个,最大血量优先");
                break;
            default: break;
        }
        return a;
    }
}
