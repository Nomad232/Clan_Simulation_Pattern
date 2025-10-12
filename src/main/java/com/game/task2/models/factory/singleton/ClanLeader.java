package com.game.task2.models.factory.singleton;

import com.game.task2.models.factory.unit.ClanUnit;
import com.game.task2.models.factory.unit.Unit;


public class ClanLeader {
    private static ClanLeader instance;
    private final Unit leader;

    private ClanLeader(Unit leader) {
        this.leader = leader;
        if (leader instanceof ClanUnit clanUnit) {
            clanUnit.setName(leader.getName() + " Лідер");
        }
    }

    public static ClanLeader getInstance(Unit leader) {
        if (instance == null) {
            instance = new ClanLeader(leader);
        }
        return instance;
    }

    public static ClanLeader getInstance() {
        if (instance == null) {
            instance = new ClanLeader(new ClanUnit());
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public Unit getLeader() {
        return leader;
    }
}
