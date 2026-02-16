package planetInevitable;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.*;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Scriptability extends TwoArgFunction {
    public Scriptability() {}

    static void main(String[] args) throws Exception {
        String script = "lua/apple.lua";

        // create an environment to run in
        Globals globals = JsePlatform.standardGlobals();

        // Use the convenience function on Globals to load a chunk.
        LuaValue chunk = globals.loadfile(script);

        // Use any of the "call()" or "invoke()" functions directly on the chunk.
        chunk.call( LuaValue.valueOf(script) );
    }

    public LuaValue call(LuaValue modname, LuaValue env) {
        LuaValue library = tableOf();
        library.set("say", new say());
        env.set( "Scriptability", library );
        return library;
    }

    static class say extends OneArgFunction {
        public LuaValue call(LuaValue x) {
            EarthBound.say(x.checkjstring());
            return LuaValue.NIL;
        }
    }
}
