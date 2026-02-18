package planetInevitable;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.ThreeArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.*;
import org.luaj.vm2.lib.jse.JsePlatform;
import planetInevitable.game.PSI;
import planetInevitable.game.PartyMember;
import planetInevitable.helpers.Stats;
import planetInevitable.*;

import java.util.HashSet;
import java.util.Scanner;

public class Scriptability extends TwoArgFunction {
    public Scriptability() {}

    static void main(String[] args) throws Exception {
        String script = "lua/apple.lua";

        // create an environment to run in
        Globals globals = JsePlatform.standardGlobals();

        // Use the convenience function on Globals to load a chunk.
        // LuaValue chunk = globals.loadfile(script);

        var inputter = new Scanner(System.in);
        do {
            String input = inputter.nextLine();
            if(input.equals("x")) {
                break;
            }

            try {
                // Use any of the "call()" or "invoke()" functions directly on the chunk.
                globals.loadfile(script).call(LuaValue.valueOf(script));
            } catch (Exception e) {
                System.out.println(e.toString());
            }

        }while(true);

    }

    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("say", new say());
        library.set("createCharacter", new createCharacter());
        env.set( "Scriptability", library );
        return library;
    }



    static class say extends OneArgFunction {
        public LuaValue call(LuaValue x) {
            EarthBound.say(x.checkjstring());
            return LuaValue.NIL;
        }
    }

    public class createCharacter extends OneArgFunction {
        public LuaValue call(LuaValue x){
            Stats defaultStats = new Stats();
            defaultStats.defaultStats();
            return LuaValue.userdataOf(new PartyMember(x.checkjstring(), defaultStats, new HashSet<PSI>() ));
        }
    }



//    static class party_member extends TwoArgFunction {
//        public LuaValue call(LuaValue x, LuaValue y) {
//            Stats defaultStats = new Stats();
//            defaultStats.defaultStats();
//            return LuaValue.val new PartyMember(x.checkjstring(),defaultStats,new HashSet<PSI>());
//        }
//    }

}
