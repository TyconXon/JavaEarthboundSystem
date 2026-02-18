require "planetInevitable/Scriptability"
Scriptability.say('apple \\rsauce')
--jframe = luajava.bindClass( "javax.swing.JFrame" )
--frame = luajava.newInstance( "javax.swing.JFrame", "Texts" );
--frame:setDefaultCloseOperation(jframe.EXIT_ON_CLOSE)
--frame:setSize(300,400)
--frame:setVisible(true)

eb = luajava.bindClass( "planetInevitable.EarthBound" )
--StatsClass = luajava.bindClass( "planetInevitable.helpers.Stats" )
--eb:print("\\bapple daaa")

--apple = Scriptability.createCharacter("Afton")
--print(apple)

--print(apple.)

pm = luajava.bindClass( "planetInevitable.game.PartyMember" )

stats = luajava.bindClass( "planetInevitable.helpers.Stats" )
istats = luajava.newInstance( "planetInevitable.helpers.Stats");
istats:defaultStats();

--ipm = luajava.newInstance( "planetInevitable.game.PartyMember", "Texts", istats, );
