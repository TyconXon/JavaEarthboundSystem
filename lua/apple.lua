require "planetInevitable/Scriptability"
Scriptability.say('apple \\rsauce')
--jframe = luajava.bindClass( "javax.swing.JFrame" )
--frame = luajava.newInstance( "javax.swing.JFrame", "Texts" );
--frame:setDefaultCloseOperation(jframe.EXIT_ON_CLOSE)
--frame:setSize(300,400)
--frame:setVisible(true)

eb = luajava.bindClass( "planetInevitable.EarthBound" )
eb:print("\\bapple aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")

pm = luajava.bindClass( "planetInevitable.game.PartyMember" )

stats = luajava.bindClass( "planetInevitable.helpers.Stats" )
istats = luajava.newInstance( "planetInevitable.helpers.Stats");
istats:defaultStats();

ipm = luajava.newInstance( "planetInevitable.game.PartyMember", "Texts", istats, );
