package me.kisr.utils;

import me.kisr.Main;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public enum RoleUtils {
    ;

    public static boolean hasTester(Member member) {
        for (Role role : member.getRoles()) {
            if (role.getId().equals(Main.config.get("TESTER_ROLE"))) {
                return true;
            }
            break;
        }
        return false;
    }
}
